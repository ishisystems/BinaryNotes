using System;
using System.Collections.Generic;
using System.Text;

using org.bn;
using org.bn.coders;
using csUnit;
using test.org.bn.utils;
using test.org.bn.coders;
using test.org.bn.coders.ber;
using test.org.bn.coders.per;

namespace Tests
{
    class Program
    {
        static void runEncoderTest(EncoderTest test)
        {
            test.testEncode();
            test.testEncodeChoice();
            test.testEncodeInteger();
            test.testEncodeString();
            test.testEnum();
            test.testITUEncode();
            test.testNullEncode();
            test.testRecursiveDefinition();
            test.testSequenceOfString();
            test.testSequenceWithEnum();
            test.testSequenceWithNull();
            test.testTaggedNullEncode();
            test.testNegativeInteger();
        }

        static void runDecoderTest(DecoderTest test)
        {
            test.testDecode();
            test.testDecodeChoice();
            test.testDecodeInteger();
            test.testDecodeString();
            test.testDecodeStringArray();
            test.testEnum();
            test.testITUDeDecode();
            test.testNullDecode();
            test.testRecursiveDefinition();
            test.testSequenceWithEnum();
            test.testSequenceWithNullDecode();
            test.testTaggedNullDecode();
            test.testDecodeNegativeInteger();
        }

        [STAThread]
        static void Main(string[] args)
        {
            new BitArrayInputStreamTest("").testRead();
            new BitArrayOutputStreamTest("").testWrite();

            runEncoderTest(new BEREncoderTest(""));
            runEncoderTest(new PERAlignedEncoderTest(""));
            runEncoderTest(new PERUnalignedEncoderTest(""));

            runDecoderTest(new BERDecoderTest(""));
            runDecoderTest(new PERAlignedDecoderTest(""));
            runDecoderTest(new PERUnalignedDecoderTest(""));


        }
    }
}
