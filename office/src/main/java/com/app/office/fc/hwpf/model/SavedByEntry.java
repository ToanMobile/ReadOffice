package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public final class SavedByEntry {
    private String saveLocation;
    private String userName;

    public SavedByEntry(String str, String str2) {
        this.userName = str;
        this.saveLocation = str2;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getSaveLocation() {
        return this.saveLocation;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SavedByEntry)) {
            return false;
        }
        SavedByEntry savedByEntry = (SavedByEntry) obj;
        if (!savedByEntry.userName.equals(this.userName) || !savedByEntry.saveLocation.equals(this.saveLocation)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((377 + this.userName.hashCode()) * 13) + this.saveLocation.hashCode();
    }

    public String toString() {
        return "SavedByEntry[userName=" + getUserName() + ",saveLocation=" + getSaveLocation() + "]";
    }
}
