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
    <xsl:import href="elementType.xsl"/>
    <xsl:import href="typeDecl.xsl"/>
    <xsl:import href="commons.xsl"/>

    <xsl:output method="text" encoding="UTF-8" indent="no"/>

  <xsl:template name="choiceElementFunctions">
        <xsl:variable name="elementName" select="name"/>
        
        public bool is<xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select="$elementName"/></xsl:call-template>Selected () {
            return this.<xsl:value-of select="$elementName"/>_selected ;
        }

        public void select<xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select="$elementName"/></xsl:call-template> (<xsl:call-template name="elementType"/> val) {
            this.<xsl:value-of select="$elementName"/>_ = val;
            this.<xsl:value-of select="$elementName"/>_selected = true;
            
            <xsl:for-each select="parent::elementTypeList/elements">
                <xsl:if test="name != $elementName">
                    this.<xsl:value-of select="name"/>_selected = false;
                </xsl:if>
            </xsl:for-each>            
        }
        
  </xsl:template>
  
</xsl:stylesheet>
