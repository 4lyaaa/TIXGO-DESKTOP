package com.tixgo.desktop.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardView extends StackPane {

    public DashboardView(Runnable onSearch) {
        // --- 1. BAGIAN BACKGROUND IMAGE ---
        try {
            String path = getClass().getResource("/images/background_kereta.png").toExternalForm();
            Image bgImg = new Image(path);
            
            BackgroundImage bImg = new BackgroundImage(
                bgImg,
                BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
            );
            this.setBackground(new Background(bImg));
        } catch (Exception e) {
            this.setStyle("-fx-background-color: #34495e;"); 
            System.err.println("Gagal memuat: Pastikan file 'background_kereta.png' ada di resources/images/");
        }

        // --- 2. LAYOUT UTAMA (SLOGAN & FORM) ---
        HBox mainLayout = new HBox(60);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));

        // SISI KIRI: SLOGAN
        VBox leftBox = new VBox(5);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        
        Label slogan = new Label("Siap Bertualang?");
        slogan.setStyle("-fx-font-size: 45px; -fx-font-weight: bold; -fx-text-fill: white; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        
        Label subSlogan = new Label("Isi Detail Perjalanan Anda Sekarang");
        subSlogan.setStyle("-fx-font-size: 18px; -fx-text-fill: white; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        
        leftBox.getChildren().addAll(slogan, subSlogan);

        // SISI KANAN: CARD FORM
        VBox card = new VBox(12);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                      "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5);");
        card.setPrefWidth(380);
        card.maxWidthProperty().bind(this.widthProperty().multiply(0.35));

        Label cardTitle = new Label("Pemesanan Tiket");
        cardTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        String inputStyle = "-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #dcdde1; -fx-background-color: #f9f9f9;";
        
        ComboBox<String> cbAsal = new ComboBox<>(FXCollections.observableArrayList("Jakarta", "Bandung", "Surabaya"));
        cbAsal.setPromptText("Pilih Stasiun Asal");
        cbAsal.setMaxWidth(Double.MAX_VALUE);
        cbAsal.setStyle(inputStyle);

        ComboBox<String> cbTujuan = new ComboBox<>(FXCollections.observableArrayList("Yogyakarta", "Semarang", "Bali"));
        cbTujuan.setPromptText("Pilih Stasiun Tujuan");
        cbTujuan.setMaxWidth(Double.MAX_VALUE);
        cbTujuan.setStyle(inputStyle);

        DatePicker dpBerangkat = new DatePicker();
        dpBerangkat.setPromptText("mm/dd/yyyy");
        dpBerangkat.setMaxWidth(Double.MAX_VALUE);
        dpBerangkat.setStyle(inputStyle);

        Button btnCari = new Button("Cari Tiket");
        String normalStyle = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; " +
                             "-fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 12 0; -fx-cursor: hand;";
        String hoverStyle = "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 16px; " +
                            "-fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 12 0; -fx-cursor: hand;";
        
        btnCari.setStyle(normalStyle);
        btnCari.setMaxWidth(Double.MAX_VALUE);
        btnCari.setOnMouseEntered(e -> btnCari.setStyle(hoverStyle));
        btnCari.setOnMouseExited(e -> btnCari.setStyle(normalStyle));
        btnCari.setOnAction(e -> onSearch.run());

        card.getChildren().addAll(
            cardTitle,
            createLabel("Stasiun Asal"), cbAsal,
            createLabel("Stasiun Tujuan"), cbTujuan,
            createLabel("Tanggal Berangkat"), dpBerangkat,
            new Region() {{ setMinHeight(10); }},
            btnCari
        );

        mainLayout.getChildren().addAll(leftBox, card);
        
        // Tambahkan layout utama ke StackPane
        this.getChildren().add(mainLayout);

        // --- 5. LOGO TIX-GO DI POJOK KIRI ATAS ---
        try {
            String logoPath = getClass().getResource("/images/logo_tixgo.png").toExternalForm();
            Image imgLogo = new Image(logoPath);
            ImageView logoView = new ImageView(imgLogo);
            
            logoView.setFitWidth(120);
            logoView.setPreserveRatio(true);
            
            HBox logoWrapper = new HBox(logoView);
            logoWrapper.setPadding(new Insets(25)); 
            logoWrapper.setAlignment(Pos.TOP_LEFT);
            logoWrapper.setPickOnBounds(false); 
            
            // Menumpuk logo di atas mainLayout
            this.getChildren().add(logoWrapper);
            
        } catch (Exception e) {
            System.err.println("Logo tidak ditemukan: Pastikan file 'logo_tixgo.jpg' ada di resources/images/");
        }
    }

    private Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
        return lbl;
    }
}