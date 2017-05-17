/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycat.modeling.language.impl;

import greycat.modeling.language.Classifier;
import greycat.modeling.language.TypedGraph;

import java.util.HashMap;
import java.util.Map;

public class TypedGraphImpl implements TypedGraph {

    private final Map<String, Classifier> classifiers;

    public TypedGraphImpl() {
        classifiers = new HashMap<String, Classifier>();
    }

    @Override
    public Classifier[] classifiers() {
        return classifiers.values().toArray(new Classifier[classifiers.size()]);
    }

    @Override
    public void addClassifier(Classifier classifier) {
        classifiers.put(classifier.name(), classifier);
//        classifiers.put(classifier.fqn(), classifier);
    }

    @Override
    public Classifier get(String fqn) {
        return classifiers.get(fqn);
    }
}
