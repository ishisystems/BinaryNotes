
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
    @ASN1Sequence ( name = "TestSeqOID", isSet = false )
    public class TestSeqOID implements IASN1PreparedElement {
            @ASN1ObjectIdentifier( name = "" )
    
        @ASN1Element ( name = "field1", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private ObjectIdentifier field1 = null;
                
  @ASN1ObjectIdentifier( name = "" )
    
        @ASN1Element ( name = "field2", isOptional =  true , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private ObjectIdentifier field2 = null;
                
  @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "field3", isOptional =  false , hasTag =  true, tag = 2 , hasDefaultValue =  false  )
    
	private Long field3 = null;
                
  
        
        public ObjectIdentifier getField1 () {
            return this.field1;
        }

        

        public void setField1 (ObjectIdentifier value) {
            this.field1 = value;
        }
        
  
        
        public ObjectIdentifier getField2 () {
            return this.field2;
        }

        
        public boolean isField2Present () {
            return this.field2 != null;
        }
        

        public void setField2 (ObjectIdentifier value) {
            this.field2 = value;
        }
        
  
        
        public Long getField3 () {
            return this.field3;
        }

        

        public void setField3 (Long value) {
            this.field3 = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(TestSeqOID.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            