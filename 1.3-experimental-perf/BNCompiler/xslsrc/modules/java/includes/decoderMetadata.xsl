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

    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="decoderMetadata">
        <xsl:choose>
            <xsl:when test="typeReference/BUILTINTYPE = 'CHARACTER STRING'">
              ValueDecoders.STRING
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'OCTET STRING'">
              ValueDecoders.OCTET_STRING
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'BIT STRING'">
              ValueDecoders.BIT_STRING
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'BOOLEAN'">
              ValueDecoders.BOOLEAN
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'INTEGER'">
              ValueDecoders.INTEGER
            </xsl:when>
            <xsl:when test="typeReference/BUILTINTYPE = 'REAL'">
              ValueDecoders.REAL
            </xsl:when>
            <xsl:when test="typeReference/isSequenceOf = 'true'">
              ValueDecoders.SEQUENCE_OF
            </xsl:when>
            <xsl:when test="typeReference/isSequence = 'true'">
              ValueDecoders.SEQUENCE
            </xsl:when>
            <xsl:when test="typeReference/isChoice = 'true'">
              ValueDecoders.CHOICE
            </xsl:when>
            <xsl:when test="typeReference/isNull = 'true'">
              ValueDecoders.NULL
            </xsl:when>
            <xsl:when test="string-length(typeName) > 0">
              ValueDecoders.ELEMENT
            </xsl:when>
            <xsl:otherwise>
              ValueDecoders.JAVA_ELEMENT
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
