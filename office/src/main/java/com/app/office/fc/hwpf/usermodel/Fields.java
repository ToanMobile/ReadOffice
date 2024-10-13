package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.FieldsDocumentPart;
import java.util.Collection;

public interface Fields {
    Field getFieldByStartOffset(FieldsDocumentPart fieldsDocumentPart, int i);

    Collection<Field> getFields(FieldsDocumentPart fieldsDocumentPart);
}
