package org.bn.mq.impl;

import java.util.HashMap;
import java.util.Map;

import org.bn.mq.IMessageQueue;
import org.bn.mq.IPersistenceQueueStorage;
import org.bn.mq.IPersistenceStorage;
import org.bn.mq.MQFactory;

public class InMemoryStorage<T> implements IPersistenceStorage<T> {
    private Map<String, InMemoryQueueStorage<T> > storages = new HashMap<String, InMemoryQueueStorage<T> > ();
    
    public IPersistenceQueueStorage<T> createQueueStorage(String queueStorageName) {
        synchronized(storages) {
            InMemoryQueueStorage<T> result = storages.get(queueStorageName);
            if(result==null) {
                result = new InMemoryQueueStorage<T>(queueStorageName);
                storages.put(queueStorageName,result);
            }
            return result;
        }
    }
}
