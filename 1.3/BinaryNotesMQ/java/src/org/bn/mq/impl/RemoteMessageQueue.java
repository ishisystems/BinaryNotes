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

import org.bn.mq.IRemoteConsumer;
import org.bn.mq.IMQConnection;
import org.bn.mq.IMessage;
import org.bn.mq.IRemoteMessageQueue;
import org.bn.mq.IRemoteSupplier;
import org.bn.mq.net.ITransport;
import org.bn.mq.protocol.LookupRequest;
import org.bn.mq.protocol.LookupResultCode;
import org.bn.mq.protocol.MessageBody;
import org.bn.mq.protocol.MessageEnvelope;
import org.bn.mq.protocol.SubscribeRequest;
import org.bn.mq.protocol.SubscribeResultCode;
import org.bn.mq.protocol.UnsubscribeRequest;
import org.bn.mq.protocol.UnsubscribeResultCode;

public class RemoteMessageQueue<T> implements IRemoteMessageQueue<T>{
    private RemoteSupplier supplier;
    private String queuePath;
    private final int subscribeTimeout = 60;
    
    public RemoteMessageQueue(String queuePath, RemoteSupplier supplier) {
        this.supplier = supplier;
        this.queuePath = queuePath;
    }

    public void addConsumer(IRemoteConsumer<T> consumer)  throws Exception  {
        addConsumer(consumer, false);
    }

    public void addConsumer(IRemoteConsumer<T> consumer, Boolean persistence)  throws Exception  {
        addConsumer(consumer, persistence, null);
    }

    public void addConsumer(IRemoteConsumer<T> consumer, Boolean persistence, String filter) throws Exception {
        SubscribeRequest request = new SubscribeRequest();
        request.setConsumerId(consumer.getId());
        request.setFilter(filter);
        if(persistence)
            request.setPersistence(true);
        request.setQueuePath(getQueuePath());
        
        MessageEnvelope message = new MessageEnvelope();
        MessageBody body = new MessageBody();
        body.selectSubscribeRequest(request);
        message.setBody(body);
        message.setId(this.toString());
        MessageEnvelope result = supplier.getConnection().call(message,subscribeTimeout);
        if (result.getBody().getSubscribeResult().getCode().getValue() == SubscribeResultCode.EnumType.success ) {
            throw new Exception("Error when accessing to queue '"+queuePath+"' for supplier '"+supplier.getId()+"': "+ result.getBody().getSubscribeResult().getCode().getValue().toString());
        }    
    }

    public void delConsumer(IRemoteConsumer<T> consumer) throws Exception {
        UnsubscribeRequest request = new UnsubscribeRequest();
        request.setConsumerId(consumer.getId());
        request.setQueuePath(getQueuePath());
        
        MessageEnvelope message = new MessageEnvelope();
        MessageBody body = new MessageBody();
        body.selectUnsubscribeRequest(request);
        message.setBody(body);
        message.setId(this.toString());
        MessageEnvelope result = supplier.getConnection().call(message,subscribeTimeout);
        if (result.getBody().getUnsubscribeResult().getCode().getValue() == UnsubscribeResultCode.EnumType.success ) {
            throw new Exception("Error when accessing to queue '"+queuePath+"' for supplier '"+supplier.getId()+"': "+ result.getBody().getUnsubscribeResult().getCode().getValue().toString());
        }        
    }

    public String getQueuePath() {
        return queuePath;
    }
}
