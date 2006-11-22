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
using System.IO;
using org.bn.attributes;

namespace org.bn.coders
{	
	public abstract class Decoder : IDecoder
	{		
        public virtual T decode<T>(Stream stream) {
            Type objectClass = typeof(T);
            ElementInfo elemInfo = 
                new ElementInfo(
                    objectClass,
                    CoderUtils.getAttribute<ASN1Element>(objectClass)
                );
            return (T)decodeClassType(decodeTag(stream),objectClass,elemInfo, stream).Value;
        }
		
		protected virtual DecodedObject<object> decodeClassType(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
            if (elementInfo.isAttributePresent<ASN1SequenceOf>())
            {
                return decodeSequenceOf(decodedTag, objectClass, elementInfo, stream);
            }
            else
            if (elementInfo.isAttributePresent<ASN1Sequence>())
			{
				return decodeSequence(decodedTag, objectClass, elementInfo, stream);
			}
            else
            if (elementInfo.isAttributePresent<ASN1Choice>())
			{
				return decodeChoice(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1BoxedType>())
			{
				return decodeBoxedType(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Enum>())
			{
				return decodeEnum(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Boolean>())
			{
				return decodeBoolean(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Any>())
			{
				return decodeAny(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Integer>())
			{
				return decodeInteger(decodedTag, objectClass, elementInfo, stream);
			}
            else
            if (elementInfo.isAttributePresent<ASN1Real>())
            {
                return decodeReal(decodedTag, objectClass, elementInfo, stream);
            }
			else
            if (elementInfo.isAttributePresent<ASN1OctetString>())
			{
				return decodeOctetString(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1BitString>())
            {
                return decodeBitString(decodedTag, objectClass, elementInfo, stream);
            }
            else
            if (elementInfo.isAttributePresent<ASN1String>())
			{
				return decodeString(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Null>())
			{
				return decodeNull(decodedTag, objectClass, elementInfo, stream);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Element>())
			{
				return decodeElement(decodedTag, objectClass, elementInfo, stream);
			}
			else
				return decodeJavaElement(decodedTag, objectClass, elementInfo, stream);
		}
		
		protected virtual DecodedObject<object> decodeJavaElement(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			if (elementInfo.AnnotatedClass.Equals(typeof(string)))
			{
				return decodeString(decodedTag, objectClass, elementInfo, stream);
			}
			else if (elementInfo.AnnotatedClass.Equals(typeof(int)))
			{
				return decodeInteger(decodedTag, objectClass, elementInfo, stream);
			}
            else if (elementInfo.AnnotatedClass.Equals(typeof(long)))
            {
                return decodeInteger(decodedTag, objectClass, elementInfo, stream);
            }
            else if (elementInfo.AnnotatedClass.Equals(typeof(double)))
            {
                return decodeReal(decodedTag, objectClass, elementInfo, stream);
            }
			else if (elementInfo.AnnotatedClass.Equals(typeof(bool)))
			{
				return decodeBoolean(decodedTag, objectClass, elementInfo, stream);
			}
			/*if(objectClass.isArray()) {
			return decodeOctetString(elementInfo, stream);
			}
			else*/
			else
				return null;
		}
					
		protected virtual void  invokeSetterMethodForField(PropertyInfo field, object obj, object param)
		{
            field.SetValue(obj, param, null);
		}

        protected virtual MethodInfo findSelectMethodForField(PropertyInfo field, object obj)
        {
            string methodName = "select" + field.Name.ToUpper().Substring(0, (1) - (0)) + field.Name.Substring(1);
            return obj.GetType().GetMethod(methodName);
        }

        protected virtual void invokeSelectMethodForField(PropertyInfo field, object obj, object param)
        {
            MethodInfo method = findSelectMethodForField(field, obj);
            method.Invoke(obj, new object[] { param });
        }

        protected bool isOptionalField(PropertyInfo field)
        {
            if (CoderUtils.isAttributePresent<ASN1Element>(field))
            {
                ASN1Element info = CoderUtils.getAttribute<ASN1Element>(field);
                if (info.IsOptional || info.HasDefaultValue )
                    return true;
            }
            return false;
        }


        protected virtual void checkForOptionalField(PropertyInfo field)
        {
            if(!isOptionalField(field))
                throw new System.ArgumentException("The mandatory field '" + field.Name + "' does not have a value!");
        }

        protected virtual void initDefaultValues(object obj) {
            try {
                string methodName = "initWithDefaults";
                MethodInfo method = obj.GetType().GetMethod(methodName);
                method.Invoke(obj,null);
            }
            catch(Exception ){};
        }

		
		protected virtual DecodedObject<object> decodeSequence(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			object sequence = Activator.CreateInstance(objectClass);
            initDefaultValues(sequence);
			DecodedObject<object> fieldTag = decodeTag(stream);
			int sizeOfSequence = 0;
			if (fieldTag != null)
				sizeOfSequence += fieldTag.Size;
			PropertyInfo[] fields = 
                objectClass.GetProperties();
			for (int i = 0; i < fields.Length; i++)
			{
				PropertyInfo field = fields[i];
				DecodedObject<object> obj = decodeSequenceField(
                    fieldTag, sequence, field, stream, elementInfo,true
                );
				if (obj != null)
				{
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
			return new DecodedObject<object>(sequence, sizeOfSequence);
		}
		
		protected virtual DecodedObject<object> decodeSequenceField(DecodedObject<object> fieldTag, object sequenceObj, PropertyInfo field, System.IO.Stream stream, ElementInfo elementInfo, bool checkOptional)
		{
            ElementInfo info = new ElementInfo(
                field,
                CoderUtils.getAttribute<ASN1Element>(field)
            );
            
            //info.setGenericInfo(field.getGenericType());
			if (CoderUtils.isAttributePresent<ASN1Null>(field))
			{
				return decodeNull(fieldTag, field.PropertyType, info, stream);
			}
			else
			{
                DecodedObject<object> val = decodeClassType(fieldTag, field.PropertyType, info, stream);
				if (val != null)
				{
					invokeSetterMethodForField(field, sequenceObj, val.Value);
				}
				else
				{
                    if(checkOptional)
					    checkForOptionalField(field);
				}
				return val;
			}
		}
		
		protected virtual DecodedObject<object> decodeChoice(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			object choice = System.Activator.CreateInstance(objectClass);
			DecodedObject<object> val = null;
			foreach(PropertyInfo field in objectClass.GetProperties())
			{
                ElementInfo info = new ElementInfo(
                    field,
                    CoderUtils.getAttribute<ASN1Element>(field)
                );
                //info.setGenericInfo(field.getGenericType());
                val = decodeClassType(decodedTag, field.PropertyType, info, stream);
				if (val != null)
				{
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
				return new DecodedObject<object>(choice, val != null?val.Size:0);
		}
		
		protected virtual DecodedObject<object> decodeEnum(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			object result = System.Activator.CreateInstance(objectClass);
            Type enumClass = null;
            foreach (MemberInfo member in objectClass.GetMembers())
            {
                if (member is System.Type)
                {
                    Type cls = (Type)member;
                    if (cls.IsEnum)
                    {
                        enumClass = cls;
                        break;
                    }
                }
            };

			System.Reflection.PropertyInfo field = objectClass.GetProperty("Value");


			DecodedObject<object> itemValue = decodeEnumItem(decodedTag, field.PropertyType, enumClass, elementInfo, stream);
			
            System.Reflection.FieldInfo param = null;
			if (itemValue != null)
			{
		        foreach(FieldInfo enumItem in enumClass.GetFields())
		        {
                    if (CoderUtils.isAttributePresent<ASN1EnumItem>(enumItem))
			        {
					    ASN1EnumItem meta =
                            CoderUtils.getAttribute<ASN1EnumItem>(enumItem);
					    if (meta.Tag.Equals(itemValue.Value))
					    {
						    param = enumItem;
						    break;
					    }
			        }
		        }
				invokeSetterMethodForField(field, result, param.GetValue(null));
			}
			return new DecodedObject<object>(result, itemValue.Size);
		}
		
		protected abstract DecodedObject<object> decodeEnumItem(DecodedObject<object> decodedTag, System.Type objectClass, System.Type enumClass, ElementInfo elementInfo, System.IO.Stream stream);
		
		
		protected virtual DecodedObject<object> decodeElement(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			elementInfo.AnnotatedClass = objectClass;
			return decodeClassType(decodedTag, objectClass, elementInfo, stream);
		}
		
		protected virtual DecodedObject<object> decodeBoxedType(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream)
		{
			object resultObj = System.Activator.CreateInstance(objectClass);
			
			DecodedObject<object> result = new DecodedObject<object>(resultObj);
			
			PropertyInfo field = objectClass.GetProperty("Value");
			elementInfo.AnnotatedClass = field;
			//elementInfo.setGenericInfo(field.getGenericType());
			
			DecodedObject<object> val = null;
            if (CoderUtils.isAttributePresent<ASN1Null>(field))
            {
				val = decodeNull(decodedTag, field.PropertyType, elementInfo, stream);
			}
			else
			{
                val = decodeClassType(decodedTag, field.PropertyType, elementInfo, stream);
				if (val != null)
				{
					result.Size = val.Size;
					invokeSetterMethodForField(field, resultObj, val.Value);
				}
				else
					result = null;
			}
			return result;
		}

        protected abstract DecodedObject<object> decodeBoolean(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeAny(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeNull(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeInteger(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeReal(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeOctetString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeBitString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeString(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeSequenceOf(DecodedObject<object> decodedTag, System.Type objectClass, ElementInfo elementInfo, System.IO.Stream stream);

        protected abstract DecodedObject<object> decodeTag(System.IO.Stream stream);


	}
}