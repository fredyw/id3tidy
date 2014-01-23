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
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.fredy.id3tidy.ID3Tag;
import org.fredy.id3tidy.ID3TidyIOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ID3TagIOTest {

    @BeforeClass
    public static void setUp() throws Exception {
        FileUtils.copy(new File("mp3/empty.mp3"), new File("mp3/empty1.mp3"));
    }
    
    @AfterClass
    public static void cleanUp() {
        new File("mp3/empty1.mp3").delete();
    }
    
    @Test
    public void testReadWithFullInformation() {
        ID3Tag tag = ID3TagIO.read(new File("mp3/test1.mp3"));
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("Comment", tag.getComment());
        assertEquals("2010", tag.getYear());
        assertEquals("1", tag.getTrack());
    }

    @Test
    public void testReadWithPartialInformation() {
        ID3Tag tag = ID3TagIO.read(new File("mp3/test2.mp3"));
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("", tag.getComment());
        assertEquals("", tag.getYear());
        assertEquals("", tag.getTrack());
    }
    
    @Test
    public void testReadListOfFiles() {
        List<File> files = new ArrayList<File>();
        files.add(new File("mp3/test5.mp3"));
        files.add(new File("mp3/test6.mp3"));
        
        List<ID3Tag> tags = ID3TagIO.read(files);
        assertEquals("Artist", tags.get(0).getArtist());
        assertEquals("Album", tags.get(0).getAlbum());
        assertEquals("Title1", tags.get(0).getTitle());
        assertEquals("Genre", tags.get(0).getGenre());
        assertEquals("Comment", tags.get(0).getComment());
        assertEquals("2011", tags.get(0).getYear());
        assertEquals("6", tags.get(0).getTrack());
        
        assertEquals("Artist", tags.get(1).getArtist());
        assertEquals("Album", tags.get(1).getAlbum());
        assertEquals("Title2", tags.get(1).getTitle());
        assertEquals("Genre", tags.get(1).getGenre());
        assertEquals("Comment", tags.get(1).getComment());
        assertEquals("2011", tags.get(1).getYear());
        assertEquals("6", tags.get(1).getTrack());
    }
    
    @Test
    public void testReadEmptyTag() throws Exception {
        File file = new File("mp3/empty1.mp3");
        ID3Tag tag = ID3TagIO.read(file);
        assertEquals("", tag.getArtist());
        assertEquals("", tag.getAlbum());
        assertEquals("", tag.getTitle());
        assertEquals("", tag.getGenre());
        assertEquals("", tag.getComment());
        assertEquals("", tag.getYear());
        assertEquals("", tag.getTrack());
    }
    
    @Test
    public void testReadInvalidFile() {
        try {
            ID3TagIO.read(new File("mp3/invalid1.mp3"));
            fail("An ID3TidyIOException should be thrown");
        } catch (ID3TidyIOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testWriteOverwrite() {
        ID3Tag tag = new ID3Tag();
        tag.setArtist("Artist");
        tag.setAlbum("Album");
        tag.setTitle("Title");
        tag.setGenre("Genre");
        tag.setComment("Comment");
        tag.setYear("2011");
        tag.setTrack("6");
        
        ID3TagIO.write(new File("mp3/test3.mp3"), tag, true);
        
        tag = ID3TagIO.read(new File("mp3/test3.mp3"));
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("Comment", tag.getComment());
        assertEquals("2011", tag.getYear());
        assertEquals("6", tag.getTrack());
    }
    
    @Test
    public void testWriteNoOverwrite() {
        ID3Tag tag = new ID3Tag();
        tag.setArtist("Artist1");
        tag.setAlbum("Album1");
        tag.setTitle("Title1");
        tag.setGenre("Genre1");
        tag.setComment("Comment1");
        tag.setYear("2012");
        tag.setTrack("5");
        
        ID3TagIO.write(new File("mp3/test4.mp3"), tag, false);
        
        tag = ID3TagIO.read(new File("mp3/test4.mp3"));
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("Comment", tag.getComment());
        assertEquals("2011", tag.getYear());
        assertEquals("6", tag.getTrack());
    }
    
    @Test
    public void testWriteInvalidFile() {
        try {
            ID3Tag tag = new ID3Tag();
            tag.setArtist("Artist1");
            tag.setAlbum("Album1");
            tag.setTitle("Title1");
            tag.setGenre("Genre1");
            tag.setComment("Comment1");
            tag.setYear("2012");
            tag.setTrack("5");
            
            ID3TagIO.write(new File("mp3/invalid2.mp3"), tag, false);
            fail("An ID3TidyIOException should be thrown");
        } catch (ID3TidyIOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testWriteListOfFiles() {
        List<File> files = new ArrayList<File>();
        files.add(new File("mp3/test7.mp3"));
        files.add(new File("mp3/test8.mp3"));
        
        List<ID3Tag> tags = new ArrayList<ID3Tag>();
        ID3Tag tag = new ID3Tag();
        tag.setArtist("Artist1");
        tag.setAlbum("Album1");
        tag.setTitle("Title1");
        tag.setGenre("Genre1");
        tag.setComment("Comment1");
        tag.setYear("2012");
        tag.setTrack("5");
        tags.add(tag);
        
        tag = new ID3Tag();
        tag.setArtist("Artist2");
        tag.setAlbum("Album2");
        tag.setTitle("Title2");
        tag.setGenre("Genre2");
        tag.setComment("Comment2");
        tag.setYear("2012");
        tag.setTrack("5");
        tags.add(tag);
        
        ID3TagIO.write(files, tags, true);
        
        tags = ID3TagIO.read(files);
        assertEquals("Artist1", tags.get(0).getArtist());
        assertEquals("Album1", tags.get(0).getAlbum());
        assertEquals("Title1", tags.get(0).getTitle());
        assertEquals("Genre1", tags.get(0).getGenre());
        assertEquals("Comment1", tags.get(0).getComment());
        assertEquals("2012", tags.get(0).getYear());
        assertEquals("5", tags.get(0).getTrack());
        
        assertEquals("Artist2", tags.get(1).getArtist());
        assertEquals("Album2", tags.get(1).getAlbum());
        assertEquals("Title2", tags.get(1).getTitle());
        assertEquals("Genre2", tags.get(1).getGenre());
        assertEquals("Comment2", tags.get(1).getComment());
        assertEquals("2012", tags.get(1).getYear());
        assertEquals("5", tags.get(1).getTrack());
    }
}
