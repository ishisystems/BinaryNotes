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

import java.io.OutputStream;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class ValueEncoders
{
    public static final IValueEncoder SEQUENCE_OF  = new SequenceOfEncoder();
    public static final IValueEncoder SEQUENCE     = new SequenceEncoder();
    public static final IValueEncoder CHOICE       = new ChoiceEncoder();
    public static final IValueEncoder BOXED_TYPE   = new BoxedTypeEncoder();
    public static final IValueEncoder ENUM         = new EnumEncoder();
    public static final IValueEncoder BOOLEAN      = new BooleanEncoder();
    public static final IValueEncoder ANY          = new AnyEncoder();
    public static final IValueEncoder INTEGER      = new IntegerEncoder();
    public static final IValueEncoder REAL         = new RealEncoder();
    public static final IValueEncoder OCTET_STRING = new OctetStringEncoder();
    public static final IValueEncoder BIT_STRING   = new BitStringEncoder();
    public static final IValueEncoder STRING       = new StringEncoder();
    public static final IValueEncoder NULL         = new NullEncoder();
    public static final IValueEncoder ELEMENT      = new ElementEncoder();
    public static final IValueEncoder JAVA_ELEMENT = new JavaElementEncoder();

    static class SequenceOfEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeSequenceOf(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class SequenceEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeSequence(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class ChoiceEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeChoice(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class  BoxedTypeEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeBoxedType(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class EnumEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeEnum(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class BooleanEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeBoolean(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class AnyEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeAny(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class IntegerEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeInteger(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class RealEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeReal(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class OctetStringEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeOctetString(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class BitStringEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeBitString(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class StringEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeString(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class NullEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeNull(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class ElementEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeElement(object, typeMetadata, elementMetadata, outputStream);
        }
    }

    static class JavaElementEncoder
        implements IValueEncoder
    {
        public int encode(FastEncoder         encoder,
                          Object              object,
                          ASN1Metadata        typeMetadata,
                          ASN1ElementMetadata elementMetadata,
                          OutputStream        outputStream)
            throws Exception
        {
            return encoder.encodeJavaElement(object, typeMetadata, elementMetadata, outputStream);
        }
    }
}
