/*
 * Copyright 2019 chunaev.
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
package ru.ilb.filedossier.functions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chunaev
 */
public class RuntimeFunction implements ByteFunction {

    private final URI commandUri;

    public RuntimeFunction(URI commandUri) {
        this.commandUri = commandUri;
    }

    private Process p;

    @Override
    public byte[] apply(byte[] t) {

        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(commandUri.toString(), new String(t)));
        File commandFile = Paths.get(commandUri.getPath()).toFile();
        if (!commandFile.exists()) {
            throw new IllegalArgumentException(commandFile.toString() + " does not exists");
        }
        if (!commandFile.canExecute()) {
            throw new IllegalArgumentException(commandFile.toString() + " not executable");
        }

        pb.directory(commandFile.getParentFile());
        byte[] output = null;
        try {
            p = pb.start();

            OutputStream os = p.getOutputStream();
            output = ((ByteArrayOutputStream) os).toByteArray();
            os.close();

        } catch (IOException ex) {
            Logger.getLogger(RuntimeFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

}
