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
    <xsl:output method="text" encoding="UTF-8" indent="no"/>

    <xsl:template name="stringTypeDecl">
        StringType = <xsl:choose>
            <xsl:when test="stringtype = 'PrintableString'"> UniversalTags.PrintableString </xsl:when>
            <xsl:when test="stringtype = 'NumericString'"> UniversalTags.NumericString </xsl:when>
            <xsl:when test="stringtype = 'TeletexString'"> UniversalTags.TeletexString </xsl:when>
            <xsl:when test="stringtype = 'VideotexString'"> UniversalTags.VideotexString </xsl:when>
            <xsl:when test="stringtype = 'IA5String'"> UniversalTags.IA5String </xsl:when>
            <xsl:when test="stringtype = 'GraphicString'"> UniversalTags.GraphicString </xsl:when>
            <xsl:when test="stringtype = 'VisibleString'"> UniversalTags.VisibleString </xsl:when>
            <xsl:when test="stringtype = 'GeneralString'"> UniversalTags.GeneralString </xsl:when>
            <xsl:when test="stringtype = 'UniversalString'"> UniversalTags.UniversalString </xsl:when>
            <xsl:when test="stringtype = 'BMPString'"> UniversalTags.BMPString </xsl:when>
            <xsl:otherwise> UniversalTags.UnspecifiedString </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
