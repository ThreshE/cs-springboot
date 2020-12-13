package com.springboot.cs.common.autocode.program;

import com.springboot.cs.common.autocode.mycode.MyCodeGenerator;
import com.springboot.cs.common.autocode.utils.NameFormator;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yyw
 * @Description: mysql的运行程序
 * @date 2018/8/1711:12
 */
public class MysqlProgram {

    public static void main(String[] args) throws IOException, XMLParserException {
        String configfile = "/Users/user/local/myGit/cs-springboot/src/main/java/com/springboot/cs/common/autocode/program/mysql-generatorConfig-imeetlink.xml";
        System.out.println("配置文件路径："+configfile);
        File configurationFile = new File(configfile);

        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configurationFile);

        Context context = config.getContext("mysql");
        List<TableConfiguration> tables = context.getTableConfigurations();
        for(TableConfiguration table : tables){
            System.out.println("表名："+table.getTableName());
        }

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = context.getSqlMapGeneratorConfiguration();
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = context.getJavaClientGeneratorConfiguration();


        String tableName = tables.get(0).getTableName();
        String className = NameFormator.toUUCase(tableName);
        try{
            String fileName = sqlMapGeneratorConfiguration.getTargetProject()+"/"+sqlMapGeneratorConfiguration.getTargetPackage().replaceAll("\\.", "/");
            File[] dels = new File(fileName).listFiles();
            {
                if(dels != null){
                    for(File del : dels){
                        if(del.getName().equals(className+"Mapper.xml")){
                            System.out.println(del.getName()+"删除");
                            del.delete();
                        }
                    }
                }
            }
            dels = new File(javaClientGeneratorConfiguration.getTargetProject()+"/"+javaClientGeneratorConfiguration.getTargetPackage().replaceAll("\\.", "/")).listFiles();
            {
                if(dels != null){
                    for(File del : dels){
                        if(del.getName().equals(className+"Mapper.java") ){
                            System.out.println(del.getName()+"删除");
                            del.delete();
                        }
                    }
                }
            }
            dels = new File(javaModelGeneratorConfiguration.getTargetProject()+"/"+javaModelGeneratorConfiguration.getTargetPackage().replaceAll("\\.", "/")).listFiles();
            {
                if(dels != null){
                    for(File del : dels){
                        if(del.getName().equals(className+".java") || del.getName().equals(className+"Example.java") ){
                            System.out.println(del.getName()+"删除");
                            del.delete();
                        }
                    }
                }
            }

            myCode(tableName);
        }catch(Exception e){
            e.printStackTrace();
        }
        String[] arg = { "-configfile", configfile};
        ShellRunner.main(arg);
    }

    private static void myCode(String tableName) throws Exception {
        System.out.println("自定义代码生成============================");
        new MyCodeGenerator().genCode(tableName);
    }
}


