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

import org.bn.mq.IConsumer;
import org.bn.mq.IMessage;
import org.bn.mq.IRemoteMessageQueue;

public class RemoteMessageQueue<T> implements IRemoteMessageQueue<T>{

    public void addConsumer(IConsumer<T> consumer) {
    }

    public void addConsumer(IConsumer<T> consumer, String filter) {
    }

    public void addPersistenceConsumer(IConsumer<T> consumer) {
    }

    public void addPersistenceConsumer(IConsumer<T> consumer, String filter) {
    }

    public void delConsumer(IConsumer<T> consumer) {
    }

    public IMessage<T> call(IMessage<T> message) {
        return null;
    }

    public String getQueuePath() {
        return null;
    }
}
