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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.bn.metadata.ASN1Metadata;
import org.bn.metadata.ASN1ElementMetadata;
import org.bn.metadata.ASN1SequenceMetadata;
import org.bn.metadata.ASN1SequenceOfMetadata;
import org.bn.metadata.ASN1StringMetadata;
import org.bn.types.BitString;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class FastBERDecoder
    extends FastDecoder
{
    protected DecodedObject decodeSequence(DecodedObject       decodedTag,
                                           Class               objectClass,
                                           CodeableSequence    sequence,
                                           ASN1Metadata        typeMetadata,
                                           ASN1ElementMetadata elementMetadata,
                                           InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        ASN1SequenceMetadata sequenceMetadata = (ASN1SequenceMetadata) typeMetadata;
        DecodedObject<Integer> length = decodeLength(stream);

        if (sequenceMetadata.isSet())
        {
            result =
                decodeSet(decodedTag, objectClass, sequenceMetadata, elementMetadata, stream);
        }
        else if (checkTag(decodedTag,
                          elementMetadata,
                          TagClass.Universal,
                          ElementType.Constructed,
                          UniversalTag.Sequence))
        {
            result =
                super.decodeSequence(decodedTag, objectClass, sequence, sequenceMetadata, elementMetadata, stream);
        }

        if (result != null)
        {
            if(result.getSize() != length.getValue())
            {
                String message = 
                    "Sequence '" + objectClass.toString() + "' size is incorrect!";
                throw new IllegalArgumentException(message);
            }
            result.setSize(result.getSize() + length.getSize());
        }
        return result;
    }

    protected DecodedObject decodeSet(DecodedObject       decodedTag,
                                      Class               objectClass,
                                      ASN1Metadata        typeMetadata,
                                      ASN1ElementMetadata elementMetadata,
                                      InputStream         stream)
        throws Exception
    {
        // check tag...

        if (true) throw new Exception("Not yet implemented.");

        return null;
    }

    protected DecodedObject decodeEnumItem(DecodedObject       decodedTag,
                                           Class               objectClass,
                                           ASN1Metadata        typeMetadata,
                                           ASN1ElementMetadata elementMetadata,
                                           InputStream         stream)
        throws Exception
    {
        return checkTag(decodedTag, elementMetadata,
                        TagClass.Universal, ElementType.Primitive, UniversalTag.Enumerated) ?
            decodeIntegerValue(stream) : null;
    }

    protected DecodedObject decodeBoolean(DecodedObject       decodedTag,
                                          Class               objectClass,
                                          ASN1Metadata        typeMetadata,
                                          ASN1ElementMetadata elementMetadata,
                                          InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, UniversalTag.Boolean))
        {
            DecodedObject<Integer> intVal = decodeIntegerValue(stream);
            result = new DecodedObject(false, intVal.getSize());
            if(intVal.getValue() != 0)
            {
                result.setValue(true);
            }
        }
        return result;
    }

    protected DecodedObject decodeAny(DecodedObject       decodedTag,
                                      Class               objectClass,
                                      ASN1Metadata        typeMetadata,
                                      ASN1ElementMetadata elementMetadata,
                                      InputStream         stream)
        throws Exception
    {
        // check tag...
        if (true) throw new Exception("Not yet implemented.");

        return null;
    }

    protected DecodedObject decodeNull(DecodedObject       decodedTag,
                                       Class               objectClass,
                                       ASN1Metadata        typeMetadata,
                                       ASN1ElementMetadata elementMetadata,
                                       InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, UniversalTag.Null))
        {
            stream.read(); // ignore null length
            result = new DecodedObject(objectClass.newInstance(), 1);
        }
        return result;
    }

    protected DecodedObject decodeInteger(DecodedObject       decodedTag,
                                          Class               objectClass,
                                          ASN1Metadata        typeMetadata,
                                          ASN1ElementMetadata elementMetadata,
                                          InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, UniversalTag.Integer))
        {
            if(objectClass.equals(Integer.class))
            {
                result =  decodeIntegerValue(stream);
                // check constraints
            }
            else
            {
                result =  decodeLongValue(stream);
                // check constraints
            }        
        }
        return result;
    }

    protected DecodedObject decodeReal(DecodedObject       decodedTag,
                                       Class               objectClass,
                                       ASN1Metadata        typeMetadata,
                                       ASN1ElementMetadata elementMetadata,
                                       InputStream         stream)
        throws Exception
    {
        DecodedObject decodedReal = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, UniversalTag.Real))
        {
            DecodedObject<Integer> len = decodeLength(stream);
            int realPreamble = stream.read();
        
            Double result = 0.0D;
            int szResult = len.getValue();
            if( (realPreamble & 0x40) == 1)
            {
                // 01000000 Value is PLUS-INFINITY
                result = Double.POSITIVE_INFINITY;
            }
            if( (realPreamble & 0x41) == 1)
            {
                // 01000001 Value is MINUS-INFINITY
                result = Double.NEGATIVE_INFINITY;
                szResult+=1;
            }
            else if(len.getValue()>0)
            {
                int szOfExp = 1 + (realPreamble & 0x3);
                int sign = realPreamble & 0x40;
                int ff = (realPreamble & 0x0C) >> 2;
                DecodedObject<Long> exponentEncFrm =
                    decodeLongValue(stream, new DecodedObject<Integer>(szOfExp));
                long exponent = exponentEncFrm.getValue();
                DecodedObject<Long> mantissaEncFrm =
                    decodeLongValue(stream, new DecodedObject<Integer>(szResult - szOfExp - 1));
                // Unpack mantissa & decrement exponent for base 2
                long mantissa = mantissaEncFrm.getValue() << ff;
                while((mantissa & 0x000ff00000000000L) == 0x0)
                {
                    exponent-=8;
                    mantissa <<= 8;
                }
                while((mantissa & 0x0010000000000000L) == 0x0)
                {
                    exponent-=1;
                    mantissa <<= 1;
                }            
                mantissa &= 0x0FFFFFFFFFFFFFL;
                long lValue = (exponent+1023+52) << 52;
                lValue|= mantissa;
                if(sign == 1)
                {
                    lValue|=0x8000000000000000L;
                }
                result = Double.longBitsToDouble(lValue);
            }
            decodedReal = new DecodedObject(result, len.getValue() + len.getSize());
        }
        return decodedReal;
    }
    
    protected DecodedObject decodeChoice(DecodedObject       decodedTag,
                                         Class               objectClass,
                                         CodeableChoice      choice,
                                         ASN1Metadata        typeMetadata,
                                         ASN1ElementMetadata elementMetadata,
                                         InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;
        if ((elementMetadata != null) &&
            (checkTag(decodedTag, elementMetadata,
                      TagClass.ContextSpecific, ElementType.Constructed,UniversalTag.LastUniversal)))
        {
            DecodedObject<Integer> lengthOfChild = decodeLength(stream);
            DecodedObject childsTag = decodeTag(stream);
            result =
                super.decodeChoice(childsTag, objectClass, choice, typeMetadata, elementMetadata, stream);
            result.setSize(result.getSize() + childsTag.getSize() + lengthOfChild.getSize());
        }
        else
        {
            result =
                super.decodeChoice(decodedTag, objectClass, choice, typeMetadata, elementMetadata, stream);
        }
        return result;
    }

    protected DecodedObject decodeOctetString(DecodedObject       decodedTag,
                                              Class               objectClass,
                                              ASN1Metadata        typeMetadata,
                                              ASN1ElementMetadata elementMetadata,
                                              InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, UniversalTag.OctetString))
        {
            // check tag...
            DecodedObject<Integer> length = decodeLength(stream);
            // check constraints...
            byte[] buffer = new byte[length.getValue()];
            stream.read(buffer);

            result = new DecodedObject(buffer, length.getValue() + length.getSize());
        }
        return result;
    }

    protected DecodedObject decodeBitString(DecodedObject       decodedTag,
                                            Class               objectClass,
                                            ASN1Metadata        typeMetadata,
                                            ASN1ElementMetadata elementMetadata,
                                            InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, UniversalTag.Bitstring))
        {
            DecodedObject<Integer> length = decodeLength(stream);
            int trailBitCnt = stream.read();
            // check constraints...
            byte[] buffer = new byte[length.getValue() - 1];        
            stream.read(buffer);                

            result = new DecodedObject(new BitString(buffer, trailBitCnt),
                                       length.getValue() + length.getSize());
        }
        return result;
    }

    protected DecodedObject decodeString(DecodedObject       decodedTag,
                                         Class               objectClass,
                                         ASN1Metadata        typeMetadata,
                                         ASN1ElementMetadata elementMetadata,
                                         InputStream         stream)
        throws Exception
    {
        ASN1StringMetadata stringMetadata = (ASN1StringMetadata) typeMetadata;
        int stringTag = (stringMetadata != null ) ?
            stringMetadata.getStringType() : UniversalTag.PrintableString;

        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Primitive, stringTag))
        {
            DecodedObject<Integer> length = decodeLength(stream);
            // check constraints...
            byte[] buffer = new byte[length.getValue()];
            stream.read(buffer);

            String stringValue = (stringTag == UniversalTag.UTF8String) ?
                new String(buffer, "utf-8") : new String(buffer);

            result = new DecodedObject(stringValue, length.getValue() + length.getSize());
        }
        return result;
    }

    protected DecodedObject decodeSequenceOf(DecodedObject       decodedTag,
                                             Class               objectClass,
                                             ASN1Metadata        typeMetadata,
                                             ASN1ElementMetadata elementMetadata,
                                             InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        if (checkTag(decodedTag, elementMetadata,
                     TagClass.Universal, ElementType.Constructed, UniversalTag.Sequence))
        {
            Collection items = new ArrayList();
            DecodedObject<Integer> length = decodeLength(stream);
            if (length.getValue().intValue() > 0)
            {
                int lengthOfItems = 0;

                ASN1SequenceOfMetadata sequenceOfMetadata = (ASN1SequenceOfMetadata) typeMetadata;
                Class itemClass = sequenceOfMetadata.getItemClass();
                do
                {
                    DecodedObject itemTag = decodeTag(stream);
                    DecodedObject item =
                        decodeClassType(itemTag, itemClass, elementMetadata, stream);
                    if (item != null)
                    {
                        lengthOfItems += item.getSize() + itemTag.getSize();
                        items.add(item.getValue());
                    }
                }
                while (lengthOfItems < length.getValue());
                // !@# check constraints...
            }

            result = new DecodedObject(items, length.getValue() + length.getSize());
        }
        return result;
    }

    protected DecodedObject<Integer> decodeLength(InputStream stream)
        throws Exception
    {
        int result = 0 ;        
        int bt = stream.read() ;
        if(bt == -1)
            throw new IllegalArgumentException("Unexpected EOF when decoding!");
        
        int len =1 ;
        if (bt < 128 ) {
            result = bt ;
        }
        else {
            for (int i = bt - 128; i > 0 ; i--) {
                int fBt = stream.read() ;
                if(fBt == -1)
                    throw new IllegalArgumentException("Unexpected EOF when decoding!");
                result = result << 8 ;
                result = result | fBt ;
                len ++;
            }
        }
        return new DecodedObject<Integer> (result,len);
    }

    protected DecodedObject<Integer> decodeIntegerValue(InputStream stream)
        throws Exception
    {
        DecodedObject<Long> lVal = decodeLongValue(stream);
        DecodedObject<Integer> result = new DecodedObject<Integer>( (int)((long)lVal.getValue()), lVal.getSize() );
        return result;    
    }
    
    protected DecodedObject<Long> decodeLongValue(InputStream stream)
        throws Exception
    {
        DecodedObject<Integer> len =  decodeLength(stream);
        return decodeLongValue(stream,len);    
    }
    
    public DecodedObject<Long> decodeLongValue(InputStream            stream,
                                               DecodedObject<Integer> len)
        throws Exception
    {
        DecodedObject<Long> result = new DecodedObject<Long>();
        long value =0;
        for(int i=0;i<len.getValue();i++)
        {
            int bt = stream.read();
            if (bt == -1 )
            {
                throw new IllegalArgumentException("Unexpected EOF when decoding!");
            }
            
            if( i == 0 && (bt & (byte)0x80)!=0)
            {
                bt = bt - 256;
            }
            
            value = (value << 8) | bt ;
        }
        result.setValue(value);
        result.setSize(len.getValue() +  len.getSize());
        return result;    
    }

    protected DecodedObject decodeTag(InputStream stream)
        throws Exception
    {
        int result = 0 ;
        int bt = stream.read();
        if(bt == - 1)
            return null;
        result = bt ;
        int len = 1;
        if ((result & UniversalTag.LastUniversal) == UniversalTag.LastUniversal)
        {
            byte fBt = (byte) bt;
            while ((fBt & 128) != 0)
            {
                result = result << 7 ;
                result = result |  (fBt & 127);
                bt = stream.read();
                if(bt == -1)
                    break;
                fBt = (byte)bt;
                len ++;
            }            
        }
        return new DecodedObject(result, len);
    }

    private boolean checkTag(DecodedObject       decodedTag,
                             ASN1ElementMetadata elementMetadata,
                             int                 tagClass,
                             int                 elementType,
                             int                 universalTag)
    {
        int definedTag = tagValue(elementMetadata, tagClass, elementType, universalTag);

        return definedTag == ((Integer) decodedTag.getValue()).intValue();
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
