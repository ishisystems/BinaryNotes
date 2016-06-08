package test.org.bn.coders.ber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.bn.CoderFactory;
import org.bn.IDecoder;
import org.bn.IEncoder;
import org.bn.types.ObjectIdentifier;

import junit.framework.TestCase;
import test.org.bn.coders.test_asn.TestOID;
import test.org.bn.coders.test_asn.TestSeqOID;
import test.org.bn.coders.test_asn.TestSeqWithOID;
import test.org.bn.coders.test_asn.TestSeqWithOIDType;
import test.org.bn.utils.ByteTools;

/**
 * Created by viral.patel on 6/7/2016.
 */
public class BEREncoderDecoderTest extends TestCase {

    CoderFactory coderFactory = new CoderFactory();

    public void testEncodeOIDInSequence() throws Exception {
        //
        TestSeqOID testSeqOID = new TestSeqOID();
        testSeqOID.setField1(new ObjectIdentifier("2.5.4.6"));  // CountryName
        testSeqOID.setField2(new ObjectIdentifier("1.2.840.113549.1.1.5"));  // sha1withRSAEncryption
        testSeqOID.setField3(100L);  // CountryName

        IEncoder encoder = newEncoder();
        assertNotNull(encoder);
        printEncoded("Encoded Hex", encoder, testSeqOID);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(testSeqOID, outputStream);

        byte[] encodedBytes = outputStream.toByteArray();

        System.out.println("Encoded bytes: " + Arrays.toString(encodedBytes));

        IDecoder decoder = newDecoder();
        assertNotNull(decoder);

        ByteArrayInputStream stream = new ByteArrayInputStream(encodedBytes);

        TestSeqOID testSeqOIDDecoded = decoder.decode(stream, TestSeqOID.class);
        assertEquals(testSeqOID.getField1().getValue(), testSeqOIDDecoded.getField1().getValue());
        assertEquals("1.2.840.113549.1.1.5", testSeqOIDDecoded.getField2().getValue());
        assertEquals(100L, testSeqOIDDecoded.getField3().longValue());
    }

    public void testSeqWithOID() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);

        TestSeqWithOID testSeqWithOID = new TestSeqWithOID();
        testSeqWithOID.setProtcolId(new ObjectIdentifier("2.5.4.6"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(testSeqWithOID, outputStream);

        byte[] encodedBytes = outputStream.toByteArray();
        System.out.println("Encoded Hex:   " + ByteTools.byteArrayToHexString(encodedBytes));
        System.out.println("Encoded bytes: " + Arrays.toString(encodedBytes));

        IDecoder decoder = newDecoder();
        assertNotNull(decoder);

        ByteArrayInputStream stream = new ByteArrayInputStream(encodedBytes);

        TestSeqWithOID testSeqWithOIDdecoded = decoder.decode(stream, TestSeqWithOID.class);
        assertEquals(testSeqWithOID.getProtcolId().getValue(), testSeqWithOIDdecoded.getProtcolId().getValue());
    }

    public void testSeqWithOIDType() throws Exception {
        IEncoder encoder = newEncoder();
        assertNotNull(encoder);

        TestSeqWithOIDType testSeqWithOID = new TestSeqWithOIDType();
        TestOID testOID = new TestOID();
        String oidValue = "2.5.4.6";
        testOID.setValue(new ObjectIdentifier(oidValue));
        testSeqWithOID.setProtcolIdRef(testOID);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(testSeqWithOID, outputStream);

        byte[] encodedBytes = outputStream.toByteArray();
        System.out.println("Encoded Hex:   " + ByteTools.byteArrayToHexString(encodedBytes));
        System.out.println("Encoded bytes: " + Arrays.toString(encodedBytes));

        IDecoder decoder = newDecoder();
        assertNotNull(decoder);

        ByteArrayInputStream stream = new ByteArrayInputStream(encodedBytes);

        TestSeqWithOIDType decoded = decoder.decode(stream, TestSeqWithOIDType.class);
        assertEquals(oidValue, decoded.getProtcolIdRef().getValue().getValue());
    }

    protected IDecoder newDecoder() throws Exception {
        return coderFactory.newDecoder("BER");
    }

    protected <T> IEncoder<T> newEncoder() throws Exception {
        return coderFactory.newEncoder("BER");
    }

    protected void printEncoded(String details, IEncoder encoder, Object obj) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encoder.encode(obj, outputStream);
        System.out.println("Encoded by " + encoder.getClass() + " (" + details + ") : " + ByteTools.byteArrayToHexString(outputStream.toByteArray()));
    }
}
