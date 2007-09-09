
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
    @ASN1Sequence ( name = "TestSeqV13", isSet = false )
    public class TestSeqV13 implements IASN1PreparedElement {
            @ASN1Real( name = "" )
    
        @ASN1Element ( name = "field1", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private Double field1 = null;
                
  @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "fieldI", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private Long fieldI = null;
                
  
        @ASN1Element ( name = "field2", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private TestReal field2 = null;
                
  @ASN1Real( name = "" )
    
        @ASN1Element ( name = "field3", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private Double field3 = null;
                
  @ASN1Real( name = "" )
    
        @ASN1Element ( name = "field4", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private Double field4 = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.GeneralizedTime , isUCS = false )
    
        @ASN1Element ( name = "field5", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String field5 = null;
                
  
    @ASN1String( name = "", 
        stringType = UniversalTag.UTCTime , isUCS = false )
    
        @ASN1Element ( name = "field6", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String field6 = null;
                
  
        @ASN1Element ( name = "field7", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private TestLong field7 = null;
                
  
        
        public Double getField1 () {
            return this.field1;
        }

        

        public void setField1 (Double value) {
            this.field1 = value;
        }
        
  
        
        public Long getFieldI () {
            return this.fieldI;
        }

        

        public void setFieldI (Long value) {
            this.fieldI = value;
        }
        
  
        
        public TestReal getField2 () {
            return this.field2;
        }

        

        public void setField2 (TestReal value) {
            this.field2 = value;
        }
        
  
        
        public Double getField3 () {
            return this.field3;
        }

        
        public boolean isField3Present () {
            return this.field3 != null;
        }
        

        public void setField3 (Double value) {
            this.field3 = value;
        }
        
  
        
        public Double getField4 () {
            return this.field4;
        }

        

        public void setField4 (Double value) {
            this.field4 = value;
        }
        
  
        
        public String getField5 () {
            return this.field5;
        }

        

        public void setField5 (String value) {
            this.field5 = value;
        }
        
  
        
        public String getField6 () {
            return this.field6;
        }

        

        public void setField6 (String value) {
            this.field6 = value;
        }
        
  
        
        public TestLong getField7 () {
            return this.field7;
        }

        

        public void setField7 (TestLong value) {
            this.field7 = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(TestSeqV13.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            