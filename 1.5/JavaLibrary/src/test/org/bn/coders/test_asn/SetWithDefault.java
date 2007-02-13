
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
    @ASN1Sequence ( name = "SetWithDefault", isSet = true )
    public class SetWithDefault implements IASN1PreparedElement {
            @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "nodefault", isOptional =  false , hasTag =  true, tag = 2 , hasDefaultValue =  false  )
    
	private Long nodefault = null;
                
  
        @ASN1Element ( name = "nodefault2", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private TestPRN nodefault2 = null;
                
  
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "default3", isOptional =  false , hasTag =  true, tag = 3 , hasDefaultValue =  true  )
    
	private String default3 = null;
                
  
        
        public Long getNodefault () {
            return this.nodefault;
        }

        

        public void setNodefault (Long value) {
            this.nodefault = value;
        }
        
  
        
        public TestPRN getNodefault2 () {
            return this.nodefault2;
        }

        

        public void setNodefault2 (TestPRN value) {
            this.nodefault2 = value;
        }
        
  
        
        public String getDefault3 () {
            return this.default3;
        }

        

        public void setDefault3 (String value) {
            this.default3 = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            String param_Default3 =         
            new String ("DDDdd");
        setDefault3(param_Default3);
    
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(SetWithDefault.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            