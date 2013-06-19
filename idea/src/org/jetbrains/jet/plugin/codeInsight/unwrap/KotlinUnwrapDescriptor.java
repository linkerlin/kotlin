/*
 * Copyright 2010-2013 JetBrains s.r.o.
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

package org.jetbrains.jet.plugin.codeInsight.unwrap;

import com.intellij.codeInsight.unwrap.UnwrapDescriptorBase;
import com.intellij.codeInsight.unwrap.Unwrapper;

public class KotlinUnwrapDescriptor extends UnwrapDescriptorBase {
    @Override
    protected Unwrapper[] createUnwrappers() {
        return new Unwrapper[] {
                new KoitlinUnwrappers.KotlinExpressionRemover("remove.expression"),
                new KoitlinUnwrappers.KotlinThenUnwrapper("unwrap.expression"),
                new KoitlinUnwrappers.KotlinElseRemover("remove.else"),
                new KoitlinUnwrappers.KotlinElseUnwrapper("unwrap.else"),
                new KoitlinUnwrappers.KotlinLoopUnwrapper("unwrap.expression"),
                new KoitlinUnwrappers.KotlinTryUnwrapper("unwrap.expression"),
                new KoitlinUnwrappers.KotlinCatchUnwrapper("unwrap.expression"),
                new KoitlinUnwrappers.KotlinCatchRemover("remove.expression"),
                new KoitlinUnwrappers.KotlinFinallyUnwrapper("unwrap.expression"),
                new KoitlinUnwrappers.KotlinFinallyRemover("remove.expression"),
                new KotlinLambdaUnwrapper("unwrap.expression"),
        };
    }
}