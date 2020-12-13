package ${mapperDaoPackage};

import ${baseBean};
import ${modelPackage}.${modelNameUpperCamel};
import ${modelPackage}.${modelNameUpperCamel}Example;
import ${mapperPackage}.${modelNameUpperCamel}Mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

@Component
public class ${modelNameUpperCamel}MapperDao{

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;


    public List<${modelNameUpperCamel}> selectByExample(${modelNameUpperCamel}Example example) {
        return ${modelNameLowerCamel}Mapper.selectByExample(example);
    }

    public int countByExample(${modelNameUpperCamel}Example example) {
        return ${modelNameLowerCamel}Mapper.countByExample(example);
    }

    public ${modelNameUpperCamel} get(${pkType} id) {
        return ${modelNameLowerCamel}Mapper.selectByPrimaryKey(id);
    }

    public void insert(${modelNameUpperCamel} po) {
        ${modelNameLowerCamel}Mapper.insertSelective(po);
    }

    public void update(${modelNameUpperCamel} po) {
        ${modelNameLowerCamel}Mapper.updateByPrimaryKeySelective(po);
    }


    public int delete(${pkType} id) {
        return ${modelNameLowerCamel}Mapper.deleteByPrimaryKey(id);
    }

    public int deleteList(List<${pkType}> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        ${modelNameUpperCamel}Example example = new ${modelNameUpperCamel}Example();
        example.createCriteria().and${pkName}In(list);
        return ${modelNameLowerCamel}Mapper.deleteByExample(example);
    }
}
