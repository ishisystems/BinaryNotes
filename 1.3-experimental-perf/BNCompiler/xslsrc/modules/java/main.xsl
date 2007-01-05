<?xml version="1.0" encoding="utf-8" ?>
<!--
/*
 * Copyright 2006 Abdulla G. Abdurakhmanov (abdulla.abdurakhmanov@gmail.com).
 * 
 * Licensed under the GPL, Version 2 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.gnu.org/copyleft/gpl.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * With any your questions welcome to my e-mail 
 * or blog at http://abdulla-a.blogspot.com.
 */
-->
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xsltc="http://xml.apache.org/xalan/xsltc"
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="xsltc redirect"
>
    <xsl:import href="includes/header.xsl"/>
    <xsl:import href="includes/footer.xsl"/>
    <xsl:import href="includes/choice.xsl"/>
    <xsl:import href="includes/sequence.xsl"/>    
    <xsl:import href="includes/enum.xsl"/>
    <xsl:import href="includes/boxedSequenceOfType.xsl"/>
    <xsl:import href="includes/boxedStringType.xsl"/>
    <xsl:import href="includes/boxedOctetStringType.xsl"/>
    <xsl:import href="includes/boxedBooleanType.xsl"/>
    <xsl:import href="includes/boxedBitStringType.xsl"/>    
    <xsl:import href="includes/boxedIntegerType.xsl"/>    
    <xsl:import href="includes/boxedRealType.xsl"/>
    <xsl:import href="includes/boxedType.xsl"/>
    <xsl:import href="includes/boxedNullType.xsl"/>
    <xsl:import href="includes/packageInfo.xsl"/>
       
    <xsl:import href="includes/boxedBitStringMetadata.xsl"/>
    <xsl:import href="includes/boxedBooleanMetadata.xsl"/>
    <xsl:import href="includes/boxedIntegerMetadata.xsl"/>    
    <xsl:import href="includes/boxedMetadata.xsl"/>
    <xsl:import href="includes/boxedNullMetadata.xsl"/>
    <xsl:import href="includes/boxedOctetStringMetadata.xsl"/>
    <xsl:import href="includes/boxedRealMetadata.xsl"/>
    <xsl:import href="includes/boxedSequenceOfMetadata.xsl"/>
    <xsl:import href="includes/boxedStringMetadata.xsl"/>
    <xsl:import href="includes/choiceMetadata.xsl"/>
    <xsl:import href="includes/enumMetadata.xsl"/>
    <xsl:import href="includes/packageInfo.xsl"/>
    <xsl:import href="includes/sequenceMetadata.xsl"/>

    <xsl:output method="text" encoding="UTF-8" indent="no"/>
    
    <xsl:variable name="outputDirectory"><xsl:value-of select="//outputDirectory"/></xsl:variable>
    <xsl:variable name="moduleName"><xsl:value-of select="//moduleNS"/></xsl:variable>
    
    <xsl:template match="/">
        <xsl:call-template name="header"/>
        <xsl:apply-templates/>
        <xsl:call-template name="footer"/>
    </xsl:template>
    
    <xsl:template match="//module/moduleIdentifier" >
        <xsl:call-template name="packageInfo"/>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="//module/asnTypes/choices">
        <xsl:call-template name="choice"/>
        <xsl:call-template name="choiceMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/sequenceSets">
        <xsl:call-template name="sequence"/>
        <xsl:call-template name="sequenceMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/enums">
        <xsl:call-template name="enum"/>
        <xsl:call-template name="enumMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/sequenceSetsOf">
        <xsl:call-template name="boxedSequenceOfType"/>
        <xsl:call-template name="boxedSequenceOfMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/characterStrings">
        <xsl:call-template name="boxedStringType"/>
        <xsl:call-template name="boxedStringMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/octetStrings">
        <xsl:call-template name="boxedOctetStringType"/>
        <xsl:call-template name="boxedOctetStringMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/booleans">
        <xsl:call-template name="boxedBooleanType"/>
        <xsl:call-template name="boxedBooleanMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/bitStrings">
        <xsl:call-template name="boxedBitStringType"/>
        <xsl:call-template name="boxedBitStringMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/integers">
        <xsl:call-template name="boxedIntegerType"/>
        <xsl:call-template name="boxedIntegerMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/reals">
        <xsl:call-template name="boxedRealType"/>
        <xsl:call-template name="boxedRealMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/taggeds">
        <xsl:call-template name="boxedType"/>
        <xsl:call-template name="boxedMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/defineds">
        <xsl:call-template name="boxedType"/>
        <xsl:call-template name="boxedMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//module/asnTypes/nulls">
        <xsl:call-template name="boxedNullType"/>
        <xsl:call-template name="boxedNullMetadata"/>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="text()"><xsl:apply-templates/></xsl:template>
</xsl:stylesheet>
