
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


    [ASN1BoxedType ( Name = "OctetStringArray") ]
    public class OctetStringArray {

	    private System.Collections.Generic.ICollection<byte[]> val = null; 
            
            [ASN1SequenceOf( Name = "OctetStringArray") ]
            [ASN1OctetString( Name = "" )]
    
            public System.Collections.Generic.ICollection<byte[]> Value
            {
                get { return val; }
                set { val = value; }
            }
    }
            
}
