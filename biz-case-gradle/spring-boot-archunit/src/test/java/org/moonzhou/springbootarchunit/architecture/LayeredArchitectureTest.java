package org.moonzhou.springbootarchunit.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.moonzhou.springbootarchunit", importOptions = ImportOption.DoNotIncludeTests.class)
class LayeredArchitectureTest {

    /**
     * 测试项目的分层架构是否符合设计规范
     * Controllers层不能被任何层访问（实际是只能被自身访问）
     * Services层只能被Controllers层访问
     * Repositories层只能被Services层访问
     * 测试成功例子：Controller调用Service，Service调用Repository，符合分层架构规范
     * 测试失败例子：Controller直接调用Repository，或者Service调用Controller等违反分层架构的行为
     */
    @ArchTest
    static final ArchRule layeredArchitectureRule = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controllers").definedBy("org.moonzhou.springbootarchunit.controller..")
            .layer("Services").definedBy("org.moonzhou.springbootarchunit.service..")
            .layer("Repositories").definedBy("org.moonzhou.springbootarchunit.repository..")

            .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
            .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
            .whereLayer("Repositories").mayOnlyBeAccessedByLayers("Services")
            .allowEmptyShould(true);
}