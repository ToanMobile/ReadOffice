package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import java.io.IOException;

@Internal
public class NotesTables {
    private PlexOfCps descriptors;
    private final NoteType noteType;
    private PlexOfCps textPositions;

    public NotesTables(NoteType noteType2) {
        this.descriptors = new PlexOfCps(FootnoteReferenceDescriptor.getSize());
        PlexOfCps plexOfCps = new PlexOfCps(0);
        this.textPositions = plexOfCps;
        this.noteType = noteType2;
        plexOfCps.addProperty(new GenericPropertyNode(0, 1, new byte[0]));
    }

    public NotesTables(NoteType noteType2, byte[] bArr, FileInformationBlock fileInformationBlock) {
        this.descriptors = new PlexOfCps(FootnoteReferenceDescriptor.getSize());
        this.textPositions = new PlexOfCps(0);
        this.noteType = noteType2;
        read(bArr, fileInformationBlock);
    }

    public GenericPropertyNode getDescriptor(int i) {
        return this.descriptors.getProperty(i);
    }

    public int getDescriptorsCount() {
        return this.descriptors.length();
    }

    public GenericPropertyNode getTextPosition(int i) {
        return this.textPositions.getProperty(i);
    }

    private void read(byte[] bArr, FileInformationBlock fileInformationBlock) {
        int notesDescriptorsOffset = fileInformationBlock.getNotesDescriptorsOffset(this.noteType);
        int notesDescriptorsSize = fileInformationBlock.getNotesDescriptorsSize(this.noteType);
        if (!(notesDescriptorsOffset == 0 || notesDescriptorsSize == 0)) {
            this.descriptors = new PlexOfCps(bArr, notesDescriptorsOffset, notesDescriptorsSize, FootnoteReferenceDescriptor.getSize());
        }
        int notesTextPositionsOffset = fileInformationBlock.getNotesTextPositionsOffset(this.noteType);
        int notesTextPositionsSize = fileInformationBlock.getNotesTextPositionsSize(this.noteType);
        if (notesTextPositionsOffset != 0 && notesTextPositionsSize != 0) {
            this.textPositions = new PlexOfCps(bArr, notesTextPositionsOffset, notesTextPositionsSize, 0);
        }
    }

    public void writeRef(FileInformationBlock fileInformationBlock, HWPFOutputStream hWPFOutputStream) throws IOException {
        PlexOfCps plexOfCps = this.descriptors;
        if (plexOfCps == null || plexOfCps.length() == 0) {
            fileInformationBlock.setNotesDescriptorsOffset(this.noteType, hWPFOutputStream.getOffset());
            fileInformationBlock.setNotesDescriptorsSize(this.noteType, 0);
            return;
        }
        int offset = hWPFOutputStream.getOffset();
        hWPFOutputStream.write(this.descriptors.toByteArray());
        int offset2 = hWPFOutputStream.getOffset();
        fileInformationBlock.setNotesDescriptorsOffset(this.noteType, offset);
        fileInformationBlock.setNotesDescriptorsSize(this.noteType, offset2 - offset);
    }

    public void writeTxt(FileInformationBlock fileInformationBlock, HWPFOutputStream hWPFOutputStream) throws IOException {
        PlexOfCps plexOfCps = this.textPositions;
        if (plexOfCps == null || plexOfCps.length() == 0) {
            fileInformationBlock.setNotesTextPositionsOffset(this.noteType, hWPFOutputStream.getOffset());
            fileInformationBlock.setNotesTextPositionsSize(this.noteType, 0);
            return;
        }
        int offset = hWPFOutputStream.getOffset();
        hWPFOutputStream.write(this.textPositions.toByteArray());
        int offset2 = hWPFOutputStream.getOffset();
        fileInformationBlock.setNotesTextPositionsOffset(this.noteType, offset);
        fileInformationBlock.setNotesTextPositionsSize(this.noteType, offset2 - offset);
    }
}
