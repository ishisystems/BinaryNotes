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
using System.Collections.Generic;
using test.org.bn.coders.test_asn;

namespace test.org.bn.coders
{
	
	public abstract class CoderTestUtilities
	{
		
		public virtual NullSequence createNullSeq()
		{
			NullSequence seq = new NullSequence();
			return seq;
		}
		public abstract byte[] createNullSeqBytes();
		
		public virtual TaggedNullSequence createTaggedNullSeq()
		{
			TaggedNullSequence seq = new TaggedNullSequence();
			return seq;
		}
		public abstract byte[] createTaggedNullSeqBytes();
		
		
		public virtual ContentSchema createEnum()
		{
			ContentSchema schema = new ContentSchema();
			schema.Value = (ContentSchema.EnumType.multipart_mixed);
			return schema;
		}
		public abstract byte[] createEnumBytes();
		
		
		public virtual ITUSequence createITUSeq()
		{
			ITUSequence seq = new ITUSequence();
			seq.Type1 = "aaaaa";
			seq.Type2 = new ITUType1("bbbbb");
			ITUType1 type1 = new ITUType1("ccccc");
			ITUType2 type2 = new ITUType2();
			type2.Value = type1;
			seq.Type3 = type2;
			ITUType3 type3 = new ITUType3();
			type3.Value = type2;
			seq.Type4 = type3;
			seq.Type5 = type2;
			seq.Type6 = "ddddd";
			ITUType6 type6 = new ITUType6();
			type6.Value = "eeeee";
			seq.Type7 = type6;
			return seq;
		}
		public abstract byte[] createITUSeqBytes();
		
		
		public virtual DataSeq createDataSeq()
		{
			DataSeq seq = new DataSeq();
			seq.Binary = new TestOCT(new byte[]{});
			seq.SimpleType = "aaaaaaa";
			seq.BooleanType = (false);
			Data dt = new Data();
			dt.selectPlain(new TestPRN("eeeeeee"));
            List<Data> lstDt = new List<Data>();
			lstDt.Add(dt);
			seq.DataArray = (lstDt);
			seq.IntBndType = (0x44);
			seq.Plain = new TestPRN("");
			seq.SimpleOctType = new byte[]{(byte) (0xBA)};
			seq.IntType = (0);
            List<String> list = new List<String>();
			list.Add("bbbbbb");
			list.Add("ccccc");
			seq.StringArray = (list);
			seq.Extension = new byte[]{(byte) (0xFF)};
			return seq;
		}
		public abstract byte[] createDataSeqBytes();
		
		public virtual DataSeqMO createDataSeqMO()
		{
			DataSeqMO seq = new DataSeqMO();
			seq.Binary = new TestOCT(new byte[]{});
			seq.SimpleType = "aaaaaaa";
			seq.BooleanType=  true;
			
			Data dt = new Data();
			dt.selectPlain(new TestPRN("eeeeeee"));
			Data dt2 = new Data();
			dt2.selectPlain(new TestPRN("ffff"));
            List<Data> lstDt = new List<Data>();
			lstDt.Add(dt);
			lstDt.Add(dt2);
			seq.DataArray = (lstDt);
			
			seq.IntBndType = (0);
			seq.Plain = new TestPRN("");
			seq.SimpleOctType = new byte[]{(byte) (0xAB)};
			seq.IntType = (0);

            List<String> list = new List<String>();
			list.Add("bbbbbb");
			list.Add("ccccc");
			seq.StringArray = (list);

            List<Data> listData = new List<Data>();
			Data choice = new Data();
			choice.selectSimpleType("dddd");
			listData.Add(choice);
			seq.DataArray2 = (listData);
			
			seq.IntBndType2 = (0xAA);
			
			return seq;
		}
		public abstract byte[] createDataSeqMOBytes();
		
		public virtual SequenceWithEnum createSequenceWithEnum()
		{
			SequenceWithEnum seq = new SequenceWithEnum();
			seq.Enval = createEnum();
			seq.Item = "aaaaa";
			seq.TaggedEnval = createEnum();
			return seq;
		}
		public abstract byte[] createSequenceWithEnumBytes();
		
		public virtual TestIR createTestIntegerR()
		{
			TestIR value_Renamed = new TestIR();
			value_Renamed.Value = 3;
			return value_Renamed;
		}
		public abstract byte[] createTestIntegerRBytes();
		
		public virtual TestI8 createTestInteger1()
		{
			TestI8 value_Renamed = new TestI8();
			value_Renamed.Value = 0x0F;
			return value_Renamed;
		}
		public abstract byte[] createTestInteger1Bytes();
		
		public virtual TestI14 createTestInteger2_12()
		{
			TestI14 value_Renamed = new TestI14();
			value_Renamed.Value = 0x1FF1;
			return value_Renamed;
		}
		public abstract byte[] createTestInteger2_12Bytes();
		
		public virtual TestI16 createTestInteger2()
		{
			TestI16 value_Renamed = new TestI16();
			value_Renamed.Value = 0x0FF0;
			return value_Renamed;
		}
		public abstract byte[] createTestInteger2Bytes();
		
		public virtual TestI16 createTestInteger3()
		{
			TestI16 value_Renamed = new TestI16();
			value_Renamed.Value = 0xFFF0;
			return value_Renamed;
		}
		public abstract byte[] createTestInteger3Bytes();
		
		public virtual TestI32 createTestInteger4()
		{
			TestI32 value_Renamed = new TestI32();
			value_Renamed.Value = 0xF0F0F0;
			return value_Renamed;
		}
		public abstract byte[] createTestInteger4Bytes();
		
		
		public virtual SequenceWithNull createSeqWithNull()
		{
			SequenceWithNull seq = new SequenceWithNull();
			seq.Test = "sss";
			seq.Test2 = "ddd";
			return seq;
		}
		public abstract byte[] createSeqWithNullBytes();
		
		public virtual TestRecursiveDefinetion createTestRecursiveDefinition()
		{
			TestRecursiveDefinetion result = new TestRecursiveDefinetion();
			TestRecursiveDefinetion subResult = new TestRecursiveDefinetion();
			result.Name = "aaaaa";
			subResult.Name = "bbbbb";
			result.Value = subResult;
			return result;
		}
		public abstract byte[] createTestRecursiveDefinitionBytes();
		
		public virtual TestI createUnboundedTestInteger()
		{
			TestI value_Renamed = new TestI();
			value_Renamed.Value = 0xFAFBFC;
			return value_Renamed;
		}
		public abstract byte[] createUnboundedTestIntegerBytes();
		
		public virtual TestPRN createTestPRN()
		{
			TestPRN value_Renamed = new TestPRN();
			value_Renamed.Value = "Hello";
			return value_Renamed;
		}
		public abstract byte[] createTestPRNBytes();
		
		public virtual TestOCT createTestOCT()
		{
			TestOCT value_Renamed = new TestOCT();
			value_Renamed.Value = new byte[]{(byte) (0x01), (byte) (0x02), (byte) (0xFF), (byte) (0x03), (byte) (0x04)};
			return value_Renamed;
		}
		public abstract byte[] createTestOCTBytes();
		
		public abstract byte[] createDataChoiceTestOCTBytes();
		
		public abstract byte[] createDataChoiceSimpleTypeBytes();
		
		public abstract byte[] createDataChoiceBooleanBytes();
		
		public abstract byte[] createDataChoiceIntBndBytes();
		
		public abstract byte[] createDataChoicePlainBytes();
		
		public virtual StringArray createStringArray()
		{
			StringArray sequenceOfString = new StringArray();
            List<String> list = new List<String>();
			list.Add("bbbbbb");
			list.Add("ccccc");
			sequenceOfString.Value = list;
			return sequenceOfString;
		}
		
		public abstract byte[] createStringArrayBytes();

        public TestNI createTestNI()
        {
            return new TestNI(-8);
        }
        public abstract byte[] createTestNIBytes();

        public TestNI2 createTestNI2()
        {
            return new TestNI2(-2000);
        }
        public abstract byte[] createTestNI2Bytes();

	}
}