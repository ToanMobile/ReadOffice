package com.app.office.fc.openxml4j.opc.internal.marshallers;

import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackagePartName;
import com.app.office.fc.openxml4j.opc.PackageRelationshipCollection;
import com.app.office.fc.openxml4j.opc.internal.PartMarshaller;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public final class ZipPartMarshaller implements PartMarshaller {
    public static boolean marshallRelationshipPart(PackageRelationshipCollection packageRelationshipCollection, PackagePartName packagePartName, ZipOutputStream zipOutputStream) {
        return true;
    }

    public boolean marshall(PackagePart packagePart, OutputStream outputStream) throws OpenXML4JException {
        return true;
    }
}
