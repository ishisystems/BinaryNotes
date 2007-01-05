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

    public void removeDeliveredMessage(IConsumer<T> consumer, 
                                       IMessage<T> message) {
    }
}
