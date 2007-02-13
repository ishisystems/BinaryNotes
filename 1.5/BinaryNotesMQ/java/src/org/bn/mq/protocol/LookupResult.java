
package org.bn.mq.protocol;
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
    @ASN1Sequence ( name = "LookupResult", isSet = false )
    public class LookupResult implements IASN1PreparedElement {
            
        @ASN1Element ( name = "code", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private LookupResultCode code = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "details", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private String details = null;
                
  
        
        public LookupResultCode getCode () {
            return this.code;
        }

        

        public void setCode (LookupResultCode value) {
            this.code = value;
        }
        
  
        
        public String getDetails () {
            return this.details;
        }

        
        public boolean isDetailsPresent () {
            return this.details != null;
        }
        

        public void setDetails (String value) {
            this.details = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(LookupResult.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            