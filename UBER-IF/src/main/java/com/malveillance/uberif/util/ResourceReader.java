package com.malveillance.uberif.util;

import java.io.InputStream;

public class ResourceReader {
    public ResourceReader() {
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
}
