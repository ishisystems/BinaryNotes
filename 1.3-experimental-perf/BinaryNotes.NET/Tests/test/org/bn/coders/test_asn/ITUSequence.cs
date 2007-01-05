
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


    [ASN1Sequence ( Name = "ITUSequence", IsSet = false  )]
    public class ITUSequence {
            
        
	private string type1_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.VisibleString , IsUCS = false )]
        [ASN1Element ( Name = "type1", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string Type1
        {
            get { return type1_; }
            set { type1_ = value;  }
        }
        
                
  
        
	private ITUType1 type2_ ;
	
        [ASN1Element ( Name = "type2", IsOptional =  false , HasTag =  true, Tag = 3, 
        TagClass =  TagClasses.Application  , HasDefaultValue =  false )  ]
    
        public ITUType1 Type2
        {
            get { return type2_; }
            set { type2_ = value;  }
        }
        
                
  
        
	private ITUType2 type3_ ;
	
        [ASN1Element ( Name = "type3", IsOptional =  false , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public ITUType2 Type3
        {
            get { return type3_; }
            set { type3_ = value;  }
        }
        
                
  
        
	private ITUType3 type4_ ;
	
        [ASN1Element ( Name = "type4", IsOptional =  false , HasTag =  true, Tag = 7, 
        TagClass =  TagClasses.Application  , HasDefaultValue =  false )  ]
    
        public ITUType3 Type4
        {
            get { return type4_; }
            set { type4_ = value;  }
        }
        
                
  
        
	private ITUType2 type5_ ;
	
        private bool  type5_present = false ;
	
        [ASN1Element ( Name = "type5", IsOptional =  true , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public ITUType2 Type5
        {
            get { return type5_; }
            set { type5_ = value; type5_present = true;  }
        }
        
                
  
        
	private string type6_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.VisibleString , IsUCS = false )]
        [ASN1Element ( Name = "type6", IsOptional =  false , HasTag =  true, Tag = 7 , HasDefaultValue =  false )  ]
    
        public string Type6
        {
            get { return type6_; }
            set { type6_ = value;  }
        }
        
                
  
        
	private ITUType6 type7_ ;
	
        [ASN1Element ( Name = "type7", IsOptional =  false , HasTag =  true, Tag = 8 , HasDefaultValue =  false )  ]
    
        public ITUType6 Type7
        {
            get { return type7_; }
            set { type7_ = value;  }
        }
        
                
  
        public bool isType5Present () {
            return this.type5_present == true;
        }
        

            public void initWithDefaults() {
                
            }
            
    }
            
}
