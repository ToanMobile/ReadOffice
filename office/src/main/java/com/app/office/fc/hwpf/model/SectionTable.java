package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.hwpf.model.io.HWPFFileSystem;
import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Internal
public class SectionTable {
    private static final int SED_SIZE = 12;
    private static final POILogger _logger = POILogFactory.getLogger(SectionTable.class);
    protected ArrayList<SEPX> _sections = new ArrayList<>();
    protected List<TextPiece> _text;
    private TextPieceTable tpt;

    public SectionTable() {
    }

    public SectionTable(byte[] bArr, byte[] bArr2, int i, int i2, int i3, TextPieceTable textPieceTable, CPSplitCalculator cPSplitCalculator) {
        PlexOfCps plexOfCps = new PlexOfCps(bArr2, i, i2, 12);
        this.tpt = textPieceTable;
        this._text = textPieceTable.getTextPieces();
        int length = plexOfCps.length();
        for (int i4 = 0; i4 < length; i4++) {
            GenericPropertyNode property = plexOfCps.getProperty(i4);
            SectionDescriptor sectionDescriptor = new SectionDescriptor(property.getBytes(), 0);
            int fc = sectionDescriptor.getFc();
            int start = property.getStart();
            int end = property.getEnd();
            if (fc == -1) {
                this._sections.add(new SEPX(sectionDescriptor, start, end, new byte[0]));
            } else {
                int i5 = LittleEndian.getShort(bArr, fc);
                byte[] bArr3 = new byte[i5];
                System.arraycopy(bArr, fc + 2, bArr3, 0, i5);
                this._sections.add(new SEPX(sectionDescriptor, start, end, bArr3));
            }
        }
        int mainDocumentEnd = cPSplitCalculator.getMainDocumentEnd();
        boolean z = false;
        boolean z2 = false;
        for (int i6 = 0; i6 < this._sections.size(); i6++) {
            SEPX sepx = this._sections.get(i6);
            if (sepx.getEnd() == mainDocumentEnd) {
                z = true;
            } else if (sepx.getEnd() == mainDocumentEnd || sepx.getEnd() == mainDocumentEnd - 1) {
                z2 = true;
            }
        }
        if (!z && z2) {
            _logger.log(POILogger.WARN, (Object) "Your document seemed to be mostly unicode, but the section definition was in bytes! Trying anyway, but things may well go wrong!");
            for (int i7 = 0; i7 < this._sections.size(); i7++) {
                SEPX sepx2 = this._sections.get(i7);
                GenericPropertyNode property2 = plexOfCps.getProperty(i7);
                int start2 = property2.getStart();
                int end2 = property2.getEnd();
                sepx2.setStart(start2);
                sepx2.setEnd(end2);
            }
        }
        Collections.sort(this._sections, PropertyNode.StartComparator.instance);
    }

    public void adjustForInsert(int i, int i2) {
        int size = this._sections.size();
        SEPX sepx = this._sections.get(i);
        sepx.setEnd(sepx.getEnd() + i2);
        while (true) {
            i++;
            if (i < size) {
                SEPX sepx2 = this._sections.get(i);
                sepx2.setStart(sepx2.getStart() + i2);
                sepx2.setEnd(sepx2.getEnd() + i2);
            } else {
                return;
            }
        }
    }

    public ArrayList<SEPX> getSections() {
        return this._sections;
    }

    @Deprecated
    public void writeTo(HWPFFileSystem hWPFFileSystem, int i) throws IOException {
        writeTo(hWPFFileSystem.getStream("WordDocument"), hWPFFileSystem.getStream("1Table"));
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream, HWPFOutputStream hWPFOutputStream2) throws IOException {
        int offset = hWPFOutputStream.getOffset();
        int size = this._sections.size();
        PlexOfCps plexOfCps = new PlexOfCps(12);
        for (int i = 0; i < size; i++) {
            SEPX sepx = this._sections.get(i);
            byte[] grpprl = sepx.getGrpprl();
            byte[] bArr = new byte[2];
            LittleEndian.putShort(bArr, (short) grpprl.length);
            hWPFOutputStream.write(bArr);
            hWPFOutputStream.write(grpprl);
            SectionDescriptor sectionDescriptor = sepx.getSectionDescriptor();
            sectionDescriptor.setFc(offset);
            plexOfCps.addProperty(new GenericPropertyNode(sepx.getStart(), sepx.getEnd(), sectionDescriptor.toByteArray()));
            offset = hWPFOutputStream.getOffset();
        }
        hWPFOutputStream2.write(plexOfCps.toByteArray());
    }
}
