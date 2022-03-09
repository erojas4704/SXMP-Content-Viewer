package com.eddiejrojas.SXMproject.api;

import java.io.IOException;

public final class GraphqlSchemaReaderUtil {
    public static String getSchemaFromFileName(String filename) throws IOException {
        return new String(
                GraphqlSchemaReaderUtil.class
                        .getClassLoader()
                        .getResourceAsStream("graphql/" + filename + ".graphql")
                        .readAllBytes());
    }
}
