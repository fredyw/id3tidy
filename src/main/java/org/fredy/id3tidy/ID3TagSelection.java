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

package org.fredy.id3tidy;

/**
 * @author fredy
 */
public class ID3TagSelection {

    private boolean allSelected;
    private boolean artistSelected;
    private boolean titleSelected;
    private boolean albumSelected;
    private boolean genreSelected;
    private boolean commentSelected;

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }

    public boolean isArtistSelected() {
        return artistSelected;
    }

    public void setArtistSelected(boolean artistSelected) {
        this.artistSelected = artistSelected;
    }

    public boolean isTitleSelected() {
        return titleSelected;
    }

    public void setTitleSelected(boolean titleSelected) {
        this.titleSelected = titleSelected;
    }

    public boolean isAlbumSelected() {
        return albumSelected;
    }

    public void setAlbumSelected(boolean albumSelected) {
        this.albumSelected = albumSelected;
    }

    public boolean isGenreSelected() {
        return genreSelected;
    }

    public void setGenreSelected(boolean genreSelected) {
        this.genreSelected = genreSelected;
    }

    public boolean isCommentSelected() {
        return commentSelected;
    }

    public void setCommentSelected(boolean commentSelected) {
        this.commentSelected = commentSelected;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID3TagSelection [albumSelected=").append(albumSelected).append(
                ", allSelected=").append(allSelected).append(", artistSelected=").append(
                artistSelected).append(", commentSelected=").append(commentSelected).append(
                ", genreSelected=").append(genreSelected).append(", titleSelected=").append(
                titleSelected).append("]");
        return builder.toString();
    }
}
