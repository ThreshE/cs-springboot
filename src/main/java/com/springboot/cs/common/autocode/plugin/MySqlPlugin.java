package com.springboot.cs.common.autocode.plugin;

import com.springboot.cs.common.autocode.utils.NameFormator;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.ibatis2.Ibatis2FormattingUtilities;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MySqlPlugin extends PluginAdapter{

	private static String BaseBeanPath= "com.springboot.cs.common.bean.BaseBean";

	private static String PagePath = "com.springboot.cs.common.bean.Page";

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable) {
		// add field, getter, setter for limit clause
		addPage(topLevelClass, introspectedTable, "page");

		//add where string
		addWhereString(topLevelClass, introspectedTable);

		return super.modelExampleClassGenerated(topLevelClass,introspectedTable);
	}
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){

		//add BaseBean
		topLevelClass.addImportedType(new FullyQualifiedJavaType(BaseBeanPath));
		topLevelClass.setSuperClass("BaseBean");

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setFinal(true);
		field.setStatic(true);
		field.setType(new FullyQualifiedJavaType("long"));
		field.setName("serialVersionUID");
		field.setInitializationString("1L");
		field.addJavaDocLine("/** serialVersionUID */");

		topLevelClass.addField(field);


		return super.modelBaseRecordClassGenerated(topLevelClass,introspectedTable);
	}

	/**
	 * @param topLevelClass
	 * @param introspectedTable
	 * @param name
	 */
	private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType(PagePath));
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType(PagePath));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType(PagePath), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType(PagePath));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	/**
	 * @Description whereString
	 * @author xhz
	 * @date 2017年6月21日 下午3:16:00
	 * @param topLevelClass
	 * @param introspectedTable
	 * @lastModifier
	 */
	private void addWhereString(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){

		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(new FullyQualifiedJavaType("String"));
		field.setName("whereSQL");
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		String camel = "WhereSQL";
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "whereSQL"));
		method.addBodyLine("this.whereSQL = whereSQL;");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("String"));
		method.setName("get" + camel);
		method.addBodyLine("return whereSQL;");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return true;
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
																	 IntrospectedTable introspectedTable) {

		XmlElement where = new XmlElement("if");
		where.addAttribute(new Attribute("test","(oredCriteria.size == 0 or oredCriteria[0].criteria.size == 0) and whereSQL != null and whereSQL != ''"));
		where.addElement(new TextElement("WHERE "));
		element.addElement(element.getElements().size()-1,where);

		where = new XmlElement("if");
		where.addAttribute(new Attribute("test","oredCriteria.size != 0 and oredCriteria[0].criteria.size != 0 and whereSQL != null and whereSQL != ''"));
		where.addElement(new TextElement("AND "));
		element.addElement(element.getElements().size()-1,where);

		where = new XmlElement("if");
		where.addAttribute(new Attribute("test","whereSQL != null and whereSQL != ''"));
		where.addElement(new TextElement("(${whereSQL})"));
		element.addElement(element.getElements().size()-1,where);

		XmlElement page = new XmlElement("if");
		page.addAttribute(new Attribute("test", "page != null"));
		page.addElement(new TextElement("limit #{page.begin} , #{page.length}"));
		element.addElement(page);

		return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapCountByExampleElementGenerated(XmlElement element,
														IntrospectedTable introspectedTable) {

		XmlElement where = new XmlElement("if");
		where.addAttribute(new Attribute("test","(oredCriteria.size == 0 or oredCriteria[0].criteria.size == 0) and whereSQL != null and whereSQL != ''"));
		where.addElement(new TextElement("WHERE "));
		element.addElement(where);

		where = new XmlElement("if");
		where.addAttribute(new Attribute("test","oredCriteria.size != 0 and oredCriteria[0].criteria.size != 0 and whereSQL != null and whereSQL != ''"));
		where.addElement(new TextElement("AND "));
		element.addElement(where);

		where = new XmlElement("if");
		where.addAttribute(new Attribute("test","whereSQL != null and whereSQL != ''"));
		where.addElement(new TextElement("(${whereSQL})"));
		element.addElement(where);

		return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
	}

	/**
	 * 转换成驼峰
	 * */
	public static String toHump(String source){
		Pattern pattern = Pattern.compile("_[a-z]");
		Matcher m = pattern.matcher(source);
		String result = source;
		int offset = 0;
		while(m.find()){
			result = result.substring(0, m.start()+offset) + m.group().toUpperCase().replace("_", "") + result.substring(m.end()+offset);
			offset--;
		}
		return result;
	}

	@Override
	public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,IntrospectedTable introspectedTable) {

		List<IntrospectedColumn> ics = introspectedTable.getPrimaryKeyColumns();
		String type = introspectedTable.getPrimaryKeyType();
		String useActualColumnNames = introspectedTable.getTableConfigurationProperty("useActualColumnNames");

		String lastInsertId = introspectedTable.getTableConfigurationProperty("lastInsertId");
		System.out.println("*******************************************"+type);
		System.out.println("*******************************************useActualColumnNames="+useActualColumnNames);
		System.out.println("*******************************************lastInsertId="+lastInsertId);

		if(lastInsertId == null){
			lastInsertId = "true";
		}
		if(ics.size()>0&&ics.size()==1 && lastInsertId.equals("true")){
			IntrospectedColumn ic = ics.get(0);
			String icName = Ibatis2FormattingUtilities.getEscapedColumnName(ic);
			if(useActualColumnNames.equals("false")){
				//改成驼峰命名
				icName = toHump(icName);
			}
			XmlElement isNotNullElement = new XmlElement("selectKey"); //$NON-NLS-1$
			isNotNullElement.addAttribute(new Attribute("resultType", "int")); //$NON-NLS-1$ //$NON-NLS-2$
			isNotNullElement.addAttribute(new Attribute("keyProperty", icName)); //$NON-NLS-1$ //$NON-NLS-2$
			isNotNullElement.addAttribute(new Attribute("order", "AFTER")); //$NON-NLS-1$ //$NON-NLS-2$
			isNotNullElement.addElement(new TextElement("<![CDATA[SELECT LAST_INSERT_ID() AS "+icName+" ]]>"));
			element.addElement(isNotNullElement);
		}
		return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapInsertElementGenerated(XmlElement element,IntrospectedTable introspectedTable){

		List<IntrospectedColumn> ics = introspectedTable.getPrimaryKeyColumns();
		String type = introspectedTable.getPrimaryKeyType();
		String useActualColumnNames = introspectedTable.getTableConfigurationProperty("useActualColumnNames");
		String lastInsertId = introspectedTable.getTableConfigurationProperty("lastInsertId");


		System.out.println("*******************************************"+type);
		System.out.println("*******************************************useActualColumnNames="+useActualColumnNames);
		System.out.println("*******************************************lastInsertId="+lastInsertId);

		if(lastInsertId == null){
			lastInsertId = "true";
		}
		if(ics.size()>0&&ics.size()==1 && lastInsertId.equals("true")){
			IntrospectedColumn ic = ics.get(0);
			String icName = Ibatis2FormattingUtilities.getEscapedColumnName(ic);
			if(useActualColumnNames.equals("false")){
				//改成驼峰命名
				icName = toHump(icName);
			}
			XmlElement isNotNullElement = new XmlElement("selectKey"); //$NON-NLS-1$
			isNotNullElement.addAttribute(new Attribute("resultType", "int")); //$NON-NLS-1$ //$NON-NLS-2$
			isNotNullElement.addAttribute(new Attribute("keyProperty", icName)); //$NON-NLS-1$ //$NON-NLS-2$
			isNotNullElement.addAttribute(new Attribute("order", "AFTER")); //$NON-NLS-1$ //$NON-NLS-2$
			isNotNullElement.addElement(new TextElement("<![CDATA[SELECT LAST_INSERT_ID() AS "+icName+" ]]>"));
			element.addElement(isNotNullElement);
		}
		return super.sqlMapInsertElementGenerated(element, introspectedTable);
	}

	public boolean validate(List<String> arg0) {
		return true;
	}

}
