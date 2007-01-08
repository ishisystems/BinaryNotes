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
    extension-element-prefixes="xsltc redirect">

    <xsl:import href="header.xsl"/>
    <xsl:import href="footer.xsl"/>

    <xsl:output method="text" encoding="UTF-8" indent="no"/>
    
    <xsl:variable name="outputDirectory"><xsl:value-of select="//outputDirectory"/></xsl:variable>
    
    <xsl:template name="boxedBitStringMetadata">
        <xsl:variable name="boxedName"><xsl:value-of select="name"/></xsl:variable>        
        <xsl:variable name="valueFieldRef"><xsl:value-of select="$boxedName"/>.class.getDeclaredField("value")</xsl:variable>
n        <xsltc:output file="{$outputDirectory}/{$boxedName}Descriptor.java">
            <xsl:call-template name="header"/>

public class <xsl:value-of select="$boxedName"/>Descriptor
   extends BoxedTypeDescriptor
{
   public <xsl:value-of select="$boxedName"/>Descriptor()
     throws Exception
   {
      super("value",
            <xsl:value-of select="$valueFieldRef"/>,
            <xsl:value-of select="$boxedName"/>.class.getMethod("getValue"),
            <xsl:value-of select="$boxedName"/>.class.getMethod("setValue", <xsl:value-of select="$valueFieldRef"/>.getType()),
            new ASN1BitStringMetadata("<xsl:value-of select='name'/>"),
            ValueEncoders.BIT_STRING,
            ValueDecoders.BIT_STRING);
   }
}
            <xsl:call-template name="footer"/>
        </xsltc:output>        
    </xsl:template>    
</xsl:stylesheet>
