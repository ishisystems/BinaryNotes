
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
    @ASN1Enum (
        name = "UnsubscribeResultCode"
    )
    public class UnsubscribeResultCode implements IASN1PreparedElement {        
        public enum EnumType {
            
            @ASN1EnumItem ( name = "success", hasTag = true , tag = 0 )
            success , 
            @ASN1EnumItem ( name = "subscriptionNotExists", hasTag = true , tag = 1 )
            subscriptionNotExists , 
            @ASN1EnumItem ( name = "unknownQueue", hasTag = true , tag = 2 )
            unknownQueue , 
            @ASN1EnumItem ( name = "invalidConsumerId", hasTag = true , tag = 4 )
            invalidConsumerId , 
            @ASN1EnumItem ( name = "accessDenied", hasTag = true , tag = 5 )
            accessDenied , 
            @ASN1EnumItem ( name = "unknown", hasTag = true , tag = 255 )
            unknown , 
        }
        
        private EnumType value;
        private Integer integerForm;
        
        public EnumType getValue() {
            return this.value;
        }
        
        public void setValue(EnumType value) {
            this.value = value;
        }
        
        public Integer getIntegerForm() {
            return integerForm;
        }
        
        public void setIntegerForm(Integer value) {
            integerForm = value;
        }

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(UnsubscribeResultCode.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            