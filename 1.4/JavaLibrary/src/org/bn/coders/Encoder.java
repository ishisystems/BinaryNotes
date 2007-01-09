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

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.lang.reflect.Type;

import java.util.LinkedList;

import java.util.List;

import org.bn.IEncoder;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.utils.ReverseByteArrayOutputStream;

public abstract class Encoder<T> implements IEncoder<T>, IASN1TypesEncoder {
    
    public void encode(T object, OutputStream stream) throws Exception {
        ElementInfo elemInfo = new ElementInfo();
        elemInfo.setAnnotatedClass(object.getClass());
        //elemInfo.setASN1ElementInfo(object.getClass().getAnnotation(ASN1Element.class));
        elemInfo.setASN1ElementInfoForClass(object.getClass());
        int sizeOfEncodedBytes = encodeClassType(object, stream, elemInfo);
        if( sizeOfEncodedBytes == 0) {
           throw new IllegalArgumentException("Unable to find any supported annotation for class type: " + object.getClass().toString());
        };
        
    }

    public int encodeClassType(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        if(elementInfo.isPreparedAnnotatedElement()) {
            IASN1PreparedElement element = elementInfo.getPreparedAnnotatedElement();
            resultSize+=element.encode(this,stream, elementInfo);
        }
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1SequenceOf.class) ) {
            resultSize+=encodeSequenceOf(object, stream, elementInfo);
        }        
        else        
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Sequence.class) ) {
            resultSize+=encodeSequence(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Choice.class) ) {
            resultSize+=encodeChoice(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1BoxedType.class) ) {
            resultSize+=encodeBoxedType(object, stream, elementInfo);
        }        
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Enum.class) ) {
            resultSize+=encodeEnum(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Boolean.class) ) {            
            resultSize+=encodeBoolean(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Any.class) ) {
            resultSize+=encodeAny(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Integer.class) ) {
            resultSize+=encodeInteger(object, stream, elementInfo);
        }        
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Real.class) ) {
            resultSize+=encodeReal(object, stream, elementInfo);
        }        
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1OctetString.class) ) {
            resultSize+=encodeOctetString(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1BitString.class) ) {
            resultSize+=encodeBitString(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1String.class) ) {
            resultSize+=encodeString(object, stream, elementInfo);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Null.class) ) {
            resultSize+=encodeNull(object, stream, elementInfo);        
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Element.class) ) {
            resultSize+=encodeElement(object, stream, elementInfo);
        }
        else
            resultSize+=encodeJavaElement(object, stream, elementInfo);
        return resultSize;
    }
    
    protected int encodeJavaElement(Object object, OutputStream stream, ElementInfo info ) throws Exception {
        if(object.getClass().equals(String.class)) {
            return encodeString(object,stream,info);
        }
        else
        if(object.getClass().equals(Integer.class)) {
            return encodeInteger(object,stream,info);
        }
        else
        if(object.getClass().equals(Long.class)) {
            return encodeInteger(object,stream,info);
        }
        else
        if(object.getClass().equals(Double.class)) {
            return encodeReal(object,stream,info);
        }
        else
        if(object.getClass().equals(Boolean.class)) {
            return encodeBoolean(object,stream,info);
        }        
        else
        if(object.getClass().isArray()) {
            return encodeOctetString(object,stream,info);
        }
        else
            return 0;
    }
    
    
    protected Method findGetterMethodForField(Field field, Object object) throws NoSuchMethodException {
        String getterMethodName = "get"+field.getName().toUpperCase().substring(0,1)+field.getName().substring(1);
        return object.getClass().getMethod(getterMethodName,(java.lang.Class[])null);
    }
    
    protected Object invokeGetterMethodForField(Field field, Object object) throws NoSuchMethodException, 
                                                                      IllegalAccessException, 
                                                                      InvocationTargetException {
        Method method = findGetterMethodForField(field,object);
        return method.invoke(object, (java.lang.Object[])null);
    }

    protected Method findSelectedMethodForField(Field field, Object object) throws NoSuchMethodException {
        String methodName = "is"+field.getName().toUpperCase().substring(0,1)+field.getName().substring(1)+"Selected";
        return object.getClass().getMethod(methodName,(java.lang.Class[])null);
    }

    protected boolean invokeSelectedMethodForField(Field field, Object object) throws NoSuchMethodException, 
                                                                      IllegalAccessException, 
                                                                      InvocationTargetException {
        Method method = findSelectedMethodForField(field,object);
        return (Boolean)method.invoke(object, (java.lang.Object[])null);
    }
    
    protected void checkForOptionalField(Field field) throws Exception {
        if(!isOptionalField(field))
            throw new  IllegalArgumentException ("The mandatory field '" + field.getName() + "' does not have a value!");
    }    
    
    protected boolean isOptionalField(Field field) {
        if( field.isAnnotationPresent(ASN1Element.class) ) {
            ASN1Element info = field.getAnnotation(ASN1Element.class);
            if(info.isOptional() || info.hasDefaultValue())
                return true;
        }        
        return false;
    }
    
    protected int encodeSequence(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception {
        int resultSize = 0;
        for ( Field field : object.getClass().getDeclaredFields() ) {
            resultSize += encodeSequenceField(object,field,stream,elementInfo);
        }
        return resultSize;
    }

    protected int encodeSequenceField(Object object, Field field, OutputStream stream, ElementInfo elementInfo) throws  Exception {
        int resultSize = 0;
        if(field.isSynthetic())
            return resultSize;
        if(field.isAnnotationPresent(ASN1Null.class)) {
            return encodeNull(object,stream,elementInfo);
        }
        else {
            Object invokeObjResult = invokeGetterMethodForField(field,object);
            if(invokeObjResult!=null) {
                ElementInfo info = new ElementInfo();
                info.setAnnotatedClass(field);
                //info.setASN1ElementInfo(field.getAnnotation(ASN1Element.class));
                info.setASN1ElementInfoForClass(field);
                resultSize += encodeClassType(invokeObjResult, stream, info);
            }
            else
                checkForOptionalField(field);
        }
        return resultSize;
    }
    
    protected boolean isSelectedChoiceItem(Field field, Object object) throws NoSuchMethodException, 
                                                                 IllegalAccessException, 
                                                                 InvocationTargetException {
        if(invokeSelectedMethodForField(field,object)) {
            return true;
        }
        else
            return false;
    }
    
    protected ElementInfo getChoiceSelectedElement(Object object) throws NoSuchMethodException, 
                                                                       IllegalAccessException, 
                                                                       InvocationTargetException {
        ElementInfo info = null;                                                               
        for ( Field field : object.getClass().getDeclaredFields() ) {
            if(!field.isSynthetic()) {
                if(isSelectedChoiceItem(field,object)) {
                    //selectedField = field;
                    //Object invokeObjResult = invokeGetterMethodForField(field,object);
                    info = new ElementInfo();
                    info.setAnnotatedClass(field);
                    //info.setASN1ElementInfo(field.getAnnotation(ASN1Element.class));
                    info.setASN1ElementInfoForClass(field);
                    break;
                }
            }
        }
        if(info==null) {
            throw new  IllegalArgumentException ("The choice '" + object.toString() + "' does not have a selected item!");
        }        
        return info;
    }

    protected int encodeChoice(Object object, OutputStream stream, ElementInfo elementInfo)  throws Exception {
        int resultSize = 0;
        ElementInfo info = getChoiceSelectedElement(object);
        Object invokeObjResult = invokeGetterMethodForField((Field)info.getAnnotatedClass(),object);
        resultSize+=encodeClassType(invokeObjResult, stream, info);
        return resultSize;
    }
    
        
    public int encodeEnum(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception  {
        int resultSize = 0;
        Field field = object.getClass().getDeclaredField("value");
        Object result = invokeGetterMethodForField( field, object);
       
        Class enumClass = null;
        for(Class cls : object.getClass().getDeclaredClasses()) {
            if(cls.isEnum()) {
                for(Field enumItem: cls.getDeclaredFields()) {
                    if(enumItem.isAnnotationPresent(ASN1EnumItem.class)) {
                        if(enumItem.getName().equals(result.toString())) {
                            elementInfo.setAnnotatedClass(enumItem);
                            break;
                        }
                    }
                }
                enumClass = cls;
                break;
            }
        }        
        resultSize+=encodeEnumItem(result, enumClass, stream, elementInfo);
        return resultSize;
    }
    
    protected int encodeElement(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception  {
        elementInfo.setAnnotatedClass(object.getClass());
        return encodeClassType(object,stream,elementInfo);
    }
    
    protected int encodeBoxedType(Object object, OutputStream stream, ElementInfo elementInfo) throws Exception  {
        Field field = object.getClass().getDeclaredField("value");
        elementInfo.setAnnotatedClass(field);
        if(field.isAnnotationPresent(ASN1Null.class)) {
            return encodeNull(object,stream,elementInfo);
        }
        else {
            
            return encodeClassType(invokeGetterMethodForField(field,object), stream, elementInfo);
        }
    }
}
