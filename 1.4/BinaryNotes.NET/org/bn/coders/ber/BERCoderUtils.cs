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

namespace org.bn.coders.ber
{	
	class BERCoderUtils
	{
		public static int getTagValueForElement(ElementInfo info, int tagClass, int elemenType, int universalTag)
		{
			int result = tagClass | elemenType | universalTag;
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
					tagClass = elementInfo.TagClass;
                    if (elementInfo.Tag <= 0x30)
                    {
                        result = tagClass | elemenType | elementInfo.Tag;
                    }
                    else
                    {
                        result = tagClass | elemenType | 0x1F;
                        if (elementInfo.Tag < 0x80)
                        {
                            result <<= 8;
                            result |= elementInfo.Tag&0x7F;
                        }
                        else
                        if (elementInfo.Tag < 0x3FFF)
                        {
                            result <<= 16;
                            result |= (((elementInfo.Tag & 0x3FFF) >> 7) | 0x80) << 8;
                            result |= ((elementInfo.Tag & 0x3FFF) & 0x7f);                            
                        }
                        else
                        if (elementInfo.Tag < 0x3FFFF)
                        {
                            result <<= 24;
                            result |= (((elementInfo.Tag & 0x3FFFF) >> 15) | 0x80) << 16;
                            result |= (((elementInfo.Tag & 0x3FFFF) >> 7) | 0x80) << 8;
                            result |= ((elementInfo.Tag & 0x3FFFF) & 0x3f);
                        }
                        //result |= elementInfo.Tag;
                    }
				}
			}
			return result;
		}

	}
}