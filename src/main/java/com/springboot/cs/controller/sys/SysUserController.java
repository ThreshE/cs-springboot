package com.springboot.cs.controller.sys;

import com.springboot.cs.aspect.Idempotent;
import com.springboot.cs.aspect.Validate;
import com.springboot.cs.common.bean.PageResult;
import com.springboot.cs.common.bean.WebResult;
import com.springboot.cs.common.enums.IdempotentEnum;
import com.springboot.cs.service.sys.SysUserService;
import com.springboot.cs.svo.sys.SysUserSearchVO;
import com.springboot.cs.vo.sys.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sys/user")
@Api(tags = {"系统"},description = "系统用户",position = 0)
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("list")
    @ApiOperation(value="系統用戶", notes="用户列表",position = 0)
    @Validate(params={"userId","userName"}) //String/Integer/Boolean/List/Long 只支持单级
    @Idempotent(timeout = 10,type = IdempotentEnum.IdempotentType.UUID)
    public WebResult<PageResult<SysUserVO>> list(@RequestBody SysUserSearchVO svo) {
        WebResult result = new WebResult();

        try{
            PageResult<SysUserVO> pageResult = sysUserService.list(svo);
            result.setData(pageResult);
            result.setResult(true);
            result.setMessage("查询用户成功");
            log.info("这是info日志");
            log.debug("这是debug日志");
            log.error("这是error日志");

        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage("查询用户失败");
        }

        return result;
    }

}


