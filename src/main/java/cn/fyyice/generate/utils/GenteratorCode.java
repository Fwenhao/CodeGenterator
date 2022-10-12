package cn.fyyice.generate.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.util.StringUtils;

import java.util.*;

public class GenteratorCode {
    /**
     * 自定义模板,模板引擎是 freemarker
     */
    private static final String ENTITY_TEMPLATE_PATH = "/templates/entity.java.ftl";
    private static final String XML_TEMPLATE_PATH = "/templates/mapper.xml.ftl";
    private static final String MAPPER_TEMPLATE_PATH = "/templates/mapper.java.ftl";
    private static final String CONTROLLER_TEMPLATE_PATH = "/templates/controller.java.ftl";

    private static final String SERVICE_IMPL_TEMPLATE_PATH = "/templates/serviceImpl.java.ftl";
    private static final String SERVICE_TEMPLATE_PATH = "/templates/service.java.ftl";

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!StringUtils.isEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        /**
         * 代码生成器
         */
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //生成文件的输出目录
        String projectPath = System.getProperty("user.dir");

        globalConfig.setOutputDir(projectPath + "/src/main/java");
        //Author设置作者
        globalConfig.setAuthor("fwh");
        //开启 Swagger2 注解
        globalConfig.setSwagger2(false);
        //是否覆盖文件
        globalConfig.setFileOverride(true);
        // 开启 ActiveRecord 模式
        globalConfig.setActiveRecord(true);
        // 开启 BaseResultMap（XML ResultMap,生成基本的resultMap）
        globalConfig.setBaseResultMap(false);
        // 开启 baseColumnList（XML columList,生成基本的SQL片段）
        globalConfig.setBaseColumnList(false);
        //生成后打开文件
        globalConfig.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！（各层文件名称方式，例如： %Mapper 生成 UserMapper）
        globalConfig.setMapperName("%sDao");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        mpg.setGlobalConfig(globalConfig);

        /**
         * 数据源配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 数据库类型,默认MYSQL
        dataSourceConfig.setDbType(DbType.MYSQL);
        //自定义数据类型转换
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
        dataSourceConfig.setUrl(PropertiesUtils.getPropertyField("spring.datasource.url"));
        dataSourceConfig.setDriverName(PropertiesUtils.getPropertyField("spring.datasource.driver-class-name"));
        dataSourceConfig.setUsername(PropertiesUtils.getPropertyField("spring.datasource.username"));
        dataSourceConfig.setPassword(PropertiesUtils.getPropertyField("spring.datasource.password"));
        mpg.setDataSource(dataSourceConfig);

        /**
         * 包配置
         */
        PackageConfig packageConfig = new PackageConfig();
        String modileName = scanner("模块名");
        packageConfig.setModuleName(modileName);
        String modileName1 = modileName.replace(".", "/");
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        /**
         * 配置文件直接找的dev,包名读取不到.
         */
        packageConfig.setParent(PropertiesUtils.getPropertyField("project.package.name"));
        //Controller包名
        packageConfig.setController("controller");
        //Entity包名
        packageConfig.setEntity("entity");
        //Mapper包名
        packageConfig.setMapper("dao");
        //Mapper XML包名
        packageConfig.setXml("mapper.xml");
        //Service包名
        packageConfig.setService("service");
        //Service Impl包名
        packageConfig.setServiceImpl("service.impl");
        mpg.setPackageInfo(packageConfig);
        /**
         * 自定义配置
         */
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("Author", "Author : " + this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };
        /**
         * 自定义输出配置
         */
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(XML_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	mapper.xml生成路径+名称		如果Entity设置了前后缀、此处注意xml的名称会跟着发生变化
                return projectPath + "/src/main/resources/mapper/" + modileName1 + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        //调整 Entity 生成目录
        focList.add(new FileOutConfig(ENTITY_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Entity生成路径+名称		如果Entity设置了前后缀、此处名称会跟着发生变化
                return projectPath + "/src/main/java/cn/fyyice/" + modileName1 + "/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        //调整 Mapper 生成目录
        focList.add(new FileOutConfig(MAPPER_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Mapper生成路径+名称		如果Entity设置了前后缀、此处注意Mapper的名称会跟着发生变化
                return projectPath + "/src/main/java/cn/fyyice/" + modileName1 + "/dao/" + tableInfo.getEntityName() + "Dao" + StringPool.DOT_JAVA;
            }
        });
        //调整Controller生成
        focList.add(new FileOutConfig(CONTROLLER_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Controller生成路径+名称		如果Entity设置了前后缀、此处注意Controller的名称会跟着发生变化
                return projectPath + "/src/main/java/cn/fyyice/" + modileName1 + "/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        //调整Service生成
        focList.add(new FileOutConfig(SERVICE_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //	Service生成路径+名称		如果Entity设置了前后缀、此处注意Service的名称会跟着发生变化
                return projectPath + "/src/main/java/cn/fyyice/" + modileName1 + "/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        mpg.setCfg(injectionConfig);

        /**
         * 配置模板
         */
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        /**
         * 策略配置
         */
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略（表名生成策略）
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(scanner("表名,多个英文逗号分割").split(","));

        //实体是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);

        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);

        //设置自定义继承的Service类全称，带包名
        //strategy.setSuperServiceClass();
        //strategy.setSuperServiceImplClass();

        //设置自定义继承的Entity类全称，带包名
        strategy.setSuperEntityClass("BaseEntity");

        //设置自定义继承的Controller类全称，带包名
        strategy.setSuperControllerClass("BaseController");

        //设置自定义继承的Mapper类全称，带包名
        //strategy.setSuperMapperClass();

        //设置自定义基础的Entity类，公共字段
        //strategy.setSuperEntityColumns("id");

        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(false);
        //表名前缀
        //strategy.setTablePrefix(packageConfig.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
