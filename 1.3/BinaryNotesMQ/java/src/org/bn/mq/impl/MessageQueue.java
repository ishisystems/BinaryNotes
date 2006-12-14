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

import org.bn.mq.IPersistenceQueueStorage;
import org.bn.mq.IRemoteConsumer;
import org.bn.mq.IMessage;
import org.bn.mq.IMessageQueue;
import org.bn.mq.IQueue;

public class MessageQueue<T> implements IMessageQueue<T> {
    public IQueue<T> getQueue() {
        return null;
    }

    public void setQueue(IQueue<T> queue) {
    }

    public void sendMessage(IMessage<T> message) {
    }

    public void setPersistenseStorage(IPersistenceQueueStorage<T> storage) {
    }

    public IPersistenceQueueStorage<T> getPersistenceStorage() {
        return null;
    }

    public void addConsumer(IRemoteConsumer<T> consumer) {
    }

    public void addConsumer(IRemoteConsumer<T> consumer, Boolean persistence) {
    }

    public void addConsumer(IRemoteConsumer<T> consumer, Boolean persistence, 
                            String filter) {
    }

    public void delConsumer(IRemoteConsumer<T> consumer) {
    }

    public String getQueuePath() {
        return null;
    }
}
