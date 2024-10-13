package com.app.office.fc.hpsf;

import com.app.office.fc.poifs.filesystem.DirectoryEntry;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.ListIterator;

public class MutablePropertySet extends PropertySet {
    private final int OFFSET_HEADER = ((((BYTE_ORDER_ASSERTION.length + FORMAT_ASSERTION.length) + 4) + 16) + 4);

    public MutablePropertySet() {
        this.byteOrder = LittleEndian.getUShort(BYTE_ORDER_ASSERTION);
        this.format = LittleEndian.getUShort(FORMAT_ASSERTION);
        this.osVersion = 133636;
        this.classID = new ClassID();
        this.sections = new LinkedList();
        this.sections.add(new MutableSection());
    }

    public MutablePropertySet(PropertySet propertySet) {
        this.byteOrder = propertySet.getByteOrder();
        this.format = propertySet.getFormat();
        this.osVersion = propertySet.getOSVersion();
        setClassID(propertySet.getClassID());
        clearSections();
        if (this.sections == null) {
            this.sections = new LinkedList();
        }
        for (Section mutableSection : propertySet.getSections()) {
            addSection(new MutableSection(mutableSection));
        }
    }

    public void setByteOrder(int i) {
        this.byteOrder = i;
    }

    public void setFormat(int i) {
        this.format = i;
    }

    public void setOSVersion(int i) {
        this.osVersion = i;
    }

    public void setClassID(ClassID classID) {
        this.classID = classID;
    }

    public void clearSections() {
        this.sections = null;
    }

    public void addSection(Section section) {
        if (this.sections == null) {
            this.sections = new LinkedList();
        }
        this.sections.add(section);
    }

    public void write(OutputStream outputStream) throws WritingNotSupportedException, IOException {
        int size = this.sections.size();
        TypeWriter.writeToStream(outputStream, (short) getByteOrder());
        TypeWriter.writeToStream(outputStream, (short) getFormat());
        TypeWriter.writeToStream(outputStream, getOSVersion());
        TypeWriter.writeToStream(outputStream, getClassID());
        TypeWriter.writeToStream(outputStream, size);
        int i = this.OFFSET_HEADER + (size * 20);
        ListIterator listIterator = this.sections.listIterator();
        while (listIterator.hasNext()) {
            MutableSection mutableSection = (MutableSection) listIterator.next();
            if (mutableSection.getFormatID() != null) {
                TypeWriter.writeToStream(outputStream, mutableSection.getFormatID());
                TypeWriter.writeUIntToStream(outputStream, (long) i);
                try {
                    i += mutableSection.getSize();
                } catch (HPSFRuntimeException e) {
                    Throwable reason = e.getReason();
                    if (reason instanceof UnsupportedEncodingException) {
                        throw new IllegalPropertySetDataException(reason);
                    }
                    throw e;
                }
            } else {
                throw new NoFormatIDException();
            }
        }
        ListIterator listIterator2 = this.sections.listIterator();
        while (listIterator2.hasNext()) {
            ((MutableSection) listIterator2.next()).write(outputStream);
        }
    }

    public InputStream toInputStream() throws IOException, WritingNotSupportedException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        write(byteArrayOutputStream);
        byteArrayOutputStream.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public void write(DirectoryEntry directoryEntry, String str) throws WritingNotSupportedException, IOException {
        try {
            directoryEntry.getEntry(str).delete();
        } catch (FileNotFoundException unused) {
        }
        directoryEntry.createDocument(str, toInputStream());
    }
}
