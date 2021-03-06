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

import java.io.OutputStream;

import org.bn.annotations.ASN1String;
import org.bn.annotations.constraints.ASN1ValueRangeConstraint;
import org.bn.utils.BitArrayOutputStream;

public class PERUnalignedEncoder<T> extends PERAlignedEncoder<T> {
    public PERUnalignedEncoder() {
    }
    
    protected int encodeConstraintNumber(int value, int min, int max, BitArrayOutputStream stream) throws Exception {
        int result = 0;
        int valueRange = max - min;
        int narrowedVal = value - min;
        int maxBitLen = PERCoderUtils.getMaxBitLength(valueRange);

        if(valueRange == 0) {
            return result;      
        }
        
        //For the UNALIGNED variant the value is always encoded in the minimum 
        // number of bits necessary to represent the range (defined in 10.5.3). 
        int currentBit = maxBitLen;
        while(currentBit > 8) {
            currentBit-=8;
            result++;
            stream.write ( (int) (narrowedVal >>> currentBit));
            
        }
        if(currentBit > 0) {
            for (int i=currentBit-1; i>=0; i--) {
                int bitValue = (narrowedVal >> i) & 0x1;
                stream.writeBit( bitValue );
            }
            result +=1;
        }
        return result;
    }
    
    protected int encodeString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        byte[] value = object.toString().getBytes();
                
        resultSize = encodeStringLength(elementInfo, value, stream);
        
        if(value.length == 0)
            return resultSize;
            
        boolean is7Bit = false;
        ASN1String strValueAnnotation = null;
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1String.class)) {
            strValueAnnotation = elementInfo.getAnnotatedClass().getAnnotation(ASN1String.class);            
        }
        else
        if(elementInfo.getParentAnnotated()!=null && elementInfo.getParentAnnotated().isAnnotationPresent(ASN1String.class)) {
            strValueAnnotation = elementInfo.getParentAnnotated().getAnnotation(ASN1String.class);
        }                
        if(strValueAnnotation!=null) {
            is7Bit = 
                ( 
                    strValueAnnotation.stringType() == UniversalTag.PrintableString || 
                    strValueAnnotation.stringType() == UniversalTag.VisibleString
                )
                ;
        }        
        if(!is7Bit)
            super.encodeString(object, stream, elementInfo);
        else {
            BitArrayOutputStream bitStream = (BitArrayOutputStream)stream;
            // 7-bit encoding of string
            int currentBit = 1;
            for(int i=0;i<value.length;i++) {
                int bt =  ( value[i] << currentBit) & 0xFF;
                resultSize++;                 
                if(i<value.length-1) {
                    bt = bt | ( (value[i+1] >> 7-currentBit));
                }
                else {
                   // Need to fill the last bits (no more bytes is available)
                   for(int j=0; j<8-currentBit;j++) {
                       bitStream.writeBit(  bt & (0x80 >> j) );
                   }
                   break;
                }
                bitStream.write ( bt );
                if(currentBit == 7) {
                   currentBit = 1;
                }
                else
                   currentBit++;
            }            
        }
        return resultSize;
    }    
    
    protected void doAlign(OutputStream stream) {
        // Do nothing! Unaligned encoding ;)
    }
    
}
