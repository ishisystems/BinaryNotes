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

package org.bn.mq.net;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import org.bn.mq.protocol.MessageEnvelope;

public interface ITransport {
    void send(ByteBuffer buffer) throws IOException;
    void sendAsync(ByteBuffer buffer) throws IOException;
    
    void send(byte[] buffer) throws IOException;
    void sendAsync(byte[] buffer) throws IOException;
    
    void send(MessageEnvelope message) throws Exception;
    void sendAsync(MessageEnvelope message) throws Exception;
    MessageEnvelope call(MessageEnvelope message) throws Exception;
    MessageEnvelope call(MessageEnvelope message, int timeout) throws Exception;
    
    URI getAddr();
    
    void addListener(ITransportListener listener);
    void delListener(ITransportListener listener);    
    
    boolean isAvailable();
    void close();
}
