
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

namespace org.bn.mq.protocol {


    [ASN1Sequence ( Name = "UnsubscribeRequest", IsSet = false  )]
    public class UnsubscribeRequest {
            
        
	private string consumerId_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "consumerId", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string ConsumerId
        {
            get { return consumerId_; }
            set { consumerId_ = value;  }
        }
        
                
  
        
	private string queuePath_ ;
	
        private bool  queuePath_present = false ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "queuePath", IsOptional =  true , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string QueuePath
        {
            get { return queuePath_; }
            set { queuePath_ = value; queuePath_present = true;  }
        }
        
                
  
        public bool isQueuePathPresent () {
            return this.queuePath_present == true;
        }
        

            public void initWithDefaults() {
                
            }
            
    }
            
}
