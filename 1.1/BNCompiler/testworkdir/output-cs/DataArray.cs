
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


    [ASN1BoxedType ( Name = "DataArray") ]
    public class DataArray {

	    private System.Collections.Generic.ICollection<Data> val = null; 
            
            [ASN1SequenceOf( Name = "DataArray") ]
            
            public System.Collections.Generic.ICollection<Data> Value
            {
                get { return val; }
                set { val = value; }
            }
    }
            
}
