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
package test.org.bn.mq.net.tcp;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.bn.mq.net.ITransport;
import org.bn.mq.net.TransportMessageCoderFactory;
import org.bn.mq.net.tcp.TransportFactory;

public class TransportFactoryTest extends TestCase {
    public TransportFactoryTest(String sTestName) {
        super(sTestName);
    }
    
    /**
     * @see TransportFactory#getTransport(URI)
     */
    public void testGetTransport() throws URISyntaxException, 
                                          InterruptedException, IOException, 
                                          Throwable {
        final String connectionString = "siberia://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        conFactory.setTransportMessageCoderFactory(new TransportMessageCoderFactory());
        ITransport transport = conFactory.getClientTransport(new URI(connectionString));
        assertNotNull(transport);
        System.out.println("Is connected = " + transport.isAvailable());
        Thread.sleep(2000);
        if(transport.isAvailable()) {
            transport.sendAsync(new byte[] { (byte)0xFF });
            Thread.sleep(1000);
        }
        ITransport transport2 =  conFactory.getClientTransport(new URI(connectionString));
        assertEquals(transport,transport2);
        conFactory.finalize();
    }
    
    public void testGetServerTransport() throws URISyntaxException, 
                                          InterruptedException, IOException, 
                                          Throwable {
        final String connectionString = "siberia://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        conFactory.setTransportMessageCoderFactory(new TransportMessageCoderFactory());
        ITransport transport = conFactory.getServerTransport(new URI(connectionString));
        assertNotNull(transport);
        Thread.sleep(1000);
        conFactory.finalize();
    }
    
    public void testSendRecvServerTransport() throws URISyntaxException, 
                                          InterruptedException, IOException, 
                                          Throwable {
        final String connectionString = "siberia://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        conFactory.setTransportMessageCoderFactory(new TransportMessageCoderFactory());
        ITransport server = conFactory.getServerTransport(new URI(connectionString));
        assertNotNull(server);
        ITransport client = conFactory.getClientTransport(new URI(connectionString));
        assertNotNull(client);
        final byte[] buffer = new byte[] { 0x01, 0x02, 0x03, 0x04 };
        for(int i=0;i<255;i++) {
            client.sendAsync(buffer);
        }
        Thread.sleep(1000);
        conFactory.finalize();
    }
    
}
