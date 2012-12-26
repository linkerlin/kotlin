/*
 * Copyright 2010-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.jet.codegen.generated;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.codegen.generated.AbstractCodegenTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/codegen/defaultArguments/blackBox")
@InnerTestClasses({DefaultArgumentsBlackBoxTestGenerated.Constructor.class, DefaultArgumentsBlackBoxTestGenerated.Function.class})
public class DefaultArgumentsBlackBoxTestGenerated extends AbstractCodegenTest {
    public void testAllFilesPresentInBlackBox() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/codegen/defaultArguments/blackBox"), "kt", true);
    }
    
    @TestMetadata("compiler/testData/codegen/defaultArguments/blackBox/constructor")
    public static class Constructor extends AbstractCodegenTest {
        public void testAllFilesPresentInConstructor() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/codegen/defaultArguments/blackBox/constructor"), "kt", true);
        }
        
        @TestMetadata("annotation.kt")
        public void testAnnotation() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/annotation.kt");
        }
        
        @TestMetadata("defArgs1.kt")
        public void testDefArgs1() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/defArgs1.kt");
        }
        
        @TestMetadata("defArgs1InnerClass.kt")
        public void testDefArgs1InnerClass() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/defArgs1InnerClass.kt");
        }
        
        @TestMetadata("defArgs2.kt")
        public void testDefArgs2() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/defArgs2.kt");
        }
        
        @TestMetadata("doubleDefArgs1InnerClass.kt")
        public void testDoubleDefArgs1InnerClass() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/doubleDefArgs1InnerClass.kt");
        }
        
        @TestMetadata("enum.kt")
        public void testEnum() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/enum.kt");
        }
        
        @TestMetadata("enumWithOneDefArg.kt")
        public void testEnumWithOneDefArg() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/enumWithOneDefArg.kt");
        }
        
        @TestMetadata("enumWithTwoDefArgs.kt")
        public void testEnumWithTwoDefArgs() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/enumWithTwoDefArgs.kt");
        }
        
        @TestMetadata("enumWithTwoDoubleDefArgs.kt")
        public void testEnumWithTwoDoubleDefArgs() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/enumWithTwoDoubleDefArgs.kt");
        }
        
        @TestMetadata("kt2852.kt")
        public void testKt2852() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/constructor/kt2852.kt");
        }
        
    }
    
    @TestMetadata("compiler/testData/codegen/defaultArguments/blackBox/function")
    public static class Function extends AbstractCodegenTest {
        public void testAllFilesPresentInFunction() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/codegen/defaultArguments/blackBox/function"), "kt", true);
        }
        
        @TestMetadata("extentionFunction.kt")
        public void testExtentionFunction() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/extentionFunction.kt");
        }
        
        @TestMetadata("extentionFunctionDouble.kt")
        public void testExtentionFunctionDouble() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/extentionFunctionDouble.kt");
        }
        
        @TestMetadata("extentionFunctionDoubleTwoArgs.kt")
        public void testExtentionFunctionDoubleTwoArgs() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/extentionFunctionDoubleTwoArgs.kt");
        }
        
        @TestMetadata("extentionFunctionInClassObject.kt")
        public void testExtentionFunctionInClassObject() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/extentionFunctionInClassObject.kt");
        }
        
        @TestMetadata("extentionFunctionInObject.kt")
        public void testExtentionFunctionInObject() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/extentionFunctionInObject.kt");
        }
        
        @TestMetadata("extentionFunctionWithOneDefArg.kt")
        public void testExtentionFunctionWithOneDefArg() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/extentionFunctionWithOneDefArg.kt");
        }
        
        @TestMetadata("funInTrait.kt")
        public void testFunInTrait() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/funInTrait.kt");
        }
        
        @TestMetadata("innerExtentionFunction.kt")
        public void testInnerExtentionFunction() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/innerExtentionFunction.kt");
        }
        
        @TestMetadata("innerExtentionFunctionDouble.kt")
        public void testInnerExtentionFunctionDouble() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/innerExtentionFunctionDouble.kt");
        }
        
        @TestMetadata("innerExtentionFunctionDoubleTwoArgs.kt")
        public void testInnerExtentionFunctionDoubleTwoArgs() throws Exception {
            doTest("compiler/testData/codegen/defaultArguments/blackBox/function/innerExtentionFunctionDoubleTwoArgs.kt");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("DefaultArgumentsBlackBoxTestGenerated");
        suite.addTestSuite(DefaultArgumentsBlackBoxTestGenerated.class);
        suite.addTestSuite(Constructor.class);
        suite.addTestSuite(Function.class);
        return suite;
    }
}
