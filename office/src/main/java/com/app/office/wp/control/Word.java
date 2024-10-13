package com.app.office.wp.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.picture.PictureKit;
import com.app.office.common.shape.IShape;
import com.app.office.constant.EventConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.FadeAnimation;
import com.app.office.simpletext.control.Highlight;
import com.app.office.simpletext.control.IHighlight;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.view.IView;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.SysKit;
import com.app.office.system.beans.pagelist.APageListView;
import com.app.office.wp.view.LayoutKit;
import com.app.office.wp.view.NormalRoot;
import com.app.office.wp.view.PageRoot;
import com.app.office.wp.view.PageView;
import com.app.office.wp.view.WPViewKit;

public class Word extends LinearLayout implements IWord {
    protected IControl control;
    /* access modifiers changed from: private */
    public int currentRootType;
    private IDialogAction dialogAction;
    protected IDocument doc;
    protected WPEventManage eventManage;
    private String filePath;
    protected IHighlight highlight;
    private boolean initFinish;
    private boolean isExportImageAfterZoom;
    protected int mHeight;
    protected int mWidth;
    /* access modifiers changed from: private */
    public NormalRoot normalRoot;
    private float normalZoom = 1.0f;
    private PageRoot pageRoot;
    private Paint paint;
    private int prePageCount = -1;
    private int preShowPageIndex = -1;
    /* access modifiers changed from: private */
    public PrintWord printWord;
    protected StatusManage status;
    private Rectangle visibleRect;
    private WPFind wpFind;
    protected float zoom = 1.0f;

    public byte getEditType() {
        return 2;
    }

    public FadeAnimation getParagraphAnimation(int i) {
        return null;
    }

    public IShape getTextBox() {
        return null;
    }

    public Word(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Word(Context context, IDocument iDocument, String str, IControl iControl) {
        super(context);
        this.control = iControl;
        this.doc = iDocument;
        byte wordDefaultView = iControl.getMainFrame().getWordDefaultView();
        setCurrentRootType(wordDefaultView);
        if (wordDefaultView == 1) {
            this.normalRoot = new NormalRoot(this);
        } else if (wordDefaultView == 0) {
            this.pageRoot = new PageRoot(this);
        } else if (wordDefaultView == 2) {
            this.pageRoot = new PageRoot(this);
            PrintWord printWord2 = new PrintWord(context, iControl, this.pageRoot);
            this.printWord = printWord2;
            addView(printWord2);
        }
        this.dialogAction = new WPDialogAction(iControl);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setTypeface(Typeface.SANS_SERIF);
        this.paint.setTextSize(24.0f);
        this.visibleRect = new Rectangle();
        initManage();
        if (wordDefaultView == 2) {
            setOnClickListener((View.OnClickListener) null);
        }
    }

    private void initManage() {
        WPEventManage wPEventManage = new WPEventManage(this, this.control);
        this.eventManage = wPEventManage;
        setOnTouchListener(wPEventManage);
        setLongClickable(true);
        this.wpFind = new WPFind(this);
        this.status = new StatusManage();
        this.highlight = new Highlight(this);
    }

    public void init() {
        NormalRoot normalRoot2 = this.normalRoot;
        if (normalRoot2 != null) {
            normalRoot2.doLayout(0, 0, this.mWidth, this.mHeight, Integer.MAX_VALUE, 0);
        } else {
            this.pageRoot.doLayout(0, 0, this.mWidth, this.mHeight, Integer.MAX_VALUE, 0);
        }
        this.initFinish = true;
        PrintWord printWord2 = this.printWord;
        if (printWord2 != null) {
            printWord2.init();
        }
        if (getCurrentRootType() != 2) {
            post(new Runnable() {
                public void run() {
                    Word.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                }
            });
        }
    }

    public void onDraw(Canvas canvas) {
        if (this.initFinish && this.currentRootType != 2) {
            try {
                if (getCurrentRootType() == 0) {
                    this.pageRoot.draw(canvas, 0, 0, this.zoom);
                    drawPageNubmer(canvas, this.zoom);
                } else if (getCurrentRootType() == 1) {
                    this.normalRoot.draw(canvas, 0, 0, this.normalZoom);
                }
                IOfficeToPicture officeToPicture = this.control.getOfficeToPicture();
                if (officeToPicture != null && officeToPicture.getModeType() == 0) {
                    toPicture(officeToPicture);
                }
            } catch (Exception e) {
                this.control.getSysKit().getErrorKit().writerLog(e);
            }
        }
    }

    public void createPicture() {
        IOfficeToPicture officeToPicture = this.control.getOfficeToPicture();
        if (officeToPicture != null && officeToPicture.getModeType() == 1) {
            try {
                toPicture(officeToPicture);
            } catch (Exception unused) {
            }
        }
    }

    private void toPicture(IOfficeToPicture iOfficeToPicture) {
        float f;
        if (getCurrentRootType() == 2) {
            ((WPPageListItem) this.printWord.getListView().getCurrentPageView()).addRepaintImageView((Bitmap) null);
            return;
        }
        boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
        PictureKit.instance().setDrawPictrue(true);
        Bitmap bitmap = iOfficeToPicture.getBitmap(getWidth(), getHeight());
        if (bitmap != null) {
            float zoom2 = getZoom();
            float f2 = (float) (-getScrollX());
            float f3 = (float) (-getScrollY());
            if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight())) {
                float min = Math.min(((float) bitmap.getWidth()) / ((float) getWidth()), ((float) bitmap.getHeight()) / ((float) getHeight())) * getZoom();
                PageRoot pageRoot2 = this.pageRoot;
                if ((pageRoot2 != null ? ((float) pageRoot2.getChildView().getWidth()) * min : 0.0f) > ((float) bitmap.getWidth()) || getCurrentRootType() == 1) {
                    f = Math.min((((float) getScrollX()) / zoom2) * min, (((float) getWordWidth()) * min) - ((float) bitmap.getWidth()));
                } else {
                    f = 0.0f;
                }
                float min2 = Math.min((((float) getScrollY()) / zoom2) * min, (((float) getWordHeight()) * min) - ((float) getHeight()));
                float f4 = -Math.max(0.0f, f);
                f3 = -Math.max(0.0f, min2);
                zoom2 = min;
                f2 = f4;
            }
            Canvas canvas = new Canvas(bitmap);
            canvas.translate(f2, f3);
            canvas.drawColor(-7829368);
            if (getCurrentRootType() == 0) {
                this.pageRoot.draw(canvas, 0, 0, zoom2);
            } else if (getCurrentRootType() == 1) {
                this.normalRoot.draw(canvas, 0, 0, zoom2);
            }
            iOfficeToPicture.callBack(bitmap);
            PictureKit.instance().setDrawPictrue(isDrawPictrue);
        }
    }

    public Bitmap getSnapshot(Bitmap bitmap) {
        float f;
        PrintWord printWord2;
        if (bitmap == null) {
            return null;
        }
        if (getCurrentRootType() == 2 && (printWord2 = this.printWord) != null) {
            return printWord2.getSnapshot(bitmap);
        }
        boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
        PictureKit.instance().setDrawPictrue(true);
        float zoom2 = getZoom();
        float f2 = (float) (-getScrollX());
        float f3 = (float) (-getScrollY());
        if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight())) {
            float min = Math.min(((float) bitmap.getWidth()) / ((float) getWidth()), ((float) bitmap.getHeight()) / ((float) getHeight())) * getZoom();
            PageRoot pageRoot2 = this.pageRoot;
            if ((pageRoot2 != null ? ((float) pageRoot2.getChildView().getWidth()) * min : 0.0f) > ((float) bitmap.getWidth()) || getCurrentRootType() == 1) {
                f = Math.min((((float) getScrollX()) / zoom2) * min, (((float) getWordWidth()) * min) - ((float) bitmap.getWidth()));
            } else {
                f = 0.0f;
            }
            float min2 = Math.min((((float) getScrollY()) / zoom2) * min, (((float) getWordHeight()) * min) - ((float) getHeight()));
            float f4 = -Math.max(0.0f, f);
            f3 = -Math.max(0.0f, min2);
            zoom2 = min;
            f2 = f4;
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(f2, f3);
        canvas.drawColor(-7829368);
        if (getCurrentRootType() == 0) {
            this.pageRoot.draw(canvas, 0, 0, zoom2);
        } else if (getCurrentRootType() == 1) {
            this.normalRoot.draw(canvas, 0, 0, zoom2);
        }
        PictureKit.instance().setDrawPictrue(isDrawPictrue);
        return bitmap;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.initFinish) {
            this.eventManage.stopFling();
            LayoutKit.instance().layoutAllPage(this.pageRoot, this.zoom);
            if (this.currentRootType == 0) {
                Rectangle visibleRect2 = getVisibleRect();
                int i5 = visibleRect2.x;
                int i6 = visibleRect2.y;
                int wordWidth = (int) (((float) getWordWidth()) * this.zoom);
                int wordHeight = (int) (((float) getWordHeight()) * this.zoom);
                if (visibleRect2.x + visibleRect2.width > wordWidth) {
                    i5 = wordWidth - visibleRect2.width;
                }
                if (visibleRect2.y + visibleRect2.height > wordHeight) {
                    i6 = wordHeight - visibleRect2.height;
                }
                if (!(i5 == visibleRect2.x && i6 == visibleRect2.y)) {
                    scrollTo(Math.max(0, i5), Math.max(0, i6));
                }
            }
            if (i != i3 && this.control.getMainFrame().isZoomAfterLayoutForWord()) {
                layoutNormal();
                setExportImageAfterZoom(true);
            }
            post(new Runnable() {
                public void run() {
                    Word.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                }
            });
        }
    }

    public void layoutNormal() {
        NormalRoot normalRoot2 = this.normalRoot;
        if (normalRoot2 != null) {
            normalRoot2.stopBackLayout();
            post(new Runnable() {
                public void run() {
                    if (Word.this.currentRootType == 1) {
                        Word word = Word.this;
                        word.scrollTo(0, word.getScrollY());
                    }
                    Word.this.normalRoot.layoutAll();
                    Word.this.postInvalidate();
                }
            });
        }
    }

    public void layoutPrintMode() {
        post(new Runnable() {
            public void run() {
                APageListView listView;
                if (Word.this.currentRootType == 2 && Word.this.printWord != null && (listView = Word.this.printWord.getListView()) != null && listView.getChildCount() == 1) {
                    listView.requestLayout();
                }
            }
        });
    }

    public void computeScroll() {
        if (getCurrentRootType() != 2) {
            this.eventManage.computeScroll();
        }
    }

    public void switchView(int i) {
        if (i != getCurrentRootType()) {
            this.eventManage.stopFling();
            setCurrentRootType(i);
            PictureKit.instance().setDrawPictrue(true);
            if (getCurrentRootType() == 1) {
                if (this.normalRoot == null) {
                    NormalRoot normalRoot2 = new NormalRoot(this);
                    this.normalRoot = normalRoot2;
                    normalRoot2.doLayout(0, 0, this.mWidth, this.mHeight, Integer.MAX_VALUE, 0);
                }
                setOnTouchListener(this.eventManage);
                PrintWord printWord2 = this.printWord;
                if (printWord2 != null) {
                    printWord2.setVisibility(4);
                }
            } else if (getCurrentRootType() == 0) {
                if (this.pageRoot == null) {
                    PageRoot pageRoot2 = new PageRoot(this);
                    this.pageRoot = pageRoot2;
                    pageRoot2.doLayout(0, 0, this.mWidth, this.mHeight, Integer.MAX_VALUE, 0);
                } else {
                    LayoutKit.instance().layoutAllPage(this.pageRoot, this.zoom);
                }
                setOnTouchListener(this.eventManage);
                PrintWord printWord3 = this.printWord;
                if (printWord3 != null) {
                    printWord3.setVisibility(4);
                }
            } else if (getCurrentRootType() == 2) {
                if (this.pageRoot == null) {
                    PageRoot pageRoot3 = new PageRoot(this);
                    this.pageRoot = pageRoot3;
                    pageRoot3.doLayout(0, 0, this.mWidth, this.mHeight, Integer.MAX_VALUE, 0);
                }
                PrintWord printWord4 = this.printWord;
                if (printWord4 == null) {
                    this.printWord = new PrintWord(getContext(), this.control, this.pageRoot);
                    Object viewBackground = this.control.getMainFrame().getViewBackground();
                    if (viewBackground != null) {
                        if (viewBackground instanceof Integer) {
                            this.printWord.setBackgroundColor(((Integer) viewBackground).intValue());
                        } else if (viewBackground instanceof Drawable) {
                            this.printWord.setBackgroundDrawable((Drawable) viewBackground);
                        }
                    }
                    addView(this.printWord);
                    post(new Runnable() {
                        public void run() {
                            Word.this.printWord.init();
                            Word.this.printWord.postInvalidate();
                        }
                    });
                } else {
                    printWord4.setVisibility(0);
                }
                scrollTo(0, 0);
                setOnClickListener((View.OnClickListener) null);
                return;
            }
            post(new Runnable() {
                public void run() {
                    Word word = Word.this;
                    word.scrollTo(0, word.getScrollY());
                    Word.this.postInvalidate();
                }
            });
        }
    }

    public Rectangle getVisibleRect() {
        this.visibleRect.x = getScrollX();
        this.visibleRect.y = getScrollY();
        this.visibleRect.width = getWidth();
        this.visibleRect.height = getHeight();
        return this.visibleRect;
    }

    public void setZoom(float f, int i, int i2) {
        float f2;
        int i3 = this.currentRootType;
        if (i3 == 0) {
            f2 = this.zoom;
            this.zoom = f;
            LayoutKit.instance().layoutAllPage(this.pageRoot, f);
        } else if (i3 == 2) {
            this.printWord.setZoom(f, i, i2);
            return;
        } else if (i3 == 1) {
            f2 = this.normalZoom;
            this.normalZoom = f;
        } else {
            f2 = 1.0f;
        }
        scrollToFocusXY(f, f2, i, i2);
    }

    public void setFitSize(int i) {
        if (this.currentRootType == 2) {
            this.printWord.setFitSize(i);
        }
    }

    public int getFitSizeState() {
        if (this.currentRootType == 2) {
            return this.printWord.getFitSizeState();
        }
        return 0;
    }

    private void scrollToFocusXY(float f, float f2, int i, int i2) {
        int i3;
        float f3;
        PageRoot pageRoot2;
        if (i == Integer.MIN_VALUE && i2 == Integer.MIN_VALUE) {
            i = getWidth() / 2;
            i2 = getHeight() / 2;
        }
        if (getCurrentRootType() != 0 || (pageRoot2 = this.pageRoot) == null || pageRoot2.getChildView() == null) {
            f3 = (float) getWidth();
            i3 = getHeight();
        } else {
            f3 = (float) this.pageRoot.getChildView().getWidth();
            i3 = this.pageRoot.getChildView().getHeight();
        }
        float f4 = (float) i3;
        int i4 = (int) (f4 * f2);
        int i5 = (int) (f2 * f3);
        float f5 = (float) (((int) (f3 * f)) - i5);
        scrollBy((int) (f5 * ((((float) (getScrollX() + i)) * 1.0f) / ((float) i5))), (int) (((float) (((int) (f4 * f)) - i4)) * ((((float) (getScrollY() + i2)) * 1.0f) / ((float) i4))));
    }

    public void scrollTo(int i, int i2) {
        super.scrollTo(Math.max(Math.min(Math.max(i, 0), (int) ((((float) getWordWidth()) * getZoom()) - ((float) getWidth()))), 0), Math.max(Math.min(Math.max(i2, 0), (int) ((((float) getWordHeight()) * getZoom()) - ((float) getHeight()))), 0));
    }

    public int getCurrentPageNumber() {
        if (this.currentRootType == 1 || this.pageRoot == null) {
            return 1;
        }
        if (getCurrentRootType() == 2) {
            return this.printWord.getCurrentPageNumber();
        }
        PageView pageView = WPViewKit.instance().getPageView(this.pageRoot, (int) (((float) getScrollX()) / this.zoom), ((int) (((float) getScrollY()) / this.zoom)) + (getHeight() / 3));
        if (pageView == null) {
            return 1;
        }
        return pageView.getPageNumber();
    }

    public Rectangle getPageSize(int i) {
        PageRoot pageRoot2 = this.pageRoot;
        if (pageRoot2 == null || this.currentRootType == 1) {
            return new Rectangle(0, 0, getWidth(), getHeight());
        }
        if (i < 0 || i > pageRoot2.getChildCount()) {
            return null;
        }
        PageView pageView = WPViewKit.instance().getPageView(this.pageRoot, (int) (((float) getScrollX()) / this.zoom), ((int) (((float) getScrollY()) / this.zoom)) + (getHeight() / 5));
        if (pageView != null) {
            return new Rectangle(0, 0, pageView.getWidth(), pageView.getHeight());
        }
        IAttributeSet attribute = this.doc.getSection(0).getAttribute();
        return new Rectangle(0, 0, (int) (((float) AttrManage.instance().getPageWidth(attribute)) * 0.06666667f), (int) (((float) AttrManage.instance().getPageHeight(attribute)) * 0.06666667f));
    }

    private void drawPageNubmer(Canvas canvas, float f) {
        int currentPageNumber = getCurrentPageNumber();
        if (this.control.getMainFrame().isDrawPageNumber() && this.pageRoot != null) {
            Rect clipBounds = canvas.getClipBounds();
            if (clipBounds.width() == getWidth() && clipBounds.height() == getHeight()) {
                String str = String.valueOf(currentPageNumber) + " / " + String.valueOf(this.pageRoot.getPageCount());
                int measureText = (int) this.paint.measureText(str);
                int descent = (int) (this.paint.descent() - this.paint.ascent());
                int scrollX = ((clipBounds.right + getScrollX()) - measureText) / 2;
                int i = (clipBounds.bottom - descent) - 20;
                Drawable pageNubmerDrawable = SysKit.getPageNubmerDrawable();
                pageNubmerDrawable.setBounds(scrollX - 10, i - 10, measureText + scrollX + 10, descent + i + 10);
                pageNubmerDrawable.draw(canvas);
                canvas.drawText(str, (float) scrollX, (float) ((int) (((float) i) - this.paint.ascent())), this.paint);
            } else {
                return;
            }
        }
        if (this.preShowPageIndex != currentPageNumber || this.prePageCount != getPageCount()) {
            this.control.getMainFrame().changePage();
            this.preShowPageIndex = currentPageNumber;
            this.prePageCount = getPageCount();
        }
    }

    public long viewToModel(int i, int i2, boolean z) {
        if (getCurrentRootType() == 0) {
            return this.pageRoot.viewToModel(i, i2, z);
        }
        if (getCurrentRootType() == 1) {
            return this.normalRoot.viewToModel(i, i2, z);
        }
        if (getCurrentRootType() == 2) {
            return this.printWord.viewToModel(i, i2, z);
        }
        return 0;
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        if (getCurrentRootType() == 0) {
            return this.pageRoot.modelToView(j, rectangle, z);
        }
        if (getCurrentRootType() == 1) {
            return this.normalRoot.modelToView(j, rectangle, z);
        }
        return getCurrentRootType() == 2 ? this.printWord.modelToView(j, rectangle, z) : rectangle;
    }

    public IView getRoot(int i) {
        if (i == 0) {
            return this.pageRoot;
        }
        if (i == 1) {
            return this.normalRoot;
        }
        return null;
    }

    public String getText(long j, long j2) {
        return this.doc.getText(j, j2);
    }

    public IDialogAction getDialogAction() {
        return this.dialogAction;
    }

    public WPFind getFind() {
        return this.wpFind;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public IHighlight getHighlight() {
        return this.highlight;
    }

    public IDocument getDocument() {
        return this.doc;
    }

    public IControl getControl() {
        return this.control;
    }

    public StatusManage getStatus() {
        return this.status;
    }

    public WPEventManage getEventManage() {
        return this.eventManage;
    }

    public void setWordWidth(int i) {
        this.mWidth = i;
    }

    public void setWordHeight(int i) {
        this.mHeight = i;
    }

    public void setSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public int getWordHeight() {
        if (getCurrentRootType() == 0) {
            return this.mHeight;
        }
        if (getCurrentRootType() == 1) {
            return this.normalRoot.getHeight();
        }
        return getHeight();
    }

    public int getWordWidth() {
        if (getCurrentRootType() == 0) {
            return this.mWidth;
        }
        if (getCurrentRootType() == 1) {
            return this.normalRoot.getWidth();
        }
        return getWidth();
    }

    /* access modifiers changed from: protected */
    public void showPage(int i, int i2) {
        if (i >= 0 && i < getPageCount() && getCurrentRootType() != 1) {
            if (getCurrentRootType() != 2) {
                PageView pageView = this.pageRoot.getPageView(i);
                if (pageView != null) {
                    scrollTo(getScrollX(), (int) (((float) pageView.getY()) * this.zoom));
                }
            } else if (i2 == 536870925) {
                this.printWord.previousPageview();
            } else if (i2 == 536870926) {
                this.printWord.nextPageView();
            } else {
                this.printWord.showPDFPageForIndex(i);
            }
        }
    }

    public Bitmap pageToImage(int i) {
        PageRoot pageRoot2;
        PageView pageView;
        if (i <= 0 || i > getPageCount() || (pageRoot2 = this.pageRoot) == null || pageRoot2.getChildView() == null || getCurrentRootType() == 1 || (pageView = this.pageRoot.getPageView(i - 1)) == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(pageView.getWidth(), pageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.translate((float) (-pageView.getX()), (float) (-pageView.getY()));
        canvas.drawColor(-1);
        pageView.draw(canvas, 0, 0, 1.0f);
        return createBitmap;
    }

    public Bitmap pageAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        PageRoot pageRoot2;
        PageView pageView;
        int i8 = i;
        if (!(i8 <= 0 || i8 > getPageCount() || (pageRoot2 = this.pageRoot) == null || pageRoot2.getChildView() == null || getCurrentRootType() == 1 || (pageView = this.pageRoot.getPageView(i8 - 1)) == null || !SysKit.isValidateRect(pageView.getWidth(), pageView.getHeight(), i2, i3, i4, i5))) {
            boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
            PictureKit.instance().setDrawPictrue(true);
            float f = (float) i4;
            float f2 = (float) i5;
            float min = Math.min(((float) i6) / f, ((float) i7) / f2);
            try {
                Bitmap createBitmap = Bitmap.createBitmap((int) (f * min), (int) (f2 * min), Bitmap.Config.ARGB_8888);
                if (createBitmap == null) {
                    return null;
                }
                Canvas canvas = new Canvas(createBitmap);
                canvas.translate(((float) (-(pageView.getX() + i2))) * min, ((float) (-(pageView.getY() + i3))) * min);
                canvas.drawColor(-1);
                pageView.draw(canvas, 0, 0, min);
                PictureKit.instance().setDrawPictrue(isDrawPictrue);
                return createBitmap;
            } catch (OutOfMemoryError unused) {
            }
        }
        return null;
    }

    public Bitmap getThumbnail(float f) {
        Rectangle pageSize = getPageSize(1);
        if (pageSize == null) {
            return null;
        }
        return pageAreaToImage(1, 0, 0, pageSize.width, pageSize.height, Math.round(((float) pageSize.width) * f), Math.round(((float) pageSize.height) * f));
    }

    public int getPageCount() {
        PageRoot pageRoot2;
        if (this.currentRootType == 1 || (pageRoot2 = this.pageRoot) == null) {
            return 1;
        }
        return pageRoot2.getPageCount();
    }

    public int getCurrentRootType() {
        return this.currentRootType;
    }

    public void setCurrentRootType(int i) {
        this.currentRootType = i;
    }

    public float getZoom() {
        int i = this.currentRootType;
        if (i == 1) {
            return this.normalZoom;
        }
        if (i == 0) {
            return this.zoom;
        }
        if (i != 2) {
            return this.zoom;
        }
        PrintWord printWord2 = this.printWord;
        if (printWord2 != null) {
            return printWord2.getZoom();
        }
        return this.zoom;
    }

    public float getFitZoom() {
        float f;
        int i;
        int i2 = this.currentRootType;
        if (i2 == 1) {
            return 0.5f;
        }
        PageRoot pageRoot2 = this.pageRoot;
        if (pageRoot2 == null) {
            return 1.0f;
        }
        if (i2 == 2) {
            return this.printWord.getFitZoom();
        }
        if (i2 == 0) {
            IView childView = pageRoot2.getChildView();
            if (childView == null) {
                i = 0;
            } else {
                i = childView.getWidth();
            }
            if (i == 0) {
                i = (int) (((float) AttrManage.instance().getPageWidth(this.doc.getSection(0).getAttribute())) * 0.06666667f);
            }
            int width = getWidth();
            if (width == 0) {
                width = ((View) getParent()).getWidth();
            }
            f = ((float) (width - 5)) / ((float) i);
        } else {
            f = 1.0f;
        }
        return Math.min(f, 1.0f);
    }

    public boolean isExportImageAfterZoom() {
        return this.isExportImageAfterZoom;
    }

    public void setExportImageAfterZoom(boolean z) {
        this.isExportImageAfterZoom = z;
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        PrintWord printWord2 = this.printWord;
        if (printWord2 != null) {
            printWord2.setBackgroundColor(i);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        PrintWord printWord2 = this.printWord;
        if (printWord2 != null) {
            printWord2.setBackgroundResource(i);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        PrintWord printWord2 = this.printWord;
        if (printWord2 != null) {
            printWord2.setBackgroundDrawable(drawable);
        }
    }

    public PrintWord getPrintWord() {
        return this.printWord;
    }

    public void updateFieldText() {
        PageRoot pageRoot2 = this.pageRoot;
        if (pageRoot2 != null && pageRoot2.checkUpdateHeaderFooterFieldText()) {
            this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
        }
    }

    public void dispose() {
        this.control = null;
        StatusManage statusManage = this.status;
        if (statusManage != null) {
            statusManage.dispose();
            this.status = null;
        }
        IHighlight iHighlight = this.highlight;
        if (iHighlight != null) {
            iHighlight.dispose();
            this.highlight = null;
        }
        WPEventManage wPEventManage = this.eventManage;
        if (wPEventManage != null) {
            wPEventManage.dispose();
            this.eventManage = null;
        }
        PageRoot pageRoot2 = this.pageRoot;
        if (pageRoot2 != null) {
            pageRoot2.dispose();
            this.pageRoot = null;
        }
        NormalRoot normalRoot2 = this.normalRoot;
        if (normalRoot2 != null) {
            normalRoot2.dispose();
            this.normalRoot = null;
        }
        IDialogAction iDialogAction = this.dialogAction;
        if (iDialogAction != null) {
            iDialogAction.dispose();
            this.dialogAction = null;
        }
        WPFind wPFind = this.wpFind;
        if (wPFind != null) {
            wPFind.dispose();
            this.wpFind = null;
        }
        IDocument iDocument = this.doc;
        if (iDocument != null) {
            iDocument.dispose();
            this.doc = null;
        }
        PrintWord printWord2 = this.printWord;
        if (printWord2 != null) {
            printWord2.dispose();
        }
        setOnClickListener((View.OnClickListener) null);
        this.doc = null;
        this.paint = null;
        this.visibleRect = null;
    }
}
