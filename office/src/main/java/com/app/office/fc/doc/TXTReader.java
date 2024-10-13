package com.app.office.fc.doc;

import com.onesignal.OneSignalRemoteParams;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.system.AbstractReader;
import com.app.office.system.IControl;
import com.app.office.wp.model.WPDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TXTReader extends AbstractReader {
    private String encoding;
    private String filePath;
    private long offset;
    private IDocument wpdoc;

    public TXTReader(IControl iControl, String str, String str2) {
        this.control = iControl;
        this.filePath = str;
        this.encoding = str2;
    }

    public boolean authenticate(String str) {
        if (this.encoding != null) {
            return true;
        }
        this.encoding = str;
        if (str != null) {
            try {
                this.control.actionEvent(0, getModel());
                return true;
            } catch (Throwable th) {
                this.control.getSysKit().getErrorKit().writerLog(th);
            }
        }
        return false;
    }

    public Object getModel() throws Exception {
        IDocument iDocument = this.wpdoc;
        if (iDocument != null) {
            return iDocument;
        }
        this.wpdoc = new WPDocument();
        if (this.encoding != null) {
            readFile();
        }
        return this.wpdoc;
    }

    public void readFile() throws Exception {
        SectionElement sectionElement = new SectionElement();
        IAttributeSet attribute = sectionElement.getAttribute();
        AttrManage.instance().setPageWidth(attribute, 11906);
        AttrManage.instance().setPageHeight(attribute, 16838);
        AttrManage.instance().setPageMarginLeft(attribute, 1800);
        AttrManage.instance().setPageMarginRight(attribute, 1800);
        AttrManage.instance().setPageMarginTop(attribute, OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW);
        AttrManage.instance().setPageMarginBottom(attribute, OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW);
        sectionElement.setStartOffset(this.offset);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.filePath)), this.encoding));
        while (true) {
            String readLine = bufferedReader.readLine();
            if ((readLine != null || this.offset == 0) && !this.abortReader) {
                String replace = (readLine == null ? "\n" : readLine.concat("\n")).replace(9, ' ');
                int length = replace.length();
                if (length > 500) {
                    int i = 200;
                    int i2 = 0;
                    while (i <= length) {
                        String concat = replace.substring(i2, i).concat("\n");
                        ParagraphElement paragraphElement = new ParagraphElement();
                        paragraphElement.setStartOffset(this.offset);
                        LeafElement leafElement = new LeafElement(concat);
                        leafElement.setStartOffset(this.offset);
                        long length2 = this.offset + ((long) concat.length());
                        this.offset = length2;
                        leafElement.setEndOffset(length2);
                        paragraphElement.appendLeaf(leafElement);
                        paragraphElement.setEndOffset(this.offset);
                        this.wpdoc.appendParagraph(paragraphElement, 0);
                        if (i == length) {
                            break;
                        }
                        int i3 = i + 100;
                        if (i3 > length) {
                            i3 = length;
                        }
                        int i4 = i3;
                        i2 = i;
                        i = i4;
                    }
                } else {
                    ParagraphElement paragraphElement2 = new ParagraphElement();
                    paragraphElement2.setStartOffset(this.offset);
                    LeafElement leafElement2 = new LeafElement(replace);
                    leafElement2.setStartOffset(this.offset);
                    long length3 = this.offset + ((long) replace.length());
                    this.offset = length3;
                    leafElement2.setEndOffset(length3);
                    paragraphElement2.appendLeaf(leafElement2);
                    paragraphElement2.setEndOffset(this.offset);
                    this.wpdoc.appendParagraph(paragraphElement2, 0);
                }
            }
        }
        bufferedReader.close();
        sectionElement.setEndOffset(this.offset);
        this.wpdoc.appendSection(sectionElement);
    }

    public boolean searchContent(File file, String str) throws Exception {
        String readLine;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        do {
            readLine = bufferedReader.readLine();
            if (readLine == null) {
                return false;
            }
        } while (readLine.indexOf(str) <= 0);
        return true;
    }

    public void dispose() {
        if (isReaderFinish()) {
            this.wpdoc = null;
            this.filePath = null;
            this.control = null;
        }
    }
}
