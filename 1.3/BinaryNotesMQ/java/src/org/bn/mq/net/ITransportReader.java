package org.bn.mq.net;

import org.bn.mq.protocol.MessageEnvelope;

public interface ITransportReader {
    boolean onReceive(MessageEnvelope message, ITransport transport);
}
