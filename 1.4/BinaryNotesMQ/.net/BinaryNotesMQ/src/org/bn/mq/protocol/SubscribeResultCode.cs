
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
    [ASN1Enum ( Name = "SubscribeResultCode")]
    public class SubscribeResultCode : IASN1PreparedElement {        
        public enum EnumType {
            
            [ASN1EnumItem ( Name = "success", HasTag = true , Tag = 0 )]
            success , 
            [ASN1EnumItem ( Name = "alreadySubscription", HasTag = true , Tag = 1 )]
            alreadySubscription , 
            [ASN1EnumItem ( Name = "unknownQueue", HasTag = true , Tag = 2 )]
            unknownQueue , 
            [ASN1EnumItem ( Name = "persistenceNotAvailable", HasTag = true , Tag = 3 )]
            persistenceNotAvailable , 
            [ASN1EnumItem ( Name = "invalidConsumerId", HasTag = true , Tag = 4 )]
            invalidConsumerId , 
            [ASN1EnumItem ( Name = "accessDenied", HasTag = true , Tag = 5 )]
            accessDenied , 
            [ASN1EnumItem ( Name = "unknown", HasTag = true , Tag = 255 )]
            unknown , 
        }
        
        private EnumType val;
        
        public EnumType Value
        {
            get { return val; }
            set { val = value; }
        }        

            public void initWithDefaults()
	    {
	    }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(SubscribeResultCode));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

                
    }
            
}
