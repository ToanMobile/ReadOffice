package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.NotesTables;
import java.util.HashMap;
import java.util.Map;

public class NotesImpl implements Notes {
    private Map<Integer, Integer> anchorToIndexMap = null;
    private final NotesTables notesTables;

    public NotesImpl(NotesTables notesTables2) {
        this.notesTables = notesTables2;
    }

    public int getNoteAnchorPosition(int i) {
        return this.notesTables.getDescriptor(i).getStart();
    }

    public int getNoteIndexByAnchorPosition(int i) {
        updateAnchorToIndexMap();
        Integer num = this.anchorToIndexMap.get(Integer.valueOf(i));
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public int getNotesCount() {
        return this.notesTables.getDescriptorsCount();
    }

    public int getNoteTextEndOffset(int i) {
        return this.notesTables.getTextPosition(i).getEnd();
    }

    public int getNoteTextStartOffset(int i) {
        return this.notesTables.getTextPosition(i).getStart();
    }

    private void updateAnchorToIndexMap() {
        if (this.anchorToIndexMap == null) {
            HashMap hashMap = new HashMap();
            for (int i = 0; i < this.notesTables.getDescriptorsCount(); i++) {
                hashMap.put(Integer.valueOf(this.notesTables.getDescriptor(i).getStart()), Integer.valueOf(i));
            }
            this.anchorToIndexMap = hashMap;
        }
    }
}
