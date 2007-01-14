
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
    @ASN1Sequence ( name = "SubscribeRequest", isSet = false )
    public class SubscribeRequest implements IASN1PreparedElement {
            
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "consumerId", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String consumerId = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "queuePath", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String queuePath = null;
                
  @ASN1Boolean( name = "" )
    
        @ASN1Element ( name = "persistence", isOptional =  false , hasTag =  false  , hasDefaultValue =  true  )
    
	private Boolean persistence = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "filter", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private String filter = null;
                
  
        
        public String getConsumerId () {
            return this.consumerId;
        }

        

        public void setConsumerId (String value) {
            this.consumerId = value;
        }
        
  
        
        public String getQueuePath () {
            return this.queuePath;
        }

        

        public void setQueuePath (String value) {
            this.queuePath = value;
        }
        
  
        
        public Boolean getPersistence () {
            return this.persistence;
        }

        

        public void setPersistence (Boolean value) {
            this.persistence = value;
        }
        
  
        
        public String getFilter () {
            return this.filter;
        }

        
        public boolean isFilterPresent () {
            return this.filter == null;
        }
        

        public void setFilter (String value) {
            this.filter = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            Boolean param_Persistence =         
            new Boolean (false);
        setPersistence(param_Persistence);
    
        }

        private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(SubscribeRequest.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            