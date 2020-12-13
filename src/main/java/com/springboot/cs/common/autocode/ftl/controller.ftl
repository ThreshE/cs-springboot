package ${controllerPackage};

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import ${commonException};
import ${baseException};
import ${tipException};
import com.springboot.cs.common.bean.WebResult;
import ${servicePackage}.${modelNameUpperCamel}Service;
import ${svoPackage}.${modelNameUpperCamel}SearchVO;
import ${voPackage}.${modelNameUpperCamel}VO;
import ${pageResult};
import ${languageEnum};
import ${multiLanguage};

@Slf4j
@RestController
@RequestMapping("${url}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/list")
    @MultiLanguage(success = LanguageEnums.ResultMessageEnum.list_success)
    public WebResult<PageResult<${modelNameUpperCamel}VO>> list(@RequestBody ${modelNameUpperCamel}SearchVO svo) throws BaseRuntimeException{
        WebResult result = new WebResult();

        PageResult<${modelNameUpperCamel}VO> pageResult = ${modelNameLowerCamel}Service.list(svo);
        result.setData(pageResult);
        result.setResult(true);

        return result;
    }

    @PostMapping("insert")
    @MultiLanguage(success = LanguageEnums.ResultMessageEnum.insert_success)
    public WebResult insert(@RequestBody  ${modelNameUpperCamel}VO vo) throws BaseRuntimeException{
        WebResult result = new WebResult();

        ${modelNameLowerCamel}Service.insert(vo);
        result.setResult(true);

        return result;
    }

    @PostMapping("update")
    @MultiLanguage(success = LanguageEnums.ResultMessageEnum.update_success)
    public WebResult update(@RequestBody  ${modelNameUpperCamel}VO vo) throws BaseRuntimeException{
        WebResult result = new WebResult();

        ${modelNameLowerCamel}Service.update(vo);
        result.setResult(true);

        return result;
    }

    @PostMapping("/get")
    @MultiLanguage(success = LanguageEnums.ResultMessageEnum.detail_success)
    public WebResult get(@RequestBody  ${modelNameUpperCamel}SearchVO svo) throws BaseRuntimeException{
        WebResult result = new WebResult();

        ${modelNameUpperCamel}VO vo = ${modelNameLowerCamel}Service.get(svo.get${pkName}());
        result.setData(vo);
        result.setResult(true);

        return result;
    }

    @PostMapping("/delete")
    @MultiLanguage(success = LanguageEnums.ResultMessageEnum.delete_success)
    public WebResult delete(@RequestBody  ${modelNameUpperCamel}SearchVO svo) throws BaseRuntimeException{
        WebResult result = new WebResult();

        ${modelNameLowerCamel}Service.delete(svo.get${pkName}());

        return result;
    }

    @PostMapping("/deleteList")
    @MultiLanguage(success = LanguageEnums.ResultMessageEnum.delete_list_success)
    public WebResult deleteList(@RequestBody  ${modelNameUpperCamel}SearchVO svo) throws BaseRuntimeException{
        WebResult result = new WebResult();

        ${modelNameLowerCamel}Service.deleteList(svo.getList${pkName}());

        return result;
    }

}