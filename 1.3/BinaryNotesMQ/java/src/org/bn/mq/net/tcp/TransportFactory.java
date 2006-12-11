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
import org.bn.mq.net.*;

public class TransportFactory implements ITransportFactory {
    private final String scheme = "bnmq";

    protected TransportWriterStorage writerStorage = new TransportWriterStorage();
    protected TransportReaderStorage readerStorage = new TransportReaderStorage();
    
    protected ConnectorFactory conFactory = new ConnectorFactory( writerStorage, readerStorage, this);
    protected AcceptorFactory acpFactory = new AcceptorFactory(writerStorage, readerStorage, this);

    protected Thread writerThread;
    protected TransportWriter writerThreadBody;
    
    protected Thread readerThread;
    protected TransportReader readerThreadBody;
    protected ITransportMessageCoderFactory messageCoderFactory;
    
    public TransportFactory () {
        startAsyncDispatchers();
    }        
    
    public void setTransportMessageCoderFactory(ITransportMessageCoderFactory coderFactory) {
        this.messageCoderFactory = coderFactory;
    }
    
    public ITransportMessageCoderFactory getTransportMessageCoderFactory() {
        return messageCoderFactory;
    }
    
    public ITransport getClientTransport(URI addr) throws IOException {        
        return conFactory.getTransport(addr);
    }

    public ITransport getServerTransport(URI addr) throws IOException {        
        return acpFactory.getTransport(addr);
    }

    public boolean checkURISupport(URI addr) {
        return addr.getScheme().equalsIgnoreCase(scheme);
    }
    
    protected void startAsyncDispatchers() {
        writerThreadBody = new TransportWriter(writerStorage);
        writerThread = new Thread(writerThreadBody);
        writerThread.start();
        readerThreadBody = new TransportReader(readerStorage);
        readerThread = new Thread(readerThreadBody);
        readerThread.start();
    }
    
    public void finalize() throws InterruptedException {
        writerThreadBody.stop();
        readerThreadBody.stop();
        writerStorage.finalize();        
        writerThread.join();
        readerThread.join();
        
        readerStorage.finalize();
        conFactory.finalize();
        acpFactory.finalize();
    }
    
}
