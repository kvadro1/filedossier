/*
 * Copyright 2019 kuznetsov_me.
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
package ru.ilb.filedossier.core;

import java.io.IOException;
import java.util.List;
import ru.ilb.filedossier.entities.DossierContext;
import ru.ilb.filedossier.context.DossierContextService;
import ru.ilb.filedossier.entities.Representation;
import ru.ilb.filedossier.entities.Store;

/**
 *
 * @author kuznetsov_me
 */
public class PdfDossierFile extends DossierFileImpl {

    private DossierContext context;

    private boolean multipage = false;

    public PdfDossierFile(Store store, String code, String name, boolean required, boolean readonly, boolean hidden,
	    String mediaType, List<Representation> representations, DossierContextService dossierContextService) {
	super(store, code, name, required, readonly, hidden, mediaType, representations, dossierContextService);
    }

    @Override
    public void setContents(byte[] data) {
	if (multipage) {
	    setMultipageContents(data);
	} else {
	    try {
		store.setContents(getStoreFileName(), data);
	    } catch (IOException ex) {
		throw new RuntimeException(ex);
	    }
	}
    }

    private void setMultipageContents(byte[] data) {
	context = getDossierContext();
	int page = context.asMap().size() + 1;
	try {
	    store.setContents(Integer.toString(page), data);
	    context.setProperty("pages", page);
	    dossierContextService.putContext(getStoreFileName(), context);
	} catch (IOException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public DossierContext getDossierContext() {
	if (this.context == null) {
	    this.context = dossierContextService.getContext(code);
	}
	return this.context;
    }

    @Override
    public byte[] getContents() {
	return super.getContents();
    }

    @Override
    public void setMultipage() {
	store = store.getNestedFileStore(code);
	multipage = true;
    }
}
