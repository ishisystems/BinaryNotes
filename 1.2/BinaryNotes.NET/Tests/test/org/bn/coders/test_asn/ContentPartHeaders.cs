
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


    [ASN1BoxedType ( Name = "ContentPartHeaders" ) ]
    public class ContentPartHeaders {

	    private System.Collections.Generic.ICollection<ContentPartHeader> val = null; 
            
            [ASN1SequenceOf( Name = "ContentPartHeaders", IsSetOf = false) ]
            
            public System.Collections.Generic.ICollection<ContentPartHeader> Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public void initValue() {
                this.Value = new System.Collections.Generic.List<ContentPartHeader>();
            }
            
            public void Add(ContentPartHeader item) {
                this.Value.Add(item);
            }
    }
            
}
