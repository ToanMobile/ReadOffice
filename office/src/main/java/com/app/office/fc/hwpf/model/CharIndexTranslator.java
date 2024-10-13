package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public interface CharIndexTranslator {
    int getByteIndex(int i);

    int getCharIndex(int i);

    int getCharIndex(int i, int i2);

    boolean isIndexInTable(int i);

    int lookIndexBackward(int i);

    int lookIndexForward(int i);
}
