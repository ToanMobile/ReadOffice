package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class Panose implements EMFConstants {
    private int armStyle;
    private int contrast;
    private int familyType;
    private int letterForm;
    private int midLine;
    private int proportion;
    private int serifStyle;
    private int strokeVariation;
    private int weight;
    private int xHeight;

    public Panose() {
        this.familyType = 1;
        this.serifStyle = 1;
        this.proportion = 1;
        this.weight = 1;
        this.contrast = 1;
        this.strokeVariation = 1;
        this.armStyle = 0;
        this.letterForm = 0;
        this.midLine = 0;
        this.xHeight = 0;
    }

    public Panose(EMFInputStream eMFInputStream) throws IOException {
        this.familyType = eMFInputStream.readBYTE();
        this.serifStyle = eMFInputStream.readBYTE();
        this.proportion = eMFInputStream.readBYTE();
        this.weight = eMFInputStream.readBYTE();
        this.contrast = eMFInputStream.readBYTE();
        this.strokeVariation = eMFInputStream.readBYTE();
        this.armStyle = eMFInputStream.readBYTE();
        this.letterForm = eMFInputStream.readBYTE();
        this.midLine = eMFInputStream.readBYTE();
        this.xHeight = eMFInputStream.readBYTE();
    }

    public String toString() {
        return "  Panose\n    familytype: " + this.familyType + "\n    serifStyle: " + this.serifStyle + "\n    weight: " + this.weight + "\n    proportion: " + this.proportion + "\n    contrast: " + this.contrast + "\n    strokeVariation: " + this.strokeVariation + "\n    armStyle: " + this.armStyle + "\n    letterForm: " + this.letterForm + "\n    midLine: " + this.midLine + "\n    xHeight: " + this.xHeight;
    }
}
