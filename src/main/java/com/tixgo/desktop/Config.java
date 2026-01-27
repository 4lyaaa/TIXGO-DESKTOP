package com.tixgo.desktop;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
    //method untuk menghubungkan aplikasi ke database PostrgeSQL
    public static Connection connect() {
        try {
            // 1. Alamat database (Host: localhost, Port: 5432, Nama DB: db_tixgo)
            String url = "jdbc:postgresql://localhost:5432/db_tixgo";
            
            // 2. Kredensial PostgreSQL Anda
            String user = "postgres"; 
            String pass = "password_kamu"; // <-- GANTI DENGAN PASSWORD POSTGRES ANDA

            // 3. Mencoba membuka koneksi
            return DriverManager.getConnection(url, user, pass);
            
        } catch (Exception e) {
            // Menampilkan pesan error di terminal jika koneksi gagal
            System.err.println("Koneksi Database Gagal: " + e.getMessage());
            return null;
        }
    }
}