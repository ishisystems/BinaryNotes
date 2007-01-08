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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collection;
import java.util.Iterator;

import org.bn.metadata.ASN1ElementMetadata;
import org.bn.metadata.ASN1Metadata;
import org.bn.metadata.ASN1SequenceMetadata;
import org.bn.metadata.ASN1StringMetadata;
import org.bn.metadata.ChoiceDescriptor;
import org.bn.metadata.FieldDescriptor;
import org.bn.metadata.SequenceDescriptor;
import org.bn.types.BitString;
import org.bn.utils.ReverseByteArrayOutputStream;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class FastBEREncoder<T>
    extends FastEncoder<T>
{
    public FastBEREncoder()
    {
    }
    
    public void encode(T            object,
                       OutputStream stream)
        throws Exception
    {
        ReverseByteArrayOutputStream reverseStream = new ReverseByteArrayOutputStream();        
        super.encode(object, reverseStream);
        reverseStream.writeTo(stream);        
    }            
    
    protected int encodeSequence(Object              object,
                                 ASN1Metadata        typeMetadata,
                                 ASN1ElementMetadata elementMetadata,
                                 OutputStream        stream)
        throws Exception
    {
        int resultSize = 0;

        CodeableSequence sequence = (CodeableSequence) object;
        SequenceDescriptor sequenceDescriptor = sequence.getSequenceDescriptor();
        FieldDescriptor[] fieldDescriptors = sequenceDescriptor.getFieldDescriptors();
        for (int index = fieldDescriptors.length - 1; index >= 0; index--)
        {
            IValueEncoder encoder = fieldDescriptors[index].getEncoder();

            // !@# need to handle optional fields...

            resultSize +=
                encoder.encode(this,
                               fieldDescriptors[index].getField(object),
                               fieldDescriptors[index].getTypeMetadata(),
                               fieldDescriptors[index].getElementMetadata(),
                               stream);
        }

        ASN1SequenceMetadata sequenceMetadata = sequenceDescriptor.getSequenceMetadata();

        int tagValue =
            tagValue(elementMetadata,
                     TagClass.Universal,
                     ElementType.Constructed,
                     sequenceMetadata.isSet() ? UniversalTag.Set : UniversalTag.Sequence);

        resultSize += encodeHeader(tagValue, resultSize, stream);

        return resultSize;
    }
        
    protected int encodeChoice(Object              object,
                               ASN1Metadata        typeMetadata,
                               ASN1ElementMetadata elementMetadata,
                               OutputStream        stream)
        throws Exception
    {
        int resultSize = 0;

        CodeableChoice choice = (CodeableChoice) object;
        ChoiceDescriptor choiceDescriptor = choice.getChoiceDescriptor();
        FieldDescriptor[] fieldDescriptors = choiceDescriptor.getFieldDescriptors();

        FieldDescriptor fieldToEncode = null;
        Object fieldValue = null;
        int index = 0;
        while ((fieldToEncode == null) && (index < fieldDescriptors.length))
        {
            // !@# Linear search -- blech!  I'm not crazy about just
            // looking for the first non-null field, but to be
            // smarter about things, we'll have to modify the
            // BNCompiler to generate a method telling us which field
            // is selected.
            fieldValue = fieldDescriptors[index].getField(object);
            if (fieldValue != null)
            {
                fieldToEncode = fieldDescriptors[index];
            }
            else
            {
                index++;
            }
        }

        IValueEncoder encoder = fieldToEncode.getEncoder();
        resultSize +=
            encoder.encode(this, fieldValue,
                           fieldToEncode.getTypeMetadata(),
                           fieldToEncode.getElementMetadata(),
                           stream);

        int tagValue =
            tagValue(elementMetadata,
                     TagClass.ContextSpecific,
                     ElementType.Constructed,
                     UniversalTag.LastUniversal);

        resultSize += encodeHeader(tagValue, resultSize, stream);

        return resultSize;
    }    
        
    protected int encodeBoolean(Object              object,
                                ASN1Metadata        typeMetadata,
                                ASN1ElementMetadata elementMetadata,
                                OutputStream        stream)
        throws Exception
    {
        int resultSize = 1;
        stream.write((Boolean) object ? 0xFF : 0x00);

        resultSize += encodeLength(1, stream);

        int tagValue =
            tagValue(elementMetadata,
                     TagClass.Universal,
                     ElementType.Primitive,
                     UniversalTag.Boolean);

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }

    protected int encodeAny(Object              object,
                            ASN1Metadata        typeMetadata,
                            ASN1ElementMetadata elementMetadata,
                            OutputStream        stream)
        throws Exception
    {
        int resultSize = 0, sizeOfString = 0;
        byte[] buffer = (byte[]) object;
        stream.write(buffer);
        sizeOfString = buffer.length;
        // CoderUtils.checkConstraints(sizeOfString,elementInfo);
        resultSize += sizeOfString;

        return resultSize;
    }
    
    protected int encodeIntegerValue(long         value,
                                     OutputStream stream)
        throws Exception
    {
        int resultSize = CoderUtils.getIntegerLength(value);
        for (int i = 0 ; i < resultSize ; i++)
        {
            stream.write((byte) value);
            value = value >> 8;
        }
        return resultSize;
    }

    protected int encodeInteger(Object              object,
                                ASN1Metadata        typeMetadata,
                                ASN1ElementMetadata elementMetadata,
                                OutputStream        stream)
        throws Exception
    {
        int resultSize = 0;
        int szOfInt = 0;
        if(object instanceof Integer)
        {
            Integer value = (Integer) object;
            // CoderUtils.checkConstraints(value,elementInfo);
            szOfInt = encodeIntegerValue(value,stream);
        }
        else if(object instanceof Long)
        {
            Long value = (Long) object;
            // CoderUtils.checkConstraints(value,elementInfo);
            szOfInt = encodeIntegerValue(value,stream);            
        }
        resultSize += szOfInt;        
        resultSize += encodeLength(szOfInt, stream);

        int tagValue =
            tagValue(elementMetadata,
                     TagClass.Universal,
                     ElementType.Primitive,
                     UniversalTag.Integer);

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }
    
    protected int encodeReal(Object              object,
                             ASN1Metadata        typeMetadata,
                             ASN1ElementMetadata elementMetadata,
                             OutputStream        stream)
        throws Exception
    {
        int resultSize = 0;
        Double value = (Double) object;
        //CoderUtils.checkConstraints(value,elementInfo);
        int szOfInt = 0;
        long asLong = Double.doubleToLongBits(value);
        if(asLong == 0x7ff0000000000000L) { // positive infinity
            stream.write(0x40); // 01000000 Value is PLUS-INFINITY
        }
        else
        if(asLong == 0xfff0000000000000L) { // negative infinity            
            stream.write(0x41); // 01000001 Value is MINUS-INFINITY
        }        
        else 
        if(asLong!=0x0) {
            long exponent = ((0x7ff0000000000000L & asLong) >> 52) - 1023 - 52;
            long mantissa = 0x000fffffffffffffL & asLong;
            mantissa |= 0x10000000000000L; // set virtual delimeter
            
            // pack mantissa for base 2
            while((mantissa & 0xFFL) == 0x0) {
                mantissa >>= 8;
                exponent += 8; //increment exponent to 8 (base 2)
            }        
            while((mantissa & 0x01L) == 0x0) {
                mantissa >>= 1;
                exponent+=1; //increment exponent to 1
            }
             
             szOfInt+= encodeIntegerValue(mantissa,stream);
             int szOfExp = CoderUtils.getIntegerLength(exponent);
             szOfInt+= encodeIntegerValue(exponent,stream);
             
             int realPreamble = 0x80;
             realPreamble |= (byte)(szOfExp - 1);
             if( (asLong & 0x8000000000000000L) == 1) {
                 realPreamble|= 0x40; // Sign
             }
             stream.write(realPreamble );
             szOfInt+=1;
        }
        resultSize += szOfInt;
        resultSize += encodeLength(szOfInt, stream);

        int tagValue =
            tagValue(elementMetadata,
                     TagClass.Universal,
                     ElementType.Primitive,
                     UniversalTag.Real);

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }

    protected int encodeOctetString(Object              object,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    OutputStream        stream)
        throws Exception
    {
        int resultSize = 0, sizeOfString = 0;
        byte[] buffer = (byte[]) object;
        stream.write( buffer );
        sizeOfString = buffer.length;

        resultSize += sizeOfString;
        // CoderUtils.checkConstraints(sizeOfString,elementInfo);
        resultSize += encodeLength(sizeOfString, stream);

        int tagValue =
            tagValue(elementMetadata,
                     TagClass.Universal,
                     ElementType.Primitive,
                     UniversalTag.OctetString);

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }

    protected int encodeBitString(Object              object,
                                  ASN1Metadata        typeMetadata,
                                  ASN1ElementMetadata elementMetadata,
                                  OutputStream        stream)
        throws Exception
    {
        int resultSize = 0, sizeOfString = 0;
        BitString str = (BitString) object;
        // CoderUtils.checkConstraints(str.getLength()*8-str.getTrailBitsCnt() , elementInfo);
        
        byte[] buffer = str.getValue();
        stream.write(buffer);
        stream.write(str.getTrailBitsCnt());
        sizeOfString = buffer.length + 1;

        resultSize += sizeOfString;
        resultSize += encodeLength(sizeOfString, stream);

        int tagValue =
            tagValue(elementMetadata, 
                     TagClass.Universal,
                     ElementType.Primitive,
                     UniversalTag.Bitstring);

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }

    protected int encodeString(Object              object,
                               ASN1Metadata        typeMetadata,
                               ASN1ElementMetadata elementMetadata,
                               OutputStream        stream)
        throws Exception
    {
        int resultSize = 0, sizeOfString = 0;
        byte[] strBuf = null;

        int stringType = ((typeMetadata != null) && (typeMetadata instanceof ASN1StringMetadata)) ?
            ((ASN1StringMetadata) typeMetadata).getStringType() : UniversalTag.PrintableString;

        if (stringType == UniversalTag.UTF8String)
        {
            strBuf = object.toString().getBytes("utf-8");
        }
        else
        {
            strBuf = object.toString().getBytes();
        }
        stream.write(strBuf);
        sizeOfString = strBuf.length;

        resultSize += sizeOfString;
        // CoderUtils.checkConstraints(sizeOfString,elementInfo);
        resultSize += encodeLength(sizeOfString, stream);

        int tagValue =
            tagValue(elementMetadata, TagClass.Universal, ElementType.Primitive, stringType);

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }

    protected int encodeSequenceOf(Object              object,
                                   ASN1Metadata        typeMetadata,
                                   ASN1ElementMetadata elementMetadata,
                                   OutputStream        stream)
        throws Exception
    {
        int resultSize = 0;

        Collection<Object> sequence = (Collection<Object>) object;
        int sizeOfCollection = 0;
        Iterator iterator = sequence.iterator();
        while (iterator.hasNext())
        {
            Object member = iterator.next();
            // !@# constraints
            sizeOfCollection += encodeClassType(member, elementMetadata, stream); // !@# elementMetadata?
        }

        resultSize += sizeOfCollection;
        //         CoderUtils.checkConstraints(collection.length,elementInfo);
        resultSize += encodeLength(sizeOfCollection, stream);
        
        int tagValue =
            tagValue(elementMetadata, 
                     TagClass.Universal,
                     ElementType.Constructed,
                     UniversalTag.Sequence); // !@# need to handle sets too!!!

        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }

    protected int encodeHeader(int          tagValue,
                               int          contentLen,
                               OutputStream stream)
        throws IOException
    {
        int resultSize = encodeLength(contentLen, stream);
        resultSize += encodeTag(tagValue, stream);
        return resultSize;
    }
    
    protected int encodeTag(int          tagValue,
                            OutputStream stream)
        throws IOException
    {
        int resultSize = 0;
        if (tagValue < 256) {
            stream.write((byte)tagValue);
            resultSize++;
        }
        else {
            while (tagValue != 0) {
                stream.write((byte)(tagValue & 127));
                tagValue = tagValue << 7 ;
                resultSize++;
            }
        }        
        return resultSize;
    }

    protected int encodeLength(int          length,
                               OutputStream stream)
        throws IOException
    {
        int resultSize = 0;
        
        if (length < 0) {
            throw new IllegalArgumentException() ;
        }
        else 
        if (length < 128) {
            stream.write(length);
            resultSize++;
        }
        else 
        if (length < 256) {
            stream.write(length);
            stream.write((byte)0x81);
            resultSize+=2;
        }
        else 
        if (length < 65536) {
            stream.write((byte)(length)) ;
            stream.write((byte)(length >> 8));
            stream.write((byte)0x82);
            resultSize+=3;
        }
        else 
        if (length < 16777126) {
            stream.write((byte)(length));
            stream.write((byte)(length >> 8)) ;
            stream.write((byte)(length >> 16)) ;
            stream.write((byte)0x83);
            resultSize+=4;
        }
        else {
            stream.write((byte)(length));
            stream.write((byte)(length >> 8));
            stream.write((byte)(length >> 16));
            stream.write((byte)(length >> 24));
            stream.write((byte)0x84);
            resultSize+=5;
        } 
        return resultSize;
    }
    
    protected int encodeNull(Object              object,
                             ASN1Metadata        typeMetadata,
                             ASN1ElementMetadata elementMetadata,
                             OutputStream        stream)
        throws Exception 
    {
        stream.write(0);
        int resultSize = 1;

        int tagValue =
            tagValue(elementMetadata, 
                     TagClass.Universal,
                     ElementType.Primitive,
                     UniversalTag.Null);
        resultSize += encodeTag(tagValue, stream);

        return resultSize;
    }
    
    private int tagValue(ASN1ElementMetadata elementMetadata,
                         int                 tagClass,
                         int                 elementType,
                         int                 universalTag)
    {
        return ((elementMetadata != null) && elementMetadata.hasTag()) ?
            elementMetadata.getTagClass() | elementType | elementMetadata.getTag() :
            tagClass | elementType | universalTag;
    }
}
