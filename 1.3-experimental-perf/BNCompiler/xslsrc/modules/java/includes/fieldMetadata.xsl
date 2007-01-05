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

    <xsl:import href="stringTypeDecl.xsl"/>

    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="fieldMetadata">
        <xsl:choose>
            <xsl:when test="typeReference/BUILTINTYPE = 'CHARACTER STRING'">
              <xsl:for-each select="typeReference">
              new ASN1StringMetadata("<xsl:value-of select='name'/>",
                                     <xsl:value-of select='isUCSType'/>,
                                     <xsl:call-template name="stringTypeDecl"/>)
              </xsl:for-each>
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'OCTET STRING'">
              new ASN1OctetStringMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'BIT STRING'">
              new ASN1BitStringMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'BOOLEAN'">
              new ASN1BooleanMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'INTEGER'">
              new ASN1IntegerMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'REAL'">
              new ASN1RealMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="typeReference/isSequenceOf = 'true'">
              new ASN1SequenceOfMetadata("<xsl:value-of select='name'/>",
                                         <xsl:for-each select="typeReference">
                                           <xsl:value-of select="typeName"/>
                                         </xsl:for-each>.class)
            </xsl:when>
            <xsl:when test="typeReference/isSequence = 'true'">
              new ASN1SequenceMetadata("<xsl:value-of select='name'/>",
                                       <xsl:choose>
                                         <xsl:when test="isSequence = 'false'">true</xsl:when>
                                         <xsl:otherwise>false</xsl:otherwise>
                                       </xsl:choose>)
            </xsl:when>
            <xsl:when test="typeReference/isChoice = 'true'">
              new ASN1ChoiceMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="typeReference/isNull = 'true'">
              new ASN1NullMetadata("<xsl:value-of select='name'/>")
            </xsl:when>
            <xsl:when test="string-length(typeName) > 0">
              null
            </xsl:when>
            <xsl:otherwise>
              new ASN1AnyMetadata("<xsl:value-of select='typeReference/name'/>")
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
