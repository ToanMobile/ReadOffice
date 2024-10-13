package com.app.office.fc.openxml4j.opc;

import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import java.util.TreeMap;

public final class PackagePartCollection extends TreeMap<PackagePartName, PackagePart> {
    private static final long serialVersionUID = 2515031135957635515L;

    public Object clone() {
        return super.clone();
    }

    public PackagePart put(PackagePartName packagePartName, PackagePart packagePart) {
        if (!containsKey(packagePartName)) {
            return (PackagePart) super.put(packagePartName, packagePart);
        }
        throw new InvalidOperationException("You can't add a part with a part name derived from another part ! [M1.11]");
    }

    public PackagePart remove(Object obj) {
        return (PackagePart) super.remove(obj);
    }
}
