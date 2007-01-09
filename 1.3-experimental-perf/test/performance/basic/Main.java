
import protocol.*;

import java.io.*;
import java.util.*;

import org.bn.*;

public class Main
{
    public static final int ITERATIONS = 20000;

    private static final TestValueType[] MEMBER_VALUES =
    {
        makeValue(new String("first")),
        makeValue(new String("deadbeefdeadbeefdeadbeef")), 
        makeValue(Byte.MAX_VALUE),                         
        makeValue(Short.MAX_VALUE),                        
        makeValue(Long.MAX_VALUE),                         
        makeValue(Long.MAX_VALUE),                         
        makeValue(Byte.MAX_VALUE),                         
        makeValue(Long.MAX_VALUE),                         
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Short.MAX_VALUE),                        
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Short.MAX_VALUE),                        
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Short.MAX_VALUE),                        
        makeValue(new String("deadbeefdeadbeefdeadbeef")), 
        makeValue(new String("deadbeefdeadbeefdeadbeef")), 
        makeValue(Byte.MAX_VALUE),                         
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Short.MAX_VALUE),                        
        makeValue(Boolean.TRUE),                           
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Integer.MAX_VALUE),                      
        makeValue(Integer.MAX_VALUE),                      
        makeValue(new String("deadbeefdeadbeefdeadbeef")), 
        makeValue(Short.MAX_VALUE),                        
        makeValue(Long.MAX_VALUE),                         
        makeValue(new String("last")),
    };

    public Main()
        throws Exception
    {
        System.out.println("Fast BER coders");
        System.out.println("---------------");
        IEncoder<TestNotice> encoder = CoderFactory.getInstance().newEncoder("FastBER");
        IDecoder decoder = CoderFactory.getInstance().newDecoder("FastBER");
        testCoders(encoder, decoder);

        System.out.println();

        System.out.println("Conventional BER coders");
        System.out.println("-----------------------");
        encoder = CoderFactory.getInstance().newEncoder("BER");
        decoder = CoderFactory.getInstance().newDecoder("BER");
        testCoders(encoder, decoder);
        System.out.println();
    }

    private void testCoders(IEncoder<TestNotice> encoder,
                            IDecoder             decoder)
        throws Exception
    {
        TestNotice notice = makeNotice();
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);

        long encodeTotal = 0;
        long decodeTotal = 0;

        for (int index = 0; index < ITERATIONS; index++)
        {
            os.reset();
            long encodeStart = System.currentTimeMillis();
            encoder.encode(notice, os);
            encodeTotal += (System.currentTimeMillis() - encodeStart);

            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            long decodeStart = System.currentTimeMillis();
            TestNotice tmpNotice = decoder.decode(is, TestNotice.class);
            decodeTotal += (System.currentTimeMillis() - decodeStart);
        }

        double encodeSeconds = encodeTotal / 1000.0;
        double decodeSeconds = decodeTotal / 1000.0;
        double totalSeconds = (encodeTotal + decodeTotal) / 1000.0;
        double encodesPerSecond = ITERATIONS / encodeSeconds;
        double decodesPerSecond = ITERATIONS / decodeSeconds;
        System.out.println("          encoded size = " + os.size());
        System.out.println("           encode time = " + encodeSeconds + " seconds");
        System.out.println("           decode time = " + decodeSeconds + " seconds");
        System.out.println("            total time = " + totalSeconds + " seconds");
        System.out.println("    encodes per second = " + encodesPerSecond);
        System.out.println("    decodes per second = " + decodesPerSecond);
    }

    private TestNotice makeNotice()
    {
        Collection<TestValueType> members = new ArrayList<TestValueType>();
        for (int index = 0; index < MEMBER_VALUES.length; index++)
        {
            members.add(MEMBER_VALUES[index]);
        }

        TestStruct struct = new TestStruct(members);
        TestNotice notice = new TestNotice();
        notice.setTestProperties(struct);

        return notice;
    }

    private static TestValueType makeValue(Long value)
    {
        TestPrimitive primitive = new TestPrimitive();
        primitive.selectTestLong(value);

        TestValueType testValue = new TestValueType();
        testValue.selectTestPrimitive(primitive);

        return testValue;
    }

    private static TestValueType makeValue(Integer value)
    {
        TestPrimitive primitive = new TestPrimitive();
        primitive.selectTestInteger(new Long(value));

        TestValueType testValue = new TestValueType();
        testValue.selectTestPrimitive(primitive);

        return testValue;
    }

    private static TestValueType makeValue(Short value)
    {
        TestPrimitive primitive = new TestPrimitive();
        primitive.selectTestShort(new Long(value));

        TestValueType testValue = new TestValueType();
        testValue.selectTestPrimitive(primitive);

        return testValue;
    }

    private static TestValueType makeValue(Byte value)
    {
        TestPrimitive primitive = new TestPrimitive();
        primitive.selectTestByte(new Long(value));

        TestValueType testValue = new TestValueType();
        testValue.selectTestPrimitive(primitive);

        return testValue;
    }

    private static TestValueType makeValue(Boolean value)
    {
        TestPrimitive primitive = new TestPrimitive();
        primitive.selectTestBoolean(value);

        TestValueType testValue = new TestValueType();
        testValue.selectTestPrimitive(primitive);

        return testValue;
    }

    private static TestValueType makeValue(String value)
    {
        TestPrimitive primitive = new TestPrimitive();
        primitive.selectTestString(value);

        TestValueType testValue = new TestValueType();
        testValue.selectTestPrimitive(primitive);

        return testValue;
    }

    public static void main(String[] args)
        throws Exception
    {
        try
        {
            new Main();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}
