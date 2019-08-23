/*
 * Copyright 2019 slavb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ilb.filedossier.representation;

import ru.ilb.filedossier.entities.DossierContents;
import ru.ilb.filedossier.entities.DossierPath;

public class DossierContentsHolder implements DossierContents {

    private byte[] contents;

    private String mediaType;

    private String code;

    private String name;

    private String extension;

    public DossierContentsHolder(byte[] contents, String mediaType, String code, String name, String extension) {
        this.contents = contents;
        this.mediaType = mediaType;
        this.code = code;
        this.name = name;
        this.extension = extension;
    }

    @Override
    public byte[] getContents() {
        return contents;
    }

    @Override
    public void setContents(byte[] data) {
        this.contents = data;
    }

    @Override
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public DossierPath getParent() {
        return null;
    }

    @Override
    public void setParent(DossierPath parent) {

    }

}
