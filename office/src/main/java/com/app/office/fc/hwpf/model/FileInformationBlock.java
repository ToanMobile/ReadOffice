package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.FIBAbstractType;
import com.app.office.fc.util.Internal;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

@Internal
public final class FileInformationBlock extends FIBAbstractType implements Cloneable {
    FIBFieldHandler _fieldHandler;
    FIBLongHandler _longHandler;
    FIBShortHandler _shortHandler;

    public FileInformationBlock(byte[] bArr) {
        fillFields(bArr, 0);
    }

    public void fillVariableFields(byte[] bArr, byte[] bArr2) {
        FIBShortHandler fIBShortHandler = new FIBShortHandler(bArr);
        this._shortHandler = fIBShortHandler;
        this._longHandler = new FIBLongHandler(bArr, fIBShortHandler.sizeInBytes() + 32);
        HashSet hashSet = new HashSet();
        hashSet.add(1);
        hashSet.add(33);
        hashSet.add(31);
        hashSet.add(12);
        hashSet.add(13);
        hashSet.add(6);
        hashSet.add(73);
        hashSet.add(74);
        for (FieldsDocumentPart fibFieldsField : FieldsDocumentPart.values()) {
            hashSet.add(Integer.valueOf(fibFieldsField.getFibFieldsField()));
        }
        hashSet.add(22);
        hashSet.add(23);
        hashSet.add(21);
        for (NoteType noteType : NoteType.values()) {
            hashSet.add(Integer.valueOf(noteType.getFibDescriptorsFieldIndex()));
            hashSet.add(Integer.valueOf(noteType.getFibTextPositionsFieldIndex()));
        }
        hashSet.add(15);
        hashSet.add(51);
        hashSet.add(71);
        hashSet.add(87);
        this._fieldHandler = new FIBFieldHandler(bArr, this._shortHandler.sizeInBytes() + 32 + this._longHandler.sizeInBytes(), bArr2, hashSet, true);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("[FIB2]\n");
        sb.append("\tSubdocuments info:\n");
        for (SubdocumentType subdocumentType : SubdocumentType.values()) {
            sb.append("\t\t");
            sb.append(subdocumentType);
            sb.append(" has length of ");
            sb.append(getSubdocumentTextStreamLength(subdocumentType));
            sb.append(" char(s)\n");
        }
        sb.append("\tFields PLCF info:\n");
        for (FieldsDocumentPart fieldsDocumentPart : FieldsDocumentPart.values()) {
            sb.append("\t\t");
            sb.append(fieldsDocumentPart);
            sb.append(": PLCF starts at ");
            sb.append(getFieldsPlcfOffset(fieldsDocumentPart));
            sb.append(" and have length of ");
            sb.append(getFieldsPlcfLength(fieldsDocumentPart));
            sb.append("\n");
        }
        sb.append("\tNotes PLCF info:\n");
        for (NoteType noteType : NoteType.values()) {
            sb.append("\t\t");
            sb.append(noteType);
            sb.append(": descriptions starts ");
            sb.append(getNotesDescriptorsOffset(noteType));
            sb.append(" and have length of ");
            sb.append(getNotesDescriptorsSize(noteType));
            sb.append(" bytes\n");
            sb.append("\t\t");
            sb.append(noteType);
            sb.append(": text positions starts ");
            sb.append(getNotesTextPositionsOffset(noteType));
            sb.append(" and have length of ");
            sb.append(getNotesTextPositionsSize(noteType));
            sb.append(" bytes\n");
        }
        try {
            sb.append("\tJava reflection info:\n");
            for (Method method : FileInformationBlock.class.getMethods()) {
                if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
                    if (method.getParameterTypes().length <= 0) {
                        sb.append("\t\t");
                        sb.append(method.getName());
                        sb.append(" => ");
                        sb.append(method.invoke(this, new Object[0]));
                        sb.append("\n");
                    }
                }
            }
        } catch (Exception e) {
            sb.append("(exc: " + e.getMessage() + ")");
        }
        sb.append("[/FIB2]\n");
        return sb.toString();
    }

    public int getFcPlcfTxbxBkd() {
        return this._fieldHandler.getFieldOffset(75);
    }

    public int getLcbPlcfTxbxBkd() {
        return this._fieldHandler.getFieldSize(75);
    }

    public int getFcPlcfTxbxHdrBkd() {
        return this._fieldHandler.getFieldOffset(76);
    }

    public int getLcbPlcfTxbxHdrBkd() {
        return this._fieldHandler.getFieldSize(76);
    }

    public int getFcDop() {
        return this._fieldHandler.getFieldOffset(31);
    }

    public void setFcDop(int i) {
        this._fieldHandler.setFieldOffset(31, i);
    }

    public int getLcbDop() {
        return this._fieldHandler.getFieldSize(31);
    }

    public void setLcbDop(int i) {
        this._fieldHandler.setFieldSize(31, i);
    }

    public int getFcStshf() {
        return this._fieldHandler.getFieldOffset(1);
    }

    public int getLcbStshf() {
        return this._fieldHandler.getFieldSize(1);
    }

    public void setFcStshf(int i) {
        this._fieldHandler.setFieldOffset(1, i);
    }

    public void setLcbStshf(int i) {
        this._fieldHandler.setFieldSize(1, i);
    }

    public int getFcClx() {
        return this._fieldHandler.getFieldOffset(33);
    }

    public int getLcbClx() {
        return this._fieldHandler.getFieldSize(33);
    }

    public void setFcClx(int i) {
        this._fieldHandler.setFieldOffset(33, i);
    }

    public void setLcbClx(int i) {
        this._fieldHandler.setFieldSize(33, i);
    }

    public int getFcPlcfbteChpx() {
        return this._fieldHandler.getFieldOffset(12);
    }

    public int getLcbPlcfbteChpx() {
        return this._fieldHandler.getFieldSize(12);
    }

    public void setFcPlcfbteChpx(int i) {
        this._fieldHandler.setFieldOffset(12, i);
    }

    public void setLcbPlcfbteChpx(int i) {
        this._fieldHandler.setFieldSize(12, i);
    }

    public int getFcPlcfbtePapx() {
        return this._fieldHandler.getFieldOffset(13);
    }

    public int getLcbPlcfbtePapx() {
        return this._fieldHandler.getFieldSize(13);
    }

    public void setFcPlcfbtePapx(int i) {
        this._fieldHandler.setFieldOffset(13, i);
    }

    public void setLcbPlcfbtePapx(int i) {
        this._fieldHandler.setFieldSize(13, i);
    }

    public int getFcPlcfsed() {
        return this._fieldHandler.getFieldOffset(6);
    }

    public int getLcbPlcfsed() {
        return this._fieldHandler.getFieldSize(6);
    }

    public void setFcPlcfsed(int i) {
        this._fieldHandler.setFieldOffset(6, i);
    }

    public void setLcbPlcfsed(int i) {
        this._fieldHandler.setFieldSize(6, i);
    }

    public int getFcPlcfLst() {
        return this._fieldHandler.getFieldOffset(73);
    }

    public int getLcbPlcfLst() {
        return this._fieldHandler.getFieldSize(73);
    }

    public void setFcPlcfLst(int i) {
        this._fieldHandler.setFieldOffset(73, i);
    }

    public void setLcbPlcfLst(int i) {
        this._fieldHandler.setFieldSize(73, i);
    }

    public int getFcPlfLfo() {
        return this._fieldHandler.getFieldOffset(74);
    }

    public int getLcbPlfLfo() {
        return this._fieldHandler.getFieldSize(74);
    }

    public int getFcSttbfbkmk() {
        return this._fieldHandler.getFieldOffset(21);
    }

    public void setFcSttbfbkmk(int i) {
        this._fieldHandler.setFieldOffset(21, i);
    }

    public int getLcbSttbfbkmk() {
        return this._fieldHandler.getFieldSize(21);
    }

    public void setLcbSttbfbkmk(int i) {
        this._fieldHandler.setFieldSize(21, i);
    }

    public int getFcPlcfbkf() {
        return this._fieldHandler.getFieldOffset(22);
    }

    public void setFcPlcfbkf(int i) {
        this._fieldHandler.setFieldOffset(22, i);
    }

    public int getLcbPlcfbkf() {
        return this._fieldHandler.getFieldSize(22);
    }

    public void setLcbPlcfbkf(int i) {
        this._fieldHandler.setFieldSize(22, i);
    }

    public int getFcPlcfbkl() {
        return this._fieldHandler.getFieldOffset(23);
    }

    public void setFcPlcfbkl(int i) {
        this._fieldHandler.setFieldOffset(23, i);
    }

    public int getLcbPlcfbkl() {
        return this._fieldHandler.getFieldSize(23);
    }

    public void setLcbPlcfbkl(int i) {
        this._fieldHandler.setFieldSize(23, i);
    }

    public void setFcPlfLfo(int i) {
        this._fieldHandler.setFieldOffset(74, i);
    }

    public void setLcbPlfLfo(int i) {
        this._fieldHandler.setFieldSize(74, i);
    }

    public int getFcSttbfffn() {
        return this._fieldHandler.getFieldOffset(15);
    }

    public int getLcbSttbfffn() {
        return this._fieldHandler.getFieldSize(15);
    }

    public void setFcSttbfffn(int i) {
        this._fieldHandler.setFieldOffset(15, i);
    }

    public void setLcbSttbfffn(int i) {
        this._fieldHandler.setFieldSize(15, i);
    }

    public int getFcSttbfRMark() {
        return this._fieldHandler.getFieldOffset(51);
    }

    public int getLcbSttbfRMark() {
        return this._fieldHandler.getFieldSize(51);
    }

    public void setFcSttbfRMark(int i) {
        this._fieldHandler.setFieldOffset(51, i);
    }

    public void setLcbSttbfRMark(int i) {
        this._fieldHandler.setFieldSize(51, i);
    }

    public int getPlcfHddOffset() {
        return this._fieldHandler.getFieldOffset(11);
    }

    public int getPlcfHddSize() {
        return this._fieldHandler.getFieldSize(11);
    }

    public void setPlcfHddOffset(int i) {
        this._fieldHandler.setFieldOffset(11, i);
    }

    public void setPlcfHddSize(int i) {
        this._fieldHandler.setFieldSize(11, i);
    }

    public int getFcSttbSavedBy() {
        return this._fieldHandler.getFieldOffset(71);
    }

    public int getLcbSttbSavedBy() {
        return this._fieldHandler.getFieldSize(71);
    }

    public void setFcSttbSavedBy(int i) {
        this._fieldHandler.setFieldOffset(71, i);
    }

    public void setLcbSttbSavedBy(int i) {
        this._fieldHandler.setFieldSize(71, i);
    }

    public int getModifiedLow() {
        return this._fieldHandler.getFieldOffset(74);
    }

    public int getModifiedHigh() {
        return this._fieldHandler.getFieldSize(74);
    }

    public void setModifiedLow(int i) {
        this._fieldHandler.setFieldOffset(74, i);
    }

    public void setModifiedHigh(int i) {
        this._fieldHandler.setFieldSize(74, i);
    }

    public int getCbMac() {
        return this._longHandler.getLong(0);
    }

    public void setCbMac(int i) {
        this._longHandler.setLong(0, i);
    }

    public int getSubdocumentTextStreamLength(SubdocumentType subdocumentType) {
        return this._longHandler.getLong(subdocumentType.getFibLongFieldIndex());
    }

    public void setSubdocumentTextStreamLength(SubdocumentType subdocumentType, int i) {
        if (i >= 0) {
            this._longHandler.setLong(subdocumentType.getFibLongFieldIndex(), i);
            return;
        }
        throw new IllegalArgumentException("Subdocument length can't be less than 0 (passed value is " + i + "). If there is no subdocument length must be set to zero.");
    }

    @Deprecated
    public int getCcpText() {
        return this._longHandler.getLong(3);
    }

    @Deprecated
    public void setCcpText(int i) {
        this._longHandler.setLong(3, i);
    }

    @Deprecated
    public int getCcpFtn() {
        return this._longHandler.getLong(4);
    }

    @Deprecated
    public void setCcpFtn(int i) {
        this._longHandler.setLong(4, i);
    }

    @Deprecated
    public int getCcpHdd() {
        return this._longHandler.getLong(5);
    }

    @Deprecated
    public void setCcpHdd(int i) {
        this._longHandler.setLong(5, i);
    }

    @Deprecated
    public int getCcpAtn() {
        return this._longHandler.getLong(7);
    }

    @Deprecated
    public int getCcpCommentAtn() {
        return getCcpAtn();
    }

    @Deprecated
    public void setCcpAtn(int i) {
        this._longHandler.setLong(7, i);
    }

    @Deprecated
    public int getCcpEdn() {
        return this._longHandler.getLong(8);
    }

    @Deprecated
    public void setCcpEdn(int i) {
        this._longHandler.setLong(8, i);
    }

    @Deprecated
    public int getCcpTxtBx() {
        return this._longHandler.getLong(9);
    }

    @Deprecated
    public void setCcpTxtBx(int i) {
        this._longHandler.setLong(9, i);
    }

    @Deprecated
    public int getCcpHdrTxtBx() {
        return this._longHandler.getLong(10);
    }

    @Deprecated
    public void setCcpHdrTxtBx(int i) {
        this._longHandler.setLong(10, i);
    }

    public void clearOffsetsSizes() {
        this._fieldHandler.clearFields();
    }

    public int getFieldsPlcfOffset(FieldsDocumentPart fieldsDocumentPart) {
        return this._fieldHandler.getFieldOffset(fieldsDocumentPart.getFibFieldsField());
    }

    public int getFieldsPlcfLength(FieldsDocumentPart fieldsDocumentPart) {
        return this._fieldHandler.getFieldSize(fieldsDocumentPart.getFibFieldsField());
    }

    public void setFieldsPlcfOffset(FieldsDocumentPart fieldsDocumentPart, int i) {
        this._fieldHandler.setFieldOffset(fieldsDocumentPart.getFibFieldsField(), i);
    }

    public void setFieldsPlcfLength(FieldsDocumentPart fieldsDocumentPart, int i) {
        this._fieldHandler.setFieldSize(fieldsDocumentPart.getFibFieldsField(), i);
    }

    @Deprecated
    public int getFcPlcffldAtn() {
        return this._fieldHandler.getFieldOffset(19);
    }

    @Deprecated
    public int getLcbPlcffldAtn() {
        return this._fieldHandler.getFieldSize(19);
    }

    @Deprecated
    public void setFcPlcffldAtn(int i) {
        this._fieldHandler.setFieldOffset(19, i);
    }

    @Deprecated
    public void setLcbPlcffldAtn(int i) {
        this._fieldHandler.setFieldSize(19, i);
    }

    @Deprecated
    public int getFcPlcffldEdn() {
        return this._fieldHandler.getFieldOffset(48);
    }

    @Deprecated
    public int getLcbPlcffldEdn() {
        return this._fieldHandler.getFieldSize(48);
    }

    @Deprecated
    public void setFcPlcffldEdn(int i) {
        this._fieldHandler.setFieldOffset(48, i);
    }

    @Deprecated
    public void setLcbPlcffldEdn(int i) {
        this._fieldHandler.setFieldSize(48, i);
    }

    @Deprecated
    public int getFcPlcffldFtn() {
        return this._fieldHandler.getFieldOffset(18);
    }

    @Deprecated
    public int getLcbPlcffldFtn() {
        return this._fieldHandler.getFieldSize(18);
    }

    @Deprecated
    public void setFcPlcffldFtn(int i) {
        this._fieldHandler.setFieldOffset(18, i);
    }

    @Deprecated
    public void setLcbPlcffldFtn(int i) {
        this._fieldHandler.setFieldSize(18, i);
    }

    @Deprecated
    public int getFcPlcffldHdr() {
        return this._fieldHandler.getFieldOffset(17);
    }

    @Deprecated
    public int getLcbPlcffldHdr() {
        return this._fieldHandler.getFieldSize(17);
    }

    @Deprecated
    public void setFcPlcffldHdr(int i) {
        this._fieldHandler.setFieldOffset(17, i);
    }

    @Deprecated
    public void setLcbPlcffldHdr(int i) {
        this._fieldHandler.setFieldSize(17, i);
    }

    @Deprecated
    public int getFcPlcffldHdrtxbx() {
        return this._fieldHandler.getFieldOffset(59);
    }

    @Deprecated
    public int getLcbPlcffldHdrtxbx() {
        return this._fieldHandler.getFieldSize(59);
    }

    @Deprecated
    public void setFcPlcffldHdrtxbx(int i) {
        this._fieldHandler.setFieldOffset(59, i);
    }

    @Deprecated
    public void setLcbPlcffldHdrtxbx(int i) {
        this._fieldHandler.setFieldSize(59, i);
    }

    @Deprecated
    public int getFcPlcffldMom() {
        return this._fieldHandler.getFieldOffset(16);
    }

    @Deprecated
    public int getLcbPlcffldMom() {
        return this._fieldHandler.getFieldSize(16);
    }

    @Deprecated
    public void setFcPlcffldMom(int i) {
        this._fieldHandler.setFieldOffset(16, i);
    }

    @Deprecated
    public void setLcbPlcffldMom(int i) {
        this._fieldHandler.setFieldSize(16, i);
    }

    @Deprecated
    public int getFcPlcffldTxbx() {
        return this._fieldHandler.getFieldOffset(57);
    }

    @Deprecated
    public int getLcbPlcffldTxbx() {
        return this._fieldHandler.getFieldSize(57);
    }

    @Deprecated
    public void setFcPlcffldTxbx(int i) {
        this._fieldHandler.setFieldOffset(57, i);
    }

    @Deprecated
    public void setLcbPlcffldTxbx(int i) {
        this._fieldHandler.setFieldSize(57, i);
    }

    public int getFSPAPlcfOffset(FSPADocumentPart fSPADocumentPart) {
        return this._fieldHandler.getFieldOffset(fSPADocumentPart.getFibFieldsField());
    }

    public int getFSPAPlcfLength(FSPADocumentPart fSPADocumentPart) {
        return this._fieldHandler.getFieldSize(fSPADocumentPart.getFibFieldsField());
    }

    public void setFSPAPlcfOffset(FSPADocumentPart fSPADocumentPart, int i) {
        this._fieldHandler.setFieldOffset(fSPADocumentPart.getFibFieldsField(), i);
    }

    public void setFSPAPlcfLength(FSPADocumentPart fSPADocumentPart, int i) {
        this._fieldHandler.setFieldSize(fSPADocumentPart.getFibFieldsField(), i);
    }

    @Deprecated
    public int getFcPlcspaMom() {
        return this._fieldHandler.getFieldOffset(40);
    }

    @Deprecated
    public int getLcbPlcspaMom() {
        return this._fieldHandler.getFieldSize(40);
    }

    public int getFcDggInfo() {
        return this._fieldHandler.getFieldOffset(50);
    }

    public int getLcbDggInfo() {
        return this._fieldHandler.getFieldSize(50);
    }

    public int getNotesDescriptorsOffset(NoteType noteType) {
        return this._fieldHandler.getFieldOffset(noteType.getFibDescriptorsFieldIndex());
    }

    public void setNotesDescriptorsOffset(NoteType noteType, int i) {
        this._fieldHandler.setFieldOffset(noteType.getFibDescriptorsFieldIndex(), i);
    }

    public int getNotesDescriptorsSize(NoteType noteType) {
        return this._fieldHandler.getFieldSize(noteType.getFibDescriptorsFieldIndex());
    }

    public void setNotesDescriptorsSize(NoteType noteType, int i) {
        this._fieldHandler.setFieldSize(noteType.getFibDescriptorsFieldIndex(), i);
    }

    public int getNotesTextPositionsOffset(NoteType noteType) {
        return this._fieldHandler.getFieldOffset(noteType.getFibTextPositionsFieldIndex());
    }

    public void setNotesTextPositionsOffset(NoteType noteType, int i) {
        this._fieldHandler.setFieldOffset(noteType.getFibTextPositionsFieldIndex(), i);
    }

    public int getNotesTextPositionsSize(NoteType noteType) {
        return this._fieldHandler.getFieldSize(noteType.getFibTextPositionsFieldIndex());
    }

    public void setNotesTextPositionsSize(NoteType noteType, int i) {
        this._fieldHandler.setFieldSize(noteType.getFibTextPositionsFieldIndex(), i);
    }

    public int getSize() {
        return super.getSize() + this._shortHandler.sizeInBytes() + this._longHandler.sizeInBytes() + this._fieldHandler.sizeInBytes();
    }
}
