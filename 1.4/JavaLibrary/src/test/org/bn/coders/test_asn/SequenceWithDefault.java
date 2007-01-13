
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
    @ASN1Sequence ( name = "SequenceWithDefault", isSet = false )
    public class SequenceWithDefault implements IASN1PreparedElement {
            @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "nodefault", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private Long nodefault = null;
                
  
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "withDefault", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  true  )
    
	private String withDefault = null;
                
  @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "withIntDef", isOptional =  false , hasTag =  true, tag = 2 , hasDefaultValue =  true  )
    
	private Long withIntDef = null;
                
  

       @ASN1PreparedElement
       @ASN1Sequence ( name = "withSeqDef" , isSet = false )
       public class WithSeqDefSequenceType implements IASN1PreparedElement {
                
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "name", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String name = null;
                
  
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
        @ASN1Element ( name = "email", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private String email = null;
                
  
        
        public String getName () {
            return this.name;
        }

        

        public void setName (String value) {
            this.name = value;
        }
        
  
        
        public String getEmail () {
            return this.email;
        }

        

        public void setEmail (String value) {
            this.email = value;
        }
        
  
                
                
        public void initWithDefaults() {
            
        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_WithSeqDefSequenceType;
        }

                
       }
       private static IASN1PreparedElementData preparedData_WithSeqDefSequenceType = new ASN1PreparedElementData(WithSeqDefSequenceType.class);

       
                
        @ASN1Element ( name = "withSeqDef", isOptional =  false , hasTag =  true, tag = 3 , hasDefaultValue =  true  )
    
	private WithSeqDefSequenceType withSeqDef = null;
                
  
        @ASN1Element ( name = "withOctDef", isOptional =  false , hasTag =  true, tag = 4 , hasDefaultValue =  true  )
    
	private TestOCT withOctDef = null;
                
  @ASN1OctetString( name = "" )
    
        @ASN1Element ( name = "withOctDef2", isOptional =  false , hasTag =  true, tag = 5 , hasDefaultValue =  true  )
    
	private byte[] withOctDef2 = null;
                
  
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
@ASN1SequenceOf( name = "", isSetOf = false ) 

    
        @ASN1Element ( name = "withSeqOf", isOptional =  false , hasTag =  true, tag = 6 , hasDefaultValue =  true  )
    
	private java.util.Collection<String>  withSeqOf = null;
                
  
@ASN1SequenceOf( name = "", isSetOf = false ) 

    
        @ASN1Element ( name = "withSeqOf2", isOptional =  false , hasTag =  true, tag = 7 , hasDefaultValue =  true  )
    
	private java.util.Collection<TestPRN>  withSeqOf2 = null;
                
  
        @ASN1Element ( name = "withSeqOf3", isOptional =  false , hasTag =  true, tag = 8 , hasDefaultValue =  true  )
    
	private StringArray withSeqOf3 = null;
                
  
        
        public Long getNodefault () {
            return this.nodefault;
        }

        

        public void setNodefault (Long value) {
            this.nodefault = value;
        }
        
  
        
        public String getWithDefault () {
            return this.withDefault;
        }

        

        public void setWithDefault (String value) {
            this.withDefault = value;
        }
        
  
        
        public Long getWithIntDef () {
            return this.withIntDef;
        }

        

        public void setWithIntDef (Long value) {
            this.withIntDef = value;
        }
        
  
        
        public WithSeqDefSequenceType getWithSeqDef () {
            return this.withSeqDef;
        }

        

        public void setWithSeqDef (WithSeqDefSequenceType value) {
            this.withSeqDef = value;
        }
        
  
        
        public TestOCT getWithOctDef () {
            return this.withOctDef;
        }

        

        public void setWithOctDef (TestOCT value) {
            this.withOctDef = value;
        }
        
  
        
        public byte[] getWithOctDef2 () {
            return this.withOctDef2;
        }

        

        public void setWithOctDef2 (byte[] value) {
            this.withOctDef2 = value;
        }
        
  
        
        public java.util.Collection<String>  getWithSeqOf () {
            return this.withSeqOf;
        }

        

        public void setWithSeqOf (java.util.Collection<String>  value) {
            this.withSeqOf = value;
        }
        
  
        
        public java.util.Collection<TestPRN>  getWithSeqOf2 () {
            return this.withSeqOf2;
        }

        

        public void setWithSeqOf2 (java.util.Collection<TestPRN>  value) {
            this.withSeqOf2 = value;
        }
        
  
        
        public StringArray getWithSeqOf3 () {
            return this.withSeqOf3;
        }

        

        public void setWithSeqOf3 (StringArray value) {
            this.withSeqOf3 = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            String param_WithDefault =         
            new String ("dd");
        setWithDefault(param_WithDefault);
    Long param_WithIntDef =         
            new Long ( 120);
        setWithIntDef(param_WithIntDef);
    WithSeqDefSequenceType param_WithSeqDef =         
            
                new WithSeqDefSequenceType();
                {
                
                    param_WithSeqDef.setName (
                        "Name"
                    );
                
                    param_WithSeqDef.setEmail (
                        "Email"
                    );
                
                }
            ;
        setWithSeqDef(param_WithSeqDef);
    TestOCT param_WithOctDef =         
            new TestOCT (org.bn.coders.CoderUtils.defStringToOctetString("'01101100'B"));
        setWithOctDef(param_WithOctDef);
    byte[] param_WithOctDef2 =         
            org.bn.coders.CoderUtils.defStringToOctetString("'FFEEAA'H").getValue();
        setWithOctDef2(param_WithOctDef2);
    java.util.Collection<String>  param_WithSeqOf =         
            
                                
                new java.util.LinkedList<String> ();
                                
                {                    
                    
                    param_WithSeqOf.add(
                        new String ("aa")
                    );
                    
                    param_WithSeqOf.add(
                        new String ("dd")
                    );
                    
                }
            ;
        setWithSeqOf(param_WithSeqOf);
    java.util.Collection<TestPRN>  param_WithSeqOf2 =         
            
                                
                new java.util.LinkedList<TestPRN> ();
                                
                {                    
                    
                    param_WithSeqOf2.add(
                        new TestPRN ("cc")
                    );
                    
                    param_WithSeqOf2.add(
                        new TestPRN ("ee")
                    );
                    
                }
            ;
        setWithSeqOf2(param_WithSeqOf2);
    StringArray param_WithSeqOf3 =         
            
                                
                new StringArray();
                
                    param_WithSeqOf3.initValue();
                                
                {                    
                    
                    param_WithSeqOf3.add(
                        "fff"
                    );
                    
                    param_WithSeqOf3.add(
                        "ggg"
                    );
                    
                }
            ;
        setWithSeqOf3(param_WithSeqOf3);
    
        }

        private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(SequenceWithDefault.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            