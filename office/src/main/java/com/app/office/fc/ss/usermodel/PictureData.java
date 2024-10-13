package com.app.office.fc.ss.usermodel;

public interface PictureData {
    byte[] getData();

    String getMimeType();

    String suggestFileExtension();
}
