package com.springboot.cs.common.autocode.mycode;

public class MyCodeProgram {

    public static void main(String[] args) throws Exception{

        new MyCodeGenerator().genCode("imeetlink_activity");

//        SAXReader reader = new SAXReader();
//        Document doc = reader.read(new File("./mysql-generatorConfig-imeetlink.xml"));
//
//        Element root = doc.getRootElement();
//        Element contextEle = root.element("context");
//        Element jdbcConnection = contextEle.element("jdbcConnection");
//
//        Attribute driverClass = jdbcConnection.attribute("driverClass");
//        System.out.print(driverClass.getValue());
//        Attribute connectionURL = jdbcConnection.attribute("connectionURL");
//        System.out.print(connectionURL.getValue());
//        Attribute userId = jdbcConnection.attribute("userId");
//        System.out.print(userId.getValue());
//        Attribute password = jdbcConnection.attribute("password");
//        System.out.print(password.getValue());

    }
}
