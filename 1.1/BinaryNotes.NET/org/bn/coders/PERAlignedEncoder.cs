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
using System;
using System.Reflection;
using System.Collections.Generic;
using org.bn.utils;
using org.bn.attributes;
using org.bn.attributes.constraints;

namespace org.bn.coders
{
	
	public class PERAlignedEncoder:Encoder
	{
		public PERAlignedEncoder()
		{
		}

        public override void encode<T>(T obj, System.IO.Stream stream)
		{
			BitArrayOutputStream bitStream = new BitArrayOutputStream();
			base.encode(obj, bitStream);
			bitStream.WriteTo(stream);
		}
		
		protected virtual int encodeIntegerValueAsBytes(int val, System.IO.Stream stream)
		{
			int integerSize = CoderUtils.getIntegerLength(val);
			for (int i = integerSize - 1; i >= 0; i--)
			{
				int valueTmp = val >> (8 * i);
				stream.WriteByte((byte) valueTmp);
			}
			return integerSize;
		}
		
		/// <summary> Encoding constraint length determinant procedure.
		/// ITU-T X.691. 10.9. General rules for encoding a length determinant
		/// 
		/// </summary>
		protected virtual int encodeConstraintLengthDeterminant(int length, int min, int max, BitArrayOutputStream stream)
		{
			if (max <= 0xFFFF)
			{
				// 10.9. NOTE 2 � (Tutorial) In the case of the ALIGNED variant 
				// if the length count is bounded above by an upper bound that is 
				// less than 64K, then the constrained whole number encoding 
				// is used for the length.
				return encodeConstraintNumber(length, min, max, stream); // encoding as constraint integer
			}
			else
				return encodeLengthDeterminant(length, stream);
		}
		
		/// <summary> Encoding length determinant procedure.
		/// ITU-T X.691. 10.9. General rules for encoding a length determinant
		/// </summary>
		protected virtual int encodeLengthDeterminant(int length, BitArrayOutputStream stream)
		{
			int result = 0;
			doAlign(stream);
			if (length >= 0 && length < 0x80)
			{
				// NOTE 2. a) ("n" less than 128)             
				stream.WriteByte(length); // a single octet containing "n" with bit 8 set to zero;
				result = 1;
			}
			else if (length < 0x4000)
			{
				// NOTE 2. b) ("n" less than 16K) two octets 
				// containing "n" with bit 8 of the first octet 
				// set to 1 and bit 7 set to zero;
				stream.WriteByte( (length >> 8) & 0x3f | 0x80);
				stream.WriteByte(length);
				result = 2;
			}
			else
			{
				// NOTE 2. b) (large "n") a single octet containing a count "m" 
				// with bit 8 set to 1 and bit 7 set to 1. 
				// The count "m" is one to four, and the length indicates that 
				// a fragment of the material follows (a multiple "m" of 16K items). 
				// For all values of "m", the fragment is then followed 
				// by another length encoding for the remainder of the material.        
				throw new System.ApplicationException("Not supported for this version. Length too big!");
			}
			return result;
		}
		
		/// <summary> Encoding of a constrained whole number
		/// ITU-T X.691. 10.5. 
		/// NOTE � (Tutorial) This subclause is referenced by other clauses, 
		/// and itself references earlier clauses for the production of 
		/// a nonnegative-binary-integer or a 2's-complement-binary-integer encoding.
		/// </summary>
		protected virtual int encodeConstraintNumber(int val, int min, int max, BitArrayOutputStream stream)
		{
			int result = 0;
			int valueRange = max - min;
			int narrowedVal = val - min;
			int maxBitLen = PERCoderUtils.getMaxBitLength(valueRange);
			
			if (valueRange == 0)
			{
				return result;
			}
			
			// The rest of this Note addresses the ALIGNED variant. 
			if (valueRange > 0 && valueRange < 256)
			{
				/*
				* 1. Where the range is less than or equal to 255, the value encodes 
				* into a bit-field of the minimum size for the range. 
				* 2. Where the range is exactly 256, the value encodes 
				* into a single octet octet-aligned bit-field. 
				*/
				doAlign(stream);
				for (int i = maxBitLen - 1; i >= 0; i--)
				{
					int bitValue = (narrowedVal >> i) & 0x1;
					stream.writeBit(bitValue);
				}
				result = 1;
			}
			else if (valueRange > 0 && valueRange < 65536)
			{
				/* 
				* 3. Where the range is 257 to 64K, the value encodes into 
				* a two octet octet-aligned bit-field. 
				*/
				doAlign(stream);
				stream.WriteByte(narrowedVal >> 8 );
				stream.WriteByte(narrowedVal & 0xFF);
				result = 2;
			}
			else
			{
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
				result = encodeConstraintLengthDeterminant(CoderUtils.getIntegerLength(narrowedVal), 1, CoderUtils.getPositiveIntegerLength(valueRange), stream);
				doAlign(stream);
				result += encodeIntegerValueAsBytes(narrowedVal, stream);
			}
			return result;
		}
		
		/// <summary> Encoding of a semi-constrained whole number
		/// ITU-T X.691. 10.7. 
		/// NOTE � (Tutorial) This procedure is used when a lower bound can be 
		/// identified but not an upper bound. The encoding procedure places 
		/// the offset from the lower bound into the minimum number of octets 
		/// as a non-negative-binary-integer, and requires an explicit length 
		/// encoding (typically a single octet) as specified in later procedures.
		/// </summary>
        /// 
        protected virtual int encodeSemiConstraintNumber(int val, int min, BitArrayOutputStream stream)
        {
            int result = 0;
            int narrowedVal = val - min;
            int intLen = CoderUtils.getIntegerLength(narrowedVal);
            result += encodeLengthDeterminant(intLen, stream);
            doAlign(stream);
            result += encodeIntegerValueAsBytes(narrowedVal, stream);
            return result;
        }

		
		/// <summary> Encode normally small number
		/// ITU-T X.691. 10.6
		/// NOTE � (Tutorial) This procedure is used when encoding 
		/// a non-negative whole number that is expected to be small, but whose size 
		/// is potentially unlimited due to the presence of an extension marker. 
		/// An example is a choice index.
		/// </summary>
		protected virtual int encodeNormallySmallNumber(int val, BitArrayOutputStream stream)
		{
			int result = 0;
			if (val > 0 && val < 64)
			{
				/* 10.6.1 If the non-negative whole number, "n", is less than 
				* or equal to 63, then a single-bit bit-field shall be appended
				* to the field-list with the bit set to 0, and "n" shall be 
				* encoded as a non-negative-binary-integer into a 6-bit bit-field.
				*/
				stream.writeBit(0);
				for (int i = 0; i < 6; i++)
				{
					int bitValue = (val >> 6 - i) & 0x1;
					stream.writeBit(bitValue);
				}
				result = 1;
			}
			else
			{
				/* If "n" is greater than or equal to 64, a single-bit 
				* bit-field with the bit set to 1 shall be appended to the field-list.
				* The value "n" shall then be encoded as a semi-constrained 
				* whole number with "lb" equal to 0 and the procedures of 
				* 10.9 shall be invoked to add it to the field-list preceded 
				* by a length determinant.
				*/
				stream.writeBit(1);
				result += encodeSemiConstraintNumber(val, 0, stream);
			}
			return result;
		}
		
		/// <summary> Encoding of a unconstrained whole number
		/// ITU-T X.691. 10.8. 
		/// NOTE � (Tutorial) This case only arises in the encoding of the 
		/// value of an integer type with no lower bound. The procedure
		/// encodes the value as a 2's-complement-binary-integer into 
		/// the minimum number of octets required to accommodate the encoding,
		/// and requires an explicit length encoding (typically a single octet) 
		/// as specified in later procedures.
		/// </summary>
		protected virtual int encodeUnconstraintNumber(int val, BitArrayOutputStream stream)
		{
			int result = 0;
			int intLen = CoderUtils.getIntegerLength(val);
			result += encodeLengthDeterminant(intLen, stream);
			doAlign(stream);
			result += encodeIntegerValueAsBytes(val, stream);
			return result;
		}
		
		protected override int encodeInteger(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int result = 0;
			System.Int32 val = (System.Int32) obj;
			BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
			if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
			{
				ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
				result += encodeConstraintNumber(val, (int) constraint.Min, (int) constraint.Max, bitStream);
			}
			else
				result += encodeUnconstraintNumber(val, bitStream);
			return result;
		}
		
		
		protected virtual int encodeSequencePreamble(object obj, System.IO.Stream stream)
		{
			int resultBitSize = 0;
            foreach (PropertyInfo field in obj.GetType().GetProperties())
            {
                if (isOptionalField(field))
				{
					object invokeObjResult = invokeGetterMethodForField(field, obj);
					((BitArrayOutputStream) stream).writeBit(invokeObjResult != null);
					resultBitSize += 1;
				}
			}
			doAlign(stream);
			return (resultBitSize / 8) + (resultBitSize % 8 > 0?1:0);
		}
		
		protected override int encodeSequence(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
			resultSize += encodeSequencePreamble(obj, stream);
			resultSize += base.encodeSequence(obj, stream, elementInfo);
			return resultSize;
		}
		
		protected virtual int encodeChoicePreamble(object obj, System.IO.Stream stream, int elementIndex)
		{
            return encodeConstraintNumber(elementIndex, 1, obj.GetType().GetProperties().Length, (BitArrayOutputStream)stream);
		}
		
		/// <summary> Encoding of the choice structure
		/// ITU-T X.691. 22. 
		/// NOTE � (Tutorial) A choice type is encoded by encoding an index specifying 
		/// the chosen alternative. This is encoded as for a constrained integer 
		/// (unless the extension marker is present in the choice type, 
		/// in which case it is a normally small non-negative whole number) 
		/// and would therefore typically occupy a fixed length bit-field of the 
		/// minimum number of bits needed to encode the index. (Although it could 
		/// in principle be arbitrarily large.) This is followed by the encoding 
		/// of the chosen alternative, with alternatives that are extension 
		/// additions encoded as if they were the value of an open type field. 
		/// Where the choice has only one alternative, there is no encoding 
		/// for the index.
		/// </summary>
		protected override int encodeChoice(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
			doAlign(stream);
			ElementInfo info = null;
			int elementIndex = 0;
			foreach(PropertyInfo field in obj.GetType().GetProperties())
			{
				elementIndex++;
                if (invokeSelectedMethodForField(field, obj))
                {
                    info = new ElementInfo(field, CoderUtils.getAttribute<ASN1Element>(field));
					break;
				}
			}
			if (info == null)
			{
				throw new System.ArgumentException("The choice '" + obj.ToString() + "' does not have a selected item!");
			}
			object invokeObjResult = invokeGetterMethodForField((System.Reflection.PropertyInfo) info.AnnotatedClass, obj);
			resultSize += encodeChoicePreamble(obj, stream, elementIndex);
			resultSize += encodeClassType(invokeObjResult, stream, info);
			return resultSize;
		}
		
		protected override int encodeEnumItem(object enumConstant, System.Type enumClass, System.IO.Stream stream, ElementInfo elementInfo)
		{
			ASN1EnumItem enumObj = elementInfo.getAttribute<ASN1EnumItem>();
			int min = 0, max = enumClass.GetFields().Length, val = 0;
            foreach (FieldInfo enumItem in enumClass.GetFields())
		    {
                if (CoderUtils.isAttributePresent<ASN1EnumItem>(enumItem))
			    {
					ASN1EnumItem enumItemObj = CoderUtils.getAttribute<ASN1EnumItem>(enumItem);
					if (enumItemObj.Tag == enumObj.Tag)
						break;
					val++;
				}
			}
			return encodeConstraintNumber(val, min, max, (BitArrayOutputStream) stream);
		}
		
		protected override int encodeBoolean(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 1;
			BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
			bitStream.writeBit((System.Boolean) obj);
			return resultSize;
		}
		
		protected override int encodeAny(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0, sizeOfString = 0;
			byte[] buffer = (byte[]) obj;
            stream.Write(buffer, 0, buffer.Length);
			sizeOfString = buffer.Length;
			resultSize += sizeOfString;
			return resultSize;
		}
		
		protected override int encodeOctetString(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0, sizeOfString = 0;
			byte[] buffer = (byte[]) obj;
			sizeOfString = buffer.Length;
			
			BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
			if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
			{
				ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
				resultSize += encodeConstraintLengthDeterminant(sizeOfString, (int) constraint.Min, (int) constraint.Max, bitStream);
			}
			else
				resultSize += encodeLengthDeterminant(sizeOfString, bitStream);
			if (sizeOfString > 0)
			{
                stream.Write(buffer, 0, buffer.Length);
			}
			return resultSize;
		}
		
		protected override int encodeString(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;

            byte[] val = System.Text.UTF8Encoding.UTF8.GetBytes((string)obj);
			
			resultSize = encodeStringLength(elementInfo, val, stream);
			resultSize += val.Length;
			if (val.Length > 0)
			{
                stream.Write(val, 0, val.Length);
			}
			return resultSize;
		}
		
		protected virtual int encodeStringLength(ElementInfo elementInfo, byte[] val, System.IO.Stream stream)
		{
			int resultSize = 0;
			BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
			if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
			{
				ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
				resultSize += encodeConstraintLengthDeterminant(val.Length, (int) constraint.Min, (int) constraint.Max, bitStream);
			}
			else
				resultSize += encodeLengthDeterminant(val.Length, bitStream);
			return resultSize;
		}
		
		protected override int encodeSequenceOf(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;			
            System.Collections.IList collection = (System.Collections.IList)obj;
			
			BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
			if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
			{
				ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
				resultSize += encodeConstraintLengthDeterminant(collection.Count, (int) constraint.Min, (int) constraint.Max, bitStream);
			}
			else
				resultSize += encodeLengthDeterminant(collection.Count, bitStream);

            for (int i = 0; i < collection.Count; i++)
            {
                object itemObj = collection[i];
                ElementInfo info = new ElementInfo(itemObj.GetType());
                info.ParentAnnotatedClass = elementInfo.AnnotatedClass;
                resultSize += encodeClassType(itemObj, stream, info);
            }
			return resultSize;
		}
		
		protected override int encodeNull(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			return 0;
		}
		
		protected virtual void  doAlign(System.IO.Stream stream)
		{
			((BitArrayOutputStream) stream).align();
		}
	}
}