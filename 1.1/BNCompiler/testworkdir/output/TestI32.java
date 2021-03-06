
package test.org.bn.coders.test_asn;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;



    @ASN1BoxedType ( name = "TestI32" )
    public class TestI32 {
    
            @ASN1Integer( name = "TestI32" )
            @ASN1ValueRangeConstraint ( 
		min = 0L, 
		max = 4294967295L ) 
	   
            private Integer value;
            
            public TestI32() {
            }

            public TestI32(Integer value) {
                this.value = value;
            }
            
            public void setValue(Integer value) {
                this.value = value;
            }
            
            public Integer getValue() {
                return this.value;
            }
    }
            