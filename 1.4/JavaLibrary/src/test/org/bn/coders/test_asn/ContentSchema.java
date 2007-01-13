
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




    @ASN1Enum (
        name = "ContentSchema"
    )
    public class ContentSchema implements IASN1PreparedElement {        
        public enum EnumType {
            
            @ASN1EnumItem ( name = "multipart_any", hasTag = true , tag = 110 )
            multipart_any , 
            @ASN1EnumItem ( name = "multipart_mixed", hasTag = true , tag = 111 )
            multipart_mixed , 
            @ASN1EnumItem ( name = "multipart_form_data", hasTag = true , tag = 112 )
            multipart_form_data , 
            @ASN1EnumItem ( name = "multipart_byteranges", hasTag = true , tag = 113 )
            multipart_byteranges , 
            @ASN1EnumItem ( name = "multipart_alternative", hasTag = true , tag = 114 )
            multipart_alternative , 
            @ASN1EnumItem ( name = "multipart_related", hasTag = true , tag = 175 )
            multipart_related
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

        private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(ContentSchema.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            