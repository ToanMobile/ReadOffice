package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import java.io.IOException;
import java.util.Arrays;

@Internal
public class BookmarksTables {
    private PlexOfCps descriptorsFirst = new PlexOfCps(4);
    private PlexOfCps descriptorsLim = new PlexOfCps(0);
    private String[] names = new String[0];

    public BookmarksTables(byte[] bArr, FileInformationBlock fileInformationBlock) {
        read(bArr, fileInformationBlock);
    }

    public int getBookmarksCount() {
        return this.descriptorsFirst.length();
    }

    public GenericPropertyNode getDescriptorFirst(int i) throws IndexOutOfBoundsException {
        return this.descriptorsFirst.getProperty(i);
    }

    public int getDescriptorFirstIndex(GenericPropertyNode genericPropertyNode) {
        return Arrays.asList(this.descriptorsFirst.toPropertiesArray()).indexOf(genericPropertyNode);
    }

    public GenericPropertyNode getDescriptorLim(int i) throws IndexOutOfBoundsException {
        return this.descriptorsLim.getProperty(i);
    }

    public int getDescriptorsFirstCount() {
        return this.descriptorsFirst.length();
    }

    public int getDescriptorsLimCount() {
        return this.descriptorsLim.length();
    }

    public String getName(int i) throws ArrayIndexOutOfBoundsException {
        return this.names[i];
    }

    public int getNamesCount() {
        return this.names.length;
    }

    private void read(byte[] bArr, FileInformationBlock fileInformationBlock) {
        int fcSttbfbkmk = fileInformationBlock.getFcSttbfbkmk();
        int lcbSttbfbkmk = fileInformationBlock.getLcbSttbfbkmk();
        if (!(fcSttbfbkmk == 0 || lcbSttbfbkmk == 0)) {
            this.names = SttbfUtils.read(bArr, fcSttbfbkmk);
        }
        int fcPlcfbkf = fileInformationBlock.getFcPlcfbkf();
        int lcbPlcfbkf = fileInformationBlock.getLcbPlcfbkf();
        if (!(fcPlcfbkf == 0 || lcbPlcfbkf == 0)) {
            this.descriptorsFirst = new PlexOfCps(bArr, fcPlcfbkf, lcbPlcfbkf, BookmarkFirstDescriptor.getSize());
        }
        int fcPlcfbkl = fileInformationBlock.getFcPlcfbkl();
        int lcbPlcfbkl = fileInformationBlock.getLcbPlcfbkl();
        if (fcPlcfbkl != 0 && lcbPlcfbkl != 0) {
            this.descriptorsLim = new PlexOfCps(bArr, fcPlcfbkl, lcbPlcfbkl, 0);
        }
    }

    public void setName(int i, String str) {
        String[] strArr = this.names;
        if (i < strArr.length) {
            String[] strArr2 = new String[(i + 1)];
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            this.names = strArr2;
        }
        this.names[i] = str;
    }

    public void writePlcfBkmkf(FileInformationBlock fileInformationBlock, HWPFOutputStream hWPFOutputStream) throws IOException {
        PlexOfCps plexOfCps = this.descriptorsFirst;
        if (plexOfCps == null || plexOfCps.length() == 0) {
            fileInformationBlock.setFcPlcfbkf(0);
            fileInformationBlock.setLcbPlcfbkf(0);
            return;
        }
        int offset = hWPFOutputStream.getOffset();
        hWPFOutputStream.write(this.descriptorsFirst.toByteArray());
        int offset2 = hWPFOutputStream.getOffset();
        fileInformationBlock.setFcPlcfbkf(offset);
        fileInformationBlock.setLcbPlcfbkf(offset2 - offset);
    }

    public void writePlcfBkmkl(FileInformationBlock fileInformationBlock, HWPFOutputStream hWPFOutputStream) throws IOException {
        PlexOfCps plexOfCps = this.descriptorsLim;
        if (plexOfCps == null || plexOfCps.length() == 0) {
            fileInformationBlock.setFcPlcfbkl(0);
            fileInformationBlock.setLcbPlcfbkl(0);
            return;
        }
        int offset = hWPFOutputStream.getOffset();
        hWPFOutputStream.write(this.descriptorsLim.toByteArray());
        int offset2 = hWPFOutputStream.getOffset();
        fileInformationBlock.setFcPlcfbkl(offset);
        fileInformationBlock.setLcbPlcfbkl(offset2 - offset);
    }

    public void writeSttbfBkmk(FileInformationBlock fileInformationBlock, HWPFOutputStream hWPFOutputStream) throws IOException {
        String[] strArr = this.names;
        if (strArr == null || strArr.length == 0) {
            fileInformationBlock.setFcSttbfbkmk(0);
            fileInformationBlock.setLcbSttbfbkmk(0);
            return;
        }
        int offset = hWPFOutputStream.getOffset();
        SttbfUtils.write(hWPFOutputStream, this.names);
        int offset2 = hWPFOutputStream.getOffset();
        fileInformationBlock.setFcSttbfbkmk(offset);
        fileInformationBlock.setLcbSttbfbkmk(offset2 - offset);
    }
}
