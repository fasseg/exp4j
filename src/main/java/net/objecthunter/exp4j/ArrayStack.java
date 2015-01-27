/*
 * Copyright 2015 Federico Vera
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

package net.objecthunter.exp4j;

import java.util.EmptyStackException;

/**
 * Simple double stack using a double array as data storage
 *
 * @author Federico Vera (dktcoding [at] gmail)
 */
class ArrayStack {

    private double[] data;

    private int idx;

    ArrayStack() {
        this(5);
    }

    ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Stack's capacity must be positive");
        }

        data = new double[initialCapacity];
        idx = -1;
    }

    void push(double value) {
        if (idx + 1 == data.length) {
            double[] temp = new double[(int) (data.length * 1.2) + 1];
            System.arraycopy(data, 0, temp, 0, data.length);
            data = temp;
        }

        data[++idx] = value;
    }

    double peek() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx];
    }

    double pop() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx--];
    }

    boolean isEmpty() {
        return idx == -1;
    }

    int size() {
        return idx + 1;
    }
}
