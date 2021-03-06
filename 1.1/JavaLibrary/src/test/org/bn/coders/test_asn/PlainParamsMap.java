
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



    @ASN1Sequence ( name = "PlainParamsMap" )
    public class PlainParamsMap {
            
        @ASN1Element ( name = "param_name", isOptional =  false , hasTag =  true, tag = 1 )
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private String param_name = null;
                
  
        @ASN1Element ( name = "param_value", isOptional =  false , hasTag =  true, tag = 2 )
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private String param_value = null;
                
  
        
        public String getParam_name () {
            return this.param_name;
        }

        

        public void setParam_name (String value) {
            this.param_name = value;
        }
        
  
        
        public String getParam_value () {
            return this.param_value;
        }

        

        public void setParam_value (String value) {
            this.param_value = value;
        }
        
  
    }
            