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
using test.org.bn.utils;
using System.Collections.Generic;

namespace test.org.bn.coders
{
	
	[TestFixture]
	public abstract class DecoderTest
	{
        private CoderTestUtilities coderTestUtils;

		public DecoderTest(string testName, CoderTestUtilities coderTestUtils)
		{
			this.coderTestUtils = coderTestUtils;
		}
		
		protected internal abstract IDecoder newDecoder();
		
		private void  checkData(Data dec, Data std)
		{
			if (std.isBinarySelected())
			{
				Assert.True(dec.isBinarySelected());
				ByteTools.checkBuffers(dec.Binary.Value, std.Binary.Value);
			}
			else if (std.isPlainSelected())
			{
				Assert.True(dec.isPlainSelected());
				Assert.Equals(dec.Plain.Value, std.Plain.Value);
			}
			else if (std.isIntTypeSelected())
			{
				Assert.True(dec.isIntTypeSelected());
				Assert.Equals(dec.IntType, std.IntType);
			}
			else if (std.isSimpleOctTypeSelected())
			{
				Assert.True(dec.isSimpleOctTypeSelected());
				ByteTools.checkBuffers(dec.SimpleOctType, std.SimpleOctType);
			}
		}

        protected void checkArray<T>(ICollection<T> dec, ICollection<T> std)
        {
            Assert.Equals(dec.Count, std.Count);
            IEnumerator<T> decIt = dec.GetEnumerator();
            IEnumerator<T> stdIt = std.GetEnumerator();
            for (int i = 0; i < std.Count; i++)
            {
                decIt.MoveNext(); stdIt.MoveNext();
                Assert.Equals(decIt.Current, stdIt.Current);
            }
        }

        protected void checkDataArray(ICollection<Data> dec, ICollection<Data> std)
        {
            Assert.Equals(dec.Count, std.Count);
            IEnumerator<Data> decIt = dec.GetEnumerator();
            IEnumerator<Data> stdIt = std.GetEnumerator();
            for (int i = 0; i < std.Count; i++)
            {
                decIt.MoveNext(); stdIt.MoveNext();
                checkData(decIt.Current,stdIt.Current);                
            }
        }
        
		
		/// <seealso cref="Decoder.decode(InputStream,Class)">
		/// </seealso>
		public virtual void  testDecode()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream(
                (coderTestUtils.createDataSeqBytes()));
			DataSeq seq = decoder.decode<DataSeq>(stream);
			checkDataSeq(seq, coderTestUtils.createDataSeq());
		}
		
		protected internal virtual void  checkDataSeq(DataSeq decoded, DataSeq standard)
		{
			ByteTools.checkBuffers(decoded.Binary.Value, standard.Binary.Value);
			Assert.Equals(decoded.BooleanType, standard.BooleanType);
			checkDataArray(decoded.DataArray, standard.DataArray);
			Assert.Equals(decoded.IntBndType, standard.IntBndType);
			Assert.Equals(decoded.IntType, standard.IntType);
			Assert.Equals(decoded.Plain.Value, standard.Plain.Value);
			Assert.Equals(decoded.SimpleType, standard.SimpleType);
			ByteTools.checkBuffers(decoded.SimpleOctType, standard.SimpleOctType);
		}
		
		public virtual void  testITUDeDecode()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createITUSeqBytes()));
            ITUSequence seq = decoder.decode<ITUSequence>(stream);
			checkITUSeq(seq, coderTestUtils.createITUSeq());
		}
		
		private void  checkITUSeq(ITUSequence decoded, ITUSequence standard)
		{
			Assert.Equals(decoded.Type1, standard.Type1);
			Assert.Equals(decoded.Type2.Value, standard.Type2.Value);
			Assert.Equals(decoded.Type3.Value.Value, standard.Type3.Value.Value);
			Assert.Equals(decoded.Type4.Value.Value.Value, standard.Type4.Value.Value.Value);
			Assert.Equals(decoded.Type5.Value.Value, standard.Type5.Value.Value);
			Assert.Equals(decoded.Type6, standard.Type6);
			Assert.Equals(decoded.Type7.Value, standard.Type7.Value);
		}
		
		public virtual void  testNullDecode()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createNullSeqBytes()));
			NullSequence seq = decoder.decode<NullSequence>(stream);
			Assert.NotNull(seq);
		}
		
		public virtual void  testTaggedNullDecode()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTaggedNullSeqBytes()));
			TaggedNullSequence seq = decoder.decode<TaggedNullSequence>(stream);
			Assert.NotNull(seq);
		}
		
		public virtual void  testSequenceWithNullDecode()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createSeqWithNullBytes()));
			SequenceWithNull seq = decoder.decode<SequenceWithNull>(stream);
			Assert.NotNull(seq);
		}
		
		public virtual void  testEnum()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createEnumBytes()));
			ContentSchema enm = decoder.decode<ContentSchema>(stream);
			Assert.NotNull(enm);
			checkContentSchema(enm, coderTestUtils.createEnum());
		}
		
		private void  checkContentSchema(ContentSchema decoded, ContentSchema standard)
		{
			Assert.Equals(decoded.Value, standard.Value);
		}
		
		public virtual void  testSequenceWithEnum()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createSequenceWithEnumBytes()));
			SequenceWithEnum seq = decoder.decode<SequenceWithEnum>(stream);
			Assert.NotNull(seq);
		}
		
		public virtual void  testRecursiveDefinition()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTestRecursiveDefinitionBytes()));
			TestRecursiveDefinetion seq = decoder.decode<TestRecursiveDefinetion>(stream);
			Assert.NotNull(seq);
			checkRecursiveDefinition(seq, coderTestUtils.createTestRecursiveDefinition());
		}
		
		private void  checkRecursiveDefinition(TestRecursiveDefinetion decoded, TestRecursiveDefinetion standard)
		{
			Assert.Equals(decoded.Name, standard.Name);
			if (standard.Value != null)
			{
				Assert.NotNull(decoded.Value);
				checkRecursiveDefinition(decoded.Value, standard.Value);
			}
		}
		
		public virtual void  testDecodeInteger()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger4Bytes()));

            TestI32 val = decoder.decode<TestI32>(stream);
			Assert.NotNull(val);
			Assert.Equals(val.Value, coderTestUtils.createTestInteger4().Value);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger3Bytes()));
            TestI16 val16 = decoder.decode<TestI16>(stream);
			Assert.NotNull(val16);
			Assert.Equals(val16.Value, coderTestUtils.createTestInteger3().Value);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger2Bytes()));
            val16 = decoder.decode<TestI16>(stream);
			Assert.NotNull(val16);
			Assert.Equals(val16.Value, coderTestUtils.createTestInteger2().Value);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger1Bytes()));
            TestI8 val8 = decoder.decode<TestI8>(stream);
			Assert.NotNull(val8);
			Assert.Equals(val8.Value, coderTestUtils.createTestInteger1().Value);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createTestIntegerRBytes()));
            TestIR valR = decoder.decode<TestIR>(stream);
			Assert.NotNull(valR);
			Assert.Equals(valR.Value, coderTestUtils.createTestIntegerR().Value);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createTestInteger2_12Bytes()));
            TestI14 val14 = decoder.decode<TestI14>(stream);
			Assert.NotNull(val14);
			Assert.Equals(val14.Value, coderTestUtils.createTestInteger2_12().Value);
		}
		
		public virtual void  testDecodeString()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createTestPRNBytes()));
            TestPRN val = decoder.decode<TestPRN>(stream);
			Assert.NotNull(val);
			Assert.Equals(val.Value, coderTestUtils.createTestPRN().Value);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createTestOCTBytes()));
            TestOCT valOct = decoder.decode<TestOCT>(stream);
			Assert.NotNull(valOct);
			ByteTools.checkBuffers(valOct.Value, coderTestUtils.createTestOCT().Value);
		}
		
		public virtual void  testDecodeStringArray()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createStringArrayBytes()));
            StringArray val = decoder.decode<StringArray>(stream);
			Assert.NotNull(val);
			checkArray(val.Value, coderTestUtils.createStringArray().Value);
		}
		
		public virtual void  testDecodeChoice()
		{
			IDecoder decoder = newDecoder();
			System.IO.MemoryStream stream = new System.IO.MemoryStream((coderTestUtils.createDataChoicePlainBytes()));
			Data choice = new Data();
            Data val = decoder.decode<Data>(stream);
			Assert.NotNull(val);
			choice.selectPlain(new TestPRN("bbbbbb"));
			checkData(val, choice);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceSimpleTypeBytes()));
            val = decoder.decode<Data>(stream);
			Assert.NotNull(val);
			choice.selectSimpleType("aaaaaaa");
			checkData(val, choice);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceTestOCTBytes()));
            val = decoder.decode<Data>(stream);
			Assert.NotNull(val);
            choice.selectBinary(new TestOCT(new byte[] { 0xFF }));
			checkData(val, choice);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceBooleanBytes()));
            val = decoder.decode<Data>(stream);
			Assert.NotNull(val);
			choice.selectBooleanType(true);
			checkData(val, choice);
			
			stream = new System.IO.MemoryStream((coderTestUtils.createDataChoiceIntBndBytes()));
            val = decoder.decode<Data>(stream);
			Assert.NotNull(val);
			choice.selectIntBndType(7);
			checkData(val, choice);
		}

        public void testDecodeNegativeInteger() {
            IDecoder decoder = newDecoder();
            System.IO.MemoryStream stream = 
                new System.IO.MemoryStream(coderTestUtils.createTestNIBytes());
            TestNI val = decoder.decode<TestNI>(stream);
            Assert.Equals(val.Value, coderTestUtils.createTestNI().Value);
            
            stream = 
                new System.IO.MemoryStream(coderTestUtils.createTestNI2Bytes());
            TestNI2 val2 = decoder.decode<TestNI2>(stream);
            Assert.Equals(val2.Value, coderTestUtils.createTestNI2().Value);
        }
	}
}