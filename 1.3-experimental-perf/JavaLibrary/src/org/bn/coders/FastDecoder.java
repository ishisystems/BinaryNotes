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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bn.IDecoder;
import org.bn.metadata.ASN1Metadata;
import org.bn.metadata.ASN1ChoiceMetadata;
import org.bn.metadata.ASN1ElementMetadata;
import org.bn.metadata.ASN1SequenceMetadata;
import org.bn.metadata.BoxedElementTypeDescriptor;
import org.bn.metadata.BoxedTypeDescriptor;
import org.bn.metadata.Descriptor;
import org.bn.metadata.FieldDescriptor;

/**
 * @author jcfinley@users.sourceforge.net
 */
public abstract class FastDecoder
    implements IDecoder
{
    public <T> T decode(InputStream stream,
                        Class<T>    objectClass)
        throws Exception
    {
        DecodedObject decodedTag = decodeTag(stream);

        return (T) decodeClassType(decodedTag, objectClass, null, stream).getValue();
    }

    protected DecodedObject decodeClassType(DecodedObject       decodedTag,
                                            Class               objectClass,
                                            ASN1ElementMetadata elementMetadata,
                                            InputStream         stream)
        throws Exception
    {
        Object objectInstance = objectClass.newInstance();

        DecodedObject decodedObject = null;

        if (objectInstance instanceof CodeableSequence)
        {
            CodeableSequence sequence = (CodeableSequence) objectInstance;
            ASN1SequenceMetadata typeMetadata =
                sequence.getSequenceDescriptor().getSequenceMetadata();
            decodedObject =
                decodeSequence(decodedTag, objectClass, sequence, typeMetadata, elementMetadata, stream);
        }
        else if (objectInstance instanceof CodeableChoice)
        {
            CodeableChoice choice = (CodeableChoice) objectInstance;
            ASN1ChoiceMetadata typeMetadata =
                choice.getChoiceDescriptor().getChoiceMetadata();
            decodedObject =
                decodeChoice(decodedTag, objectClass, choice, typeMetadata, elementMetadata, stream);
        }
        else if (objectInstance instanceof CodeableBoxedType)
        {
            decodedObject =
                decodeBoxedType(decodedTag, objectClass, null, elementMetadata, stream);
        }
        else
        {
            throw new Exception("Not yet implemented.");
        }
        return decodedObject;
    }

    protected DecodedObject decodeSequence(DecodedObject       decodedTag,
                                           Class               objectClass,
                                           ASN1Metadata        typeMetadata,
                                           ASN1ElementMetadata elementMetadata,
                                           InputStream         stream)
        throws Exception
    {
        return decodeSequence(decodedTag, objectClass,
                              (CodeableSequence) objectClass.newInstance(),
                              typeMetadata, elementMetadata, stream);
    }

    protected DecodedObject decodeSequence(DecodedObject       decodedTag,
                                           Class               objectClass,
                                           CodeableSequence    sequence,
                                           ASN1Metadata        typeMetadata,
                                           ASN1ElementMetadata elementMetadata,
                                           InputStream         stream)
        throws Exception
    {
        // !@# doesn't handle nested declarations!
        // !@# need to init

        DecodedObject fieldTag = decodeTag(stream);
        int sizeOfSequence = 0;
        if (fieldTag != null)
        {
            sizeOfSequence += fieldTag.getSize();
        }

        FieldDescriptor[] fieldDescriptors =
            sequence.getSequenceDescriptor().getFieldDescriptors();
        for (int i = 0; i < fieldDescriptors.length; i++)
        {
            Field field = fieldDescriptors[i].getField();
            IValueDecoder decoder = fieldDescriptors[i].getDecoder();
            DecodedObject decodedObject =
                decoder.decode(this,
                               fieldTag,
                               field.getType(),
                               fieldDescriptors[i].getTypeMetadata(),
                               fieldDescriptors[i].getElementMetadata(),
                               stream);

            fieldDescriptors[i].setField(sequence, decodedObject.getValue());

            // !@# optional field?
            sizeOfSequence += decodedObject.getSize();
            if (i < (fieldDescriptors.length - 1))
            {
                fieldTag = decodeTag(stream);
                if (fieldTag != null)
                {
                    sizeOfSequence += fieldTag.getSize();
                }
            }
        }
        return new DecodedObject(sequence, sizeOfSequence);
    }

    protected DecodedObject decodeChoice(DecodedObject       decodedTag,
                                         Class               objectClass,
                                         ASN1Metadata        typeMetadata,
                                         ASN1ElementMetadata elementMetadata,
                                         InputStream         stream)
        throws Exception
    {
        return decodeChoice(decodedTag, objectClass,
                            (CodeableChoice) objectClass.newInstance(),
                            typeMetadata, elementMetadata, stream);
    }

    protected DecodedObject decodeChoice(DecodedObject       decodedTag,
                                         Class               objectClass,
                                         CodeableChoice      choice,
                                         ASN1Metadata        typeMetadata,
                                         ASN1ElementMetadata elementMetadata,
                                         InputStream         stream)
        throws Exception
    {
        // !@# doesn't handle nested declarations!

        DecodedObject value = null;
        FieldDescriptor[] fieldDescriptors =
            choice.getChoiceDescriptor().getFieldDescriptors();
        int i = 0;
        while ((value == null) && (i < fieldDescriptors.length))
        {
            Field field = fieldDescriptors[i].getField();
            IValueDecoder decoder = fieldDescriptors[i].getDecoder();
            value = decoder.decode(this,
                                   decodedTag,
                                   field.getType(),
                                   fieldDescriptors[i].getTypeMetadata(),
                                   fieldDescriptors[i].getElementMetadata(),
                                   stream);
            if (value != null)
            {
                fieldDescriptors[i].setField(choice, value.getValue());
            }
            i++;
        }

        /**
         * !@# Need to support isOptional=true ala Decoder.java
         */
        if (value == null)
        {
            String message = 
                "The choice '" + objectClass.toString() + "' does not have a selected item!";
            throw new  IllegalArgumentException(message);
        }
        return new DecodedObject(choice, value.getSize());
    }

    protected DecodedObject decodeEnum(DecodedObject       decodedTag,
                                       Class               objectClass,
                                       ASN1Metadata        typeMetadata,
                                       ASN1ElementMetadata elementMetadata,
                                       InputStream         stream)
        throws Exception
    {
        if (true) throw new Exception("decodeEnum(): Not yet implemented.");
        return null;
    }

    protected DecodedObject decodeElement(DecodedObject       decodedTag,
                                          Class               objectClass,
                                          ASN1Metadata        typeMetadata,
                                          ASN1ElementMetadata elementMetadata,
                                          InputStream         stream)
        throws Exception
    {
        return decodeClassType(decodedTag, objectClass, elementMetadata, stream);
    }

    protected DecodedObject decodeJavaElement(DecodedObject       decodedTag,
                                              Class               objectClass,
                                              ASN1Metadata        typeMetadata,
                                              ASN1ElementMetadata elementMetadata,
                                              InputStream         stream)
        throws Exception
    {
        if (true) throw new Exception("decodeJavaElement(): Not yet implemented.");
        return null;
    }

    protected DecodedObject decodeBoxedType(DecodedObject       decodedTag,
                                            Class               objectClass,
                                            ASN1Metadata        typeMetadata,
                                            ASN1ElementMetadata elementMetadata,
                                            InputStream         stream)
        throws Exception
    {
        DecodedObject result = null;

        CodeableBoxedType boxedValue = (CodeableBoxedType) objectClass.newInstance();
        BoxedTypeDescriptor descriptor = boxedValue.getBoxedTypeDescriptor();

        Field field = objectClass.getDeclaredField(descriptor.getName());
        IValueDecoder decoder = descriptor.getDecoder();

        DecodedObject value = null;
        if (descriptor instanceof BoxedElementTypeDescriptor)
        {
            BoxedElementTypeDescriptor elementTypeDescriptor =
                (BoxedElementTypeDescriptor) descriptor;
            value = decoder.decode(this,
                                   decodedTag,
                                   field.getType(),
                                   elementTypeDescriptor.getTypeMetadata(),
                                   elementTypeDescriptor.getElementMetadata(),
                                   stream);
        }
        else
        {
            value = decoder.decode(this,
                                   decodedTag,
                                   field.getType(),
                                   descriptor.getTypeMetadata(),
                                   elementMetadata,
                                   stream);
        }

        if (value != null)
        {
            Method setter = descriptor.getSetter();
            setter.invoke(boxedValue, value.getValue());

            result = new DecodedObject(boxedValue);
            result.setSize(value.getSize());
        }
        return result;
    }

    protected abstract DecodedObject decodeBoolean(DecodedObject       decodedTag,
                                                   Class               objectClass,
                                                   ASN1Metadata        typeMetadata,
                                                   ASN1ElementMetadata elementMetadata,
                                                   InputStream         stream)
        throws Exception;

    protected abstract DecodedObject decodeAny(DecodedObject       decodedTag,
                                               Class               objectClass,
                                               ASN1Metadata        typeMetadata,
                                               ASN1ElementMetadata elementMetadata,
                                               InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeNull(DecodedObject       decodedTag,
                                                Class               objectClass,
                                                ASN1Metadata        typeMetadata,
                                                ASN1ElementMetadata elementMetadata,
                                                InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeInteger(DecodedObject       decodedTag,
                                                   Class               objectClass,
                                                   ASN1Metadata        typeMetadata,
                                                   ASN1ElementMetadata elementMetadata,
                                                   InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeReal(DecodedObject       decodedTag,
                                                Class               objectClass,
                                                ASN1Metadata        typeMetadata,
                                                ASN1ElementMetadata elementMetadata,
                                                InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeOctetString(DecodedObject       decodedTag,
                                                       Class               objectClass,
                                                       ASN1Metadata        typeMetadata,
                                                       ASN1ElementMetadata elementMetadata,
                                                       InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeBitString(DecodedObject       decodedTag,
                                                     Class               objectClass,
                                                     ASN1Metadata        typeMetadata,
                                                     ASN1ElementMetadata elementMetadata,
                                                     InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeString(DecodedObject       decodedTag,
                                                  Class               objectClass,
                                                  ASN1Metadata        typeMetadata,
                                                  ASN1ElementMetadata elementMetadata,
                                                  InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeSequenceOf(DecodedObject       decodedTag,
                                                      Class               objectClass,
                                                      ASN1Metadata        typeMetadata,
                                                      ASN1ElementMetadata elementMetadata,
                                                      InputStream         stream)
        throws Exception ;

    protected abstract DecodedObject decodeTag(InputStream stream)
        throws Exception;
}
