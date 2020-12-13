package com.springboot.cs.controller;

import com.springboot.cs.common.bean.WebResult;
import com.springboot.cs.svo.TestVO;
import com.springboot.cs.vo.sys.SysUserVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/springboot")
@Api(tags = {"心跳"},description = "普通心跳检测",position = 0)
//@ApiIgnore
public class HealthController {

    @GetMapping("/health.do")
    @ApiOperation(value="心跳", notes="心跳机制",position = 0)
    public WebResult<Object> health(HttpServletRequest request) {

        WebResult result = new WebResult();
        result.setMessage("springboot is running");
        result.setResult(true);
        return result;
    }

    @PostMapping("/demoPost")
    @ApiOperation(value="post接口方式", notes="post接口方式",position = 1)
    public WebResult<Object> demoPost(@RequestBody TestVO testVO) {

        WebResult result = new WebResult();
        result.setMessage(testVO.getId() + "_" + testVO.getName());
        result.setResult(true);
        return result;
    }

//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
//        @ApiImplicitParam(name = "name", value = "用户名称", required = true, dataType = "String")
//    })
    @GetMapping("/demoGet")
    @ApiOperation(value="get接口方式", notes="get接口方式",position = 2)
    public WebResult<Object> demoGet(@ApiParam(value = "用户id",required = false,example="1") @RequestParam(value = "id",required = false) Long id,
                                @ApiParam(value = "用户名称",required = false,example="yyw") @RequestParam(value = "name",required = false) String name) {

        WebResult result = new WebResult();
        result.setMessage(id + name);
        result.setResult(true);
        return result;
    }
}
