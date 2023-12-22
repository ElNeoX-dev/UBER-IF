package com.malveillance.uberif.util;

import java.io.File;
import java.io.InputStream;

public class Reader {
    public Reader() {
    }

    public InputStream getFileAsIOStream(final String fileName)
    {
        InputStream ioStream = getClass()
                .getClassLoader()
                .getResourceAsStream("com/malveillance/uberif/" + fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public File getFile(final String path) {
        return new File(path);
    }
}
