package org.bn.mq.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bn.mq.IConsumer;
import org.bn.mq.IMessage;
import org.bn.mq.IMessageQueue;
import org.bn.mq.IPersistenceQueueStorage;
import org.bn.mq.IRemoteConsumer;

public class InMemoryQueueStorage<T> implements IPersistenceQueueStorage<T> {
    private String queueStorageName;
    private Map < String, List < IMessage<T> > > storage = new HashMap < String, List < IMessage<T> > > ();
    private final List < IMessage<T> > nullList = new LinkedList < IMessage<T> >();
    
    public InMemoryQueueStorage(String queueStorageName) {
        this.queueStorageName = queueStorageName;        
    }
    
    public List< IMessage<T> > getMessagesToSend(IConsumer<T> consumer) {
        List< IMessage<T> > result;
        synchronized(storage) {
            result = storage.get(consumer.getId());
            if(result==null)
                result=nullList;
        }    
        return result;
    }
    
    public void removeDeliveredMessage(IConsumer<T> consumer, IMessage<T> message) {
        synchronized(storage) {
            List< IMessage<T> > messages = storage.get(consumer.getId());
            if(messages!=null) {
                Iterator<IMessage<T>> iterator =  messages.iterator();
                for(IMessage<T> item: messages) {
                    if(item.getId().equals(message.getId())) {
                        messages.remove(item);
                        break;
                    }
                }
            }
        }        
    }

    public void persistenceSubscribe(IConsumer<T> consumer) {
        synchronized(storage) {
            if(!storage.containsKey(consumer.getId())) {
                List < IMessage<T> > list = new LinkedList< IMessage<T> >();
                storage.put(consumer.getId(),list);
            }
        }
    }

    public void persistenceUnsubscribe(IConsumer<T> consumer) {
        synchronized(storage) {
            storage.remove(consumer.getId());
        }    
    }

    public void registerPersistenceMessage(IMessage<T> message) {
        synchronized(storage) {
            for( Map.Entry< String, List < IMessage<T> > > item : storage.entrySet() ) {
                item.getValue().add(message);
            }
        }    
    }
}
