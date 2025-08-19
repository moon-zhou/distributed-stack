package org.moonzhou.springbootarchunit.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "org.moonzhou.springbootarchunit", importOptions = ImportOption.DoNotIncludeTests.class)
class AnnotationTest {

    /**
     * 测试service包下的类是否正确使用了@Service注解
     * 测试成功例子：service包下的UserService类使用了@Service注解
     * 测试失败例子：service包下的UserService类没有使用@Service注解或使用了其他注解如@Component
     */
    @ArchTest
    static final ArchRule serviceAnnotationRule = classes()
            .that().resideInAPackage("..service..")
            .should().beAnnotatedWith(Service.class)
            .allowEmptyShould(true);

    /**
     * 测试repository包下的类是否正确使用了@Repository注解
     * 测试成功例子：repository包下的UserRepository类使用了@Repository注解
     * 测试失败例子：repository包下的UserRepository类没有使用@Repository注解或使用了其他注解如@Component
     */
    @ArchTest
    static final ArchRule repositoryAnnotationRule = classes()
            .that().resideInAPackage("..repository..")
            .should().beAnnotatedWith(Repository.class)
            .allowEmptyShould(true);

    /**
     * 测试controller包下的类是否正确使用了@RestController或@Controller注解
     * 测试成功例子：controller包下的UserController类使用了@RestController或@Controller注解
     * 测试失败例子：controller包下的UserController类没有使用@RestController或@Controller注解或使用了其他注解如@Service
     */
    @ArchTest
    static final ArchRule controllerAnnotationRule = classes()
            .that().resideInAPackage("..controller..")
            .should().beAnnotatedWith(RestController.class)
            .orShould().beAnnotatedWith(Controller.class)
            .allowEmptyShould(true);
}