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
package test.org.bn.mq.net;

import java.net.URI;
import junit.framework.TestCase;

import org.bn.mq.net.ITransport;
import org.bn.mq.net.ITransportListener;
import org.bn.mq.net.ASN1TransportMessageCoderFactory;
import org.bn.mq.net.ITransportCallListener;
import org.bn.mq.net.ITransportReader;
import org.bn.mq.net.tcp.TransportFactory;
import org.bn.mq.protocol.MessageBody;
import org.bn.mq.protocol.MessageEnvelope;
import org.bn.mq.protocol.MessageUserBody;


public class MessageDecoderThreadTest extends TestCase {

    public MessageDecoderThreadTest(String sTestName) {
        super(sTestName);
    }
    
    protected MessageEnvelope createMessage(String vl) {
        MessageEnvelope message = new MessageEnvelope();
        message.setId("MsgId-"+vl);
        MessageBody msgBody = new MessageBody();
        MessageUserBody userBody = new MessageUserBody();
        userBody.setUserBody(new byte[] { (byte)0xFF, (byte)0xFE });
        userBody.setConsumerId(this.toString());
        userBody.setQueuePath("testQueuePath/Temp");
        msgBody.selectMessageUserBody(userBody);
        message.setBody(msgBody);
        return message;
    }

    public void testTakeMessage() throws Exception {
        final String connectionString = "bnmq://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        try {
            conFactory.setTransportMessageCoderFactory(new ASN1TransportMessageCoderFactory());
            
            ITransport server = conFactory.getServerTransport(new URI(connectionString));
            assertNotNull(server);
            MessageListener ml = new MessageListener(this);
            server.addListener(ml);
            server.addReader(ml);
            
            ITransport client = conFactory.getClientTransport(new URI(connectionString));
            ml = new MessageListener(this);
            client.addListener(ml);
            client.addReader(ml);
            assertNotNull(client);
                
            client.send(createMessage("AAAaasasasasassas"));
            client.sendAsync(createMessage("Two"));
            Thread.sleep(500);
        }
        finally {
            conFactory.finalize();
        }
        System.out.println("Finished: testTakeMessage");
    }
    
    public void testCall() throws Exception {
        final String connectionString = "bnmq://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        try {
            conFactory.setTransportMessageCoderFactory(new ASN1TransportMessageCoderFactory());
            
            ITransport server = conFactory.getServerTransport(new URI(connectionString));
            assertNotNull(server);
            CallMessageListener cl = new CallMessageListener(this);
            server.addListener(cl);
            server.addReader(cl);
            Thread.sleep(500);
            
            ITransport client = conFactory.getClientTransport(new URI(connectionString));
            assertNotNull(client);
            MessageEnvelope result = client.call(createMessage("Call"), 10);
            System.out.println("Result call received with Id:"+result.getId()+" has been received successfully");
        }
        finally {
            conFactory.finalize();
        }
        System.out.println("Finished: testCall");
    }    

    public void testAsyncCall() throws Exception {
        final String connectionString = "bnmq://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        try {
            conFactory.setTransportMessageCoderFactory(new ASN1TransportMessageCoderFactory());
            
            ITransport server = conFactory.getServerTransport(new URI(connectionString));
            assertNotNull(server);
            CallMessageListener cl = new CallMessageListener(this);
            server.addListener(cl);
            server.addReader(cl);
            Thread.sleep(500);
            
            ITransport client = conFactory.getClientTransport(new URI(connectionString));
            assertNotNull(client);
            client.callAsync(createMessage("CallAsync"), new AsyncCallMessageListener());
            Thread.sleep(500);
        }
        finally {
            conFactory.finalize();
        }
        System.out.println("Finished: testCall");
    }    

    private class MessageListener implements ITransportListener, ITransportReader {         
        private MessageDecoderThreadTest parent;
        private int counter = 0;
        public MessageListener(MessageDecoderThreadTest parent) {
            this.parent = parent;
        }
        public boolean onReceive(MessageEnvelope message, ITransport transport) {
            System.out.println("Message from "+transport+" with Id:"+message.getId()+" has been received successfully");
            try {
                if(counter<10) {
                    transport.send(parent.createMessage("Three" + counter++));
                    transport.sendAsync(parent.createMessage("Four" + counter++));
                }
                
            }
            catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
            return true;
        }

        public void onConnected(ITransport transport) {
            System.out.println("Connected from "+transport+". Addr:"+transport.getAddr());
        }

        public void onDisconnected(ITransport transport) {
            System.out.println("Disconnected from "+transport+". Addr:"+transport.getAddr());
        }
    }
    
    private class CallMessageListener implements ITransportListener, ITransportReader {         
        private MessageDecoderThreadTest parent;
        private int counter = 0;
        public CallMessageListener(MessageDecoderThreadTest parent) {
            this.parent = parent;
        }
        public boolean onReceive(MessageEnvelope message, ITransport transport) {
            System.out.println("Call from "+transport+" with Id:"+message.getId()+" has been received successfully");
            try {
                MessageEnvelope result = createMessage("result");
                result.setId(message.getId());
                transport.sendAsync(result);
            }
            catch (Exception e) {
                System.err.println(e);
            }
            return true;
        }

        public void onConnected(ITransport transport) {
            System.out.println("Connected from "+transport+". Addr:"+transport.getAddr());
        }

        public void onDisconnected(ITransport transport) {
            System.out.println("Disconnected from "+transport+". Addr:"+transport.getAddr());
        }
    }    
    
    private class AsyncCallMessageListener implements ITransportCallListener {

        public void onCallResult(MessageEnvelope request, 
                                 MessageEnvelope result) {
            System.out.println("Call result received: " + result.toString());
        }

        public void onCallTimeout(MessageEnvelope request) {
            System.out.println("!! Call result timeout !!. Request: " + request.toString());
        }
    }
}
