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
package test.org.bn.coders;

import java.util.LinkedList;
import java.util.List;

import test.org.bn.coders.test_asn.ContentSchema;
import test.org.bn.coders.test_asn.Data;
import test.org.bn.coders.test_asn.DataSeq;
import test.org.bn.coders.test_asn.DataSeqMO;
import test.org.bn.coders.test_asn.ITUSequence;
import test.org.bn.coders.test_asn.ITUType1;
import test.org.bn.coders.test_asn.ITUType2;
import test.org.bn.coders.test_asn.ITUType3;
import test.org.bn.coders.test_asn.ITUType6;
import test.org.bn.coders.test_asn.NullSequence;
import test.org.bn.coders.test_asn.SequenceWithEnum;
import test.org.bn.coders.test_asn.SequenceWithNull;
import test.org.bn.coders.test_asn.StringArray;
import test.org.bn.coders.test_asn.TaggedNullSequence;
import test.org.bn.coders.test_asn.TestI;
import test.org.bn.coders.test_asn.TestI14;
import test.org.bn.coders.test_asn.TestI16;
import test.org.bn.coders.test_asn.TestI32;
import test.org.bn.coders.test_asn.TestI8;
import test.org.bn.coders.test_asn.TestIR;
import test.org.bn.coders.test_asn.TestNI;
import test.org.bn.coders.test_asn.TestNI2;
import test.org.bn.coders.test_asn.TestOCT;
import test.org.bn.coders.test_asn.TestPRN;
import test.org.bn.coders.test_asn.TestRecursiveDefinetion;

public abstract class CoderTestUtilities {

    public NullSequence createNullSeq() {
        NullSequence seq = new NullSequence();
        return seq;
    }
    public abstract byte[] createNullSeqBytes();    

    public TaggedNullSequence createTaggedNullSeq() {
        TaggedNullSequence seq = new TaggedNullSequence();
        return seq;
    }
    public abstract byte[] createTaggedNullSeqBytes();


    public ContentSchema createEnum() {
        ContentSchema schema = new ContentSchema();
        schema.setValue(ContentSchema.EnumType.multipart_mixed);
        return schema;
    }    
    public abstract byte[] createEnumBytes();
    

    public ITUSequence createITUSeq() {
        ITUSequence seq = new ITUSequence();
        seq.setType1("aaaaa");
        seq.setType2(new ITUType1("bbbbb"));
        ITUType1 type1 = new ITUType1("ccccc");
        ITUType2 type2 = new ITUType2();
        type2.setValue(type1);
        seq.setType3(type2);
        ITUType3 type3 = new ITUType3();
        type3.setValue(type2);
        seq.setType4(type3);
        seq.setType5(type2);
        seq.setType6("ddddd");
        ITUType6 type6 = new ITUType6();
        type6.setValue("eeeee");
        seq.setType7(type6);
        return seq;
    }    
    public abstract byte[] createITUSeqBytes();    


    public DataSeq createDataSeq() {
        DataSeq seq = new DataSeq();
        seq.setBinary(new TestOCT(new byte[] { }));
        seq.setSimpleType("aaaaaaa");
        seq.setBooleanType(false);
        Data dt =new Data();
        dt.selectPlain(new TestPRN("eeeeeee"));        
        LinkedList<Data> lstDt = new LinkedList<Data>();
        lstDt.add(dt);
        seq.setDataArray(lstDt);
        seq.setIntBndType(0x44);
        seq.setPlain(new TestPRN(""));
        seq.setSimpleOctType(new byte[] { (byte)0xBA });
        seq.setIntType(0);
        List<String> list = new LinkedList<String>();
        list.add("bbbbbb");
        list.add("ccccc");
        seq.setStringArray(list);
        seq.setExtension(new byte[] { (byte)0xFF });
        return seq;
    }
    public abstract byte[] createDataSeqBytes();

    public DataSeqMO createDataSeqMO() {
        DataSeqMO seq = new DataSeqMO();
        seq.setBinary(new TestOCT(new byte[] { }));
        seq.setSimpleType("aaaaaaa");
        seq.setBooleanType(true);
        
        Data dt =new Data();
        dt.selectPlain(new TestPRN("eeeeeee"));        
        Data dt2 =new Data();
        dt2.selectPlain(new TestPRN("ffff"));        
        LinkedList<Data> lstDt = new LinkedList<Data>();
        lstDt.add(dt);
        lstDt.add(dt2);
        seq.setDataArray(lstDt);
        
        seq.setIntBndType(0);
        seq.setPlain(new TestPRN(""));
        seq.setSimpleOctType(new byte[] { (byte)0xAB });
        seq.setIntType(0);
        
        List<String> list = new LinkedList<String>();
        list.add("bbbbbb");
        list.add("ccccc");
        seq.setStringArray(list);

        List<Data> listData = new LinkedList<Data>();
        Data choice = new Data();
        choice.selectSimpleType("dddd");
        listData.add(choice);        
        seq.setDataArray2(listData);
        
        seq.setIntBndType2(0xAA);
        
        return seq;
    }
    public abstract byte[] createDataSeqMOBytes();

    public SequenceWithEnum createSequenceWithEnum() {
        SequenceWithEnum seq = new SequenceWithEnum();
        seq.setEnval(createEnum());
        seq.setItem("aaaaa");
        seq.setTaggedEnval(createEnum());
        return seq;
    }
    public abstract byte[] createSequenceWithEnumBytes();

    public TestIR createTestIntegerR() {
        TestIR value = new TestIR();
        value.setValue(new Integer(3));
        return value;
    }
    public abstract byte[] createTestIntegerRBytes();        

    public TestI8 createTestInteger1() {
        TestI8 value = new TestI8();
        value.setValue(new Integer(0x0F));
        return value;
    }
    public abstract byte[] createTestInteger1Bytes();

    public TestI14 createTestInteger2_12() {
        TestI14 value = new TestI14();
        value.setValue(new Integer(0x1FF1));
        return value;
    }
    public abstract byte[] createTestInteger2_12Bytes();

    public TestI16 createTestInteger2() {
        TestI16 value = new TestI16();
        value.setValue(new Integer(0x0FF0));
        return value;
    }
    public abstract byte[] createTestInteger2Bytes();
    
    public TestI16 createTestInteger3() {
        TestI16 value = new TestI16();
        value.setValue(new Integer(0xFFF0));
        return value;
    }
    public abstract byte[] createTestInteger3Bytes();
    
    public TestI32 createTestInteger4() {
        TestI32 value = new TestI32();
        value.setValue(new Integer(0xF0F0F0));
        return value;
    }
    public abstract byte[] createTestInteger4Bytes();        


    public SequenceWithNull createSeqWithNull() {
        SequenceWithNull seq = new SequenceWithNull();
        seq.setTest("sss");
        seq.setTest2("ddd");
        return seq;
    }    
    public abstract byte[] createSeqWithNullBytes();
    
    public TestRecursiveDefinetion createTestRecursiveDefinition() {
        TestRecursiveDefinetion result = new TestRecursiveDefinetion();
        TestRecursiveDefinetion subResult = new TestRecursiveDefinetion();
        result.setName("aaaaa");
        subResult.setName("bbbbb");
        result.setValue(subResult);
        return result;
    }
    public abstract byte[] createTestRecursiveDefinitionBytes();

    public TestI createUnboundedTestInteger() {
        TestI value = new TestI();
        value.setValue(new Integer(0xFAFBFC));
        return value;    
    }
    public abstract byte[] createUnboundedTestIntegerBytes();

    public TestPRN createTestPRN() {
        TestPRN value = new TestPRN();
        value.setValue("Hello");
        return value;
    }
    public abstract byte[] createTestPRNBytes();

    public TestOCT createTestOCT() {
        TestOCT value = new TestOCT();
        value.setValue(new byte[] {0x01,0x02, (byte)0xFF, 0x03, 0x04});
        return value;
    }
    public abstract byte[] createTestOCTBytes();

    public abstract byte[] createDataChoiceTestOCTBytes();

    public abstract byte[]createDataChoiceSimpleTypeBytes();

    public abstract byte[] createDataChoiceBooleanBytes();

    public abstract byte[] createDataChoiceIntBndBytes();

    public abstract byte[] createDataChoicePlainBytes();

    public StringArray createStringArray() {
        StringArray sequenceOfString = new StringArray();
        List<String> list = new LinkedList<String>();
        list.add("bbbbbb");
        list.add("ccccc");
        sequenceOfString.setValue(list);
        return sequenceOfString;
    }
    
    public abstract byte[] createStringArrayBytes();

    public TestNI createTestNI() {
        return new TestNI(-8);
    }
    public abstract byte[] createTestNIBytes();
    
    public TestNI2 createTestNI2() {
        return new TestNI2(-2000);
    }
    public abstract byte[] createTestNI2Bytes();
    
}
