
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
    @ASN1Sequence ( name = "MessageUserBody", isSet = false )
    public class MessageUserBody implements IASN1PreparedElement {
            @ASN1OctetString( name = "" )
    
        @ASN1Element ( name = "userBody", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private byte[] userBody = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "consumerId", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private String consumerId = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "queuePath", isOptional =  false , hasTag =  true, tag = 2 , hasDefaultValue =  false  )
    
	private String queuePath = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "senderId", isOptional =  true , hasTag =  true, tag = 3 , hasDefaultValue =  false  )
    
	private String senderId = null;
                
  
        
        public byte[] getUserBody () {
            return this.userBody;
        }

        

        public void setUserBody (byte[] value) {
            this.userBody = value;
        }
        
  
        
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
        
  
        
        public String getSenderId () {
            return this.senderId;
        }

        
        public boolean isSenderIdPresent () {
            return this.senderId != null;
        }
        

        public void setSenderId (String value) {
            this.senderId = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(MessageUserBody.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            