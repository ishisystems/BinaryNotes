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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.util.ArrayList;

import org.bn.annotations.ASN1Element;
import org.bn.annotations.ASN1EnumItem;
import org.bn.annotations.ASN1String;
import org.bn.utils.ReverseByteArrayOutputStream;

public class BEREncoder<T> extends Encoder<T> {
    
    public BEREncoder() {
    }
    
    public void encode(T object, OutputStream stream) throws Exception {
        ReverseByteArrayOutputStream reverseStream = new ReverseByteArrayOutputStream();        
        super.encode(object, reverseStream);
        reverseStream.writeTo(stream);        
    }            
    
    protected int encodeSequence(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        //result += super.encodeSequence ( object, stream, elementInfo);
         for ( int i = 0;i<object.getClass().getDeclaredFields().length; i++) {
             Field field  = object.getClass().getDeclaredFields()[ object.getClass().getDeclaredFields().length - 1 - i];
             resultSize+= encodeSequenceField(object,field,stream,elementInfo);
         }        
        resultSize += encodeHeader ( BERCoderUtils.getTagValueForElement (elementInfo, TagClass.Universal, ElementType.Constructed, UniversalTag.Sequence), resultSize, stream );
        return resultSize;
    }

    protected int encodeChoice(Object object, OutputStream stream, ElementInfo elementInfo)  throws Exception {
        int result = 0;
        int sizeOfChoiceField =  super.encodeChoice ( object, stream , elementInfo);        
        result +=sizeOfChoiceField;
        return result;
    }    
        
    protected int encodeEnumItem(Object enumConstant, Class enumClass, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        ASN1EnumItem enumObj = elementInfo.getAnnotatedClass().getAnnotation(ASN1EnumItem.class);
        int szOfInt = encodeIntegerValue(enumObj.tag(),stream);
        resultSize += szOfInt;
        resultSize += encodeLength(szOfInt, stream);
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Primitive, UniversalTag.Enumerated),
            stream
        );
        return resultSize;
    }

    protected int encodeBoolean(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 1;
        stream.write( (Boolean)object ? 0xFF:0x00 );

        resultSize += encodeLength(1, stream);
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Primitive, UniversalTag.Boolean),
            stream
        );
        return resultSize;
    }

    protected int encodeAny(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0, sizeOfString = 0;
        byte[] buffer = (byte[])object;
        stream.write( buffer );
        sizeOfString = buffer.length;
        resultSize += sizeOfString;
        return resultSize;
    }
    
    protected int encodeIntegerValue(int value, OutputStream stream) throws Exception {
        int resultSize = CoderUtils.getIntegerLength(value);
        for (int i = 0 ; i < resultSize ; i++) {
            stream.write((byte)value);
            value =  value >> 8 ;
        }
        return resultSize;
    }

    protected int encodeInteger(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        Integer value = (Integer) object;
        int szOfInt = encodeIntegerValue(value,stream);
        resultSize += szOfInt;
        resultSize += encodeLength(szOfInt, stream);
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Primitive, UniversalTag.Integer),
            stream
        );
        return resultSize;
    }
    /*        
    protected int getIntegerLength(int value) {
    
        int mask = 0x7f800000 ;
        int sizeOfInt = 4;
        if (value < 0) {
            while (((mask & value) == mask) && (sizeOfInt > 1)) {
              mask = mask >> 8 ;
              sizeOfInt-- ;
            }
        }
        else {
          while (((mask & value) == 0) && (sizeOfInt > 1)) {
            mask = mask >> 8 ;
            sizeOfInt -- ;
          }
        }
        return sizeOfInt;
    }
     */            

    protected int encodeOctetString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0, sizeOfString = 0;
        byte[] buffer = (byte[])object;
        stream.write( buffer );
        sizeOfString = buffer.length;

        resultSize += sizeOfString;
        resultSize += encodeLength(sizeOfString, stream);
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Primitive, UniversalTag.OctetString),
            stream
        );
        return resultSize;
    }

    protected int encodeString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0, sizeOfString = 0;
        stream.write( object.toString().getBytes() );
        sizeOfString = object.toString().length();

        resultSize += sizeOfString;
        resultSize += encodeLength(sizeOfString, stream);
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Primitive, BERCoderUtils.getStringTagForElement(elementInfo)),
            stream
        );
        return resultSize;
    }

    protected int encodeSequenceOf(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {    
        int resultSize = 0;
        Object[] collection = ((java.util.Collection<Object>)object).toArray();
        int sizeOfCollection = 0;
        for(int i=0;i<collection.length;i++) {
            Object obj = collection[collection.length - 1 - i];
            ElementInfo info = new ElementInfo(obj.getClass());
            info.setParentAnnotated(elementInfo.getAnnotatedClass());
            sizeOfCollection+=encodeClassType(obj,stream,info);
        }
        resultSize += sizeOfCollection;
        resultSize += encodeLength(sizeOfCollection, stream);
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Constructed, UniversalTag.Sequence),
            stream
        );        
        return resultSize;
    }

    protected int encodeHeader(int tagValue, int contentLen, OutputStream stream) throws IOException {
        int resultSize = encodeLength(contentLen, stream);
        resultSize += encodeTag(tagValue, stream);
        return resultSize;
    }
    
    protected int encodeTag(int tagValue, OutputStream stream) throws IOException {
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

    protected int encodeLength(int length, OutputStream stream) throws IOException {
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
    
    protected int encodeNull(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception  {
        stream.write( 0 );
        int resultSize = 1;
        resultSize += encodeTag( 
            BERCoderUtils.getTagValueForElement(elementInfo,TagClass.Universal, ElementType.Primitive, UniversalTag.Null),
            stream
        );
        return resultSize;
    }
}
