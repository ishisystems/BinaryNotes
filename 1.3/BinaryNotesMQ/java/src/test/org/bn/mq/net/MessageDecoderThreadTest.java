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
import org.bn.mq.net.TransportMessageCoderFactory;
import org.bn.mq.net.tcp.TransportFactory;
import org.bn.mq.protocol.MessageBody;
import org.bn.mq.protocol.MessageEnvelope;


public class MessageDecoderThreadTest extends TestCase {

    public MessageDecoderThreadTest(String sTestName) {
        super(sTestName);
    }
    
    protected MessageEnvelope createMessage(String vl) {
        MessageEnvelope message = new MessageEnvelope();
        message.setId("MsgId-"+vl);
        MessageBody msgBody = new MessageBody();
        msgBody.selectUserBody(new byte[] { (byte)0xFF, (byte)0xFE });
        message.setBody(msgBody);
        return message;
    }

    public void testTakeMessage() throws Exception {
        final String connectionString = "bnmq://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        conFactory.setTransportMessageCoderFactory(new TransportMessageCoderFactory());
        
        ITransport server = conFactory.getServerTransport(new URI(connectionString));
        assertNotNull(server);
        server.addListener(new MessageListener(this));
        
        ITransport client = conFactory.getClientTransport(new URI(connectionString));
        client.addListener(new MessageListener(this));
        assertNotNull(client);
            
        client.send(createMessage("AAAaasasasasassas"));
        client.sendAsync(createMessage("Two"));
        Thread.sleep(500);
        conFactory.finalize();
        System.out.println("Finished: testTakeMessage");
    }
    
    public void testCall() throws Exception {
        final String connectionString = "bnmq://localhost:3333";
        TransportFactory conFactory = new TransportFactory();
        conFactory.setTransportMessageCoderFactory(new TransportMessageCoderFactory());
        
        ITransport server = conFactory.getServerTransport(new URI(connectionString));
        assertNotNull(server);
        server.addListener(new CallMessageListener(this));
        
        ITransport client = conFactory.getClientTransport(new URI(connectionString));
        assertNotNull(client);
        MessageEnvelope result = client.call(createMessage("Call"));
        System.out.println("Result call received with Id:"+result.getId()+" has been received successfully");
        conFactory.finalize();
        System.out.println("Finished: testCall");
    }    

    private class MessageListener implements ITransportListener {         
        private MessageDecoderThreadTest parent;
        private int counter = 0;
        public MessageListener(MessageDecoderThreadTest parent) {
            this.parent = parent;
        }
        public void onReceive(MessageEnvelope message, ITransport transport) {
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
        }
    }
    
    private class CallMessageListener implements ITransportListener {         
        private MessageDecoderThreadTest parent;
        private int counter = 0;
        public CallMessageListener(MessageDecoderThreadTest parent) {
            this.parent = parent;
        }
        public void onReceive(MessageEnvelope message, ITransport transport) {
            System.out.println("Call from "+transport+" with Id:"+message.getId()+" has been received successfully");
            try {
                MessageEnvelope result = createMessage("result");
                result.setId(message.getId());
                transport.sendAsync(result);
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }
    }    
}
