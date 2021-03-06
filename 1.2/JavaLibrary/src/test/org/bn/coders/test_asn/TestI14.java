
package test.org.bn.coders.test_asn;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;




    @ASN1BoxedType ( name = "TestI14" )
    public class TestI14 {
    
            @ASN1Integer( name = "TestI14" )
            @ASN1ValueRangeConstraint ( 
		min = 0L, 
		max = 16384L ) 
	   
            private Integer value;
            
            public TestI14() {
            }

            public TestI14(Integer value) {
                this.value = value;
            }
            
            public void setValue(Integer value) {
                this.value = value;
            }
            
            public Integer getValue() {
                return this.value;
            }
    }
            