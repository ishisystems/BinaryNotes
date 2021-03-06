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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bn.types.BitString;

import test.org.bn.coders.test_asn.BugList;
import test.org.bn.coders.test_asn.BugPrimitive;
import test.org.bn.coders.test_asn.BugSequenceType;
import test.org.bn.coders.test_asn.BugValueType;
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
import test.org.bn.coders.test_asn.PlainParamsMap;
import test.org.bn.coders.test_asn.SequenceWithEnum;
import test.org.bn.coders.test_asn.SequenceWithNull;
import test.org.bn.coders.test_asn.SetWithDefault;
import test.org.bn.coders.test_asn.StringArray;
import test.org.bn.coders.test_asn.TaggedNullSequence;
import test.org.bn.coders.test_asn.TaggedSeqInSeq;
import test.org.bn.coders.test_asn.TestBitStr;
import test.org.bn.coders.test_asn.TestBitStrBnd;
import test.org.bn.coders.test_asn.TestI;
import test.org.bn.coders.test_asn.TestI14;
import test.org.bn.coders.test_asn.TestI16;
import test.org.bn.coders.test_asn.TestI32;
import test.org.bn.coders.test_asn.TestI8;
import test.org.bn.coders.test_asn.TestIR;
import test.org.bn.coders.test_asn.TestLongTag;
import test.org.bn.coders.test_asn.TestNI;
import test.org.bn.coders.test_asn.TestNI2;
import test.org.bn.coders.test_asn.TestOCT;
import test.org.bn.coders.test_asn.TestPRN;
import test.org.bn.coders.test_asn.TestReal;
import test.org.bn.coders.test_asn.TestRecursiveDefinetion;
import test.org.bn.coders.test_asn.TestSequenceV12;
import test.org.bn.coders.test_asn.TestUnicodeStr;

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
        seq.setIntType(0L);
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
        seq.setIntType(0L);
        
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
        value.setValue(new Long(0xF0F0F0L));
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
        value.setValue(new Long(0xFAFBFC));
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
    
    public SetWithDefault createSet() {
        SetWithDefault result = new SetWithDefault();
        result.setNodefault(0xAAL);
        result.setNodefault2(new TestPRN("aaaa"));
        result.setDefault3("bbbb");    
        return result;
    }    
    public abstract byte[] createSetBytes();

    public TestBitStr createTestBitStr() {
        TestBitStr result = new TestBitStr();
        result.setValue(new BitString(new byte[] {(byte)0xAA,(byte)0xBB,(byte)0xCC,(byte)0xDD, (byte)0xF0} , 4 ));
        return result;
    }    
    public abstract byte[] createTestBitStrBytes();

    public TestBitStr createTestBitStrSmall() {
        TestBitStr result = new TestBitStr();
        result.setValue(new BitString(new byte[] {(byte)0xAA,(byte)0xB0} , 4 ));
        return result;
    }    
    public abstract byte[] createTestBitStrSmallBytes();

    public TestBitStrBnd createTestBitStrBnd() {
        TestBitStrBnd result = new TestBitStrBnd();
        //result.setValue(new BitString(new byte[] {(byte)0xAA,(byte)0xB0} , 4 ));
        result.setValue(new BitString(new byte[] {(byte)0xF0} , 4 ));
        return result;
    }    
    public abstract byte[] createTestBitStrBndBytes();


    public TestUnicodeStr createUnicodeStr() {
        TestUnicodeStr result = new TestUnicodeStr();
        result.setValue("\u0465\u0464\u0466");
        return result;
    }    
    public abstract byte[] createUnicodeStrBytes();
    
    public TestSequenceV12 createTestSequenceV12() {
        TestSequenceV12 result = new TestSequenceV12();
        result.setAttrSimple("aba");
        java.util.Collection<String> array = new java.util.LinkedList<String>();
        array.add("aaaa");
        array.add("bbb");
        result.setAttrArr(array);
        result.setAttrBitStr(new BitString(new byte[]{ (byte)0x99, (byte)0x80 }, 1 ));
        result.setAttrBitStrBnd(new BitString(new byte[]{ (byte)0xF0}, 4));
        result.setAttrBoxBitStr(new TestBitStrBnd(new BitString(new byte[]{ (byte)0xA0}, 4)));
        //result.setAttrInt(0x3);
        result.setAttrStr("cccc");
        result.setAttrStr2( new TestPRN("dddddd"));
        result.setAttrStrict(new byte[]{ 0x0A, 0x0B, 0x0C, 0x0D});
        return result;
    }
    public abstract byte[] createTestSequenceV12Bytes();

    public BugValueType createChoiceInChoice() {
        BugPrimitive primitive = new BugPrimitive();
        primitive.selectBugInteger(5L);

        BugValueType valueType = new BugValueType();
        valueType.selectBugPrimitive(primitive);

        List<BugValueType> list = new ArrayList<BugValueType>();
        list.add(valueType);

        BugList bugList = new BugList();
        bugList.setValue(list);
        return valueType;
    }
    public abstract byte[] createChoiceInChoiceBytes();

    public BugList createChoiceInChoice2() {
        BugPrimitive primitive = new BugPrimitive();
        primitive.selectBugInteger(Long.valueOf(5));

        BugValueType valueType = new BugValueType();
        valueType.selectBugPrimitive(primitive);

        List<BugValueType> list = new ArrayList<BugValueType>();
        list.add(valueType);

        BugList bugList = new BugList();
        bugList.setValue(list);
        return bugList;
    }
    public abstract byte[] createChoiceInChoice2Bytes();

    private BugSequenceType makeSequence(boolean booleanValue,
                                         long    integerValue)
    {
        BugSequenceType sequenceType = new BugSequenceType();
        sequenceType.setBooleanField(booleanValue);
        sequenceType.setIntegerField(integerValue);

        return sequenceType;
    }


    private BugValueType makeValueType(BugSequenceType sequenceType)
    {
        BugValueType valueType = new BugValueType();
        valueType.selectBugSequence(sequenceType);

        return valueType;
    }
    
    public BugList createChoiceInChoice3() {
        BugValueType[] valueTypes =
        {
            makeValueType(makeSequence(false, Long.valueOf(0))),
            //makeValueType(makeSequence(false, Long.MAX_VALUE)),
            makeValueType(makeSequence(false, 100L)),
        };

        List<BugValueType> list = new ArrayList<BugValueType>();
        for (int index = 0; index < valueTypes.length; index++)
        {
            list.add(valueTypes[index]);
        }

        BugList bugList = new BugList();
        bugList.setValue(list);
        return bugList;
    }
    public abstract byte[] createChoiceInChoice3Bytes();    
    
    public TaggedSeqInSeq createTaggedSeqInSeq() {
        TaggedSeqInSeq result = new TaggedSeqInSeq();
        TaggedSeqInSeq.TaggedSeqInSeqSequenceType resSeq = result.new TaggedSeqInSeqSequenceType();
        PlainParamsMap field = new PlainParamsMap();
        field.setParam_name("aaaa");
        field.setParam_value("bbbb");
        resSeq.setField(field);
        result.setValue(resSeq);
        return result;
    }
    public abstract byte[] createTaggedSeqInSeqBytes();
        
    
    public TestReal createTestReal0_5() {
        return new TestReal(0.5);
    }
    public abstract byte[] createTestReal0_5Bytes();

    public TestReal createTestReal1_5() {
        return new TestReal(1.5);
    }
    public abstract byte[] createTestReal1_5Bytes();

    public TestReal createTestReal2() {
        return new TestReal(2D);
    }
    public abstract byte[] createTestReal2Bytes();    
    
    public TestReal createTestRealBig() {
        return new TestReal(200100.125);
    }
    public abstract byte[] createTestRealBigBytes();    

    public TestLongTag createTestLongTag() {
        TestLongTag result = new TestLongTag();
        result.setValue(0xAAL);
        return result;
    }
    public abstract byte[] createTestLongTagBytes();    
    
}
