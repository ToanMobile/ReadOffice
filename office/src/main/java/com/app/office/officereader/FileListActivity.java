package com.app.office.officereader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.actions.SearchIntents;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.constant.MainConstant;
import com.app.office.officereader.beans.AToolsbar;
import com.app.office.officereader.database.DBService;
import com.app.office.officereader.filelist.FileDialogAction;
import com.app.office.officereader.filelist.FileItem;
import com.app.office.officereader.filelist.FileItemAdapter;
import com.app.office.officereader.filelist.FileItemView;
import com.app.office.officereader.filelist.FileRenameDialog;
import com.app.office.officereader.filelist.FileSort;
import com.app.office.officereader.filelist.FileSortType;
import com.app.office.officereader.filelist.FileToolsbar;
import com.app.office.officereader.filelist.NewFolderDialog;
import com.app.office.officereader.filelist.SortDialog;
import com.app.office.officereader.search.ISearchResult;
import com.app.office.officereader.search.Search;
import com.app.office.system.FileKit;
import com.app.office.system.IControl;
import com.app.office.system.dialog.MessageDialog;
import com.app.office.system.dialog.QuestionDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class FileListActivity extends Activity implements ISearchResult {
    public static final byte LIST_TYPE_EXPLORE = 0;
    public static final byte LIST_TYPE_MARKED = 2;
    public static final byte LIST_TYPE_RECENTLY = 1;
    public static final byte LIST_TYPE_SEARCH = 3;
    private boolean bCopy;
    private boolean bCut;
    private IControl control;
    private File currentDirectory;
    /* access modifiers changed from: private */
    public int currentPos;
    private DBService dbService;
    private FileDialogAction dialogAction;
    /* access modifiers changed from: private */
    public List<FileItem> directoryEntries;
    /* access modifiers changed from: private */
    public TextView emptyView;
    /* access modifiers changed from: private */
    public FileItemAdapter fileAdapter;
    /* access modifiers changed from: private */
    public FileFrame fileFrame;
    private FileSortType fileSortType;
    /* access modifiers changed from: private */
    public byte listType;
    /* access modifiers changed from: private */
    public ListView listView;
    private int mHeight;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    /* access modifiers changed from: private */
    public boolean onLongPress;
    private File sdcardPath;
    private Search search;
    private List<FileItem> selectFileItem;
    /* access modifiers changed from: private */
    public Toast toast;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(5);
        this.toast = Toast.makeText(getApplicationContext(), "", 0);
        this.selectFileItem = new ArrayList();
        this.directoryEntries = new ArrayList();
        FileListControl fileListControl = new FileListControl(this);
        this.control = fileListControl;
        File sDPath = fileListControl.getSysKit().getSDPath();
        this.sdcardPath = sDPath;
        if (sDPath == null) {
            this.sdcardPath = new File("/mnt/sdcard");
        }
        this.mHeight = getResources().getDisplayMetrics().heightPixels;
        FileFrame fileFrame2 = new FileFrame(getApplicationContext());
        this.fileFrame = fileFrame2;
        fileFrame2.post(new Runnable() {
            public void run() {
                FileListActivity.this.initListener();
                FileListActivity.this.init();
            }
        });
        setTheme(this.control.getSysKit().isVertical(this) ? R.style.title_background_vertical : R.style.title_background_horizontal);
        setContentView(this.fileFrame);
        this.dialogAction = new FileDialogAction(this.control);
        this.fileSortType = new FileSortType();
        this.dbService = new DBService(getApplicationContext());
    }

    public void onBackPressed() {
        Search search2 = this.search;
        if (search2 != null) {
            search2.stopSearch();
        }
        byte b = this.listType;
        if (b == 3) {
            File file = this.currentDirectory;
            if (file == null) {
                super.onBackPressed();
            } else {
                createFileList(file);
            }
        } else if (b == 1 || b == 2) {
            super.onBackPressed();
        } else {
            File file2 = this.currentDirectory;
            if (file2 == null || file2.equals(this.sdcardPath)) {
                super.onBackPressed();
            } else {
                browseTo(this.currentDirectory.getParentFile());
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = getResources().getDisplayMetrics().heightPixels;
        this.mHeight = i;
        this.mHeight = i - getWindow().findViewById(16908290).getTop();
        this.listView.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mHeight - this.listView.getTop()));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        dispose();
    }

    /* access modifiers changed from: private */
    public void initListener() {
        this.onItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (FileListActivity.this.onLongPress) {
                    boolean unused = FileListActivity.this.onLongPress = false;
                    FileListActivity.this.toast.cancel();
                    return;
                }
                int unused2 = FileListActivity.this.currentPos = i;
                FileListActivity fileListActivity = FileListActivity.this;
                fileListActivity.browseTo(((FileItem) fileListActivity.directoryEntries.get(i)).getFile());
            }
        };
        this.onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                FileItem fileItem;
                if (!(FileListActivity.this.listType == 0 || (fileItem = (FileItem) FileListActivity.this.directoryEntries.get(i)) == null)) {
                    boolean unused = FileListActivity.this.onLongPress = true;
                    FileListActivity.this.toast.setText(fileItem.getFile().getAbsolutePath().substring(1));
                    FileListActivity.this.toast.setGravity(49, 0, FileListActivity.this.listView.getTop());
                    FileListActivity.this.toast.show();
                }
                return false;
            }
        };
    }

    /* access modifiers changed from: private */
    public void init() {
        this.mHeight -= getWindow().findViewById(16908290).getTop();
        this.fileAdapter = new FileItemAdapter(getApplicationContext(), this.control);
        TextView textView = new TextView(getApplicationContext());
        this.emptyView = textView;
        textView.setText(R.string.file_message_empty_directory);
        this.emptyView.setGravity(17);
        this.emptyView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        FileToolsbar fileToolsbar = new FileToolsbar(getApplicationContext(), this.control);
        this.fileFrame.addView(fileToolsbar);
        ListView listView2 = new ListView(getApplicationContext());
        this.listView = listView2;
        listView2.setOnItemClickListener(this.onItemClickListener);
        this.listView.setOnItemLongClickListener(this.onItemLongClickListener);
        this.fileFrame.addView(this.listView, new LinearLayout.LayoutParams(-1, this.mHeight - fileToolsbar.getButtonHeight()));
        initSortType();
        createFileList(this.sdcardPath);
    }

    private void createFileList(File file) {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(MainConstant.INTENT_FILED_FILE_LIST_TYPE);
        if (MainConstant.INTENT_FILED_MARK_FILES.equals(stringExtra)) {
            this.listType = 2;
            this.currentDirectory = this.sdcardPath;
            ArrayList arrayList = new ArrayList();
            this.dbService.get(MainConstant.TABLE_STAR, arrayList);
            listFiles((File[]) arrayList.toArray(new File[arrayList.size()]));
            updateToolsbarStatus();
        } else if (MainConstant.INTENT_FILED_RECENT_FILES.equals(stringExtra)) {
            this.listType = 1;
            this.currentDirectory = this.sdcardPath;
            ArrayList arrayList2 = new ArrayList();
            this.dbService.get(MainConstant.TABLE_RECENT, arrayList2);
            int size = arrayList2.size();
            File[] fileArr = new File[size];
            int i = size - 1;
            for (int i2 = i; i2 >= 0; i2--) {
                fileArr[i - i2] = (File) arrayList2.get(i2);
            }
            listFiles(fileArr);
            updateToolsbarStatus();
        } else if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            this.listType = 3;
            createSearchFileList(intent);
        } else {
            this.listType = 0;
            browseTo(file);
        }
    }

    public void createSearchFileList(Intent intent) {
        String trim = intent.getStringExtra(SearchIntents.EXTRA_QUERY).trim();
        if (trim.length() > 0) {
            this.listType = 3;
            if (this.search == null) {
                this.search = new Search(this.control, this);
            }
            Search search2 = this.search;
            File file = this.currentDirectory;
            if (file == null) {
                file = this.sdcardPath;
            }
            search2.doSearch(file, trim, (byte) 0);
            this.directoryEntries.clear();
            this.selectFileItem.clear();
            this.fileAdapter.setListItems(this.directoryEntries);
            this.listView.setAdapter(this.fileAdapter);
            setTitle(getResources().getString(R.string.sys_button_search));
            setProgressBarIndeterminateVisibility(true);
            updateToolsbarStatus();
        }
    }

    public void onResult(final File file) {
        this.fileFrame.post(new Runnable() {
            public void run() {
                FileListActivity.this.directoryEntries.add(new FileItem(file, FileListActivity.this.fileAdapter.getFileIconType(file.getName()), 0));
                FileListActivity.this.fileAdapter.notifyDataSetChanged();
                FileListActivity.this.updateToolsbarStatus();
            }
        });
    }

    public void searchFinish() {
        FileFrame fileFrame2 = this.fileFrame;
        if (fileFrame2 != null) {
            fileFrame2.post(new Runnable() {
                public void run() {
                    FileListActivity.this.setProgressBarIndeterminateVisibility(false);
                    if (FileListActivity.this.directoryEntries.size() == 0) {
                        if (FileListActivity.this.emptyView.getParent() == null) {
                            FileListActivity.this.fileFrame.addView(FileListActivity.this.emptyView);
                        }
                        if (FileListActivity.this.listType == 3) {
                            FileListActivity.this.emptyView.setText(R.string.sys_no_match);
                        } else {
                            FileListActivity.this.emptyView.setText(R.string.file_message_empty_directory);
                        }
                        FileListActivity.this.listView.setEmptyView(FileListActivity.this.emptyView);
                    }
                }
            });
        }
    }

    public void actionEvent(int i, Object obj) {
        Vector vector;
        Search search2 = this.search;
        if (search2 != null) {
            search2.stopSearch();
        }
        if (i == 5) {
            onSearchRequested();
        } else if (i == 15) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.sys_url_wxiwei))));
        } else if (i == 20) {
            updateToolsbarStatus();
        } else if (i != 17) {
            if (i != 18) {
                switch (i) {
                    case EventConstant.FILE_CREATE_FOLDER_ID:
                        Vector vector2 = new Vector();
                        vector2.add(this.currentDirectory.getAbsolutePath());
                        new NewFolderDialog(this.control, this, this.dialogAction, vector2, 2, R.string.file_toolsbar_create_folder).show();
                        return;
                    case EventConstant.FILE_RENAME_ID:
                        fileRename();
                        return;
                    case EventConstant.FILE_COPY_ID:
                        this.bCopy = true;
                        fileCopy();
                        clearSelectFileItem();
                        return;
                    case EventConstant.FILE_CUT_ID:
                        this.bCut = true;
                        fileCopy();
                        clearSelectFileItem();
                        return;
                    case EventConstant.FILE_PASTE_ID:
                        if (this.currentDirectory.canWrite()) {
                            filePaste();
                            this.bCopy = false;
                            this.bCut = false;
                            updateToolsbarStatus();
                            return;
                        }
                        new MessageDialog(this.control, this, this.dialogAction, (Vector<Object>) null, 0, R.string.dialog_error_title, getResources().getText(R.string.dialog_move_file_error).toString()).show();
                        return;
                    case EventConstant.FILE_DELETE_ID:
                        fileDelete();
                        return;
                    case EventConstant.FILE_SHARE_ID:
                        fileShare();
                        return;
                    case EventConstant.FILE_SORT_ID:
                        Vector vector3 = new Vector();
                        vector3.add(Integer.valueOf(getSortType()));
                        vector3.add(Integer.valueOf(getAscending()));
                        new SortDialog(this.control, this, this.dialogAction, vector3, 6, R.string.file_toolsbar_sort, R.array.file_sort_items).show();
                        return;
                    case EventConstant.FILE_MARK_STAR_ID:
                        fileMarkStar();
                        return;
                    default:
                        switch (i) {
                            case EventConstant.FILE_REFRESH_ID:
                                if (obj != null && ((Boolean) obj).booleanValue()) {
                                    clearSelectFileItem();
                                }
                                browseTo(this.currentDirectory);
                                return;
                            case EventConstant.FILE_SORT_TYPE_ID:
                                if (obj != null && (vector = (Vector) obj) != null) {
                                    setSortType(((Integer) vector.get(0)).intValue(), ((Integer) vector.get(1)).intValue());
                                    Collections.sort(this.directoryEntries, FileSort.instance());
                                    this.fileAdapter.setListItems(this.directoryEntries);
                                    this.listView.setAdapter(this.fileAdapter);
                                    return;
                                }
                                return;
                            case EventConstant.FILE_CREATE_FOLDER_FAILED_ID:
                                new MessageDialog(this.control, this, this.dialogAction, (Vector<Object>) null, 0, R.string.dialog_create_folder_error, getResources().getText(R.string.file_toolsbar_create_folder).toString()).show();
                                return;
                            default:
                                return;
                        }
                }
            } else {
                this.toast.cancel();
            }
        } else if (obj != null && (obj instanceof String)) {
            this.toast.setText((String) obj);
            this.toast.setGravity(17, 0, 0);
            this.toast.show();
        }
    }

    /* access modifiers changed from: private */
    public void browseTo(File file) {
        if (file.isDirectory()) {
            this.currentDirectory = file;
            listFiles(file.listFiles());
            setTitle(file.getAbsolutePath());
            updateToolsbarStatus();
        } else if (FileKit.instance().isSupport(file.getName())) {
            Intent intent = new Intent();
            intent.setClass(this, AppActivity.class);
            intent.putExtra(MainConstant.INTENT_FILED_FILE_PATH, file.getAbsolutePath());
            startActivityForResult(intent, 1);
        }
    }

    private void listFiles(File[] fileArr) {
        this.directoryEntries.clear();
        this.selectFileItem.clear();
        if (fileArr != null) {
            if (this.listType != 0 || !isCurrentDirectory4mnt()) {
                ArrayList arrayList = null;
                if (this.listType != 2) {
                    arrayList = new ArrayList();
                    this.dbService.get(MainConstant.TABLE_STAR, arrayList);
                }
                for (File file : fileArr) {
                    String name = file.getName();
                    if (!name.startsWith(".")) {
                        if (file.isDirectory()) {
                            this.directoryEntries.add(new FileItem(file, 0, 0));
                        } else {
                            int fileIconType = this.fileAdapter.getFileIconType(name);
                            if (fileIconType >= 0) {
                                this.directoryEntries.add(new FileItem(file, fileIconType, (this.listType == 2 || FileKit.instance().isFileMarked(file.getAbsolutePath(), arrayList)) ? 1 : 0));
                            }
                        }
                    }
                }
            } else {
                for (File file2 : fileArr) {
                    if (file2.isDirectory()) {
                        String name2 = file2.getName();
                        if (name2.startsWith(MainConstant.INTENT_FILED_SDCARD_FILES) || name2.startsWith("extern_sd") || name2.startsWith("usbhost")) {
                            FileItem fileItem = new FileItem(file2, 0, 0);
                            fileItem.setShowCheckView(false);
                            this.directoryEntries.add(fileItem);
                        }
                    }
                }
            }
        }
        if (this.directoryEntries.size() > 0) {
            Collections.sort(this.directoryEntries, FileSort.instance());
            this.fileAdapter.setListItems(this.directoryEntries);
            this.listView.setAdapter(this.fileAdapter);
            return;
        }
        if (this.emptyView.getParent() == null) {
            this.fileFrame.addView(this.emptyView);
        }
        this.emptyView.setText(R.string.file_message_empty_directory);
        this.listView.setEmptyView(this.emptyView);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == -1) {
            updateMarkStatus(intent.getBooleanExtra(MainConstant.INTENT_FILED_MARK_STATUS, false) ? 1 : 0);
        }
    }

    public void updateMarkStatus(int i) {
        FileItem fileItem = this.directoryEntries.get(this.currentPos);
        if (fileItem == null) {
            return;
        }
        if (this.listType != 2 || i == 1) {
            fileItem.setFileStar(i);
            this.fileAdapter.notifyDataSetChanged();
            return;
        }
        this.directoryEntries.remove(this.currentPos);
        this.fileAdapter.notifyDataSetChanged();
        updateToolsbarStatus();
    }

    public void addSelectFileItem(FileItem fileItem) {
        this.selectFileItem.add(fileItem);
        updateToolsbarStatus();
    }

    public void removeSelectFileItem(FileItem fileItem) {
        this.selectFileItem.remove(fileItem);
        updateToolsbarStatus();
    }

    public void clearSelectFileItem() {
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof FileItemView) {
                ((FileItemView) childAt).setSelected(false);
            }
        }
        this.selectFileItem.clear();
        updateToolsbarStatus();
    }

    /* access modifiers changed from: private */
    public void updateToolsbarStatus() {
        for (int i = 0; i < this.fileFrame.getChildCount(); i++) {
            View childAt = this.fileFrame.getChildAt(i);
            if (childAt instanceof AToolsbar) {
                ((AToolsbar) childAt).updateStatus();
            }
        }
    }

    public void fileRename() {
        Vector vector = new Vector();
        vector.add(this.selectFileItem.get(0).getFile());
        new FileRenameDialog(this.control, this, this.dialogAction, vector, 3, R.string.file_toolsbar_rename).show();
    }

    public void fileCopy() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.selectFileItem.size(); i++) {
            stringBuffer.append(this.selectFileItem.get(i).getFile().getAbsolutePath());
            stringBuffer.append(";");
        }
        ((ClipboardManager) getSystemService("clipboard")).setText(stringBuffer);
    }

    public void filePaste() {
        boolean z;
        File file;
        String[] split = ((ClipboardManager) getSystemService("clipboard")).getText().toString().split(";");
        int i = 0;
        while (true) {
            if (i >= split.length) {
                z = true;
                break;
            }
            File file2 = new File(split[i]);
            if (file2.exists()) {
                if ((this.currentDirectory.getAbsolutePath() + File.separator).contains(file2.getAbsolutePath() + File.separator)) {
                    z = false;
                    break;
                }
            }
            i++;
        }
        if (z) {
            for (String file3 : split) {
                File file4 = new File(file3);
                if (file4.exists()) {
                    String absolutePath = this.currentDirectory.getAbsolutePath();
                    if (absolutePath.endsWith(File.separator)) {
                        file = new File(absolutePath + file4.getName());
                    } else {
                        file = new File(absolutePath + File.separator + file4.getName());
                    }
                    if (!file.exists()) {
                        FileKit.instance().pasteFile(file4, file);
                        if (this.bCut) {
                            FileKit.instance().deleteFile(file4);
                        }
                    } else {
                        Vector vector = new Vector();
                        vector.clear();
                        vector.add(Boolean.valueOf(this.bCut));
                        vector.add(file4);
                        vector.add(file);
                        new QuestionDialog(this.control, this, this.dialogAction, vector, 5, R.string.dialog_error_title, getResources().getText(R.string.dialog_name_error).toString().replace("%s", file4.getName()).concat(getResources().getText(R.string.dialog_overwrite_file).toString())).show();
                    }
                }
            }
            browseTo(this.currentDirectory);
            return;
        }
        new MessageDialog(this.control, this, this.dialogAction, (Vector<Object>) null, 0, R.string.dialog_error_title, getResources().getText(R.string.dialog_move_file_error).toString()).show();
    }

    public void fileDelete() {
        Vector vector = new Vector();
        for (int i = 0; i < this.selectFileItem.size(); i++) {
            vector.add(this.selectFileItem.get(i).getFile());
        }
        new QuestionDialog(this.control, this, this.dialogAction, vector, 4, R.string.file_toolsbar_delete, getResources().getText(R.string.dialog_delete_file).toString()).show();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            createSearchFileList(intent);
        }
    }

    public void fileShare() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectFileItem.size(); i++) {
            arrayList.add(Uri.fromFile(this.selectFileItem.get(i).getFile()));
        }
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.putExtra("android.intent.extra.STREAM", arrayList);
        intent.setType("application/octet-stream");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.sys_share_title)));
    }

    public void initSortType() {
        FileSort.instance().setType(MainConstant.INTENT_FILED_RECENT_FILES.equals(getIntent().getStringExtra(MainConstant.INTENT_FILED_FILE_LIST_TYPE)) ? -1 : 0, 0);
    }

    public int getSortType() {
        byte b = this.listType;
        if (b == 2) {
            return this.fileSortType.getStarType();
        }
        if (b == 1) {
            return this.fileSortType.getRecentType();
        }
        return this.fileSortType.getSdcardType();
    }

    public int getAscending() {
        byte b = this.listType;
        if (b == 2) {
            return this.fileSortType.getStarAscending();
        }
        if (b == 1) {
            return this.fileSortType.getRecentAscending();
        }
        return this.fileSortType.getSdcardAscending();
    }

    public void setSortType(int i, int i2) {
        byte b = this.listType;
        if (b == 2) {
            this.fileSortType.setStarType(i, i2);
        } else if (b == 1) {
            this.fileSortType.setRecentType(i, i2);
        } else {
            this.fileSortType.setSdcardType(i, i2);
        }
        FileSort.instance().setType(i, i2);
    }

    public void fileMarkStar() {
        FileItem fileItem = this.selectFileItem.get(0);
        if (fileItem.getFileStar() == 1) {
            fileItem.setFileStar(0);
            this.dbService.deleteItem(MainConstant.TABLE_STAR, fileItem.getFile().getAbsolutePath());
        } else {
            fileItem.setFileStar(1);
            this.dbService.insertStarFiles(MainConstant.TABLE_STAR, fileItem.getFile().getAbsolutePath());
        }
        this.fileAdapter.notifyDataSetChanged();
    }

    public boolean isCut() {
        return this.bCut;
    }

    public boolean isCopy() {
        return this.bCopy;
    }

    public byte getListType() {
        return this.listType;
    }

    public List<FileItem> getSelectFileItem() {
        return this.selectFileItem;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r2.currentDirectory;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isCurrentDirectory4mnt() {
        /*
            r2 = this;
            byte r0 = r2.listType
            if (r0 != 0) goto L_0x0016
            java.io.File r0 = r2.currentDirectory
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r0.getAbsolutePath()
            java.lang.String r1 = "/mnt"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0016
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.officereader.FileListActivity.isCurrentDirectory4mnt():boolean");
    }

    public int getCurrentDirectoryFileSize() {
        return this.directoryEntries.size();
    }

    public void dispose() {
        this.currentDirectory = null;
        this.sdcardPath = null;
        List<FileItem> list = this.directoryEntries;
        if (list != null) {
            list.clear();
            this.directoryEntries = null;
        }
        List<FileItem> list2 = this.selectFileItem;
        if (list2 != null) {
            list2.clear();
            this.selectFileItem = null;
        }
        FileItemAdapter fileItemAdapter = this.fileAdapter;
        if (fileItemAdapter != null) {
            fileItemAdapter.dispose();
            this.fileAdapter = null;
        }
        int childCount = this.listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof FileItemView) {
                ((FileItemView) childAt).dispose();
            }
        }
        this.listView = null;
        this.toast = null;
        this.onItemClickListener = null;
        this.onItemLongClickListener = null;
        this.emptyView = null;
        int childCount2 = this.fileFrame.getChildCount();
        for (int i2 = 0; i2 < childCount2; i2++) {
            View childAt2 = this.fileFrame.getChildAt(i2);
            if (childAt2 instanceof AToolsbar) {
                ((AToolsbar) childAt2).dispose();
            }
        }
        this.fileFrame = null;
        FileDialogAction fileDialogAction = this.dialogAction;
        if (fileDialogAction != null) {
            fileDialogAction.dispose();
            this.dialogAction = null;
        }
        FileSortType fileSortType2 = this.fileSortType;
        if (fileSortType2 != null) {
            fileSortType2.dispos();
            this.fileSortType = null;
        }
        DBService dBService = this.dbService;
        if (dBService != null) {
            dBService.dispose();
            this.dbService = null;
        }
        Search search2 = this.search;
        if (search2 != null) {
            search2.dispose();
            this.search = null;
        }
        IControl iControl = this.control;
        if (iControl != null) {
            iControl.dispose();
            this.control = null;
        }
    }
}
