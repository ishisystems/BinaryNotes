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

import java.io.IOException;
import java.io.OutputStream;

import java.lang.reflect.Field;

import java.util.Collection;

import org.bn.annotations.ASN1Element;
import org.bn.annotations.ASN1EnumItem;
import org.bn.annotations.ASN1String;
import org.bn.annotations.constraints.ASN1ValueRangeConstraint;
import org.bn.utils.BitArrayOutputStream;

public class PERAlignedEncoder<T> extends Encoder<T> {

    public PERAlignedEncoder() {
    }
     
    public void encode(T object, OutputStream stream) throws Exception {
        BitArrayOutputStream bitStream = new BitArrayOutputStream();
        super.encode(object, bitStream);
        bitStream.writeTo(stream);
    }    
    
    protected int encodeIntegerValueAsBytes(int value, OutputStream stream) throws Exception {
        int integerSize = CoderUtils.getIntegerLength(value);
        for (int i = integerSize-1 ; i >= 0  ; i--) {
            int valueTmp =  value >> (8 * i);
            stream.write((byte)valueTmp);
        }
        return integerSize;
    }

    /**
     * Encoding constraint length determinant procedure.
     * ITU-T X.691. 10.9. General rules for encoding a length determinant
     * 
     */
    protected int encodeConstraintLengthDeterminant(int length, int min, int max, BitArrayOutputStream stream) throws Exception {
        if( max <= 0xFFFF) {
            // 10.9. NOTE 2 – (Tutorial) In the case of the ALIGNED variant 
            // if the length count is bounded above by an upper bound that is 
            // less than 64K, then the constrained whole number encoding 
            // is used for the length.
            return encodeConstraintNumber(length, min, max, stream); // encoding as constraint integer
        }
        else
            return encodeLengthDeterminant(length, stream);
    }

    /**
     * Encoding length determinant procedure.
     * ITU-T X.691. 10.9. General rules for encoding a length determinant
     */    
    protected int encodeLengthDeterminant(int length, BitArrayOutputStream stream) throws IOException {
        int result = 0;
        doAlign(stream);
        if( length >= 0 && length < 0x80) { // NOTE 2. a) ("n" less than 128)             
            stream.write(length); // a single octet containing "n" with bit 8 set to zero;
            result = 1;
        }
        else
        if ( length < 0x4000 ) {
            // NOTE 2. b) ("n" less than 16K) two octets 
            // containing "n" with bit 8 of the first octet 
            // set to 1 and bit 7 set to zero;
            stream.write ( length >>> 8 & 0x3f | 0x80 );
            stream.write ( length );
            result = 2;
        }
        else {
            // NOTE 2. b) (large "n") a single octet containing a count "m" 
            // with bit 8 set to 1 and bit 7 set to 1. 
            // The count "m" is one to four, and the length indicates that 
            // a fragment of the material follows (a multiple "m" of 16K items). 
            // For all values of "m", the fragment is then followed 
            // by another length encoding for the remainder of the material.        
            throw new InternalError ("Not supported for this version. Length too big!");
        }
        return result;
    }
            
    /**
     * Encoding of a constrained whole number
     * ITU-T X.691. 10.5. 
     * NOTE – (Tutorial) This subclause is referenced by other clauses, 
     * and itself references earlier clauses for the production of 
     * a nonnegative-binary-integer or a 2's-complement-binary-integer encoding.
     */
    protected int encodeConstraintNumber(int value, int min, int max, BitArrayOutputStream stream) throws Exception {
        int result = 0;
        int valueRange = max - min;
        int narrowedVal = value - min;
        int maxBitLen = PERCoderUtils.getMaxBitLength(valueRange);
        
        if(valueRange == 0) {
            return result;      
        }
        
        // The rest of this Note addresses the ALIGNED variant. 
        if(valueRange>0 && valueRange <256) {
            /*
            * 1. Where the range is less than or equal to 255, the value encodes 
            * into a bit-field of the minimum size for the range. 
            * 2. Where the range is exactly 256, the value encodes 
            * into a single octet octet-aligned bit-field. 
            */            
            doAlign(stream);
            for(int i=maxBitLen-1;i>=0;i--) {
                int bitValue = (narrowedVal >> i) & 0x1;
                stream.writeBit(bitValue);
            }
            result = 1;
        }
        else
        if (valueRange>0 && valueRange < 65536) {
            /* 
            * 3. Where the range is 257 to 64K, the value encodes into 
            * a two octet octet-aligned bit-field. 
            */
            doAlign(stream);
            stream.write((narrowedVal >>> 8));
            stream.write(narrowedVal & 0xFF);
            result = 2;
        }
        else {
            /*
            * 4. Where the range is greater than 64K, the range is ignored 
            * and the value encodes into an  octet-aligned bit-field 
            * which is the minimum number of octets for the value. 
            * In this latter case, later procedures (see 10.9)
            * also encode a length field (usually a single octet) to indicate 
            * the length of the encoding. For the other cases, the length 
            * of the encoding is independent of the value being encoded, 
            * and is not explicitly encoded.
             */
            result = encodeConstraintLengthDeterminant(
                CoderUtils.getIntegerLength(narrowedVal), 
                1, CoderUtils.getPositiveIntegerLength(valueRange), 
                stream
            );
            doAlign(stream);
            result += encodeIntegerValueAsBytes(narrowedVal,stream);
        }
        return result;        
    }
    
    /** 
     * Encoding of a semi-constrained whole number
     * ITU-T X.691. 10.7. 
     * NOTE – (Tutorial) This procedure is used when a lower bound can be 
     * identified but not an upper bound. The encoding procedure places 
     * the offset from the lower bound into the minimum number of octets 
     * as a non-negative-binary-integer, and requires an explicit length 
     * encoding (typically a single octet) as specified in later procedures.
     */
    protected int encodeSemiConstraintNumber(int value, int min, BitArrayOutputStream stream) throws Exception {
        int result =0;
        int narrowedVal = value - min;
        int intLen = CoderUtils.getIntegerLength(narrowedVal);
        result += encodeLengthDeterminant(intLen, stream);
        doAlign(stream);
        result += encodeIntegerValueAsBytes(narrowedVal,stream);
        return result;
    }
    
    /**
     * Encode normally small number
     * ITU-T X.691. 10.6
     * NOTE – (Tutorial) This procedure is used when encoding 
     * a non-negative whole number that is expected to be small, but whose size 
     * is potentially unlimited due to the presence of an extension marker. 
     * An example is a choice index.
     */
     protected int encodeNormallySmallNumber(int value, BitArrayOutputStream stream) throws Exception {
        int result = 0;
         if (value > 0 && value < 64) {
             /* 10.6.1 If the non-negative whole number, "n", is less than 
              * or equal to 63, then a single-bit bit-field shall be appended
              * to the field-list with the bit set to 0, and "n" shall be 
              * encoded as a non-negative-binary-integer into a 6-bit bit-field.
              */
             stream.writeBit(0);
             for(int i=0;i<6;i++) {
                 int bitValue = (value >> 6-i) & 0x1;
                 stream.writeBit( bitValue ); 
             }
             result = 1;
         }
         else {
             /* If "n" is greater than or equal to 64, a single-bit 
              * bit-field with the bit set to 1 shall be appended to the field-list.
              * The value "n" shall then be encoded as a semi-constrained 
              * whole number with "lb" equal to 0 and the procedures of 
              * 10.9 shall be invoked to add it to the field-list preceded 
              * by a length determinant.
              */
             stream.writeBit(1);
             result += encodeSemiConstraintNumber (value,0,stream);
         }
         return result;
     }

    /**
     * Encoding of a unconstrained whole number
     * ITU-T X.691. 10.8. 
     * NOTE – (Tutorial) This case only arises in the encoding of the 
     * value of an integer type with no lower bound. The procedure
     * encodes the value as a 2's-complement-binary-integer into 
     * the minimum number of octets required to accommodate the encoding,
     * and requires an explicit length encoding (typically a single octet) 
     * as specified in later procedures.
     */
    protected int encodeUnconstraintNumber(int value, BitArrayOutputStream stream) throws Exception {
        int result = 0;
        int intLen = CoderUtils.getIntegerLength(value);
        result += encodeLengthDeterminant(intLen, stream);
        doAlign(stream);
        result += encodeIntegerValueAsBytes(value,stream);
        return result;
    }
    
    protected int encodeInteger(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int result = 0;
        Integer value = (Integer) object;
        BitArrayOutputStream bitStream = (BitArrayOutputStream)stream;
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1ValueRangeConstraint.class)) {
            ASN1ValueRangeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1ValueRangeConstraint.class);
            result += encodeConstraintNumber(value, (int)constraint.min(), (int)constraint.max(), bitStream);
        }
        else
            result += encodeUnconstraintNumber(value, bitStream);        
        return result;
    }
    
    
    protected int encodeSequencePreamble(Object object, OutputStream stream) throws Exception {
        int resultBitSize = 0;
        for ( Field field : object.getClass().getDeclaredFields()) {
            if(isOptionalField(field)) {
                Object invokeObjResult = invokeGetterMethodForField(field,object);
                ((BitArrayOutputStream)stream).writeBit( invokeObjResult !=null);
                resultBitSize+=1;
            }
        }
        doAlign(stream);
        return (resultBitSize / 8) + (resultBitSize % 8 > 0 ? 1:0);
    }
    
    protected int encodeSequence(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {        
        int resultSize = 0;
        resultSize += encodeSequencePreamble(object, stream);
        resultSize += super.encodeSequence(object,stream,elementInfo);
        return resultSize;
    }    
    
    protected int encodeChoicePreamble(Object object, OutputStream stream, int elementIndex) throws Exception {
        return encodeConstraintNumber(elementIndex,1,object.getClass().getDeclaredFields().length, (BitArrayOutputStream)stream);
    }

    /**
     * Encoding of the choice structure
     * ITU-T X.691. 22. 
     * NOTE – (Tutorial) A choice type is encoded by encoding an index specifying 
     * the chosen alternative. This is encoded as for a constrained integer 
     * (unless the extension marker is present in the choice type, 
     * in which case it is a normally small non-negative whole number) 
     * and would therefore typically occupy a fixed length bit-field of the 
     * minimum number of bits needed to encode the index. (Although it could 
     * in principle be arbitrarily large.) This is followed by the encoding 
     * of the chosen alternative, with alternatives that are extension 
     * additions encoded as if they were the value of an open type field. 
     * Where the choice has only one alternative, there is no encoding 
     * for the index.
     */
    protected int encodeChoice(Object object, OutputStream stream, ElementInfo elementInfo)  throws Exception {
        int resultSize = 0;
        doAlign(stream);
        ElementInfo info = null;
        int elementIndex = 0;
        for ( Field field : object.getClass().getDeclaredFields() ) {
            elementIndex++;
            if(isSelectedChoiceItem(field,object)) {
                //selectedField = field;
                //Object invokeObjResult = invokeGetterMethodForField(field,object);
                info = new ElementInfo(field, field.getAnnotation(ASN1Element.class));
                break;
            }            
        }
        if(info==null) {
            throw new  IllegalArgumentException ("The choice '" + object.toString() + "' does not have a selected item!");
        }        
        Object invokeObjResult = invokeGetterMethodForField((Field)info.getAnnotatedClass(),object);
        resultSize+=encodeChoicePreamble(object,stream,elementIndex);
        resultSize+=encodeClassType(invokeObjResult, stream, info);
        return resultSize;
    }    
        
    protected int encodeEnumItem(Object enumConstant, Class enumClass, OutputStream stream, ElementInfo elementInfo) throws Exception {
        ASN1EnumItem enumObj = elementInfo.getAnnotatedClass().getAnnotation(ASN1EnumItem.class);
        int min = 0, max = enumClass.getDeclaredFields().length, value =0;
        for(Field enumItem: enumClass.getDeclaredFields()) { 
            if(enumItem.isAnnotationPresent(ASN1EnumItem.class)) {
                ASN1EnumItem enumItemObj = enumItem.getAnnotation(ASN1EnumItem.class);        
                if(enumItemObj.tag() == enumObj.tag())
                    break;
                value++;
            }
        }
         return encodeConstraintNumber(value,min,max,(BitArrayOutputStream)stream);
    }

    protected int encodeBoolean(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 1;
        //doAlign(stream);
        BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
        bitStream.writeBit((Boolean)object);
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
    
    protected int encodeOctetString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0, sizeOfString = 0;
        byte[] buffer = (byte[])object;
        sizeOfString = buffer.length;
        
        BitArrayOutputStream bitStream = (BitArrayOutputStream)stream;        
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1ValueRangeConstraint.class)) {
            ASN1ValueRangeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1ValueRangeConstraint.class);
            resultSize += encodeConstraintLengthDeterminant(sizeOfString, (int)constraint.min(), (int)constraint.max(), bitStream);
        }
        else
            resultSize += encodeLengthDeterminant(sizeOfString, bitStream);
        if(sizeOfString >0)
            stream.write( buffer );
        return resultSize;
    }

    protected int encodeString(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        byte[] value = object.toString().getBytes();
                
        resultSize = encodeStringLength(elementInfo, value, stream);
        resultSize+=value.length;
        if(value.length > 0)
            stream.write( value );        
        return resultSize;
    }

    protected int encodeStringLength(ElementInfo elementInfo, byte[] value, OutputStream stream) throws IOException, Exception {
        int resultSize = 0;
        BitArrayOutputStream bitStream = (BitArrayOutputStream)stream;                
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1ValueRangeConstraint.class)) {
            ASN1ValueRangeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1ValueRangeConstraint.class);
            resultSize += encodeConstraintLengthDeterminant(value.length, (int)constraint.min(), (int)constraint.max(), bitStream);
        }
        else
            resultSize += encodeLengthDeterminant(value.length, bitStream);
        return resultSize;
    }

    protected int encodeSequenceOf(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {    
        int resultSize = 0;
        Object[] collection = ((Collection<Object>)object).toArray();
        
        BitArrayOutputStream bitStream = (BitArrayOutputStream)stream;        
        if(elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1ValueRangeConstraint.class)) {
            ASN1ValueRangeConstraint constraint = elementInfo.getAnnotatedClass().getAnnotation(ASN1ValueRangeConstraint.class);
            resultSize += encodeConstraintLengthDeterminant(collection.length, (int)constraint.min(), (int)constraint.max(), bitStream);
        }
        else
            resultSize += encodeLengthDeterminant(collection.length, bitStream);        
        for(int i=0;i<collection.length;i++) {
            Object obj = collection[i];
            ElementInfo info = new ElementInfo(obj.getClass());
            info.setParentAnnotated(elementInfo.getAnnotatedClass());
            resultSize+=encodeClassType(obj,stream,info);
        }
        return resultSize;
    }    
        
    protected int encodeNull(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception  {
        return 0;
    }

    protected void doAlign(OutputStream stream) {
        ((BitArrayOutputStream) stream).align();
    }    
}
