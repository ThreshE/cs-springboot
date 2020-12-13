package com.springboot.cs.common.autocode.mycode;

import com.alibaba.druid.util.StringUtils;
import com.google.common.base.CaseFormat;
import com.springboot.cs.aspect.MultiLanguage;
import com.springboot.cs.common.autocode.utils.*;
import com.springboot.cs.common.bean.BaseBean;
import com.springboot.cs.common.bean.BaseSearchVO;
import com.springboot.cs.common.bean.Page;
import com.springboot.cs.common.bean.PageResult;
import com.springboot.cs.common.enums.LanguageEnums;
import com.springboot.cs.common.exception.BaseRuntimeException;
import com.springboot.cs.common.exception.CommonRuntimeException;
import com.springboot.cs.common.exception.TipRuntimeException;
import com.springboot.cs.common.utils.BeanUtil;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.*;

public class MyCodeGenerator extends MyGeneratorManager implements GeneratorProperties{

    public void genCode(String tableName) throws Exception {

        String modelName = NameFormator.toUUCase(tableName);

        Configuration cfg = getFreemarkerConfiguration();


        String modelNameUpperCamel = modelName;

        Class.forName(DriverClass);
        Connection con = DriverManager.getConnection(ConnectionURL, UserId, Password);
        DatabaseMetaData dbMetaData = con.getMetaData();
        ResultSet rs = dbMetaData.getColumns(null,"%", tableName,"%");
        rs.next();
        String columnType = rs.getString("TYPE_NAME");
        String columnName = rs.getString("COLUMN_NAME");

        Map<String, Object> data = getDataMapInit(tableName, modelName, modelNameUpperCamel,columnType,columnName);

        try {

            //创建VO
            codeVO(cfg,tableName,modelName,data);

            //创建SVO
            codeSVO(cfg,tableName,modelName,data);

            //创建MapperDao
            codeMappderDao(cfg,tableName,modelName,data);

            //创建Helper
            codeHelper(cfg,tableName,modelName,data);

            //创建Service 接口
            codeService(cfg,tableName,modelName,data);

            //创建Service实现类
            codeServiceImpl(cfg,tableName,modelName,data);

            //创建Controller
            codeController(cfg,tableName,modelName,data);

        } catch (Exception e) {
            throw new RuntimeException("Service 生成失败!", e);
        }
    }

    private void codeController(Configuration cfg, String tableName, String modelNameUpperCamel, Map<String, Object> data) throws Exception{
        File serviceFile = new File( ProjectPath + JavaPath + Split  + ControllerPackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "Controller.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "Controller.java 已经存在 - 跳过生成!");
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("controller.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "Controller.java 生成成功!");
    }

    private void codeHelper(Configuration cfg, String tableName, String modelNameUpperCamel, Map<String, Object> data) throws Exception {
        File serviceFile = new File( ProjectPath + JavaPath + Split  + HelperPackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "Helper.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "Helper.java 已经存在 - 跳过生成!");
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("helper.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "Helper.java 生成成功!");
    }

    private void codeMappderDao(Configuration cfg, String tableName, String modelNameUpperCamel, Map<String, Object> data) throws Exception{

        File serviceFile = new File( ProjectPath + JavaPath + Split  + MapperDaoPackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "MapperDao.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "MapperDao.java 已经存在 - 跳过生成!");
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("mapperDao.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "MapperDao.java 生成成功!");

    }

    private void codeServiceImpl(Configuration cfg, String tableName, String modelNameUpperCamel, Map<String, Object> data) throws Exception{

        File serviceFile = new File( ProjectPath + JavaPath + Split  + ServiceImplPackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "ServiceImpl.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 已经存在 - 跳过生成!");
//            serviceFile.delete();
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("serviceImpl.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功!");

    }

    private void codeService(Configuration cfg, String tableName, String modelNameUpperCamel, Map<String, Object> data) throws Exception {



        File serviceFile = new File( ProjectPath + JavaPath + Split  + ServicePackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "Service.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "Service.java 已经存在 - 跳过生成!");
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "Service.java 生成成功!");

    }

    private void codeSVO(Configuration cfg, String tableName, String modelNameUpperCamel, Map<String, Object> data) throws Exception{

        File serviceFile = new File( ProjectPath + JavaPath + Split  + SVOPackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "SearchVO.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "SVO.java 已经存在 - 跳过生成!");
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("svo.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "SVO.java 生成成功!");

    }

    private void codeVO(Configuration cfg,String tableName,String modelNameUpperCamel,Map<String, Object> data) throws Exception{

        File serviceFile = new File( ProjectPath + JavaPath + Split  + VOPackage.replaceAll("\\.", Split) + Split + modelNameUpperCamel + "VO.java");

        if(serviceFile.exists()) {
            System.out.println(modelNameUpperCamel + "VO.java 已经存在 - 跳过生成!");
            return;
        }

        // 查看父级目录是否存在, 不存在则创建
        if (!serviceFile.getParentFile().exists()) {
            File pa = serviceFile.getParentFile();
            serviceFile.getParentFile().mkdirs();
        }

        cfg.getTemplate("vo.ftl").process(data, new FileWriter(serviceFile));
        System.out.println(modelNameUpperCamel + "VO.java 生成成功!");

    }


    public  Map<String, Object> getDataMapInit(String tableName, String modelName, String modelNameUpperCamel,String type,String pkName) throws Exception{
        Map<String, Object> data = new HashMap<>();

        data.put("tableName", tableName);
        data.put("modelNameUpperCamel", modelNameUpperCamel);
        data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));


        data.put("modelPackage",ModelPackage);
        data.put("voPackage", VOPackage);
        data.put("svoPackage", SVOPackage);
        data.put("servicePackage", ServicePackage);
        data.put("serviceImplPackage",ServiceImplPackage);
        data.put("controllerPackage", ControllerPackage);
        data.put("mapperDaoPackage",MapperDaoPackage);
        data.put("mapperPackage",MapperPackage);
        data.put("helperPackage",HelperPackage);
        data.put("controllerPackage",ControllerPackage);

        data.put("baseBean", BaseBean.class.getName());
        data.put("baseSearchBean", BaseSearchVO.class.getName());
        data.put("pageResult", PageResult.class.getName());
        data.put("beanUtil", BeanUtil.class.getName());
        data.put("page", Page.class.getName());
        data.put("commonException", CommonRuntimeException.class.getName());
        data.put("baseException", BaseRuntimeException.class.getName());
        data.put("tipException", TipRuntimeException.class.getName());
        data.put("languageEnum", LanguageEnums.class.getName());
        data.put("multiLanguage", MultiLanguage.class.getName());

        data.put("pkType",changeType(type));
        data.put("pkPackage",getPkPackage(type));
        data.put("pkName",NameFormator.toUUCase(pkName));
        data.put("pkNameSmall",NameFormator.toLCase(NameFormator.toUUCase(pkName)));

        data.put("url",getUrl(tableName));
        return data;
    }


    public String changeType(String dbType) throws Exception{
        if(StringUtils.equalsIgnoreCase(dbType,"VARCHAR")) {
            return "String";
        } else if(StringUtils.equalsIgnoreCase(dbType,"INT")) {
            return "Integer";
        } else if(StringUtils.equalsIgnoreCase(dbType,"BIGINT")) {
            return "Long";
        }
        return dbType;
    }

    public String getPkPackage(String dbType) throws Exception{
        if(StringUtils.equalsIgnoreCase(dbType,"VARCHAR")) {
            return String.class.getName();
        } else if(StringUtils.equalsIgnoreCase(dbType,"INT")) {
            return Integer.class.getName();
        } else if(StringUtils.equalsIgnoreCase(dbType,"BIGINT")) {
            return Long.class.getName();
        }
        return dbType;
    }

    public String getUrl(String tableName) throws Exception{
        String url = "/api";

        String[] split = tableName.split("_");
        for(int i = 1; i < split.length; i ++)  {
            url += "/" + split[i];
        }
        return url;

    }

}
