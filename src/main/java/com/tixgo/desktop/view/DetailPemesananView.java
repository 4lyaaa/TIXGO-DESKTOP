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
        // --- Konfigurasi Layout ---
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f0f8ff;");

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(25));
        mainBox.setStyle("-fx-background-color: #5dade2; -fx-background-radius: 20;");
        mainBox.setAlignment(Pos.CENTER);
        mainBox.maxWidthProperty().bind(this.widthProperty().multiply(0.9));
        mainBox.setPrefWidth(500);

        Label title = new Label("Detail Pemesanan Tiket");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // --- Form Kontak ---
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

        // --- Metode Pembayaran ---
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
        rbEWallet.setSelected(true);
        rbEWallet.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        RadioButton rbCreditCard = new RadioButton("Kartu Kredit");
        rbCreditCard.setToggleGroup(group);
        rbCreditCard.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        paymentBox.getChildren().addAll(rbBank, rbEWallet, rbCreditCard);

        // --- Tombol Aksi ---
        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER);
        
        Button btnKembali = new Button("Kembali");
        btnKembali.setOnAction(e -> onBack.run()); 

        Button btnBayar = new Button("Konfirmasi & Bayar");
        btnBayar.setStyle("-fx-background-color: #1a5276; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnBayar.setCursor(javafx.scene.Cursor.HAND);

        // --- LOGIKA DATABASE GABUNGAN ---
        btnBayar.setOnAction(e -> {
            try (Connection conn = Config.connect()) {
                String sql = "INSERT INTO pemesanan (nama_pemesan, email, hp, stasiun_asal, " +
                             "stasiun_tujuan, tanggal_berangkat, kelas_kereta, total_bayar, metode_bayar) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                // 1-3: Data Form
                stmt.setString(1, txtNama.getText());
                stmt.setString(2, txtEmail.getText());
                stmt.setString(3, txtHP.getText());
                
                // 4-8: Data Perjalanan (Dummy untuk saat ini)
                stmt.setString(4, "Jakarta"); 
                stmt.setString(5, "Surabaya");
                stmt.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now())); 
                stmt.setString(7, "Eksekutif");
                stmt.setDouble(8, 150000.0);
                
                // 9: Metode Bayar
                RadioButton selected = (RadioButton) group.getSelectedToggle();
                stmt.setString(9, selected != null ? selected.getText() : "E-Wallet");
                
                // Eksekusi dan Navigasi
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Simpan Berhasil!");
                    onConfirm.run(); // PINDAH HALAMAN KE BUKTI PEMESANAN
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Alert ini akan muncul jika ada yang salah (misal: tabel belum dibuat)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText("Gagal Simpan ke Riwayat");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        actions.getChildren().addAll(btnKembali, btnBayar);
        mainBox.getChildren().addAll(title, formBox, lblBayarHeader, paymentBox, actions);
        this.getChildren().add(mainBox);
    }
}