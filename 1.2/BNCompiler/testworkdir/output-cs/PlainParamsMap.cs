
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


    [ASN1Sequence ( Name = "PlainParamsMap", IsSet = false  )]
    public class PlainParamsMap {
            
        
	private string param_name_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
        [ASN1Element ( Name = "param_name", IsOptional =  false , HasTag =  true, Tag = 1 , HasDefaultValue =  false )  ]
    
        public string Param_name
        {
            get { return param_name_; }
            set { param_name_ = value;  }
        }
        
                
  
        
	private string param_value_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
        [ASN1Element ( Name = "param_value", IsOptional =  false , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public string Param_value
        {
            get { return param_value_; }
            set { param_value_ = value;  }
        }
        
                
  

            public void initWithDefaults() {
                
            }
            
    }
            
}
