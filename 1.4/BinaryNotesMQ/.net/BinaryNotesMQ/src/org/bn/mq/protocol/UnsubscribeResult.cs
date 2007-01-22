
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

using System;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;
using org.bn.types;
using org.bn;

namespace org.bn.mq.protocol {


    [ASN1PreparedElement]
    [ASN1Sequence ( Name = "UnsubscribeResult", IsSet = false  )]
    public class UnsubscribeResult : IASN1PreparedElement {
            
        
	private UnsubscribeResultCode code_ ;
	
        [ASN1Element ( Name = "code", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public UnsubscribeResultCode Code
        {
            get { return code_; }
            set { code_ = value;  }
        }
        
                
  
        
	private string details_ ;
	
        private bool  details_present = false ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "details", IsOptional =  true , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string Details
        {
            get { return details_; }
            set { details_ = value; details_present = true;  }
        }
        
                
  
        public bool isDetailsPresent () {
            return this.details_present == true;
        }
        

            public void initWithDefaults() {
                
            }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(UnsubscribeResult));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

            
    }
            
}
