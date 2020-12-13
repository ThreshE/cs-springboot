package com.springboot.cs.service.sys.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.springboot.cs.common.bean.PageResult;
import com.springboot.cs.dao.sys.SysUserMapperDao;
import com.springboot.cs.service.sys.SysUserService;
import com.springboot.cs.svo.sys.SysUserSearchVO;
import com.springboot.cs.vo.sys.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapperDao sysUserMapperDao;

    @Override
    public PageResult<SysUserVO> list(SysUserSearchVO svo) throws RuntimeException {

        PageResult pr = new PageResult<SysUserVO>();

        List<SysUserVO> listVO = new ArrayList<SysUserVO>();
        SysUserVO userVO = new SysUserVO();
        userVO.setUserId("1212322");
        userVO.setUserName("123");
        listVO.add(userVO);
        pr.setCount(listVO.size());
        pr.setList(listVO);
        return pr;
    }
}
