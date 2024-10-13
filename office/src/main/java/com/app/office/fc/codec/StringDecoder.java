package com.app.office.fc.codec;

public interface StringDecoder extends Decoder {
    String decode(String str) throws DecoderException;
}
