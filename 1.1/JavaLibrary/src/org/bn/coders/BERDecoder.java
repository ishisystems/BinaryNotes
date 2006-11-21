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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.lang.reflect.TypeVariable;

import java.util.Collection;
import java.util.LinkedList;

import org.bn.annotations.ASN1Element;

public class BERDecoder extends Decoder {
    
    protected DecodedObject decodeSequence(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag,TagClass.Universal, ElementType.Constructed,UniversalTag.Sequence,elementInfo))
            return null;
        else {
            DecodedObject<Integer> len = decodeLength(stream);
            DecodedObject result = super.decodeSequence(decodedTag,objectClass,elementInfo,stream);
            if(result.getSize()!= len.getValue())
                throw new  IllegalArgumentException ("Sequence '" + objectClass.toString() + "' size is incorrect!");
            result.setSize(result.getSize() + len.getSize());
            return result;
        }
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
        return decodeIntegerValue(stream);
    }
    
    protected DecodedObject<Integer> decodeIntegerValue(InputStream stream) throws Exception {
        DecodedObject<Integer> result = new DecodedObject();
        DecodedObject<Integer> len =  decodeLength(stream);
        int value = 0;
        for(int i=0;i<len.getValue();i++) {
            int bt = stream.read();
            if (bt == -1 ) {
                throw new IllegalArgumentException("Unexpected EOF when encoding!");
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
        byte[] byteBuf = new byte[ len.getValue()];
        stream.read(byteBuf);
        return new DecodedObject(byteBuf, len.getValue() + len.getSize());
    }

    protected DecodedObject decodeString(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, 
                                  InputStream stream) throws Exception {
        if(!checkTagForObject(decodedTag, TagClass.Universal, ElementType.Primitive,BERCoderUtils.getStringTagForElement(elementInfo), elementInfo))
            return null;
        DecodedObject<Integer> len = decodeLength(stream);
        byte[] byteBuf = new byte[len.getValue()];
        stream.read(byteBuf);
        String result = new String(byteBuf);
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
        if ((result & UniversalTag.LastUniversal) == UniversalTag.LastUniversal) {
            byte fBt = (byte) bt;
            while ((fBt & 128) != 0) {
              result = result << 7 ;
              result = result |  (fBt & 127);
              bt = stream.read();
              if(bt == -1)
                break;
              fBt = (byte)bt;
              len ++;
            }            
        }
        return new DecodedObject (result,len);
    }

    protected boolean checkTagForObject(DecodedObject decodedTag, int tagClass, int elementType, int universalTag,
                                     ElementInfo elementInfo) {
        int definedTag = BERCoderUtils.getTagValueForElement(elementInfo,tagClass,elementType,universalTag);
        return definedTag == (Integer)decodedTag.getValue();
    }
}
