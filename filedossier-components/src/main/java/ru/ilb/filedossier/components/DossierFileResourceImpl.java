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
package ru.ilb.filedossier.components;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import ru.ilb.filedossier.api.DossierContextResource;
import ru.ilb.filedossier.api.DossierFileResource;
import ru.ilb.filedossier.entities.DossierFile;
import ru.ilb.filedossier.entities.Representation;
import ru.ilb.filedossier.filedossier.usecases.upload.UploadDossierFileContents;

public class DossierFileResourceImpl implements DossierFileResource {

    private final DossierFile dossierFile;

    @Context
    private ResourceContext resourceContext;

    public DossierFileResourceImpl(DossierFile dossierFile) {
        this.dossierFile = dossierFile;
    }

    @Override
    public Response getContents() {
        Representation representation = dossierFile.getRepresentation();
        return Response.ok(representation.getContents())
                .header("Content-Type", representation.getMediaType())
                .header("Content-Disposition",
                        "attachment; filename=" + representation.getFileName())
                .build();
    }

    @Override
    public void setContents(InputStream inputstream) {
        try {
            UploadDossierFileContents useCase = new UploadDossierFileContents(
                    Util.toByteArray(inputstream), dossierFile);
            useCase.upload();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read input stream: " + e);
        }
    }

    @Override
    public void uploadContents(File file) {
        dossierFile.setContents(file);
    }

    @Override
    public DossierContextResource getDossierContextResource() {
        DossierContextResourceImpl resource = new DossierContextResourceImpl(dossierFile);
        return resourceContext.initResource(resource);
    }
}
