/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fredy.id3tidy.util;

import java.util.Arrays;
import java.util.List;

/*
 * This class is used to escape regular expression special characters.
 * @author fredy
 */
public class RegexEscapeUtils {

    private static final List<Character> regexSpecialChars = Arrays.asList(
            new Character[] { '[', ']', '.', '(', ')', '{', '}', ',', '+', '$',
                    '^', '-' });

    private RegexEscapeUtils() {
    }

    public static String escapeRegex(String regex) {
        StringBuilder newString = new StringBuilder();
        char[] chars = regex.toCharArray();
        for (char c : chars) {
            if (regexSpecialChars.contains(Character.valueOf(c))) {
                newString.append("\\");
            }
            newString.append(c);
        }
        return newString.toString();
    }
}

