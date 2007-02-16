
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

namespace test.org.bn.coders.test_asn {


    [ASN1PreparedElement]
    [ASN1Sequence ( Name = "DataSeq", IsSet = false  )]
    public class DataSeq : IASN1PreparedElement {
                    
	private TestPRN plain_ ;
	
        [ASN1Element ( Name = "plain", IsOptional =  false , HasTag =  true, Tag = 0 , HasDefaultValue =  false )  ]
    
        public TestPRN Plain
        {
            get { return plain_; }
            set { plain_ = value;  }
        }
        
                
          
	private TestOCT unicode_ ;
	
        private bool  unicode_present = false ;
	
        [ASN1Element ( Name = "unicode", IsOptional =  true , HasTag =  true, Tag = 1 , HasDefaultValue =  false )  ]
    
        public TestOCT Unicode
        {
            get { return unicode_; }
            set { unicode_ = value; unicode_present = true;  }
        }
        
                
          
	private TestOCT binary_ ;
	
        [ASN1Element ( Name = "binary", IsOptional =  false , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public TestOCT Binary
        {
            get { return binary_; }
            set { binary_ = value;  }
        }
        
                
          
	private string simpleType_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
        [ASN1Element ( Name = "simpleType", IsOptional =  false , HasTag =  true, Tag = 3 , HasDefaultValue =  false )  ]
    
        public string SimpleType
        {
            get { return simpleType_; }
            set { simpleType_ = value;  }
        }
        
                
          
	private byte[] simpleOctType_ ;
	[ASN1OctetString( Name = "" )]
    
        [ASN1Element ( Name = "simpleOctType", IsOptional =  false , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public byte[] SimpleOctType
        {
            get { return simpleOctType_; }
            set { simpleOctType_ = value;  }
        }
        
                
          
	private bool booleanType_ ;
	[ASN1Boolean( Name = "" )]
    
        [ASN1Element ( Name = "booleanType", IsOptional =  false , HasTag =  true, Tag = 5 , HasDefaultValue =  false )  ]
    
        public bool BooleanType
        {
            get { return booleanType_; }
            set { booleanType_ = value;  }
        }
        
                
          
	private long intType_ ;
	[ASN1Integer( Name = "" )]
    
        [ASN1Element ( Name = "intType", IsOptional =  false , HasTag =  true, Tag = 6 , HasDefaultValue =  false )  ]
    
        public long IntType
        {
            get { return intType_; }
            set { intType_ = value;  }
        }
        
                
          
	private int intBndType_ ;
	[ASN1Integer( Name = "" )]
    [ASN1ValueRangeConstraint ( 
		Min = 0L, 
		Max = 255L ) ]
	    
        [ASN1Element ( Name = "intBndType", IsOptional =  false , HasTag =  true, Tag = 7 , HasDefaultValue =  false )  ]
    
        public int IntBndType
        {
            get { return intBndType_; }
            set { intBndType_ = value;  }
        }
        
                
          
	private System.Collections.Generic.ICollection<string> stringArray_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
[ASN1SequenceOf( Name = "", IsSetOf = false  )]

    
        [ASN1Element ( Name = "stringArray", IsOptional =  false , HasTag =  true, Tag = 8 , HasDefaultValue =  false )  ]
    
        public System.Collections.Generic.ICollection<string> StringArray
        {
            get { return stringArray_; }
            set { stringArray_ = value;  }
        }
        
                
          
	private System.Collections.Generic.ICollection<Data> dataArray_ ;
	
[ASN1SequenceOf( Name = "", IsSetOf = false  )]

    
        [ASN1Element ( Name = "dataArray", IsOptional =  false , HasTag =  true, Tag = 9 , HasDefaultValue =  false )  ]
    
        public System.Collections.Generic.ICollection<Data> DataArray
        {
            get { return dataArray_; }
            set { dataArray_ = value;  }
        }
        
                
          
	private byte[] extension_ ;
	
        private bool  extension_present = false ;
	[ASN1Any( Name = "" )]
    
        [ASN1Element ( Name = "extension", IsOptional =  true , HasTag =  false  , HasDefaultValue =  false )  ]
    
        public byte[] Extension
        {
            get { return extension_; }
            set { extension_ = value; extension_present = true;  }
        }
        
                
  
        public bool isUnicodePresent () {
            return this.unicode_present == true;
        }
        
        public bool isExtensionPresent () {
            return this.extension_present == true;
        }
        

            public void initWithDefaults() {
            	
            }


            private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(typeof(DataSeq));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

            
    }
            
}
