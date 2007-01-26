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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xsltc="http://xml.apache.org/xalan/xsltc"
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
    extension-element-prefixes="xsltc redirect"
>
    <xsl:import href="elementDefaults.xsl"/>
    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="sequenceDecl">
	<xsl:param name="elementName"/>
	<xsl:variable name="sequenceName"><xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select="$elementName"/></xsl:call-template>SequenceType</xsl:variable>        

            <xsl:if test="typeReference/isSequence = 'true'">
                <xsl:for-each select="typeReference">

       @ASN1PreparedElement
       @ASN1Sequence ( name = "<xsl:value-of select='$elementName'/>" , isSet = <xsl:choose><xsl:when test="isSequence = 'false'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose> )
       public class <xsl:value-of select="$sequenceName"/> implements IASN1PreparedElement {
                <xsl:call-template name="elements"/>
                <xsl:call-template name="sequenceFunctions"/>
                
        <!-- public <xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select="$elementName"/></xsl:call-template>SequenceType() {            -->        
        public void initWithDefaults() {
            <xsl:call-template name="elementDefaults"/>
        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_<xsl:value-of select='$sequenceName'/>;
        }

                
       }
       private static IASN1PreparedElementData preparedData_<xsl:value-of select='$sequenceName'/> = CoderFactory.getInstance().newPreparedElementData(<xsl:value-of select='$sequenceName'/>.class);

       
                </xsl:for-each>
            </xsl:if>
    </xsl:template>
</xsl:stylesheet>
