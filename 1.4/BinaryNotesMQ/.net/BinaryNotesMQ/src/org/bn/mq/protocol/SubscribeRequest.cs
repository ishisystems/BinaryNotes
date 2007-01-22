
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
    [ASN1Sequence ( Name = "SubscribeRequest", IsSet = false  )]
    public class SubscribeRequest : IASN1PreparedElement {
            
        
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
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "queuePath", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string QueuePath
        {
            get { return queuePath_; }
            set { queuePath_ = value;  }
        }
        
                
  
        
	private bool persistence_ ;
	[ASN1Boolean( Name = "" )]
    
        [ASN1Element ( Name = "persistence", IsOptional =  false , HasTag =  false  , HasDefaultValue =  true )  ]
    
        public bool Persistence
        {
            get { return persistence_; }
            set { persistence_ = value;  }
        }
        
                
  
        
	private string filter_ ;
	
        private bool  filter_present = false ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.UTF8String , IsUCS = false )]
        [ASN1Element ( Name = "filter", IsOptional =  true , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string Filter
        {
            get { return filter_; }
            set { filter_ = value; filter_present = true;  }
        }
        
                
  
        public bool isFilterPresent () {
            return this.filter_present == true;
        }
        

            public void initWithDefaults() {
                bool param_Persistence =         
            false;
        Persistence = param_Persistence;
    
            }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(SubscribeRequest));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

            
    }
            
}
