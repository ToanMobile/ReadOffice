package com.app.office.thirdpart.emf.io;

import com.app.office.thirdpart.emf.io.RoutedInputStream;
import java.io.IOException;

public interface RouteListener {
    void routeFound(RoutedInputStream.Route route) throws IOException;
}
