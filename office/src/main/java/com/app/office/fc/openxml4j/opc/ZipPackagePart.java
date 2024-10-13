package com.app.office.fc.openxml4j.opc;

import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.app.office.fc.openxml4j.opc.internal.marshallers.ZipPartMarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;

public class ZipPackagePart extends PackagePart {
    private ZipEntry zipEntry;

    /* access modifiers changed from: protected */
    public OutputStream getOutputStreamImpl() {
        return null;
    }

    public ZipPackagePart(ZipPackage zipPackage, PackagePartName packagePartName, String str) throws InvalidFormatException {
        super(zipPackage, packagePartName, str);
    }

    public ZipPackagePart(ZipPackage zipPackage, ZipEntry zipEntry2, PackagePartName packagePartName, String str) throws InvalidFormatException {
        super(zipPackage, packagePartName, str);
        this.zipEntry = zipEntry2;
    }

    public ZipEntry getZipArchive() {
        return this.zipEntry;
    }

    /* access modifiers changed from: protected */
    public InputStream getInputStreamImpl() throws IOException {
        return this._container.getZipArchive().getInputStream(this.zipEntry);
    }

    public boolean save(OutputStream outputStream) throws OpenXML4JException {
        return new ZipPartMarshaller().marshall(this, outputStream);
    }

    public boolean load(InputStream inputStream) {
        throw new InvalidOperationException("Method not implemented !");
    }

    public void close() {
        throw new InvalidOperationException("Method not implemented !");
    }

    public void flush() {
        throw new InvalidOperationException("Method not implemented !");
    }
}
