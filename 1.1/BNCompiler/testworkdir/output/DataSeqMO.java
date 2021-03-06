
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



    @ASN1Sequence ( name = "DataSeqMO" )
    public class DataSeqMO {
            
        @ASN1Element ( name = "plain", isOptional =  false , hasTag =  true, tag = 0 )
    
	private TestPRN plain = null;
                
  
        @ASN1Element ( name = "unicode", isOptional =  true , hasTag =  true, tag = 1 )
    
	private TestOCT unicode = null;
                
  
        @ASN1Element ( name = "binary", isOptional =  true , hasTag =  true, tag = 2 )
    
	private TestOCT binary = null;
                
  
        @ASN1Element ( name = "simpleType", isOptional =  true , hasTag =  true, tag = 3 )
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private String simpleType = null;
                
  
        @ASN1Element ( name = "simpleOctType", isOptional =  false , hasTag =  false  )
    @ASN1OctetString( name = "" )
    
	private byte[] simpleOctType = null;
                
  
        @ASN1Element ( name = "booleanType", isOptional =  true , hasTag =  true, tag = 5 )
    @ASN1Boolean( name = "" )
    
	private Boolean booleanType = null;
                
  
        @ASN1Element ( name = "intType", isOptional =  true , hasTag =  true, tag = 6 )
    @ASN1Integer( name = "" )
    
	private Integer intType = null;
                
  
        @ASN1Element ( name = "intBndType", isOptional =  true , hasTag =  true, tag = 7 )
    @ASN1Integer( name = "" )
    @ASN1ValueRangeConstraint ( 
		min = 0L, 
		max = 255L ) 
	   
	private Integer intBndType = null;
                
  
        @ASN1Element ( name = "stringArray", isOptional =  true , hasTag =  true, tag = 8 )
    @ASN1SequenceOf( name = "" ) 
@ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private java.util.Collection<String>  stringArray = null;
                
  
        @ASN1Element ( name = "dataArray", isOptional =  true , hasTag =  true, tag = 9 )
    @ASN1SequenceOf( name = "" ) 

	private java.util.Collection<Data>  dataArray = null;
                
  
        @ASN1Element ( name = "plain2", isOptional =  true , hasTag =  true, tag = 10 )
    
	private TestPRN plain2 = null;
                
  
        @ASN1Element ( name = "unicode2", isOptional =  true , hasTag =  true, tag = 18 )
    
	private TestOCT unicode2 = null;
                
  
        @ASN1Element ( name = "binary2", isOptional =  true , hasTag =  true, tag = 11 )
    
	private TestOCT binary2 = null;
                
  
        @ASN1Element ( name = "simpleType2", isOptional =  true , hasTag =  true, tag = 12 )
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private String simpleType2 = null;
                
  
        @ASN1Element ( name = "simpleOctType2", isOptional =  true , hasTag =  false  )
    @ASN1OctetString( name = "" )
    
	private byte[] simpleOctType2 = null;
                
  
        @ASN1Element ( name = "booleanType2", isOptional =  true , hasTag =  true, tag = 13 )
    @ASN1Boolean( name = "" )
    
	private Boolean booleanType2 = null;
                
  
        @ASN1Element ( name = "intType2", isOptional =  true , hasTag =  true, tag = 19 )
    @ASN1Integer( name = "" )
    
	private Integer intType2 = null;
                
  
        @ASN1Element ( name = "intBndType2", isOptional =  true , hasTag =  true, tag = 14 )
    @ASN1Integer( name = "" )
    @ASN1ValueRangeConstraint ( 
		min = 0L, 
		max = 255L ) 
	   
	private Integer intBndType2 = null;
                
  
        @ASN1Element ( name = "stringArray2", isOptional =  true , hasTag =  true, tag = 15 )
    @ASN1SequenceOf( name = "" ) 
@ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
	private java.util.Collection<String>  stringArray2 = null;
                
  
        @ASN1Element ( name = "dataArray2", isOptional =  true , hasTag =  true, tag = 16 )
    @ASN1SequenceOf( name = "" ) 

	private java.util.Collection<Data>  dataArray2 = null;
                
  
        @ASN1Element ( name = "plain3", isOptional =  true , hasTag =  true, tag = 17 )
    
	private TestPRN plain3 = null;
                
  
        
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

        
        public boolean isBinaryPresent () {
            return this.binary == null;
        }
        

        public void setBinary (TestOCT value) {
            this.binary = value;
        }
        
  
        
        public String getSimpleType () {
            return this.simpleType;
        }

        
        public boolean isSimpleTypePresent () {
            return this.simpleType == null;
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

        
        public boolean isBooleanTypePresent () {
            return this.booleanType == null;
        }
        

        public void setBooleanType (Boolean value) {
            this.booleanType = value;
        }
        
  
        
        public Integer getIntType () {
            return this.intType;
        }

        
        public boolean isIntTypePresent () {
            return this.intType == null;
        }
        

        public void setIntType (Integer value) {
            this.intType = value;
        }
        
  
        
        public Integer getIntBndType () {
            return this.intBndType;
        }

        
        public boolean isIntBndTypePresent () {
            return this.intBndType == null;
        }
        

        public void setIntBndType (Integer value) {
            this.intBndType = value;
        }
        
  
        
        public java.util.Collection<String>  getStringArray () {
            return this.stringArray;
        }

        
        public boolean isStringArrayPresent () {
            return this.stringArray == null;
        }
        

        public void setStringArray (java.util.Collection<String>  value) {
            this.stringArray = value;
        }
        
  
        
        public java.util.Collection<Data>  getDataArray () {
            return this.dataArray;
        }

        
        public boolean isDataArrayPresent () {
            return this.dataArray == null;
        }
        

        public void setDataArray (java.util.Collection<Data>  value) {
            this.dataArray = value;
        }
        
  
        
        public TestPRN getPlain2 () {
            return this.plain2;
        }

        
        public boolean isPlain2Present () {
            return this.plain2 == null;
        }
        

        public void setPlain2 (TestPRN value) {
            this.plain2 = value;
        }
        
  
        
        public TestOCT getUnicode2 () {
            return this.unicode2;
        }

        
        public boolean isUnicode2Present () {
            return this.unicode2 == null;
        }
        

        public void setUnicode2 (TestOCT value) {
            this.unicode2 = value;
        }
        
  
        
        public TestOCT getBinary2 () {
            return this.binary2;
        }

        
        public boolean isBinary2Present () {
            return this.binary2 == null;
        }
        

        public void setBinary2 (TestOCT value) {
            this.binary2 = value;
        }
        
  
        
        public String getSimpleType2 () {
            return this.simpleType2;
        }

        
        public boolean isSimpleType2Present () {
            return this.simpleType2 == null;
        }
        

        public void setSimpleType2 (String value) {
            this.simpleType2 = value;
        }
        
  
        
        public byte[] getSimpleOctType2 () {
            return this.simpleOctType2;
        }

        
        public boolean isSimpleOctType2Present () {
            return this.simpleOctType2 == null;
        }
        

        public void setSimpleOctType2 (byte[] value) {
            this.simpleOctType2 = value;
        }
        
  
        
        public Boolean getBooleanType2 () {
            return this.booleanType2;
        }

        
        public boolean isBooleanType2Present () {
            return this.booleanType2 == null;
        }
        

        public void setBooleanType2 (Boolean value) {
            this.booleanType2 = value;
        }
        
  
        
        public Integer getIntType2 () {
            return this.intType2;
        }

        
        public boolean isIntType2Present () {
            return this.intType2 == null;
        }
        

        public void setIntType2 (Integer value) {
            this.intType2 = value;
        }
        
  
        
        public Integer getIntBndType2 () {
            return this.intBndType2;
        }

        
        public boolean isIntBndType2Present () {
            return this.intBndType2 == null;
        }
        

        public void setIntBndType2 (Integer value) {
            this.intBndType2 = value;
        }
        
  
        
        public java.util.Collection<String>  getStringArray2 () {
            return this.stringArray2;
        }

        
        public boolean isStringArray2Present () {
            return this.stringArray2 == null;
        }
        

        public void setStringArray2 (java.util.Collection<String>  value) {
            this.stringArray2 = value;
        }
        
  
        
        public java.util.Collection<Data>  getDataArray2 () {
            return this.dataArray2;
        }

        
        public boolean isDataArray2Present () {
            return this.dataArray2 == null;
        }
        

        public void setDataArray2 (java.util.Collection<Data>  value) {
            this.dataArray2 = value;
        }
        
  
        
        public TestPRN getPlain3 () {
            return this.plain3;
        }

        
        public boolean isPlain3Present () {
            return this.plain3 == null;
        }
        

        public void setPlain3 (TestPRN value) {
            this.plain3 = value;
        }
        
  
    }
            