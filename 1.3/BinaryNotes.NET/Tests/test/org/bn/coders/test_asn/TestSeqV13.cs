
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


    [ASN1Sequence ( Name = "TestSeqV13", IsSet = false  )]
    public class TestSeqV13 {
            
        
	private double field1_ ;
	[ASN1Real( Name = "" )]
    
        [ASN1Element ( Name = "field1", IsOptional =  false , HasTag =  true, Tag = 0 , HasDefaultValue =  false )  ]
    
        public double Field1
        {
            get { return field1_; }
            set { field1_ = value;  }
        }
        
                
  
        
	private long fieldI_ ;
	[ASN1Integer( Name = "" )]
    
        [ASN1Element ( Name = "fieldI", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public long FieldI
        {
            get { return fieldI_; }
            set { fieldI_ = value;  }
        }
        
                
  
        
	private TestReal field2_ ;
	
        [ASN1Element ( Name = "field2", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public TestReal Field2
        {
            get { return field2_; }
            set { field2_ = value;  }
        }
        
                
  
        
	private double field3_ ;
	
        private bool  field3_present = false ;
	[ASN1Real( Name = "" )]
    
        [ASN1Element ( Name = "field3", IsOptional =  true , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public double Field3
        {
            get { return field3_; }
            set { field3_ = value; field3_present = true;  }
        }
        
                
  
        
	private double field4_ ;
	[ASN1Real( Name = "" )]
    
        [ASN1Element ( Name = "field4", IsOptional =  false , HasTag =  true, Tag = 1 , HasDefaultValue =  false )  ]
    
        public double Field4
        {
            get { return field4_; }
            set { field4_ = value;  }
        }
        
                
  
        
	private string field5_ ;
	[ASN1String( Name = "", 
        StringType = UniversalTags.GeneralizedTime , IsUCS = false )]
        [ASN1Element ( Name = "field5", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string Field5
        {
            get { return field5_; }
            set { field5_ = value;  }
        }
        
                
  
        
	private string field6_ ;
	[ASN1String( Name = "", 
        StringType = UniversalTags.UTCTime , IsUCS = false )]
        [ASN1Element ( Name = "field6", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public string Field6
        {
            get { return field6_; }
            set { field6_ = value;  }
        }
        
                
  
        
	private TestLong field7_ ;
	
        [ASN1Element ( Name = "field7", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public TestLong Field7
        {
            get { return field7_; }
            set { field7_ = value;  }
        }
        
                
  
        public bool isField3Present () {
            return this.field3_present == true;
        }
        

            public void initWithDefaults() {
                
            }
            
    }
            
}
