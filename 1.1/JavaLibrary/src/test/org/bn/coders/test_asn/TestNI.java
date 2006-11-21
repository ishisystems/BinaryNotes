
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



    @ASN1BoxedType ( name = "TestNI" )
    public class TestNI {
    
            @ASN1Integer( name = "TestNI" )
            @ASN1ValueRangeConstraint ( 
		min = -128L, 
		max = 128L ) 
	   
            private Integer value;
            
            public TestNI() {
            }

            public TestNI(Integer value) {
                this.value = value;
            }
            
            public void setValue(Integer value) {
                this.value = value;
            }
            
            public Integer getValue() {
                return this.value;
            }
    }
            