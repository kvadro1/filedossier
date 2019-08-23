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
package ru.ilb.filedossier.context;

import ru.ilb.filedossier.entities.DossierContext;

/**
 *
 * @author kuznetsov_me
 */
public interface DossierContextService {

    /**
     * Returns DossierContext by contextKey
     *
     * @param contextKey context key to search
     * @see ru.ilb.filedossier.entities.DossierContext
     * @return DossierContext
     */
    DossierContext getContext(String contextKey);

    /**
     * Puts DossierContext to a repository
     *
     * @param contextKey context key
     * @param context DossierContext implementation
     * @see ru.ilb.filedossier.entities.DossierContext
     */
    void putContext(String contextKey, DossierContext context);

}
