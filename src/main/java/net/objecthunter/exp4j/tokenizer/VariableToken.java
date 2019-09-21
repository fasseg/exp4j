/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.objecthunter.exp4j.tokenizer;

/**
 * represents a setVariable used in an expression
 */
public class VariableToken extends Token {
    private final String name;

    /**
     * Get the name of the setVariable
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Create a new instance
     *
     * @param name the name of the setVariable
     */
    public VariableToken(String name) {
        super(TOKEN_VARIABLE);
        this.name = name;
    }
}
