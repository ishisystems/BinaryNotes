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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.nio.ByteBuffer;

import org.bn.CoderFactory;
import org.bn.IDecoder;
import org.bn.IEncoder;
import org.bn.mq.net.ITransportMessageCoder;
import org.bn.mq.protocol.MessageEnvelope;

public class ASN1TransportMessageCoder implements ITransportMessageCoder {
    protected IEncoder<MessageEnvelope> encoder;
    protected IDecoder decoder;    
    protected final String coderScheme = "PER/U";
    protected final byte coderVersion = 0x10;
    protected final int headerSize = 4 + coderScheme.length() + 1;
    protected ByteArrayOutputStream outputByteStream = new ByteArrayOutputStream();
    
    protected ByteBuffer currentDecoded = ByteBuffer.allocate(65535);
    protected boolean headerIsReaded = false;
    protected byte[] crDecodedSchema = new byte[coderScheme.length()];
    protected byte crDecodedVersion = 0;
    protected int crDecodedLen = 0;
    
    
    public ASN1TransportMessageCoder() {
        try {
            encoder = CoderFactory.getInstance().newEncoder(coderScheme );
            decoder = CoderFactory.getInstance().newDecoder(coderScheme );
        }
        catch (Exception e) {
            // TODO
        }
        
    }

    public ByteBuffer encode(MessageEnvelope message) throws Exception {
        outputByteStream.reset();
        encoder.encode(message, outputByteStream);
        ByteBuffer buffer = ByteBuffer.allocate(outputByteStream.size()+headerSize);        
        buffer.put(coderScheme.getBytes("US-ASCII"));
        buffer.put(coderVersion);
        buffer.putInt(outputByteStream.size());
        buffer.put(outputByteStream.toByteArray());
        buffer.position(0);
        return buffer;
    }

    public MessageEnvelope decode(ByteBuffer buffer)  throws Exception {
        MessageEnvelope result = null;
        int readedBytes = buffer.limit();
        if(currentDecoded.remaining()<readedBytes) {
            byte[] data = currentDecoded.array();
            currentDecoded = ByteBuffer.allocate(data.length+65535);
            currentDecoded.put(data);
        }
        currentDecoded.put(buffer.array(),currentDecoded.position(),buffer.limit());
        // if header presents
        if(currentDecoded.position() > headerSize && !headerIsReaded) {
            int savePos = currentDecoded.position();
            currentDecoded.position(0);
            currentDecoded.get(crDecodedSchema);
            crDecodedVersion = currentDecoded.get();
            crDecodedLen = currentDecoded.getInt();
            headerIsReaded = true;
            currentDecoded.position(savePos);
        }
        
        if(headerIsReaded) {
            if(crDecodedLen <= currentDecoded.position() - headerSize ) {
                //int savePos = currentDecoded.position();
                currentDecoded.position(headerSize);
                byte[] content = new byte[crDecodedLen];
                currentDecoded.get(content);
                currentDecoded = currentDecoded.slice();
                result= decoder.decode(new ByteArrayInputStream(content),MessageEnvelope.class);       
                headerIsReaded = false;
            }
        }
        return result;
    }
}
