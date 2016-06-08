
package test.org.bn.coders.test_asn;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.CoderFactory;
import org.bn.annotations.ASN1Element;
import org.bn.annotations.ASN1PreparedElement;
import org.bn.annotations.ASN1Sequence;
import org.bn.coders.IASN1PreparedElement;
import org.bn.coders.IASN1PreparedElementData;




    @ASN1PreparedElement
    @ASN1Sequence ( name = "TestSeqWithOIDType", isSet = false )
    public class TestSeqWithOIDType implements IASN1PreparedElement {
            
        @ASN1Element ( name = "protcolIdRef", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private TestOID protcolIdRef = null;
                
  
        
        public TestOID getProtcolIdRef () {
            return this.protcolIdRef;
        }

        

        public void setProtcolIdRef (TestOID value) {
            this.protcolIdRef = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(TestSeqWithOIDType.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            