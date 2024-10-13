package com.app.office.fc.openxml4j.opc.internal.unmarshallers;

import com.app.office.fc.openxml4j.opc.PackagePartName;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import java.util.zip.ZipEntry;

public final class UnmarshallContext {
    private ZipPackage _package;
    private PackagePartName partName;
    private ZipEntry zipEntry;

    public UnmarshallContext(ZipPackage zipPackage, PackagePartName packagePartName) {
        this._package = zipPackage;
        this.partName = packagePartName;
    }

    public ZipPackage getPackage() {
        return this._package;
    }

    public void setPackage(ZipPackage zipPackage) {
        this._package = zipPackage;
    }

    /* access modifiers changed from: package-private */
    public PackagePartName getPartName() {
        return this.partName;
    }

    public void setPartName(PackagePartName packagePartName) {
        this.partName = packagePartName;
    }

    /* access modifiers changed from: package-private */
    public ZipEntry getZipEntry() {
        return this.zipEntry;
    }

    public void setZipEntry(ZipEntry zipEntry2) {
        this.zipEntry = zipEntry2;
    }
}
