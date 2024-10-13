package com.app.office.objectpool;

public interface IMemObj {
    void free();

    IMemObj getCopy();
}
