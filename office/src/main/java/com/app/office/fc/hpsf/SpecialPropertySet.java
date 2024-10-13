package com.app.office.fc.hpsf;

import com.app.office.fc.poifs.filesystem.DirectoryEntry;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class SpecialPropertySet extends MutablePropertySet {
    private MutablePropertySet delegate;

    public abstract PropertyIDMap getPropertySetIDMap();

    public SpecialPropertySet(PropertySet propertySet) {
        this.delegate = new MutablePropertySet(propertySet);
    }

    public SpecialPropertySet(MutablePropertySet mutablePropertySet) {
        this.delegate = mutablePropertySet;
    }

    public int getByteOrder() {
        return this.delegate.getByteOrder();
    }

    public int getFormat() {
        return this.delegate.getFormat();
    }

    public int getOSVersion() {
        return this.delegate.getOSVersion();
    }

    public ClassID getClassID() {
        return this.delegate.getClassID();
    }

    public int getSectionCount() {
        return this.delegate.getSectionCount();
    }

    public List getSections() {
        return this.delegate.getSections();
    }

    public boolean isSummaryInformation() {
        return this.delegate.isSummaryInformation();
    }

    public boolean isDocumentSummaryInformation() {
        return this.delegate.isDocumentSummaryInformation();
    }

    public Section getFirstSection() {
        return this.delegate.getFirstSection();
    }

    public void addSection(Section section) {
        this.delegate.addSection(section);
    }

    public void clearSections() {
        this.delegate.clearSections();
    }

    public void setByteOrder(int i) {
        this.delegate.setByteOrder(i);
    }

    public void setClassID(ClassID classID) {
        this.delegate.setClassID(classID);
    }

    public void setFormat(int i) {
        this.delegate.setFormat(i);
    }

    public void setOSVersion(int i) {
        this.delegate.setOSVersion(i);
    }

    public InputStream toInputStream() throws IOException, WritingNotSupportedException {
        return this.delegate.toInputStream();
    }

    public void write(DirectoryEntry directoryEntry, String str) throws WritingNotSupportedException, IOException {
        this.delegate.write(directoryEntry, str);
    }

    public void write(OutputStream outputStream) throws WritingNotSupportedException, IOException {
        this.delegate.write(outputStream);
    }

    public boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    public Property[] getProperties() throws NoSingleSectionException {
        return this.delegate.getProperties();
    }

    /* access modifiers changed from: protected */
    public Object getProperty(int i) throws NoSingleSectionException {
        return this.delegate.getProperty(i);
    }

    /* access modifiers changed from: protected */
    public boolean getPropertyBooleanValue(int i) throws NoSingleSectionException {
        return this.delegate.getPropertyBooleanValue(i);
    }

    /* access modifiers changed from: protected */
    public int getPropertyIntValue(int i) throws NoSingleSectionException {
        return this.delegate.getPropertyIntValue(i);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public String toString() {
        return this.delegate.toString();
    }

    public boolean wasNull() throws NoSingleSectionException {
        return this.delegate.wasNull();
    }
}
