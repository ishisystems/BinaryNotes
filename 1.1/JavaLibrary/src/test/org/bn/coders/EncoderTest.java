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

import java.io.ByteArrayOutputStream;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.bn.CoderFactory;
import org.bn.IDecoder;
import org.bn.IEncoder;
import org.bn.coders.BEREncoder;
import org.bn.coders.Encoder;

import test.org.bn.utils.ByteTools;
import test.org.bn.coders.test_asn.*;


public abstract class EncoderTest extends TestCase {
    private CoderTestUtilities coderTestUtils;
    
    public EncoderTest(String sTestName, CoderTestUtilities coderTestUtils) {
        super(sTestName);
        this.coderTestUtils = coderTestUtils;        
    }        
    
    protected void printEncoded(String details,IEncoder encoder, Object obj) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(obj, outputStream);
        System.out.println("Encoded by "+encoder.getClass()+" ("+details+") : " + ByteTools.byteArrayToHexString(outputStream.toByteArray()));        
    }
    
    protected abstract <T> IEncoder<T> newEncoder() throws Exception;
    
    /**
     * @see Encoder#encode(T,OutputStream)
     */
    public void testEncodeChoice() throws Exception {
        IEncoder<Data> encoder = newEncoder();
        assertNotNull(encoder);
        Data choice = new Data();        

        choice.selectBinary( new TestOCT( new byte[] { (byte)0xFF } ) );
        printEncoded("Choice boxed octet",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceTestOCTBytes());

        choice.selectSimpleType("aaaaaaa");
        printEncoded("Choice string", encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceSimpleTypeBytes());
        
        choice.selectBooleanType(true);
        printEncoded("Choice boolean",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceBooleanBytes());
        
        choice.selectIntBndType(7);
        printEncoded("Choice boxed int",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoiceIntBndBytes());
        
        choice.selectPlain(new TestPRN("bbbbbb"));
        printEncoded("Choice plain",encoder,choice);
        checkEncoded(encoder, choice, coderTestUtils.createDataChoicePlainBytes());
        
        choice.selectSimpleOctType(new byte[10] );
        printEncoded("Choice simple octet",encoder,choice);
        choice.selectIntType(7);
        printEncoded("Choice simple int",encoder,choice);
    }
        
    /**
     * @see Encoder#encode(T,OutputStream)
     */
    public void testEncode() throws Exception {
        IEncoder<DataSeq> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("SequenceMO test",encoder, coderTestUtils.createDataSeqMO());
        checkEncoded(encoder, coderTestUtils.createDataSeqMO(), coderTestUtils.createDataSeqMOBytes());
    
        printEncoded("Sequence test",encoder, coderTestUtils.createDataSeq());
        checkEncoded(encoder, coderTestUtils.createDataSeq(), coderTestUtils.createDataSeqBytes());
        
    }

    /**
     * @see Encoder#encode(T,OutputStream)
     */
    public void testITUEncode() throws Exception {
        IEncoder<ITUSequence> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("ITUSequence test",encoder, coderTestUtils.createITUSeq());
        checkEncoded(encoder, coderTestUtils.createITUSeq(), coderTestUtils.createITUSeqBytes());
    }

    protected void checkEncoded(IEncoder encoder, Object obj, byte[] standard) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(obj, outputStream);
        byte[] array = outputStream.toByteArray();
        assertEquals( array.length , standard. length);
        for(int i=0;i<array.length;i++) {            
            assertEquals(array[i],standard[i]);
        }
    }
    
    public void testNullEncode() throws Exception {
        IEncoder<NullSequence> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("NullSequence test",encoder, coderTestUtils.createNullSeq());
        checkEncoded(encoder, coderTestUtils.createNullSeq(), coderTestUtils.createNullSeqBytes());
    }
    
    public void testTaggedNullEncode() throws Exception {
        IEncoder<TaggedNullSequence> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TaggedNullSequence test",encoder, coderTestUtils.createTaggedNullSeq());
        checkEncoded(encoder, coderTestUtils.createTaggedNullSeq(), coderTestUtils.createTaggedNullSeqBytes());        
    }

    public void testSequenceWithNull() throws Exception {
        IEncoder<SequenceWithNull> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("SequenceWithNull test",encoder, coderTestUtils.createSeqWithNull());
        checkEncoded(encoder, coderTestUtils.createSeqWithNull(), coderTestUtils.createSeqWithNullBytes());
    }
    
    public void testEnum() throws Exception {
        IEncoder<ContentSchema> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Enum test",encoder, coderTestUtils.createEnum());
        checkEncoded(encoder, coderTestUtils.createEnum(), coderTestUtils.createEnumBytes());
    }    

    public void testSequenceWithEnum() throws Exception {
        IEncoder<SequenceWithEnum> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence Enum test",encoder, coderTestUtils.createSequenceWithEnum());
        checkEncoded(encoder, coderTestUtils.createSequenceWithEnum(), coderTestUtils.createSequenceWithEnumBytes());
    }
    
    public void testSequenceOfString() throws Exception {
        IEncoder<StringArray> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Sequence Of String",encoder, coderTestUtils.createStringArray());
        checkEncoded(encoder, coderTestUtils.createStringArray(), coderTestUtils.createStringArrayBytes());
    }
    
    
    public void testRecursiveDefinition() throws Exception {
        IEncoder<TestRecursiveDefinetion> encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Recursive test",encoder, coderTestUtils.createTestRecursiveDefinition());
        checkEncoded(encoder, coderTestUtils.createTestRecursiveDefinition(), coderTestUtils.createTestRecursiveDefinitionBytes());
    }
    
    
    public void testEncodeInteger() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Unbounded integer test",encoder, coderTestUtils.createUnboundedTestInteger());
        checkEncoded(encoder, coderTestUtils.createUnboundedTestInteger(), coderTestUtils.createUnboundedTestIntegerBytes());
        printEncoded("Integer2_12 test",encoder, coderTestUtils.createTestInteger2_12());
        checkEncoded(encoder, coderTestUtils.createTestInteger2_12(), coderTestUtils.createTestInteger2_12Bytes());
        
        printEncoded("IntegerR test",encoder, coderTestUtils.createTestIntegerR());
        checkEncoded(encoder, coderTestUtils.createTestIntegerR(), coderTestUtils.createTestIntegerRBytes());        
        
        printEncoded("Integer4 test",encoder, coderTestUtils.createTestInteger4());
        checkEncoded(encoder, coderTestUtils.createTestInteger4(), coderTestUtils.createTestInteger4Bytes());
        printEncoded("Integer3 test",encoder, coderTestUtils.createTestInteger3());
        checkEncoded(encoder, coderTestUtils.createTestInteger3(), coderTestUtils.createTestInteger3Bytes());
        printEncoded("Integer2 test",encoder, coderTestUtils.createTestInteger2());
        checkEncoded(encoder, coderTestUtils.createTestInteger2(), coderTestUtils.createTestInteger2Bytes());

        
        printEncoded("Integer1 test",encoder, coderTestUtils.createTestInteger1());
        checkEncoded(encoder, coderTestUtils.createTestInteger1(), coderTestUtils.createTestInteger1Bytes());        
                
    }
    
    public void testEncodeString() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("TestPRN",encoder, coderTestUtils.createTestPRN());
        checkEncoded(encoder, coderTestUtils.createTestPRN(), coderTestUtils.createTestPRNBytes());
        
        printEncoded("TestOCT",encoder, coderTestUtils.createTestOCT());
        checkEncoded(encoder, coderTestUtils.createTestOCT(), coderTestUtils.createTestOCTBytes());        
    }
    
    public void testNegativeInteger() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Negative integer test",encoder, coderTestUtils.createTestNI());
        checkEncoded(encoder, coderTestUtils.createTestNI(), coderTestUtils.createTestNIBytes());
        printEncoded("Negative integer test 2",encoder, coderTestUtils.createTestNI2());
        checkEncoded(encoder, coderTestUtils.createTestNI2(), coderTestUtils.createTestNI2Bytes());        
    }
}
