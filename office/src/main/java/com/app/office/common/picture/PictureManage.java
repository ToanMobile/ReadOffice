package com.app.office.common.picture;

import android.graphics.Bitmap;
import com.app.office.fc.hslf.usermodel.PictureData;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.res.ResConstant;
import com.app.office.system.IControl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.InflaterInputStream;

public class PictureManage {
    private static int bitmapTotalCacheSize;
    private static Map<String, Bitmap> bitmaps = new LinkedHashMap(10);
    private final int CACHE_SIZE = 8388608;
    private IControl control;
    private PictureConverterMgr picConverterMgr;
    private Map<String, Integer> picIndexs;
    private String picTempPath;
    private List<Picture> pictures;

    public PictureManage(IControl iControl) {
        this.control = iControl;
        this.pictures = new ArrayList();
        this.picIndexs = new HashMap();
        File temporaryDirectory = iControl.getMainFrame().getTemporaryDirectory();
        if (temporaryDirectory == null) {
            iControl.getSysKit().getErrorKit().writerLog(new Throwable(ResConstant.SD_CARD_ERROR));
            return;
        }
        this.picTempPath = temporaryDirectory.getAbsolutePath() + File.separator + "tempPic";
        File file = new File(this.picTempPath);
        if (!file.exists()) {
            file.mkdir();
        }
        this.picTempPath = file.getAbsolutePath() + File.separator + System.currentTimeMillis();
        File file2 = new File(this.picTempPath);
        if (!file2.exists()) {
            file2.mkdir();
        }
    }

    public int addPicture(PackagePart packagePart) throws Exception {
        String name = packagePart.getPartName().getName();
        Integer num = this.picIndexs.get(name);
        if (num != null) {
            return num.intValue();
        }
        Picture picture = new Picture();
        picture.setTempFilePath(writeTempFile(packagePart));
        picture.setPictureType(packagePart.getPartName().getExtension());
        int size = this.pictures.size();
        this.pictures.add(picture);
        this.picIndexs.put(name, Integer.valueOf(size));
        return size;
    }

    public int addPicture(PictureData pictureData) {
        Integer num = this.picIndexs.get(pictureData.getTempFilePath());
        if (num != null) {
            return num.intValue();
        }
        Picture picture = new Picture();
        picture.setTempFilePath(pictureData.getTempFilePath());
        picture.setPictureType((byte) pictureData.getType());
        int size = this.pictures.size();
        this.pictures.add(picture);
        this.picIndexs.put(pictureData.getTempFilePath(), Integer.valueOf(size));
        return size;
    }

    public int addPicture(Picture picture) {
        if (picture.getTempFilePath() == null) {
            picture.setTempFilePath(writeTempFile(picture.getData()));
            picture.setData((byte[]) null);
        } else {
            int pictureIndex = getPictureIndex(picture.getTempFilePath());
            if (pictureIndex >= 0) {
                return pictureIndex;
            }
        }
        int size = this.pictures.size();
        this.pictures.add(picture);
        this.picIndexs.put(picture.getTempFilePath(), Integer.valueOf(size));
        return size;
    }

    public Picture getPicture(int i) {
        if (i < 0 || i >= this.pictures.size()) {
            return null;
        }
        return this.pictures.get(i);
    }

    public int getPictureIndex(String str) {
        Integer num = this.picIndexs.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public String writeTempFile(byte[] bArr) {
        try {
            return writeTempFile(bArr, 0, bArr.length);
        } catch (Exception e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
            return null;
        }
    }

    public String writeTempFile(InflaterInputStream inflaterInputStream) {
        File file = new File(this.picTempPath + File.separator + (String.valueOf(System.currentTimeMillis()) + ".tmp"));
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inflaterInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (Exception e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
        }
        return file.getAbsolutePath();
    }

    public String writeTempFile(byte[] bArr, int i, int i2) {
        File file = new File(this.picTempPath + File.separator + (String.valueOf(System.currentTimeMillis()) + ".tmp"));
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr, i, i2);
            fileOutputStream.close();
        } catch (Exception e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
        }
        return file.getAbsolutePath();
    }

    private String writeTempFile(PackagePart packagePart) {
        if (packagePart == null) {
            return null;
        }
        try {
            File file = new File(this.picTempPath + File.separator + (String.valueOf(System.currentTimeMillis()) + ".tmp"));
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = packagePart.getInputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr, 0, 8192);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    inputStream.close();
                    fileOutputStream.close();
                    return file.getAbsolutePath();
                }
            }
        } catch (Exception e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
            return null;
        }
    }

    public synchronized Bitmap getBitmap(String str) {
        return bitmaps.get(str);
    }

    public synchronized void addBitmap(String str, Bitmap bitmap) {
        if (bitmapTotalCacheSize > 8388608) {
            String str2 = (String) bitmaps.entrySet().iterator().next().getKey();
            Bitmap bitmap2 = bitmaps.get(str2);
            bitmapTotalCacheSize -= bitmap2.getWidth() * bitmap2.getHeight();
            bitmaps.remove(str2).recycle();
        }
        bitmapTotalCacheSize += bitmap.getHeight() * bitmap.getHeight();
        bitmaps.put(str, bitmap);
    }

    private void checkPictureConverterMgr() {
        PictureConverterMgr pictureConverterMgr = this.picConverterMgr;
        if (pictureConverterMgr == null) {
            this.picConverterMgr = new PictureConverterMgr(this.control);
        } else {
            pictureConverterMgr.setControl(this.control);
        }
    }

    public boolean isConverting(String str) {
        checkPictureConverterMgr();
        return this.picConverterMgr.isPictureConverting(str);
    }

    public boolean hasConvertingVectorgraph(int i) {
        checkPictureConverterMgr();
        return this.picConverterMgr.hasConvertingVectorgraph(i);
    }

    public void appendViewIndex(String str, int i) {
        checkPictureConverterMgr();
        PictureConverterMgr pictureConverterMgr = this.picConverterMgr;
        if (pictureConverterMgr != null) {
            pictureConverterMgr.appendViewIndex(str, i);
        }
    }

    public String convertVectorgraphToPng(int i, byte b, String str, int i2, int i3, boolean z) {
        String str2 = str.substring(0, str.length() - 4) + "converted.tmp";
        checkPictureConverterMgr();
        this.picConverterMgr.addConvertPicture(i, b, str, str2, i2, i3, z);
        return str2;
    }

    public String convertToPng(int i, String str, String str2, boolean z) {
        String str3 = str.substring(0, str.length() - 4) + "converted.tmp";
        checkPictureConverterMgr();
        this.picConverterMgr.addConvertPicture(i, str, str3, str2, z);
        return str3;
    }

    public synchronized void clearBitmap() {
        for (Bitmap recycle : bitmaps.values()) {
            recycle.recycle();
        }
        bitmaps.clear();
        bitmapTotalCacheSize = 0;
    }

    public boolean hasBitmap() {
        return bitmaps.size() > 0;
    }

    public String getPicTempPath() {
        return this.picTempPath;
    }

    /* access modifiers changed from: private */
    public void deleteTempFile(File file) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
            file.delete();
        }
    }

    public boolean saveBitmapToFile(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String str) {
        File file = new File(this.picTempPath + File.separatorChar + str + ".jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(compressFormat, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
            return false;
        }
    }

    public void dispose() {
        clearBitmap();
        List<Picture> list = this.pictures;
        if (list != null) {
            for (Picture dispose : list) {
                dispose.dispose();
            }
            this.pictures.clear();
        }
        this.picIndexs.clear();
        this.control = null;
        final File file = new File(this.picTempPath);
        try {
            PictureConverterMgr pictureConverterMgr = this.picConverterMgr;
            if (pictureConverterMgr != null) {
                pictureConverterMgr.dispose();
            }
            new Thread() {
                public void run() {
                    try {
                        PictureManage.this.deleteTempFile(file);
                    } catch (Exception unused) {
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
