package com.app.office.fc.ppt;

import android.graphics.Bitmap;
import com.app.office.constant.MainConstant;

public class PPTReaderThumbnail {
    private static PPTReaderThumbnail kit = new PPTReaderThumbnail();

    private Bitmap getThumbnailForPPT(String str) throws Exception {
        return null;
    }

    private Bitmap getThumbnailForPPTX(String str) throws Exception {
        return null;
    }

    public static PPTReaderThumbnail instance() {
        return kit;
    }

    public Bitmap getThumbnail(String str) {
        try {
            String lowerCase = str.toLowerCase();
            if (!lowerCase.endsWith(MainConstant.FILE_TYPE_PPT)) {
                if (!lowerCase.endsWith(MainConstant.FILE_TYPE_POT)) {
                    if (!lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) && !lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) && !lowerCase.endsWith(MainConstant.FILE_TYPE_POTX)) {
                        if (!lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
                            return null;
                        }
                    }
                    return getThumbnailForPPT(str);
                }
            }
            return getThumbnailForPPT(str);
        } catch (Exception unused) {
            return null;
        }
    }
}
