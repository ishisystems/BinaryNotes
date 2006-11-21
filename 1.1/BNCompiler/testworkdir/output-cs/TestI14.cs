
//
// This file was generated by the BinaryNotes compiler.
// See http://bn.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

using System;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;

namespace test.org.bn.coders.test_asn {


    [ASN1BoxedType ( Name = "TestI14" )]
    public class TestI14 {
    
            private int val;
            
            [ASN1Integer( Name = "TestI14" )]
            [ASN1ValueRangeConstraint ( 
		Min = 0L, 
		Max = 16384L ) ]
	    
            public int Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public TestI14() {
            }

            public TestI14(int value) {
                this.Value = value;
            }            
    }
            
}
