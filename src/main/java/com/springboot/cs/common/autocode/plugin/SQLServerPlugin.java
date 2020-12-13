package com.springboot.cs.common.autocode.plugin;

import com.springboot.cs.common.autocode.utils.NameFormator;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
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


public class SQLServerPlugin extends PluginAdapter {

	private static String BaseBeanPath= "com.springboot.cs.common.bean.BaseBean";
	private static String PagePath = "com.springboot.cs.common.bean.Page";

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// add field, getter, setter for limit clause
		addPage(topLevelClass, introspectedTable, "page");
		
		return super.modelExampleClassGenerated(topLevelClass,introspectedTable);
	}
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){

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
    
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
    	if(introspectedTable.getPrimaryKeyColumns().size() == 0){
    		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    	}
        XmlElement page = new XmlElement("if");
        page.addAttribute(new Attribute("test", "page != null"));
        page.addElement(new TextElement("TOP (#{page.length})"));
        
        int index = 0;
        for(Element e : element.getElements()){
        	if(!(e instanceof TextElement)){
        		index++;
        		break;
        	}
        	index++;
        }
        element.addElement(index, page);
        
        String column = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();
        System.out.println(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        XmlElement where = new XmlElement("if");
        where.addAttribute(new Attribute("test","(oredCriteria.size == 0 or oredCriteria[0].criteria.size == 0) and page != null and page.begin != 0"));
        where.addElement(new TextElement("WHERE "));
        element.addElement(element.getElements().size()-1,where);
        
        where = new XmlElement("if");
        where.addAttribute(new Attribute("test","oredCriteria.size != 0 and oredCriteria[0].criteria.size != 0 and page != null and page.begin != 0"));
        where.addElement(new TextElement("AND "));
        element.addElement(element.getElements().size()-1,where);
                
        where = new XmlElement("if");
        where.addAttribute(new Attribute("test","page != null and page.begin != 0"));
        where.addElement(new TextElement(column+" not in (SELECT TOP (#{page.begin}) "+ column +" FROM "+introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        element.addElement(element.getElements().size()-1,where);
        
        where = new XmlElement("if");
        where.addAttribute(new Attribute("test","_parameter != null and page != null and page.begin != 0"));
        where.addElement(new TextElement("<include refid=\"Example_Where_Clause\" />"));
        element.addElement(element.getElements().size()-1,where);
        
        where = new XmlElement("if");
        where.addAttribute(new Attribute("test","page != null and page.begin != 0 and orderByClause != null"));
        where.addElement(new TextElement("order by ${orderByClause}"));
        element.addElement(element.getElements().size()-1,where);
        
        where = new XmlElement("if");
        where.addAttribute(new Attribute("test","page != null and page.begin != 0"));
        where.addElement(new TextElement(")"));
        element.addElement(element.getElements().size()-1,where);
        
        
 
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
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
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);  
    }
    
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
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);  
    }
    
	public boolean validate(List<String> arg0) {
		return true;
	}

	public static void main(String[] args) throws IOException, XMLParserException {
		//此目录是generateConfig.xml的路径
		String configfile = "/Users/user/local/myGit/cs-springboot/src/main/java/com/springboot/cs/common/autocode/program/mysql-generatorConfig-imeetlink.xml";
		File configurationFile = new File(configfile);
        
        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configurationFile);
        
        Context context = config.getContext("fx");
        List<TableConfiguration> tables = context.getTableConfigurations();
        for(TableConfiguration table : tables){
        	System.out.println(table.getTableName());
        }
        
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = context.getSqlMapGeneratorConfiguration();
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = context.getJavaClientGeneratorConfiguration();
        
        
		String tableName = tables.get(0).getTableName();
		String className = NameFormator.toUUCase(tableName);
		try{
			String fileName = sqlMapGeneratorConfiguration.getTargetProject()+"\\"+sqlMapGeneratorConfiguration.getTargetPackage().replaceAll("\\.", "\\\\");
			System.out.println(fileName);
			 File[] dels = new File(fileName).listFiles();
			 {
				 if(dels != null){
					 for(File del : dels){
						 if(del.getName().equals(className+"Mapper.xml")){
							 System.out.println(del.getName()+"删除了.");
							 del.delete();
						 }
					 }
				 }
			 }
			 dels = new File(javaClientGeneratorConfiguration.getTargetProject()+"\\"+javaClientGeneratorConfiguration.getTargetPackage().replaceAll("\\.", "\\\\")).listFiles();
			 {
				 if(dels != null){
					 for(File del : dels){
						 if(del.getName().equals(className+"Mapper.java") ){
							 System.out.println(del.getName()+"删除了.");
							 del.delete();
						 }
					 }
				 }
			 }		 
			 dels = new File(javaModelGeneratorConfiguration.getTargetProject()+"\\"+javaModelGeneratorConfiguration.getTargetPackage().replaceAll("\\.", "\\\\")).listFiles();
			 {
				 if(dels != null){
					 for(File del : dels){
						 if(del.getName().equals(className+".java") || del.getName().equals(className+"Example.java") ){
							 System.out.println(del.getName()+"删除了.");
							 del.delete();
						 }
					 }
				 }
			 }			 
		}catch(Exception e){
			e.printStackTrace();
		}
         String[] arg = { "-configfile", configfile};  
         ShellRunner.main(arg);
	}
}
