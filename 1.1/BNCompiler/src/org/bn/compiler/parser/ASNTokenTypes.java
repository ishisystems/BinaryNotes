
//$ANTLR 2.7.6 (2005-12-22): "ASN1.G" -> "ASNParser.java"$
package org.bn.compiler.parser;

//~--- JDK imports ------------------------------------------------------------

import java.math.*;

import java.util.*;

//~--- interfaces -------------------------------------------------------------

public interface ASNTokenTypes {
    int EOF                  = 1;
    int NULL_TREE_LOOKAHEAD  = 3;
    int NULL_KW              = 55;
    int MIN_KW               = 54;
    int MINUS_INFINITY_KW    = 53;
    int MAX_KW               = 52;
    int LINKED_KW            = 51;
    int ISO646STRING_KW      = 50;
    int INTERSECTION_KW      = 49;
    int INTEGER_KW           = 48;
    int INSTANCE_KW          = 47;
    int INCLUDES_KW          = 46;
    int IMPORTS_KW           = 45;
    int IMPLIED_KW           = 44;
    int IMPLICIT_KW          = 43;
    int IDENTIFIER_KW        = 42;
    int IA5_STRING_KW        = 41;
    int GRAPHIC_STR_KW       = 40;
    int GENERAL_STR_KW       = 39;
    int GENERALIZED_TIME_KW  = 38;
    int FROM_KW              = 37;
    int FALSE_KW             = 36;
    int EXTERNAL_KW          = 35;
    int EXTENSIBILITY_KW     = 34;
    int EXPORTS_KW           = 33;
    int EXPLICIT_KW          = 32;
    int EXCEPT_KW            = 31;
    int ERROR_KW             = 29;
    int ERRORS_KW            = 30;
    int ENUMERATED_KW        = 28;
    int END_KW               = 27;
    int EMBEDDED_KW          = 26;
    int DEFINITIONS_KW       = 25;
    int DEFINED_KW           = 24;
    int DEFAULT_KW           = 23;
    int CONSTRAINED_KW       = 22;
    int COMPONENT_KW         = 21;
    int COMPONENTS_KW        = 20;
    int CLASS_KW             = 19;
    int CHOICE_KW            = 18;
    int CHARACTER_KW         = 17;
    int BY_KW                = 16;
    int BOOLEAN_KW           = 15;
    int BMP_STRING_KW        = 14;
    int BIT_KW               = 13;
    int BEGIN_KW             = 12;
    int BASED_NUM_KW         = 11;
    int AUTOMATIC_KW         = 10;
    int ARGUMENT_KW          = 8;
    int APPLICATION_KW       = 9;
    int ANY_KW               = 7;
    int ALL_KW               = 6;
    int ABSTRACT_SYNTAX_KW   = 5;
    int ABSENT_KW            = 4;
    int NUMERIC_STR_KW       = 56;
    int OBJECT_DESCRIPTOR_KW = 57;
    int OBJECT_KW            = 58;
    int OCTET_KW             = 59;
    int OPERATION_KW         = 60;
    int OID_KW               = 62;
    int OF_KW                = 61;
    int OPTIONAL_KW          = 63;
    int PARAMETER_KW         = 64;
    int PDV_KW               = 65;
    int PLUS_INFINITY_KW     = 66;
    int PRESENT_KW           = 67;
    int PRINTABLE_STR_KW     = 68;
    int PRIVATE_KW           = 69;
    int REAL_KW              = 70;
    int RELATIVE_KW          = 71;
    int RESULT_KW            = 72;
    int SEQUENCE_KW          = 73;
    int SET_KW               = 74;
    int SIZE_KW              = 75;
    int STRING_KW            = 76;
    int TAGS_KW              = 77;
    int TELETEX_STR_KW       = 78;
    int TRUE_KW              = 79;
    int TYPE_IDENTIFIER_KW   = 80;
    int UNION_KW             = 81;
    int UNIQUE_KW            = 82;
    int UNIVERSAL_KW         = 83;
    int UNIVERSAL_STR_KW     = 84;
    int UTC_TIME_KW          = 85;
    int UTF8STRING_KW        = 86;
    int VIDEOTEX_STR_KW      = 87;
    int VISIBLE_STR_KW       = 88;
    int WITH_KW              = 89;
    int SINGLE_QUOTE         = 110;
    int SEMI                 = 109;
    int R_PAREN              = 108;
    int R_BRACKET            = 107;
    int R_BRACE              = 106;
    int PLUS                 = 105;
    int MINUS                = 104;
    int L_PAREN              = 103;
    int L_BRACKET            = 102;
    int L_BRACE              = 101;
    int LESS                 = 100;
    int INTERSECTION         = 99;
    int EXCLAMATION          = 98;
    int ELLIPSIS             = 97;
    int DOTDOT               = 96;
    int DOT                  = 95;
    int COMMENT              = 94;
    int COMMA                = 93;
    int COLON                = 92;
    int CHARH                = 112;
    int CHARB                = 111;
    int BAR                  = 91;
    int ASSIGN_OP            = 90;
    int WS                   = 113;
    int UTF8_STR_KW          = 150;
    int UPPER                = 116;
    int T61_STR_KW           = 149;
    int SL_COMMENT           = 114;
    int PATTERN_KW           = 162;
    int NUMBER               = 115;
    int LOWER                = 117;
    int LITERAL_UNBIND       = 125;

    // "EXTENSION-ATTRIBUTE" = 130
    int LITERAL_TOKEN     = 131;
    int LITERAL_SYNTAX    = 151;
    int LITERAL_STATUS    = 153;
    int LITERAL_SIGNED    = 142;
    int LITERAL_SIGNATURE = 143;
    int LITERAL_REFINE    = 135;
    int LITERAL_REFERENCE = 155;
    int LITERAL_PROTECTED = 144;

    // "TOKEN-DATA" = 132
    // "SECURITY-CATEGORY" = 133
    int LITERAL_PORT = 134;

    // "OBJECT-TYPE" = 145
    int LITERAL_MACRO      = 146;
    int LITERAL_INDEX      = 156;
    int LITERAL_EXTENSIONS = 129;

    // "APPLICATION-SERVICE-ELEMENT" = 126
    // "APPLICATION-CONTEXT" = 127
    int LITERAL_EXTENSION   = 128;
    int LITERAL_ENCRYPTED   = 141;
    int LITERAL_DESCRIPTION = 154;
    int LITERAL_DEFVAL      = 157;
    int LITERAL_BIND        = 124;

    // "ABSTRACT-BIND" = 136
    // "ABSTRACT-UNBIND" = 137
    // "ABSTRACT-OPERATION" = 138
    // "ABSTRACT-ERROR" = 139
    int LITERAL_ALGORITHM = 140;
    int LITERAL_ACCESS    = 152;
    int ISO646_STR_KW     = 148;
    int INCLUDES          = 161;
    int H_STRING          = 122;
    int HDIG              = 119;
    int EXCEPT            = 160;
    int DOTDOTDOT         = 158;
    int C_STRING          = 123;
    int B_STRING          = 121;
    int B_OR_H_STRING     = 120;
    int BMP_STR_KW        = 147;
    int BDIG              = 118;
    int ALL               = 159;
}


//~ Formatted by Jindent --- http://www.jindent.com
