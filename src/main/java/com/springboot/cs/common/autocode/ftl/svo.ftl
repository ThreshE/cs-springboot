package ${svoPackage};

import ${baseSearchBean};
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel
public class ${modelNameUpperCamel}SearchVO extends BaseSearchVO {

    private ${pkType} ${pkNameSmall};

    private List<${pkType}> list${pkName};

}
