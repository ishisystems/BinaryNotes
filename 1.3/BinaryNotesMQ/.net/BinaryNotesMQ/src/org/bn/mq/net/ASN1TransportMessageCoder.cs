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
using System;
using System.Collections.Generic;
using org.bn.mq.protocol;
using org.bn;

namespace org.bn.mq.net
{

    public class ASN1TransportMessageCoder : ITransportMessageCoder
    {
        protected internal IEncoder encoder;
        protected internal int coderSchemeDefVal = (short)0x0101;

        protected internal const byte coderVersion = 0x10;
        protected internal const int headerSize = 4 + 2 + 1; // length packet + coder schema + coderVersion;
        protected internal IDictionary<int, IDecoder> coderSchemaMap = new Dictionary<int, IDecoder>();
        protected internal System.IO.MemoryStream outputByteStream = new System.IO.MemoryStream();

        protected internal ByteBuffer currentDecoded = ByteBuffer.allocate(65535);
        protected internal bool headerIsReaded = false;
        protected internal int crDecodedSchema = 0;
        protected internal byte crDecodedVersion = 0;
        protected internal int crDecodedLen = 0;


        public ASN1TransportMessageCoder()
        {
            try
            {
                coderSchemaMap.Add(0x0000, CoderFactory.getInstance().newDecoder("BER"));
                coderSchemaMap.Add(0x0100, CoderFactory.getInstance().newDecoder("PER"));
                coderSchemaMap.Add(0x0101, CoderFactory.getInstance().newDecoder("PER/U"));
                IEncoder defEnc = CoderFactory.getInstance().newEncoder("PER/U");
                setDefaultEncoder(coderSchemeDefVal, defEnc);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        public virtual void setDefaultEncoder(int coderSchemaVal, IEncoder encoder)
        {
            this.coderSchemeDefVal = coderSchemaVal;
            this.encoder = encoder;
        }

        public virtual ByteBuffer encode(MessageEnvelope message)
        {
            outputByteStream.Position = 0; //reset();
            encoder.encode<MessageEnvelope>(message, outputByteStream);
            ByteBuffer buffer = ByteBuffer.allocate((int)outputByteStream.Length + headerSize);
            buffer.putShort((short)coderSchemeDefVal);
            buffer.put(coderVersion);
            buffer.putInt((int)outputByteStream.Length);
            buffer.put(outputByteStream.ToArray());
            buffer.Position = 0;
            return buffer;
        }

        public virtual MessageEnvelope decode(ByteBuffer buffer)
        {
            lock (this)
            {
                try
                {
                    MessageEnvelope result = null;
                    int readedBytes = buffer.Limit;
                    if (currentDecoded.Remaining < readedBytes)
                    {
                        byte[] data = currentDecoded.Value;
                        currentDecoded = ByteBuffer.allocate(data.Length + 65535);
                        currentDecoded.put(data);
                    }
                    currentDecoded.put(buffer.Value, 0, buffer.Limit);

                    // if header presents
                    if (currentDecoded.Position > headerSize && !headerIsReaded)
                    {
                        int savePos = currentDecoded.Position;
                        currentDecoded.Position = 0;
                        crDecodedSchema = currentDecoded.getShort();
                        crDecodedVersion = currentDecoded.get();
                        crDecodedLen = currentDecoded.getInt();
                        headerIsReaded = true;
                        currentDecoded.Position = savePos;
                    }

                    if (headerIsReaded)
                    {
                        IDecoder decoder = coderSchemaMap[crDecodedSchema];
                        if (crDecodedLen <= currentDecoded.Position - headerSize)
                        {
                            currentDecoded.Position = headerSize;
                            byte[] content = new byte[crDecodedLen];
                            currentDecoded.get(content);
                            currentDecoded = currentDecoded.slice();
                            result = decoder.decode<MessageEnvelope>(new System.IO.MemoryStream(content));
                            headerIsReaded = false;
                            currentDecoded.clear();
                        }
                    }
                    return result;
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Decode problem!: "+ex.ToString());
                    throw ex;
                }
            }
        }
    }
}