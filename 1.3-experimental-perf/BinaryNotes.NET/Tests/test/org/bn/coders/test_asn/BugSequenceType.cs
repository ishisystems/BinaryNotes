
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

namespace test.org.bn.coders.test_asn {


    [ASN1Sequence ( Name = "BugSequenceType", IsSet = false  )]
    public class BugSequenceType {
            
        
	private bool booleanField_ ;
	[ASN1Boolean( Name = "" )]
    
        [ASN1Element ( Name = "booleanField", IsOptional =  false , HasTag =  true, Tag = 0 , HasDefaultValue =  false )  ]
    
        public bool BooleanField
        {
            get { return booleanField_; }
            set { booleanField_ = value;  }
        }
        
                
  
        
	private long integerField_ ;
	[ASN1Integer( Name = "" )]
    
        [ASN1Element ( Name = "integerField", IsOptional =  false , HasTag =  true, Tag = 0 , HasDefaultValue =  false )  ]
    
        public long IntegerField
        {
            get { return integerField_; }
            set { integerField_ = value;  }
        }
        
                
  

            public void initWithDefaults() {
                
            }
            
    }
            
}