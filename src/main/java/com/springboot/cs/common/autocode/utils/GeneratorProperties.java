package com.springboot.cs.common.autocode.utils;

public interface GeneratorProperties extends GeneratorBaseProperties{

    //////////////////////////////////数据库配置//////////////////////////////////////////////////

    String DriverClass  = "com.mysql.jdbc.Driver";

    String ConnectionURL  = "jdbc:mysql://47.244.51.250:11306/imeetlink";

    String UserId  = "root";

    String Password  = "mysql@123456";

    /////////////////////////////////基本不修改配置///////////////////////////////////////////////////

    /**
     * 目录位置
     */
    String TemplateFilePath = ProjectPath + "/src/main/java/com/springboot/cs/common/autocode/ftl";

    String PackagePath = "com.springboot.cs";
    /**
     * java目录
     */
    String JavaPath = "/src/main/java";

    /**
     * po目录
     */
    String ModelPackage = PackagePath + ".po." + prefix;

    /**
     * vo目录
     */
    String VOPackage = PackagePath + ".vo." + prefix;

    /**
     * svo目录
     */
    String SVOPackage = PackagePath + ".svo." + prefix;

    /**
     * helper目录
     */
    String HelperPackage = PackagePath + ".helper." + prefix;

    /**
     * mapper目录
     */
    String MapperPackage = PackagePath + ".mapper.autocode." + prefix;

    /**
     * mapperdao目录
     */
    String MapperDaoPackage = PackagePath + ".dao." + prefix;

    /**
     * service目录
     */
    String ServicePackage = PackagePath + ".service." + prefix;

    /**
     * serviceImpl目录
     */
    String ServiceImplPackage = PackagePath + ".service." + prefix + ".impl";

    /**
     * controller目录
     */
    String ControllerPackage = PackagePath + ".controller." + prefix;


}
