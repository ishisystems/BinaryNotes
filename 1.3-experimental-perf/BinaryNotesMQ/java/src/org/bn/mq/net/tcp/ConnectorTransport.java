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

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectorTransport extends Transport {    
    protected final Lock awaitConnectLock = new ReentrantLock();
    protected final Condition awaitConnectEvent  = awaitConnectLock.newCondition(); 
    protected final ConnectorFactory factory;

    public ConnectorTransport(URI addr, ConnectorFactory factory) {
        super(addr, factory);
        this.factory = factory;
    }
    
    public boolean connect() throws IOException {
        SocketChannel cSocket = SocketChannel.open(new InetSocketAddress( getAddr().getHost(), getAddr().getPort()));
        cSocket.configureBlocking(false);
        if(cSocket.finishConnect()) {
            setSocket(cSocket);
            return true;
        }
        else
            return false;
    }
    
    protected void onConnected() {
        awaitConnectLock.lock();
        awaitConnectEvent.signal();
        fireConnectedEvent();        
        awaitConnectLock.unlock();
    }

    protected void onNotConnected() {
        awaitConnectLock.lock();
        awaitConnectEvent.signal();
        awaitConnectLock.unlock();
        factory.reconnect(this);
    }
    
    protected void onDisconnect() {        
        socketLock.writeLock().lock();
        fireDisconnectedEvent();
        if(getSocket()!=null) {
            setSocket(null);
            factory.reconnect(this);
        }        
        socketLock.writeLock().unlock();        
    }
    
    protected void onTransportClosed() {
        onDisconnect();
    }
    
    
    public boolean finishConnect() {
        awaitConnectLock.lock();
        try {
            awaitConnectEvent.await();
        }
        catch (InterruptedException e) {
            // TODO
        }
        awaitConnectLock.unlock();  
        return isAvailable();
    }
    
    public void send(ByteBuffer buffer) throws IOException {
        try {
            super.send(buffer);
        }
        catch(IOException ex) {
            if(!isAvailable()) {
                onDisconnect();
            }
            throw ex;
        }
    }

}
