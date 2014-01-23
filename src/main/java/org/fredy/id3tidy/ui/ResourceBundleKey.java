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

package org.fredy.id3tidy.ui;

/**
 * @author fredy
 */
public enum ResourceBundleKey {
    VERSION("version"),
    
    LABEL_APPLICATION("label.application"),
    LABEL_CREATED_BY("label.createdby"),
    LABEL_ARTIST("label.artist"),
    LABEL_TITLE("label.title"),
    LABEL_ALBUM("label.album"),
    LABEL_GENRE("label.genre"),
    LABEL_YEAR("label.year"),
    LABEL_TRACK("label.track"),
    LABEL_FILENAME_PATTERN("label.filenamepattern"),
    LABEL_COMMENT("label.comment"),
    LABEL_OVERWRITE("label.overwrite"),
    LABEL_ALL("label.all"),
    LABEL_DELETE("label.delete"),
    LABEL_TRIM("label.trim"),
    LABEL_M3U_FILE("label.m3ufile"),
    
    BUTTON_CHECK_FOR_UPDATE("button.checkforupdate"),
    BUTTON_UPDATE("button.update"),
    BUTTON_OPEN("button.open"),
    BUTTON_UP("button.up"),
    BUTTON_DOWN("button.down"),
    BUTTON_DELETE("button.delete"),
    BUTTON_ADD_PATTERN("button.addpattern"),
    BUTTON_REMOVE_PATTERN("button.removepattern"),
    BUTTON_CREATE("button.create"),
    BUTTON_SAVETO("button.saveto"),
    
    FILECHOOSER_ID3TIDY_SUPPORTED_FILES("filechooser.id3tidysupportedfiles"),
    FILECHOOSER_M3U("filechooser.m3u"),
    
    RADIO_BUTTON_RELATIVE_PATH("radiobutton.relativepath"),
    RADIO_BUTTON_ABSOLUTE_PATH("radiobutton.absolutepath"),
    
    ERROR_TITLE("error.title"),
    ERROR_UNABLE_TO_READ("error.unabletoread"),
    
    INFO_CHECK_FOR_UPDATE("info.checkforupdate"),
    INFO_LATEST_VERSION("info.latestversion"),
    INFO_NEW_VERSION("info.newversion");
    
    private String key;

    private ResourceBundleKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
