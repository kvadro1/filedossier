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
package ru.ilb.filedossier.ddl.reader;

import ru.ilb.filedossier.ddl.PackageDefinition;

/**
 *
 * Reader of dossier contents in specific format
 * @author slavb
 */
public interface DossierReader {

    String modelFileExtension();
    /**
     * read and parse contents to PackageDefinition
     * @param source source (for example, xml or any other format)
     * @param dossierMode context parameter used by reader
     * @return
     */
    PackageDefinition read(String source, String dossierMode);
}
