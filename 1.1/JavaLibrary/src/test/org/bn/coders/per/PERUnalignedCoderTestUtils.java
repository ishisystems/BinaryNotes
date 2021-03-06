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
package test.org.bn.coders.per;

import test.org.bn.coders.CoderTestUtilities;

public class PERUnalignedCoderTestUtils extends CoderTestUtilities {
    public byte[] createNullSeqBytes() {
        return new byte[0];
    }

    public byte[] createTaggedNullSeqBytes() {
        return new byte[0];
    }

    public byte[] createEnumBytes() {
        return new byte[] { 0x20 } ;
    }

    public byte[] createITUSeqBytes() {
        return new byte[] { (byte)0x82, (byte)0xE1, (byte)0xC3, (byte)0x87, 
                0x0E,0x10,0x5C,0x58, (byte)0xB1, 0x62, (byte)0xC4, 
                0x0B, (byte)0x8F, 0x1E,0x3C,0x78, (byte)0xC1, 
                0x71, (byte)0xE3, (byte)0xC7, (byte)0x8F, 
                0x18,0x2E,0x3C,0x78, (byte)0xF1, (byte)0xE3, 
                0x05, (byte)0xC9, (byte)0x93, 0x26,0x4C, (byte)0x80, 
                (byte)0xB9, 0x72, (byte)0xE5, (byte)0xCB, (byte)0x94 };
    }

    public byte[] createDataSeqBytes() {
        return new byte[] { 0x40,0x00,0x01, (byte)0xF0, (byte)0xE1, (byte)0xC3, 
            (byte)0x87, 0x0E,0x1C,0x20,0x37,0x40,0x10,0x04,0x40,0x20,0x6C,0x58, (byte)0xB1, 
            0x62, (byte)0xC5, (byte)0x88, 0x17,0x1E,0x3C,0x78, (byte)0xF1, (byte)0x80, (byte)0x80, 
            0x7C, (byte)0xB9, 0x72, (byte)0xE5, (byte)0xCB, (byte)0x97, 0x2F, (byte)0xF8 };
    }

    public byte[] createSequenceWithEnumBytes() {
        return new byte[] { 0x05, (byte)0xC3, (byte)0x87, 0x0E,0x1C,0x24, (byte)0x80 };
    }

    public byte[] createTestInteger1Bytes() {
        return new byte[] { 0x0F };
    }

    public byte[] createTestInteger2Bytes() {
        return new byte[] { 0x0F, (byte)0xF0 };
    }

    public byte[] createTestInteger3Bytes() {
        return new byte[] { (byte)0xFF, (byte)0xF0 };
    }

    public byte[] createTestInteger4Bytes() {
        return new byte[] {
            0x00,(byte)0xF0,(byte)0xF0,(byte)0xF0
        };
    }

    public byte[] createSeqWithNullBytes() {
        return new byte[] { 0x03, (byte)0xE7, (byte)0xCF, (byte)0x98, 0x1E,0x4C, (byte)0x99, 0x00 } ;
    }

    public byte[] createTestRecursiveDefinitionBytes() {
        return new byte[] { (byte)0x82, (byte)0xE1, (byte)0xC3, (byte)0x87, 
                0x0E,0x10,0x2E,0x2C,0x58, (byte)0xB1, 0x62};
    }

    public byte[] createUnboundedTestIntegerBytes() {
        return new byte[] {
            0x04,0x00,(byte)0xFA,(byte)0xFB,(byte)0xFC
        };
    }

    public byte[] createTestIntegerRBytes() {
        return new byte[] { 0x40 };
    }

    public byte[] createTestInteger2_12Bytes() {
        return new byte[] { 0x3f, (byte)0xe2 };
    }

    public byte[] createTestPRNBytes() {
        return new byte[] {0x05, (byte)0x91, (byte)0x97, 0x66, (byte)0xCD, (byte)0xE0 };
    }

    public byte[] createTestOCTBytes() {
        return new byte[] {0x05,0x01,0x02, (byte)0xFF, 0x03, 0x04};
    }

    public byte[] createDataSeqMOBytes() {
        return new byte[] { 0x7F,0x01,0x40,0x00,0x00, (byte)0xF8, 0x70, (byte)0xE1, 
        (byte)0xC3, (byte)0x87, 0x0E,0x10,0x1A, (byte)0xB8, 0x08,0x00,0x00,0x10,
        0x36,0x2C,0x58, (byte)0xB1, 0x62, (byte)0xC4, 0x0B, (byte)0x8F, 
        0x1E,0x3C,0x78, (byte)0xC0, (byte)0x80, 0x3E,0x5C, 
        (byte)0xB9, 0x72, (byte)0xE5, (byte)0xCB, (byte)0x94, 0x02,
        0x66, (byte)0xCD, (byte)0x9B, 0x35,0x50,0x0B,0x04, (byte)0xC9, (byte)0x93, 0x26,0x40 };
    }

    public byte[] createDataChoiceTestOCTBytes() {
        return new byte[] {0x40,0x3f, (byte)0xe0 };
    }

    public byte[] createDataChoiceSimpleTypeBytes() {
        return new byte[] {0x60, (byte)0xF8, 0x70, (byte)0xE1, (byte)0xC3, (byte)0x87, 
                0x0E,0x10};
    }

    public byte[] createDataChoiceBooleanBytes() {
        return new byte[] { (byte)0xB0 };
    }

    public byte[] createDataChoiceIntBndBytes() {
        return new byte[] { (byte)0xE0, (byte)0xE0 };
    }

    public byte[] createDataChoicePlainBytes() {
        return new byte[] {0x00, (byte)0xD8, (byte)0xB1, 0x62, (byte)0xC5, (byte)0x8B, 
                0x10};
    }

    public byte[] createStringArrayBytes() {
        return new byte[] { 0x02,0x06, (byte)0xC5, (byte)0x8B, 0x16,0x2C,0x58, (byte)0x81, 0x71, (byte)0xE3, (byte)0xC7, (byte)0x8F, 
                0x18 };
    }

    public byte[] createTestNIBytes() {
        return new byte[] {0x3c, 0x00};
    }

    public byte[] createTestNI2Bytes() {
        return new byte[] {0x01, (byte)0x80 };
    }
}
