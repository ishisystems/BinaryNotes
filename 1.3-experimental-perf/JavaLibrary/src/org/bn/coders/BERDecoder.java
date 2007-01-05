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
import java.io.InputStream;

import java.lang.reflect.Array;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.lang.reflect.TypeVariable;

import java.util.Collection;
import java.util.LinkedList;

import org.bn.annotations.ASN1Any;
import org.bn.annotations.ASN1Element;
import org.bn.types.BitString;

public class BERDecoder extends Decoder {
    
    protected DecodedObject decodeSequence(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {
        boolean isSet = false;
        if(checkTagForObject(decodedTag,TagClass.Universal, ElementType.Constructed,UniversalTag.Sequence,elementInfo)) {
        }
        else
        if(checkTagForObject(decodedTag,TagClass.Universal, ElementType.Constructed,UniversalTag.Set,elementInfo)) {
            isSet = true;
        }
        else
            return null;

        DecodedObject<Integer> len = decodeLength(stream);
        DecodedObject result = null;
        if(!isSet)
            result =  super.decodeSequence(decodedTag,objectClass,elementInfo,stream);
        else
            result =  decodeSet(decodedTag,objectClass,elementInfo,len.getValue(),stream);
        if(result.getSize()!= len.getValue())
            throw new  IllegalArgumentException ("Sequence '" + objectClass.toString() + "' size is incorrect!");
        result.setSize(result.getSize() + len.getSize());
        return result;
    }
    
    protected DecodedObject decodeSet(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, Integer len,InputStream stream) throws Exception {
        Object set = createInstanceForElement(objectClass,elementInfo);
        initDefaultValues(set);

        DecodedObject fieldTag = decodeTag(stream);
        int sizeOfSet = 0;
        if(fieldTag!=null)
            sizeOfSet+=fieldTag.getSize();

        Field[] fields =objectClass.getDeclaredFields();

        boolean fieldEncoded = false; 
        do {
            
            for(int i=0; i<fields.length; i++) {        
                Field field = fields[i];
                DecodedObject obj = decodeSequenceField(fieldTag,set,field,stream,elementInfo, false);
                if(obj!=null) {
                    fieldEncoded = true;
                    sizeOfSet +=obj.getSize();                
                    if(i!=fields.length-1 && fields[i+1].isAnnotationPresent(ASN1Any.class)) {
                    }
                    else {
                        fieldTag = decodeTag(stream);
                        if(fieldTag!=null)
                            sizeOfSet += fieldTag.getSize();
                        else {
                            break;
                        }
                    }
                }
            }    
        }
        while(sizeOfSet < len && fieldEncoded);

        return new DecodedObject(set,sizeOfSet);
    }    
    
    
    protected DecodedObject decodeEnumItem(DecodedObject decodedTag, Class objectClass, Class enumClass, ElementInfo elementInfo, 
                                    InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.Enumerated, elementInfo))
            return null;
        return decodeIntegerValue(stream);
    }

    protected DecodedObject decodeBoolean(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                   InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.Boolean, elementInfo))
            return null;
        DecodedObject<Integer> intVal = decodeIntegerValue(stream);
        DecodedObject result = new DecodedObject (false , intVal.getSize());
        if(intVal.getValue()!=0)
            result.setValue(true);
        return result;
    }

    protected DecodedObject decodeAny(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                               InputStream stream) throws Exception {
        ByteArrayOutputStream anyStream = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len = 0;
        int readed = stream.read(buffer);
        while( readed > 0) {
            anyStream.write(buffer,0,readed);
            len+=readed;
            readed = stream.read(buffer);            
        }        
        CoderUtils.checkConstraints(len,elementInfo);
        return new DecodedObject(anyStream.toByteArray(),len);
    }

    protected DecodedObject decodeNull(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.Null, elementInfo))
            return null;
        stream.read ( ); // ignore null length
        DecodedObject result = new DecodedObject (objectClass.newInstance(),1);
        return result;
    }

    protected DecodedObject decodeInteger(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                   InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.Integer, elementInfo))
            return null;
        if(objectClass.equals(Integer.class)) {
            DecodedObject<Integer> result =  decodeIntegerValue(stream);
            CoderUtils.checkConstraints(result.getValue(),elementInfo);
            return result;
        }
        else {
            DecodedObject<Long> result =  decodeLongValue(stream);
            CoderUtils.checkConstraints(result.getValue(),elementInfo);
            return result;
        }        

    }

    protected DecodedObject decodeReal(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                   InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.Real, elementInfo))
            return null;
        DecodedObject<Integer> len = decodeLength(stream);
        int realPreamble = stream.read();
        
        Double result = 0.0D;
        int szResult = len.getValue();
        if( (realPreamble & 0x40) == 1) {
            // 01000000 Value is PLUS-INFINITY
            result = Double.POSITIVE_INFINITY;
        }
        if( (realPreamble & 0x41) == 1) {
            // 01000001 Value is MINUS-INFINITY
            result = Double.NEGATIVE_INFINITY;
            szResult+=1;
        }
        else 
        if(len.getValue()>0){
            int szOfExp = 1 + (realPreamble & 0x3);
            int sign = realPreamble & 0x40;
            int ff = (realPreamble & 0x0C) >> 2;
            DecodedObject<Long> exponentEncFrm = decodeLongValue(stream, new DecodedObject<Integer>(szOfExp));
            long exponent = exponentEncFrm.getValue();
            DecodedObject<Long> mantissaEncFrm = decodeLongValue(stream, new DecodedObject<Integer>(szResult - szOfExp - 1));
            // Unpack mantissa & decrement exponent for base 2
            long mantissa = mantissaEncFrm.getValue() << ff;
            while((mantissa & 0x000ff00000000000L) == 0x0) {
                exponent-=8;
                mantissa <<= 8;
            }
            while((mantissa & 0x0010000000000000L) == 0x0) {
                exponent-=1;
                mantissa <<= 1;
            }            
            mantissa &= 0x0FFFFFFFFFFFFFL;
            long lValue = (exponent+1023+52) << 52;
            lValue|= mantissa;
            if(sign == 1) {
                lValue|=0x8000000000000000L;
            }
            result = Double.longBitsToDouble(lValue);
        }
        return new DecodedObject(result,len.getValue()+len.getSize());
    }
    
    protected DecodedObject decodeChoice(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                   InputStream stream) throws Exception {        
        if(elementInfo.getElement()!=null) {            
            if(!checkTagForObject(decodedTag, TagClass.ContextSpecific, ElementType.Constructed,UniversalTag.LastUniversal, elementInfo))
                return null;
            DecodedObject<Integer> lenOfChild = decodeLength(stream);
            DecodedObject childDecodedTag = decodeTag(stream);
            DecodedObject<Object> result =  super.decodeChoice(childDecodedTag, objectClass, elementInfo, stream);
            result.setSize(result.getSize()+ childDecodedTag.getSize()+lenOfChild.getSize());
            return result;
        }
        else
            return super.decodeChoice(decodedTag, objectClass, elementInfo, stream);
    }

    protected DecodedObject<Integer> decodeIntegerValue(InputStream stream) throws Exception {
        DecodedObject<Long> lVal = decodeLongValue(stream);
        DecodedObject<Integer> result = new DecodedObject<Integer>( (int)((long)lVal.getValue()), lVal.getSize() );
        return result;    
    }
    
    protected DecodedObject<Long> decodeLongValue(InputStream stream) throws Exception {
        DecodedObject<Integer> len =  decodeLength(stream);
        return decodeLongValue(stream,len);    
    }
    
    public DecodedObject<Long> decodeLongValue(InputStream stream, DecodedObject<Integer> len) throws Exception {
        DecodedObject<Long> result = new DecodedObject<Long>();
        long value =0;
        for(int i=0;i<len.getValue();i++) {
            int bt = stream.read();
            if (bt == -1 ) {
                throw new IllegalArgumentException("Unexpected EOF when decoding!");
            }
            
            if( i == 0 && (bt & (byte)0x80)!=0) {
                bt = bt - 256;
            }
            
            value = (value << 8) | bt ;
        }
        result.setValue(value);
        result.setSize(len.getValue() +  len.getSize());
        return result;    
    }
    
    protected DecodedObject decodeOctetString(DecodedObject decodedTag, Class objectClass, 
                                       ElementInfo elementInfo, 
                                       InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.OctetString, elementInfo))
            return null;
        DecodedObject<Integer> len = decodeLength(stream);
        CoderUtils.checkConstraints(len.getValue(),elementInfo);
        byte[] byteBuf = new byte[ len.getValue()];
        stream.read(byteBuf);
        return new DecodedObject(byteBuf, len.getValue() + len.getSize());
    }

    protected DecodedObject decodeBitString(DecodedObject decodedTag, Class objectClass, 
                                       ElementInfo elementInfo, 
                                       InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,UniversalTag.Bitstring, elementInfo))
            return null;
        DecodedObject<Integer> len = decodeLength(stream);
        int trailBitCnt = stream.read();
        CoderUtils.checkConstraints(len.getValue()*8-trailBitCnt,elementInfo);
        byte[] byteBuf = new byte[ len.getValue()-1];        
        
        stream.read(byteBuf);                
        return new DecodedObject( new BitString( byteBuf, trailBitCnt) , len.getValue() + len.getSize());
    }

    protected DecodedObject decodeString(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                  InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,CoderUtils.getStringTagForElement(elementInfo), elementInfo))
            return null;
        DecodedObject<Integer> len = decodeLength(stream);
        CoderUtils.checkConstraints(len.getValue(),elementInfo);
        byte[] byteBuf = new byte[len.getValue()];
        stream.read(byteBuf);
        String result = null;
        
        if(CoderUtils.getStringTagForElement(elementInfo) == UniversalTag.UTF8String) {        
            result = new String(byteBuf, "utf-8");
        }
        else {
            result = new String(byteBuf);
        }
        return new DecodedObject(result, len.getValue() + len.getSize());
    }

    protected DecodedObject decodeSequenceOf(DecodedObject decodedTag, Class objectClass, 
                                      ElementInfo elementInfo, 
                                      InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Constructed,UniversalTag.Sequence, elementInfo))
            return null;                                      
        Collection result = new LinkedList();
        DecodedObject<Integer> len = decodeLength(stream);
        if(len.getValue()!=0) {
            int lenOfItems = 0;
            ParameterizedType tp = (ParameterizedType)elementInfo.getGenericInfo();
            Class paramType = (Class)tp.getActualTypeArguments()[0];
            elementInfo.setAnnotatedClass(paramType);
            elementInfo.setElement(null);
            do {                
                DecodedObject itemTag = decodeTag(stream);
                DecodedObject item=decodeClassType(itemTag,paramType,elementInfo,stream);
                if(item!=null) {
                    lenOfItems+=item.getSize()+itemTag.getSize();
                    result.add(item.getValue());
                }
            }
            while(lenOfItems < len.getValue());
            CoderUtils.checkConstraints(lenOfItems ,elementInfo);            
        }
        return new DecodedObject(result, len.getValue() + len.getSize());
    }    
    
    
    protected DecodedObject<Integer> decodeLength(InputStream stream) throws Exception {
        int result = 0 ;        
        int bt = stream.read() ;
        if(bt == -1)
            throw new IllegalArgumentException("Unexpected EOF when decoding!");
        
        int len =1 ;
        if (bt < 128 ) {
            result = bt ;
        }
        else {
            //for (int i = 256 - bt ; i > 0 ; i--) {
            // Decode length bug fix. Thanks to John 
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

    protected DecodedObject decodeTag(InputStream stream) throws Exception {
        int result = 0 ;
        int bt = stream.read();
        if(bt == - 1)
            return null;
        result = bt ;
        int len = 1;
        int tagClass = bt & 0xC0;
        int tagValue = bt & 31;
        boolean isPrimitive = (bt & 0x20) == 0;
        if (tagValue == UniversalTag.LastUniversal) 
        {
                tagValue = 0;
                do 
                {
                    bt = stream.read();
                    tagValue = (tagValue << 7) | (bt & 0x7f);
                    len++;
                } 
                while ((bt&0x80) != 0);
                tagValue= tagClass | tagValue;
                result = tagValue;
        }
        else
            result = bt;
        
        return new DecodedObject (result,len);
    }

    protected boolean checkTagForObject(DecodedObject decodedTag, int tagClass, int elementType, int universalTag,
                                     ElementInfo elementInfo) {
        int definedTag = BERCoderUtils.getTagValueForElement(elementInfo,tagClass,elementType,universalTag);
        return definedTag == (Integer)decodedTag.getValue();
    }
}
