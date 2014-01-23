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
import java.io.IOException;
import java.util.logging.Logger;

import org.fredy.id3tidy.ID3Tag;
import org.fredy.id3tidy.M3UException;
import org.fredy.id3tidy.M3UInfo;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * @author fredy
 */
public class M3UUtils {

    private static Logger logger = Logger.getLogger(M3UUtils.class.getName());
    
    private M3UUtils() {
    }
    
    /**
     * Gets an M3U information.
     * @param file mp3 file
     * @param absolutePath true for absolute path; false for relative path
     * @return the M3U instance
     */
    public static M3UInfo getM3UInfo(File file, boolean absolutePath) {
        logger.info("Retrieving M3U information from " + file);
        M3UInfo m3uInfo = new M3UInfo();
        try {
            AudioFile f = AudioFileIO.read(file);
            m3uInfo.setTrackLength(f.getAudioHeader().getTrackLength());
            ID3Tag tag = ID3TagIO.read(file);
            m3uInfo.setTitle(tag.getTitle());
            m3uInfo.setArtist(tag.getArtist());
            if (absolutePath) {
                m3uInfo.setPath(file.getAbsolutePath());
            } else {
                m3uInfo.setPath(file.getName());
            }
        } catch (CannotReadException e) {
            throw new M3UException(e);
        } catch (IOException e) {
            throw new M3UException(e);
        } catch (TagException e) {
            throw new M3UException(e);
        } catch (ReadOnlyFileException e) {
            throw new M3UException(e);
        } catch (InvalidAudioFrameException e) {
            throw new M3UException(e);
        }
        logger.info("M3U info=" + m3uInfo);
        return m3uInfo;
    }
}
