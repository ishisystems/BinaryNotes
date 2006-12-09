
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
        name = "ContentType"
    )
    public class ContentType {        
        public enum EnumType {
            
            @ASN1EnumItem ( name = "text_any", hasTag = true , tag = 100 )
            text_any , 
            @ASN1EnumItem ( name = "text_html", hasTag = true , tag = 101 )
            text_html , 
            @ASN1EnumItem ( name = "text_plain", hasTag = true , tag = 102 )
            text_plain , 
            @ASN1EnumItem ( name = "audio_x_midi", hasTag = true , tag = 306 )
            audio_x_midi , 
            @ASN1EnumItem ( name = "video_any", hasTag = true , tag = 400 )
            video_any , 
            @ASN1EnumItem ( name = "video_mpeg", hasTag = true , tag = 401 )
            video_mpeg , 
            @ASN1EnumItem ( name = "video_avi", hasTag = true , tag = 402 )
            video_avi , 
            @ASN1EnumItem ( name = "video_quicktime", hasTag = true , tag = 403 )
            video_quicktime , 
            @ASN1EnumItem ( name = "video_x_msvideo", hasTag = true , tag = 404 )
            video_x_msvideo , 
            @ASN1EnumItem ( name = "application_smil", hasTag = true , tag = 500 )
            application_smil , 
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
            