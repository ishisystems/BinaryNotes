
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


    [ASN1PreparedElement]
    [ASN1BoxedType ( Name = "TestNI2" )]
    public class TestNI2: IASN1PreparedElement {
    
            private int val;
            
            [ASN1Integer( Name = "TestNI2" )]
            [ASN1ValueRangeConstraint ( 
		Min = -2048L, 
		Max = 2048L ) ]
	    
            public int Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public TestNI2() {
            }

            public TestNI2(int value) {
                this.Value = value;
            }            

            public void initWithDefaults()
	    {
	    }


            private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(typeof(TestNI2));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

    }
            
}
