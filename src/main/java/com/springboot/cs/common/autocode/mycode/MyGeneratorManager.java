package com.springboot.cs.common.autocode.mycode;


import java.io.File;
import java.io.IOException;

import com.springboot.cs.common.autocode.utils.GeneratorProperties;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class MyGeneratorManager implements GeneratorProperties{

    private static Configuration configuration = null;


    public Configuration getFreemarkerConfiguration() {
        if (configuration == null) {
            configuration = initFreemarkerConfiguration();
        }
        return configuration;
    }

    /**
     * Freemarker 模板环境配置
     *
     * @return
     * @throws IOException
     */
    private Configuration initFreemarkerConfiguration() {
        Configuration cfg = null;
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File(TemplateFilePath));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        } catch (IOException e) {
            throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
        }
        return cfg;
    }

}
