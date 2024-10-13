package com.app.office.fc.doc;

import android.os.Handler;
import com.app.office.common.ICustomDialog;
import com.app.office.fc.fs.storage.LittleEndian;
import com.app.office.system.FileReaderThread;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.thirdpart.mozilla.intl.chardet.CharsetDetector;
import com.app.office.wp.dialog.TXTEncodingDialog;
import java.io.FileInputStream;
import java.util.Vector;

public class TXTKit {
    private static TXTKit kit = new TXTKit();

    public static TXTKit instance() {
        return kit;
    }

    public void readText(final IControl iControl, final Handler handler, final String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[16];
            fileInputStream.read(bArr);
            long j = LittleEndian.getLong(bArr, 0);
            if (j == -2226271756974174256L || j == 1688935826934608L) {
                fileInputStream.close();
                iControl.getSysKit().getErrorKit().writerLog(new Exception("Format error"), true);
            } else if ((j & 72057594037927935L) == 13001919450861605L) {
                fileInputStream.close();
                iControl.getSysKit().getErrorKit().writerLog(new Exception("Format error"), true);
            } else {
                fileInputStream.close();
                String detect = iControl.isAutoTest() ? "GBK" : CharsetDetector.detect(str);
                if (detect != null) {
                    new FileReaderThread(iControl, handler, str, detect).start();
                } else if (iControl.getMainFrame().isShowTXTEncodeDlg()) {
                    Vector vector = new Vector();
                    vector.add(str);
                    new TXTEncodingDialog(iControl, iControl.getMainFrame().getActivity(), new IDialogAction() {
                        public void dispose() {
                        }

                        public IControl getControl() {
                            return iControl;
                        }

                        public void doAction(int i, Vector<Object> vector) {
                            if (TXTEncodingDialog.BACK_PRESSED.equals(vector.get(0))) {
                                iControl.getMainFrame().getActivity().onBackPressed();
                            } else {
                                new FileReaderThread(iControl, handler, str, vector.get(0).toString()).start();
                            }
                        }
                    }, vector, 1).show();
                } else {
                    String tXTDefaultEncode = iControl.getMainFrame().getTXTDefaultEncode();
                    if (tXTDefaultEncode == null) {
                        ICustomDialog customDialog = iControl.getCustomDialog();
                        if (customDialog != null) {
                            customDialog.showDialog((byte) 1);
                        } else {
                            new FileReaderThread(iControl, handler, str, "UTF-8").start();
                        }
                    } else {
                        new FileReaderThread(iControl, handler, str, tXTDefaultEncode).start();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reopenFile(IControl iControl, Handler handler, String str, String str2) {
        new FileReaderThread(iControl, handler, str, str2).start();
    }
}
