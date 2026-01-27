package com.tixgo.desktop.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tixgo.desktop.Config;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class BuktiPemesananView extends VBox {

    public BuktiPemesananView(Runnable onHome) {
        // --- Konfigurasi Layout Utama ---
        this.setSpacing(20);
        this.setPadding(new Insets(50));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #5dade2;"); // Background biru sesuai gambar

        // --- Card Putih di Tengah ---
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
        card.setMaxWidth(450);
        card.setAlignment(Pos.CENTER);

        // Header Selamat
        Label icon = new Label("🎉");
        icon.setStyle("-fx-font-size: 30px;");
        
        Label title = new Label("Selamat!");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label subtitle = new Label("Pemesanan tiket Anda berhasil");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // --- Detail Informasi dari Database ---
        GridPane details = new GridPane();
        details.setHgap(20);
        details.setVgap(10);
        details.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(20, 0, 20, 0));

        // Logika Mengambil Data Terakhir dari PostgreSQL
        try (Connection conn = Config.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pemesanan ORDER BY id DESC LIMIT 1")) {
            
            if (rs.next()) {
                addDetailRow(details, 0, "Nama Pemesan", rs.getString("nama"));
                addDetailRow(details, 1, "Email", rs.getString("email"));
                addDetailRow(details, 2, "No. HP", rs.getString("hp"));
                addDetailRow(details, 3, "Kereta", rs.getString("kereta"));
                addDetailRow(details, 4, "Rute", "Surabaya - Bali"); // Data statis/tambahan
                addDetailRow(details, 5, "Pembayaran", rs.getString("metode_bayar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Gagal mengambil data pemesanan.");
            errorLabel.setStyle("-fx-text-fill: red;");
            card.getChildren().add(errorLabel);
        }

        // --- Tombol Kembali ke Home ---
        Button btnHome = new Button("Kembali ke Home");
        btnHome.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 10;");
        btnHome.setCursor(javafx.scene.Cursor.HAND);
        btnHome.setOnAction(e -> onHome.run());

        // Masukkan semua elemen ke dalam kartu
        card.getChildren().addAll(icon, title, subtitle, details, btnHome);
        this.getChildren().add(card);
    }

    // Helper Method untuk membuat baris detail yang rapi
    private void addDetailRow(GridPane grid, int row, String label, String value) {
        Label lblName = new Label(label);
        lblName.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        
        Label lblValue = new Label(value != null ? value : "-");
        lblValue.setStyle("-fx-text-fill: #2c3e50;");

        grid.add(lblName, 0, row);
        grid.add(lblValue, 1, row);
    }
}