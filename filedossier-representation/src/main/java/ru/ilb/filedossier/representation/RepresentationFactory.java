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

import ru.ilb.filedossier.entities.Representation;
import java.net.URI;
import ru.ilb.filedossier.ddl.RepresentationDefinition;
import ru.ilb.filedossier.entities.DossierContext;
import ru.ilb.filedossier.scripting.TemplateEvaluator;

/**
 *
 * @author slavb
 */
public class RepresentationFactory {

    public Representation createRepresentation(URI baseUri, RepresentationDefinition representationModel,
	    DossierContext dossierContext, TemplateEvaluator evaluator) {
	String mediaType = representationModel.getMediaType();
	switch (mediaType) {
	case "application/vnd.oasis.opendocument.spreadsheet":
	    return new OdsXsltRepresentation(representationModel.getMediaType(),
		    URI.create(representationModel.getStylesheet()), URI.create(representationModel.getTemplate()));
	case "application/pdf":
	    return new XmlPdfRepresentation(mediaType, URI.create(representationModel.getTemplate()),
		    URI.create(evaluator.evaluateStringExpression(representationModel.getSchema(), dossierContext)));
	default:
	    throw new IllegalArgumentException("unsupported media type " + mediaType);
	}
    }
}
