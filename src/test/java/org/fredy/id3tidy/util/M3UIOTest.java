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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.fredy.id3tidy.M3UInfo;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author fredy
 */
public class M3UIOTest {

    @AfterClass
    public static void cleanUp() {
        new File("mp3/test1.m3u").delete();
    }
    
    @Test
    public void testWrite() throws Exception {
        List<M3UInfo> m3uInfoList = new ArrayList<M3UInfo>();
        M3UInfo info = new M3UInfo();
        info.setArtist("Artist1");
        info.setTitle("Title1");
        info.setTrackLength(2345);
        info.setPath("/home/whoever/whatever1.mp3");
        m3uInfoList.add(info);
        
        info = new M3UInfo();
        info.setArtist("Artist2");
        info.setTitle("Title2");
        info.setTrackLength(2345);
        info.setPath("/home/whoever/whatever2.mp3");
        m3uInfoList.add(info);
        
        M3UIO.write(new File("mp3/test1.m3u"), m3uInfoList);
        
        assertEquals(FileUtils.readLines(new File("mp3/expected1.m3u")), 
                FileUtils.readLines(new File("mp3/test1.m3u")));  
    }
    
    @Test
    public void testRead() {
        List<File> files = M3UIO.read(new File("mp3/Sample1.m3u"));
        assertEquals("My_Artist - My_Title.mp3", files.get(0).getName());
        assertEquals("Your Artist - Your Title.mp3", files.get(1).getName());
    }
}
