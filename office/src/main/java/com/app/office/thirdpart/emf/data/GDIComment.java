package com.app.office.thirdpart.emf.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class GDIComment extends EMFTag {
    private String comment;
    private Bitmap image;
    private int type;

    public void render(EMFRenderer eMFRenderer) {
    }

    public GDIComment() {
        super(70, 1);
        this.comment = "";
    }

    public GDIComment(String str) {
        this();
        this.comment = str;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        GDIComment gDIComment = new GDIComment();
        int readDWORD = eMFInputStream.readDWORD();
        int readDWORD2 = eMFInputStream.readDWORD();
        gDIComment.type = readDWORD2;
        if (readDWORD2 == 726027589) {
            eMFInputStream.readByte(readDWORD - 4);
            int i3 = readDWORD % 4;
            if (i3 != 0) {
                eMFInputStream.readBYTE(4 - i3);
            }
        } else if (readDWORD2 == 2) {
            eMFInputStream.readRECTL();
            int readDWORD3 = eMFInputStream.readDWORD();
            if (readDWORD3 > 0) {
                gDIComment.comment = new String(eMFInputStream.readByte(readDWORD3));
            }
        } else if (readDWORD2 != 3) {
            if (readDWORD2 == 1073741828) {
                eMFInputStream.readRECTL();
                eMFInputStream.readDWORD();
                int i4 = (readDWORD - 4) - 8;
                gDIComment.comment = new String(eMFInputStream.readBYTE(i4));
                int i5 = i4 % 4;
                if (i5 != 0) {
                    eMFInputStream.readBYTE(4 - i5);
                }
            } else if (readDWORD2 == -2147483647) {
                eMFInputStream.readDWORD();
                eMFInputStream.readDWORD();
                eMFInputStream.readDWORD();
                gDIComment.image = BitmapFactory.decodeStream(new ByteArrayInputStream(eMFInputStream.readByte(eMFInputStream.readDWORD())));
                return this;
            } else {
                int i6 = readDWORD - 4;
                if (i6 > 0) {
                    gDIComment.comment = new String(eMFInputStream.readBYTE(i6));
                    int i7 = i6 % 4;
                    if (i7 != 0) {
                        eMFInputStream.readBYTE(4 - i7);
                    }
                } else {
                    this.comment = "";
                }
            }
        }
        return gDIComment;
    }

    public String toString() {
        return super.toString() + "\n  length: " + this.comment.length();
    }
}
