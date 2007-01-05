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

package test.org.bn.mq;

import java.net.URI;
import junit.framework.TestCase;
import org.bn.mq.IConsumer;
import org.bn.mq.IMQConnection;
import org.bn.mq.IMQConnectionListener;
import org.bn.mq.IMessage;
import org.bn.mq.IMessageQueue;
import org.bn.mq.IMessagingBus;
import org.bn.mq.IRemoteMessageQueue;
import org.bn.mq.IRemoteSupplier;
import org.bn.mq.ISupplier;
import org.bn.mq.MQFactory;
import org.bn.mq.net.ITransport;

public class MQFactoryTest extends TestCase {
    public MQFactoryTest(String sTestName) {
        super(sTestName);
    }

    /**
     * @see MQFactory#createMessagingBus()
     */
    public void testCreatingObjects() {
        IMessagingBus bus = MQFactory.getInstance().createMessagingBus();
        IMQConnection serverConnection  = null;
        IMQConnection clientConnection  = null;
        IMessageQueue<String> queue = null;
        try {
            serverConnection  = bus.create(new URI("bnmq://127.0.0.1:3333"));
            ISupplier supplier =  serverConnection.createSupplier("TestSupplier");
            queue = supplier.createQueue("myqueues/queue", String.class);
            serverConnection.addListener(new TestMQConnectionListener());
            Thread.sleep(200);
            
            clientConnection  = bus.connect(new URI("bnmq://127.0.0.1:3333"));
            clientConnection.addListener(new TestMQConnectionListener());
            IRemoteSupplier remSupplier =  clientConnection.lookup("TestSupplier");
            IRemoteMessageQueue remQueue = remSupplier.lookupQueue("myqueues/queue", String.class);            
            remQueue.addConsumer(new TestConsumer());
            for(int i=0;i<100;i++) {                            
                IMessage<String> message = queue.createMessage("Hello"+i);
                queue.sendMessage(message);
            }
            Thread.sleep(100);
            
            try {
                IRemoteMessageQueue unknownRemQueue = remSupplier.lookupQueue("myqueues/queue1", String.class);
                unknownRemQueue.addConsumer(new TestConsumer());
                assertTrue(false);
            }
            catch(Exception ex) {
                assertTrue(ex.toString().contains("unknownQueue"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(queue!=null) {
                queue.stop();
            }
            if(clientConnection!=null)
                clientConnection.close();        
            if(serverConnection!=null)
                serverConnection.close();
            if(bus!=null) {
                try {
                    bus.finalize();
                }
                catch (Throwable e) {e = null; }
            }
        }
    }
    
    protected class TestMQConnectionListener implements IMQConnectionListener {

        public void onDisconnected(IMQConnection connection, 
                                   ITransport networkTransport) {
            System.out.println("onDisconnected: "+connection.toString()+"/"+networkTransport.toString());
        }

        public void onConnected(IMQConnection connection, 
                                ITransport networkTransport) {
            System.out.println("onConnected: "+connection.toString()+"/"+networkTransport.toString());
        }
    }

    protected class TestConsumer implements IConsumer<String> {
        
        public String getId() {
            return this.toString();
        }

        public void onMessage(IMessage<String> message) {
            System.out.println("Received message: "+message.getBody());
        }
    }
}
