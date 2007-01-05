
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




    @ASN1BoxedType ( name = "TestUnicodeStr" )
    public class TestUnicodeStr {
    
            @ASN1String( name = "TestUnicodeStr", 
        stringType = UniversalTag.UTF8String , isUCS = false )
            
            private String value;
            
            public TestUnicodeStr() {
            }

            public TestUnicodeStr(String value) {
                this.value = value;
            }
            
            public void setValue(String value) {
                this.value = value;
            }
            
            public String getValue() {
                return this.value;
            }
    }
            