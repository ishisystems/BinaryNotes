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

package org.bn.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bn.coders.IValueDecoder;
import org.bn.coders.IValueEncoder;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class BoxedTypeDescriptor
    extends Descriptor
{
    private String        name;
    private Field         field;
    private Method        getter;
    private Method        setter;
    private ASN1Metadata  typeMetadata;
    private IValueEncoder encoder;
    private IValueDecoder decoder;

    public BoxedTypeDescriptor(String        name,
                               Field         field,
                               Method        getter,
                               Method        setter,
                               ASN1Metadata  typeMetadata,
                               IValueEncoder encoder,
                               IValueDecoder decoder)
    {
        this.name         = name;
        this.field        = field;
        this.getter       = getter;
        this.setter       = setter;
        this.typeMetadata = typeMetadata;
        this.encoder      = encoder;
        this.decoder      = decoder;
    }

    public String getName()
    {
        return name;
    }

    public Field getField()
    {
        return field;
    }

    public Method getGetter()
    {
        return getter;
    }

    public Method getSetter()
    {
        return setter;
    }

    public ASN1Metadata getTypeMetadata()
    {
        return typeMetadata;
    }

    public IValueEncoder getEncoder()
    {
        return encoder;
    }

    public IValueDecoder getDecoder()
    {
        return decoder;
    }
}