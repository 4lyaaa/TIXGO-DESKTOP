package com.tixgo.desktop;

import com.tixgo.desktop.view.BuktiPemesananView;
import com.tixgo.desktop.view.CariJadwalView;
import com.tixgo.desktop.view.DashboardView;
import com.tixgo.desktop.view.DetailPemesananView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DesktopApplication extends Application {
    private StackPane root; 

    @Override
    public void start(Stage primaryStage) {
        // Inisialisasi container utama (Layer dasar aplikasi)
        root = new StackPane();
        
        // --- PERUBAHAN: Memunculkan Dashboard sebagai halaman awal ---
        showDashboardView(); 

        // Membuat Scene tanpa ukuran statis agar fleksibel (Responsive)
        Scene scene = new Scene(root);
        
        // --- Pengaturan Stage (Jendela Aplikasi) ---
        primaryStage.setTitle("TIX-GO - Pemesanan Tiket Kereta");
        
        // Batas minimal ukuran agar UI tetap rapi seperti tampilan mobile
        primaryStage.setMinWidth(420); 
        primaryStage.setMinHeight(650);
        
        // Ukuran default saat aplikasi pertama kali dibuka (Desktop view)
        primaryStage.setWidth(1000);
        primaryStage.setHeight(750);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- FITUR BARU: Navigasi ke Landing Page / Dashboard ---
    public void showDashboardView() {
        // Mengirimkan referensi ke showJadwalView agar tombol 'Cari' bisa berpindah halaman
        root.getChildren().setAll(new DashboardView(this::showJadwalView));
    }

    // Navigasi ke halaman tabel hasil jadwal
    public void showJadwalView() {
        // Mengirimkan reference method showDetailView sebagai callback tombol 'Pesan'
        root.getChildren().setAll(new CariJadwalView(this::showDetailView));
    }

    // Navigasi ke halaman form input detail pemesan
    public void showDetailView() {
        // DetailPemesananView menerima callback untuk tombol 'Kembali' dan 'Bayar'
        DetailPemesananView view = new DetailPemesananView(
            this::showJadwalView, 
            this::showBuktiView
        );
        root.getChildren().setAll(view);
    }

    // Navigasi ke halaman bukti/struk pemesanan sukses
    public void showBuktiView() {
        // Tombol 'Home' di BuktiPemesanan akan mengarah kembali ke Dashboard awal
        root.getChildren().setAll(new BuktiPemesananView(this::showDashboardView));
    }

    public static void main(String[] args) {
        launch(args); 
    }
}