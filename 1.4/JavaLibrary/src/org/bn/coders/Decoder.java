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
import org.bn.metadata.ASN1AnyMetadata;
import org.bn.metadata.ASN1ElementMetadata;
import org.bn.metadata.ASN1NullMetadata;


public abstract class Decoder implements IDecoder, IASN1TypesDecoder { 

    public <T> T decode(InputStream stream, Class<T> objectClass) throws Exception {
        ElementInfo elemInfo = new ElementInfo();
        elemInfo.setAnnotatedClass(objectClass);
        Object objectInstance = objectClass.newInstance();
        
        if(objectInstance instanceof IASN1PreparedElement) {
            elemInfo.setPreparedInstance(objectInstance);
            return (T)decodePreparedElement(decodeTag(stream), objectClass,elemInfo, stream).getValue();        
        }
        else {
            elemInfo.setASN1ElementInfoForClass(objectClass);
            return (T)decodeClassType(decodeTag(stream),objectClass,elemInfo, stream).getValue();            
        }        
    }
    
    public DecodedObject decodeClassType(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {
        if(CoderUtils.isImplements(objectClass,IASN1PreparedElement.class)) {
            return decodePreparedElement(decodedTag, objectClass,elementInfo, stream);
        }
        else 
        if(elementInfo.hasPreparedInfo()) {            
            return elementInfo.getPreparedInfo().getTypeMetadata().decode(
                this, decodedTag, objectClass, elementInfo, stream
            );    
        }
        else
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
        if(elementInfo.getAnnotatedClass().equals(byte[].class)) {
            return decodeOctetString(decodedTag, objectClass,elementInfo, stream);
        }
        else
            return null;
    }
    
    public DecodedObject decodePreparedElement(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream ) throws Exception {    
        IASN1PreparedElementData saveInfo = elementInfo.getPreparedInfo();
    
        IASN1PreparedElement preparedInstance = (IASN1PreparedElement) createInstanceForElement(objectClass,elementInfo);
        
        elementInfo.setPreparedInstance(preparedInstance);        
        ASN1ElementMetadata elementDataSave = null;
        if(elementInfo.hasPreparedASN1ElementInfo()) {
            elementDataSave = elementInfo.getPreparedASN1ElementInfo();
        }        
        elementInfo.setPreparedInfo(preparedInstance.getPreparedData());
        elementInfo.setPreparedASN1ElementInfo(preparedInstance.getPreparedData().getASN1ElementInfo());
        if(elementDataSave!=null)
            elementInfo.setPreparedASN1ElementInfo(elementDataSave);
        DecodedObject result= elementInfo.getPreparedInfo().getTypeMetadata().decode(
            this, decodedTag, objectClass, elementInfo, stream
        );    
        elementInfo.setPreparedInfo(saveInfo);
        return result;
    }
             
    
    public void invokeSetterMethodForField(Field field, Object object, Object param, ElementInfo elementInfo) throws Exception {
        if(elementInfo!=null && elementInfo.hasPreparedInfo()) {
            elementInfo.getPreparedInfo().invokeSetterMethod(object, param);
        }
        else {
            Method method = CoderUtils.findSetterMethodForField(field,object.getClass(), param.getClass());
            method.invoke(object, param);
        }
    }


    public void invokeSelectMethodForField(Field field, Object object, Object param, ElementInfo elementInfo) throws Exception {
        if(elementInfo!=null && elementInfo.hasPreparedInfo()) {
            elementInfo.getPreparedInfo().invokeDoSelectMethod(object, param);
        }
        else {    
            Method method = CoderUtils.findDoSelectMethodForField(field,object.getClass(), param.getClass());
            method.invoke(object, param);
        }
    }
    
    
    protected void initDefaultValues(Object object, ElementInfo elementInfo) throws NoSuchMethodException, 
                                                           IllegalAccessException, 
                                                           InvocationTargetException {
        try {
            if(object instanceof IASN1PreparedElement ) {
                ((IASN1PreparedElement)object).initWithDefaults();
            }
            else {
                Method method = object.getClass().getMethod("initWithDefaults",(java.lang.Class[])null);        
                if(method!=null)
                    method.invoke(object,(java.lang.Object[])null);
            }
        }
        catch(NoSuchMethodException ex){};
    }
    
    protected Object createInstanceForElement(Class objectClass, ElementInfo elementInfo) throws InstantiationException, 
                                                                              IllegalAccessException, 
                                                                              NoSuchMethodException, 
                                                                              InvocationTargetException {
        Object result = null;
        if(elementInfo.hasPreparedInstance()) {
            result= elementInfo.getPreparedInstance();
        }
        else {
            if(elementInfo.hasPreparedInfo()) {
                if(elementInfo.getPreparedInfo().isMemberClass() && elementInfo.getParentObject()!=null) {
                    Constructor decl = objectClass.getDeclaredConstructor(elementInfo.getParentObject().getClass());
                    result = decl.newInstance(elementInfo.getParentObject());
                }
            }
            else
            if(objectClass.isMemberClass() && elementInfo.getParentObject()!=null) {
                Constructor decl = objectClass.getDeclaredConstructor(elementInfo.getParentObject().getClass());
                result = decl.newInstance(elementInfo.getParentObject());
            }
        }
        if(result==null)
            result = objectClass.newInstance();
        return result;
    }
        
    public DecodedObject decodeSequence(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception {
        Object sequence = createInstanceForElement(objectClass, elementInfo);
        initDefaultValues(sequence, elementInfo);
        
        DecodedObject fieldTag = decodeTag(stream);
        int sizeOfSequence = 0;
        if(fieldTag!=null)
            sizeOfSequence+=fieldTag.getSize();
        
        Field[] fields = elementInfo.getFields(objectClass);
        
        for(int i=0; i<fields.length; i++) {
            Field field = fields[i];            
            DecodedObject obj = decodeSequenceField(fieldTag,sequence,i, field,stream,elementInfo, true);
            if(obj!=null) {
                sizeOfSequence+=obj.getSize();
                
                boolean isAny = false;
                if(i!=fields.length-1) {
                    isAny = CoderUtils.isAnyField(field, elementInfo);
                }
                
                if(!isAny) {
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
    
    
    protected DecodedObject decodeSequenceField(DecodedObject fieldTag, Object sequenceObj, int fieldIdx, Field field, InputStream stream, ElementInfo elementInfo, boolean optionalCheck) throws  Exception {
        ElementInfo info = new ElementInfo();
        info.setAnnotatedClass(field);        
        
        info.setGenericInfo(field.getGenericType());
        if(elementInfo.hasPreparedInfo()) {
            info.setPreparedInfo(elementInfo.getPreparedInfo().getFieldMetadata(fieldIdx));
        }
        else
            info.setASN1ElementInfoForClass(field);
            
        if(CoderUtils.isMemberClass(field.getType(),info)) {
            info.setParentObject(sequenceObj);
        }            
            
        if(CoderUtils.isNullField(field,info)) {
            return decodeNull(fieldTag,field.getType(),info, stream);
        }
        else {            
            DecodedObject value = decodeClassType(fieldTag,field.getType(),info,stream);
            if(value!=null) {                
                invokeSetterMethodForField(field, sequenceObj, value.getValue(),info);
            }
            else {
                if(optionalCheck)
                    CoderUtils.checkForOptionalField(field, info);
            }
            return value;
        }
    }

    public DecodedObject decodeChoice(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream)  throws Exception {
        Object choice = createInstanceForElement(objectClass,elementInfo);
        DecodedObject value = null;
        
        Field[] fields = elementInfo.getFields(objectClass);
        
        int fieldIdx = 0;
        for ( Field field : fields ) {            
            if(!field.isSynthetic()) {                
                ElementInfo info = new ElementInfo();
                info.setAnnotatedClass(field);
                if(elementInfo.hasPreparedInfo()) {
                    info.setPreparedInfo(elementInfo.getPreparedInfo().getFieldMetadata(fieldIdx));
                }
                else
                    info.setASN1ElementInfoForClass(field);
                if(CoderUtils.isMemberClass(field.getType(),info)) {
                //if(field.getType().isMemberClass()) {
                    info.setParentObject(choice);
                }                
                info.setGenericInfo(field.getGenericType());
                
                value = decodeClassType(decodedTag, field.getType(),info,stream);
                fieldIdx++;
                if(value!=null) {
                    invokeSelectMethodForField(field, choice, value.getValue(),info);
                    break;
                };
                
            }            
        }
        if(value == null && CoderUtils.isOptional(elementInfo)) {
            throw new  IllegalArgumentException ("The choice '" + objectClass.toString() + "' does not have a selected item!");
        }
        else
            return new DecodedObject(choice, value!=null ? value.getSize(): 0);
    }
        
    public DecodedObject decodeEnum(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception  {
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
            invokeSetterMethodForField ( field, result, param.get(null), null) ;
        }        
        return new DecodedObject(result,itemValue.getSize());
    }    

    public DecodedObject decodeElement(DecodedObject decodedTag,Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception  {
        elementInfo.setAnnotatedClass(objectClass);
        return decodeClassType(decodedTag, objectClass,elementInfo, stream);
    }
    
    public DecodedObject decodeBoxedType(DecodedObject decodedTag, Class objectClass, ElementInfo elementInfo, InputStream stream) throws Exception  {    
        Object resultObj = createInstanceForElement(objectClass,elementInfo);
        
        DecodedObject result = new DecodedObject(resultObj);
        
        Field field = null;
        if(elementInfo.hasPreparedInfo()) {            
            field = elementInfo.getPreparedInfo().getValueField();
        }
        else
            field = objectClass.getDeclaredField("value");
        elementInfo.setAnnotatedClass(field);
        elementInfo.setGenericInfo(field.getGenericType());
        //if(field.getType().isMemberClass()) {
        if(CoderUtils.isMemberClass(field.getType(),elementInfo)) {
            elementInfo.setParentObject(resultObj);
        }
        
        boolean isNull = false;
        if(elementInfo.hasPreparedInfo()) {
            isNull = elementInfo.getPreparedInfo().getTypeMetadata() instanceof ASN1NullMetadata;
        }
        else {
            isNull = field.isAnnotationPresent(ASN1Null.class);        
            if(elementInfo.getASN1ElementInfo()==null) {
                elementInfo.setASN1ElementInfoForClass(field);
            }
        }
        
        DecodedObject value = null;
        if(isNull) {
            value = decodeNull(decodedTag, field.getType(),elementInfo,stream);
        }
        else {            
            value = decodeClassType(decodedTag, field.getType(), elementInfo, stream);
            if(value!=null) {
                result.setSize(value.getSize());
                invokeSetterMethodForField(field,resultObj,value.getValue(),elementInfo );
            }
            else
                result = null;
        }
        return result;
    }
}
