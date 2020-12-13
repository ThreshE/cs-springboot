package com.springboot.cs.common.autocode.utils;

import java.io.File;

public interface GeneratorBaseProperties {

    //////////////////////////////////常修改//////////////////////////////////////////////////
    /**
     * 子目录
     */
    String prefix = "sys";

    /**
     * 项目目录
     */
    String ProjectPath = "/Users/user/local/myGit/cs-springboot";

    /**
     * 系统分隔符
     */
    String Split = File.separator;
}
