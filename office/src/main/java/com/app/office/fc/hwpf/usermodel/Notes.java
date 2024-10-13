package com.app.office.fc.hwpf.usermodel;

public interface Notes {
    int getNoteAnchorPosition(int i);

    int getNoteIndexByAnchorPosition(int i);

    int getNoteTextEndOffset(int i);

    int getNoteTextStartOffset(int i);

    int getNotesCount();
}
