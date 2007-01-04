package org.bn.mq;

import org.bn.mq.impl.MessagingBus;

public class MQFactory {
    private static MQFactory instance = new MQFactory();
    
    public static MQFactory getInstance() {
        return instance;
    }
    
    public IMessagingBus createMessagingBus() {
        return new MessagingBus();
    }
}
