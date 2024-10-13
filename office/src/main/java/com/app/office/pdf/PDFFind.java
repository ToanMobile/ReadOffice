package com.app.office.pdf;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.widget.Toast;
import com.app.office.common.ICustomDialog;
import com.app.office.constant.PDFConstant;
import com.app.office.simpletext.control.SafeAsyncTask;
import com.app.office.system.IFind;
import com.app.office.system.beans.pagelist.APageListItem;

public class PDFFind implements IFind {
    /* access modifiers changed from: private */
    public boolean isCancel;
    /* access modifiers changed from: private */
    public boolean isSetPointToVisible;
    /* access modifiers changed from: private */
    public boolean isStartSearch;
    /* access modifiers changed from: private */
    public int pageIndex;
    protected Paint paint;
    /* access modifiers changed from: private */
    public PDFView pdfView;
    /* access modifiers changed from: private */
    public String query;
    /* access modifiers changed from: private */
    public SafeAsyncTask safeSearchTask;
    /* access modifiers changed from: private */
    public RectF[] searchResult;
    protected Toast toast;

    static /* synthetic */ int access$212(PDFFind pDFFind, int i) {
        int i2 = pDFFind.pageIndex + i;
        pDFFind.pageIndex = i2;
        return i2;
    }

    public PDFFind(PDFView pDFView) {
        this.pdfView = pDFView;
        this.toast = Toast.makeText(pDFView.getContext(), "", 0);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setColor(PDFConstant.HIGHLIGHT_COLOR);
    }

    public boolean find(String str) {
        if (str == null) {
            return false;
        }
        this.isStartSearch = true;
        this.query = str;
        this.pageIndex = this.pdfView.getCurrentPageNumber() - 1;
        search(1);
        return true;
    }

    public boolean findBackward() {
        if (this.query == null) {
            return false;
        }
        this.isStartSearch = false;
        int i = this.pageIndex;
        if (i == 0) {
            this.toast.setText(this.pdfView.getControl().getMainFrame().getLocalString("DIALOG_FIND_TO_BEGIN"));
            this.toast.show();
            return false;
        }
        this.pageIndex = i - 1;
        search(-1);
        return true;
    }

    public boolean findForward() {
        if (this.query == null) {
            return false;
        }
        this.isStartSearch = false;
        if (this.pageIndex + 1 >= this.pdfView.getPageCount()) {
            this.toast.setText(this.pdfView.getControl().getMainFrame().getLocalString("DIALOG_FIND_TO_END"));
            this.toast.show();
            return false;
        }
        this.pageIndex++;
        search(1);
        return true;
    }

    private void search(final int i) {
        SafeAsyncTask safeAsyncTask = this.safeSearchTask;
        if (safeAsyncTask != null) {
            safeAsyncTask.cancel(true);
            this.safeSearchTask = null;
        }
        this.isSetPointToVisible = false;
        this.searchResult = null;
        this.isCancel = false;
        int pageCount = i > 0 ? this.pdfView.getPageCount() - this.pageIndex : this.pageIndex;
        final boolean isShowFindDlg = this.pdfView.getControl().getMainFrame().isShowFindDlg();
        final ProgressDialog progressDialog = new ProgressDialog(this.pdfView.getControl().getActivity());
        progressDialog.setProgressStyle(1);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(this.pdfView.getControl().getMainFrame().getLocalString("DIALOG_PDF_SEARCHING"));
        progressDialog.setMax(pageCount);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    boolean unused = PDFFind.this.isCancel = true;
                    if (PDFFind.this.safeSearchTask != null) {
                        PDFFind.this.safeSearchTask.cancel(true);
                        SafeAsyncTask unused2 = PDFFind.this.safeSearchTask = null;
                    }
                }
                return true;
            }
        });
        AnonymousClass2 r0 = new SafeAsyncTask<Void, Integer, RectF[]>() {
            /* access modifiers changed from: protected */
            public RectF[] doInBackground(Void... voidArr) {
                int i = 1;
                while (PDFFind.this.pageIndex >= 0 && PDFFind.this.pageIndex < PDFFind.this.pdfView.getPageCount() && !isCancelled()) {
                    try {
                        int i2 = i + 1;
                        publishProgress(new Integer[]{Integer.valueOf(i)});
                        RectF[] searchContentSync = PDFFind.this.pdfView.getPDFLib().searchContentSync(PDFFind.this.pageIndex, PDFFind.this.query);
                        if (searchContentSync != null && searchContentSync.length > 0) {
                            return searchContentSync;
                        }
                        PDFFind.access$212(PDFFind.this, i);
                        i = i2;
                    } catch (Exception unused) {
                    }
                }
                return null;
            }

            /* access modifiers changed from: protected */
            public void onCancelled() {
                super.onCancelled();
                if (isShowFindDlg) {
                    progressDialog.cancel();
                    return;
                }
                ICustomDialog customDialog = PDFFind.this.pdfView.getControl().getCustomDialog();
                if (customDialog != null) {
                    customDialog.dismissDialog((byte) 4);
                }
            }

            /* access modifiers changed from: protected */
            public void onProgressUpdate(Integer... numArr) {
                super.onProgressUpdate(numArr);
                if (isShowFindDlg) {
                    progressDialog.setProgress(numArr[0].intValue());
                }
            }

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                if (isShowFindDlg) {
                    PDFFind.this.pdfView.postDelayed(new Runnable() {
                        public void run() {
                            if (!PDFFind.this.isCancel) {
                                progressDialog.show();
                                progressDialog.setProgress(1);
                            }
                        }
                    }, 0);
                    return;
                }
                ICustomDialog customDialog = PDFFind.this.pdfView.getControl().getCustomDialog();
                if (customDialog != null) {
                    customDialog.showDialog((byte) 4);
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(RectF[] rectFArr) {
                String str;
                if (isShowFindDlg) {
                    progressDialog.cancel();
                } else {
                    ICustomDialog customDialog = PDFFind.this.pdfView.getControl().getCustomDialog();
                    if (customDialog != null) {
                        customDialog.dismissDialog((byte) 2);
                    }
                }
                if (rectFArr != null) {
                    RectF[] unused = PDFFind.this.searchResult = rectFArr;
                    if (PDFFind.this.pdfView.getCurrentPageNumber() - 1 != PDFFind.this.pageIndex) {
                        PDFFind.this.pdfView.getListView().showPDFPageForIndex(PDFFind.this.pageIndex);
                        boolean unused2 = PDFFind.this.isSetPointToVisible = true;
                    } else if (PDFFind.this.pdfView.getListView().isPointVisibleOnScreen((int) PDFFind.this.searchResult[0].left, (int) PDFFind.this.searchResult[0].top)) {
                        PDFFind.this.pdfView.invalidate();
                    } else {
                        PDFFind.this.pdfView.getListView().setItemPointVisibleOnScreen((int) PDFFind.this.searchResult[0].left, (int) PDFFind.this.searchResult[0].top);
                    }
                } else if (isShowFindDlg) {
                    if (PDFFind.this.isStartSearch) {
                        PDFFind.this.pdfView.getControl().getMainFrame().setFindBackForwardState(false);
                        str = PDFFind.this.pdfView.getControl().getMainFrame().getLocalString("DIALOG_FIND_NOT_FOUND");
                    } else {
                        int i = i;
                        if (i > 0) {
                            str = PDFFind.this.pdfView.getControl().getMainFrame().getLocalString("DIALOG_FIND_TO_END");
                        } else {
                            str = i < 0 ? PDFFind.this.pdfView.getControl().getMainFrame().getLocalString("DIALOG_FIND_TO_BEGIN") : "";
                        }
                    }
                    if (str != null && str.length() > 0) {
                        PDFFind.this.toast.setText(str);
                        PDFFind.this.toast.show();
                    }
                }
            }
        };
        this.safeSearchTask = r0;
        r0.safeExecute((Params[]) null);
    }

    public void drawHighlight(Canvas canvas, int i, int i2, APageListItem aPageListItem) {
        if (this.pageIndex == aPageListItem.getPageIndex()) {
            float width = ((float) aPageListItem.getWidth()) / ((float) aPageListItem.getPageWidth());
            RectF[] rectFArr = this.searchResult;
            if (rectFArr != null && rectFArr.length > 0) {
                for (RectF rectF : rectFArr) {
                    float f = ((float) i) * width;
                    float f2 = ((float) i2) * width;
                    canvas.drawRect((rectF.left * width) + f, (rectF.top * width) + f2, (rectF.right * width) + f, (rectF.bottom * width) + f2, this.paint);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public RectF[] getSearchResult() {
        return this.searchResult;
    }

    public void resetSearchResult() {
        this.searchResult = null;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public boolean isSetPointToVisible() {
        return this.isSetPointToVisible;
    }

    public void setSetPointToVisible(boolean z) {
        this.isSetPointToVisible = z;
    }

    public void dispose() {
        this.pdfView = null;
        this.toast = null;
    }
}
