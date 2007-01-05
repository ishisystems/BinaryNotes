package org.bn.mq.impl;

import org.bn.mq.IMessageQueue;
import org.bn.mq.IPersistenceQueueStorage;
import org.bn.mq.IPersistenceStorage;

public class NullStorage<T> implements IPersistenceStorage<T> {
    private NullQueueStorage<T> nullQueueStorage = new NullQueueStorage<T>();
    public IPersistenceQueueStorage<T> createQueueStorage(String queueStorageName) {
        return nullQueueStorage;
    }
}
