/* 
* Copyright 2015 Holger Klene
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

import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;

public class TypeUtil {

    public static boolean isAllIntegral(Number ... value) {
        for (Number v : value) {
            if (!(v instanceof Long || v instanceof Integer || v instanceof BigInteger)) {
                return false;
            }
        }
        return true;
    }

    public static Number parseConstant(String value) {
        final String v = value.toLowerCase();
        final boolean hex = v.startsWith("0x");
        try {
            if (hex) {
                return Long.valueOf(value.substring(2), 16);
            }
            return Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            if (hex && -1 == v.lastIndexOf('p')) {
                // add binary exponent declaration if missing
                return Double.valueOf(value + "p0");
            }
            return Double.valueOf(value);
        }
    }

    public static double[] toDouble(Number ... values) {
        double[] result = new double[values.length];
        int i = 0;
        for (Number v : values) {
            result[i++] = v.doubleValue();
        }
        return result;
    }

    public static void toDouble(Map<?, Number> values) {
        for (Entry<?, Number> entry : values.entrySet()) {
            if (!(entry.getValue() instanceof Double)) {
                entry.setValue(entry.getValue().doubleValue());
            }
        }
    }

}
