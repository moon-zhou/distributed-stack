package org.moonzhou.springbootarchunit.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "org.moonzhou.springbootarchunit", importOptions = ImportOption.DoNotIncludeTests.class)
class NamingConventionTest {

    /**
     * 测试service包下的类名是否以Service结尾
     * 测试成功例子：UserService, OrderService等以Service结尾的类名
     * 测试失败例子：UserSrv, OrderSvc等不以Service结尾的类名
     */
    @ArchTest
    static final ArchRule serviceNamingRule = classes()
            .that().resideInAPackage("..service..")
            .should().haveSimpleNameEndingWith("Service")
            .allowEmptyShould(true);

    /**
     * 测试repository包下的类名是否以Repository结尾
     * 测试成功例子：UserRepository, OrderRepository等以Repository结尾的类名
     * 测试失败例子：UserRepo, OrderDao等不以Repository结尾的类名
     */
    @ArchTest
    static final ArchRule repositoryNamingRule = classes()
            .that().resideInAPackage("..repository..")
            .should().haveSimpleNameEndingWith("Repository")
            .allowEmptyShould(true);

    /**
     * 测试controller包下的类名是否以Controller结尾
     * 测试成功例子：UserController, OrderController等以Controller结尾的类名
     * 测试失败例子：UserCtrl, OrderCtl等不以Controller结尾的类名
     */
    @ArchTest
    static final ArchRule controllerNamingRule = classes()
            .that().resideInAPackage("..controller..")
            .should().haveSimpleNameEndingWith("Controller")
            .allowEmptyShould(true);
}