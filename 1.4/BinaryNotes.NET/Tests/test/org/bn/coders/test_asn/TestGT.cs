
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


    [ASN1BoxedType ( Name = "TestGT") ]
    public class TestGT {

            private String val;
    
            [ASN1String( Name = "TestGT", 
        StringType = UniversalTags.GeneralizedTime , IsUCS = false) ]
            
            public String Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public TestGT() {
            }

            public TestGT(String val) {
                this.val = val;
            }            
    }
            
}