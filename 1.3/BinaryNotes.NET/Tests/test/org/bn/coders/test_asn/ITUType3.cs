
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

using System;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;
using org.bn.types;

namespace test.org.bn.coders.test_asn {


    [ASN1BoxedType ( Name = "ITUType3") ]
    public class ITUType3 {
            
           
        private ITUType2  val;

        
        [ASN1Element ( Name = "ITUType3", IsOptional =  false , HasTag =  true, Tag = 2 , HasDefaultValue =  false )  ]
    
        public ITUType2 Value
        {
                get { return val; }        
                    
                set { val = value; }
                        
        }            

                    
        
        public ITUType3 ()
        {
        }
        
    }
            
}
