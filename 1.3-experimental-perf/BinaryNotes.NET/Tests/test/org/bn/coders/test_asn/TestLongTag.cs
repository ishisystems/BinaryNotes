
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


    [ASN1BoxedType ( Name = "TestLongTag") ]
    public class TestLongTag {
            
           
        private long  val;

        [ASN1Integer( Name = "" )]
    
        [ASN1Element ( Name = "TestLongTag", IsOptional =  false , HasTag =  true, Tag = 15123, 
        TagClass =  TagClasses.Application  , HasDefaultValue =  false )  ]
    
        public long Value
        {
                get { return val; }        
                    
                set { val = value; }
                        
        }            

                    
        
        public TestLongTag ()
        {
        }
        
    }
            
}
