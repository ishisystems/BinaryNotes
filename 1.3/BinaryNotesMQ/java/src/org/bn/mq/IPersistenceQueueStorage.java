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
package org.bn.mq;

import java.util.List;

import org.bn.mq.impl.InMemoryQueueStorage;

public interface IPersistenceQueueStorage<T> {
    List< IMessage <T> > getMessagesToSend(IConsumer<T> consumer);
    void persistenceSubscribe(IConsumer<T> consumer);
    void persistenceUnsubscribe(IConsumer<T> consumer);
    void registerPersistenceMessage(IMessage<T> message);
    void removeDeliveredMessage(IConsumer<T> consumer, IMessage<T> message);
}
