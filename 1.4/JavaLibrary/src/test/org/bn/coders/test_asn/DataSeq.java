
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
    @ASN1Sequence ( name = "DataSeq", isSet = false )
    public class DataSeq implements IASN1PreparedElement {
            
        @ASN1Element ( name = "plain", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private TestPRN plain = null;
                
  
        @ASN1Element ( name = "unicode", isOptional =  true , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private TestOCT unicode = null;
                
  
        @ASN1Element ( name = "binary", isOptional =  false , hasTag =  true, tag = 2 , hasDefaultValue =  false  )
    
	private TestOCT binary = null;
                
  
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "simpleType", isOptional =  false , hasTag =  true, tag = 3 , hasDefaultValue =  false  )
    
	private String simpleType = null;
                
  @ASN1OctetString( name = "" )
    
        @ASN1Element ( name = "simpleOctType", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private byte[] simpleOctType = null;
                
  @ASN1Boolean( name = "" )
    
        @ASN1Element ( name = "booleanType", isOptional =  false , hasTag =  true, tag = 5 , hasDefaultValue =  false  )
    
	private Boolean booleanType = null;
                
  @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "intType", isOptional =  false , hasTag =  true, tag = 6 , hasDefaultValue =  false  )
    
	private Long intType = null;
                
  @ASN1Integer( name = "" )
    @ASN1ValueRangeConstraint ( 
		min = 0L, 
		max = 255L ) 
	   
        @ASN1Element ( name = "intBndType", isOptional =  false , hasTag =  true, tag = 7 , hasDefaultValue =  false  )
    
	private Integer intBndType = null;
                
  
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
@ASN1SequenceOf( name = "", isSetOf = false ) 

    
        @ASN1Element ( name = "stringArray", isOptional =  false , hasTag =  true, tag = 8 , hasDefaultValue =  false  )
    
	private java.util.Collection<String>  stringArray = null;
                
  
@ASN1SequenceOf( name = "", isSetOf = false ) 

    
        @ASN1Element ( name = "dataArray", isOptional =  false , hasTag =  true, tag = 9 , hasDefaultValue =  false  )
    
	private java.util.Collection<Data>  dataArray = null;
                
  @ASN1Any( name = "" )
    
        @ASN1Element ( name = "extension", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private byte[] extension = null;
                
  
        
        public TestPRN getPlain () {
            return this.plain;
        }

        

        public void setPlain (TestPRN value) {
            this.plain = value;
        }
        
  
        
        public TestOCT getUnicode () {
            return this.unicode;
        }

        
        public boolean isUnicodePresent () {
            return this.unicode == null;
        }
        

        public void setUnicode (TestOCT value) {
            this.unicode = value;
        }
        
  
        
        public TestOCT getBinary () {
            return this.binary;
        }

        

        public void setBinary (TestOCT value) {
            this.binary = value;
        }
        
  
        
        public String getSimpleType () {
            return this.simpleType;
        }

        

        public void setSimpleType (String value) {
            this.simpleType = value;
        }
        
  
        
        public byte[] getSimpleOctType () {
            return this.simpleOctType;
        }

        

        public void setSimpleOctType (byte[] value) {
            this.simpleOctType = value;
        }
        
  
        
        public Boolean getBooleanType () {
            return this.booleanType;
        }

        

        public void setBooleanType (Boolean value) {
            this.booleanType = value;
        }
        
  
        
        public Long getIntType () {
            return this.intType;
        }

        

        public void setIntType (Long value) {
            this.intType = value;
        }
        
  
        
        public Integer getIntBndType () {
            return this.intBndType;
        }

        

        public void setIntBndType (Integer value) {
            this.intBndType = value;
        }
        
  
        
        public java.util.Collection<String>  getStringArray () {
            return this.stringArray;
        }

        

        public void setStringArray (java.util.Collection<String>  value) {
            this.stringArray = value;
        }
        
  
        
        public java.util.Collection<Data>  getDataArray () {
            return this.dataArray;
        }

        

        public void setDataArray (java.util.Collection<Data>  value) {
            this.dataArray = value;
        }
        
  
        
        public byte[] getExtension () {
            return this.extension;
        }

        
        public boolean isExtensionPresent () {
            return this.extension == null;
        }
        

        public void setExtension (byte[] value) {
            this.extension = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(DataSeq.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            