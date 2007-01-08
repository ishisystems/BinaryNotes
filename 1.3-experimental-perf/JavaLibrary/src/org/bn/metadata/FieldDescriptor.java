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

import org.bn.coders.IValueDecoder;
import org.bn.coders.IValueEncoder;

/**
 * @author jcfinley@users.sourceforge.net
 */
abstract public class FieldDescriptor
    extends ItemDescriptor
{
    abstract public Object getField(Object targetObject); 

    abstract public void setField(Object targetObject,
                                  Object valueObject);

    private String       name;
    private Field        field;
    private ASN1Metadata typeMetadata;

    public FieldDescriptor(String              name,
                           Field               field,
                           ASN1Metadata        typeMetadata,
                           ASN1ElementMetadata elementMetadata,
                           IValueEncoder       encoder,
                           IValueDecoder       decoder)
    {
        super(elementMetadata, encoder, decoder);

        this.name         = name;
        this.field        = field;
        this.typeMetadata = typeMetadata;
    }

    public String getName()
    {
        return name;
    }

    public Field getField()
    {
        return field;
    }

    public ASN1Metadata getTypeMetadata()
    {
        return typeMetadata;
    }
}