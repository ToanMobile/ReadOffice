package com.app.office.common.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.app.office.constant.EventConstant;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.system.IControl;
import com.app.office.thirdpart.emf.util.EMFUtil;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureConverterMgr {
    private IControl control;
    private Map<String, Thread> convertingPictPathMap = new HashMap();
    private List<Thread> convertingThread = new ArrayList();
    private Map<String, List<Integer>> vectorgraphViews = new HashMap();
    private Map<Integer, List<String>> viewVectorgraphs = new HashMap();

    public PictureConverterMgr(IControl iControl) {
        this.control = iControl;
    }

    public void setControl(IControl iControl) {
        this.control = iControl;
    }

    public synchronized void addConvertPicture(int i, byte b, String str, String str2, int i2, int i3, boolean z) {
        String str3 = str2;
        synchronized (this) {
            this.control.actionEvent(26, true);
            if (z) {
                convertWMF_EMF(b, str, str2, i2, i3, true);
            } else {
                VectorgraphConverterThread vectorgraphConverterThread = new VectorgraphConverterThread(this, b, str, str2, i2, i3);
                this.convertingThread.add(vectorgraphConverterThread);
                this.convertingPictPathMap.put(str2, vectorgraphConverterThread);
                ArrayList arrayList = new ArrayList();
                arrayList.add(Integer.valueOf(i));
                this.vectorgraphViews.put(str2, arrayList);
                if (this.viewVectorgraphs.get(Integer.valueOf(i)) == null) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(str2);
                    this.viewVectorgraphs.put(Integer.valueOf(i), arrayList2);
                } else {
                    this.viewVectorgraphs.get(Integer.valueOf(i)).add(str2);
                }
                if (this.convertingThread.size() == 1) {
                    List<Thread> list = this.convertingThread;
                    list.get(list.size() - 1).start();
                }
            }
        }
    }

    public void convertWMF_EMF(byte b, String str, String str2, int i, int i2, boolean z) {
        Bitmap bitmap;
        if (b == 3) {
            try {
                PDFLib.getPDFLib().wmf2Jpg(str, str2, i, i2);
                bitmap = BitmapFactory.decodeFile(str2);
            } catch (OutOfMemoryError e) {
                if (this.control.getSysKit().getPictureManage().hasBitmap()) {
                    this.control.getSysKit().getPictureManage().clearBitmap();
                    convertWMF_EMF(b, str, str2, i, i2, z);
                    return;
                }
                this.control.getSysKit().getErrorKit().writerLog(e);
                remove(str2);
                return;
            } catch (Exception e2) {
                if (this.control == null || !(this.convertingPictPathMap.get(str2) == null || this.control.getView() == null)) {
                    this.control.getSysKit().getErrorKit().writerLog(e2);
                    remove(str2);
                    return;
                }
                return;
            }
        } else {
            bitmap = b == 2 ? EMFUtil.convert(str, str2, i, i2) : null;
        }
        if (this.control != null && (this.convertingPictPathMap.get(str2) == null || this.control.getView() == null)) {
            return;
        }
        if (bitmap != null) {
            this.control.getSysKit().getPictureManage().addBitmap(str2, bitmap);
            remove(str2);
            if (!z) {
                this.control.actionEvent(EventConstant.TEST_REPAINT_ID, (Object) null);
                return;
            }
            return;
        }
        remove(str2);
    }

    public synchronized void addConvertPicture(int i, String str, String str2, String str3, boolean z) {
        this.control.actionEvent(26, true);
        if (z) {
            convertPNG(str, str2, str3, true);
        } else {
            PictureConverterThread pictureConverterThread = new PictureConverterThread(this, str, str2, str3);
            this.convertingThread.add(pictureConverterThread);
            this.convertingPictPathMap.put(str2, pictureConverterThread);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
            this.vectorgraphViews.put(str2, arrayList);
            if (this.viewVectorgraphs.get(Integer.valueOf(i)) == null) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(str2);
                this.viewVectorgraphs.put(Integer.valueOf(i), arrayList2);
            } else {
                this.viewVectorgraphs.get(Integer.valueOf(i)).add(str2);
            }
            if (this.convertingThread.size() == 1) {
                List<Thread> list = this.convertingThread;
                list.get(list.size() - 1).start();
            }
        }
    }

    public void convertPNG(String str, String str2, String str3, boolean z) {
        try {
            boolean convertToPNG = PDFLib.getPDFLib().convertToPNG(str, str2, str3);
            if (this.control != null && (this.convertingPictPathMap.get(str2) == null || this.control.getView() == null)) {
                return;
            }
            if (convertToPNG) {
                Bitmap decodeStream = BitmapFactory.decodeStream(new FileInputStream(str2), (Rect) null, (BitmapFactory.Options) null);
                if (decodeStream != null) {
                    this.control.getSysKit().getPictureManage().addBitmap(str2, decodeStream);
                    remove(str2);
                    if (!z) {
                        this.control.actionEvent(EventConstant.TEST_REPAINT_ID, (Object) null);
                        return;
                    }
                    return;
                }
                remove(str2);
                return;
            }
            remove(str2);
        } catch (OutOfMemoryError e) {
            if (this.control.getSysKit().getPictureManage().hasBitmap()) {
                this.control.getSysKit().getPictureManage().clearBitmap();
                convertPNG(str, str2, str3, z);
                return;
            }
            this.control.getSysKit().getErrorKit().writerLog(e);
            remove(str2);
        } catch (Exception e2) {
            if (this.control == null || !(this.convertingPictPathMap.get(str2) == null || this.control.getView() == null)) {
                this.control.getSysKit().getErrorKit().writerLog(e2);
                remove(str2);
            }
        }
    }

    public void remove(String str) {
        synchronized (this.control) {
            Map<String, Thread> map = this.convertingPictPathMap;
            if (map != null) {
                this.convertingThread.remove(map.remove(str));
                List remove = this.vectorgraphViews.remove(str);
                ArrayList arrayList = null;
                for (int i = 0; i < remove.size(); i++) {
                    int intValue = ((Integer) remove.get(i)).intValue();
                    List list = this.viewVectorgraphs.get(Integer.valueOf(intValue));
                    list.remove(str);
                    if (list.size() == 0) {
                        this.viewVectorgraphs.remove(Integer.valueOf(intValue));
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(Integer.valueOf(intValue));
                    }
                }
                if (this.convertingThread.size() > 0) {
                    List list2 = this.viewVectorgraphs.get(Integer.valueOf(this.control.getCurrentViewIndex()));
                    if (list2 == null || list2.size() <= 0) {
                        List<Thread> list3 = this.convertingThread;
                        list3.get(list3.size() - 1).start();
                    } else {
                        this.convertingPictPathMap.get(list2.get(0)).start();
                    }
                }
                if (arrayList != null && arrayList.size() > 0) {
                    if (arrayList.contains(Integer.valueOf(this.control.getCurrentViewIndex()))) {
                        this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                    }
                    this.control.actionEvent(27, arrayList);
                }
                if (this.convertingPictPathMap.size() == 0) {
                    this.control.actionEvent(26, false);
                }
            }
        }
    }

    public boolean hasConvertingVectorgraph(int i) {
        boolean containsKey;
        synchronized (this.control) {
            containsKey = this.viewVectorgraphs.containsKey(Integer.valueOf(i));
        }
        return containsKey;
    }

    public boolean isPictureConverting(String str) {
        boolean containsKey;
        synchronized (this.control) {
            containsKey = this.vectorgraphViews.containsKey(str);
        }
        return containsKey;
    }

    public void appendViewIndex(String str, int i) {
        synchronized (this.control) {
            if (isPictureConverting(str)) {
                this.vectorgraphViews.get(str).add(Integer.valueOf(i));
                if (this.viewVectorgraphs.get(Integer.valueOf(i)) == null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(str);
                    this.viewVectorgraphs.put(Integer.valueOf(i), arrayList);
                } else {
                    this.viewVectorgraphs.get(Integer.valueOf(i)).add(str);
                }
            }
        }
    }

    public synchronized void dispose() {
        Map<String, Thread> map = this.convertingPictPathMap;
        if (map != null) {
            for (Thread interrupt : map.values()) {
                try {
                    interrupt.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.convertingPictPathMap.clear();
            this.vectorgraphViews.clear();
            this.viewVectorgraphs.clear();
        }
    }
}
