
package test.org.bn.coders.test_asn;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;



    @ASN1Sequence ( name = "ContentPartHeader" )
    public class ContentPartHeader {
            
        @ASN1Element ( name = "name", isOptional =  false , hasTag =  true, tag = 0 )
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private String name = null;
                
  
        @ASN1Element ( name = "values", isOptional =  false , hasTag =  true, tag = 1 )
    @ASN1SequenceOf( name = "" ) 

	private java.util.Collection<ValueWithParams>  values = null;
                
  
        
        public String getName () {
            return this.name;
        }

        

        public void setName (String value) {
            this.name = value;
        }
        
  
        
        public java.util.Collection<ValueWithParams>  getValues () {
            return this.values;
        }

        

        public void setValues (java.util.Collection<ValueWithParams>  value) {
            this.values = value;
        }
        
  
    }
            