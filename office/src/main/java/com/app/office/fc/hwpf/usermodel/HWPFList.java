package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.ListFormatOverride;
import com.app.office.fc.hwpf.model.POIListData;
import com.app.office.fc.hwpf.model.StyleSheet;
import com.app.office.fc.hwpf.sprm.CharacterSprmCompressor;
import com.app.office.fc.hwpf.sprm.ParagraphSprmCompressor;

public final class HWPFList {
    private POIListData _listData;
    private ListFormatOverride _override = new ListFormatOverride(this._listData.getLsid());
    private boolean _registered;
    private StyleSheet _styleSheet;

    public HWPFList(boolean z, StyleSheet styleSheet) {
        this._listData = new POIListData((int) (Math.random() * ((double) System.currentTimeMillis())), z);
        this._styleSheet = styleSheet;
    }

    public void setLevelNumberProperties(int i, CharacterProperties characterProperties) {
        this._listData.getLevel(i).setNumberProperties(CharacterSprmCompressor.compressCharacterProperty(characterProperties, this._styleSheet.getCharacterStyle(this._listData.getLevelStyle(i))));
    }

    public void setLevelParagraphProperties(int i, ParagraphProperties paragraphProperties) {
        this._listData.getLevel(i).setLevelProperties(ParagraphSprmCompressor.compressParagraphProperty(paragraphProperties, this._styleSheet.getParagraphStyle(this._listData.getLevelStyle(i))));
    }

    public void setLevelStyle(int i, int i2) {
        this._listData.setLevelStyle(i, i2);
    }

    public POIListData getListData() {
        return this._listData;
    }

    public ListFormatOverride getOverride() {
        return this._override;
    }
}
