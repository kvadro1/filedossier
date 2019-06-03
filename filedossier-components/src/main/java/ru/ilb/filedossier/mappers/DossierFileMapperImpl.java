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
package ru.ilb.filedossier.mappers;

import javax.inject.Named;
import ru.ilb.filedossier.view.DossierFileView;

@Named
public class DossierFileMapperImpl implements DossierFileMapper {

    @Override
    public DossierFileView fromModel(ru.ilb.filedossier.entities.DossierFile model) {
        DossierFileView df = new DossierFileView();
        df.setCode(model.getCode());
        df.setName(model.getName());
        df.setExists(model.getExists());
        df.setReadonly(model.getReadonly());
        df.setRequired(model.getRequired());
        return df;
    }

}
