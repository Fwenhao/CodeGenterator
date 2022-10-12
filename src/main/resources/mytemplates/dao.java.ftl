package ${package.Mapper};

/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.mapperName}>
<#else>
public interface ${table.mapperName}{

}
</#if>
