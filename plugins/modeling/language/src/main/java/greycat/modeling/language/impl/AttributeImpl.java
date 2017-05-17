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

import greycat.modeling.language.Attribute;
import greycat.modeling.language.Index;

import java.util.HashSet;
import java.util.Set;

public class AttributeImpl extends PropertyImpl implements Attribute {
    private final boolean isArray;
    private final Set<Index> indexes;

    public AttributeImpl(String name, String type, boolean isArray) {
        super(name, type);
        this.isArray = isArray;
        this.indexes = new HashSet<>();
    }


    @Override
    public boolean isArray() {
        return isArray;
    }

    @Override
    public Index[] indexes() {
        return indexes.toArray(new Index[indexes.size()]);
    }

    @Override
    public void addIndex(Index index) {
        indexes.add(index);
    }
}
