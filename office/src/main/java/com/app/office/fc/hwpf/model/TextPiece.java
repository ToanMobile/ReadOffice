package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import java.io.UnsupportedEncodingException;

@Internal
public class TextPiece extends PropertyNode<TextPiece> {
    private PieceDescriptor _pd;
    private boolean _usesUnicode;

    public TextPiece(int i, int i2, byte[] bArr, PieceDescriptor pieceDescriptor, int i3) {
        this(i, i2, bArr, 0, bArr.length, pieceDescriptor);
    }

    public TextPiece(int i, int i2, byte[] bArr, int i3, int i4, PieceDescriptor pieceDescriptor) {
        super(i, i2, buildInitSB(bArr, i3, i4, pieceDescriptor));
        this._usesUnicode = pieceDescriptor.isUnicode();
        this._pd = pieceDescriptor;
        int length = ((CharSequence) this._buf).length();
        if (i2 - i != length) {
            throw new IllegalStateException("Told we're for characters " + i + " -> " + i2 + ", but actually covers " + length + " characters!");
        } else if (i2 < i) {
            throw new IllegalStateException("Told we're of negative size! start=" + i + " end=" + i2);
        }
    }

    private static StringBuilder buildInitSB(byte[] bArr, int i, int i2, PieceDescriptor pieceDescriptor) {
        String str;
        try {
            if (pieceDescriptor.isUnicode()) {
                str = new String(bArr, i, i2, "UTF-16LE");
            } else {
                str = new String(bArr, i, i2, "Cp1252");
            }
            return new StringBuilder(str);
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("Your Java is broken! It doesn't know about basic, required character encodings!");
        }
    }

    public boolean isUnicode() {
        return this._usesUnicode;
    }

    public PieceDescriptor getPieceDescriptor() {
        return this._pd;
    }

    @Deprecated
    public StringBuffer getStringBuffer() {
        return new StringBuffer(getStringBuilder());
    }

    public StringBuilder getStringBuilder() {
        return (StringBuilder) this._buf;
    }

    public byte[] getRawBytes() {
        try {
            return ((CharSequence) this._buf).toString().getBytes(this._usesUnicode ? "UTF-16LE" : "Cp1252");
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("Your Java is broken! It doesn't know about basic, required character encodings!");
        }
    }

    @Deprecated
    public String substring(int i, int i2) {
        StringBuilder sb = (StringBuilder) this._buf;
        if (i < 0) {
            throw new StringIndexOutOfBoundsException("Can't request a substring before 0 - asked for " + i);
        } else if (i2 > sb.length()) {
            throw new StringIndexOutOfBoundsException("Index " + i2 + " out of range 0 -> " + sb.length());
        } else if (i2 >= i) {
            return sb.substring(i, i2);
        } else {
            throw new StringIndexOutOfBoundsException("Asked for text from " + i + " to " + i2 + ", which has an end before the start!");
        }
    }

    @Deprecated
    public void adjustForDelete(int i, int i2) {
        int start = getStart();
        int end = getEnd();
        int i3 = i + i2;
        if (i <= end && i3 >= start) {
            ((StringBuilder) this._buf).delete(Math.max(start, i) - start, Math.min(end, i3) - start);
        }
        super.adjustForDelete(i, i2);
    }

    @Deprecated
    public int characterLength() {
        return getEnd() - getStart();
    }

    public int bytesLength() {
        return (getEnd() - getStart()) * (this._usesUnicode ? 2 : 1);
    }

    public boolean equals(Object obj) {
        if (!limitsAreEqual(obj)) {
            return false;
        }
        TextPiece textPiece = (TextPiece) obj;
        if (!getStringBuilder().toString().equals(textPiece.getStringBuilder().toString()) || textPiece._usesUnicode != this._usesUnicode || !this._pd.equals(textPiece._pd)) {
            return false;
        }
        return true;
    }

    public int getCP() {
        return getStart();
    }

    public String toString() {
        return "TextPiece from " + getStart() + " to " + getEnd() + " (" + getPieceDescriptor() + ")";
    }
}
