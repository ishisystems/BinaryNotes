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

    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="boxedSequenceOfType">
        <xsl:variable name="boxedName"><xsl:value-of select="name"/></xsl:variable>
        <xsltc:output file="{$outputDirectory}/{$boxedName}.cs">
            <xsl:call-template name="header"/>

    [ASN1BoxedType ( Name = "<xsl:value-of select='$boxedName'/>" ) ]
    public class <xsl:value-of select="$boxedName"/> {

	    private System.Collections.Generic.ICollection&lt;<xsl:call-template name="elementType"/>&gt; val = null; 
            
            [ASN1SequenceOf( Name = "<xsl:value-of select='name'/>", IsSetOf = <xsl:choose><xsl:when test="isSequenceOf = 'false'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>) ]
            <xsl:call-template name="typeDecl"/>
            <xsl:for-each select="constraint">
                <xsl:call-template name="constraint"/>
            </xsl:for-each>
            public System.Collections.Generic.ICollection&lt;<xsl:call-template name="elementType"/>&gt; Value
            {
                get { return val; }
                set { val = value; }
            }
            
            public void initValue() {
                this.Value = new System.Collections.Generic.List&lt;<xsl:call-template name="elementType"/>&gt;();
            }
            
            public void Add(<xsl:call-template name="elementType"/> item) {
                this.Value.Add(item);
            }
    }
            <xsl:call-template name="footer"/>
        </xsltc:output>        
    </xsl:template>
</xsl:stylesheet>