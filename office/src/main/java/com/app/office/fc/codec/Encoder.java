package com.app.office.fc.codec;

public interface Encoder {
    Object encode(Object obj) throws EncoderException;
}
