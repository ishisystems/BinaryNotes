/*
 * Copyright 2006 Abdulla G. Abdurakhmanov (abdulla.abdurakhmanov@gmail.com).
 * 
 * Licensed under the LGPL, Version 2 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.gnu.org/copyleft/lgpl.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * With any your questions welcome to my e-mail 
 * or blog at http://abdulla-a.blogspot.com.
 */

package org.bn.mq.net.tcp;

import java.io.IOException;

import java.net.URI;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.bn.mq.IMessage;

import org.bn.mq.net.ITransport;
import org.bn.mq.net.ITransportListener;
import org.bn.mq.net.ITransportMessageCoder;
import org.bn.mq.protocol.MessageEnvelope;

public abstract class Transport implements ITransport {
    private URI addr;
    private SocketChannel socket = null;
    protected ReadWriteLock socketLock =  new ReentrantReadWriteLock();
    protected List<ITransportListener> listeners = new LinkedList<ITransportListener>();
    protected SocketFactory socketFactory;
    protected ByteBuffer tempReceiveBuffer = ByteBuffer.allocate(64*1024);
    protected ITransportMessageCoder messageCoder;
    protected final Lock callLock = new ReentrantLock();
    protected final Condition callLockEvent  = callLock.newCondition();    
    protected String currentCallMessageId = "-none-";
    protected MessageEnvelope currentCallMessage = null;
    
    public Transport(URI addr, SocketFactory factory) {
        setAddr(addr);
        this.socketFactory = factory ;
    }
       
    protected void onTransportClosed() {
        close();
    }    
    
    public void close() {
        socketLock.writeLock().lock();
        if(isAvailable()) {
            try {
                getSocket().close();
                setSocket(null);
            }
            catch (IOException e) {
                // TODO
            }
        }
        socketLock.writeLock().unlock();
    }

    
    public ByteBuffer receiveAsync() throws IOException {
        socketLock.readLock().lock();
        SocketChannel channel = getSocket();
        ByteBuffer result = null;
        try {
            if(isAvailable()) {
                result = ByteBuffer.allocate(4096);                
                int readedBytes = -1;
                do {                                        
                    tempReceiveBuffer.clear();
                    readedBytes = channel.read(tempReceiveBuffer);
                    if(readedBytes>0) {
                        if(result.remaining()<readedBytes) {
                            byte[] data = result.array();
                            result = ByteBuffer.allocate(data.length+4096);
                            result.put(data);
                        }
                        result.put(tempReceiveBuffer.array(),result.position(),tempReceiveBuffer.position());
                    }
                    else
                    if(readedBytes==-1) {
                        onTransportClosed();
                    }                    
                }                
                while(readedBytes>0);
                result.flip();
            }
            else {
                throw new IOException("Not connected");
            }
        }
        finally {
            socketLock.readLock().unlock();
        }    
        return result;        
    }
    
    public void sendAsync(ByteBuffer buffer) throws IOException {
        if(socketFactory.getWriterStorage()!=null) {
            if(isAvailable()) {
                socketFactory.getWriterStorage().pushPacket(this, buffer);
            }
            else
                throw new IOException("Transport is not connected!");
        }
        else
            throw new IOException("Unable to write readonly transport!");
    }
    
    public void sendAsync(byte[] buffer) throws IOException {
        sendAsync(ByteBuffer.wrap(buffer));
    }
    
    public void send(MessageEnvelope message) throws Exception {
        ByteBuffer buffer = messageCoder.encode(message);
        send(buffer);
    }
        
    public void sendAsync(MessageEnvelope message) throws Exception {
        ByteBuffer buffer = messageCoder.encode(message);
        sendAsync(buffer);
    }     
    
    public void send(ByteBuffer buffer) throws IOException {
        socketLock.readLock().lock();        
        try {                        
            if(isAvailable()) {
                SocketChannel channel = getSocket();
                if(channel!=null)
                    channel.write(buffer);
            }
            else {
                throw new IOException("Not connected");
            }
        }
        finally {
            socketLock.readLock().unlock();
        }    
    }

    public void send(byte[] buffer) throws IOException {
        send(ByteBuffer.wrap(buffer));
    }
    
    public void addListener(ITransportListener listener) {
        synchronized(listeners) {
            listeners.add(listener);
        }
    }

    public void delListener(ITransportListener listener) {
        synchronized(listeners) {
            listeners.remove(listener);
        }    
    }
    
    protected void fireConnectedEvent() {
        synchronized(listeners) {
            for(ITransportListener listener: listeners) {                    
                listener.onConnected(this);
            }            
        }
    }

    protected void fireDisconnectedEvent() {
        synchronized(listeners) {
            for(ITransportListener listener: listeners) {                    
                listener.onDisconnected(this);
            }            
        }
    }
    
    protected void fireReceivedData(ByteBuffer packet) throws Exception {
        synchronized(listeners) {
            MessageEnvelope message = messageCoder.decode(packet);
            boolean doProcessListeners = true;            
            if(message!=null) {
                callLock.lock();
                if(currentCallMessageId.equals(message.getId())) {
                    currentCallMessage = message;
                    callLockEvent.signal();
                    doProcessListeners = false;
                }
                callLock.unlock();
            }
            if(doProcessListeners) {
                for(ITransportListener listener: listeners) {                    
                    listener.onReceive(message,this);
                }
            }
        }
    }

    public synchronized MessageEnvelope call(MessageEnvelope message, int timeout) throws Exception {
        MessageEnvelope result = null;
        callLock.lock();
        currentCallMessage = null;
        currentCallMessageId = message.getId();        
        try {
            sendAsync(message);
            callLockEvent.await(timeout,TimeUnit.SECONDS);
            result = currentCallMessage;
            currentCallMessageId = "";
        }
        finally {
            callLock.unlock();
        }
        return result;    
    }
    
    public synchronized MessageEnvelope call(MessageEnvelope message) throws Exception {
        return this.call(message,120); // By default timeout is 2 min
    }

    public URI getAddr() {
        return addr;
    }

    protected void setAddr(URI addr) {
        this.addr = addr;
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public void setSocket(SocketChannel socket) {
        socketLock.writeLock().lock();
        this.socket = socket;
        if(this.socketFactory!=null) {
            if(this.socket!=null) {
                this.socketFactory.getReaderStorage().addTransport(this);
                messageCoder = socketFactory.getTransportFactory().getTransportMessageCoderFactory().newCoder(this);
            }
            else {
                this.socketFactory.getReaderStorage().removeTransport(this);
            }
        }
        socketLock.writeLock().unlock();
    }

    public boolean isAvailable() {
        socketLock.readLock().lock();
        boolean result = getSocket() != null && getSocket().isOpen() && getSocket().isConnected() ;
        socketLock.readLock().unlock();
        return result;
    }
    
    public void finalize() {
        close();
    }
    
    
}
