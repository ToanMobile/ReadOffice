package com.app.office.officereader;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.app.office.R;
import com.app.office.common.IOfficeToPicture;
import com.app.office.constant.EventConstant;
import com.app.office.constant.MainConstant;
import com.app.office.mychanges.interfaces.OnBookmarkCallback;
import com.app.office.mychanges.interfaces.OnDeleteCallback;
import com.app.office.mychanges.interfaces.OnRenameCallback;
import com.app.office.mychanges.utils.ExtensionFunKt;
import com.app.office.mychanges.utils.SharedPref;
import com.app.office.officereader.beans.AImageButton;
import com.app.office.officereader.beans.AImageCheckButton;
import com.app.office.officereader.beans.AToolsbar;
import com.app.office.officereader.beans.CalloutToolsbar;
import com.app.office.officereader.beans.PDFToolsbar;
import com.app.office.officereader.beans.PGToolsbar;
import com.app.office.officereader.beans.SSToolsbar;
import com.app.office.officereader.beans.WPToolsbar;
import com.app.office.officereader.database.DBService;
import com.app.office.res.ResKit;
import com.app.office.ss.sheetbar.SheetBar;
import com.app.office.system.FileKit;
import com.app.office.system.IControl;
import com.app.office.system.IMainFrame;
import com.app.office.system.MainControl;
import com.app.office.system.dialog.ColorPickerDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity implements IMainFrame {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public AppFrame appFrame;
    private int applicationType = -1;
    private final Object bg = -7829368;
    private SheetBar bottomBar;
    private CalloutToolsbar calloutBar;
    /* access modifiers changed from: private */
    public MainControl control;
    private DBService dbService;
    private AImageCheckButton eraserButton;
    String fileName = "";
    private String filePath;
    private boolean fullscreen;
    /* access modifiers changed from: private */
    public View gapView;
    Boolean isBookmarked = false;
    private boolean isDispose;
    private boolean isThumbnail;
    private boolean marked;
    private AImageButton pageDown;
    private AImageButton pageUp;
    private AImageCheckButton penButton;
    private FindToolBar searchBar;
    private AImageButton settingsButton;
    private SharedPref sharedPref;
    private String tempFilePath;
    private Toast toast;
    private AToolsbar toolsbar;
    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;
    private boolean writeLog = true;

    private void handleBackPress() {
    }

    public void changePage() {
    }

    public void changeZoom() {
    }

    public void completeLayout() {
    }

    public void error(int i) {
    }

    public Activity getActivity() {
        return this;
    }

    public byte getPageListViewMovingPosition() {
        return 0;
    }

    public String getTXTDefaultEncode() {
        return "GBK";
    }

    public int getTopBarHeight() {
        return 0;
    }

    public byte getWordDefaultView() {
        return 0;
    }

    public boolean isChangePage() {
        return true;
    }

    public boolean isDrawPageNumber() {
        return true;
    }

    public boolean isIgnoreOriginalSize() {
        return false;
    }

    public boolean isPopUpErrorDlg() {
        return true;
    }

    public boolean isShowFindDlg() {
        return true;
    }

    public boolean isShowPasswordDlg() {
        return true;
    }

    public boolean isShowProgressBar() {
        return true;
    }

    public boolean isShowTXTEncodeDlg() {
        return true;
    }

    public boolean isShowZoomingMsg() {
        return true;
    }

    public boolean isTouchZoom() {
        return true;
    }

    public boolean isZoomAfterLayoutForWord() {
        return true;
    }

    public boolean onEventMethod(View view, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, byte b) {
        return false;
    }

    public void setIgnoreOriginalSize(boolean z) {
    }

    public void updateViewImages(List<Integer> list) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(5);
        ExtensionFunKt.hideStatusBar(this);
        this.control = new MainControl(this);
        AppFrame appFrame2 = new AppFrame(getApplicationContext());
        this.appFrame = appFrame2;
        appFrame2.post(new Runnable() {
            public final void run() {
                AppActivity.this.init();
            }
        });
        this.control.setOffictToPicture(new IOfficeToPicture() {
            private Bitmap bitmap;

            public void dispose() {
            }

            public byte getModeType() {
                return 1;
            }

            public boolean isZoom() {
                return false;
            }

            public void setModeType(byte b) {
            }

            public Bitmap getBitmap(int i, int i2) {
                if (i == 0 || i2 == 0) {
                    return null;
                }
                Bitmap bitmap2 = this.bitmap;
                if (!(bitmap2 != null && bitmap2.getWidth() == i && this.bitmap.getHeight() == i2)) {
                    Bitmap bitmap3 = this.bitmap;
                    if (bitmap3 != null) {
                        bitmap3.recycle();
                    }
                    this.bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                }
                return this.bitmap;
            }

            public void callBack(Bitmap bitmap2) {
                AppActivity.this.saveBitmapToFile(bitmap2);
            }
        });
        setTheme(this.control.getSysKit().isVertical(this) ? R.style.title_background_vertical : R.style.title_background_horizontal);
        setContentView((View) this.appFrame);
        Toolbar toolbar = new Toolbar(this);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(-1, 100));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setPopupTheme(androidx.appcompat.R.style.Theme_AppCompat);
        toolbar.setVisibility(0);
        Intent intent = getIntent();
        this.fileName = intent.getStringExtra("FileName");
        this.isBookmarked = Boolean.valueOf(intent.getExtras().getBoolean("IsBookmarked", false));
        this.sharedPref = new SharedPref(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle((CharSequence) this.fileName);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_round_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AppActivity.this.lambda$onCreate$0$AppActivity(view);
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        try {
            Field declaredField = toolbar.getClass().getDeclaredField("mTitleTextView");
            declaredField.setAccessible(true);
            TextView textView = (TextView) declaredField.get(toolbar);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
            textView.requestFocus();
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setMarqueeRepeatLimit(-1);
        } catch (IllegalAccessException | NoSuchFieldException unused) {
        }
        this.appFrame.addView(toolbar);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        this.appFrame.addView(recyclerView);
        ExtensionFunKt.setRenameCompleteCallback(new OnRenameCallback() {
            public final void onRename(File file, File file2) {
                Toolbar.this.setTitle((CharSequence) file2.getName());
            }
        });
        ExtensionFunKt.setDeleteCompleteCallback(new OnDeleteCallback() {
            public final void onDelete(File file) {
                AppActivity.this.lambda$onCreate$2$AppActivity(file);
            }
        });
        ExtensionFunKt.setBookmarkCompleteCallback(new OnBookmarkCallback() {
            public final void onBookmark(File file, boolean z) {
                AppActivity.this.lambda$onCreate$3$AppActivity(file, z);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$AppActivity(View view) {
        finish();
    }

    public /* synthetic */ void lambda$onCreate$2$AppActivity(File file) {
        finish();
    }

    public /* synthetic */ void lambda$onCreate$3$AppActivity(File file, boolean z) {
        this.isBookmarked = Boolean.valueOf(z);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.sys_menu_settings) {
            try {
                ExtensionFunKt.showBottomSheet(this, this.appFrame.getRootView(), ExtensionFunKt.getDataModelFromFile(this, new File(this.filePath), false, this.isBookmarked.booleanValue()));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        } else if (menuItem.getItemId() != R.id.sys_menu_share) {
            return false;
        } else {
            ExtensionFunKt.shareDocument(this, this.filePath);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void saveBitmapToFile(Bitmap bitmap) {
        if (bitmap != null) {
            if (this.tempFilePath == null) {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    this.tempFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                }
                File file = new File(this.tempFilePath + File.separatorChar + "tempPic");
                if (!file.exists()) {
                    file.mkdir();
                }
                this.tempFilePath = file.getAbsolutePath();
            }
            File file2 = new File(this.tempFilePath + File.separatorChar + "export_image.jpg");
            try {
                if (file2.exists()) {
                    file2.delete();
                }
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                PrintStream printStream = System.out;
                printStream.println("error conveting bitmap" + e.getLocalizedMessage());
            }
        }
    }

    public void setButtonEnabled(boolean z) {
        if (this.fullscreen) {
            this.pageUp.setEnabled(z);
            this.pageDown.setEnabled(z);
            this.penButton.setEnabled(z);
            this.eraserButton.setEnabled(z);
            this.settingsButton.setEnabled(z);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        Object actionValue = this.control.getActionValue(EventConstant.PG_SLIDESHOW, (Object) null);
        if (actionValue != null && ((Boolean) actionValue).booleanValue()) {
            this.wm.removeView(this.pageUp);
            this.wm.removeView(this.pageDown);
            this.wm.removeView(this.penButton);
            this.wm.removeView(this.eraserButton);
            this.wm.removeView(this.settingsButton);
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Object actionValue = this.control.getActionValue(EventConstant.PG_SLIDESHOW, (Object) null);
        if (actionValue != null && ((Boolean) actionValue).booleanValue()) {
            this.wmParams.gravity = 8388661;
            this.wmParams.x = 5;
            this.wm.addView(this.penButton, this.wmParams);
            this.wmParams.gravity = 8388661;
            this.wmParams.x = 5;
            WindowManager.LayoutParams layoutParams = this.wmParams;
            layoutParams.y = layoutParams.height;
            this.wm.addView(this.eraserButton, this.wmParams);
            this.wmParams.gravity = 8388661;
            this.wmParams.x = 5;
            WindowManager.LayoutParams layoutParams2 = this.wmParams;
            layoutParams2.y = layoutParams2.height * 2;
            this.wm.addView(this.settingsButton, this.wmParams);
            this.wmParams.gravity = 8388627;
            this.wmParams.x = 5;
            this.wmParams.y = 0;
            this.wm.addView(this.pageUp, this.wmParams);
            this.wmParams.gravity = 8388629;
            this.wm.addView(this.pageDown, this.wmParams);
        }
    }

    public void onBackPressed() {
        if (isSearchbarActive()) {
            showSearchBar(false);
            updateToolsbarStatus();
            return;
        }
        Object actionValue = this.control.getActionValue(EventConstant.PG_SLIDESHOW, (Object) null);
        if (actionValue == null || !((Boolean) actionValue).booleanValue()) {
            if (this.control.getReader() != null) {
                this.control.getReader().abortReader();
            }
            if (this.marked != this.dbService.queryItem(MainConstant.TABLE_STAR, this.filePath)) {
                if (!this.marked) {
                    this.dbService.deleteItem(MainConstant.TABLE_STAR, this.filePath);
                } else {
                    this.dbService.insertStarFiles(MainConstant.TABLE_STAR, this.filePath);
                }
                Intent intent = new Intent();
                intent.putExtra(MainConstant.INTENT_FILED_MARK_STATUS, this.marked);
                setResult(-1, intent);
            }
            MainControl mainControl = this.control;
            if (mainControl == null || !mainControl.isAutoTest()) {
                super.onBackPressed();
            } else {
                System.exit(0);
            }
        } else {
            fullScreen(false);
            this.control.actionEvent(EventConstant.PG_SLIDESHOW_END, (Object) null);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (isSearchbarActive()) {
            this.searchBar.onConfigurationChanged(configuration);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        dispose();
        super.onDestroy();
    }

    public void showProgressBar(boolean z) {
        setProgressBarIndeterminateVisibility(z);
    }

    /* access modifiers changed from: private */
    public void init() {
        Intent intent = getIntent();
        this.dbService = new DBService(getApplicationContext());
        String stringExtra = intent.getStringExtra(MainConstant.INTENT_FILED_FILE_PATH);
        this.filePath = stringExtra;
        if (stringExtra == null) {
            this.filePath = intent.getDataString();
            int indexOf = getFilePath().indexOf(":");
            if (indexOf > 0) {
                this.filePath = this.filePath.substring(indexOf + 3);
            }
            this.filePath = Uri.decode(this.filePath);
        }
        int lastIndexOf = this.filePath.lastIndexOf(File.separator);
        if (lastIndexOf > 0) {
            setTitle(this.filePath.substring(lastIndexOf + 1));
        } else {
            setTitle(this.filePath);
        }
        if (FileKit.instance().isSupport(this.filePath)) {
            this.dbService.insertRecentFiles(MainConstant.TABLE_RECENT, this.filePath);
        }
        createView();
        this.control.openFile(this.filePath);
        initMarked();
    }

    private void createView() {
        String lowerCase = this.filePath.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            this.applicationType = 0;
            WPToolsbar wPToolsbar = new WPToolsbar(getApplicationContext(), this.control);
            this.toolsbar = wPToolsbar;
            wPToolsbar.setVisibility(8);
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            this.applicationType = 1;
            SSToolsbar sSToolsbar = new SSToolsbar(getApplicationContext(), (IControl) this.control);
            this.toolsbar = sSToolsbar;
            sSToolsbar.setVisibility(8);
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            this.applicationType = 2;
            PGToolsbar pGToolsbar = new PGToolsbar(getApplicationContext(), (IControl) this.control);
            this.toolsbar = pGToolsbar;
            pGToolsbar.setVisibility(8);
        } else if (lowerCase.endsWith("pdf")) {
            this.applicationType = 3;
            PDFToolsbar pDFToolsbar = new PDFToolsbar(getApplicationContext(), this.control);
            this.toolsbar = pDFToolsbar;
            pDFToolsbar.setVisibility(8);
        } else {
            this.applicationType = 0;
            WPToolsbar wPToolsbar2 = new WPToolsbar(getApplicationContext(), this.control);
            this.toolsbar = wPToolsbar2;
            wPToolsbar2.setVisibility(8);
        }
    }

    private boolean isSearchbarActive() {
        AppFrame appFrame2 = this.appFrame;
        if (appFrame2 != null && !this.isDispose) {
            int childCount = appFrame2.getChildCount();
            int i = 0;
            while (i < childCount) {
                View childAt = this.appFrame.getChildAt(i);
                if (!(childAt instanceof FindToolBar)) {
                    i++;
                } else if (childAt.getVisibility() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void showSearchBar(boolean z) {
        if (z) {
            if (this.searchBar == null) {
                FindToolBar findToolBar = new FindToolBar(this, this.control);
                this.searchBar = findToolBar;
                this.appFrame.addView(findToolBar, 0);
            }
            this.searchBar.setVisibility(0);
            this.toolsbar.setVisibility(8);
            return;
        }
        FindToolBar findToolBar2 = this.searchBar;
        if (findToolBar2 != null) {
            findToolBar2.setVisibility(8);
        }
        this.toolsbar.setVisibility(0);
    }

    public void showCalloutToolsBar(boolean z) {
        if (z) {
            if (this.calloutBar == null) {
                CalloutToolsbar calloutToolsbar = new CalloutToolsbar(getApplicationContext(), (IControl) this.control);
                this.calloutBar = calloutToolsbar;
                this.appFrame.addView(calloutToolsbar, 0);
            }
            this.calloutBar.setCheckState(EventConstant.APP_PEN_ID, 1);
            this.calloutBar.setCheckState(EventConstant.APP_ERASER_ID, 2);
            this.calloutBar.setVisibility(0);
            this.toolsbar.setVisibility(8);
            return;
        }
        CalloutToolsbar calloutToolsbar2 = this.calloutBar;
        if (calloutToolsbar2 != null) {
            calloutToolsbar2.setVisibility(8);
        }
        this.toolsbar.setVisibility(0);
    }

    public void setPenUnChecked() {
        if (this.fullscreen) {
            this.penButton.setState(2);
            this.penButton.postInvalidate();
            return;
        }
        this.calloutBar.setCheckState(EventConstant.APP_PEN_ID, 2);
        this.calloutBar.postInvalidate();
    }

    public void setEraserUnChecked() {
        if (this.fullscreen) {
            this.eraserButton.setState(2);
            this.eraserButton.postInvalidate();
            return;
        }
        this.calloutBar.setCheckState(EventConstant.APP_ERASER_ID, 2);
        this.calloutBar.postInvalidate();
    }

    public void setFindBackForwardState(boolean z) {
        if (isSearchbarActive()) {
            this.searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, z);
            this.searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, z);
        }
    }

    public void fileShare() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Uri.fromFile(new File(this.filePath)));
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.putExtra("android.intent.extra.STREAM", arrayList);
        intent.setType("application/octet-stream");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.sys_share_title)));
    }

    public void initMarked() {
        boolean queryItem = this.dbService.queryItem(MainConstant.TABLE_STAR, this.filePath);
        this.marked = queryItem;
        if (queryItem) {
            this.toolsbar.setCheckState(EventConstant.FILE_MARK_STAR_ID, 1);
        } else {
            this.toolsbar.setCheckState(EventConstant.FILE_MARK_STAR_ID, 2);
        }
    }

    private void markFile() {
        this.marked = !this.marked;
    }

    public Dialog onCreateDialog(int i) {
        return this.control.getDialog(this, i);
    }

    public void updateToolsbarStatus() {
        AppFrame appFrame2 = this.appFrame;
        if (appFrame2 != null && !this.isDispose) {
            int childCount = appFrame2.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.appFrame.getChildAt(i);
                if (childAt instanceof AToolsbar) {
                    ((AToolsbar) childAt).updateStatus();
                }
            }
        }
    }

    public IControl getControl() {
        return this.control;
    }

    public int getApplicationType() {
        return this.applicationType;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public boolean doActionEvent(int i, Object obj) {
        if (i == 0) {
            onBackPressed();
        } else if (i == 15) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.sys_url_wxiwei))));
        } else if (i == 20) {
            updateToolsbarStatus();
        } else if (i == 25) {
            setTitle((String) obj);
        } else if (i == 268435464) {
            markFile();
        } else if (i == 1073741828) {
            this.bottomBar.setFocusSheetButton(((Integer) obj).intValue());
        } else if (i == 536870912) {
            showSearchBar(true);
        } else if (i != 536870913) {
            switch (i) {
                case EventConstant.APP_DRAW_ID:
                    showCalloutToolsBar(true);
                    this.control.getSysKit().getCalloutManager().setDrawingMode(1);
                    this.appFrame.post(new Runnable() {
                        public final void run() {
                            AppActivity.this.lambda$doActionEvent$4$AppActivity();
                        }
                    });
                    break;
                case EventConstant.APP_BACK_ID:
                    showCalloutToolsBar(false);
                    this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                    break;
                case EventConstant.APP_PEN_ID:
                    if (!((Boolean) obj).booleanValue()) {
                        this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                        break;
                    } else {
                        this.control.getSysKit().getCalloutManager().setDrawingMode(1);
                        setEraserUnChecked();
                        this.appFrame.post(new Runnable() {
                            public final void run() {
                                AppActivity.this.lambda$doActionEvent$5$AppActivity();
                            }
                        });
                        break;
                    }
                case EventConstant.APP_ERASER_ID:
                    if (!((Boolean) obj).booleanValue()) {
                        this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                        break;
                    } else {
                        this.control.getSysKit().getCalloutManager().setDrawingMode(2);
                        setPenUnChecked();
                        break;
                    }
                case EventConstant.APP_COLOR_ID:
                    ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, this.control);
                    colorPickerDialog.show();
                    colorPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public final void onDismiss(DialogInterface dialogInterface) {
                            AppActivity.this.lambda$doActionEvent$6$AppActivity(dialogInterface);
                        }
                    });
                    setButtonEnabled(false);
                    break;
                default:
                    switch (i) {
                        case EventConstant.APP_FINDING:
                            String trim = ((String) obj).trim();
                            if (trim.length() > 0 && this.control.getFind().find(trim)) {
                                setFindBackForwardState(true);
                                break;
                            } else {
                                setFindBackForwardState(false);
                                this.toast.setText(getLocalString("DIALOG_FIND_NOT_FOUND"));
                                this.toast.show();
                                break;
                            }
                        case EventConstant.APP_FIND_BACKWARD:
                            if (this.control.getFind().findBackward()) {
                                this.searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, true);
                                break;
                            } else {
                                this.searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, false);
                                this.toast.setText(getLocalString("DIALOG_FIND_TO_BEGIN"));
                                this.toast.show();
                                break;
                            }
                        case EventConstant.APP_FIND_FORWARD:
                            try {
                                if (this.control.getFind().findForward()) {
                                    this.searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, true);
                                    break;
                                } else {
                                    this.searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, false);
                                    this.toast.setText(getLocalString("DIALOG_FIND_TO_END"));
                                    this.toast.show();
                                    break;
                                }
                            } catch (Exception e) {
                                this.control.getSysKit().getErrorKit().writerLog(e);
                                break;
                            }
                        default:
                            return false;
                    }
            }
        } else {
            fileShare();
        }
        return true;
    }

    public /* synthetic */ void lambda$doActionEvent$4$AppActivity() {
        this.control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, (Object) null);
    }

    public /* synthetic */ void lambda$doActionEvent$5$AppActivity() {
        this.control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, (Object) null);
    }

    public /* synthetic */ void lambda$doActionEvent$6$AppActivity(DialogInterface dialogInterface) {
        setButtonEnabled(true);
    }

    public void openFileFinish() {
        this.appFrame.post(new Runnable() {
            public void run() {
                View unused = AppActivity.this.gapView = new View(AppActivity.this.getApplicationContext());
                AppActivity.this.gapView.setBackgroundColor(-7829368);
                AppActivity.this.appFrame.addView(AppActivity.this.gapView, new LinearLayout.LayoutParams(-1, 1));
                AppActivity.this.appFrame.addView(AppActivity.this.control.getView(), new LinearLayout.LayoutParams(-1, -1));
            }
        });
    }

    public int getBottomBarHeight() {
        SheetBar sheetBar = this.bottomBar;
        if (sheetBar != null) {
            return sheetBar.getSheetbarHeight();
        }
        return 0;
    }

    public String getAppName() {
        return getString(R.string.sys_name);
    }

    private void initFloatButton() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.file_slideshow_left, options);
        Resources resources = getResources();
        AImageButton aImageButton = new AImageButton(this, this.control, resources.getString(R.string.pg_slideshow_pageup), -1, -1, EventConstant.APP_PAGE_UP_ID);
        this.pageUp = aImageButton;
        aImageButton.setNormalBgResID(R.drawable.file_slideshow_left);
        this.pageUp.setPushBgResID(R.drawable.file_slideshow_left_push);
        this.pageUp.setLayoutParams(new LinearLayout.LayoutParams(options.outWidth, options.outHeight));
        AImageButton aImageButton2 = new AImageButton(this, this.control, resources.getString(R.string.pg_slideshow_pagedown), -1, -1, EventConstant.APP_PAGE_DOWN_ID);
        this.pageDown = aImageButton2;
        aImageButton2.setNormalBgResID(R.drawable.file_slideshow_right);
        this.pageDown.setPushBgResID(R.drawable.file_slideshow_right_push);
        this.pageDown.setLayoutParams(new LinearLayout.LayoutParams(options.outWidth, options.outHeight));
        BitmapFactory.decodeResource(getResources(), R.drawable.file_slideshow_pen_normal, options);
        AImageCheckButton aImageCheckButton = new AImageCheckButton(this, this.control, resources.getString(R.string.app_toolsbar_pen_check), resources.getString(R.string.app_toolsbar_pen), R.drawable.file_slideshow_pen_check, R.drawable.file_slideshow_pen_normal, R.drawable.file_slideshow_pen_normal, EventConstant.APP_PEN_ID);
        this.penButton = aImageCheckButton;
        aImageCheckButton.setNormalBgResID(R.drawable.file_slideshow_pen_normal);
        this.penButton.setPushBgResID(R.drawable.file_slideshow_pen_push);
        this.penButton.setLayoutParams(new LinearLayout.LayoutParams(options.outWidth, options.outHeight));
        AImageCheckButton aImageCheckButton2 = new AImageCheckButton(this, this.control, resources.getString(R.string.app_toolsbar_eraser_check), resources.getString(R.string.app_toolsbar_eraser), R.drawable.file_slideshow_eraser_check, R.drawable.file_slideshow_eraser_normal, R.drawable.file_slideshow_eraser_normal, EventConstant.APP_ERASER_ID);
        this.eraserButton = aImageCheckButton2;
        aImageCheckButton2.setNormalBgResID(R.drawable.file_slideshow_eraser_normal);
        this.eraserButton.setPushBgResID(R.drawable.file_slideshow_eraser_push);
        this.eraserButton.setLayoutParams(new LinearLayout.LayoutParams(options.outWidth, options.outHeight));
        AImageButton aImageButton3 = new AImageButton(this, this.control, resources.getString(R.string.app_toolsbar_color), -1, -1, EventConstant.APP_COLOR_ID);
        this.settingsButton = aImageButton3;
        aImageButton3.setNormalBgResID(R.drawable.file_slideshow_settings_normal);
        this.settingsButton.setPushBgResID(R.drawable.file_slideshow_settings_push);
        this.settingsButton.setLayoutParams(new LinearLayout.LayoutParams(options.outWidth, options.outHeight));
        this.wm = (WindowManager) getApplicationContext().getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.wmParams = layoutParams;
        layoutParams.type = 2002;
        this.wmParams.format = 1;
        this.wmParams.flags = 40;
        this.wmParams.width = options.outWidth;
        this.wmParams.height = options.outHeight;
    }

    public void fullScreen(boolean z) {
        this.fullscreen = z;
        if (z) {
            if (this.wm == null || this.wmParams == null) {
                initFloatButton();
            }
            this.wmParams.gravity = 8388661;
            this.wmParams.x = 5;
            this.wm.addView(this.penButton, this.wmParams);
            this.wmParams.gravity = 8388661;
            this.wmParams.x = 5;
            WindowManager.LayoutParams layoutParams = this.wmParams;
            layoutParams.y = layoutParams.height;
            this.wm.addView(this.eraserButton, this.wmParams);
            this.wmParams.gravity = 8388661;
            this.wmParams.x = 5;
            WindowManager.LayoutParams layoutParams2 = this.wmParams;
            layoutParams2.y = layoutParams2.height * 2;
            this.wm.addView(this.settingsButton, this.wmParams);
            this.wmParams.gravity = 8388627;
            this.wmParams.x = 5;
            this.wmParams.y = 0;
            this.wm.addView(this.pageUp, this.wmParams);
            this.wmParams.gravity = 8388629;
            this.wm.addView(this.pageDown, this.wmParams);
            ((View) getWindow().findViewById(16908310).getParent()).setVisibility(8);
            this.toolsbar.setVisibility(8);
            this.gapView.setVisibility(8);
            this.penButton.setState(2);
            this.eraserButton.setState(2);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.flags |= 1024;
            getWindow().setAttributes(attributes);
            getWindow().addFlags(512);
            setRequestedOrientation(0);
            return;
        }
        this.wm.removeView(this.pageUp);
        this.wm.removeView(this.pageDown);
        this.wm.removeView(this.penButton);
        this.wm.removeView(this.eraserButton);
        this.wm.removeView(this.settingsButton);
        ((View) getWindow().findViewById(16908310).getParent()).setVisibility(0);
        this.toolsbar.setVisibility(0);
        this.gapView.setVisibility(0);
        WindowManager.LayoutParams attributes2 = getWindow().getAttributes();
        attributes2.flags &= -1025;
        getWindow().setAttributes(attributes2);
        getWindow().clearFlags(512);
        setRequestedOrientation(4);
    }

    public String getLocalString(String str) {
        return ResKit.instance().getLocalString(str);
    }

    public void setWriteLog(boolean z) {
        this.writeLog = z;
    }

    public boolean isWriteLog() {
        return this.writeLog;
    }

    public void setThumbnail(boolean z) {
        this.isThumbnail = z;
    }

    public Object getViewBackground() {
        return this.bg;
    }

    public boolean isThumbnail() {
        return this.isThumbnail;
    }

    public File getTemporaryDirectory() {
        File externalFilesDir = getExternalFilesDir((String) null);
        if (externalFilesDir != null) {
            return externalFilesDir;
        }
        return getFilesDir();
    }

    public void dispose() {
        this.isDispose = true;
        MainControl mainControl = this.control;
        if (mainControl != null) {
            mainControl.dispose();
            this.control = null;
        }
        this.toolsbar = null;
        this.searchBar = null;
        this.bottomBar = null;
        DBService dBService = this.dbService;
        if (dBService != null) {
            dBService.dispose();
            this.dbService = null;
        }
        AppFrame appFrame2 = this.appFrame;
        if (appFrame2 != null) {
            int childCount = appFrame2.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.appFrame.getChildAt(i);
                if (childAt instanceof AToolsbar) {
                    ((AToolsbar) childAt).dispose();
                }
            }
            this.appFrame = null;
        }
        if (this.wm != null) {
            this.wm = null;
            this.wmParams = null;
            this.pageUp.dispose();
            this.pageDown.dispose();
            this.penButton.dispose();
            this.eraserButton.dispose();
            this.settingsButton.dispose();
            this.pageUp = null;
            this.pageDown = null;
            this.penButton = null;
            this.eraserButton = null;
            this.settingsButton = null;
        }
    }
}
