
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



    @ASN1BoxedType ( name = "DataArray" )
    public class DataArray {
    
            @ASN1SequenceOf( name = "DataArray" )
            
	    private java.util.Collection<Data> value = null; 
                        
            public void setValue(java.util.Collection<Data> value) {
                this.value = value;
            }
            
            public java.util.Collection<Data> getValue() {
                return this.value;
            }
    }
            