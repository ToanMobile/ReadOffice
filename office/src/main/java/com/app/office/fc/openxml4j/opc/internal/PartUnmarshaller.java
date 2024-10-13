package com.app.office.fc.openxml4j.opc.internal;

import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.internal.unmarshallers.UnmarshallContext;
import java.io.IOException;
import java.io.InputStream;

public interface PartUnmarshaller {
    PackagePart unmarshall(UnmarshallContext unmarshallContext, InputStream inputStream) throws InvalidFormatException, IOException;
}
