package com.app.office.system;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import com.itextpdf.text.pdf.PdfBoolean;
import com.app.office.common.ICustomDialog;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.ISlideShow;
import com.app.office.common.picture.PictureKit;
import com.app.office.constant.MainConstant;
import com.app.office.fc.doc.TXTKit;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.pdf.PDFControl;
import com.app.office.pg.control.PGControl;
import com.app.office.pg.model.PGModel;
import com.app.office.simpletext.model.IDocument;
import com.app.office.ss.control.SSControl;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.system.MainControl;
import com.app.office.wp.control.WPControl;
import java.util.Objects;

public class MainControl extends AbstractControl {
    private IControl appControl;
    /* access modifiers changed from: private */
    public byte applicationType = -1;
    /* access modifiers changed from: private */
    public ICustomDialog customDialog;
    private String filePath;
    /* access modifiers changed from: private */
    public IMainFrame frame;
    private Handler handler;
    private boolean isAutoTest;
    /* access modifiers changed from: private */
    public boolean isCancel;
    /* access modifiers changed from: private */
    public boolean isDispose;
    private IOfficeToPicture officeToPicture;
    /* access modifiers changed from: private */
    public DialogInterface.OnKeyListener onKeyListener;
    /* access modifiers changed from: private */
    public ProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public IReader reader;
    private ISlideShow slideShow;
    public SysKit sysKit;
    private Toast toast;
    private AUncaughtExceptionHandler uncaught;

    public Dialog getDialog(Activity activity, int i) {
        return null;
    }

    public void layoutView(int i, int i2, int i3, int i4) {
    }

    public MainControl(IMainFrame iMainFrame) {
        this.frame = iMainFrame;
        AUncaughtExceptionHandler aUncaughtExceptionHandler = new AUncaughtExceptionHandler(this);
        this.uncaught = aUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(aUncaughtExceptionHandler);
        this.sysKit = new SysKit(this);
        init();
    }

    private void init() {
        initListener();
        Activity activity = getActivity();
        Objects.requireNonNull(activity);
        boolean z = false;
        this.toast = Toast.makeText(activity.getApplicationContext(), "", 0);
        String stringExtra = getActivity().getIntent().getStringExtra("autoTest");
        if (stringExtra != null && stringExtra.equals(PdfBoolean.TRUE)) {
            z = true;
        }
        this.isAutoTest = z;
    }

    private void initListener() {
        this.onKeyListener = new DialogInterface.OnKeyListener() {
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return MainControl.this.lambda$initListener$0$MainControl(dialogInterface, i, keyEvent);
            }
        };
        this.handler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message message) {
                if (!MainControl.this.isCancel) {
                    int i = message.what;
                    if (i == 0) {
                        post(new Runnable(message) {
                            public final /* synthetic */ Message f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                MainControl.AnonymousClass1.this.lambda$handleMessage$0$MainControl$1(this.f$1);
                            }
                        });
                    } else if (i == 1) {
                        post(new Runnable() {
                            public final void run() {
                                MainControl.AnonymousClass1.this.lambda$handleMessage$1$MainControl$1();
                            }
                        });
                    } else if (i != 2) {
                        if (i == 3) {
                            post(new Runnable() {
                                public final void run() {
                                    MainControl.AnonymousClass1.this.lambda$handleMessage$3$MainControl$1();
                                }
                            });
                        } else if (i == 4) {
                            IReader unused = MainControl.this.reader = (IReader) message.obj;
                        }
                    } else if (MainControl.this.getMainFrame().isShowProgressBar()) {
                        post(new Runnable() {
                            public final void run() {
                                MainControl.AnonymousClass1.this.lambda$handleMessage$2$MainControl$1();
                            }
                        });
                    } else if (MainControl.this.customDialog != null) {
                        MainControl.this.customDialog.showDialog((byte) 2);
                    }
                }
            }

            public /* synthetic */ void lambda$handleMessage$0$MainControl$1(Message message) {
                try {
                    if (MainControl.this.getMainFrame().isShowProgressBar()) {
                        MainControl.this.dismissProgressDialog();
                    } else if (MainControl.this.customDialog != null) {
                        MainControl.this.customDialog.dismissDialog((byte) 2);
                    }
                    MainControl.this.createApplication(message.obj);
                } catch (Exception e) {
                    MainControl.this.sysKit.getErrorKit().writerLog(e, true);
                }
            }

            public /* synthetic */ void lambda$handleMessage$1$MainControl$1() {
                MainControl.this.dismissProgressDialog();
                Toast.makeText(MainControl.this.getActivity(), "File cannot be open", 0).show();
                Activity activity = MainControl.this.getActivity();
                Objects.requireNonNull(activity);
                activity.finish();
            }

            public /* synthetic */ void lambda$handleMessage$2$MainControl$1() {
                MainControl mainControl = MainControl.this;
                ProgressDialog unused = mainControl.progressDialog = ProgressDialog.show(mainControl.getActivity(), MainControl.this.frame.getAppName(), MainControl.this.frame.getLocalString("DIALOG_LOADING"), false, false, (DialogInterface.OnCancelListener) null);
                MainControl.this.progressDialog.setOnKeyListener(MainControl.this.onKeyListener);
            }

            public /* synthetic */ void lambda$handleMessage$3$MainControl$1() {
                MainControl.this.dismissProgressDialog();
            }
        };
    }

    public /* synthetic */ boolean lambda$initListener$0$MainControl(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        dialogInterface.dismiss();
        this.isCancel = true;
        IReader iReader = this.reader;
        if (iReader != null) {
            iReader.abortReader();
            this.reader.dispose();
        }
        Activity activity = getActivity();
        Objects.requireNonNull(activity);
        activity.onBackPressed();
        return true;
    }

    public void dismissProgressDialog() {
        ProgressDialog progressDialog2 = this.progressDialog;
        if (progressDialog2 != null) {
            progressDialog2.dismiss();
            this.progressDialog = null;
        }
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages((Object) null);
        }
    }

    /* access modifiers changed from: private */
    public void createApplication(Object obj) throws Exception {
        Object viewBackground;
        if (obj != null) {
            byte b = this.applicationType;
            if (b == 0) {
                this.appControl = new WPControl(this, (IDocument) obj, this.filePath);
            } else if (b == 1) {
                this.appControl = new SSControl(this, (Workbook) obj, this.filePath);
            } else if (b == 2) {
                this.appControl = new PGControl(this, (PGModel) obj, this.filePath);
            } else if (b == 3) {
                this.appControl = new PDFControl(this, (PDFLib) obj, this.filePath);
            }
            View view = this.appControl.getView();
            if (!(view == null || (viewBackground = this.frame.getViewBackground()) == null)) {
                if (viewBackground instanceof Integer) {
                    view.setBackgroundColor(((Integer) viewBackground).intValue());
                } else if (viewBackground instanceof Drawable) {
                    view.setBackground((Drawable) viewBackground);
                }
            }
            final boolean z = this.applicationType == 3 && ((PDFLib) obj).hasPasswordSync();
            if (this.applicationType != 3) {
                this.frame.openFileFinish();
            } else if (!z) {
                this.frame.openFileFinish();
            }
            PictureKit.instance().setDrawPictrue(true);
            this.handler.post(new Runnable() {
                static final /* synthetic */ boolean $assertionsDisabled = false;

                static {
                    Class<MainControl> cls = MainControl.class;
                }

                public void run() {
                    try {
                        View view = MainControl.this.getView();
                        Object invoke = view.getClass().getMethod("isHardwareAccelerated", (Class[]) null).invoke(view, new Object[]{null});
                        if ((invoke instanceof Boolean) && ((Boolean) invoke).booleanValue()) {
                            view.getClass().getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(view, new Object[]{Integer.valueOf(view.getClass().getField("LAYER_TYPE_SOFTWARE").getInt((Object) null)), null});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MainControl.this.actionEvent(26, false);
                    MainControl.this.actionEvent(19, (Object) null);
                    if (MainControl.this.applicationType != 3) {
                        MainControl.this.frame.updateToolsbarStatus();
                    } else if (!z) {
                        MainControl.this.frame.updateToolsbarStatus();
                    }
                    MainControl.this.getView().postInvalidate();
                }
            });
            return;
        }
        throw new Exception("Document with password");
    }

    public boolean openFile(String str) {
        this.filePath = str;
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            this.applicationType = 0;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            this.applicationType = 1;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            this.applicationType = 2;
        } else if (lowerCase.endsWith("pdf")) {
            this.applicationType = 3;
        } else {
            this.applicationType = 0;
        }
        boolean isSupport = FileKit.instance().isSupport(lowerCase);
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) || !isSupport) {
            TXTKit.instance().readText(this, this.handler, str);
        } else {
            new FileReaderThread(this, this.handler, str, (String) null).start();
        }
        return true;
    }

    public void actionEvent(int i, final Object obj) {
        if (i == 23 && this.reader != null) {
            IControl iControl = this.appControl;
            if (iControl != null) {
                iControl.actionEvent(i, obj);
            }
            this.reader.dispose();
            this.reader = null;
        }
        IMainFrame iMainFrame = this.frame;
        if (iMainFrame != null && !iMainFrame.doActionEvent(i, obj)) {
            if (i == -268435456) {
                getView().postInvalidate();
            } else if (i == 0) {
                try {
                    Message message = new Message();
                    message.obj = obj;
                    this.reader.dispose();
                    message.what = 0;
                    this.handler.handleMessage(message);
                } catch (Throwable th) {
                    this.sysKit.getErrorKit().writerLog(th);
                }
            } else if (i == 26) {
                Handler handler2 = this.handler;
                if (handler2 != null) {
                    handler2.post(new Runnable() {
                        public void run() {
                            if (!MainControl.this.isDispose) {
                                MainControl.this.frame.showProgressBar(((Boolean) obj).booleanValue());
                            }
                        }
                    });
                }
            } else if (i == 536870919) {
                this.appControl.actionEvent(i, obj);
                this.frame.updateToolsbarStatus();
            } else if (i == 536870921) {
                IReader iReader = this.reader;
                if (iReader != null) {
                    iReader.abortReader();
                }
            } else if (i != 17) {
                if (i == 18) {
                    this.toast.cancel();
                } else if (i == 23) {
                    Handler handler3 = this.handler;
                    if (handler3 != null) {
                        handler3.post(new Runnable() {
                            public void run() {
                                if (!MainControl.this.isDispose) {
                                    MainControl.this.frame.showProgressBar(false);
                                }
                            }
                        });
                    }
                } else if (i == 24) {
                    Handler handler4 = this.handler;
                    if (handler4 != null) {
                        handler4.post(new Runnable() {
                            public void run() {
                                if (!MainControl.this.isDispose) {
                                    MainControl.this.frame.showProgressBar(true);
                                }
                            }
                        });
                    }
                } else if (i == 117440512) {
                    TXTKit.instance().reopenFile(this, this.handler, this.filePath, (String) obj);
                } else if (i != 117440513) {
                    IControl iControl2 = this.appControl;
                    if (iControl2 != null) {
                        iControl2.actionEvent(i, obj);
                    }
                } else {
                    String[] strArr = (String[]) obj;
                    if (strArr.length == 2) {
                        this.filePath = strArr[0];
                        this.applicationType = 0;
                        TXTKit.instance().reopenFile(this, this.handler, this.filePath, strArr[1]);
                    }
                }
            } else if (obj != null && (obj instanceof String)) {
                this.toast.setText((String) obj);
                this.toast.setGravity(17, 0, 0);
                this.toast.show();
            }
        }
    }

    public IFind getFind() {
        return this.appControl.getFind();
    }

    public Object getActionValue(int i, Object obj) {
        if (i == 1) {
            return this.filePath;
        }
        IControl iControl = this.appControl;
        if (iControl == null) {
            return null;
        }
        if (i != 536870928 && i != 805306371 && i != 536870931 && i != 1342177283 && i != 1358954506) {
            return iControl.getActionValue(i, obj);
        }
        boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
        boolean isThumbnail = this.frame.isThumbnail();
        PictureKit.instance().setDrawPictrue(true);
        if (i == 536870928) {
            this.frame.setThumbnail(true);
        }
        Object actionValue = this.appControl.getActionValue(i, obj);
        if (i == 536870928) {
            this.frame.setThumbnail(isThumbnail);
        }
        PictureKit.instance().setDrawPictrue(isDrawPictrue);
        return actionValue;
    }

    public View getView() {
        IControl iControl = this.appControl;
        if (iControl == null) {
            return null;
        }
        return iControl.getView();
    }

    public boolean isAutoTest() {
        return this.isAutoTest;
    }

    public IMainFrame getMainFrame() {
        return this.frame;
    }

    public Activity getActivity() {
        return this.frame.getActivity();
    }

    public IOfficeToPicture getOfficeToPicture() {
        return this.officeToPicture;
    }

    public ICustomDialog getCustomDialog() {
        return this.customDialog;
    }

    public ISlideShow getSlideShow() {
        return this.slideShow;
    }

    public IReader getReader() {
        return this.reader;
    }

    public byte getApplicationType() {
        return this.applicationType;
    }

    public void setOffictToPicture(IOfficeToPicture iOfficeToPicture) {
        this.officeToPicture = iOfficeToPicture;
    }

    public void setCustomDialog(ICustomDialog iCustomDialog) {
        this.customDialog = iCustomDialog;
    }

    public void setSlideShow(ISlideShow iSlideShow) {
        this.slideShow = iSlideShow;
    }

    public SysKit getSysKit() {
        return this.sysKit;
    }

    public int getCurrentViewIndex() {
        return this.appControl.getCurrentViewIndex();
    }

    public void dispose() {
        this.isDispose = true;
        IControl iControl = this.appControl;
        if (iControl != null) {
            iControl.dispose();
            this.appControl = null;
        }
        IReader iReader = this.reader;
        if (iReader != null) {
            iReader.dispose();
            this.reader = null;
        }
        IOfficeToPicture iOfficeToPicture = this.officeToPicture;
        if (iOfficeToPicture != null) {
            iOfficeToPicture.dispose();
            this.officeToPicture = null;
        }
        ProgressDialog progressDialog2 = this.progressDialog;
        if (progressDialog2 != null) {
            progressDialog2.dismiss();
            this.progressDialog = null;
        }
        if (this.customDialog != null) {
            this.customDialog = null;
        }
        if (this.slideShow != null) {
            this.slideShow = null;
        }
        this.frame = null;
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages((Object) null);
            this.handler = null;
        }
        AUncaughtExceptionHandler aUncaughtExceptionHandler = this.uncaught;
        if (aUncaughtExceptionHandler != null) {
            aUncaughtExceptionHandler.dispose();
            this.uncaught = null;
        }
        this.onKeyListener = null;
        this.toast = null;
        this.filePath = null;
        System.gc();
        SysKit sysKit2 = this.sysKit;
        if (sysKit2 != null) {
            sysKit2.dispose();
        }
    }
}
