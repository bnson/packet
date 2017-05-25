<?xml version="1.0" encoding="UTF-8"?>
<!--
This file was generated by Altova MapForce 2017r3

YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.

Refer to the Altova MapForce Documentation for further details.
http://www.altova.com/mapforce
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" exclude-result-prefixes="xs fn">
	<xsl:output method="xml" encoding="UTF-8" byte-order-mark="no" indent="yes"/>
	<xsl:template match="/">
		<xsl:variable name="var2_table" as="node()?" select="table"/>
		<sgb>
			<xsl:attribute name="xsi:noNamespaceSchemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance" select="'file:///D:/project/sgb/NetBeansProjects/SGBExport/src/Projects/sgb_soft_mrp/template_export_001.xsd'"/>
			<sgb_soft_mrp>
				<exportTemplate>
					<metaData>
						<id>
							<xsl:sequence select="()"/>
						</id>
						<xsl:for-each select="$var2_table">
							<exportName>
								<xsl:sequence select="()"/>
							</exportName>
						</xsl:for-each>
						<exportType>
							<xsl:sequence select="()"/>
						</exportType>
						<exportPath>
							<xsl:sequence select="()"/>
						</exportPath>
					</metaData>
					<xsl:for-each select="$var2_table">
						<xsl:variable name="var1_row" as="node()" select="row"/>
						<row>
							<xsl:sequence select="($var1_row/@node(), $var1_row/node())"/>
						</row>
					</xsl:for-each>
				</exportTemplate>
			</sgb_soft_mrp>
		</sgb>
	</xsl:template>
</xsl:stylesheet>
