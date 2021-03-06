
package org.bn.mq.examples.protocol;
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
    @ASN1Sequence ( name = "ExampleMessage", isSet = false )
    public class ExampleMessage implements IASN1PreparedElement {
            
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "field1", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String field1 = null;
                
  @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "field2", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private Long field2 = null;
                
  @ASN1OctetString( name = "" )
    
        @ASN1Element ( name = "field3", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private byte[] field3 = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTF8String , isUCS = false )
    
        @ASN1Element ( name = "field4", isOptional =  false , hasTag =  false  , hasDefaultValue =  true  )
    
	private String field4 = null;
                
  
        
        public String getField1 () {
            return this.field1;
        }

        

        public void setField1 (String value) {
            this.field1 = value;
        }
        
  
        
        public Long getField2 () {
            return this.field2;
        }

        

        public void setField2 (Long value) {
            this.field2 = value;
        }
        
  
        
        public byte[] getField3 () {
            return this.field3;
        }

        
        public boolean isField3Present () {
            return this.field3 != null;
        }
        

        public void setField3 (byte[] value) {
            this.field3 = value;
        }
        
  
        
        public String getField4 () {
            return this.field4;
        }

        

        public void setField4 (String value) {
            this.field4 = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            String param_Field4 =         
            new String ("Hello");
        setField4(param_Field4);
    
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(ExampleMessage.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            