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

    <xsl:template name="valueRangeConstraint">
        <xsl:if test= "elemSetSpec/intersectionList/cnsElemList/lEndValue/signedNumber/num">
            <xsl:if test= "elemSetSpec/intersectionList/cnsElemList/uEndValue/signedNumber/num">@ASN1ValueRangeConstraint ( 
		min = <xsl:if test= "elemSetSpec/intersectionList/cnsElemList/lEndValue/signedNumber/positive = 'false'">-</xsl:if><xsl:value-of select="elemSetSpec/intersectionList/cnsElemList/lEndValue/signedNumber/num"/>L, 
		max = <xsl:if test= "elemSetSpec/intersectionList/cnsElemList/uEndValue/signedNumber/positive = 'false'">-</xsl:if><xsl:value-of select="elemSetSpec/intersectionList/cnsElemList/uEndValue/signedNumber/num"/>L ) 
	   </xsl:if>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
