
//
// This file was generated by the BinaryNotes compiler.
// See http://bn.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

using System;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;

namespace test.org.bn.coders.test_asn {


    [ASN1Enum ( Name = "ContentType")]
    public class ContentType {        
        public enum EnumType {
            
            [ASN1EnumItem ( Name = "text_any", HasTag = true , Tag = 100 )]
            text_any , 
            [ASN1EnumItem ( Name = "text_html", HasTag = true , Tag = 101 )]
            text_html , 
            [ASN1EnumItem ( Name = "text_plain", HasTag = true , Tag = 102 )]
            text_plain , 
            [ASN1EnumItem ( Name = "audio_x_midi", HasTag = true , Tag = 306 )]
            audio_x_midi , 
            [ASN1EnumItem ( Name = "video_any", HasTag = true , Tag = 400 )]
            video_any , 
            [ASN1EnumItem ( Name = "video_mpeg", HasTag = true , Tag = 401 )]
            video_mpeg , 
            [ASN1EnumItem ( Name = "video_avi", HasTag = true , Tag = 402 )]
            video_avi , 
            [ASN1EnumItem ( Name = "video_quicktime", HasTag = true , Tag = 403 )]
            video_quicktime , 
            [ASN1EnumItem ( Name = "video_x_msvideo", HasTag = true , Tag = 404 )]
            video_x_msvideo , 
            [ASN1EnumItem ( Name = "application_smil", HasTag = true , Tag = 500 )]
            application_smil , 
        }
        
        private EnumType val;
        
        public EnumType Value
        {
            get { return val; }
            set { val = value; }
        }        
                
    }
            
}
