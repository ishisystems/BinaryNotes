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

import org.bn.mq.IMQConnection;
import org.bn.mq.IRemoteMessageQueue;
import org.bn.mq.IRemoteSupplier;
import org.bn.mq.net.ITransport;

public class RemoteSupplier implements IRemoteSupplier {
    private ITransport connection;
    
    public RemoteSupplier(ITransport connection) {
        this.connection = connection;
    }
    
    public <T> IRemoteMessageQueue<T> lookupQueue(String queuePath, Class<T> messageBodyClass) {
        return null;
    }
    
    public ITransport getConnection() {
        return this.connection;
    }

    public String getId() {
        return null;
    }
}
