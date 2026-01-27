module com.tixgo.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    // Gabungkan semua izin akses untuk package utama di sini
    opens com.tixgo.desktop to javafx.fxml, javafx.graphics;
    
    // Izin agar TableView bisa membaca property di class Jadwal
    opens com.tixgo.desktop.model to javafx.base; 
    
    // Izin untuk package view jika menggunakan FXML di masa depan
    opens com.tixgo.desktop.view to javafx.fxml;
   
    exports com.tixgo.desktop;
    exports com.tixgo.desktop.view;
}