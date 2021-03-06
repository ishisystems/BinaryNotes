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

import java.lang.reflect.Field;

import java.util.SortedMap;
import java.util.TreeMap;

import org.bn.annotations.ASN1Element;
import org.bn.annotations.ASN1String;
import org.bn.annotations.constraints.ASN1SizeConstraint;
import org.bn.annotations.constraints.ASN1ValueRangeConstraint;
import org.bn.types.*;

public class CoderUtils {
    public static int getIntegerLength(int value) {
        long mask = 0x7f800000L;
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

    public static int getIntegerLength(long value) {
        long mask = 0x7f80000000000000L;
        int sizeOfInt = 8;
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

    public static int getPositiveIntegerLength(int value) {
        if (value < 0) {
            long mask = 0x7f800000L;
            int sizeOfInt = 4;        
            while (((mask & ~value) == mask) && (sizeOfInt > 1)) {
              mask = mask >> 8 ;
              sizeOfInt-- ;
            }
            return sizeOfInt;
        }
        else
            return getIntegerLength(value);
    }
    
    public static int getPositiveIntegerLength(long value) {
        if (value < 0) {
            long mask = 0x7f80000000000000L;
            int sizeOfInt = 8;        
            while (((mask & ~value) == mask) && (sizeOfInt > 1)) {
              mask = mask >> 8 ;
              sizeOfInt-- ;
            }
            return sizeOfInt;
        }
        else
            return getIntegerLength(value);
    }
    
    public static BitString defStringToOctetString(String bhString) {
        if(bhString.length() < 4)
            return new BitString(new byte[0]);
        if(bhString.lastIndexOf('B')==bhString.length()-1)
            return bitStringToOctetString(bhString.substring(1,bhString.length()-2));
        else
            return hexStringToOctetString(bhString.substring(1,bhString.length()-2));
    }

    private static BitString bitStringToOctetString(String bhString) {        
        boolean hasTrailBits = bhString.length()%2!=0;
        int trailBits = 0;
        byte[] resultBuf = new byte[bhString.length()/8 + (hasTrailBits?1:0)];
        int currentStrPos = 0;
        for(int i=0;i<resultBuf.length;i++) {
            byte bt = 0x00;
            int bitCnt = currentStrPos;
            while(bitCnt<currentStrPos+8 && bitCnt< bhString.length()) {
                if(bhString.charAt(bitCnt)!='0')
                    bt |=  ( 0x01 << (7- (bitCnt-currentStrPos)));
                bitCnt++;
            }
            currentStrPos+=8;            
            if(bitCnt!=currentStrPos)
                trailBits = 8 - (currentStrPos - bitCnt);
            // hi byte
            resultBuf[i] = bt;
        }
        BitString result = new BitString (resultBuf,trailBits);        
        return result;
    }

    private static BitString hexStringToOctetString(String bhString) {
           boolean hasTrailBits = bhString.length()%2!=0;
           BitString result = new BitString (new byte[bhString.length()/2 + (hasTrailBits ? 1:0)], hasTrailBits? 4:0);
           final byte hex[] = {0, 1, 2,3, 4, 5, 6, 7, 8, 9, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA, 0xB, 0xC, 0xD, 0xE,0xF};
           
           for(int i=0;i<result.getLength();i++) {
               // high byte
               result.getValue()[i] = (byte)(hex[((int)(bhString.charAt(i*2)) - 0x30)] << 4);
               if(!hasTrailBits || (hasTrailBits && i<result.getLength()-1))                
                result.getValue()[i] |= (byte)(hex[((int)(bhString.charAt(i*2+1)) - 0x30)] & 0x0F);
           }
           return result;
    }    
    
    public static SortedMap<Integer,Field> getSetOrder(Object object){
        SortedMap<Integer, Field> fieldOrder = new TreeMap<Integer,Field>();
        final int tagNA = -1;
        for ( Field field : object.getClass().getDeclaredFields() ) {
            ASN1Element element = field.getAnnotation(ASN1Element.class);
            if(element!=null) {
                if(element.hasTag())
                    fieldOrder.put(element.tag(),field);
                else
                    fieldOrder.put(tagNA,field);
            }
        }
        return fieldOrder;
    }
    
    public static  int getStringTagForElement(ElementInfo elementInfo) {
        int result = UniversalTag.PrintableString;
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1String.class)) {
            ASN1String value = elementInfo.getAnnotatedClass().getAnnotation(ASN1String.class);
            result = value.stringType();
        }
        else 
        if(elementInfo.getParentAnnotated()!=null && elementInfo.getParentAnnotated().isAnnotationPresent(ASN1String.class)) {
            ASN1String value = elementInfo.getParentAnnotated().getAnnotation(ASN1String.class);
            result = value.stringType();
        }
        
        return result;
    }
    
    public static void checkConstraints(long value, ElementInfo elementInfo) throws Exception {
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1ValueRangeConstraint.class)) {
            ASN1ValueRangeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1ValueRangeConstraint.class);
            if(value> constraint.max() || value<constraint.min() )
                throw new Exception("Length of '"+elementInfo.getAnnotatedClass().toString()+"' out of bound");
        }
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1SizeConstraint.class)) {
            ASN1SizeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1SizeConstraint.class);
            if(value!= constraint.max())
                throw new Exception("Length of '"+elementInfo.getAnnotatedClass().toString()+"' out of bound");
        }        
    }
    
}
