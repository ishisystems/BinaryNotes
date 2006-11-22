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
using org.bn.types;

namespace org.bn.coders
{
	
	public class BERDecoder:Decoder
	{		
		protected override DecodedObject<object> decodeSequence(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
            bool isSet = false;
            if(checkTagForObject(decodedTag,TagClasses.Universal, ElementType.Constructed,UniversalTags.Sequence,elementInfo)) {
            }
            else
            if(checkTagForObject(decodedTag,TagClasses.Universal, ElementType.Constructed,UniversalTags.Set,elementInfo)) {
                isSet = true;
            }
            else
                return null;
			DecodedObject<int> len = decodeLength(stream);
            DecodedObject<object> result = null;
            if(!isSet)
			    result =base.decodeSequence(decodedTag, objectClass, elementInfo, stream);
            else
                result = decodeSet(decodedTag, objectClass, elementInfo, len.Value, stream);
			if (result.Size != len.Value)
				throw new System.ArgumentException("Sequence '" + objectClass.ToString() + "' size is incorrect!");
			result.Size = result.Size + len.Size;
			return result;
		}

        protected virtual DecodedObject<object> decodeSet(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, int len, System.IO.Stream stream)
        {
            object sequence = Activator.CreateInstance(objectClass);
            initDefaultValues(sequence);
            DecodedObject<object> fieldTag = decodeTag(stream);
            int sizeOfSequence = 0;
            if (fieldTag != null)
                sizeOfSequence += fieldTag.Size;
            PropertyInfo[] fields =
                objectClass.GetProperties();

            bool fieldEncoded = false;
            do
            {
                for (int i = 0; i < fields.Length; i++)
                {
                    PropertyInfo field = fields[i];
                    DecodedObject<object> obj = decodeSequenceField(
                        fieldTag, sequence, field, stream, elementInfo, false
                    );
                    if (obj != null)
                    {
                        fieldEncoded = true;
                        sizeOfSequence += obj.Size;

                        if (i != fields.Length - 1 && CoderUtils.isAttributePresent<ASN1Any>(fields[i + 1]))
                        {
                        }
                        else
                        {
                            fieldTag = decodeTag(stream);
                            if (fieldTag != null)
                                sizeOfSequence += fieldTag.Size;
                            else
                            {
                                break;
                            }
                        }
                    }
                    ;
                }
            }
            while (sizeOfSequence < len && fieldEncoded);
            return new DecodedObject<object>(sequence, sizeOfSequence);
        }

        protected override DecodedObject<object> decodeChoice(DecodedObject<object> decodedTag, Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
        {
            if (elementInfo.Element != null)
            {
                if (!checkTagForObject(decodedTag, TagClasses.ContextSpecific, ElementType.Constructed, UniversalTags.LastUniversal, elementInfo))
                    return null;
                decodeLength(stream);
                decodedTag = decodeTag(stream);
            }
            return base.decodeChoice(decodedTag, objectClass, elementInfo, stream);
        }

		
		
		protected override DecodedObject<object> decodeEnumItem(DecodedObject<object> decodedTag, System.Type objectClass, System.Type enumClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.Enumerated, elementInfo))
				return null;
			return decodeIntegerValue(stream);
		}
		
		protected override DecodedObject<object> decodeBoolean(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.Boolean, elementInfo))
				return null;
			DecodedObject<object> result = decodeIntegerValue(stream);
			int val = (int) result.Value;
			if (val != 0)
				result.Value  = true;
			else
				result.Value = false;
			return result;
		}
		
		protected override DecodedObject<object> decodeAny(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			System.IO.MemoryStream anyStream = new System.IO.MemoryStream(1024);
			byte[] buffer = new byte[1024];
			int len = 0;
            int readed = stream.Read(buffer, 0, buffer.Length);
			while (readed > 0)
			{
				anyStream.Write(buffer,0,readed);
				len += readed;
                readed = stream.Read(buffer, 0, buffer.Length);
			}
            CoderUtils.checkConstraints(len, elementInfo);
			return new DecodedObject<object>(anyStream.ToArray(), len);
		}
		
		protected override DecodedObject<object> decodeNull(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.Null, elementInfo))
				return null;
			stream.ReadByte(); // ignore null length
            object obj = System.Activator.CreateInstance(objectClass);
			DecodedObject<object> result = new DecodedObject<object>(obj, 1);
			return result;
		}
		
		protected override DecodedObject<object> decodeInteger(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.Integer, elementInfo))
				return null;
			DecodedObject<object> result = decodeIntegerValue(stream);
            CoderUtils.checkConstraints((int)result.Value, elementInfo);
            return result;
		}

        protected override DecodedObject<object> decodeReal(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
        {
            if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.Real, elementInfo))
                return null;
            DecodedObject<int> len = decodeLength(stream);
            int realPreamble = stream.ReadByte();

            Double result = 0.0D;
            int szResult = len.Value;
            if ((realPreamble & 0x40) == 1)
            {
                // 01000000 Value is PLUS-INFINITY
                result = Double.PositiveInfinity;
            }
            if ((realPreamble & 0x41) == 1)
            {
                // 01000001 Value is MINUS-INFINITY
                result = Double.NegativeInfinity;
                szResult += 1;
            }
            else
                if (len.Value > 0)
                {
                    int szOfExp = 1 + (realPreamble & 0x3);
                    int sign = realPreamble & 0x40;
                    int ff = (realPreamble & 0x0C) >> 2;
                    DecodedObject<object> exponentEncFrm = decodeLongValue(stream, new DecodedObject<int>(szOfExp));
                    long exponent = (long)exponentEncFrm.Value;
                    DecodedObject<object> mantissaEncFrm = decodeLongValue(stream, new DecodedObject<int>(szResult - szOfExp - 1));
                    // Unpack mantissa & decrement exponent for base 2
                    long mantissa = (long)mantissaEncFrm.Value << ff;
                    while ((mantissa & 0x000ff00000000000L) == 0x0)
                    {
                        exponent -= 8;
                        mantissa <<= 8;
                    }
                    while ((mantissa & 0x0010000000000000L) == 0x0)
                    {
                        exponent -= 1;
                        mantissa <<= 1;
                    }
                    mantissa &= 0x0FFFFFFFFFFFFFL;
                    long lValue = (exponent + 1023 + 52) << 52;
                    lValue |= mantissa;
                    if (sign == 1)
                    {
                        lValue = (long)((ulong)lValue | 0x8000000000000000L);
                    }
                    result = System.BitConverter.Int64BitsToDouble(lValue);
                }
            return new DecodedObject<object>(result, len.Value + len.Size);
        }

        protected DecodedObject<object> decodeLongValue(System.IO.Stream stream)
        {
            DecodedObject<int> len =  decodeLength(stream);
            return decodeLongValue(stream,len);    
        }

        protected DecodedObject<object> decodeIntegerValue(System.IO.Stream stream)
        {
            DecodedObject<object> result = new DecodedObject<object>();
            DecodedObject<int> len = decodeLength(stream);
            int val = 0;
            for (int i = 0; i < len.Value; i++)
            {
                int bt = stream.ReadByte();
                if (bt == -1)
                {
                    throw new System.ArgumentException("Unexpected EOF when encoding!");
                }
                if (i == 0 && (bt & (byte)0x80) != 0)
                {
                    bt = bt - 256;
                }

                val = (val << 8) | bt;
            }
            result.Value = val;
            result.Size = len.Value + len.Size;
            return result;  
        }

		protected internal virtual DecodedObject<object> decodeLongValue(System.IO.Stream stream, DecodedObject<int> len)
        {
            DecodedObject<object> result = new DecodedObject<object>();
            long val = 0;
            for (int i = 0; i < len.Value; i++)
            {
                long bt = stream.ReadByte();
                if (bt == -1)
                {
                    throw new System.ArgumentException("Unexpected EOF when encoding!");
                }
                if (i == 0 && (bt & (byte)0x80) != 0)
                {
                    bt = bt - 256;
                }

                val = (val << 8) | bt;
            }
            result.Value = val;
            result.Size = len.Value + len.Size;
            return result;
        }
		
		protected override DecodedObject<object> decodeOctetString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.OctetString, elementInfo))
				return null;
			DecodedObject<int> len = decodeLength(stream);
            CoderUtils.checkConstraints(len.Value, elementInfo);
			byte[] byteBuf = new byte[len.Value];
            stream.Read(byteBuf,0,byteBuf.Length);
			return new DecodedObject<object>(byteBuf, len.Value + len.Size);
		}

		protected override DecodedObject<object> decodeBitString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, UniversalTags.Bitstring, elementInfo))
				return null;
			DecodedObject<int> len = decodeLength(stream);            
            int trailBitCnt = stream.ReadByte();
            CoderUtils.checkConstraints(len.Value * 8 - trailBitCnt, elementInfo);
			byte[] byteBuf = new byte[len.Value - 1];
            stream.Read(byteBuf,0,byteBuf.Length);
			return new DecodedObject<object>( new BitString(byteBuf,trailBitCnt), len.Value + len.Size);
		}
		
		protected override DecodedObject<object> decodeString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, CoderUtils.getStringTagForElement(elementInfo), elementInfo))
				return null;
			DecodedObject<int> len = decodeLength(stream);
            CoderUtils.checkConstraints(len.Value, elementInfo);
			byte[] byteBuf = new byte[len.Value];
            stream.Read(byteBuf, 0, byteBuf.Length);
            string result = null;
            if (CoderUtils.getStringTagForElement(elementInfo) == UniversalTags.UTF8String)
            {
                result = new string(
                    System.Text.UTF8Encoding.UTF8.GetChars(byteBuf)
                );
            }
            else {
                result = new string(
                    System.Text.ASCIIEncoding.ASCII.GetChars(byteBuf)
                );
            }
 
			return new DecodedObject<object>(result, len.Value + len.Size);
		}
		
		protected override DecodedObject<object> decodeSequenceOf(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Constructed, UniversalTags.Sequence, elementInfo))
				return null;

            Type paramType = (System.Type)objectClass.GetGenericArguments()[0];
            Type collectionType = typeof(List<>);
            Type genCollectionType = collectionType.MakeGenericType(paramType);
            Object param = Activator.CreateInstance(genCollectionType);
            
            DecodedObject<int> len = decodeLength(stream);
			if (len.Value != 0)
			{
				int lenOfItems = 0;
                elementInfo.ParentAnnotatedClass = elementInfo.AnnotatedClass;
				elementInfo.AnnotatedClass = paramType;
				elementInfo.Element = null;
				do 
				{
					DecodedObject<object> itemTag = decodeTag(stream);
					DecodedObject<object> item = decodeClassType(itemTag, paramType, elementInfo, stream);
					if (item != null)
					{
						lenOfItems += item.Size + itemTag.Size;
                        MethodInfo method = param.GetType().GetMethod("Add");
                        method.Invoke(param, new object[] { item.Value });
					}
				}
				while (lenOfItems < len.Value);
                CoderUtils.checkConstraints(lenOfItems, elementInfo);
			}
			return new DecodedObject<object>(param, len.Value + len.Size);
		}
		
		
		protected internal virtual DecodedObject<int> decodeLength(System.IO.Stream stream)
		{
			int result = 0;
			int bt = stream.ReadByte();
			if (bt == - 1)
				throw new System.ArgumentException("Unexpected EOF when decoding!");
			
			int len = 1;
			if (bt < 128)
			{
				result = bt;
			}
			else
			{
                // Decode length bug fixed. Thanks to John 
				for (int i = bt - 128; i > 0; i--)
				{
					int fBt = stream.ReadByte();
					if (fBt == - 1)
						throw new System.ArgumentException("Unexpected EOF when decoding!");
					
					result = result << 8;
					result = result | fBt;
					len++;
				}
			}
			return new DecodedObject<int>(result, len);
		}
		
		protected override DecodedObject<object> decodeTag(System.IO.Stream stream)
		{
			int result = 0;
			int bt = stream.ReadByte();
			if (bt == - 1)
				return null;
			result = bt;
			int len = 1;
			if ((result & UniversalTags.LastUniversal) == UniversalTags.LastUniversal)
			{
				sbyte fBt = (sbyte) bt;
				while ((fBt & 128) != 0)
				{
					result = result << 7;
					result = result | (fBt & 127);
					bt = stream.ReadByte();
					if (bt == - 1)
						break;
					fBt = (sbyte) bt;
					len++;
				}
			}
			return new DecodedObject<object>(result, len);
		}
		
		protected bool checkTagForObject(DecodedObject<object> decodedTag, int tagClass, int elementType, int universalTag, ElementInfo elementInfo)
		{
			int definedTag = BERCoderUtils.getTagValueForElement(elementInfo, tagClass, elementType, universalTag);
			return definedTag == (int) decodedTag.Value;
		}
	}
}