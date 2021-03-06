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
using org.bn;
using org.bn.coders;
using csUnit;
using test.org.bn.coders.test_asn;

namespace test.org.bn.coders.per
{
	
	public class PERAlignedCoderTestUtils:CoderTestUtilities
	{
		public override byte[] createNullSeqBytes()
		{
			return new byte[0];
		}
		
		public override byte[] createTaggedNullSeqBytes()
		{
			return new byte[0];
		}
		
		public override byte[] createEnumBytes()
		{
			return new byte[]{(byte) (0x20)};
		}
		
		public override byte[] createITUSeqBytes()
		{
			return new byte[]{(byte) (0x80), (byte) (0x05), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x05), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x05), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x05), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x05), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x05), (byte) (0x64), (byte) (0x64), (byte) (0x64), (byte) (0x64), (byte) (0x64), (byte) (0x05), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65)};
		}
		
		public override byte[] createDataSeqBytes()
		{
			return new byte[]{(byte) (0x40), (byte) (0x00), (byte) (0x00), (byte) (0x07), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x01), (byte) (0xBA), (byte) (0x00), (byte) (0x01), (byte) (0x00), (byte) (0x44), (byte) (0x02), (byte) (0x06), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x05), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x01), (byte) (0x00), (byte) (0x07), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0xFF)};
		}
		
		public override byte[] createSequenceWithEnumBytes()
		{
			return new byte[]{(byte) (0x05), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x20), (byte) (0x20)};
		}
		
		public override byte[] createTestInteger1Bytes()
		{
			return new byte[]{(byte) (0x0F)};
		}
		
		public override byte[] createTestInteger2Bytes()
		{
			return new byte[]{(byte) 0x0F, (byte) (0xF0)};
		}
		
		public override byte[] createTestInteger3Bytes()
		{
			return new byte[]{(byte) (0xFF), (byte) (0xF0)};
		}
		
		public override byte[] createTestInteger4Bytes()
		{
			return new byte[]{(byte) (0xC0), (byte) (0x00), (byte) (0xF0), (byte) (0xF0), (byte) (0xF0)};
		}
		
		public override byte[] createSeqWithNullBytes()
		{
			return new byte[]{(byte) (0x03), (byte) (0x73), (byte) (0x73), (byte) (0x73), (byte) (0x03), (byte) (0x64), (byte) (0x64), (byte) (0x64)};
		}
		
		public override byte[] createTestRecursiveDefinitionBytes()
		{
			return new byte[]{(byte) (0x80), (byte) (0x05), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x00), (byte) (0x05), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62)};
		}
		
		public override byte[] createUnboundedTestIntegerBytes()
		{
			return new byte[]{(byte) (0x04), (byte) (0x00), (byte) (0xFA), (byte) (0xFB), (byte) (0xFC)};
		}
		
		public override byte[] createTestIntegerRBytes()
		{
			return new byte[]{(byte) (0x40)};
		}
		
		public override byte[] createTestInteger2_12Bytes()
		{
			return new byte[]{(byte) (0x1F), (byte) (0xF1)};
		}
		
		public override byte[] createTestPRNBytes()
		{
			return new byte[]{(byte) (0x05), (byte) (0x48), (byte) (0x65), (byte) (0x6C), (byte) (0x6C), (byte) (0x6F)};
		}
		
		public override byte[] createTestOCTBytes()
		{
			return new byte[]{(byte) (0x05), (byte) (0x01), (byte) (0x02), (byte) (0xFF), (byte) (0x03), (byte) (0x04)};
		}
		
		public override byte[] createDataSeqMOBytes()
		{
			return new byte[]{(byte) (0x7F), (byte) (0x01), (byte) (0x40), (byte) (0x00), (byte) (0x00), (byte) (0x07), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x01), (byte) (0xAB), (byte) (0x80), (byte) (0x01), (byte) (0x00), (byte) (0x00), (byte) (0x02), (byte) (0x06), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x05), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x02), (byte) (0x00), (byte) (0x07), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x65), (byte) (0x00), (byte) (0x04), (byte) (0x66), (byte) (0x66), (byte) (0x66), (byte) (0x66), (byte) (0xAA), (byte) (0x01), (byte) (0x60), (byte) (0x04), (byte) (0x64), (byte) (0x64), (byte) (0x64), (byte) (0x64)};
		}
		
		public override byte[] createDataChoiceTestOCTBytes()
		{
			return new byte[]{(byte) (0x40), (byte) (0x01), (byte) (0xFF)};
		}
		
		public override byte[] createDataChoiceSimpleTypeBytes()
		{
			return new byte[]{(byte) (0x60), (byte) (0x07), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61), (byte) (0x61)};
		}
		
		public override byte[] createDataChoiceBooleanBytes()
		{
			return new byte[]{(byte) (0xB0)};
		}
		
		public override byte[] createDataChoiceIntBndBytes()
		{
			return new byte[]{(byte) (0xE0), (byte) (0x07)};
		}
		
		public override byte[] createDataChoicePlainBytes()
		{
			return new byte[]{(byte) (0x00), (byte) (0x06), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62)};
		}
		
		public override byte[] createStringArrayBytes()
		{
			return new byte[]{(byte) (0x02), (byte) (0x06), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x62), (byte) (0x05), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63), (byte) (0x63)};
		}

        public override byte[] createTestNIBytes()
        {
            return new byte[] { 0x00, 0x78 };
        }

        public override byte[] createTestNI2Bytes()
        {
            return new byte[] { 0x00, 0x30 };
        }

	}
}