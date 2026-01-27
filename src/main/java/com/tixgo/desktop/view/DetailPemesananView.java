package com.tixgo.desktop.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tixgo.desktop.Config;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DetailPemesananView extends VBox {

    public DetailPemesananView(Runnable onBack, Runnable onConfirm) {
        // --- Konfigurasi Background Utama ---
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f0f8ff;");

        // --- Container Utama (Biru) ---
        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(25));
        mainBox.setStyle("-fx-background-color: #5dade2; -fx-background-radius: 20;");
        mainBox.setAlignment(Pos.CENTER);
        
        // KUNCI RESPONSIF: Mengikuti 90% lebar jendela, dengan lebar ideal 500px
        mainBox.maxWidthProperty().bind(this.widthProperty().multiply(0.9));
        mainBox.setPrefWidth(500);

        Label title = new Label("Detail Pemesanan Tiket");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // --- Bagian Form (Kontak Pemesan) ---
        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(15));
        formBox.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15;");

        Label lblKontak = new Label("Kontak Pemesan");
        lblKontak.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TextField txtNama = new TextField();
        txtNama.setPromptText("Nama Lengkap");
        
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        TextField txtHP = new TextField();
        txtHP.setPromptText("No. HP");

        formBox.getChildren().addAll(lblKontak, txtNama, txtEmail, txtHP);

        // --- Bagian Metode Pembayaran ---
        Label lblBayarHeader = new Label("Metode Pembayaran");
        lblBayarHeader.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        HBox paymentBox = new HBox(15);
        paymentBox.setAlignment(Pos.CENTER);
        ToggleGroup group = new ToggleGroup();
        
        RadioButton rbBank = new RadioButton("Transfer Bank");
        rbBank.setToggleGroup(group);
        rbBank.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        RadioButton rbEWallet = new RadioButton("E-Wallet");
        rbEWallet.setToggleGroup(group);
        rbEWallet.setSelected(true); // Default terpilih
        rbEWallet.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        RadioButton rbCreditCard = new RadioButton("Kartu Kredit");
        rbCreditCard.setToggleGroup(group);
        rbCreditCard.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        paymentBox.getChildren().addAll(rbBank, rbEWallet, rbCreditCard);

        // --- Tombol Aksi ---
        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER);
        
        Button btnKembali = new Button("Kembali");
        btnKembali.setStyle("-fx-background-radius: 5;");
        btnKembali.setOnAction(e -> onBack.run()); 

        Button btnBayar = new Button("Konfirmasi & Bayar");
        btnBayar.setStyle("-fx-background-color: #1a5276; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnBayar.setCursor(javafx.scene.Cursor.HAND);

        // LOGIKA DATABASE & NAVIGASI
        btnBayar.setOnAction(e -> {
            try (Connection conn = Config.connect()) {
                String sql = "INSERT INTO pemesanan (nama, email, hp, metode_bayar, kereta) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, txtNama.getText());
                stmt.setString(2, txtEmail.getText());
                stmt.setString(3, txtHP.getText());
                
                // Ambil teks dari RadioButton yang aktif
                RadioButton selected = (RadioButton) group.getSelectedToggle();
                stmt.setString(4, selected.getText());
                
                stmt.setString(5, "EXPRESS 999"); // Dummy data kereta
                
                stmt.executeUpdate(); // Eksekusi simpan ke PostgreSQL
                
                // NAVIGASI: Pindah ke halaman bukti hanya setelah database berhasil diupdate
                onConfirm.run(); 
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Tampilkan pesan error jika koneksi bermasalah
                Alert alert = new Alert(Alert.AlertType.ERROR, "Gagal menyimpan ke Database: " + ex.getMessage());
                alert.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        actions.getChildren().addAll(btnKembali, btnBayar);

        // Gabungkan semua komponen ke mainBox
        mainBox.getChildren().addAll(title, formBox, lblBayarHeader, paymentBox, actions);
        this.getChildren().add(mainBox);
    }
}