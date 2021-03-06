
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




    @ASN1Sequence ( name = "TestRecursiveDefinetion", isSet = false )
    public class TestRecursiveDefinetion {
            
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "name", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private String name = null;
                
  
        @ASN1Element ( name = "value", isOptional =  true , hasTag =  true, tag = 2 , hasDefaultValue =  false  )
    
	private TestRecursiveDefinetion value = null;
                
  
        
        public String getName () {
            return this.name;
        }

        

        public void setName (String value) {
            this.name = value;
        }
        
  
        
        public TestRecursiveDefinetion getValue () {
            return this.value;
        }

        
        public boolean isValuePresent () {
            return this.value == null;
        }
        

        public void setValue (TestRecursiveDefinetion value) {
            this.value = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }
            
    }
            