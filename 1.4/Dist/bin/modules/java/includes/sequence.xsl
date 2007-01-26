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

    <xsl:import href="header.xsl"/>
    <xsl:import href="footer.xsl"/>
    <xsl:import href="elements.xsl"/>
    <xsl:import href="sequenceFunctions.xsl"/>
    <xsl:import href="elementDefaults.xsl"/>

    <xsl:output method="text" encoding="UTF-8" indent="no"/>
    
    <xsl:variable name="outputDirectory"><xsl:value-of select="//outputDirectory"/></xsl:variable>
    
    <xsl:template name="sequence">
	<xsl:variable name="sequenceName"><xsl:call-template name="doMangleIdent"><xsl:with-param name='input' select="name"/></xsl:call-template></xsl:variable>

        <xsltc:output file="{$outputDirectory}/{$sequenceName}.java">
            <xsl:call-template name="header"/>

    @ASN1PreparedElement
    @ASN1Sequence ( name = "<xsl:value-of select='$sequenceName'/>", isSet = <xsl:choose><xsl:when test="isSequence = 'false'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose> )
    public class <xsl:value-of select="$sequenceName"/> implements IASN1PreparedElement {
            <xsl:call-template name="elements"/>            
            <xsl:call-template name="sequenceFunctions"/>
                    
        <!-- defaults for elements -->
        <!-- public <xsl:value-of select="$sequenceName"/>() {            -->
        public void initWithDefaults() {
            <xsl:call-template name="elementDefaults"/>
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(<xsl:value-of select='$sequenceName'/>.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            <xsl:call-template name="footer"/>
        </xsltc:output>        
    </xsl:template>    
</xsl:stylesheet>
