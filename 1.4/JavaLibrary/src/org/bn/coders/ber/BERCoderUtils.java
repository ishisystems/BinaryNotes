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
package org.bn.coders.ber;

import org.bn.annotations.ASN1Element;
import org.bn.annotations.ASN1String;
import org.bn.coders.ElementInfo;

public class BERCoderUtils {
    
    public static int getTagValueForElement(ElementInfo info, int tagClass, int elemenType, int universalTag) {
        int result = tagClass | elemenType | universalTag;
        ASN1Element elementInfo = null;
        if(info.getASN1ElementInfo()!=null) {
            elementInfo = info.getASN1ElementInfo();
        }
        else
        if(info.getAnnotatedClass().isAnnotationPresent(ASN1Element.class)) {
            elementInfo = info.getAnnotatedClass().getAnnotation(ASN1Element.class);
        }        
        
        if(elementInfo!=null) {
            if(elementInfo.hasTag()) {
                tagClass = elementInfo.tagClass();
                result = tagClass | elemenType | elementInfo.tag();
            }
        }
        return result;
    }
}
