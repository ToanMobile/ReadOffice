package com.app.office.system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.app.office.common.ICustomDialog;
import com.app.office.constant.EventConstant;
import com.app.office.fc.OldFileFormatException;
import com.app.office.fc.poifs.filesystem.OfficeXmlFileException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ErrorUtil {
    public static final int BAD_FILE = 2;
    public static final int INSUFFICIENT_MEMORY = 0;
    public static final int OLD_DOCUMENT = 3;
    public static final int PARSE_ERROR = 4;
    public static final int PASSWORD_DOCUMENT = 6;
    public static final int PASSWORD_INCORRECT = 7;
    public static final int RTF_DOCUMENT = 5;
    public static final int SD_CARD_ERROR = 8;
    public static final int SD_CARD_NOSPACELEFT = 10;
    public static final int SD_CARD_WRITEDENIED = 9;
    public static final int SYSTEM_CRASH = 1;
    private static final String VERSION = "2.0.0.4";
    private static final SimpleDateFormat sdf_24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private File logFile;
    /* access modifiers changed from: private */
    public AlertDialog message;
    /* access modifiers changed from: private */
    public SysKit sysKit;

    public ErrorUtil(SysKit sysKit2) {
        this.sysKit = sysKit2;
        if (sysKit2.getControl().getMainFrame().isWriteLog()) {
            File temporaryDirectory = sysKit2.getControl().getMainFrame().getTemporaryDirectory();
            this.logFile = temporaryDirectory;
            if (temporaryDirectory != null && temporaryDirectory.exists() && this.logFile.canWrite()) {
                File file = new File(this.logFile.getAbsolutePath() + File.separatorChar + "ASReader");
                this.logFile = file;
                if (!file.exists()) {
                    this.logFile.mkdirs();
                }
                this.logFile = new File(this.logFile.getAbsolutePath() + File.separatorChar + "errorLog.txt");
            }
        }
    }

    public void writerLog(Throwable th) {
        writerLog(th, false);
    }

    public void writerLog(Throwable th, boolean z) {
        writerLog(th, z, true);
    }

    public void writerLog(Throwable th, boolean z, boolean z2) {
        try {
            if (!(th instanceof AbortReaderError)) {
                File file = this.logFile;
                if (file == null) {
                    th = new Throwable("SD CARD ERROR");
                } else if (file != null && file.exists() && !this.logFile.canWrite()) {
                    th = new Throwable("Write Permission denied");
                } else if (this.sysKit.getControl().getMainFrame().isWriteLog() && !(th instanceof OutOfMemoryError)) {
                    FileWriter fileWriter = new FileWriter(this.logFile, true);
                    PrintWriter printWriter = new PrintWriter(fileWriter, true);
                    printWriter.println();
                    printWriter.println("--------------------------------------------------------------------------");
                    printWriter.println("Exception occurs: " + sdf_24.format(Calendar.getInstance().getTime()) + "  " + VERSION);
                    th.printStackTrace(printWriter);
                    fileWriter.close();
                }
                if (z2) {
                    processThrowable(th, z);
                }
            }
        } catch (OutOfMemoryError unused) {
            this.sysKit.getControl().getMainFrame().getActivity().onBackPressed();
        } catch (Exception unused2) {
        }
    }

    private void processThrowable(Throwable th, boolean z) {
        final IControl control = this.sysKit.getControl();
        final Activity activity = control.getMainFrame().getActivity();
        if (control != null && activity != null) {
            if (control.isAutoTest()) {
                System.exit(0);
            } else if (this.message == null) {
                final Throwable th2 = th;
                final boolean z2 = z;
                control.getActivity().getWindow().getDecorView().post(new Runnable() {
                    public void run() {
                        int i;
                        String str = "";
                        try {
                            String th = th2.toString();
                            if (th.contains("SD")) {
                                str = control.getMainFrame().getLocalString("SD_CARD");
                                i = 8;
                            } else if (th.contains("Write Permission denied")) {
                                str = control.getMainFrame().getLocalString("SD_CARD_WRITEDENIED");
                                i = 9;
                            } else if (th.contains("No space left on device")) {
                                str = control.getMainFrame().getLocalString("SD_CARD_NOSPACELEFT");
                                i = 10;
                            } else {
                                if (!(th2 instanceof OutOfMemoryError)) {
                                    if (!th.contains("OutOfMemoryError")) {
                                        if (!th.contains("no such entry") && !th.contains("Format error") && !th.contains("Unable to read entire header") && !(th2 instanceof OfficeXmlFileException) && !th.contains("The text piece table is corrupted")) {
                                            if (!th.contains("Invalid header signature")) {
                                                if (th.contains("The document is really a RTF file")) {
                                                    str = control.getMainFrame().getLocalString("DIALOG_RTF_FILE");
                                                    i = 5;
                                                } else if (th2 instanceof OldFileFormatException) {
                                                    str = control.getMainFrame().getLocalString("DIALOG_OLD_DOCUMENT");
                                                    i = 3;
                                                } else if (th.contains("Cannot process encrypted office file")) {
                                                    str = control.getMainFrame().getLocalString("DIALOG_CANNOT_ENCRYPTED_FILE");
                                                    i = 6;
                                                } else if (th.contains("Password is incorrect")) {
                                                    str = control.getMainFrame().getLocalString("DIALOG_PASSWORD_INCORRECT");
                                                    i = 7;
                                                } else if (z2) {
                                                    str = control.getMainFrame().getLocalString("DIALOG_PARSE_ERROR");
                                                    i = 4;
                                                } else {
                                                    Throwable th2 = th2;
                                                    if (!(th2 instanceof IllegalArgumentException)) {
                                                        if (!(th2 instanceof ClassCastException)) {
                                                            if (ErrorUtil.this.sysKit.isDebug()) {
                                                                PrintStream printStream = System.out;
                                                                printStream.println("on dialog cash syskit//" + th2.getLocalizedMessage());
                                                                PrintStream printStream2 = System.out;
                                                                printStream2.println("on dialog cash syskit//" + th2.getMessage());
                                                                str = control.getMainFrame().getLocalString("DIALOG_SYSTEM_CRASH");
                                                            }
                                                            i = 1;
                                                        }
                                                    }
                                                    PrintStream printStream3 = System.out;
                                                    printStream3.println("on dialog cash//" + th2.getLocalizedMessage());
                                                    PrintStream printStream4 = System.out;
                                                    printStream4.println("on dialog cash//" + th2.getMessage());
                                                    str = control.getMainFrame().getLocalString("DIALOG_SYSTEM_CRASH");
                                                    i = 1;
                                                }
                                            }
                                        }
                                        str = control.getMainFrame().getLocalString("DIALOG_FORMAT_ERROR");
                                        i = 2;
                                    }
                                }
                                str = control.getMainFrame().getLocalString("DIALOG_INSUFFICIENT_MEMORY");
                                i = 0;
                            }
                            if (str.length() > 0) {
                                control.getMainFrame().error(i);
                                control.actionEvent(EventConstant.APP_ABORTREADING, true);
                                if (!control.getMainFrame().isPopUpErrorDlg() || ErrorUtil.this.message != null) {
                                    ICustomDialog customDialog = control.getCustomDialog();
                                    if (customDialog != null) {
                                        customDialog.showDialog((byte) 3);
                                        return;
                                    }
                                    return;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setMessage(str);
                                builder.setCancelable(false);
                                builder.setTitle(control.getMainFrame().getAppName());
                                builder.setPositiveButton(control.getMainFrame().getLocalString("BUTTON_OK"), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        AlertDialog unused = ErrorUtil.this.message = null;
                                        activity.onBackPressed();
                                    }
                                });
                                AlertDialog unused = ErrorUtil.this.message = builder.create();
                                ErrorUtil.this.message.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void dispose() {
        this.sysKit = null;
    }
}
