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

namespace org.bn.coders
{	
	class BERCoderUtils
	{
		public static int getTagValueForElement(ElementInfo info, int tagClass, int elemenType, int universalTag)
		{
			int result = tagClass | elemenType | universalTag;
			ASN1Element elementInfo = null;
			if (info.Element != null)
			{
				elementInfo = info.Element;
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
					result = tagClass | elemenType | elementInfo.Tag;
				}
			}
			return result;
		}
		
		public static int getStringTagForElement(ElementInfo elementInfo)
		{
			int result = UniversalTags.PrintableString;
            if (elementInfo.isAttributePresent<ASN1String>())
			{
                ASN1String val = elementInfo.getAttribute<ASN1String>();
				result = val.StringType;
			}
			return result;
		}
	}
}