package com.springboot.cs.service.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@JobHandler(value="lizeHandler")
@Component
public class lizeJobHandler extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(lizeJobHandler.class);

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        logger.info("XXL-JOB, Hello World.");
        System.out.println("XXL-JOB, Hello World.");
        return ReturnT.SUCCESS;
    }

}