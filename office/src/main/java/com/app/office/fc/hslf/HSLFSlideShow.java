package com.app.office.fc.hslf;

import com.app.office.fc.fs.filesystem.CFBFileSystem;
import com.app.office.fc.fs.filesystem.Property;
import com.app.office.fc.hslf.blip.Metafile;
import com.app.office.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.app.office.fc.hslf.exceptions.EncryptedPowerPointFileException;
import com.app.office.fc.hslf.record.CurrentUserAtom;
import com.app.office.fc.hslf.record.ExOleObjStg;
import com.app.office.fc.hslf.record.PersistPtrHolder;
import com.app.office.fc.hslf.record.PersistRecord;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.record.UserEditAtom;
import com.app.office.fc.hslf.usermodel.ObjectData;
import com.app.office.fc.hslf.usermodel.PictureData;
import com.app.office.system.IControl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public final class HSLFSlideShow {
    protected static final int CHECKSUM_SIZE = 16;
    private Property _docProp;
    private byte[] _docstream;
    private ObjectData[] _objects;
    private List<PictureData> _pictures;
    private Record[] _records;
    private CFBFileSystem cfbFS;
    private IControl control;
    private CurrentUserAtom currentUser;

    private void readOtherStreams() {
    }

    public HSLFSlideShow(IControl iControl, String str) throws IOException {
        this.control = iControl;
        this.cfbFS = new CFBFileSystem(new FileInputStream(str));
        readCurrentUserStream();
        readPowerPointStream();
        if (!(this.cfbFS.getPropertyRawData("EncryptedSummary") != null)) {
            buildRecords();
            readOtherStreams();
            return;
        }
        throw new EncryptedPowerPointFileException("Cannot process encrypted office files!");
    }

    private void readPowerPointStream() throws IOException {
        this._docstream = this.cfbFS.getPropertyRawData("PowerPoint Document");
        this._docProp = this.cfbFS.getProperty("PowerPoint Document");
    }

    private void buildRecords() {
        this._records = read((int) this.currentUser.getCurrentEditOffset());
    }

    private Record[] read(int i) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        while (i != 0) {
            UserEditAtom userEditAtom = (UserEditAtom) Record.buildRecordAtOffset(this._docProp.getRecordData(i), 0, i);
            arrayList.add(Integer.valueOf(i));
            int persistPointersOffset = userEditAtom.getPersistPointersOffset();
            arrayList.add(Integer.valueOf(persistPointersOffset));
            Hashtable<Integer, Integer> slideLocationsLookup = ((PersistPtrHolder) Record.buildRecordAtOffset(this._docProp.getRecordData(persistPointersOffset), 0, persistPointersOffset)).getSlideLocationsLookup();
            for (Integer next : slideLocationsLookup.keySet()) {
                Integer num = slideLocationsLookup.get(next);
                arrayList.add(num);
                hashMap.put(num, next);
            }
            i = userEditAtom.getLastUserEditAtomOffset();
        }
        Integer[] numArr = (Integer[]) arrayList.toArray(new Integer[arrayList.size()]);
        Arrays.sort(numArr);
        Record[] recordArr = new Record[arrayList.size()];
        for (int i2 = 0; i2 < numArr.length; i2++) {
            Integer num2 = numArr[i2];
            recordArr[i2] = Record.buildRecordAtOffset(this._docProp.getRecordData(num2.intValue()), 0, num2.intValue());
            if (recordArr[i2] instanceof PersistRecord) {
                ((PersistRecord) recordArr[i2]).setPersistId(((Integer) hashMap.get(num2)).intValue());
            }
        }
        return recordArr;
    }

    private void readCurrentUserStream() {
        try {
            this.currentUser = new CurrentUserAtom(this.cfbFS);
        } catch (IOException unused) {
            this.currentUser = new CurrentUserAtom();
        }
    }

    private void readPictures() throws IOException {
        Property property;
        if (this.control != null && (property = this.cfbFS.getProperty("Pictures")) != null) {
            this._pictures = new ArrayList();
            long propertyRawDataSize = property.getPropertyRawDataSize();
            int i = 0;
            while (((long) i) <= propertyRawDataSize - 8) {
                property.getUShort(i);
                int i2 = i + 2;
                int uShort = property.getUShort(i2);
                int i3 = i2 + 2;
                int i4 = property.getInt(i3);
                int i5 = i3 + 4;
                if (i4 >= 0) {
                    if (uShort != 0) {
                        try {
                            PictureData create = PictureData.create(uShort - RecordTypes.EscherBlip_START);
                            create.setOffset(i);
                            if (create.getType() == 5 || create.getType() == 6 || create.getType() == 7 || create.getType() == 3 || create.getType() == 2) {
                                File file = new File(this.control.getSysKit().getPictureManage().getPicTempPath() + File.separator + (String.valueOf(System.currentTimeMillis()) + ".tmp"));
                                try {
                                    file.createNewFile();
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    if (create.getType() != 3) {
                                        if (create.getType() != 2) {
                                            if (create.getType() == 6) {
                                                int i6 = i5 + 17;
                                                if (property.getLong(i6) == 727905341920923785L) {
                                                    property.writeByte(fileOutputStream, i6, i4 - 17);
                                                } else {
                                                    int i7 = i5 + 33;
                                                    if (property.getLong(i7) == 727905341920923785L) {
                                                        property.writeByte(fileOutputStream, i7, i4 - 33);
                                                    }
                                                }
                                            } else {
                                                property.writeByte(fileOutputStream, i5 + 17, i4 - 17);
                                            }
                                            fileOutputStream.close();
                                            create.setTempFilePath(file.getAbsolutePath());
                                        }
                                    }
                                    create.setRawData(property.getRecordData(create.getOffset()));
                                    ((Metafile) create).writeByte_WMFAndEMF(fileOutputStream);
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    this.control.getSysKit().getErrorKit().writerLog(e);
                                }
                                create.setTempFilePath(file.getAbsolutePath());
                            }
                            this._pictures.add(create);
                        } catch (IllegalArgumentException e2) {
                            this.control.getSysKit().getErrorKit().writerLog(e2);
                        }
                    }
                    i = i5 + i4;
                } else {
                    return;
                }
            }
        }
    }

    public synchronized int appendRootLevelRecord(Record record) {
        int i;
        Record[] recordArr = this._records;
        Record[] recordArr2 = new Record[(recordArr.length + 1)];
        boolean z = false;
        i = -1;
        for (int length = recordArr.length - 1; length >= 0; length--) {
            if (z) {
                recordArr2[length] = this._records[length];
            } else {
                Record[] recordArr3 = this._records;
                recordArr2[length + 1] = recordArr3[length];
                if (recordArr3[length] instanceof PersistPtrHolder) {
                    recordArr2[length] = record;
                    i = length;
                    z = true;
                }
            }
        }
        this._records = recordArr2;
        return i;
    }

    public int addPicture(PictureData pictureData) {
        if (this._pictures == null) {
            try {
                readPictures();
            } catch (IOException e) {
                throw new CorruptPowerPointFileException(e.getMessage());
            }
        }
        int i = 0;
        if (this._pictures.size() > 0) {
            List<PictureData> list = this._pictures;
            PictureData pictureData2 = list.get(list.size() - 1);
            i = pictureData2.getOffset() + pictureData2.getRawData().length + 8;
        }
        pictureData.setOffset(i);
        this._pictures.add(pictureData);
        return i;
    }

    public Record[] getRecords() {
        return this._records;
    }

    public byte[] getUnderlyingBytes() {
        return this._docstream;
    }

    public CurrentUserAtom getCurrentUserAtom() {
        return this.currentUser;
    }

    public PictureData[] getPictures() {
        if (this._pictures == null) {
            try {
                readPictures();
            } catch (IOException e) {
                throw new CorruptPowerPointFileException(e.getMessage());
            } catch (OutOfMemoryError e2) {
                this.control.getSysKit().getErrorKit().writerLog(e2, true);
                this.control.actionEvent(23, true);
                this.control = null;
            }
        }
        List<PictureData> list = this._pictures;
        if (list != null) {
            return (PictureData[]) list.toArray(new PictureData[list.size()]);
        }
        return null;
    }

    public ObjectData[] getEmbeddedObjects() {
        if (this._objects == null) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                Record[] recordArr = this._records;
                if (i >= recordArr.length) {
                    break;
                }
                if (recordArr[i] instanceof ExOleObjStg) {
                    arrayList.add(new ObjectData((ExOleObjStg) recordArr[i]));
                }
                i++;
            }
            this._objects = (ObjectData[]) arrayList.toArray(new ObjectData[arrayList.size()]);
        }
        return this._objects;
    }

    public void dispose() {
        CurrentUserAtom currentUserAtom = this.currentUser;
        if (currentUserAtom != null) {
            currentUserAtom.dispose();
            this.currentUser = null;
        }
        Record[] recordArr = this._records;
        if (recordArr != null) {
            for (Record dispose : recordArr) {
                dispose.dispose();
            }
            this._records = null;
        }
        List<PictureData> list = this._pictures;
        if (list != null) {
            for (PictureData dispose2 : list) {
                dispose2.dispose();
            }
            this._pictures.clear();
            this._pictures = null;
        }
        ObjectData[] objectDataArr = this._objects;
        if (objectDataArr != null) {
            for (ObjectData dispose3 : objectDataArr) {
                dispose3.dispose();
            }
            this._objects = null;
        }
        CFBFileSystem cFBFileSystem = this.cfbFS;
        if (cFBFileSystem != null) {
            cFBFileSystem.dispose();
            this.cfbFS = null;
        }
        this.control = null;
        this._docstream = null;
    }
}
