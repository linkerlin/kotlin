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
package org.jetbrains.jet.codegen.defaultConstructor;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.codegen.defaultConstructor.AbstractDefaultConstructorCodegenTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/codegen/defaultArguments/reflection")
public class DefaultArgumentsReflectionTestGenerated extends AbstractDefaultConstructorCodegenTest {
    public void testAllFilesPresentInReflection() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/codegen/defaultArguments/reflection"), "kt", true);
    }
    
    @TestMetadata("classInClassObject.kt")
    public void testClassInClassObject() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/classInClassObject.kt");
    }
    
    @TestMetadata("classInObject.kt")
    public void testClassInObject() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/classInObject.kt");
    }
    
    @TestMetadata("classWithTwoDefaultArgs.kt")
    public void testClassWithTwoDefaultArgs() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/classWithTwoDefaultArgs.kt");
    }
    
    @TestMetadata("classWithVararg.kt")
    public void testClassWithVararg() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/classWithVararg.kt");
    }
    
    @TestMetadata("enum.kt")
    public void testEnum() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/enum.kt");
    }
    
    @TestMetadata("internalClass.kt")
    public void testInternalClass() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/internalClass.kt");
    }
    
    @TestMetadata("privateClass.kt")
    public void testPrivateClass() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/privateClass.kt");
    }
    
    @TestMetadata("privateConstructor.kt")
    public void testPrivateConstructor() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/privateConstructor.kt");
    }
    
    @TestMetadata("publicClass.kt")
    public void testPublicClass() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/publicClass.kt");
    }
    
    @TestMetadata("publicClassWoDefArgs.kt")
    public void testPublicClassWoDefArgs() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/publicClassWoDefArgs.kt");
    }
    
    @TestMetadata("publicInnerClass.kt")
    public void testPublicInnerClass() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/publicInnerClass.kt");
    }
    
    @TestMetadata("publicInnerClassInPrivateClass.kt")
    public void testPublicInnerClassInPrivateClass() throws Exception {
        doTest("compiler/testData/codegen/defaultArguments/reflection/publicInnerClassInPrivateClass.kt");
    }
    
}
