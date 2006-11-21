package org.bn.compiler.parser.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

//~--- classes ----------------------------------------------------------------

public class AsnSequenceOfValue {
    boolean          isValPresent;
    public ArrayList valueList;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public AsnSequenceOfValue() {
        valueList = new ArrayList();
    }

    //~--- methods ------------------------------------------------------------

    // toString Method
    public String toString() {
        String   ts = "";
        Iterator i  = valueList.iterator();

        while (i.hasNext()) {
            ts += i.next();
        }

        return ts;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
