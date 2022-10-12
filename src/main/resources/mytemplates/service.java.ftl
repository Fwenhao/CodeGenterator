package ${package.Service};

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/


@Service
@Transactional(readOnly = true)
<#if kotlin>
    interface ${table.serviceName}
<#else>
public interface ${table.serviceName} {

}
</#if>
