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
using System.Collections.Generic;
using org.bn.utils;
using org.bn.attributes;
using org.bn.attributes.constraints;

namespace org.bn.coders
{
	
	public class PERUnalignedEncoder:PERAlignedEncoder
	{
		public PERUnalignedEncoder()
		{
		}
		
		protected override int encodeConstraintNumber(long val, long min, long max, BitArrayOutputStream stream)
		{
			int result = 0;
			long valueRange = max - min;
            long narrowedVal = val - min;
			int maxBitLen = PERCoderUtils.getMaxBitLength(valueRange);
			
			if (valueRange == 0)
			{
				return result;
			}
			
			//For the UNALIGNED variant the value is always encoded in the minimum 
			// number of bits necessary to represent the range (defined in 10.5.3). 
			int currentBit = maxBitLen;
			while (currentBit > 8)
			{
				currentBit -= 8;
				result++;
				stream.WriteByte((byte) (narrowedVal >> currentBit));
			}
			if (currentBit > 0)
			{
				for (int i = currentBit - 1; i >= 0; i--)
				{
					int bitValue = (int) ((narrowedVal >> i) & 0x1);
					stream.writeBit(bitValue);
				}
				result += 1;
			}
			return result;
		}
		
		protected override int encodeString(System.Object obj, System.IO.Stream stream, ElementInfo elementInfo)
		{					
			bool is7Bit = false;
			ASN1String strValueAnnotation = null;
			if (elementInfo.isAttributePresent<ASN1String>())
			{
				strValueAnnotation = elementInfo.getAttribute<ASN1String>();
			}
			else if (elementInfo.ParentAnnotatedClass != null && elementInfo.isParentAttributePresent<ASN1String>())
			{
				strValueAnnotation = elementInfo.getParentAttribute<ASN1String>();
			}
			if (strValueAnnotation != null)
			{
				is7Bit = 
                    (strValueAnnotation.StringType == org.bn.coders.UniversalTags.PrintableString || 
                    strValueAnnotation.StringType == org.bn.coders.UniversalTags.VisibleString);
			}
			if (!is7Bit)
				return base.encodeString(obj, stream, elementInfo);
			else
			{
                int resultSize = 0;
                byte[] val = System.Text.ASCIIEncoding.ASCII.GetBytes((string)obj);
                resultSize = encodeLength(val.Length, elementInfo, stream);
                if (val.Length == 0)
                    return resultSize;


				BitArrayOutputStream bitStream = (BitArrayOutputStream) stream;
                // 7-bit encoding of string
                for (int i = 0; i < val.Length; i++)
                {
                    bitStream.writeBits(val[i], 7);
                }
                return resultSize;
			}
			
		}
		
		protected override void  doAlign(System.IO.Stream stream)
		{
			// Do nothing! Unaligned encoding ;)
		}
	}
}