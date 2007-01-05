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

import org.bn.IEncoder;
import org.bn.metadata.ASN1ElementMetadata;
import org.bn.metadata.ASN1Metadata;
import org.bn.metadata.ChoiceDescriptor;
import org.bn.metadata.FieldDescriptor;
import org.bn.utils.ReverseByteArrayOutputStream;

/**
 * @author jcfinley@users.sourceforge.net
 */
public abstract class FastEncoder<T> implements IEncoder<T> {
    
    public void encode(T object, OutputStream stream)
        throws Exception
    {
    }

    protected int encodeClassType(Object              object,
                                  ASN1ElementMetadata elementMetadata,
                                  OutputStream        stream)
        throws Exception
    {
        return 0;
    }

    protected int encodeJavaElement(Object              object,
                                    ASN1Metadata        typeMetadata,
                                    ASN1ElementMetadata elementMetadata,
                                    OutputStream        stream)
        throws Exception
    {
        return 0;
    }
    
    abstract protected int encodeSequence(Object              object,
                                          ASN1Metadata        typeMetadata,
                                          ASN1ElementMetadata elementMetadata,
                                          OutputStream        stream)
        throws Exception;

    protected int encodeChoice(Object              object,
                               ASN1Metadata        typeMetadata,
                               ASN1ElementMetadata elementMetadata,
                               OutputStream        stream)
        throws Exception
    {
        return 0;
    }
    
        
    protected int encodeEnum(Object              object,
                             ASN1Metadata        typeMetadata,
                             ASN1ElementMetadata elementMetadata,
                             OutputStream        stream)
        throws Exception 
    {
        return 0;
    }
    
    protected int encodeElement(Object              object,
                                ASN1Metadata        typeMetadata,
                                ASN1ElementMetadata elementMetadata,
                                OutputStream        stream)
                                throws Exception 
    {
        return 0;
    }
    
    protected int encodeBoxedType(Object              object,
                                  ASN1Metadata        typeMetadata,
                                  ASN1ElementMetadata elementMetadata,
                                  OutputStream        stream)
        throws Exception 
    {
        return 0;
    }

    protected abstract int encodeBoolean(Object              object,
                                         ASN1Metadata        typeMetadata,
                                         ASN1ElementMetadata elementMetadata,
                                         OutputStream        stream)
        throws Exception;

    protected abstract int encodeAny(Object              object,
                                     ASN1Metadata        typeMetadata,
                                     ASN1ElementMetadata elementMetadata,
                                     OutputStream        stream)
        throws Exception;

    protected abstract int encodeNull(Object              object,
                                      ASN1Metadata        typeMetadata,
                                      ASN1ElementMetadata elementMetadata,
                                      OutputStream        stream)
        throws Exception;

    protected abstract int encodeInteger(Object              object,
                                         ASN1Metadata        typeMetadata,
                                         ASN1ElementMetadata elementMetadata,
                                         OutputStream        stream)
        throws Exception;

    protected abstract int encodeReal(Object              object,
                                      ASN1Metadata        typeMetadata,
                                      ASN1ElementMetadata elementMetadata,
                                      OutputStream        stream)
        throws Exception;

    protected abstract int encodeOctetString(Object              object,
                                             ASN1Metadata        typeMetadata,
                                             ASN1ElementMetadata elementMetadata,
                                             OutputStream        stream)
        throws Exception;

    protected abstract int encodeBitString(Object              object,
                                           ASN1Metadata        typeMetadata,
                                           ASN1ElementMetadata elementMetadata,
                                           OutputStream        stream)
        throws Exception;

    protected abstract int encodeString(Object              object,
                                        ASN1Metadata        typeMetadata,
                                        ASN1ElementMetadata elementMetadata,
                                        OutputStream        stream)
        throws Exception;

    protected abstract int encodeSequenceOf(Object              object,
                                            ASN1Metadata        typeMetadata,
                                            ASN1ElementMetadata elementMetadata,
                                            OutputStream        stream)
        throws Exception;
    
}
