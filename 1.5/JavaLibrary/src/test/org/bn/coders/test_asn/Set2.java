
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




    @ASN1PreparedElement
    @ASN1BoxedType ( name = "Set2" )
    public class Set2 implements IASN1PreparedElement {
                
        
@ASN1SequenceOf( name = "Set2", isSetOf = true ) 

    
        @ASN1Element ( name = "Set2", isOptional =  false , hasTag =  true, tag = 128, 
        tagClass =  TagClass.Application  , hasDefaultValue =  false  )
    
        private java.util.Collection<Set1>   value;        

        
        
        public Set2 () {
        }
        
        
        
        public void setValue(java.util.Collection<Set1>  value) {
            this.value = value;
        }
        
        
        
        public java.util.Collection<Set1>  getValue() {
            return this.value;
        }            
        

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(Set2.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            