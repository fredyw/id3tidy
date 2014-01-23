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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 * @author fredy
 */
public class FileUtilsTest {

    @Test
    public void testGetFileListing() throws Exception {
        List<File> files = FileUtils.getFileListing(new File("./testdata"), ".mp3", ".dat");
        for (File file : files) {
            System.out.println(file);
        }
        assertEquals(7, files.size());
    }
    
    @Test
    public void testCopy() throws Exception {
        File destination = new File("mp3/copy.mp3");
        FileUtils.copy(new File("mp3/empty.mp3"), destination);
        assertTrue(destination.exists());
        destination.delete();
    }
}
