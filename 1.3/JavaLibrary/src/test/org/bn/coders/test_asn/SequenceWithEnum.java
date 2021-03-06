
package test.org.bn.coders.test_asn;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;




    @ASN1Sequence ( name = "SequenceWithEnum", isSet = false )
    public class SequenceWithEnum {
            
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "item", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String item = null;
                
  
        @ASN1Element ( name = "enval", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private ContentSchema enval = null;
                
  
        @ASN1Element ( name = "taggedEnval", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private ContentSchema taggedEnval = null;
                
  
        
        public String getItem () {
            return this.item;
        }

        

        public void setItem (String value) {
            this.item = value;
        }
        
  
        
        public ContentSchema getEnval () {
            return this.enval;
        }

        

        public void setEnval (ContentSchema value) {
            this.enval = value;
        }
        
  
        
        public ContentSchema getTaggedEnval () {
            return this.taggedEnval;
        }

        

        public void setTaggedEnval (ContentSchema value) {
            this.taggedEnval = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }
            
    }
            