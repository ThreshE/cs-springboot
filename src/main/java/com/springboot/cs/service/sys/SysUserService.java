package com.springboot.cs.service.sys;

import com.springboot.cs.common.bean.PageResult;
import com.springboot.cs.svo.sys.SysUserSearchVO;
import com.springboot.cs.vo.sys.SysUserVO;

import java.util.List;

public interface SysUserService {

    PageResult<SysUserVO> list(SysUserSearchVO svo) throws RuntimeException;
}
