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
using System;
using System.Reflection;
using org.bn.attributes;
using org.bn.metadata;

namespace org.bn.coders.ber
{	
	class BERCoderUtils
	{
		public static DecodedObject<int> getTagValueForElement(ElementInfo info, int tagClass, int elemenType, int universalTag)
		{
            DecodedObject<int> result = new DecodedObject<int>();
			result.Value =  tagClass | elemenType | universalTag;
            result.Size = 1;
            if(info.hasPreparedInfo()) 
            {
                ASN1ElementMetadata meta = info.PreparedASN1ElementInfo;
                if(meta!=null && meta.HasTag) {
                    result = getTagValue(tagClass,elemenType,universalTag,
                        meta.Tag,
                        meta.TagClass
                    );
                }
            }
            else 
            {
			    ASN1Element elementInfo = null;
			    if (info.ASN1ElementInfo != null)
			    {
				    elementInfo = info.ASN1ElementInfo;
			    }
			    else 
                if (info.isAttributePresent<ASN1Element>())
			    {
				    elementInfo = info.getAttribute<ASN1Element>();
			    }
    			
			    if (elementInfo != null)
			    {
				    if (elementInfo.HasTag)
				    {
                        result = getTagValue(tagClass,elemenType,universalTag, elementInfo.Tag,elementInfo.TagClass);
                    }
				}
			}
			return result;
		}

        public static DecodedObject<int> getTagValue(int tagClass, int elemenType, int universalTag, int userTag, int userTagClass)
        {
            DecodedObject<int> resultObj = new DecodedObject<int>();
            int result = tagClass | elemenType | universalTag;
            tagClass = userTagClass;
            if (userTag < 31)
            {
                result = tagClass | elemenType | userTag;
                resultObj.Size = 1;
            }
            else
            {
                result = tagClass | elemenType | 0x1F;
                if (userTag < 0x80)
                {
                    result <<= 8;
                    result |= userTag & 0x7F;
                    resultObj.Size = 2;
                }
                else
                    if (userTag < 0x3FFF)
                    {
                        result <<= 16;
                        result |= (((userTag & 0x3FFF) >> 7) | 0x80) << 8;
                        result |= ((userTag & 0x3FFF) & 0x7f);
                        resultObj.Size = 3;
                    }
                    else
                        if (userTag < 0x3FFFF)
                        {
                            result <<= 24;
                            result |= (((userTag & 0x3FFFF) >> 15) | 0x80) << 16;
                            result |= (((userTag & 0x3FFFF) >> 7) | 0x80) << 8;
                            result |= ((userTag & 0x3FFFF) & 0x3f);
                            resultObj.Size = 4;
                        }
            }
            resultObj.Value = result;
            return resultObj;
        }

	}
}
