package com.app.office.thirdpart.emf.io;

import com.app.office.thirdpart.emf.io.RoutedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PromptInputStream extends RoutedInputStream {
    public PromptInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public void addPromptListener(String str, final PromptListener promptListener) {
        addRoute(str, str, (RouteListener) new RouteListener() {
            public void routeFound(RoutedInputStream.Route route) throws IOException {
                promptListener.promptFound(route);
            }
        });
    }
}
