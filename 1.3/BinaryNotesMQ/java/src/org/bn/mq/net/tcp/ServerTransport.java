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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

import java.net.URISyntaxException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

import org.bn.mq.net.ITransportListener;
import org.bn.mq.protocol.MessageEnvelope;

public class ServerTransport extends Transport {
    private ServerSocketChannel serverChannel = null;
    protected List<ServerClientTransport> clients = new LinkedList<ServerClientTransport>();
    protected AcceptorFactory acceptorFactory;
    
    public ServerTransport(URI addr, AcceptorFactory acceptorFactory) {
        super(addr, null);
        setSocket(null);      
        this.acceptorFactory = acceptorFactory;
    }
    
    public synchronized void startListener() throws IOException {
        close();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().setReuseAddress(true);
        serverChannel.socket().bind(new InetSocketAddress( getAddr().getHost(), getAddr().getPort()));
        
    }
    
    public void close() {        
        if(isAvailable()) {
            try {
                getServerSocket().close();
            }
            catch (IOException e) {
                // TODO
            }            
        }
         
         synchronized(clients) {
             for(ServerClientTransport transport: clients) {
                 transport.close();
             }
             clients.clear();
         }        
    }
    
    
    public synchronized boolean isAvailable() {
        return getServerSocket() != null && getServerSocket().isOpen();
    }
    
    public boolean acceptClient() throws IOException {
        SocketChannel client = getServerSocket().accept();        
        if(client!=null) {            
            InetAddress addr = client.socket().getInetAddress();
            System.out.println("Client connected from "+addr);            
            client.configureBlocking(false);
            ServerClientTransport transport;
            try {
                transport = 
                        new ServerClientTransport( new URI("bnmq",addr.getHostAddress(),"",""), this, acceptorFactory );
                transport.setSocket(client);
                synchronized(clients) {
                    clients.add(transport);
                    fireConnectedEvent(transport);
                }
                
            }
            catch (URISyntaxException e) {
                e = null;
            }            
        }
        return client!=null;
    }
    
    public void removeClient(ServerClientTransport transport) {
        synchronized(clients) {
            clients.remove(transport);
            fireDisconnectedEvent(transport);
        }
    }
    
    protected void fireReceivedData(MessageEnvelope message, ServerClientTransport client) {
        synchronized(listeners) {
            for(ITransportListener listener: listeners) {
                listener.onReceive(message,client);
            }
        }
    }
    
    protected void fireConnectedEvent(ServerClientTransport client) {
        synchronized(listeners) {
            for(ITransportListener listener: listeners) {                    
                listener.onConnected(client);
            }            
        }
    }

    protected void fireDisconnectedEvent(ServerClientTransport client) {
        synchronized(listeners) {
            for(ITransportListener listener: listeners) {                    
                listener.onDisconnected(client);
            }            
        }
    }
    
    
    public void finalize() {
        close();
    }

    public ServerSocketChannel getServerSocket() {
        return serverChannel;
    }

    public void setServerSocket(ServerSocketChannel serverChannel) {
        this.serverChannel = serverChannel;
    }

    protected void onTransportClosed() {
        // Do nothing. 
        //fireDisconnectedEvent();
    }
}
