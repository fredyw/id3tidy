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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fredy.id3tidy.ID3Tag;
import org.fredy.id3tidy.ID3TagEnum;
import org.fredy.id3tidy.ID3TagSelection;
import org.fredy.id3tidy.M3UInfo;

/**
 * A utility class for ID3 tag related operations.
 * @author fredy
 */
public class ID3Tidy {
    
    private static final Logger logger = Logger.getLogger(ID3Tidy.class.getName());
    private static final String MP3_EXTENSION = ".mp3";
    
    private ID3Tidy() {
    }
    
    /**
     * Updates the ID3 tag information.
     * @param files the list of mp3 files
     * @param parts the file name parts
     * @param overwrite true to overwrite; false otherwise
     */
    public static void update(List<File> files, List<?> parts, boolean overwrite) {
        StringBuilder regex = new StringBuilder();
        Map<Integer, String> map = new HashMap<Integer, String>();
        int i = 1;
        for (Object part : parts) {
            if (part instanceof ID3TagEnum) {
                regex.append("(.*)");
                map.put(i, ((ID3TagEnum) part).toString());
                i++;
            } else {
                regex.append(RegexEscapeUtils.escapeRegex(part.toString()));
            }
        }
        logger.info("Regex=" + regex.toString());
        for (File file : files) {
            try {
                ID3Tag tag = createID3Tag(file, regex.toString(), map);
                logger.info("ID3Tag=" + tag.toString());
                ID3TagIO.write(file, tag, overwrite);
            } catch (IntrospectionException e) {
                logger.severe(e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.severe(e.getMessage());
            } catch (IllegalAccessException e) {
                logger.severe(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.severe(e.getMessage());
            }
        }
    }
    
    /**
     * Reads the ID3 tag information from a file.
     * @param file the mp3 file
     * @return the ID3 tag information
     */
    public static ID3Tag read(File file) {
        return ID3TagIO.read(file);
    }
    
    /**
     * Updates the ID3 tag information.
     * @param file the mp3 file
     * @param tag the ID3 tag
     * @param overwrite true to overwrite; false otherwise
     */
    public static void update(File file, ID3Tag tag, boolean overwrite) {
        ID3TagIO.write(file, tag, overwrite);
    }
    
    private static ID3Tag createID3Tag(File file, String regex, Map<Integer, String> map)
            throws IntrospectionException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        if (!file.getName().toLowerCase().endsWith(MP3_EXTENSION)) {
            throw new IllegalArgumentException("The file does not have .mp3 extension");
        }
        ID3Tag tag = ID3Tidy.read(file);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(file.getName().substring(0,
                file.getName().toLowerCase().lastIndexOf(MP3_EXTENSION)));
        if (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                BeanInfo info = Introspector.getBeanInfo(ID3Tag.class);
                for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                    if (pd.getName().equals(map.get(i))) {
                        System.out.println(pd.getName() + ";" + m.group(i).trim());
                        pd.getWriteMethod().invoke(tag, m.group(i).trim());
                    }
                }
            }
        }
        return tag;
    }
    
    /**
     * Trim the selected ID3 tags.
     * @param files the mp3 files
     * @param selection the list of ID3 tags selected
     */
    public static void trim(List<File> files, ID3TagSelection selection) {
        for (File file : files) {
            logger.info("Trimming ID3 tags from " + file);
            ID3Tag tag = ID3Tidy.read(file);
            if (selection.isAllSelected()) {
                tag.setArtist(tag.getArtist().trim());
                tag.setAlbum(tag.getAlbum().trim());
                tag.setTitle(tag.getTitle().trim());
                tag.setGenre(tag.getGenre().trim());
                tag.setComment(tag.getComment().trim());
                tag.setTrack(tag.getTrack().trim());
                tag.setYear(tag.getYear().trim());
            } else {
                if (selection.isArtistSelected()) {
                    tag.setArtist(tag.getArtist().trim());
                } 
                if (selection.isAlbumSelected()) {
                    tag.setAlbum(tag.getAlbum().trim());
                }
                if (selection.isTitleSelected()) {
                    tag.setTitle(tag.getTitle().trim());
                }
                if (selection.isGenreSelected()) {
                    tag.setGenre(tag.getGenre().trim());
                }
                if (selection.isCommentSelected()) {
                    tag.setComment(tag.getComment().trim());
                }
            }
            ID3Tidy.update(file, tag, true);
        }
    }
    
    /**
     * Delete the selected ID3 tags.
     * @param files the mp3 files
     * @param selection the list of ID3 tags selected
     */
    public static void delete(List<File> files, ID3TagSelection selection) {
        for (File file : files) {
            logger.info("Deleting ID3 tags from " + file);
            ID3Tag tag = ID3Tidy.read(file);
            if (selection.isAllSelected()) {
                tag.setArtist("");
                tag.setAlbum("");
                tag.setTitle("");
                tag.setGenre("");
                tag.setComment("");
                tag.setTrack("");
                tag.setYear("");
            } else {
                if (selection.isArtistSelected()) {
                    tag.setArtist("");
                } 
                if (selection.isAlbumSelected()) {
                    tag.setAlbum("");
                }
                if (selection.isTitleSelected()) {
                    tag.setTitle("");
                }
                if (selection.isGenreSelected()) {
                    tag.setGenre("");
                }
                if (selection.isCommentSelected()) {
                    tag.setComment("");
                }
            }
            ID3Tidy.update(file, tag, true);
        }
    }
    
    /**
     * Create M3U file.
     * @param sourceFiles the list of mp3 files
     * @param destinationFile the destination file
     * @param absolutePath true for absolute path; false for relative path
     */
    public static void createM3UFile(List<File> sourceFiles, File destinationFile, boolean absolutePath) {
        List<M3UInfo> m3uInfoList = new ArrayList<M3UInfo>();
        for (File file : sourceFiles) {
            M3UInfo m3uInfo = M3UUtils.getM3UInfo(file, absolutePath);
            m3uInfoList.add(m3uInfo);
        }
        M3UIO.write(destinationFile, m3uInfoList);
    }
    
    /**
     * Reads M3U file.
     * @param m3uFile the M3U file
     * @return the list of MP3 files
     */
    public static List<File> readM3UFile(File m3uFile) {
        return M3UIO.read(m3uFile);
    }
}
