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
package org.bn.coders;

import org.bn.metadata.ASN1ElementMetadata;
import org.bn.metadata.ASN1Metadata;

import java.io.InputStream;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class ValueDecoders
{
    public static final IValueDecoder SEQUENCE_OF  = new SequenceOfDecoder();
    public static final IValueDecoder SEQUENCE     = new SequenceDecoder();
    public static final IValueDecoder CHOICE       = new ChoiceDecoder();
    public static final IValueDecoder BOXED_TYPE   = new BoxedTypeDecoder();
    public static final IValueDecoder ENUM         = new EnumDecoder();
    public static final IValueDecoder BOOLEAN      = new BooleanDecoder();
    public static final IValueDecoder ANY          = new AnyDecoder();
    public static final IValueDecoder INTEGER      = new IntegerDecoder();
    public static final IValueDecoder REAL         = new RealDecoder();
    public static final IValueDecoder OCTET_STRING = new OctetStringDecoder();
    public static final IValueDecoder BIT_STRING   = new BitStringDecoder();
    public static final IValueDecoder STRING       = new StringDecoder();
    public static final IValueDecoder NULL         = new NullDecoder();
    public static final IValueDecoder ELEMENT      = new ElementDecoder();
    public static final IValueDecoder JAVA_ELEMENT = new JavaElementDecoder();

    static class SequenceOfDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeSequenceOf(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class SequenceDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeSequence(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class ChoiceDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeChoice(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class  BoxedTypeDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeBoxedType(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class EnumDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeEnum(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class BooleanDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeBoolean(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class AnyDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeAny(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class IntegerDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeInteger(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class RealDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeReal(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class OctetStringDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeOctetString(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class BitStringDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeBitString(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class StringDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeString(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class NullDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeNull(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class ElementDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeElement(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }

    static class JavaElementDecoder
        implements IValueDecoder
    {
        public DecodedObject decode(FastDecoder         decoder,
                                    DecodedObject       decodedTag,
                                    Class               objectClass,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    InputStream         inputStream)
            throws Exception
        {
            return decoder.decodeJavaElement(decodedTag, objectClass, typeMetadata, elementMetadata, inputStream);
        }
    }
}
