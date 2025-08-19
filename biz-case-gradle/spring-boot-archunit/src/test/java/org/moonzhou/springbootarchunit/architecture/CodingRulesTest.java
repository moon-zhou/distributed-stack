package org.moonzhou.springbootarchunit.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

@AnalyzeClasses(packages = "org.moonzhou.springbootarchunit", importOptions = ImportOption.DoNotIncludeTests.class)
class CodingRulesTest {

    /**
     * 测试项目中是否避免使用@Autowired进行字段注入
     * 测试成功例子：使用构造函数注入或setter注入代替@Autowired字段注入
     * 测试失败例子：在字段上直接使用@Autowired注解进行依赖注入
     */
    @ArchTest
    static final ArchRule noAutowiredFieldInjection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION
            .allowEmptyShould(true);

    /**
     * 测试项目中是否避免抛出通用异常如Exception, RuntimeException等
     * 测试成功例子：抛出具体业务异常如UserNotFoundException, InvalidParameterException等
     * 测试失败例子：抛出Exception, RuntimeException, Throwable等通用异常
     */
    @ArchTest
    static final ArchRule noGenericExceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
            .allowEmptyShould(true);
}