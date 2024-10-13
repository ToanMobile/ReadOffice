package com.app.office.fc;

public abstract class POITextExtractor {
    protected POIDocument document;

    public abstract POITextExtractor getMetadataTextExtractor();

    public abstract String getText();

    public POITextExtractor(POIDocument pOIDocument) {
        this.document = pOIDocument;
    }

    protected POITextExtractor(POITextExtractor pOITextExtractor) {
        this.document = pOITextExtractor.document;
    }
}
