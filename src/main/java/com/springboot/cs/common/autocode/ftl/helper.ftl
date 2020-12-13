package ${helperPackage};

import ${modelPackage}.${modelNameUpperCamel};
import ${voPackage}.${modelNameUpperCamel}VO;
import ${beanUtil};

import java.util.List;

public class ${modelNameUpperCamel}Helper{

    /**
     * @Description 转换成VO
    */
    public static ${modelNameUpperCamel}VO convertToVO(${modelNameUpperCamel} source){
        return BeanUtil.copy(source,${modelNameUpperCamel}VO.class);
    }

    /**
     * @Description 转换成ListVO
    */
    public static List<${modelNameUpperCamel}VO> convertToVO(List<${modelNameUpperCamel}> source){
        return BeanUtil.copy(source,${modelNameUpperCamel}VO.class);
    }

    /**
     * @Description 转换成PO
    */
    public static ${modelNameUpperCamel} convertToPO(${modelNameUpperCamel}VO source){
        return BeanUtil.copy(source,${modelNameUpperCamel}.class);
    }

    /**
     * @Description 转换成ListPO
    */
    public static List<${modelNameUpperCamel}> convertToPO(List<${modelNameUpperCamel}VO> source){
        return BeanUtil.copy(source,${modelNameUpperCamel}.class);
    }

}
