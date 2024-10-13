package com.app.office.fc.codec;

public interface StringEncoder extends Encoder {
    String encode(String str) throws EncoderException;
}
