<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
<xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>
<xsl:template match="users-data">
    <fo:root language="EN">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="A4-portrail" 
                page-height="297mm" page-width="210mm" 
                margin-top="5mm" margin-bottom="5mm" 
                margin-left="5mm" margin-right="5mm">
                    <fo:region-body margin-top="25mm" margin-bottom="20mm"/>
                    <fo:region-before region-name="xsl-region-before" 
                        extent="25mm" display-align="before" precedence="true"/>
                </fo:simple-page-master>
        </fo:layout-master-set>
        <fo:page-sequence master-reference="A4-portrail">
            <fo:static-content flow-name="xsl-region-before">
                    <fo:table table-layout="fixed" 
                        width="100%" font-size="10pt" 
                        border-color="black" border-width="0.4mm" 
                        border-style="solid">
                        <fo:table-column column-width="proportional-column-width(20)"/>
                        <fo:table-column column-width="proportional-column-width(20)"/>
                        <fo:table-column column-width="proportional-column-width(50)"/>
                        <fo:table-column column-width="proportional-column-width(20)"/>
                        <fo:table-column column-width="proportional-column-width(20)"/>
                        <fo:table-body font-size="95%">
                            <fo:table-row height="8mm">
                                <fo:table-cell><fo:block>Quant.</fo:block></fo:table-cell>
                                <fo:table-cell><fo:block>Cód.</fo:block></fo:table-cell>
                                <fo:table-cell><fo:block>Discriminação</fo:block></fo:table-cell>
                                <fo:table-cell><fo:block>Vl. Unit.</fo:block></fo:table-cell>
                                <fo:table-cell><fo:block>Vl. Total.</fo:block></fo:table-cell>
                            </fo:table-row>
                            <xsl:for-each select="">
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block><xsl:value-of select="qtd"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block><xsl:value-of select="productId"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block><xsl:value-of select="desc"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block><xsl:value-of select="productPrice"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>R$ <xsl:value-of select="totalPrice"/></fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:for-each>
                            <fo:table-row height="8mm">
                                <fo:table-cell><fo:block></fo:block></fo:table-cell>
                                <fo:table-cell><fo:block></fo:block></fo:table-cell>
                                <fo:table-cell><fo:block></fo:block></fo:table-cell>
                                <fo:table-cell><fo:block></fo:block></fo:table-cell>
                                <fo:table-cell><fo:block>R$ <xsl:value-of select=""/></fo:block></fo:table-cell>
                            </fo:table-row>
                    </fo:table>
        </fo:page-sequence>
    </fo:root>
</xsl:template>