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

import org.bn.coders.IValueDecoder;
import org.bn.coders.IValueEncoder;

/**
 * @author jcfinley@users.sourceforge.net
 */
public class ItemDescriptor
    extends Descriptor
{
    private ASN1ElementMetadata elementMetadata;
    private IValueEncoder       encoder;
    private IValueDecoder       decoder;

    public ItemDescriptor(ASN1ElementMetadata elementMetadata,
                          IValueEncoder       encoder,
                          IValueDecoder       decoder)
    {
        this.elementMetadata = elementMetadata;
        this.encoder         = encoder;
        this.decoder         = decoder;
    }

    public ASN1ElementMetadata getElementMetadata()
    {
        return elementMetadata;
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