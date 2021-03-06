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

import java.util.LinkedList;
import java.util.List;

import org.bn.mq.IConsumer;
import org.bn.mq.IMessage;
import org.bn.mq.IPersistenceQueueStorage;
import org.bn.mq.IRemoteConsumer;

public class NullQueueStorage<T> implements IPersistenceQueueStorage<T> {
    private List< IMessage<T> > nullMessagesToSend = new LinkedList < IMessage<T> >();

    public List<IMessage<T>> getMessagesToSend(IConsumer<T> consumer) {
        return nullMessagesToSend;
    }

    public void persistenceSubscribe(IConsumer<T> consumer) {
    }

    public void persistenceUnsubscribe(IConsumer<T> consumer) {
    }

    public void registerPersistenceMessage(IMessage<T> message) {
    }

    public void removeDeliveredMessage(String consumerId, String messageId) {
    }

    public void close() {
    }
}
