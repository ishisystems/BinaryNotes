
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


    [ASN1PreparedElement]
    [ASN1Sequence ( Name = "MessageUserBody", IsSet = false  )]
    public class MessageUserBody : IASN1PreparedElement {
            
        
	private byte[] userBody_ ;
	[ASN1OctetString( Name = "" )]
    
        [ASN1Element ( Name = "userBody", IsOptional =  false , HasTag =  true, Tag = 0 , HasDefaultValue =  false )  ]
    
        public byte[] UserBody
        {
            get { return userBody_; }
            set { userBody_ = value;  }
        }
        
                
  
        
	private string consumerId_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "consumerId", IsOptional =  false , HasTag =  true, Tag = 1 , HasDefaultValue =  false )  ]
    
        public string ConsumerId
        {
            get { return consumerId_; }
            set { consumerId_ = value;  }
        }
        
                
  
        
	private string queuePath_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "queuePath", IsOptional =  false , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public string QueuePath
        {
            get { return queuePath_; }
            set { queuePath_ = value;  }
        }
        
                
  
        
	private string senderId_ ;
	
        private bool  senderId_present = false ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "senderId", IsOptional =  true , HasTag =  true, Tag = 3 , HasDefaultValue =  false )  ]
    
        public string SenderId
        {
            get { return senderId_; }
            set { senderId_ = value; senderId_present = true;  }
        }
        
                
  
        public bool isSenderIdPresent () {
            return this.senderId_present == true;
        }
        

            public void initWithDefaults() {
                
            }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(MessageUserBody));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

            
    }
            
}
