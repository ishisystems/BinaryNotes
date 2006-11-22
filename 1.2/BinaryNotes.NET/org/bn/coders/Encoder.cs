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
	
	public abstract class Encoder: IEncoder
	{
        public virtual void encode<T>(T obj, System.IO.Stream stream)
		{
            object ob = (object)obj;
			ElementInfo elemInfo = new ElementInfo(
                obj.GetType(),
                CoderUtils.getAttribute<ASN1Element>(obj.GetType())
            );

			int sizeOfEncodedBytes = encodeClassType(obj, stream, elemInfo);

			if (sizeOfEncodedBytes == 0)
			{
				throw new System.ArgumentException("Unable to find any supported annotation for class type: " + obj.GetType().ToString());
			};
		}
		
		protected virtual int encodeClassType(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
            if (elementInfo.isAttributePresent<ASN1SequenceOf>())
            {
                resultSize += encodeSequenceOf(obj, stream, elementInfo);
            }
            else
			if (elementInfo.isAttributePresent<ASN1Sequence>())
			{
				resultSize += encodeSequence(obj, stream, elementInfo);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Choice>())
			{
				resultSize += encodeChoice(obj, stream, elementInfo);
			}
			else 
            if (elementInfo.isAttributePresent<ASN1BoxedType>())
			{
				resultSize += encodeBoxedType(obj, stream, elementInfo);
			}
			else 
            if (elementInfo.isAttributePresent<ASN1Enum>())
			{
				resultSize += encodeEnum(obj, stream, elementInfo);
			}
			else 
            if (elementInfo.isAttributePresent<ASN1Boolean>())
			{
				resultSize += encodeBoolean(obj, stream, elementInfo);
			}
			else 
            if (elementInfo.isAttributePresent<ASN1Any>())
			{
				resultSize += encodeAny(obj, stream, elementInfo);
			}
			else 
            if (elementInfo.isAttributePresent<ASN1Integer>())
			{
				resultSize += encodeInteger(obj, stream, elementInfo);
			}
            else
            if (elementInfo.isAttributePresent<ASN1Real>())
            {
                resultSize += encodeReal(obj, stream, elementInfo);
            }
            else 
            if (elementInfo.isAttributePresent<ASN1OctetString>())
			{
				resultSize += encodeOctetString(obj, stream, elementInfo);
			}
			else
            if (elementInfo.isAttributePresent<ASN1BitString>())
            {
                resultSize += encodeBitString(obj, stream, elementInfo);
            }
            else
            if (elementInfo.isAttributePresent<ASN1String>())
			{
				resultSize += encodeString(obj, stream, elementInfo);
			}
			else 
			if (elementInfo.isAttributePresent<ASN1Null>())
			{
				resultSize += encodeNull(obj, stream, elementInfo);
			}
			else
            if (elementInfo.isAttributePresent<ASN1Element>())
			{
				resultSize += encodeElement(obj, stream, elementInfo);
			}
			else
				resultSize += encodeCSElement(obj, stream, elementInfo);
			return resultSize;
		}
		
		protected internal virtual int encodeCSElement(object obj, System.IO.Stream stream, ElementInfo info)
		{
			if (obj.GetType().Equals(typeof(string)))
			{
				return encodeString(obj, stream, info);
			}
			else 
            if (obj.GetType().Equals(typeof(int)))
			{
				return encodeInteger(obj, stream, info);
			}
            else
            if (obj.GetType().Equals(typeof(double)))
            {
                return encodeReal(obj, stream, info);
            }
			else 
            if (obj.GetType().Equals(typeof(bool)))
			{
				return encodeBoolean(obj, stream, info);
			}
			else if (obj.GetType().IsArray)
			{
				return encodeOctetString(obj, stream, info);
			}
			else
				return 0;
		}

        protected virtual MethodInfo findPresentMethodForField(PropertyInfo field, object obj)
        {
            string methodName = "is" + field.Name.ToUpper().Substring(0, (1) - (0)) + field.Name.Substring(1) + "Present";
            return obj.GetType().GetMethod(methodName, new System.Type[0]);
        }
						
		protected virtual object invokeGetterMethodForField(PropertyInfo field, object obj)
		{
            MethodInfo method = findPresentMethodForField(field, obj);
            if (method!=null)
            {
                if ((bool)method.Invoke(obj, null))
                {
                    return field.GetValue(obj, null);
                }
                else
                    return null;
            }
            return field.GetValue(obj, null);
		}
		
		protected virtual MethodInfo findSelectedMethodForField(PropertyInfo field, object obj)
		{
			string methodName = "is" + field.Name.ToUpper().Substring(0, (1) - (0)) + field.Name.Substring(1) + "Selected";
			return obj.GetType().GetMethod(methodName, new System.Type[0]);
		}

		
		protected virtual bool invokeSelectedMethodForField(PropertyInfo field, object obj)
		{
			MethodInfo method = findSelectedMethodForField(field, obj);
			return (bool)method.Invoke(obj, null);
		}

        protected bool isOptionalField(PropertyInfo field)
        {
            if (CoderUtils.isAttributePresent<ASN1Element>(field))
            {
                ASN1Element info = CoderUtils.getAttribute<ASN1Element>(field);
                if (info.IsOptional || info.HasDefaultValue)
                    return true;
            }
            return false;
        }
		
		protected virtual void  checkForOptionalField(PropertyInfo field)
		{
            if(!isOptionalField(field))
			    throw new System.ArgumentException("The mandatory field '" + field.Name + "' does not have a value!");
		}
		
		protected virtual int encodeSequence(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
			foreach(PropertyInfo field in obj.GetType().GetProperties())
			{
				resultSize += encodeSequenceField(obj, field, stream, elementInfo);
			}
			return resultSize;
		}
		
		protected virtual int encodeSequenceField(object obj, PropertyInfo field, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
            if (CoderUtils.isAttributePresent<ASN1Null>(field))
			{
				return encodeNull(obj, stream, elementInfo);
			}
			else
			{
				object invokeObjResult = invokeGetterMethodForField(field, obj);
				if (invokeObjResult != null)
				{
					ElementInfo info = new ElementInfo(
                        field,
                        CoderUtils.getAttribute<ASN1Element>(field)
                    );
					resultSize += encodeClassType(invokeObjResult, stream, info);
				}
				else
					checkForOptionalField(field);
			}
			return resultSize;
		}
		
		protected virtual int encodeChoice(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
			PropertyInfo selectedField = null;
			foreach(PropertyInfo field in obj.GetType().GetProperties())
			{
				if (invokeSelectedMethodForField(field, obj))
				{
					selectedField = field;
					object invokeObjResult = invokeGetterMethodForField(field, obj);
                    ElementInfo info = new ElementInfo(
                        field,
                        CoderUtils.getAttribute<ASN1Element>(field)
                    );
                    resultSize += encodeClassType(invokeObjResult, stream, info);
					break;
				}
			}
			if (selectedField == null)
			{
				throw new System.ArgumentException("The choice '" + obj.ToString() + "' does not have a selected item!");
			}
			return resultSize;
		}
		
		
		protected virtual int encodeEnum(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			int resultSize = 0;
			PropertyInfo field = 
                obj.GetType().GetProperty("Value");
			object result = invokeGetterMethodForField(field, obj);
            Type enumClass = null;
			
			foreach(MemberInfo member in obj.GetType().GetMembers())
			{
				if (member is System.Type)
				{
                    Type cls = (Type) member;
                    if(cls.IsEnum) {
                        enumClass = cls;
					    foreach(FieldInfo enumItem in cls.GetFields())
					    {
                            if (CoderUtils.isAttributePresent<ASN1EnumItem>(enumItem))
						    {
							    if (enumItem.Name.Equals(result.ToString(),StringComparison.CurrentCultureIgnoreCase))
							    {
								    elementInfo.AnnotatedClass = enumItem;
								    break;
							    }
						    }
					    }
					    break;
                    }
				}
			}
			resultSize += encodeEnumItem(result, enumClass, stream, elementInfo);
			return resultSize;
		}
		
		protected abstract int encodeEnumItem(object enumConstant, Type enumClass, System.IO.Stream stream, ElementInfo elementInfo);		
		
		protected virtual int encodeElement(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			elementInfo.AnnotatedClass = obj.GetType();
			return encodeClassType(obj, stream, elementInfo);
		}
		
		protected virtual int encodeBoxedType(object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{
			PropertyInfo field = obj.GetType().GetProperty("Value");
			elementInfo.AnnotatedClass = field;
			if (CoderUtils.isAttributePresent<ASN1Null>(field))
			{
				return encodeNull(obj, stream, elementInfo);
			}
			else
			{				
				return encodeClassType(invokeGetterMethodForField(field, obj), stream, elementInfo);
			}
		}
		
		protected abstract int encodeBoolean(object obj, System.IO.Stream stream, ElementInfo elementInfo);
		
		protected abstract int encodeAny(object obj, System.IO.Stream stream, ElementInfo elementInfo);
		
		protected abstract int encodeNull(object obj, System.IO.Stream stream, ElementInfo elementInfo);
		
		protected abstract int encodeInteger(object obj, System.IO.Stream steam, ElementInfo elementInfo);

        protected abstract int encodeReal(object obj, System.IO.Stream steam, ElementInfo elementInfo);

		protected abstract int encodeOctetString(object obj, System.IO.Stream steam, ElementInfo elementInfo);

        protected abstract int encodeBitString(object obj, System.IO.Stream steam, ElementInfo elementInfo);
		
		protected abstract int encodeString(object obj, System.IO.Stream steam, ElementInfo elementInfo);
		
		protected abstract int encodeSequenceOf(object obj, System.IO.Stream steam, ElementInfo elementInfo);
	}
}