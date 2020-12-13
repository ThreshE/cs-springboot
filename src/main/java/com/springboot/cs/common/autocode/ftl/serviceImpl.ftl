package ${serviceImplPackage};

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import ${helperPackage}.${modelNameUpperCamel}Helper;
import ${svoPackage}.${modelNameUpperCamel}SearchVO;
import ${voPackage}.${modelNameUpperCamel}VO;
import ${servicePackage}.${modelNameUpperCamel}Service;
import ${mapperDaoPackage}.${modelNameUpperCamel}MapperDao;
import ${modelPackage}.${modelNameUpperCamel}Example;
import ${pageResult};
import ${modelPackage}.${modelNameUpperCamel};
import ${page};
import ${commonException};
import ${baseException};
import ${tipException};
import ${languageEnum};

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("${modelNameLowerCamel}Service")
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}MapperDao ${modelNameLowerCamel}MapperDao;

    /**
     * 根据条件做搜索
     * @param svo
     * @return
    */
    @Override
    @Transactional
    public PageResult list(${modelNameUpperCamel}SearchVO svo) throws BaseRuntimeException {
        try {
            PageResult result = new PageResult();
            Page page = new Page(svo.getPageNo(), svo.getPageSize());

            ${modelNameUpperCamel}Example example = new ${modelNameUpperCamel}Example();
            ${modelNameUpperCamel}Example.Criteria criteria = example.createCriteria();
            example.setPage(page);

            List<${modelNameUpperCamel}VO> list = ${modelNameUpperCamel}Helper.convertToVO(${modelNameLowerCamel}MapperDao.selectByExample(example));

            result.setList(list);
            result.setCount(${modelNameLowerCamel}MapperDao.countByExample(example));
            return result;
        }  catch (TipRuntimeException e) {
            e.printStackTrace();
            log.error("查询出错，{}", e.getMessage());
            throw new TipRuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错，{}", e.getMessage());
            throw new CommonRuntimeException(LanguageEnums.ResultMessageEnum.list_failed.toString(),e.getMessage());
        }

    }


    /**
     * 根据主键获取
     * @param id
     * @return
    */
    @Override
    @Transactional
    public ${modelNameUpperCamel}VO get(${pkType} id) throws BaseRuntimeException {
        try {
            ${modelNameUpperCamel}VO vo = ${modelNameUpperCamel}Helper.convertToVO(${modelNameLowerCamel}MapperDao.get(id));
            return vo;
        }  catch (TipRuntimeException e) {
            e.printStackTrace();
            log.error("获取出错，{}", e.getMessage());
            throw new TipRuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取出错，{}", e.getMessage());
            throw new CommonRuntimeException(LanguageEnums.ResultMessageEnum.detail_failed.toString(),e.getMessage());
        }
    }

    /**
     * 新增
     * @param vo
    */
    @Override
    @Transactional
    public void insert(${modelNameUpperCamel}VO vo) throws BaseRuntimeException {
        try {
            ${modelNameUpperCamel} po = ${modelNameUpperCamel}Helper.convertToPO(vo);
            ${modelNameLowerCamel}MapperDao.insert(po);
        }  catch (TipRuntimeException e) {
            e.printStackTrace();
            log.error("新增出错，{}", e.getMessage());
            throw new TipRuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增出错，{}", e.getMessage());
            throw new CommonRuntimeException(LanguageEnums.ResultMessageEnum.insert_failed.toString(),e.getMessage());
        }
    }
    /**
     * 更新
     * @param vo
    */
    @Override
    @Transactional
    public void update(${modelNameUpperCamel}VO vo) throws BaseRuntimeException {
        try {
            ${modelNameUpperCamel} po = ${modelNameUpperCamel}Helper.convertToPO(vo);
            ${modelNameLowerCamel}MapperDao.update(po);
        }  catch (TipRuntimeException e) {
            e.printStackTrace();
            log.error("更新出错，{}", e.getMessage());
            throw new TipRuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新出错，{}", e.getMessage());
            throw new CommonRuntimeException(LanguageEnums.ResultMessageEnum.update_failed.toString(),e.getMessage());
        }
    }
    /**
     * 删除
     * @param id
    */
    @Override
    @Transactional
    public void delete(${pkType} id) throws BaseRuntimeException {
        try {
            ${modelNameLowerCamel}MapperDao.delete(id);
        }  catch (TipRuntimeException e) {
            e.printStackTrace();
            log.error("删除出错，{}", e.getMessage());
            throw new TipRuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错，{}", e.getMessage());
            throw new CommonRuntimeException(LanguageEnums.ResultMessageEnum.delete_failed.toString(),e.getMessage());
        }
    }
    /**
     * 批量删除
     * @param list
    */
    @Override
    @Transactional
    public void deleteList(List<${pkType}> list) throws BaseRuntimeException {
        try {
            ${modelNameLowerCamel}MapperDao.deleteList(list);
        }  catch (TipRuntimeException e) {
            e.printStackTrace();
            log.error("批量删除出错，{}", e.getMessage());
            throw new TipRuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("批量删除出错，{}", e.getMessage());
            throw new CommonRuntimeException(LanguageEnums.ResultMessageEnum.delete_list_failed.toString(),e.getMessage());
        }
    }
}