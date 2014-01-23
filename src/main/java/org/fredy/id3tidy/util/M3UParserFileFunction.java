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

import java.io.File;
import java.util.List;

/**
 * A file function to parse M3U file.
 * @author fredy
 */
public class M3UParserFileFunction implements FileFunction {

    private List<File> mp3Files;
    private File m3uFile;
    
    public M3UParserFileFunction(File m3uFile, List<File> mp3Files) {
        this.mp3Files = mp3Files;
        this.m3uFile = m3uFile;
    }
    
    @Override
    public void read(String line) {
        if (!line.trim().startsWith("#")) {
            if (line.trim().toLowerCase().endsWith(".mp3")) {
                // Try relative path first, then absolute path.
                File file = new File(m3uFile.getParentFile(), line.trim());
                if (file.exists()) {
                    mp3Files.add(file);
                } else if (!file.exists()) { // Use absolute path
                    file = new File(line.trim());
                    if (file.exists()) {
                        mp3Files.add(file);
                    }
                }
            }
        }
    }
}
