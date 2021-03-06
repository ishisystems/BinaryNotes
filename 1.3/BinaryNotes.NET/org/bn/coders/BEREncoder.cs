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
	
	public class BEREncoder: Encoder
	{		
		public BEREncoder()
		{
		}

        public override void encode<T>(T obj, System.IO.Stream stream)
		{
			ReverseByteArrayOutputStream reverseStream = new ReverseByteArrayOutputStream();
			base.encode(obj, reverseStream);
			reverseStream.WriteTo(stream);
		}

        protected override int encodeSequence(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
            PropertyInfo[] fields = obj.GetType().GetProperties();
            for (int i = 0; i < fields.Length; i++)
			{
                PropertyInfo field = fields[fields.Length - 1 - i];
				resultSize += encodeSequenceField(obj, field, stream, elementInfo);
			}

            ASN1Sequence seqInfo = elementInfo.getAttribute<ASN1Sequence>();
            if (!seqInfo.IsSet)
            {
                resultSize += encodeHeader(
                    BERCoderUtils.getTagValueForElement(
                        elementInfo,
                        TagClasses.Universal,
                        ElementType.Constructed,
                        UniversalTags.Sequence)
                    , resultSize, stream);
            }
            else
            {
                resultSize += encodeHeader(
                    BERCoderUtils.getTagValueForElement(
                        elementInfo,
                        TagClasses.Universal,
                        ElementType.Constructed,
                        UniversalTags.Set)
                    , resultSize, stream);
            }
			return resultSize;
		}

        protected override int encodeChoice(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int result = 0;
			int sizeOfChoiceField = base.encodeChoice(obj, stream, elementInfo);
            if (elementInfo.Element != null)
            {
                result += encodeHeader(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.ContextSpecific, ElementType.Constructed, UniversalTags.LastUniversal), sizeOfChoiceField, stream);
            }
			result += sizeOfChoiceField;
			return result;
		}

        protected override int encodeEnumItem(object enumConstant, Type enumClass, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
            ASN1EnumItem enumObj = //elementInfo.AnnotatedClass.getAnnotation(typeof(ASN1EnumItem));
                elementInfo.getAttribute<ASN1EnumItem>();
			int szOfInt = encodeIntegerValue(enumObj.Tag, stream);
			resultSize += szOfInt;
			resultSize += encodeLength(szOfInt, stream);
			resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, UniversalTags.Enumerated), stream);
			return resultSize;
		}
		
		protected override int encodeBoolean(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 1;
            bool value = (bool)obj;
			stream.WriteByte((byte) (value ? 0xFF:0x00));
			
			resultSize += encodeLength(1, stream);
			resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, UniversalTags.Boolean), stream);
			return resultSize;
		}
		
		protected override int encodeAny(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0, sizeOfString = 0;
			byte[] buffer = (byte[]) obj;
            sizeOfString = buffer.Length;
            CoderUtils.checkConstraints(sizeOfString, elementInfo);
            stream.Write(buffer, 0, buffer.Length);
			resultSize += sizeOfString;
			return resultSize;
		}
		
		protected internal int encodeIntegerValue(long val, System.IO.Stream stream)
		{            
			int resultSize = CoderUtils.getIntegerLength(val);
			for (int i = 0; i < resultSize; i++)
			{
				stream.WriteByte((byte) val);
				val = val >> 8;
			}
			return resultSize;
		}
		
		protected override int encodeInteger(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
            int szOfInt = 0;
            if (obj.GetType().Equals(typeof(int)))
            {
                int val = (int)obj;
                CoderUtils.checkConstraints(val, elementInfo);
                szOfInt = encodeIntegerValue(val, stream);
            }
            else
            {
                long val = (long)obj;
                CoderUtils.checkConstraints(val, elementInfo);
                szOfInt = encodeIntegerValue(val, stream);
            }
			resultSize += szOfInt;
			resultSize += encodeLength(szOfInt, stream);
			resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, UniversalTags.Integer), stream);
			return resultSize;
		}

        protected override int encodeReal(object obj, System.IO.Stream stream, ElementInfo elementInfo)
        {
            int resultSize = 0;
            Double value = (Double) obj;
            //CoderUtils.checkConstraints(value,elementInfo);
            int szOfInt = 0;
            long asLong = System.BitConverter.DoubleToInt64Bits(value);
            if (value == Double.PositiveInfinity)
            { // positive infinity
                stream.WriteByte(0x40); // 01000000 Value is PLUS-INFINITY
            }
            else
            if(value == Double.NegativeInfinity) 
            { // negative infinity            
                stream.WriteByte(0x41); // 01000001 Value is MINUS-INFINITY
            }        
            else 
            if(asLong!=0x0) {
                long exponent = ((0x7ff0000000000000L & asLong) >> 52) - 1023 - 52;
                long mantissa = 0x000fffffffffffffL & asLong;
                mantissa |= 0x10000000000000L; // set virtual delimeter
                
                // pack mantissa for base 2
                while((mantissa & 0xFFL) == 0x0) {
                    mantissa >>= 8;
                    exponent += 8; //increment exponent to 8 (base 2)
                }        
                while((mantissa & 0x01L) == 0x0) {
                    mantissa >>= 1;
                    exponent+=1; //increment exponent to 1
                }
                 
                 szOfInt+= encodeIntegerValue(mantissa,stream);
                 int szOfExp = CoderUtils.getIntegerLength(exponent);
                 szOfInt+= encodeIntegerValue(exponent,stream);
                 
                 byte realPreamble = 0x80;
                 realPreamble |= (byte)(szOfExp - 1);
                 if( ((ulong)asLong & 0x8000000000000000L) == 1) {
                     realPreamble|= 0x40; // Sign
                 }
                 stream.WriteByte(realPreamble );
                 szOfInt+=1;
            }
            resultSize += szOfInt;
            resultSize += encodeLength(szOfInt, stream);
            resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, UniversalTags.Real), stream);
            return resultSize;
        }
				
		protected override int encodeOctetString(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0, sizeOfString = 0;
			byte[] buffer = (byte[]) obj;
            sizeOfString = buffer.Length;
            CoderUtils.checkConstraints(sizeOfString, elementInfo);

			stream.Write(buffer, 0, buffer.Length);
			
			resultSize += sizeOfString;
			resultSize += encodeLength(sizeOfString, stream);
			resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, UniversalTags.OctetString), stream);
			return resultSize;
		}
		
		protected override int encodeString(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0, sizeOfString = 0;
            byte[] buffer = null;
            if (CoderUtils.getStringTagForElement(elementInfo)== UniversalTags.UTF8String)
                buffer = System.Text.UTF8Encoding.UTF8.GetBytes((string)obj);
            else
                buffer = System.Text.ASCIIEncoding.ASCII.GetBytes((string)obj);
            sizeOfString = buffer.Length;
            CoderUtils.checkConstraints(sizeOfString, elementInfo);

            stream.Write(buffer, 0, buffer.Length);
			
			resultSize += sizeOfString;
			resultSize += encodeLength(sizeOfString, stream);
			resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, CoderUtils.getStringTagForElement(elementInfo)), stream);
			return resultSize;
		}
		
		protected override int encodeSequenceOf(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
            System.Collections.IList collection = (System.Collections.IList)obj;
            //System.Collections.ICollection collection = (System.Collections.ICollection)obj;
            CoderUtils.checkConstraints(collection.Count, elementInfo);
            
            int sizeOfCollection = 0;
			for (int i = 0; i < collection.Count; i++)
			{
				object item = collection[collection.Count - 1 - i];
				ElementInfo info = new ElementInfo(item.GetType());
                info.ParentAnnotatedClass = elementInfo.AnnotatedClass;
				sizeOfCollection += encodeClassType(item, stream, info);
			}
			resultSize += sizeOfCollection;
			resultSize += encodeLength(sizeOfCollection, stream);

            ASN1SequenceOf seqInfo = elementInfo.getAttribute<ASN1SequenceOf>();
            if (!seqInfo.IsSetOf)
            {
                resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Constructed, UniversalTags.Sequence), stream);
            }
            else
            {
                resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Constructed, UniversalTags.Set), stream);
            }
			return resultSize;
		}
		
		protected internal int encodeHeader(int tagValue, int contentLen, System.IO.Stream stream)
		{
			int resultSize = encodeLength(contentLen, stream);
			resultSize += encodeTag(tagValue, stream);
			return resultSize;
		}
		
		protected internal int encodeTag(int tagValue, System.IO.Stream stream)
		{
            int resultSize = 0;
            if ( tagValue  < 255 && ((tagValue&0x1f) < UniversalTags.LastUniversal )) { // Short tag
                stream.WriteByte((byte)tagValue);
                resultSize++;
            }
            else { // Long tag
               // First octet 
               int tagValueT = tagValue;
               stream.WriteByte( (byte) (tagValueT & 0x1F)); // 0001 1111
               resultSize++;
               tagValueT >>= 7;            
                while (tagValueT != 0) { 
                    stream.WriteByte((byte) ((tagValueT & 0x7F)|0x80));
                    tagValueT >>= 7 ;
                    resultSize++;
                }
                stream.WriteByte( (byte) ((tagValue&0x7f)| UniversalTags.LastUniversal ));
             }        
            return resultSize;
		}
		
		protected internal int encodeLength(int length, System.IO.Stream stream)
		{
			int resultSize = 0;
			
			if (length < 0)
			{
				throw new System.ArgumentException();
			}
			else if (length < 128)
			{
				stream.WriteByte((byte) length);
				resultSize++;
			}
			else if (length < 256)
			{
				stream.WriteByte((byte) length);
				stream.WriteByte((byte) 0x81);
				resultSize += 2;
			}
			else if (length < 65536)
			{
				stream.WriteByte((byte) (length));
				stream.WriteByte((byte) (length >> 8));
				stream.WriteByte((byte) 0x82);
				resultSize += 3;
			}
			else if (length < 16777126)
			{
				stream.WriteByte((byte) (length));
				stream.WriteByte((byte) (length >> 8));
				stream.WriteByte((byte) (length >> 16));
				stream.WriteByte((byte) 0x83);
				resultSize += 4;
			}
			else
			{
				stream.WriteByte((byte) (length));
				stream.WriteByte((byte) (length >> 8));
				stream.WriteByte((byte) (length >> 16));
				stream.WriteByte((byte) (length >> 24));
				stream.WriteByte((byte) 0x84);
				resultSize += 5;
			}
			return resultSize;
		}
		
		protected override int encodeNull(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			stream.WriteByte((byte) 0);
			int resultSize = 1;
			resultSize += encodeTag(BERCoderUtils.getTagValueForElement(elementInfo, TagClasses.Universal, ElementType.Primitive, UniversalTags.Null), stream);
			return resultSize;
		}

        protected override int encodeBitString(Object obj, System.IO.Stream stream, ElementInfo elementInfo) {
            int resultSize = 0, sizeOfString = 0;
            BitString str = (BitString)obj;
            CoderUtils.checkConstraints(str.getLength()*8-str.TrailBitsCnt , elementInfo);
            byte[] buffer = str.Value;
            stream.Write( buffer, 0, buffer.Length );
            stream.WriteByte ( (byte) str.getTrailBitsCnt() );
            sizeOfString = buffer.Length + 1;
            

            resultSize += sizeOfString;
            resultSize += encodeLength(sizeOfString, stream);
            resultSize += encodeTag( 
                BERCoderUtils.getTagValueForElement(elementInfo,TagClasses.Universal, ElementType.Primitive, UniversalTags.Bitstring),
                stream
            );
            return resultSize;
        }

	}
}