
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

using System;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;
using org.bn.types;

namespace test.org.bn.coders.test_asn {


    [ASN1BoxedType ( Name = "TestBitStr") ]
    public class TestBitStr {
    
            private BitString val = null;

            [ASN1BitString( Name = "TestBitStr") ]            
            
            public BitString Value
            {
                get { return val; }
                set { val = value; }
            }            
            
            public TestBitStr() {
            }

            public TestBitStr(BitString value) {
                this.Value = value;
            }            
    }
            
}
