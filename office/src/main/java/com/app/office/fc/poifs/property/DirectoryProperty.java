package com.app.office.fc.poifs.property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DirectoryProperty extends Property implements Parent {
    private List<Property> _children = new ArrayList();
    private Set<String> _children_names = new HashSet();

    public boolean isDirectory() {
        return true;
    }

    public DirectoryProperty(String str) {
        setName(str);
        setSize(0);
        setPropertyType((byte) 1);
        setStartBlock(0);
        setNodeColor((byte) 1);
    }

    protected DirectoryProperty(int i, byte[] bArr, int i2) {
        super(i, bArr, i2);
    }

    public boolean changeName(Property property, String str) {
        String name = property.getName();
        property.setName(str);
        String name2 = property.getName();
        if (this._children_names.contains(name2)) {
            property.setName(name);
            return false;
        }
        this._children_names.add(name2);
        this._children_names.remove(name);
        return true;
    }

    public boolean deleteChild(Property property) {
        boolean remove = this._children.remove(property);
        if (remove) {
            this._children_names.remove(property.getName());
        }
        return remove;
    }

    public static class PropertyComparator implements Comparator<Property> {
        public boolean equals(Object obj) {
            return this == obj;
        }

        public int compare(Property property, Property property2) {
            String name = property.getName();
            String name2 = property2.getName();
            int length = name.length() - name2.length();
            if (length != 0) {
                return length;
            }
            if (name.compareTo("_VBA_PROJECT") != 0) {
                if (name2.compareTo("_VBA_PROJECT") != 0) {
                    if (name.startsWith("__") && name2.startsWith("__")) {
                        return name.compareToIgnoreCase(name2);
                    }
                    if (!name.startsWith("__")) {
                        if (!name2.startsWith("__")) {
                            return name.compareToIgnoreCase(name2);
                        }
                    }
                }
                return -1;
            }
            return 1;
        }
    }

    /* access modifiers changed from: protected */
    public void preWrite() {
        if (this._children.size() > 0) {
            Property[] propertyArr = (Property[]) this._children.toArray(new Property[0]);
            Arrays.sort(propertyArr, new PropertyComparator());
            int length = propertyArr.length / 2;
            setChildProperty(propertyArr[length].getIndex());
            propertyArr[0].setPreviousChild((Child) null);
            propertyArr[0].setNextChild((Child) null);
            for (int i = 1; i < length; i++) {
                propertyArr[i].setPreviousChild(propertyArr[i - 1]);
                propertyArr[i].setNextChild((Child) null);
            }
            if (length != 0) {
                propertyArr[length].setPreviousChild(propertyArr[length - 1]);
            }
            if (length != propertyArr.length - 1) {
                Property property = propertyArr[length];
                int i2 = length + 1;
                property.setNextChild(propertyArr[i2]);
                while (i2 < propertyArr.length - 1) {
                    propertyArr[i2].setPreviousChild((Child) null);
                    Property property2 = propertyArr[i2];
                    i2++;
                    property2.setNextChild(propertyArr[i2]);
                }
                propertyArr[propertyArr.length - 1].setPreviousChild((Child) null);
                propertyArr[propertyArr.length - 1].setNextChild((Child) null);
                return;
            }
            propertyArr[length].setNextChild((Child) null);
        }
    }

    public Iterator<Property> getChildren() {
        return this._children.iterator();
    }

    public void addChild(Property property) throws IOException {
        String name = property.getName();
        if (!this._children_names.contains(name)) {
            this._children_names.add(name);
            this._children.add(property);
            return;
        }
        throw new IOException("Duplicate name \"" + name + "\"");
    }
}
