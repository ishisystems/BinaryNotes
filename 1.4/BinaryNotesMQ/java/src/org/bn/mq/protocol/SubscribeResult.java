
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




    @ASN1Sequence ( name = "SubscribeResult", isSet = false )
    public class SubscribeResult {
            
        @ASN1Element ( name = "code", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private SubscribeResultCode code = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "details", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private String details = null;
                
  
        
        public SubscribeResultCode getCode () {
            return this.code;
        }

        

        public void setCode (SubscribeResultCode value) {
            this.code = value;
        }
        
  
        
        public String getDetails () {
            return this.details;
        }

        
        public boolean isDetailsPresent () {
            return this.details == null;
        }
        

        public void setDetails (String value) {
            this.details = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }
            
    }
            