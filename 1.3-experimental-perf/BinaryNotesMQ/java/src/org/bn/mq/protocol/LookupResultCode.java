
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
        name = "LookupResultCode"
    )
    public class LookupResultCode {        
        public enum EnumType {
            
            @ASN1EnumItem ( name = "success", hasTag = true , tag = 0 )
            success , 
            @ASN1EnumItem ( name = "notFound", hasTag = true , tag = 1 )
            notFound , 
            @ASN1EnumItem ( name = "accessDenied", hasTag = true , tag = 2 )
            accessDenied , 
            @ASN1EnumItem ( name = "unknown", hasTag = true , tag = 255 )
            unknown
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
            