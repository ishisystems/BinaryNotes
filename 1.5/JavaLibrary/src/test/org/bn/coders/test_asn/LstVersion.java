
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
    @ASN1BoxedType ( name = "LstVersion" )
    public class LstVersion implements IASN1PreparedElement {
                
        
@ASN1SequenceOf( name = "LstVersion", isSetOf = true ) 

    
        @ASN1Element ( name = "LstVersion", isOptional =  false , hasTag =  true, tag = 75, 
        tagClass =  TagClass.Application  , hasDefaultValue =  false  )
    
        private java.util.Collection<Version>   value;        

        
        
        public LstVersion () {
        }
        
        
        
        public void setValue(java.util.Collection<Version>  value) {
            this.value = value;
        }
        
        
        
        public java.util.Collection<Version>  getValue() {
            return this.value;
        }            
        

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(LstVersion.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            