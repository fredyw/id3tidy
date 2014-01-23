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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.fredy.id3tidy.M3UException;
import org.fredy.id3tidy.M3UInfo;

/**
 * @author fredy
 */
public class M3UIO {

    private static final String M3U_HEADER = "#EXTM3U";
    private static final String M3U_EXTRAINFO = "#EXTINF";
    
    /**
     * Writes the M3U file.
     * @param destinationFile the destination file
     * @param m3uInfo the M3UInfo instance
     */
    public static void write(File destinationFile, List<M3UInfo> m3uInfoList) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(destinationFile);
            pw.println(M3U_HEADER);
            for (M3UInfo m3u : m3uInfoList) {
                StringBuilder sb = new StringBuilder(M3U_EXTRAINFO);
                sb.append(":").append(m3u.getTrackLength()).append(",").append(m3u.getTitle())
                        .append(" - ").append(m3u.getArtist());
                pw.println(sb.toString());
                pw.println(m3u.getPath());
            }
        } catch (FileNotFoundException e) {
            throw new M3UException(e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
    
    /**
     * Reads the M3U file.
     * @param m3uFile the M3U file
     * @return the list of MP3 files
     */
    public static List<File> read(File m3uFile) {
        List<File> files = new ArrayList<File>();
        try {
            FileUtils.eachLine(m3uFile, new M3UParserFileFunction(m3uFile, files));
        } catch (IOException e) {
            throw new M3UException(e);
        }
        return files;
    }
}
