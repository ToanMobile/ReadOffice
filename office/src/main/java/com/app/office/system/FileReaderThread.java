package com.app.office.system;

import android.os.Handler;
import android.os.Message;
import com.app.office.constant.MainConstant;
import com.app.office.fc.doc.DOCReader;
import com.app.office.fc.doc.DOCXReader;
import com.app.office.fc.doc.TXTReader;
import com.app.office.fc.pdf.PDFReader;
import com.app.office.fc.ppt.PPTReader;
import com.app.office.fc.ppt.PPTXReader;
import com.app.office.fc.xls.XLSReader;
import com.app.office.fc.xls.XLSXReader;

public class FileReaderThread extends Thread {
    private IControl control;
    private String encoding;
    private String filePath;
    private Handler handler;

    public FileReaderThread(IControl iControl, Handler handler2, String str, String str2) {
        this.control = iControl;
        this.handler = handler2;
        this.filePath = str;
        this.encoding = str2;
    }

    public void run() {
        IReader iReader;
        Message message = new Message();
        message.what = 2;
        this.handler.handleMessage(message);
        Message message2 = new Message();
        message2.what = 3;
        try {
            String lowerCase = this.filePath.toLowerCase();
            if (!lowerCase.endsWith(MainConstant.FILE_TYPE_DOC)) {
                if (!lowerCase.endsWith(MainConstant.FILE_TYPE_DOT)) {
                    if (!lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) && !lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX)) {
                        if (!lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
                            if (lowerCase.endsWith(MainConstant.FILE_TYPE_TXT)) {
                                iReader = new TXTReader(this.control, this.filePath, this.encoding);
                            } else {
                                if (!lowerCase.endsWith(MainConstant.FILE_TYPE_XLS)) {
                                    if (!lowerCase.endsWith(MainConstant.FILE_TYPE_XLT)) {
                                        if (!lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) && !lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) && !lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM)) {
                                            if (!lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
                                                if (!lowerCase.endsWith(MainConstant.FILE_TYPE_PPT)) {
                                                    if (!lowerCase.endsWith(MainConstant.FILE_TYPE_POT)) {
                                                        if (!lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) && !lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) && !lowerCase.endsWith(MainConstant.FILE_TYPE_POTX)) {
                                                            if (!lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
                                                                if (lowerCase.endsWith("pdf")) {
                                                                    iReader = new PDFReader(this.control, this.filePath);
                                                                } else {
                                                                    iReader = new TXTReader(this.control, this.filePath, this.encoding);
                                                                }
                                                            }
                                                        }
                                                        iReader = new PPTXReader(this.control, this.filePath);
                                                    }
                                                }
                                                iReader = new PPTReader(this.control, this.filePath);
                                            }
                                        }
                                        iReader = new XLSXReader(this.control, this.filePath);
                                    }
                                }
                                iReader = new XLSReader(this.control, this.filePath);
                            }
                            Message message3 = new Message();
                            message3.obj = iReader;
                            message3.what = 4;
                            this.handler.handleMessage(message3);
                            message2.obj = iReader.getModel();
                            iReader.dispose();
                            message2.what = 0;
                            this.handler.handleMessage(message2);
                            this.control = null;
                            this.handler = null;
                            this.encoding = null;
                            this.filePath = null;
                        }
                    }
                    iReader = new DOCXReader(this.control, this.filePath);
                    Message message32 = new Message();
                    message32.obj = iReader;
                    message32.what = 4;
                    this.handler.handleMessage(message32);
                    message2.obj = iReader.getModel();
                    iReader.dispose();
                    message2.what = 0;
                    this.handler.handleMessage(message2);
                    this.control = null;
                    this.handler = null;
                    this.encoding = null;
                    this.filePath = null;
                }
            }
            iReader = new DOCReader(this.control, this.filePath);
            Message message322 = new Message();
            message322.obj = iReader;
            message322.what = 4;
            this.handler.handleMessage(message322);
            message2.obj = iReader.getModel();
            iReader.dispose();
            message2.what = 0;
        } catch (OutOfMemoryError e) {
            message2.what = 1;
            message2.obj = e;
        } catch (Exception e2) {
            message2.what = 1;
            message2.obj = e2;
        } catch (AbortReaderError e3) {
            message2.what = 1;
            message2.obj = e3;
        } catch (Throwable th) {
            this.handler.handleMessage(message2);
            this.control = null;
            this.handler = null;
            this.encoding = null;
            this.filePath = null;
            throw th;
        }
        this.handler.handleMessage(message2);
        this.control = null;
        this.handler = null;
        this.encoding = null;
        this.filePath = null;
    }
}
