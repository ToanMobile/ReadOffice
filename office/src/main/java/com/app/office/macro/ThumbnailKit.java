package com.app.office.macro;

import android.graphics.Bitmap;
import com.app.office.constant.MainConstant;
import com.app.office.fc.ReaderThumbnail;

public class ThumbnailKit {
    private static ThumbnailKit kit = new ThumbnailKit();

    public static ThumbnailKit instance() {
        return kit;
    }

    public Bitmap getPPTThumbnail(String str, int i, int i2) {
        try {
            String lowerCase = str.toLowerCase();
            if (lowerCase.indexOf(".") <= 0 || i <= 0 || i2 <= 0) {
                return null;
            }
            if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT)) {
                return ReaderThumbnail.instance().getThumbnailForPPT(str, i, i2);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public Bitmap getPPTXThumbnail(String str) {
        try {
            String lowerCase = str.toLowerCase();
            if (lowerCase.indexOf(".") <= 0) {
                return null;
            }
            if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
                return ReaderThumbnail.instance().getThumbnailForPPTX(str);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public Bitmap getPDFThumbnail(String str, int i) {
        try {
            String lowerCase = str.toLowerCase();
            if (lowerCase.indexOf(".") <= 0 || !lowerCase.endsWith("pdf") || i <= 0 || i > 5000) {
                return null;
            }
            return ReaderThumbnail.instance().getThumbnailForPDF(str, ((float) i) / 10000.0f);
        } catch (Exception unused) {
            return null;
        }
    }
}
