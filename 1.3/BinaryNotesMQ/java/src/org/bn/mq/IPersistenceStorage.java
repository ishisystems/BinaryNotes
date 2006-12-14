package org.bn.mq;

public interface IPersistenceStorage {
    <T> IPersistenceQueueStorage createQueueStorage(IMessageQueue<T> messageQueue);
}
