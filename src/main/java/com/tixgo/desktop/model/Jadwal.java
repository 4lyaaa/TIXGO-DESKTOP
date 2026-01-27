package com.tixgo.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jadwal {
    private final StringProperty no = new SimpleStringProperty();
    private final StringProperty namaKereta = new SimpleStringProperty();
    private final StringProperty tglBerangkat = new SimpleStringProperty();
    private final StringProperty tglSampai = new SimpleStringProperty();

    public Jadwal(String no, String nama, String berangkat, String sampai) {
        this.no.set(no);
        this.namaKereta.set(nama);
        this.tglBerangkat.set(berangkat);
        this.tglSampai.set(sampai);
    }

    public StringProperty noProperty() { return no; }
    public StringProperty namaKeretaProperty() { return namaKereta; }
    public StringProperty tglBerangkatProperty() { return tglBerangkat; }
    public StringProperty tglSampaiProperty() { return tglSampai; }
}