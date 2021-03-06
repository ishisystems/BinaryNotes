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
    <xsl:import href="typeDecl.xsl"/>
    <xsl:import href="elements.xsl"/>
    <xsl:import href="elementDecl.xsl"/>
    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="boxedType">
        <xsl:variable name="boxedName"><xsl:value-of select="name"/></xsl:variable>        
        <xsltc:output file="{$outputDirectory}/{$boxedName}.java">
            <xsl:call-template name="header"/>

    @ASN1BoxedType ( name = "<xsl:value-of select='$boxedName'/>" )
    public class <xsl:value-of select="$boxedName"/> {
            
            <xsl:if test="typeReference/isSequence = 'true'">       
                <xsl:for-each select="typeReference">
       @ASN1Sequence ( name = "<xsl:value-of select='$boxedName'/>" )
       public class SequenceType {
                <xsl:call-template name="elements"/>
       }
                </xsl:for-each>
            </xsl:if>
            
        <xsl:call-template name="elementDecl"/>
        <xsl:call-template name="typeDecl"/> private <xsl:call-template name="elementType"/>  value;

        <xsl:choose>
        <xsl:when test="typeReference/isNull = 'true'"></xsl:when>
        <xsl:otherwise>
        
        public void setValue(<xsl:call-template name="elementType"/> value) {
            this.value = value;
        }
        
        public <xsl:call-template name="elementType"/> getValue() {
            return this.value;
        }            
        </xsl:otherwise>
        </xsl:choose>
            
    }
            <xsl:call-template name="footer"/>
        </xsltc:output>        
    
    </xsl:template>
</xsl:stylesheet>
