package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import java.io.IOException;

@Internal
public class SinglentonTextPiece extends TextPiece {
    public int getCP() {
        return 0;
    }

    public int getStart() {
        return 0;
    }

    public SinglentonTextPiece(StringBuilder sb) throws IOException {
        super(0, sb.length(), sb.toString().getBytes("UTF-16LE"), new PieceDescriptor(new byte[8], 0), 0);
    }

    public int bytesLength() {
        return getStringBuilder().length() * 2;
    }

    public int characterLength() {
        return getStringBuilder().length();
    }

    public int getEnd() {
        return characterLength();
    }

    public String toString() {
        return "SinglentonTextPiece (" + characterLength() + " chars)";
    }
}
