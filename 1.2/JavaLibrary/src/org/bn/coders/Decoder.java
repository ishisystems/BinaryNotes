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

import java.io.InputStream;

import java.lang.reflect.AnnotatedElement;

import java.lang.reflect.Constructor;

import org.bn.IDecoder;
import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.bn.annotations.*;


public abstract class Decoder implements IDecoder {    
    public <T> T decode(InputStream stream, Class<T> objectClass) throws Exception {

        ElementInfo elemInfo = 
            new ElementInfo(
                objectClass, 
                objectClass.getAnnotation(ASN1Element.class)
            );
        return (T)decodeClassType(decodeTag(stream),objectClass,elemInfo, stream).getValue();
    }
    
    protected DecodedObject decodeClassType(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1SequenceOf.class) ) {
            return decodeSequenceOf(decodedTag, objectClass,elementInfo, stream);
        }        
        else    
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Sequence.class) ) {
            return decodeSequence(decodedTag,objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Choice.class) ) {
            return decodeChoice(decodedTag,objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1BoxedType.class) ) {
            return decodeBoxedType(decodedTag,objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Enum.class) ) {
            return decodeEnum(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Boolean.class) ) {            
            return decodeBoolean(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Any.class) ) {
            return decodeAny(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Integer.class) ) {
            return decodeInteger(decodedTag, objectClass,elementInfo, stream);
        }        
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Real.class) ) {
            return decodeReal(decodedTag, objectClass,elementInfo, stream);
        }        
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1OctetString.class) ) {
            return decodeOctetString(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1BitString.class) ) {
            return decodeBitString(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1String.class) ) {
            return decodeString(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Null.class) ) {
            return decodeNull(decodedTag, objectClass,elementInfo, stream);        
        }
        else
        if( elementInfo.getAnnotatedClass().isAnnotationPresent(ASN1Element.class) ) {
            return decodeElement(decodedTag, objectClass,elementInfo, stream);
        }
        else
            return decodeJavaElement(decodedTag, objectClass,elementInfo, stream);
    }
    
    protected DecodedObject decodeJavaElement(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream ) throws Exception {
        if(elementInfo.getAnnotatedClass().equals(String.class)) {
            return decodeString(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if(elementInfo.getAnnotatedClass().equals(Integer.class)) {
            return decodeInteger(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if(elementInfo.getAnnotatedClass().equals(Long.class)) {
            return decodeInteger(decodedTag, objectClass,elementInfo, stream);
        }
        else
        if(elementInfo.getAnnotatedClass().equals(Double.class)) {
            return decodeReal(decodedTag, objectClass,elementInfo, stream);
        }
        else        
        if(elementInfo.getAnnotatedClass().equals(Boolean.class)) {
            return decodeBoolean(decodedTag, objectClass,elementInfo, stream);
        }        
        else
        /*if(objectClass.isArray()) {
            return decodeOctetString(elementInfo, stream);
        }
        else*/
            return null;
    }
    
    protected Method findMethodForField(String methodName, Object object, Object param) throws NoSuchMethodException {
        try {
            return object.getClass().getMethod(methodName, new Class[] {param.getClass()});
        }
        catch(NoSuchMethodException ex) {
            Method[] methods = object.getClass().getMethods();
            for(Method method : methods) {
                if(method.getName().equalsIgnoreCase(methodName)) {
                    return method;
                }
            }
            throw ex;
        }
    }    
    
    protected Method findSetterMethodForField(Field field, Object object, Object param) throws NoSuchMethodException {
        String methodName = "set"+field.getName().toUpperCase().substring(0,1)+field.getName().substring(1);
        return findMethodForField(methodName, object, param);
    }
    
    protected void invokeSetterMethodForField(Field field, Object object, Object param) throws Exception {
        Method method = findSetterMethodForField(field,object, param);
        method.invoke(object, param);
    }

    protected Method findSelectedMethodForField(Field field, Object object,Object param) throws NoSuchMethodException {
        String methodName = "select"+field.getName().toUpperCase().substring(0,1)+field.getName().substring(1);
        return findMethodForField(methodName, object, param);
    }

    protected void invokeSelectMethodForField(Field field, Object object, Object param) throws NoSuchMethodException, 
                                                                      IllegalAccessException, 
                                                                      InvocationTargetException {
        Method method = findSelectedMethodForField(field,object, param);
        method.invoke(object, param);
    }
    
    protected boolean isOptionalField(Field field) {
        if( field.isAnnotationPresent(ASN1Element.class) ) {
            ASN1Element info = field.getAnnotation(ASN1Element.class);
            if(info.isOptional() || info.hasDefaultValue())
                return true;
        }        
        return false;
    }
    
    
    protected void checkForOptionalField(Field field) throws Exception {
        if( isOptionalField(field) )
                return;
        throw new  IllegalArgumentException ("The mandatory field '" + field.getName() + "' does not have a value!");
    }
    
    protected void initDefaultValues(Object object) throws NoSuchMethodException, 
                                                           IllegalAccessException, 
                                                           InvocationTargetException {
        try {
            Method method = object.getClass().getMethod("initWithDefaults",(java.lang.Class[])null);        
            if(method!=null)
                method.invoke(object,(java.lang.Object[])null);
        }
        catch(NoSuchMethodException ex){};
    }
    
    protected Object createInstanceForElement(Class objectClass, ElementInfo elementInfo) throws InstantiationException, 
                                                                              IllegalAccessException, 
                                                                              NoSuchMethodException, 
                                                                              InvocationTargetException {
        if(objectClass.isMemberClass() && elementInfo.getParentObject()!=null) {
            Constructor decl = objectClass.getDeclaredConstructor(elementInfo.getParentObject().getClass());
            return decl.newInstance(elementInfo.getParentObject());
        }
        else {
            return objectClass.newInstance();
        }
    }
        
    protected DecodedObject decodeSequence(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {        
        Object sequence = createInstanceForElement(objectClass,elementInfo);
        initDefaultValues(sequence);
        
        DecodedObject fieldTag = decodeTag(stream);
        int sizeOfSequence = 0;
        if(fieldTag!=null)
            sizeOfSequence+=fieldTag.getSize();
        
        Field[] fields =objectClass.getDeclaredFields();
        for(int i=0; i<fields.length; i++) {        
            Field field = fields[i];            
            DecodedObject obj = decodeSequenceField(fieldTag,sequence,field,stream,elementInfo, true);
            if(obj!=null) {
                sizeOfSequence+=obj.getSize();
                
                if(i!=fields.length-1 && fields[i+1].isAnnotationPresent(ASN1Any.class)) {
                }
                else {
                    if(i<fields.length-1) {
                        fieldTag = decodeTag(stream);
                        if(fieldTag!=null) {                        
                            sizeOfSequence+=fieldTag.getSize();
                        }
                        else
                            break;
                    }
                }
            };
        }
        return new DecodedObject(sequence,sizeOfSequence);
    }
    

    protected DecodedObject decodeSequenceField(DecodedObject fieldTag, Object sequenceObj, Field field, InputStream stream, ElementInfo elementInfo, boolean optionalCheck) throws  Exception {
        ElementInfo info = new ElementInfo(field, field.getAnnotation(ASN1Element.class));
        if(field.getType().isMemberClass()) {
            info.setParentObject(sequenceObj);
        }
        info.setGenericInfo(field.getGenericType());
        if(field.isAnnotationPresent(ASN1Null.class)) {            
            return decodeNull(fieldTag,field.getType(),info, stream);
        }
        else {
            DecodedObject value = decodeClassType(fieldTag,field.getType(),info,stream);
            if(value!=null) {                
                invokeSetterMethodForField(field, sequenceObj, value.getValue());
            }
            else {
                if(optionalCheck)
                    checkForOptionalField(field);
            }
            return value;
        }
    }

    protected DecodedObject decodeChoice(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream)  throws Exception {
        Object choice = createInstanceForElement(objectClass,elementInfo);
        DecodedObject value = null;
        for ( Field field : objectClass.getDeclaredFields() ) {
            if(!field.isSynthetic()) {
                ElementInfo info = new ElementInfo(field, field.getAnnotation(ASN1Element.class));
                if(field.getType().isMemberClass()) {
                    info.setParentObject(choice);
                }                
                info.setGenericInfo(field.getGenericType());            
                value = decodeClassType(decodedTag, field.getType(),info,stream);
                if(value!=null) {
                    invokeSelectMethodForField(field, choice, value.getValue());
                    break;
                };
            }            
        }
        if(value == null && elementInfo.getElement()!=null && !elementInfo.getElement().isOptional()) {
            throw new  IllegalArgumentException ("The choice '" + objectClass.toString() + "' does not have a selected item!");
        }
        else
            return new DecodedObject(choice, value!=null ? value.getSize(): 0);
    }
        
    protected DecodedObject decodeEnum(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception  {
        Object result = objectClass.newInstance();
        Field field = objectClass.getDeclaredField("value");

        Class enumClass = null;
        for(Class cls : objectClass.getDeclaredClasses()) {
            if(cls.isEnum()) {
                enumClass = cls;
                break;
            }
        };

        DecodedObject itemValue = decodeEnumItem(decodedTag, field.getType(),enumClass, elementInfo, stream );
        
        Field param = null;
        if(itemValue!=null) {
            for(Field enumItem: enumClass.getDeclaredFields()) {
                if(enumItem.isAnnotationPresent(ASN1EnumItem.class)) {
                    ASN1EnumItem meta = enumItem.getAnnotation(ASN1EnumItem.class);
                    if(meta.tag() == (Integer)itemValue.getValue()) {
                        param = enumItem;
                        break;
                    }
                }
            }
            invokeSetterMethodForField ( field, result, param.get(null)) ;
        }        
        return new DecodedObject(result,itemValue.getSize());
    }
        
    protected abstract DecodedObject decodeEnumItem(DecodedObject decodedTag,Class objectClass, Class enumClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    

    protected DecodedObject decodeElement(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception  {
        elementInfo.setAnnotatedClass(objectClass);
        return decodeClassType(decodedTag, objectClass,elementInfo, stream);
    }
    
    protected DecodedObject decodeBoxedType(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception  {    
        Object resultObj = createInstanceForElement(objectClass,elementInfo);
        
        DecodedObject result = new DecodedObject(resultObj);
        
        Field field = objectClass.getDeclaredField("value");
        elementInfo.setAnnotatedClass(field);
        elementInfo.setGenericInfo(field.getGenericType());
        if(field.getType().isMemberClass()) {
            elementInfo.setParentObject(resultObj);
        }                
        
        
        DecodedObject value = null;
        if(field.isAnnotationPresent(ASN1Null.class)) {
            value = decodeNull(decodedTag, field.getType(),elementInfo,stream);
        }
        else {            
            value = decodeClassType(decodedTag, field.getType(), elementInfo, stream);
            if(value!=null) {
                result.setSize(value.getSize());
                invokeSetterMethodForField(field,resultObj,value.getValue());
            }
            else
                result = null;
        }
        return result;
    }

    protected abstract DecodedObject  decodeBoolean(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception;

    protected abstract DecodedObject  decodeAny(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeNull(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeInteger(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeReal(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeOctetString(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeBitString(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeString(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;

    protected abstract DecodedObject  decodeSequenceOf(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception ;
    
    protected abstract DecodedObject decodeTag(InputStream stream) throws Exception ;
    
    //
}
