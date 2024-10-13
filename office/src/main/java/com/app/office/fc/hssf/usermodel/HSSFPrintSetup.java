package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.PrintSetupRecord;
import com.app.office.fc.ss.usermodel.PrintSetup;

public class HSSFPrintSetup implements PrintSetup {
    PrintSetupRecord printSetupRecord;

    protected HSSFPrintSetup(PrintSetupRecord printSetupRecord2) {
        this.printSetupRecord = printSetupRecord2;
    }

    public void setPaperSize(short s) {
        this.printSetupRecord.setPaperSize(s);
    }

    public void setScale(short s) {
        this.printSetupRecord.setScale(s);
    }

    public void setPageStart(short s) {
        this.printSetupRecord.setPageStart(s);
    }

    public void setFitWidth(short s) {
        this.printSetupRecord.setFitWidth(s);
    }

    public void setFitHeight(short s) {
        this.printSetupRecord.setFitHeight(s);
    }

    public void setOptions(short s) {
        this.printSetupRecord.setOptions(s);
    }

    public void setLeftToRight(boolean z) {
        this.printSetupRecord.setLeftToRight(z);
    }

    public void setLandscape(boolean z) {
        this.printSetupRecord.setLandscape(!z);
    }

    public void setValidSettings(boolean z) {
        this.printSetupRecord.setValidSettings(z);
    }

    public void setNoColor(boolean z) {
        this.printSetupRecord.setNoColor(z);
    }

    public void setDraft(boolean z) {
        this.printSetupRecord.setDraft(z);
    }

    public void setNotes(boolean z) {
        this.printSetupRecord.setNotes(z);
    }

    public void setNoOrientation(boolean z) {
        this.printSetupRecord.setNoOrientation(z);
    }

    public void setUsePage(boolean z) {
        this.printSetupRecord.setUsePage(z);
    }

    public void setHResolution(short s) {
        this.printSetupRecord.setHResolution(s);
    }

    public void setVResolution(short s) {
        this.printSetupRecord.setVResolution(s);
    }

    public void setHeaderMargin(double d) {
        this.printSetupRecord.setHeaderMargin(d);
    }

    public void setFooterMargin(double d) {
        this.printSetupRecord.setFooterMargin(d);
    }

    public void setCopies(short s) {
        this.printSetupRecord.setCopies(s);
    }

    public short getPaperSize() {
        return this.printSetupRecord.getPaperSize();
    }

    public short getScale() {
        return this.printSetupRecord.getScale();
    }

    public short getPageStart() {
        return this.printSetupRecord.getPageStart();
    }

    public short getFitWidth() {
        return this.printSetupRecord.getFitWidth();
    }

    public short getFitHeight() {
        return this.printSetupRecord.getFitHeight();
    }

    public short getOptions() {
        return this.printSetupRecord.getOptions();
    }

    public boolean getLeftToRight() {
        return this.printSetupRecord.getLeftToRight();
    }

    public boolean getLandscape() {
        return !this.printSetupRecord.getLandscape();
    }

    public boolean getValidSettings() {
        return this.printSetupRecord.getValidSettings();
    }

    public boolean getNoColor() {
        return this.printSetupRecord.getNoColor();
    }

    public boolean getDraft() {
        return this.printSetupRecord.getDraft();
    }

    public boolean getNotes() {
        return this.printSetupRecord.getNotes();
    }

    public boolean getNoOrientation() {
        return this.printSetupRecord.getNoOrientation();
    }

    public boolean getUsePage() {
        return this.printSetupRecord.getUsePage();
    }

    public short getHResolution() {
        return this.printSetupRecord.getHResolution();
    }

    public short getVResolution() {
        return this.printSetupRecord.getVResolution();
    }

    public double getHeaderMargin() {
        return this.printSetupRecord.getHeaderMargin();
    }

    public double getFooterMargin() {
        return this.printSetupRecord.getFooterMargin();
    }

    public short getCopies() {
        return this.printSetupRecord.getCopies();
    }
}
