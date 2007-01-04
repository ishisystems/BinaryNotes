package org.bn.mq;

public interface IConsumer<T>  {
    String getId();
    void onMessage(IMessage<T> message);
}
