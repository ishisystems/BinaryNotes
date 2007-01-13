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
package test.org.bn.coders.ber;

import java.util.LinkedList;
import java.util.List;

import test.org.bn.coders.CoderTestUtilities;
import test.org.bn.coders.test_asn.*;

public class BERCoderTestUtils extends CoderTestUtilities {


    public byte[] createDataSeqBytes() {
        return new byte[] {
            0x30,0x36, (byte)0x80, 0x00, (byte)0x82, 0x00, (byte)0x83, 
                0x07,0x61,0x61,0x61,0x61,0x61,0x61,0x61,0x04,0x01, (byte)0xBA, (byte)0x85, 
                0x01,0x00, (byte)0x86, 0x01,0x00, (byte)0x87, 
                0x01,0x44, (byte)0xA8, 0x0F,0x13,0x06,0x62,0x62,0x62,0x62,0x62,0x62,0x13,
                0x05,0x63,0x63,0x63,0x63,0x63, (byte)0xA9, 0x09, (byte)0x80, 
                0x07,0x65,0x65,0x65,0x65,0x65,0x65,0x65, (byte)0xFF };
    }


    public byte[] createITUSeqBytes() {
        return new byte[] {
                    0x30,0x31,0x1A,0x05,0x61,0x61,0x61,
                    0x61,0x61,0x43,0x05,0x62,0x62,0x62,0x62,
                    0x62, (byte)0x82, 0x05,0x63,0x63,0x63,0x63,0x63,
                    0x47,0x05,0x63,0x63,0x63,0x63,0x63, (byte)0x82, 
                    0x05,0x63,0x63,0x63,0x63,0x63, (byte)0x87, 0x05,
                    0x64,0x64,0x64,0x64,0x64, (byte)0x88, 
                    0x05,0x65,0x65,0x65,0x65,0x65        
                };        
    }


    public byte[] createNullSeqBytes() {
        return new byte[] {
                    0x05, 0x00
                };
    }


    public byte[] createTaggedNullSeqBytes() {
        return new byte[] { 
                (byte)0x81, 0x00
            };
    }


    public byte[] createSeqWithNullBytes() {
        return new byte[] { 
            0x30,0x0C,0x13,0x03,0x73,0x73,0x73,0x05,0x00,(byte)0x81,0x03,0x64,0x64,0x64 
        };
    }


    public byte[] createEnumBytes() {
        return new byte[] {
                    0x0A,0x01,0x6F
                };    
    }


    public byte[] createSequenceWithEnumBytes() {
        return new byte[] {
            0x30,0x0D,0x13,0x05,0x61,0x61,0x61,0x61,0x61,0x0A,0x01,0x6F,(byte)0x81,0x01,0x6F
        };
    }


    public byte[] createTestRecursiveDefinitionBytes(){ 
        return new byte[] {
            0x30,0x10,(byte)0x81,0x05,0x61,0x61,0x61,0x61,0x61,(byte)0xA2,0x07,(byte)0x81,0x05,0x62,0x62,0x62,0x62,0x62
        };
    }


    public byte[] createTestInteger4Bytes() {
        return new byte[] {
            0x02,0x04,0x00,(byte)0xF0,(byte)0xF0,(byte)0xF0
        };
    }


    public byte[] createTestInteger3Bytes() {
        return new byte[] {
            0x02,0x03,0x00,(byte)0xFF,(byte)0xF0
        };
    }


    public byte[] createTestInteger2Bytes() {
        return new byte[] {
            0x02,0x02,0x0F,(byte)0xF0
        };
    }


    public byte[] createTestInteger1Bytes() {
        return new byte[] {
            0x02,0x01,0x0F
        };
    }

    public byte[] createUnboundedTestIntegerBytes() {
        return new byte[] {
            0x02,0x04,0x00,(byte)0xFA,(byte)0xFB,(byte)0xFC
        };
    }

    public byte[] createTestIntegerRBytes() {
        return new byte[] {
            0x02,0x01,0x03
        };
    }

    public byte[] createTestInteger2_12Bytes() {
        return new byte[] {
            0x02,0x02,0x1F,(byte)0xF1
        };
    }

    public byte[] createTestPRNBytes() {
        return new byte[] { 0x13, 0x05, 0x48, 0x65, 0x6C, 0x6C, 0x6F};
    }

    public byte[] createTestOCTBytes() {
        return new byte[] {0x04,0x05,0x01,0x02, (byte)0xFF, 0x03,0x04};
    }

    public byte[] createDataSeqMOBytes() {
        return new byte[] {0x30,0x47, (byte)0x80, 0x00, (byte)0x82, 0x00, (byte)0x83, 
                0x07,0x61,0x61,0x61,0x61,0x61,0x61,0x61,0x04,0x01, (byte)0xAB, (byte)0x85, 
                0x01, (byte)0xFF, (byte)0x86, 0x01,0x00, (byte)0x87, 
                0x01,0x00, (byte)0xA8, 0x0F,0x13,0x06,0x62,0x62,0x62,0x62,0x62,
                0x62,0x13,0x05,0x63,0x63,0x63,0x63,0x63, (byte)0xA9, 
                0x0F, (byte)0x80, 0x07,0x65,0x65,0x65,0x65,0x65,0x65,0x65, 
                (byte)0x80, 0x04,0x66,0x66,0x66,0x66, (byte)0x8E, 
                0x02,0x00, (byte)0xAA, (byte)0xB0, 
                0x06, (byte)0x83, 0x04,0x64,0x64,0x64,0x64 };
    }

    public byte[] createDataChoiceTestOCTBytes() {
        return new byte[] { (byte)0x82, 0x01, (byte)0xFF };
    }

    public byte[] createDataChoiceSimpleTypeBytes() {
        return new byte[] { (byte)0x83, 0x07,0x61,0x61,0x61,0x61,0x61,0x61,0x61};
    }

    public byte[] createDataChoiceBooleanBytes() {
        return new byte[] { (byte)0x85, 0x01, (byte)0xFF };
    }

    public byte[] createDataChoiceIntBndBytes() {
        return new byte[] { (byte)0x87, 0x01, 0x07 };
    }

    public byte[] createDataChoicePlainBytes() {
        return new byte[] { (byte)0x80, 0x06,0x62,0x62,0x62,0x62,0x62,0x62};
    }

    public byte[] createStringArrayBytes() {
        return new byte[] { 0x30,0x0F,0x13,0x06,0x62,0x62,0x62,0x62,0x62,0x62,0x13,0x05,0x63,0x63,0x63,0x63,0x63 };
    }

    public byte[] createTestNIBytes() {
        return new byte[] {0x02,0x01, (byte)0xF8 };
    }

    public byte[] createTestNI2Bytes() {
        return new byte[] {0x02,0x02, (byte)0xF8, 0x30};
    }

    public byte[] createSetBytes() {
        // Annotation version bytes is disabled
        /*return new byte[] {0x31,0x10, (byte)0x82, 0x02,0x00, (byte)0xAA, (byte)0x81, 
                0x04,0x61,0x61,0x61,0x61, (byte)0x83, 
                0x04,0x62,0x62,0x62,0x62}; */
        // Already prepared set
       return new byte[] {0x31,0x10, (byte)0x81, 
                 0x04,0x61,0x61,0x61,0x61,(byte)0x82, 0x02,0x00, (byte)0xAA,  (byte)0x83, 
                 0x04,0x62,0x62,0x62,0x62}; 
    }

    public byte[] createTestBitStrBytes() {
        return new byte[] {03,0x06,0x04, (byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD, (byte)0xF0 };
    }

    public byte[] createTestBitStrSmallBytes() {
        return new byte[] { 0x03 , 0x03, 0x04, (byte)0xAA, (byte)0xB0 };
    }

    public byte[] createUnicodeStrBytes() {
        return new byte[] { 0x0C,0x06, (byte)0xD1, (byte)0xA5, (byte)0xD1, (byte)0xA4, (byte)0xD1, (byte)0xA6 };
    }

    public byte[] createTestSequenceV12Bytes() {
        return new byte[] {0x30,0x33, (byte)0x80, 0x03,0x61,0x62,0x61, (byte)0x81, 
                0x04,0x63,0x63,0x63,0x63, (byte)0x82, 
                0x06,0x64,0x64,0x64,0x64,0x64,0x64, (byte)0xA3, 0x0B,0x13,0x04,0x61,0x61,0x61,0x61,0x13,0x03,0x62,0x62,0x62, (byte)0x84, 
                0x03,0x01, (byte)0x99, (byte)0x80, (byte)0x85, 
                0x02,0x04, (byte)0xF0, (byte)0x86, 
                0x02,0x04, (byte)0xA0, (byte)0x87, 0x04,0x0A,0x0B,0x0C,0x0D};
    }

    public byte[] createTestBitStrBndBytes() {
        return new byte[] { 0x03,0x02,0x04, (byte)0xF0 };
    }

    public byte[] createChoiceInChoiceBytes() {
        return new byte[] { (byte)0xA0, 0x03, (byte)0x81, 0x01,0x05};
    }

    public byte[] createTaggedSeqInSeqBytes() {
        return new byte[] { 0x64,0x0E, (byte)0xA0, 0x0C, (byte)0x81, 0x04,0x61,0x61,0x61,0x61, (byte)0x82, 0x04,0x62,0x62,0x62,0x62 };
    }

    public byte[] createTestReal0_5Bytes() {
        return new byte[] { 0x09,0x03, (byte)0x80, (byte)0xFF, 0x01} ;
    }

    public byte[] createTestReal1_5Bytes() {
        return new byte[] { 0x09,0x03, (byte)0x80, (byte)0xFF, 0x03 };
    }

    public byte[] createTestReal2Bytes() {
        return new byte[] { 0x09,0x03, (byte)0x80, 0x01,0x01 };
    }

    public byte[] createTestRealBigBytes() {
        return new byte[] { 0x09,0x05, (byte)0x80, (byte)0xFD, 0x18,0x6D,0x21};
    }

    public byte[] createChoiceInChoice2Bytes() {
        return new byte[] { 0x30,0x05, (byte)0xA0, 0x03, (byte)0x81, 0x01,0x05};
    }

    public byte[] createChoiceInChoice3Bytes() {
        /*return new byte[] {0x30,0x17, (byte)0xA2, 0x06, (byte)0x80, 0x01,0x00, (byte)0x80, 0x01,0x00, (byte)0xA2, 0x0D, (byte)0x80, 
                0x01,0x00, (byte)0x80, 0x08,0x7F, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF };*/
         return new byte[] {0x30,0x10, (byte)0xA2, 0x06, (byte)0x80, 0x01,0x00, (byte)0x80, 0x01,0x00, (byte)0xA2, 0x06, (byte)0x80, 
                0x01,0x00, (byte)0x80, 0x01,0x64 };                
    }

    public byte[] createTestLongTagBytes() {
        return new byte[] { 0x5F, (byte)0xF6, 0x13,0x02, 0x00, (byte)0xAA };
    }
}
