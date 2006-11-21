package org.bn.compiler.parser.model;

public class AsnDefinedType {
    public AsnConstraint constraint;
    public boolean       isModuleReference;
    public String        moduleReference;
    public String        name;
    public String        typeReference;

    //~--- constructors -------------------------------------------------------

    // Default Constructor
    public AsnDefinedType() {
        name              = "";
        moduleReference   = "";
        typeReference     = "";
        isModuleReference = false;
    }

    // toString() Definition

    //~--- methods ------------------------------------------------------------

    public String toString() {
        String ts = "";

        if (isModuleReference) {
            ts += (moduleReference + "." + typeReference);
        } else {
            ts += (typeReference);
        }

        if (constraint != null) {
            ts += constraint;
        }

        return ts;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
