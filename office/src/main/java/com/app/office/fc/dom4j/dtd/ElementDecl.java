package com.app.office.fc.dom4j.dtd;

public class ElementDecl {
    private String model;
    private String name;

    public ElementDecl() {
    }

    public ElementDecl(String str, String str2) {
        this.name = str;
        this.model = str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String toString() {
        return "<!ELEMENT " + this.name + " " + this.model + ">";
    }
}
