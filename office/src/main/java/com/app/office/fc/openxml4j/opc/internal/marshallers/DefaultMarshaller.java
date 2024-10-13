package com.app.office.fc.openxml4j.opc.internal.marshallers;

import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.internal.PartMarshaller;
import java.io.OutputStream;

public final class DefaultMarshaller implements PartMarshaller {
    public boolean marshall(PackagePart packagePart, OutputStream outputStream) throws OpenXML4JException {
        return packagePart.save(outputStream);
    }
}
