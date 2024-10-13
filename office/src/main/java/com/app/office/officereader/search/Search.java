package com.app.office.officereader.search;

import com.app.office.constant.MainConstant;
import com.app.office.fc.doc.DOCReader;
import com.app.office.fc.doc.DOCXReader;
import com.app.office.fc.doc.TXTReader;
import com.app.office.fc.pdf.PDFReader;
import com.app.office.fc.ppt.PPTReader;
import com.app.office.fc.ppt.PPTXReader;
import com.app.office.fc.xls.XLSReader;
import com.app.office.fc.xls.XLSXReader;
import com.app.office.system.AbortReaderError;
import com.app.office.system.FileKit;
import com.app.office.system.IControl;
import com.app.office.system.IReader;
import java.io.File;

public class Search {
    public static final byte SEARCH_BY_AUTHOR = 2;
    public static final byte SEARCH_BY_CONTENT = 1;
    public static final byte SEARCH_BY_NAME = 0;
    /* access modifiers changed from: private */
    public IControl control;
    /* access modifiers changed from: private */
    public IReader reader = null;
    /* access modifiers changed from: private */
    public ISearchResult searchResult;
    /* access modifiers changed from: private */
    public boolean searching;
    /* access modifiers changed from: private */
    public boolean stopSearch;

    public Search(IControl iControl, ISearchResult iSearchResult) {
        this.control = iControl;
        this.searchResult = iSearchResult;
    }

    public void doSearch(File file, String str, byte b) {
        this.stopSearch = false;
        if (!this.searching) {
            this.searching = true;
            new SearchThread(file, str, b).start();
        }
    }

    public void stopSearch() {
        IReader iReader = this.reader;
        if (iReader != null) {
            iReader.abortReader();
        }
        this.stopSearch = true;
    }

    class SearchThread extends Thread {
        private File directory;
        private String key;
        private byte searchType;

        public SearchThread(File file, String str, byte b) {
            this.directory = file;
            this.key = str;
            this.searchType = b;
        }

        public void run() {
            searchFiles(this.directory, this.key);
            boolean unused = Search.this.searching = false;
            if (Search.this.searchResult != null) {
                Search.this.searchResult.searchFinish();
            }
        }

        private void searchFiles(File file, String str) {
            String lowerCase = str.toLowerCase();
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                int i = 0;
                while (i < length) {
                    File file2 = listFiles[i];
                    if (!Search.this.stopSearch) {
                        if (file2.isDirectory()) {
                            searchFiles(file2, lowerCase);
                        } else {
                            String name = file2.getName();
                            if (FileKit.instance().isSupport(name)) {
                                byte b = this.searchType;
                                if (b == 0) {
                                    if (name.toLowerCase().indexOf(lowerCase) > -1) {
                                        Search.this.searchResult.onResult(file2);
                                    }
                                } else if (b == 1) {
                                    try {
                                        searchContent(file2);
                                    } catch (AbortReaderError unused) {
                                        if (Search.this.reader != null) {
                                            Search.this.reader.dispose();
                                            IReader unused2 = Search.this.reader = null;
                                            return;
                                        }
                                        return;
                                    } catch (Exception unused3) {
                                    }
                                }
                            }
                        }
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }

        private void searchContent(File file) throws Exception {
            String lowerCase = file.getName().toLowerCase();
            if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT)) {
                IReader unused = Search.this.reader = new DOCReader((IControl) null, file.getAbsolutePath());
            } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
                IReader unused2 = Search.this.reader = new DOCXReader((IControl) null, file.getAbsolutePath());
            } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_TXT)) {
                IReader unused3 = Search.this.reader = new TXTReader((IControl) null, file.getAbsolutePath(), "GBK");
                Search.this.reader.dispose();
            } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT)) {
                Search search = Search.this;
                IReader unused4 = search.reader = new XLSReader(search.control, file.getAbsolutePath());
            } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
                Search search2 = Search.this;
                IReader unused5 = search2.reader = new XLSXReader(search2.control, file.getAbsolutePath());
            } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT)) {
                Search search3 = Search.this;
                IReader unused6 = search3.reader = new PPTReader(search3.control, file.getAbsolutePath());
            } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
                Search search4 = Search.this;
                IReader unused7 = search4.reader = new PPTXReader(search4.control, file.getAbsolutePath());
            } else if (lowerCase.endsWith("pdf")) {
                Search search5 = Search.this;
                IReader unused8 = search5.reader = new PDFReader(search5.control, file.getAbsolutePath());
            }
            Search.this.reader.dispose();
            IReader unused9 = Search.this.reader = null;
        }
    }

    public void dispose() {
        this.control = null;
        this.searchResult = null;
        this.reader = null;
    }
}
