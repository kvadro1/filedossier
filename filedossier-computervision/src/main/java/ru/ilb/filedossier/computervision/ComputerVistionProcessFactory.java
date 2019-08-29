/*
 * Copyright 2019 kudrin.
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
package ru.ilb.filedossier.computervision;

import java.io.File;
import ru.ilb.filedossier.computervision.process.BarcodeDetection;
import ru.ilb.filedossier.computervision.process.ComputerVistionProcesss;
import ru.ilb.filedossier.computervision.process.SignatureDetection;

/**
 *
 * @author kudrin
 */
public class ComputerVistionProcessFactory {

    public static ComputerVistionProcesss createProcess(ComputerVistionProcessType processType, File inputFile) {
        switch (processType) {
            case SIGNATURE_DETECTION:
                return new SignatureDetection().withInputFile(inputFile);
            case BARCODE_DETECTION:
                return new BarcodeDetection().withInputFile(inputFile);
            default:
                throw new RuntimeException("unsuported process type");
        }
    }
}
