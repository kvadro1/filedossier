package ru.ilb.filedossier.entities;

import java.io.File;
import java.io.IOException;

public interface DossierFileVersion extends DossierContents {

    @Override
    void setContents(File file) throws IOException;

    @Override
    byte[] getContents() throws IOException;

    @Override
    void setContents(byte[] contents) throws IOException;

    @Override
    String getMediaType();

    @Override
    String getExtension();

    @Override
    default String getFileName() {
        return getCode();
    }

    Representation getRepresentation();

    void setStore(Store store);

    void setMediaType(String mediaType);
}
