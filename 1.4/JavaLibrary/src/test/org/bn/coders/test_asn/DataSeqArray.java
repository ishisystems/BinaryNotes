
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
    @ASN1BoxedType ( name = "DataSeqArray" )
    public class DataSeqArray implements IASN1PreparedElement {
                
            @ASN1SequenceOf( name = "DataSeqArray" , isSetOf = false)
            
	    private java.util.Collection<DataSeq> value = null; 
    
            public DataSeqArray () {
            }
        
            public DataSeqArray ( java.util.Collection<DataSeq> value ) {
                setValue(value);
            }
                        
            public void setValue(java.util.Collection<DataSeq> value) {
                this.value = value;
            }
            
            public java.util.Collection<DataSeq> getValue() {
                return this.value;
            }            
            
            public void initValue() {
                setValue(new java.util.LinkedList<DataSeq>()); 
            }
            
            public void add(DataSeq item) {
                value.add(item);
            }

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = new ASN1PreparedElementData(DataSeqArray.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            