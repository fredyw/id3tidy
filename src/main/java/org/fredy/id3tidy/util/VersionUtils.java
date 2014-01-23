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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.fredy.id3tidy.ID3TidyVersionException;

/**
 * A utility class for version related stuff.
 * 
 * @author fredy
 */
public class VersionUtils {
    private static final String UPDATE_URL = "https://raw.github.com/fredyw/id3tidy/master/VERSION.txt";
    
    private VersionUtils() {
    }
    
    /**
     * Gets the ID3Tidy latest version.
     * 
     * @return the ID3Tidy latest version
     */
    public static String getLatestVersion() {
        BufferedReader br = null;
        try {
            URL url = new URL(UPDATE_URL);
            URLConnection conn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return br.readLine();
        } catch (MalformedURLException e) {
            throw new ID3TidyVersionException(e);
        } catch (IOException e) {
            throw new ID3TidyVersionException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new ID3TidyVersionException(e);
                }
            }
        }
    }
    
    /**
     * Compares the two versions.
     * 
     * @param version1 the version 1
     * @param version2 the version 2
     * @return 0 if both versions are the same
     *         -1 if version1 is less than version2
     *         1 if version1 is greater than version2
     */
    public static int compare(String version1, String version2) {
        String[] ver1 = version1.split("\\.");
        String[] ver2 = version2.split("\\.");
        // compare the major versions
        int majorVer1 = Integer.parseInt(ver1[0]);
        int majorVer2 = Integer.parseInt(ver2[0]);
        if (majorVer1 < majorVer2) {
            return -1;
        } else if (majorVer1 > majorVer2) {
            return 1;
        } else {
            // compare the minor versions
            int minorVer1 = Integer.parseInt(ver1[1]);
            int minorVer2 = Integer.parseInt(ver2[1]);
            if (minorVer1 < minorVer2) {
                return -1;
            } else if (minorVer1 > minorVer2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
