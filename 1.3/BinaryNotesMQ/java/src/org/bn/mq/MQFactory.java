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

import java.util.HashMap;
import java.util.Map;

import org.bn.mq.impl.InMemoryStorage;
import org.bn.mq.impl.MessagingBus;
import org.bn.mq.impl.NullStorage;
import org.bn.mq.impl.PriorityQueue;
import org.bn.mq.impl.Queue;
import org.bn.mq.impl.SQLStorage;

public class MQFactory {
    private static MQFactory instance = new MQFactory();
        
    protected MQFactory() {
    }    
    
    public static MQFactory getInstance() {
        return instance;
    }
    
    public IMessagingBus createMessagingBus() {
        return new MessagingBus();
    }
    
    public <T> IQueue<T> createQueue(Class<T> messageClass) {
        return createQueue("simple",messageClass);
    }
    
    public <T> IQueue<T> createQueue(String algorithm, Class<T> messageClass) {
        if(algorithm == null || (algorithm!=null && algorithm.equalsIgnoreCase("simple"))) {
            return new Queue<T>();
        }
        else
        if(algorithm.equalsIgnoreCase("priority")) {
            return new PriorityQueue<T>();
        }
        else
            return null;
    }
    
    public <T> IPersistenceStorage<T> createPersistenceStorage(String storageType, Map<String,Object> storageProperties, Class<T> messageClass) throws Exception {
        if(storageType == null || (storageType!=null && storageType.length() == 0)) {
            return new NullStorage<T>(storageProperties);
        }
        else
        if(storageType.equalsIgnoreCase("InMemory")) {
            return new InMemoryStorage<T>(storageProperties);
        }
        else
        if(storageType.equalsIgnoreCase("SQL")) {
            return new SQLStorage<T>(storageProperties);
        }
        else
            return null;
    }
}
