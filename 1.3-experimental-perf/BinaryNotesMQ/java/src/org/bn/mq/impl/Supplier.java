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

package org.bn.mq.impl;

import java.util.HashMap;
import java.util.Map;

import org.bn.mq.IMessageQueue;
import org.bn.mq.IRemoteMessageQueue;
import org.bn.mq.ISupplier;
import org.bn.mq.net.ITransport;
import org.bn.mq.net.ITransportListener;
import org.bn.mq.protocol.MessageBody;
import org.bn.mq.protocol.MessageEnvelope;
import org.bn.mq.protocol.SubscribeResult;
import org.bn.mq.protocol.SubscribeResultCode;
import org.bn.mq.protocol.UnsubscribeResult;
import org.bn.mq.protocol.UnsubscribeResultCode;

public class Supplier implements ISupplier, ITransportListener {
    private ITransport transport;
    private String supplierId;
    private Map<String,MessageQueue> queues = new HashMap<String,MessageQueue>();
    
    public Supplier(String supplierId, ITransport transport) {
        this.transport = transport;
        this.supplierId = supplierId;
        this.transport.setUnhandledMessagesListener(this);
    }

    public <T> IRemoteMessageQueue<T> lookupQueue(String queuePath, Class<T> messageClass) {
        IRemoteMessageQueue<T> queue = null;
        synchronized(queues) {
            queue = (IRemoteMessageQueue<T>)queues.get(queuePath);
        }
        return queue;
    }

    public <T> IMessageQueue<T> createQueue(String queuePath, Class<T> messageClass) {
        MessageQueue<T> queue = new MessageQueue<T>(queuePath,transport, messageClass);
        synchronized(queues) {
            queues.put(queuePath,queue);
        }
        return queue;
    }

    public <T> void removeQueue(IMessageQueue<T> queue) {
        synchronized(queues) {
            queues.remove(queue.getQueuePath());
        }    
    }

    public String getId() {
        return supplierId;
    }

    public boolean onReceive(MessageEnvelope message, ITransport transport) {
        if(message.getBody().isSubscribeRequestSelected()) {
            MessageEnvelope resultMsg = new MessageEnvelope();
            MessageBody body = new MessageBody();
            SubscribeResult subscribeResult = new SubscribeResult();
            SubscribeResultCode subscribeResultCode = new SubscribeResultCode();
            body.selectSubscribeResult(subscribeResult);
            resultMsg.setBody(body);
            resultMsg.setId(message.getId());
            subscribeResultCode.setValue(SubscribeResultCode.EnumType.unknownQueue);
            subscribeResult.setCode(subscribeResultCode);
            try {
                transport.sendAsync(resultMsg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        if(message.getBody().isUnsubscribeRequestSelected()) {
            MessageEnvelope resultMsg = new MessageEnvelope();
            MessageBody body = new MessageBody();
            UnsubscribeResult unsubscribeResult = new UnsubscribeResult();
            UnsubscribeResultCode unsubscribeResultCode = new UnsubscribeResultCode();
            body.selectUnsubscribeResult(unsubscribeResult);
            resultMsg.setBody(body);
            resultMsg.setId(message.getId());
            unsubscribeResultCode.setValue(UnsubscribeResultCode.EnumType.unknownQueue);
            unsubscribeResult.setCode(unsubscribeResultCode);
            try {
                transport.sendAsync(resultMsg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }

    public void onConnected(ITransport transport) {}

    public void onDisconnected(ITransport transport) {}
}
