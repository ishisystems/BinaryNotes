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

public interface IMessageQueue<T> extends IRemoteMessageQueue<T> {
    IQueue<T> getQueue();
    void setQueue(IQueue<T> queue);
    IMessage<T> createMessage(T body);
    IMessage<T> createMessage();
    
    void sendMessage(IMessage<T> message) throws Exception;    
    T call(T args, String consumerId) throws Exception;
    T call(T args, String consumerId, int timeout) throws Exception;
    void callAsync(T args, String consumerId, ICallAsyncListener<T> listener) throws Exception;
    void callAsync(T args, String consumerId, ICallAsyncListener<T> listener, int timeout) throws Exception;
    
    void setPersistenseStorage(IPersistenceQueueStorage<T> storage);
    IPersistenceQueueStorage<T> getPersistenceStorage();
}
