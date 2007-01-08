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

    <xsl:import href="decoderMetadata.xsl"/>    
    <xsl:import href="encoderMetadata.xsl"/>    
    <xsl:import href="elementMetadata.xsl"/>    
    <xsl:import href="fieldMetadata.xsl"/>    

    <xsl:template name="choiceFieldsMetadata">
       <xsl:param name="codeableClass"/>
       <xsl:call-template name="fieldsMetadata">
         <xsl:with-param name="codeableClass"><xsl:value-of select='$codeableClass'/></xsl:with-param>
         <xsl:with-param name="setterPrefix">select</xsl:with-param>
       </xsl:call-template>
    </xsl:template>

    <xsl:template name="sequenceFieldsMetadata">
       <xsl:param name="codeableClass"/>
       <xsl:call-template name="fieldsMetadata">
         <xsl:with-param name="codeableClass"><xsl:value-of select='$codeableClass'/></xsl:with-param>
         <xsl:with-param name="setterPrefix">set</xsl:with-param>
       </xsl:call-template>
    </xsl:template>

    <xsl:template name="fieldsMetadata">
       <xsl:param name="codeableClass"/>
       <xsl:param name="setterPrefix"/>
       <xsl:variable name="classRef"><xsl:value-of select="$codeableClass"/>.class</xsl:variable>
    private static final FieldDescriptor[] FIELD_DESCRIPTORS;

    static
    {
       try
       {
          FieldDescriptor[] descriptors =
           {
             <xsl:for-each select="elementTypeList/elements">
               <xsl:variable name="fieldDescriptorClass"><xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select='name'/></xsl:call-template>Descriptor</xsl:variable>
               <xsl:variable name="getDeclaredField"><xsl:value-of select="$classRef"/>.getDeclaredField("<xsl:value-of select='name'/>")</xsl:variable>
              new <xsl:value-of select="$fieldDescriptorClass"/>("<xsl:value-of select='name'/>",
                                  <xsl:value-of select="$getDeclaredField"/>,
                                  <xsl:call-template name="fieldMetadata"/>,
                                  <xsl:call-template name="elementMetadata"/>,
                                  <xsl:call-template name="encoderMetadata"/>,
                                  <xsl:call-template name="decoderMetadata"/>),
             </xsl:for-each>        
           };
          FIELD_DESCRIPTORS = descriptors;
       }
       catch (Exception exception)
       {
          throw new RuntimeException(exception);
       }
    };

       <xsl:for-each select="elementTypeList/elements">
         <xsl:variable name="fieldDescriptorClass"><xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select='name'/></xsl:call-template>Descriptor</xsl:variable>
         <xsl:variable name="getterName">get<xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select='name'/></xsl:call-template></xsl:variable>    
         <xsl:variable name="setterName"><xsl:value-of select='$setterPrefix'/><xsl:call-template name="toUpperFirstLetter"><xsl:with-param name="input" select='name'/></xsl:call-template></xsl:variable>    
    private static class <xsl:value-of select="$fieldDescriptorClass"/>
      extends FieldDescriptor
    {
       public <xsl:value-of select="$fieldDescriptorClass"/>(String              name,
                                                             Field               field,
                                                             ASN1Metadata        typeMetadata,
                                                             ASN1ElementMetadata elementMetadata,
                                                             IValueEncoder       encoder,
                                                             IValueDecoder       decoder)
       {
          super(name, field, typeMetadata, elementMetadata, encoder, decoder);
       }

       public Object getField(Object targetObject)
       {
         <xsl:value-of select="$codeableClass"/> target =
           (<xsl:value-of select="$codeableClass"/>) targetObject;

         return target.<xsl:value-of select="$getterName"/>();
       }

       public void setField(Object targetObject,
                            Object valueObject)
       {
         <xsl:value-of select="$codeableClass"/> target =
           (<xsl:value-of select="$codeableClass"/>) targetObject;

         target.<xsl:value-of select="$setterName"/>((<xsl:call-template name="elementType"/>) valueObject);
       }
    }
       </xsl:for-each>

    </xsl:template>

</xsl:stylesheet>
