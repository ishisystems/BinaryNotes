
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
using org.bn;

namespace test.org.bn.coders.test_asn {


    [ASN1PreparedElement]
    [ASN1BoxedType ( Name = "BugEnum" )]
    public class BugEnum: IASN1PreparedElement {
    
            private long val;
            
            [ASN1Integer( Name = "BugEnum" )]
            
            public long Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public BugEnum() {
            }

            public BugEnum(long value) {
                this.Value = value;
            }            

            public void initWithDefaults()
	    {
	    }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(BugEnum));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

    }
            
}
