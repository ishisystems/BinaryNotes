
//
// This file was generated by the BinaryNotes compiler.
// See http://bn.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

using System;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;

namespace test.org.bn.coders.test_asn {


    [ASN1Sequence ( Name = "SequenceWithEnum" )]
    public class SequenceWithEnum {
            
        
	private string item_ ;
	
        [ASN1Element ( Name = "item", IsOptional =  false , HasTag =  false ) ]
    [ASN1String( Name = "", 
        StringType =  UniversalTags.PrintableString , IsUCS = false )]
        public string Item
        {
            get { return item_; }
            set { item_ = value;  }
        }
        
                
  
        
	private ContentSchema enval_ ;
	
        [ASN1Element ( Name = "enval", IsOptional =  false , HasTag =  false ) ]
    
        public ContentSchema Enval
        {
            get { return enval_; }
            set { enval_ = value;  }
        }
        
                
  
        
	private ContentSchema taggedEnval_ ;
	
        [ASN1Element ( Name = "taggedEnval", IsOptional =  false , HasTag =  true, Tag = 1) ]
    
        public ContentSchema TaggedEnval
        {
            get { return taggedEnval_; }
            set { taggedEnval_ = value;  }
        }
        
                
  
    }
            
}
