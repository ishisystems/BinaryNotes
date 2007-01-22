
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
    @ASN1BoxedType ( name = "SetOfTest" )
    public class SetOfTest implements IASN1PreparedElement {
                
            @ASN1SequenceOf( name = "SetOfTest" , isSetOf = true)
            
	    private java.util.Collection<Data> value = null; 
    
            public SetOfTest () {
            }
        
            public SetOfTest ( java.util.Collection<Data> value ) {
                setValue(value);
            }
                        
            public void setValue(java.util.Collection<Data> value) {
                this.value = value;
            }
            
            public java.util.Collection<Data> getValue() {
                return this.value;
            }            
            
            public void initValue() {
                setValue(new java.util.LinkedList<Data>()); 
            }
            
            public void add(Data item) {
                value.add(item);
            }

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(SetOfTest.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            