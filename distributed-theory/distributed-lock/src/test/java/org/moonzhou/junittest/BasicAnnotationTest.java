package org.moonzhou.junittest;

import org.junit.*;

/**
 * Junit注解基本使用方法<br>
 *     1. 注解调用顺序:@BeforeClass –> @Before –> @Test –> @After –> @AfterClass
 *
 * @author moon-zhou
 * @Date: 2020/1/17 14:27
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BasicAnnotationTest {
    // Run once, e.g. Database connection, connection pool
    @BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
    }

    // Run once, e.g close connection, cleanup
    @AfterClass
    public static void runOnceAfterClass() {
        System.out.println("@AfterClass - runOnceAfterClass");
    }

    // Should rename to @BeforeTestMethod
    // e.g. Creating an similar object and share for all @Test
    @Before
    public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod");
    }

    // Should rename to @AfterTestMethod
    @After
    public void runAfterTestMethod() {
        System.out.println("@After - runAfterTestMethod");
    }

    @Test
    public void test_method_1() {
        System.out.println("@Test - test_method_1");
    }

    @Test
    public void test_method_2() {
        System.out.println("@Test - test_method_2");
    }

    @Ignore
    @Test
    public void testIgnore() {
        System.out.println("忽略的方法");
    }
}
