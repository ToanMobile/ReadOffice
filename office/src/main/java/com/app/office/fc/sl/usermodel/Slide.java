package com.app.office.fc.sl.usermodel;

public interface Slide extends Sheet {
    boolean getFollowMasterBackground();

    boolean getFollowMasterColourScheme();

    boolean getFollowMasterObjects();

    Notes getNotes();

    void setFollowMasterBackground(boolean z);

    void setFollowMasterColourScheme(boolean z);

    void setFollowMasterObjects(boolean z);

    void setNotes(Notes notes);
}
