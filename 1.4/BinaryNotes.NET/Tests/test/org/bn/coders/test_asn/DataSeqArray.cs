
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
    [ASN1BoxedType ( Name = "DataSeqArray" ) ]
    public class DataSeqArray : IASN1PreparedElement {

	    private System.Collections.Generic.ICollection<DataSeq> val = null; 
            
            [ASN1SequenceOf( Name = "DataSeqArray", IsSetOf = false) ]
            
            public System.Collections.Generic.ICollection<DataSeq> Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public void initValue() {
                this.Value = new System.Collections.Generic.List<DataSeq>();
            }
            
            public void Add(DataSeq item) {
                this.Value.Add(item);
            }

            public void initWithDefaults()
	    {
	    }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(DataSeqArray));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

    }
            
}
