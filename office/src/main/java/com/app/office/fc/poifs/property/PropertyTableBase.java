package com.app.office.fc.poifs.property;

import com.app.office.fc.poifs.filesystem.BATManaged;
import com.app.office.fc.poifs.storage.HeaderBlock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class PropertyTableBase implements BATManaged {
    private final HeaderBlock _header_block;
    protected final List<Property> _properties;

    public PropertyTableBase(HeaderBlock headerBlock) {
        this._header_block = headerBlock;
        this._properties = new ArrayList();
        addProperty(new RootProperty());
    }

    public PropertyTableBase(HeaderBlock headerBlock, List<Property> list) throws IOException {
        this._header_block = headerBlock;
        this._properties = list;
        populatePropertyTree((DirectoryProperty) list.get(0));
    }

    public void addProperty(Property property) {
        this._properties.add(property);
    }

    public void removeProperty(Property property) {
        this._properties.remove(property);
    }

    public RootProperty getRoot() {
        return (RootProperty) this._properties.get(0);
    }

    private void populatePropertyTree(DirectoryProperty directoryProperty) throws IOException {
        int childIndex = directoryProperty.getChildIndex();
        if (Property.isValidIndex(childIndex)) {
            Stack stack = new Stack();
            stack.push(this._properties.get(childIndex));
            while (!stack.empty()) {
                Property property = (Property) stack.pop();
                directoryProperty.addChild(property);
                if (property.isDirectory()) {
                    populatePropertyTree((DirectoryProperty) property);
                }
                int previousChildIndex = property.getPreviousChildIndex();
                if (Property.isValidIndex(previousChildIndex)) {
                    stack.push(this._properties.get(previousChildIndex));
                }
                int nextChildIndex = property.getNextChildIndex();
                if (Property.isValidIndex(nextChildIndex)) {
                    stack.push(this._properties.get(nextChildIndex));
                }
            }
        }
    }

    public int getStartBlock() {
        return this._header_block.getPropertyStart();
    }

    public void setStartBlock(int i) {
        this._header_block.setPropertyStart(i);
    }
}
