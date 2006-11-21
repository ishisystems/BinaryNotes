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

namespace org.bn.coders
{
	
	public class BERDecoder:Decoder
	{		
		protected override DecodedObject<object> decodeSequence(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Constructed, UniversalTags.Sequence, elementInfo))
				return null;
			else
			{
				DecodedObject<int> len = decodeLength(stream);
				DecodedObject<object> result = base.decodeSequence(decodedTag, objectClass, elementInfo, stream);
				if (result.Size != len.Value)
					throw new System.ArgumentException("Sequence '" + objectClass.ToString() + "' size is incorrect!");
				result.Size = result.Size + len.Size;
				return result;
			}
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
			return decodeIntegerValue(stream);
		}
		
		protected internal virtual DecodedObject<object> decodeIntegerValue(System.IO.Stream stream)
		{
			DecodedObject<object> result = new DecodedObject<object>();
			DecodedObject<int> len = decodeLength(stream);
			int val = 0;
			for (int i = 0; i < len.Value; i++)
			{
				int bt = stream.ReadByte();
				if (bt == - 1)
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
			byte[] byteBuf = new byte[len.Value];
            stream.Read(byteBuf,0,byteBuf.Length);
			return new DecodedObject<object>(byteBuf, len.Value + len.Size);
		}
		
		protected override DecodedObject<object> decodeString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (!checkTagForObject(decodedTag, TagClasses.Universal, ElementType.Primitive, BERCoderUtils.getStringTagForElement(elementInfo), elementInfo))
				return null;
			DecodedObject<int> len = decodeLength(stream);
			byte[] byteBuf = new byte[len.Value];
            stream.Read(byteBuf, 0, byteBuf.Length);
			string result = new string(
                System.Text.UTF8Encoding.UTF8.GetChars(byteBuf)
            );
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