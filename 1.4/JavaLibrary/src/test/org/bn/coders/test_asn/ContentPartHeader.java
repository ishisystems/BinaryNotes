
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




    @ASN1PreparedElement
    @ASN1Sequence ( name = "ContentPartHeader", isSet = false )
    public class ContentPartHeader implements IASN1PreparedElement {
            
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "name", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private String name = null;
                
  
@ASN1SequenceOf( name = "", isSetOf = false ) 

    
        @ASN1Element ( name = "values", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
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
        
  
                    
        
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(ContentPartHeader.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            