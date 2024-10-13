package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Internal
public class TextPieceTable implements CharIndexTranslator {
    int _cpMin;
    protected ArrayList<TextPiece> _textPieces = new ArrayList<>();
    protected ArrayList<TextPiece> _textPiecesFCOrder = new ArrayList<>();

    public TextPieceTable() {
    }

    public TextPieceTable(byte[] bArr, byte[] bArr2, int i, int i2, int i3) {
        PlexOfCps plexOfCps = new PlexOfCps(bArr2, i, i2, PieceDescriptor.getSizeInBytes());
        int length = plexOfCps.length();
        PieceDescriptor[] pieceDescriptorArr = new PieceDescriptor[length];
        for (int i4 = 0; i4 < length; i4++) {
            pieceDescriptorArr[i4] = new PieceDescriptor(plexOfCps.getProperty(i4).getBytes(), 0);
        }
        this._cpMin = pieceDescriptorArr[0].getFilePosition() - i3;
        for (int i5 = 0; i5 < length; i5++) {
            int filePosition = pieceDescriptorArr[i5].getFilePosition() - i3;
            if (filePosition < this._cpMin) {
                this._cpMin = filePosition;
            }
        }
        for (int i6 = 0; i6 < length; i6++) {
            int filePosition2 = pieceDescriptorArr[i6].getFilePosition();
            GenericPropertyNode property = plexOfCps.getProperty(i6);
            int start = property.getStart();
            int end = property.getEnd();
            this._textPieces.add(new TextPiece(start, end, bArr, filePosition2, (end - start) * (pieceDescriptorArr[i6].isUnicode() ? 2 : 1), pieceDescriptorArr[i6]));
        }
        Collections.sort(this._textPieces);
        ArrayList<TextPiece> arrayList = new ArrayList<>(this._textPieces);
        this._textPiecesFCOrder = arrayList;
        Collections.sort(arrayList, new FCComparator());
    }

    public void add(TextPiece textPiece) {
        this._textPieces.add(textPiece);
        this._textPiecesFCOrder.add(textPiece);
        Collections.sort(this._textPieces);
        Collections.sort(this._textPiecesFCOrder, new FCComparator());
    }

    public int adjustForInsert(int i, int i2) {
        int size = this._textPieces.size();
        TextPiece textPiece = this._textPieces.get(i);
        textPiece.setEnd(textPiece.getEnd() + i2);
        while (true) {
            i++;
            if (i >= size) {
                return i2;
            }
            TextPiece textPiece2 = this._textPieces.get(i);
            textPiece2.setStart(textPiece2.getStart() + i2);
            textPiece2.setEnd(textPiece2.getEnd() + i2);
        }
    }

    public boolean equals(Object obj) {
        TextPieceTable textPieceTable = (TextPieceTable) obj;
        int size = textPieceTable._textPieces.size();
        if (size != this._textPieces.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!textPieceTable._textPieces.get(i).equals(this._textPieces.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int getByteIndex(int i) {
        Iterator<TextPiece> it = this._textPieces.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            TextPiece next = it.next();
            int i3 = 2;
            if (i >= next.getEnd()) {
                int filePosition = next.getPieceDescriptor().getFilePosition();
                int end = next.getEnd() - next.getStart();
                if (!next.isUnicode()) {
                    i3 = 1;
                }
                i2 = filePosition + (end * i3);
                if (i == next.getEnd()) {
                    return i2;
                }
            } else if (i < next.getEnd()) {
                int start = i - next.getStart();
                int filePosition2 = next.getPieceDescriptor().getFilePosition();
                if (!next.isUnicode()) {
                    i3 = 1;
                }
                return filePosition2 + (start * i3);
            }
        }
        return i2;
    }

    public int getCharIndex(int i) {
        return getCharIndex(i, 0);
    }

    public int getCharIndex(int i, int i2) {
        int lookIndexForward = lookIndexForward(i);
        Iterator<TextPiece> it = this._textPieces.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            TextPiece next = it.next();
            int filePosition = next.getPieceDescriptor().getFilePosition();
            int bytesLength = next.bytesLength();
            int i4 = filePosition + bytesLength;
            if (lookIndexForward >= filePosition && lookIndexForward <= i4) {
                bytesLength = (lookIndexForward <= filePosition || lookIndexForward >= i4) ? bytesLength - (i4 - lookIndexForward) : lookIndexForward - filePosition;
            }
            if (next.isUnicode()) {
                bytesLength /= 2;
            }
            i3 += bytesLength;
            if (lookIndexForward >= filePosition && lookIndexForward <= i4 && i3 >= i2) {
                break;
            }
        }
        return i3;
    }

    public int getCpMin() {
        return this._cpMin;
    }

    public StringBuilder getText() {
        System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        Iterator<TextPiece> it = this._textPieces.iterator();
        while (it.hasNext()) {
            TextPiece next = it.next();
            String sb2 = next.getStringBuilder().toString();
            int length = sb2.length();
            next.getEnd();
            next.getStart();
            sb.replace(next.getStart(), next.getStart() + length, sb2);
        }
        return sb;
    }

    public List<TextPiece> getTextPieces() {
        return this._textPieces;
    }

    public int hashCode() {
        return this._textPieces.size();
    }

    public boolean isIndexInTable(int i) {
        Iterator<TextPiece> it = this._textPiecesFCOrder.iterator();
        while (it.hasNext()) {
            TextPiece next = it.next();
            int filePosition = next.getPieceDescriptor().getFilePosition();
            if (i <= next.bytesLength() + filePosition) {
                if (filePosition > i) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isIndexInTable(int i, int i2) {
        Iterator<TextPiece> it = this._textPiecesFCOrder.iterator();
        while (it.hasNext()) {
            TextPiece next = it.next();
            int filePosition = next.getPieceDescriptor().getFilePosition();
            if (i < next.bytesLength() + filePosition) {
                if (Math.max(i, filePosition) >= Math.min(i2, filePosition + next.bytesLength())) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public int lookIndexBackward(int i) {
        Iterator<TextPiece> it = this._textPiecesFCOrder.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            TextPiece next = it.next();
            int filePosition = next.getPieceDescriptor().getFilePosition();
            if (i <= next.bytesLength() + filePosition) {
                return filePosition > i ? i2 : i;
            }
            i2 = next.bytesLength() + filePosition;
        }
        return i;
    }

    public int lookIndexForward(int i) {
        Iterator<TextPiece> it = this._textPiecesFCOrder.iterator();
        while (it.hasNext()) {
            TextPiece next = it.next();
            int filePosition = next.getPieceDescriptor().getFilePosition();
            if (i < next.bytesLength() + filePosition) {
                return filePosition > i ? filePosition : i;
            }
        }
        return i;
    }

    public byte[] writeTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        PlexOfCps plexOfCps = new PlexOfCps(PieceDescriptor.getSizeInBytes());
        int size = this._textPieces.size();
        for (int i = 0; i < size; i++) {
            TextPiece textPiece = this._textPieces.get(i);
            PieceDescriptor pieceDescriptor = textPiece.getPieceDescriptor();
            int offset = hWPFOutputStream.getOffset() % 512;
            if (offset != 0) {
                hWPFOutputStream.write(new byte[(512 - offset)]);
            }
            pieceDescriptor.setFilePosition(hWPFOutputStream.getOffset());
            hWPFOutputStream.write(textPiece.getRawBytes());
            plexOfCps.addProperty(new GenericPropertyNode(textPiece.getStart(), textPiece.getEnd(), pieceDescriptor.toByteArray()));
        }
        return plexOfCps.toByteArray();
    }

    private static class FCComparator implements Comparator<TextPiece> {
        private FCComparator() {
        }

        public int compare(TextPiece textPiece, TextPiece textPiece2) {
            if (textPiece.getPieceDescriptor().fc > textPiece2.getPieceDescriptor().fc) {
                return 1;
            }
            return textPiece.getPieceDescriptor().fc < textPiece2.getPieceDescriptor().fc ? -1 : 0;
        }
    }
}
