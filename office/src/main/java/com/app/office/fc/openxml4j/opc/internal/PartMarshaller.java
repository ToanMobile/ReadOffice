package com.app.office.fc.openxml4j.opc.internal;

import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.opc.PackagePart;
import java.io.OutputStream;

public interface PartMarshaller {
    boolean marshall(PackagePart packagePart, OutputStream outputStream) throws OpenXML4JException;
}
