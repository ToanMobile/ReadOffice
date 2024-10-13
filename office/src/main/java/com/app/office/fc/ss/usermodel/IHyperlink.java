package com.app.office.fc.ss.usermodel;

import com.app.office.fc.usermodel.Hyperlink;

public interface IHyperlink extends Hyperlink {
    int getFirstColumn();

    int getFirstRow();

    int getLastColumn();

    int getLastRow();

    void setFirstColumn(int i);

    void setFirstRow(int i);

    void setLastColumn(int i);

    void setLastRow(int i);
}
