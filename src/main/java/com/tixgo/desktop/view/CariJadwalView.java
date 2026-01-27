package com.tixgo.desktop.view;

import com.tixgo.desktop.model.Jadwal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class CariJadwalView extends VBox {

    public CariJadwalView(Runnable onBook) {
        // --- Konfigurasi Layout Utama ---
        this.setSpacing(20);
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f0f8ff;");

        // --- Container Biru Muda (Panel Jadwal) ---
        VBox container = new VBox(15);
        container.setPadding(new Insets(25));
        container.setStyle("-fx-background-color: #5dade2; -fx-background-radius: 20;");
        container.setAlignment(Pos.CENTER);
        
        // KUNCI RESPONSIF: Lebar box mengikuti 90% lebar jendela
        container.maxWidthProperty().bind(this.widthProperty().multiply(0.9));

        Label title = new Label("Jadwal Kereta Tersedia");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // --- Konfigurasi TableView ---
        TableView<Jadwal> table = new TableView<>();
        table.setPrefHeight(350);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-selection-bar: #075386;"); 

        // 1. Kolom Nama Kereta
        TableColumn<Jadwal, String> colNama = new TableColumn<>("Kereta");
        colNama.setCellValueFactory(cell -> cell.getValue().namaKeretaProperty());

        // 2. Kolom Waktu (Menggabungkan info berangkat-sampai)
        TableColumn<Jadwal, String> colWaktu = new TableColumn<>("Waktu");
        colWaktu.setCellValueFactory(cell -> cell.getValue().tglBerangkatProperty());

        // 3. Kolom Harga (Tambahan sesuai Tugas Point 2)
        TableColumn<Jadwal, String> colHarga = new TableColumn<>("Harga");
        // Catatan: Pastikan model Jadwal Anda memiliki property harga, 
        // jika belum, sementara bisa menggunakan dummy text atau property lain
        colHarga.setCellValueFactory(cell -> cell.getValue().tglSampaiProperty()); 

        // 4. Kolom Aksi (Tombol Pilih & Pesan)
        TableColumn<Jadwal, Void> colAksi = new TableColumn<>("Aksi");
        colAksi.setStyle("-fx-alignment: CENTER;"); 
        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Pilih & Pesan");
            {
                btn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                btn.setCursor(javafx.scene.Cursor.HAND);
                btn.setOnAction(e -> onBook.run()); 
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(btn);
            }
        });

        // Tambahkan semua kolom ke tabel
        table.getColumns().addAll(colNama, colWaktu, colHarga, colAksi);

        // Data dummy sesuai tampilan frontend
        ObservableList<Jadwal> data = FXCollections.observableArrayList(
            new Jadwal("1", "EXPRESS 999", "30-11-2025 07:30", "Rp 500.000"),
            new Jadwal("2", "EK-524", "30-11-2025 16:10", "Rp 450.000"),
            new Jadwal("3", "EK-471", "30-11-2025 20:25", "Rp 450.000")
        );
        table.setItems(data);

        // --- Tombol Kembali (Opsional untuk navigasi) ---
        Button btnPilihManual = new Button("Pilih & Pesan");
        btnPilihManual.setStyle("-fx-background-color: #1a5276; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnPilihManual.setOnAction(e -> onBook.run());

        // --- Gabungkan Semua ke Container ---
        container.getChildren().addAll(title, table, btnPilihManual);
        this.getChildren().add(container);
    }
}