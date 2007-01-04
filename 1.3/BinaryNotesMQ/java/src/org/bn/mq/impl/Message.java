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

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;

import java.util.Date;

import org.bn.CoderFactory;
import org.bn.IDecoder;
import org.bn.IEncoder;
import org.bn.mq.IMessage;
import org.bn.mq.IRemoteSupplier;
import org.bn.mq.protocol.MessageBody;
import org.bn.mq.protocol.MessageEnvelope;
import org.bn.mq.protocol.MessageUserBody;

public class Message<T> implements IMessage<T> {
    
    private String id;
    private T body;
    private String senderId;
    private int priority;
    private boolean mandatoryFlag = false;
    private Date lifeTime;
    private String queuePath;
    private Class<T> messageClass;
    
    public Message(Class<T> messageClass) {
        this.messageClass = messageClass;
    }

    public Message(IMessage<T> src, Class<T> messageClass) {
        this(messageClass);
        setId(src.getId());
        setSenderId(src.getSenderId());
        setPrioriy(src.getPriority());
        setMandatory(src.isMandatory());
        setLifeTime(src.getLifeTime());
        setBody(src.getBody());
        setQueuePath(src.getQueuePath());
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
    public int getPriority() {
        return priority;
    }

    public void setPrioriy(int prio) {
        this.priority = prio;
    }

    public boolean isMandatory() {
        return mandatoryFlag;
    }

    public void setMandatory(boolean flag) {
        this.mandatoryFlag = flag;
    }

    public Date getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Date lifeTime) {
        this.lifeTime = lifeTime;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setQueuePath(String path) {
        this.queuePath = path;
    }
    
    public String getQueuePath() {
        return this.queuePath;
    }
    
    
    public void fillFromEnvelope(MessageEnvelope messageEnvelope) throws Exception {
        byte[] userBody = messageEnvelope.getBody().getMessageUserBody().getUserBody();
        setBody((T)decoder.decode(new ByteArrayInputStream(userBody),messageClass));
        setId(messageEnvelope.getId());
        setSenderId(messageEnvelope.getBody().getMessageUserBody().getSenderId());
    }

    public MessageEnvelope createEnvelope() throws Exception {
        MessageEnvelope result = new MessageEnvelope();
        MessageBody messageBody = new MessageBody();
        MessageUserBody userBody = new MessageUserBody();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        encoder.encode(getBody(),output);
        userBody.setUserBody(output.toByteArray());
        userBody.setSenderId(getSenderId());
        userBody.setQueuePath(getQueuePath());
        messageBody.selectMessageUserBody(userBody);        
        result.setBody(messageBody);
        result.setId(this.getId());        
        
        return result;
    }
    
    private static IDecoder decoder = null;
    private static IEncoder encoder = null;
    static {
        try {
            decoder = CoderFactory.getInstance().newDecoder("PER/U");
            encoder = CoderFactory.getInstance().newEncoder("PER/U");
        }
        catch (Exception e) { e = null; }
    }


}
