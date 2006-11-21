
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




    @ASN1Sequence ( name = "TestSequenceWithNonames", isSet = false )
    public class TestSequenceWithNonames {
            
       @ASN1Sequence ( name = "seq" , isSet = false )
       public class SeqSequenceType {
                @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "it1", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private Integer it1 = null;
                
  
        
        public Integer getIt1 () {
            return this.it1;
        }

        
        public boolean isIt1Present () {
            return this.it1 == null;
        }
        

        public void setIt1 (Integer value) {
            this.it1 = value;
        }
        
  
                
                
        public void initWithDefaults() {
            
        }
                
       }
       
                
        @ASN1Element ( name = "seq", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private SeqSequenceType seq = null;
                
  
        

    @ASN1Choice ( name = "ch" )
    public class ChChoiceType {
            @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "it1", isOptional =  false , hasTag =  true, tag = 0 , hasDefaultValue =  false  )
    
	private Integer it1 = null;
                
  @ASN1OctetString( name = "" )
    
        @ASN1Element ( name = "it2", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private byte[] it2 = null;
                
  
        
        public Integer getIt1 () {
            return this.it1;
        }

        public boolean isIt1Selected () {
            return this.it1 != null;
        }

        private void setIt1 (Integer value) {
            this.it1 = value;
        }

        public void selectIt1 (Integer value) {
            this.it1 = value;
            
                    setIt2(null);
                            
        }
        
  
        
        public byte[] getIt2 () {
            return this.it2;
        }

        public boolean isIt2Selected () {
            return this.it2 != null;
        }

        private void setIt2 (byte[] value) {
            this.it2 = value;
        }

        public void selectIt2 (byte[] value) {
            this.it2 = value;
            
                    setIt1(null);
                            
        }
        
  
    }
                
        @ASN1Element ( name = "ch", isOptional =  false , hasTag =  true, tag = 1 , hasDefaultValue =  false  )
    
	private ChChoiceType ch = null;
                
  
       @ASN1Sequence ( name = "" , isSet = false )
       public class SequenceType {
                @ASN1Integer( name = "" )
    
        @ASN1Element ( name = "it1", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private Integer it1 = null;
                
  
        
        public Integer getIt1 () {
            return this.it1;
        }

        
        public boolean isIt1Present () {
            return this.it1 == null;
        }
        

        public void setIt1 (Integer value) {
            this.it1 = value;
        }
        
  
                
                
        public void initWithDefaults() {
            
        }
                
       }
       
                
@ASN1SequenceOf( name = "", isSetOf = false ) 

    
        @ASN1Element ( name = "seqf", isOptional =  false , hasTag =  true, tag = 2 , hasDefaultValue =  false  )
    
	private java.util.Collection<SequenceType>  seqf = null;
                
  
        
        public SeqSequenceType getSeq () {
            return this.seq;
        }

        

        public void setSeq (SeqSequenceType value) {
            this.seq = value;
        }
        
  
        
        public ChChoiceType getCh () {
            return this.ch;
        }

        

        public void setCh (ChChoiceType value) {
            this.ch = value;
        }
        
  
        
        public java.util.Collection<SequenceType>  getSeqf () {
            return this.seqf;
        }

        

        public void setSeqf (java.util.Collection<SequenceType>  value) {
            this.seqf = value;
        }
        
  
                    
        
        
        public void initWithDefaults() {
            
        }
            
    }
            