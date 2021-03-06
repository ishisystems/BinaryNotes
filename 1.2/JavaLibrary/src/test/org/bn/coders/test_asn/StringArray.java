
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




    @ASN1BoxedType ( name = "StringArray" )
    public class StringArray {
                
            @ASN1SequenceOf( name = "StringArray" , isSetOf = false)
            
    @ASN1String( name = "", 
        stringType =  UniversalTag.PrintableString , isUCS = false )
    
	    private java.util.Collection<String> value = null; 
    
            public StringArray () {
            }
        
            public StringArray ( java.util.Collection<String> value ) {
                setValue(value);
            }
                        
            public void setValue(java.util.Collection<String> value) {
                this.value = value;
            }
            
            public java.util.Collection<String> getValue() {
                return this.value;
            }            
            
            public void initValue() {
                setValue(new java.util.LinkedList<String>()); 
            }
            
            public void add(String item) {
                value.add(item);
            }
    }
            