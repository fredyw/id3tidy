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

import org.fredy.id3tidy.ID3Tag;
import org.fredy.id3tidy.ID3TagEnum;
import org.fredy.id3tidy.ID3TagSelection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ID3TidyTest {

    @BeforeClass
    public static void setUp() throws Exception {
        FileUtils.copy(new File("mp3/delete.mp3"), new File("mp3/delete1.mp3"));
        FileUtils.copy(new File("mp3/delete.mp3"), new File("mp3/delete2.mp3"));
        FileUtils.copy(new File("mp3/trim.mp3"), new File("mp3/trim1.mp3"));
        FileUtils.copy(new File("mp3/trim.mp3"), new File("mp3/trim2.mp3"));
    }
    
    @AfterClass
    public static void cleanUp() {
        new File("mp3/delete1.mp3").delete();
        new File("mp3/delete2.mp3").delete();
        new File("mp3/trim1.mp3").delete();
        new File("mp3/trim2.mp3").delete();
        new File("mp3/test2.m3u").delete();
    }
    
    @Test
    public void testUpdateByFileNamePattern() {
        List<File> files = new ArrayList<File>();
        files.add(new File("mp3/My_Artist - My_Title.mp3"));
        files.add(new File("mp3/Your Artist - Your Title.mp3"));
        List<Object> parts = new ArrayList<Object>();
        parts.add(ID3TagEnum.ARTIST);
        parts.add(" - ");
        parts.add(ID3TagEnum.TITLE);
        ID3Tidy.update(files, parts, true);
        
        List<ID3Tag> tags = ID3TagIO.read(files);
        assertEquals("My_Artist", tags.get(0).getArtist());
        assertEquals("My_Title", tags.get(0).getTitle());
        assertEquals("Album", tags.get(0).getAlbum());
        assertEquals("Genre", tags.get(0).getGenre());
        assertEquals("2012", tags.get(0).getYear());
        assertEquals("5", tags.get(0).getTrack());
        assertEquals("Comment", tags.get(0).getComment());
        
        assertEquals("Your Artist", tags.get(1).getArtist());
        assertEquals("Your Title", tags.get(1).getTitle());
        assertEquals("Album", tags.get(1).getAlbum());
        assertEquals("Genre", tags.get(1).getGenre());
        assertEquals("2012", tags.get(1).getYear());
        assertEquals("5", tags.get(1).getTrack());
        assertEquals("Comment", tags.get(1).getComment());
    }

    @Test
    public void testUpdate() {
        ID3Tag tag = new ID3Tag();
        tag.setArtist("Artist");
        tag.setAlbum("Album");
        tag.setTitle("Title");
        tag.setGenre("Genre");
        tag.setComment("Comment");
        tag.setYear("2011");
        tag.setTrack("6");
        
        ID3Tidy.update(new File("mp3/test9.mp3"), tag, true);

        tag = ID3Tidy.read(new File("mp3/test9.mp3"));
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("Comment", tag.getComment());
        assertEquals("2011", tag.getYear());
        assertEquals("6", tag.getTrack());
    }
    
    @Test
    public void testRead() {
        ID3Tag tag = ID3Tidy.read(new File("mp3/test10.mp3"));
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("Comment", tag.getComment());
        assertEquals("2011", tag.getYear());
        assertEquals("6", tag.getTrack());
    }
    
    @Test
    public void testTrimAll() {
        File file = new File("mp3/trim1.mp3");
        List<File> files = new ArrayList<File>();
        files.add(file);

        ID3TagSelection selection = new ID3TagSelection();
        selection.setTitleSelected(true);
        selection.setArtistSelected(true);
        selection.setAlbumSelected(true);
        selection.setGenreSelected(true);
        selection.setCommentSelected(true);
        
        ID3Tidy.trim(files, selection);
        
        ID3Tag tag = ID3Tidy.read(file);
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("Genre", tag.getGenre());
        assertEquals("Comment", tag.getComment());
        assertEquals("2010", tag.getYear());
        assertEquals("1", tag.getTrack());
    }
    
    @Test
    public void testTrimAlbumTitle() {
        File file = new File("mp3/trim2.mp3");
        List<File> files = new ArrayList<File>();
        files.add(file);

        ID3TagSelection selection = new ID3TagSelection();
        selection.setTitleSelected(true);
        selection.setArtistSelected(false);
        selection.setAlbumSelected(true);
        selection.setGenreSelected(false);
        selection.setCommentSelected(false);
        
        ID3Tidy.trim(files, selection);
        
        ID3Tag tag = ID3Tidy.read(file);
        assertEquals("  Artist     ", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("   Genre   ", tag.getGenre());
        assertEquals("  Comment   ", tag.getComment());
        assertEquals("2010", tag.getYear());
        assertEquals("1", tag.getTrack());
    }
    
    @Test
    public void testDeleteAll() {
        File file = new File("mp3/delete1.mp3");
        List<File> files = new ArrayList<File>();
        files.add(file);

        ID3TagSelection selection = new ID3TagSelection();
        selection.setTitleSelected(true);
        selection.setArtistSelected(true);
        selection.setAlbumSelected(true);
        selection.setGenreSelected(true);
        selection.setCommentSelected(true);
        
        ID3Tidy.delete(files, selection);
        
        ID3Tag tag = ID3Tidy.read(file);
        assertEquals("", tag.getArtist());
        assertEquals("", tag.getAlbum());
        assertEquals("", tag.getTitle());
        assertEquals("", tag.getGenre());
        assertEquals("", tag.getComment());
        assertEquals("2010", tag.getYear());
        assertEquals("1", tag.getTrack());
    }
    
    @Test
    public void testDeleteCommentYearGenre() {
        File file = new File("mp3/delete2.mp3");
        List<File> files = new ArrayList<File>();
        files.add(file);

        ID3TagSelection selection = new ID3TagSelection();
        selection.setTitleSelected(false);
        selection.setArtistSelected(false);
        selection.setAlbumSelected(false);
        selection.setGenreSelected(true);
        selection.setCommentSelected(true);
        
        ID3Tidy.delete(files, selection);
        
        ID3Tag tag = ID3Tidy.read(file);
        assertEquals("Artist", tag.getArtist());
        assertEquals("Album", tag.getAlbum());
        assertEquals("Title", tag.getTitle());
        assertEquals("", tag.getGenre());
        assertEquals("", tag.getComment());
        assertEquals("2010", tag.getYear());
        assertEquals("1", tag.getTrack());
    }
    
    @Test
    public void testCreateM3UFile() throws Exception {
        List<File> sourceFiles = new ArrayList<File>();
        sourceFiles.add(new File("mp3/My_Artist - My_Title.mp3"));
        sourceFiles.add(new File("mp3/Your Artist - Your Title.mp3"));
        
        File destinationFile = new File("mp3/test2.m3u");
        ID3Tidy.createM3UFile(sourceFiles, destinationFile, false);
        
        assertEquals(FileUtils.readLines(new File("mp3/Sample.m3u")), FileUtils.readLines(
            new File("mp3/test2.m3u")));
    }
    
    @Test
    public void testReadM3UFile() throws Exception {
        List<File> files = ID3Tidy.readM3UFile(new File("mp3/Sample.m3u"));
        assertEquals("My_Artist - My_Title.mp3", files.get(0).getName());
        assertEquals("Your Artist - Your Title.mp3", files.get(1).getName());
    }
}
