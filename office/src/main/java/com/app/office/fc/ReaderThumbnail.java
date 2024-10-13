package com.app.office.fc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.app.office.fc.fs.filesystem.CFBFileSystem;
import com.app.office.fc.fs.filesystem.Property;
import com.app.office.fc.fs.storage.LittleEndian;
import com.app.office.fc.hpsf.SummaryInformation;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.fc.ppt.PPTReader;
import com.app.office.java.awt.Dimension;
import com.app.office.pg.control.PGEditor;
import com.app.office.pg.control.Presentation;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.view.SlideDrawKit;
import com.app.office.system.IControl;
import java.io.File;
import java.io.FileInputStream;

public class ReaderThumbnail {
    private static ReaderThumbnail kit = new ReaderThumbnail();

    public static ReaderThumbnail instance() {
        return kit;
    }

    public Bitmap getThumbnailForPPT(String str, int i, int i2) throws Exception {
        Property property = new CFBFileSystem(new FileInputStream(new File(str)), true).getProperty(SummaryInformation.DEFAULT_STREAM_NAME);
        if (property != null) {
            byte[] documentRawData = property.getDocumentRawData();
            LittleEndian.getUShort(documentRawData, 0);
            LittleEndian.getUShort(documentRawData, 2);
            LittleEndian.getUInt(documentRawData, 4);
            int i3 = LittleEndian.getInt(documentRawData, 24);
            int i4 = 28;
            if (i3 < 0) {
                return null;
            }
            for (int i5 = 0; i5 < i3; i5++) {
                Bitmap readSection = readSection(documentRawData, i4, i, i2);
                if (readSection != null) {
                    return readSection;
                }
                i4 += 20;
            }
        }
        return null;
    }

    private Bitmap readSection(byte[] bArr, int i, int i2, int i3) {
        int uInt = (int) LittleEndian.getUInt(bArr, i + 16);
        LittleEndian.getUInt(bArr, uInt);
        int i4 = uInt + 4;
        int uInt2 = (int) LittleEndian.getUInt(bArr, i4);
        int i5 = i4 + 4;
        int i6 = 0;
        int i7 = -1;
        for (int i8 = 0; i8 < uInt2; i8++) {
            i7 = (int) LittleEndian.getUInt(bArr, i5);
            int i9 = i5 + 4;
            i6 = (int) LittleEndian.getUInt(bArr, i9);
            i5 = i9 + 4;
            if (i7 == 17) {
                break;
            }
        }
        if (i7 != 17) {
            return null;
        }
        int i10 = i6 + uInt;
        LittleEndian.getUInt(bArr, i10);
        int i11 = i10 + 4;
        LittleEndian.getUInt(bArr, i11);
        int i12 = i11 + 4;
        int uInt3 = (int) LittleEndian.getUInt(bArr, i12);
        int uInt4 = (int) LittleEndian.getUInt(bArr, i12 + 4);
        if (uInt3 != -1) {
            return null;
        }
        int i13 = uInt4 == 3 ? i10 + 24 : i10;
        if (i13 <= i10 || uInt4 == 3 || uInt4 != 819) {
            return null;
        }
        try {
            return BitmapFactory.decodeByteArray(bArr, i13, bArr.length - i13);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getThumbnailForPPT_Small(String str, int i, int i2) {
        try {
            PGModel pGModel = (PGModel) new PPTReader((IControl) null, str, true).getModel();
            if (pGModel != null) {
                Dimension pageSize = pGModel.getPageSize();
                PGEditor pGEditor = new PGEditor((Presentation) null);
                return SlideDrawKit.instance().getThumbnail(pGModel, pGEditor, pGModel.getSlide(0), (float) Math.min(((double) i) / pageSize.getWidth(), ((double) i2) / pageSize.getHeight()));
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public Bitmap getThumbnailForPPTX(String str) throws Exception {
        PackagePart part;
        ZipPackage zipPackage = new ZipPackage(str);
        PackageRelationship relationship = zipPackage.getRelationshipsByType(PackageRelationshipTypes.THUMBNAIL).getRelationship(0);
        if (relationship == null || (part = zipPackage.getPart(relationship.getTargetURI())) == null) {
            return null;
        }
        return BitmapFactory.decodeStream(part.getInputStream());
    }

    public Bitmap getThumbnailForPDF(String str, float f) throws Exception {
        Bitmap bitmap = null;
        try {
            PDFLib pDFLib = PDFLib.getPDFLib();
            pDFLib.openFileSync(str);
            if (pDFLib.hasPasswordSync()) {
                return null;
            }
            Rect rect = pDFLib.getAllPagesSize()[0];
            int width = (int) (((float) rect.width()) * f);
            int height = (int) (((float) rect.height()) * f);
            try {
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                try {
                    pDFLib.drawPageSync(createBitmap, 0, (float) width, (float) height, 0, 0, width, height, 1);
                    return createBitmap;
                } catch (OutOfMemoryError unused) {
                    bitmap = createBitmap;
                }
            } catch (OutOfMemoryError unused2) {
                return bitmap;
            }
        } catch (Exception unused3) {
            return null;
        }
    }
}
