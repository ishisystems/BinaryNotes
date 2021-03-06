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
	
	public class PERAlignedDecoder:Decoder
	{
		
		public override T decode<T>(System.IO.Stream stream)
		{
			return base.decode<T>(new BitArrayInputStream(stream));
		}
		
		
		protected virtual void  skipAlignedBits(System.IO.Stream stream)
		{
			((BitArrayInputStream) stream).skipUnreadedBits();
		}
		
		protected virtual int decodeIntegerValueAsBytes(int intLen, System.IO.Stream stream)
		{
			int result = 0;
			for (int i = intLen - 1; i >= 0; i--)
			{
				result |= stream.ReadByte() << 8 * i;
			}
			return result;
		}
		
		/// <summary> Decode the constraint length determinant.
		/// ITU-T X.691. 10.9. General rules for encoding a length determinant
		/// 
		/// </summary>
		protected virtual int decodeConstraintLengthDeterminant(int min, int max, BitArrayInputStream stream)
		{
			if (max <= 0xFFFF)
			{
				// 10.9. NOTE 2 � (Tutorial) In the case of the ALIGNED variant 
				// if the length count is bounded above by an upper bound that is 
				// less than 64K, then the constrained whole number encoding 
				// is used for the length.
				return decodeConstraintNumber(min, max, stream); // encoding as constraint integer
			}
			else
				return decodeLengthDeterminant(stream);
		}
		
		/// <summary> Decode the length determinant
		/// ITU-T X.691. 10.9. General rules for encoding a length determinant
		/// </summary>
		protected virtual int decodeLengthDeterminant(BitArrayInputStream stream)
		{
			skipAlignedBits(stream);
			int result = stream.ReadByte();
			if ((result & 0x80) == 0)
			{
				// NOTE 2. a) ("n" less than 128)             
				// a single octet containing "n" with bit 8 set to zero;
				return result;
			}
			else
			{
				// NOTE 2. b) ("n" less than 16K) two octets 
				// containing "n" with bit 8 of the first octet 
				// set to 1 and bit 7 set to zero;        
				result = (result & 0x3f) << 8;
				result |= stream.ReadByte();
			}
			// WARNING! Large N doesn't supported NOW!
			// NOTE 2. b) (large "n") a single octet containing a count "m" 
			// with bit 8 set to 1 and bit 7 set to 1. 
			// The count "m" is one to four, and the length indicates that 
			// a fragment of the material follows (a multiple "m" of 16K items). 
			// For all values of "m", the fragment is then followed 
			// by another length encoding for the remainder of the material.        
			return result;
		}
		
		/// <summary> Decode of the constrained whole number
		/// ITU-T X.691. 10.5. 
		/// NOTE � (Tutorial) This subclause is referenced by other clauses, 
		/// and itself references earlier clauses for the production of 
		/// a nonnegative-binary-integer or a 2's-complement-binary-integer encoding.
		/// </summary>
		protected virtual int decodeConstraintNumber(int min, int max, BitArrayInputStream stream)
		{
			int result = 0;
			int valueRange = max - min;
			//!!!! int narrowedVal = value - min; !!!
			int maxBitLen = PERCoderUtils.getMaxBitLength(valueRange);
			
			if (valueRange == 0)
			{
				return max;
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
				skipAlignedBits(stream);
				result = stream.readBits(maxBitLen);
				result += min;
			}
			else if (valueRange > 0 && valueRange < 65536)
			{
				/* 
				* 3. Where the range is 257 to 64K, the value encodes into 
				* a two octet octet-aligned bit-field. 
				*/
				skipAlignedBits(stream);
				result = stream.ReadByte() << 8;
				result |= stream.ReadByte();
				result += min;
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
				int intLen = decodeConstraintLengthDeterminant(1, CoderUtils.getPositiveIntegerLength(valueRange), stream);
				skipAlignedBits(stream);
				result = decodeIntegerValueAsBytes(intLen, stream);
				result += min;
			}
			
			return result;
		}
		
		/// <summary> Decode the semi-constrained whole number
		/// ITU-T X.691. 10.7. 
		/// NOTE � (Tutorial) This procedure is used when a lower bound can be 
		/// identified but not an upper bound. The encoding procedure places 
		/// the offset from the lower bound into the minimum number of octets 
		/// as a non-negative-binary-integer, and requires an explicit length 
		/// encoding (typically a single octet) as specified in later procedures.
		/// </summary>
		protected virtual int decodeSemiConstraintNumber(int min, BitArrayInputStream stream)
		{
			int result = 0;
			int intLen = decodeLengthDeterminant(stream);
			skipAlignedBits(stream);
			result = decodeIntegerValueAsBytes(intLen, stream);
			result += min;
			return result;
		}
		
		/// <summary> Decode the normally small number
		/// ITU-T X.691. 10.6
		/// NOTE � (Tutorial) This procedure is used when encoding 
		/// a non-negative whole number that is expected to be small, but whose size 
		/// is potentially unlimited due to the presence of an extension marker. 
		/// An example is a choice index.
		/// </summary>
		protected virtual int decodeNormallySmallNumber(BitArrayInputStream stream)
		{
			int result = 0;
			int bitIndicator = stream.readBit();
			if (bitIndicator == 0)
			{
				/* 10.6.1 If the non-negative whole number, "n", is less than 
				* or equal to 63, then a single-bit bit-field shall be appended
				* to the field-list with the bit set to 0, and "n" shall be 
				* encoded as a non-negative-binary-integer into a 6-bit bit-field.
				*/
				result = stream.readBits(6);
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
				result = decodeSemiConstraintNumber(0, stream);
			}
			return result;
		}
		
		/// <summary> Decode the unconstrained whole number
		/// ITU-T X.691. 10.8. 
		/// NOTE � (Tutorial) This case only arises in the encoding of the 
		/// value of an integer type with no lower bound. The procedure
		/// encodes the value as a 2's-complement-binary-integer into 
		/// the minimum number of octets required to accommodate the encoding,
		/// and requires an explicit length encoding (typically a single octet) 
		/// as specified in later procedures.
		/// </summary>
		protected virtual int decodeUnconstraintNumber(BitArrayInputStream stream)
		{
			int result = 0;
			int numLen = decodeLengthDeterminant(stream);
			skipAlignedBits(stream);
			result += decodeIntegerValueAsBytes(numLen, stream);
			return result;
		}
		
		protected override DecodedObject<object> decodeChoice(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			object choice = System.Activator.CreateInstance(objectClass);
			skipAlignedBits(stream);
			System.Reflection.PropertyInfo[] fields = objectClass.GetProperties();
			int elementIndex = decodeConstraintNumber(1, fields.Length, (BitArrayInputStream) stream);
			DecodedObject<object> val = null;
			for (int i = 0; i < elementIndex && i < fields.Length; i++)
			{
				if (i + 1 == elementIndex)
				{
					System.Reflection.PropertyInfo field = fields[i];
					ElementInfo info = new ElementInfo(field, CoderUtils.getAttribute<ASN1Element>(field));
                    val = decodeClassType(decodedTag, field.PropertyType, info, stream);
                    if(val != null)
					    invokeSelectMethodForField(field, choice, val.Value);
					break;
				}
				;
			}
            if (val == null && elementInfo.Element != null && !elementInfo.Element.IsOptional)
            {
				throw new System.ArgumentException("The choice '" + objectClass.ToString() + "' does not have a selected item!");
			}
			else
				return new DecodedObject<object>(choice);
		}
		
		
		protected virtual int getSequencePreambleBitLen(System.Type objectClass)
		{
			int preambleLen = 0;
            foreach ( PropertyInfo field in objectClass.GetProperties()) 
            {
				if (isOptionalField(field))
				{
					preambleLen++;
				}
			}
			
			return preambleLen;
		}
		
		protected override DecodedObject<object> decodeSequence(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			// TO DO 
			// Decode sequence preamble
			BitArrayInputStream bitStream = (BitArrayInputStream) stream;
			int preambleLen = getSequencePreambleBitLen(objectClass);
			int preamble = bitStream.readBits(preambleLen);
			int preambleCurrentBit = 32 - preambleLen;
			skipAlignedBits(stream);
			object sequence = System.Activator.CreateInstance(objectClass);

            foreach ( PropertyInfo field in objectClass.GetProperties()) 
            {
				if (isOptionalField(field))
				{
					if ((preamble & (0x80000000 >> preambleCurrentBit)) != 0)
					{
						decodeSequenceField(null, sequence, field, stream, elementInfo);
					}
					preambleCurrentBit++;
				}
				else
				{
					decodeSequenceField(null, sequence, field, stream, elementInfo);
				}
			}
			return new DecodedObject<object>(sequence);
		}
		
		
		protected override DecodedObject<object> decodeEnumItem(DecodedObject<object> decodedTag, System.Type objectClass, System.Type enumClass, ElementInfo elementInfo, System.IO.Stream stream)
		{			
			int min = 0, max = enumClass.GetFields().Length;
			int enumItemIdx = decodeConstraintNumber(min, max, (BitArrayInputStream) stream);
			DecodedObject<object> result = new DecodedObject<object>();
			int idx = 0;
            foreach (FieldInfo enumItem in enumClass.GetFields())
            {
                if (CoderUtils.isAttributePresent<ASN1EnumItem>(enumItem))
                {
					if (idx++ == enumItemIdx)
					{
                        ASN1EnumItem enumItemObj = CoderUtils.getAttribute<ASN1EnumItem>(enumItem);
						result.Value = enumItemObj.Tag;
						break;
					}
				}
			}
			return result;
		}
		
		protected override DecodedObject<object> decodeBoolean(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			DecodedObject<object> result = new DecodedObject<object>();
			BitArrayInputStream bitStream = (BitArrayInputStream) stream;
			result.Value = (bitStream.readBit() == 1);
			return result;
		}
		
		protected override DecodedObject<object> decodeAny(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			return null;
		}
		
		protected override DecodedObject<object> decodeNull(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			return new DecodedObject<object>(System.Activator.CreateInstance(objectClass));
		}
		
		protected override DecodedObject<object> decodeInteger(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			DecodedObject<object> result = new DecodedObject<object>();
			BitArrayInputStream bitStream = (BitArrayInputStream) stream;
			int val = 0;
            if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
            {
                ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
                val = decodeConstraintNumber((int)constraint.Min, (int)constraint.Max, bitStream);
			}
			else
				val = decodeUnconstraintNumber(bitStream);
			result.Value = val;
			return result;
		}
		
		
		protected override DecodedObject<object> decodeOctetString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			DecodedObject<object> result = new DecodedObject<object>();
			int sizeOfString = 0;
			
			BitArrayInputStream bitStream = (BitArrayInputStream) stream;
            if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
            {
                ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
                sizeOfString = decodeConstraintLengthDeterminant((int)constraint.Min, (int)constraint.Max, bitStream);
			}
			else
				sizeOfString = decodeLengthDeterminant(bitStream);
			if (sizeOfString > 0)
			{
				byte[] val = new byte[sizeOfString];
				stream.Read(val, 0, val.Length);
                result.Value = val;
			}
			else
				result.Value = (new sbyte[0]);
			return result;
		}
		
		protected virtual int decodeStringLength(ElementInfo elementInfo, System.IO.Stream stream)
		{
			int resultSize = 0;
			BitArrayInputStream bitStream = (BitArrayInputStream) stream;
            if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
            {
                ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
                resultSize = decodeConstraintLengthDeterminant((int)constraint.Min, (int)constraint.Max, bitStream);
			}
			else
				resultSize = decodeLengthDeterminant(bitStream);
			return resultSize;
		}
		
		
		protected override DecodedObject<object> decodeString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			DecodedObject<object> result = new DecodedObject<object>();
			int strLen = decodeStringLength(elementInfo, stream);
			if (strLen > 0)
			{
				byte[] val = new byte[strLen];
				stream.Read(val, 0, val.Length);

				result.Value = new string(
                    System.Text.UTF8Encoding.UTF8.GetChars(val)
                );
			}
			else
				result.Value = ("");
			
			return result;
		}
		
		protected override DecodedObject<object> decodeSequenceOf(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{

            Type paramType = (System.Type)objectClass.GetGenericArguments()[0];
            Type collectionType = typeof(List<>);
            Type genCollectionType = collectionType.MakeGenericType(paramType);
            Object param = Activator.CreateInstance(genCollectionType);

            int countOfElements;
            BitArrayInputStream bitStream = (BitArrayInputStream)stream;
            if (elementInfo.isAttributePresent<ASN1ValueRangeConstraint>())
            {
                ASN1ValueRangeConstraint constraint = elementInfo.getAttribute<ASN1ValueRangeConstraint>();
                countOfElements = decodeConstraintLengthDeterminant((int)constraint.Min, (int)constraint.Max, bitStream);
            }
            else
                countOfElements = decodeLengthDeterminant(bitStream);

            ElementInfo elementItemInfo = new ElementInfo();
            elementItemInfo.ParentAnnotatedClass = elementInfo.AnnotatedClass;
            elementItemInfo.AnnotatedClass = paramType;
            elementItemInfo.Element = null;


            for (int i = 0; i < countOfElements;i ++ )
            {
                DecodedObject<object> item = decodeClassType(null, paramType, elementItemInfo, stream);
                if (item != null)
                {
                    MethodInfo method = param.GetType().GetMethod("Add");
                    method.Invoke(param, new object[] { item.Value });
                }
            }

            return new DecodedObject<object>(param);
		}
		
		protected override DecodedObject<object> decodeTag(System.IO.Stream stream)
		{
			return null;
		}
		
	}
}