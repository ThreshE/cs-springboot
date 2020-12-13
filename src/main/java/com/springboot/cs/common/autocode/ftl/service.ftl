package ${servicePackage};

import ${voPackage}.${modelNameUpperCamel}VO;
import ${svoPackage}.${modelNameUpperCamel}SearchVO;
import ${pageResult};
import ${commonException};
import ${baseException};
import ${tipException};
import java.util.List;

public interface ${modelNameUpperCamel}Service {


    /**
     * 根据条件做搜索
     * @param svo
     * @return
    */
    public PageResult list(${modelNameUpperCamel}SearchVO svo) throws BaseRuntimeException;


    /**
     * 根据主键获取
     * @param id
     * @return
    */
    public ${modelNameUpperCamel}VO get(${pkType} id) throws BaseRuntimeException;

    /**
     * 新增
     * @param vo
    */
    public void insert(${modelNameUpperCamel}VO vo) throws BaseRuntimeException;

    /**
     * 更新
     * @param vo
    */
    public void update(${modelNameUpperCamel}VO vo) throws BaseRuntimeException;

    /**
     * 删除
     * @param id
    */
    public void delete(${pkType} id) throws BaseRuntimeException;

    /**
     * 批量删除
     * @param list
    */
    public void deleteList(List<${pkType}> list) throws BaseRuntimeException;

}
