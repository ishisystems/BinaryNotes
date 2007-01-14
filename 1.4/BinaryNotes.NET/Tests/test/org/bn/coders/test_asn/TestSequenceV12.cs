
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


    [ASN1PreparedElement]
    [ASN1Sequence ( Name = "TestSequenceV12", IsSet = false  )]
    public class TestSequenceV12 : IASN1PreparedElement {
            
        
	private string attrSimple_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
        [ASN1Element ( Name = "attrSimple", IsOptional =  false , HasTag =  true, Tag = 0 , HasDefaultValue =  false )  ]
    
        public string AttrSimple
        {
            get { return attrSimple_; }
            set { attrSimple_ = value;  }
        }
        
                
  
        
	private string attrStr_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )][ASN1ValueRangeConstraint ( 
		Min = 1L, 
		Max = 4L ) ]
	    
        [ASN1Element ( Name = "attrStr", IsOptional =  false , HasTag =  true, Tag = 1 , HasDefaultValue =  false )  ]
    
        public string AttrStr
        {
            get { return attrStr_; }
            set { attrStr_ = value;  }
        }
        
                
  
        
	private TestPRN attrStr2_ ;
	
        [ASN1Element ( Name = "attrStr2", IsOptional =  false , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public TestPRN AttrStr2
        {
            get { return attrStr2_; }
            set { attrStr2_ = value;  }
        }
        
                
  
        
	private System.Collections.Generic.ICollection<string> attrArr_ ;
	[ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
[ASN1SequenceOf( Name = "", IsSetOf = false  )]

    [ASN1ValueRangeConstraint ( 
		Min = 1L, 
		Max = 5L ) ]
	    
        [ASN1Element ( Name = "attrArr", IsOptional =  false , HasTag =  true, Tag = 3 , HasDefaultValue =  false )  ]
    
        public System.Collections.Generic.ICollection<string> AttrArr
        {
            get { return attrArr_; }
            set { attrArr_ = value;  }
        }
        
                
  
        
	private BitString attrBitStr_ ;
	
        private bool  attrBitStr_present = false ;
	[ASN1BitString( Name = "" )]
    
        [ASN1Element ( Name = "attrBitStr", IsOptional =  true , HasTag =  true, Tag = 4 , HasDefaultValue =  false )  ]
    
        public BitString AttrBitStr
        {
            get { return attrBitStr_; }
            set { attrBitStr_ = value; attrBitStr_present = true;  }
        }
        
                
  
        
	private BitString attrBitStrDef_ ;
	[ASN1BitString( Name = "" )]
    
        [ASN1Element ( Name = "attrBitStrDef", IsOptional =  false , HasTag =  true, Tag = 4 , HasDefaultValue =  true )  ]
    
        public BitString AttrBitStrDef
        {
            get { return attrBitStrDef_; }
            set { attrBitStrDef_ = value;  }
        }
        
                
  
        
	private BitString attrBitStrBnd_ ;
	
        private bool  attrBitStrBnd_present = false ;
	[ASN1BitString( Name = "" )]
    [ASN1ValueRangeConstraint ( 
		Min = 1L, 
		Max = 36L ) ]
	    
        [ASN1Element ( Name = "attrBitStrBnd", IsOptional =  true , HasTag =  true, Tag = 5 , HasDefaultValue =  false )  ]
    
        public BitString AttrBitStrBnd
        {
            get { return attrBitStrBnd_; }
            set { attrBitStrBnd_ = value; attrBitStrBnd_present = true;  }
        }
        
                
  
        
	private TestBitStrBnd attrBoxBitStr_ ;
	
        private bool  attrBoxBitStr_present = false ;
	
        [ASN1Element ( Name = "attrBoxBitStr", IsOptional =  true , HasTag =  true, Tag = 6 , HasDefaultValue =  false )  ]
    
        public TestBitStrBnd AttrBoxBitStr
        {
            get { return attrBoxBitStr_; }
            set { attrBoxBitStr_ = value; attrBoxBitStr_present = true;  }
        }
        
                
  
        
	private byte[] attrStrict_ ;
	[ASN1OctetString( Name = "" )]
    
            [ASN1SizeConstraint ( Max = 4L )]
        
        [ASN1Element ( Name = "attrStrict", IsOptional =  false , HasTag =  true, Tag = 7 , HasDefaultValue =  false )  ]
    
        public byte[] AttrStrict
        {
            get { return attrStrict_; }
            set { attrStrict_ = value;  }
        }
        
                
  
        public bool isAttrBitStrPresent () {
            return this.attrBitStr_present == true;
        }
        
        public bool isAttrBitStrBndPresent () {
            return this.attrBitStrBnd_present == true;
        }
        
        public bool isAttrBoxBitStrPresent () {
            return this.attrBoxBitStr_present == true;
        }
        

            public void initWithDefaults() {
                BitString param_AttrBitStrDef =         
            new BitString (CoderUtils.defStringToOctetString("'011'B"));
        AttrBitStrDef = param_AttrBitStrDef;
    
            }


            private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(typeof(TestSequenceV12));
            public IASN1PreparedElementData PreparedData {
            	get { return preparedData; }
            }

            
    }
            
}
