package com.app.office.fc.hpsf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MutableSection extends Section {
    private boolean dirty;
    private List<Property> preprops;
    private byte[] sectionBytes;

    public MutableSection() {
        this.dirty = true;
        this.dirty = true;
        this.formatID = null;
        this.offset = -1;
        this.preprops = new LinkedList();
    }

    public MutableSection(Section section) {
        this.dirty = true;
        setFormatID(section.getFormatID());
        Property[] properties = section.getProperties();
        MutableProperty[] mutablePropertyArr = new MutableProperty[properties.length];
        for (int i = 0; i < properties.length; i++) {
            mutablePropertyArr[i] = new MutableProperty(properties[i]);
        }
        setProperties(mutablePropertyArr);
        setDictionary(section.getDictionary());
    }

    public void setFormatID(ClassID classID) {
        this.formatID = classID;
    }

    public void setFormatID(byte[] bArr) {
        ClassID formatID = getFormatID();
        if (formatID == null) {
            formatID = new ClassID();
            setFormatID(formatID);
        }
        formatID.setBytes(bArr);
    }

    public void setProperties(Property[] propertyArr) {
        this.properties = propertyArr;
        this.preprops = new LinkedList();
        for (Property add : propertyArr) {
            this.preprops.add(add);
        }
        this.dirty = true;
    }

    public void setProperty(int i, String str) {
        setProperty(i, 31, str);
        this.dirty = true;
    }

    public void setProperty(int i, int i2) {
        setProperty(i, 3, Integer.valueOf(i2));
        this.dirty = true;
    }

    public void setProperty(int i, long j) {
        setProperty(i, 20, Long.valueOf(j));
        this.dirty = true;
    }

    public void setProperty(int i, boolean z) {
        setProperty(i, 11, Boolean.valueOf(z));
        this.dirty = true;
    }

    public void setProperty(int i, long j, Object obj) {
        MutableProperty mutableProperty = new MutableProperty();
        mutableProperty.setID((long) i);
        mutableProperty.setType(j);
        mutableProperty.setValue(obj);
        setProperty(mutableProperty);
        this.dirty = true;
    }

    public void setProperty(Property property) {
        removeProperty(property.getID());
        this.preprops.add(property);
        this.dirty = true;
    }

    public void removeProperty(long j) {
        Iterator<Property> it = this.preprops.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().getID() == j) {
                    it.remove();
                    break;
                }
            } else {
                break;
            }
        }
        this.dirty = true;
    }

    /* access modifiers changed from: protected */
    public void setPropertyBooleanValue(int i, boolean z) {
        setProperty(i, 11, Boolean.valueOf(z));
    }

    public int getSize() {
        if (this.dirty) {
            try {
                this.size = calcSize();
                this.dirty = false;
            } catch (HPSFRuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new HPSFRuntimeException((Throwable) e2);
            }
        }
        return this.size;
    }

    private int calcSize() throws WritingNotSupportedException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        write(byteArrayOutputStream);
        byteArrayOutputStream.close();
        byte[] pad4 = Util.pad4(byteArrayOutputStream.toByteArray());
        this.sectionBytes = pad4;
        return pad4.length;
    }

    public int write(OutputStream outputStream) throws WritingNotSupportedException, IOException {
        int i;
        int writeDictionary;
        byte[] bArr;
        if (this.dirty || (bArr = this.sectionBytes) == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            int propertyCount = (getPropertyCount() * 2 * 4) + 8 + 0;
            if (getProperty(0) != null) {
                Object property = getProperty(1);
                if (property == null) {
                    setProperty(1, 2, 1200);
                } else if (!(property instanceof Integer)) {
                    throw new IllegalPropertySetDataException("The codepage property (ID = 1) must be an Integer object.");
                }
                i = getCodepage();
            } else {
                i = -1;
            }
            Collections.sort(this.preprops, new Comparator<Property>() {
                public int compare(Property property, Property property2) {
                    if (property.getID() < property2.getID()) {
                        return -1;
                    }
                    return property.getID() == property2.getID() ? 0 : 1;
                }
            });
            ListIterator<Property> listIterator = this.preprops.listIterator();
            while (listIterator.hasNext()) {
                MutableProperty mutableProperty = (MutableProperty) listIterator.next();
                long id = mutableProperty.getID();
                TypeWriter.writeUIntToStream(byteArrayOutputStream2, mutableProperty.getID());
                TypeWriter.writeUIntToStream(byteArrayOutputStream2, (long) propertyCount);
                if (id != 0) {
                    writeDictionary = mutableProperty.write(byteArrayOutputStream, getCodepage());
                } else if (i != -1) {
                    writeDictionary = writeDictionary(byteArrayOutputStream, this.dictionary, i);
                } else {
                    throw new IllegalPropertySetDataException("Codepage (property 1) is undefined.");
                }
                propertyCount += writeDictionary;
            }
            byteArrayOutputStream.close();
            byteArrayOutputStream2.close();
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
            TypeWriter.writeToStream(outputStream, byteArray.length + 8 + byteArray2.length);
            TypeWriter.writeToStream(outputStream, getPropertyCount());
            outputStream.write(byteArray);
            outputStream.write(byteArray2);
            return byteArray.length + 8 + byteArray2.length;
        }
        outputStream.write(bArr);
        return this.sectionBytes.length;
    }

    private static int writeDictionary(OutputStream outputStream, Map<Long, String> map, int i) throws IOException {
        int writeUIntToStream = TypeWriter.writeUIntToStream(outputStream, (long) map.size());
        for (Long next : map.keySet()) {
            String str = map.get(next);
            if (i == 1200) {
                int length = str.length() + 1;
                if (length % 2 == 1) {
                    length++;
                }
                writeUIntToStream = writeUIntToStream + TypeWriter.writeUIntToStream(outputStream, next.longValue()) + TypeWriter.writeUIntToStream(outputStream, (long) length);
                byte[] bytes = str.getBytes(VariantSupport.codepageToEncoding(i));
                for (int i2 = 2; i2 < bytes.length; i2 += 2) {
                    outputStream.write(bytes[i2 + 1]);
                    outputStream.write(bytes[i2]);
                    writeUIntToStream += 2;
                }
                for (int length2 = length - str.length(); length2 > 0; length2--) {
                    outputStream.write(0);
                    outputStream.write(0);
                    writeUIntToStream += 2;
                }
            } else {
                int writeUIntToStream2 = writeUIntToStream + TypeWriter.writeUIntToStream(outputStream, next.longValue()) + TypeWriter.writeUIntToStream(outputStream, (long) (str.length() + 1));
                byte[] bytes2 = str.getBytes(VariantSupport.codepageToEncoding(i));
                for (byte write : bytes2) {
                    outputStream.write(write);
                    writeUIntToStream2++;
                }
                outputStream.write(0);
                writeUIntToStream = writeUIntToStream2 + 1;
            }
        }
        return writeUIntToStream;
    }

    public int getPropertyCount() {
        return this.preprops.size();
    }

    public Property[] getProperties() {
        this.properties = (Property[]) this.preprops.toArray(new Property[0]);
        return this.properties;
    }

    public Object getProperty(long j) {
        getProperties();
        return super.getProperty(j);
    }

    public void setDictionary(Map<Long, String> map) throws IllegalPropertySetDataException {
        if (map != null) {
            this.dictionary = map;
            setProperty(0, -1, map);
            if (((Integer) getProperty(1)) == null) {
                setProperty(1, 2, 1200);
                return;
            }
            return;
        }
        removeProperty(0);
    }

    public void setProperty(int i, Object obj) {
        if (obj instanceof String) {
            setProperty(i, (String) obj);
        } else if (obj instanceof Long) {
            setProperty(i, ((Long) obj).longValue());
        } else if (obj instanceof Integer) {
            setProperty(i, ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            setProperty(i, ((Short) obj).intValue());
        } else if (obj instanceof Boolean) {
            setProperty(i, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Date) {
            setProperty(i, 64, obj);
        } else {
            throw new HPSFRuntimeException("HPSF does not support properties of type " + obj.getClass().getName() + ".");
        }
    }

    public void clear() {
        Property[] properties = getProperties();
        for (Property id : properties) {
            removeProperty(id.getID());
        }
    }

    public void setCodepage(int i) {
        setProperty(1, 2, Integer.valueOf(i));
    }
}
