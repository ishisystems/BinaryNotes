
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




    @ASN1Enum (
        name = "DeliveredStatus"
    )
    public class DeliveredStatus {        
        public enum EnumType {
            
            @ASN1EnumItem ( name = "unknown", hasTag = true , tag = 0 )
            unknown , 
            @ASN1EnumItem ( name = "delivered", hasTag = true , tag = 1 )
            delivered , 
            @ASN1EnumItem ( name = "expired", hasTag = true , tag = 2 )
            expired , 
            @ASN1EnumItem ( name = "partialDelivered", hasTag = true , tag = 3 )
            partialDelivered , 
            @ASN1EnumItem ( name = "noneConsumers", hasTag = true , tag = 4 )
            noneConsumers , 
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
    }
            