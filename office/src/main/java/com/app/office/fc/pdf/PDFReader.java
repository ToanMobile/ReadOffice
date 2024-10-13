package com.app.office.fc.pdf;

import com.app.office.system.AbstractReader;
import com.app.office.system.IControl;

public class PDFReader extends AbstractReader {
    private String filePath;
    private PDFLib lib;

    public PDFReader(IControl iControl, String str) throws Exception {
        this.control = iControl;
        this.filePath = str;
    }

    public Object getModel() throws Exception {
        this.control.actionEvent(26, false);
        PDFLib pDFLib = PDFLib.getPDFLib();
        this.lib = pDFLib;
        pDFLib.openFileSync(this.filePath);
        return this.lib;
    }

    public void dispose() {
        super.dispose();
        this.lib = null;
        this.control = null;
    }
}
