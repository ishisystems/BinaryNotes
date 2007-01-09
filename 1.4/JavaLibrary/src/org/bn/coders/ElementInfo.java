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

import java.lang.reflect.AnnotatedElement;

import java.lang.reflect.Type;

import org.bn.annotations.ASN1Element;

class ElementInfo {
    private ASN1Element element;
    private AnnotatedElement  annotatedClass, parentAnnotated;
    private Type genericInfo;
    private Object parentObject;

    public ElementInfo() {            
    }
    
    /*public ElementInfo(AnnotatedElement  annotatedObj) {
        this(annotatedObj,null);
    }

    public ElementInfo(AnnotatedElement  annotatedObj, ASN1Element element) {
        setAnnotatedClass(annotatedObj);
        setElement(element);
    }*/
    
    public ASN1Element getASN1ElementInfo() {
        return element;
    }

    public void setASN1ElementInfo(ASN1Element element) {
        this.element = element;
    }

    public AnnotatedElement  getAnnotatedClass() {
        return this.annotatedClass;
    }
    
    public void setAnnotatedClass(AnnotatedElement cls) {
        this.annotatedClass = cls;
    }
    
    public void setGenericInfo(Type info) {
        this.genericInfo = info;
    }
    
    public Type getGenericInfo() {
        return this.genericInfo;
    }

    public AnnotatedElement getParentAnnotated() {
        return parentAnnotated;
    }

    public void setParentAnnotated(AnnotatedElement parentAnnotated) {
        this.parentAnnotated = parentAnnotated;
    }

    public Object getParentObject() {
        return parentObject;
    }

    public void setParentObject(Object parentObject) {
        this.parentObject = parentObject;
    }
}
