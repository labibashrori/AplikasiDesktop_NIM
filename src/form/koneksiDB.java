package form;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Wiwit Agus Triyanto
 */
public class koneksiDB {
    
    public static Connection setKoneksi(){  //membuat method setKoneksi untuk konfigurasi koneksi ke database
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //nama driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpus?user=root");   //nama host database dan username
            //System.out.println("koneksi berhasil");
        } catch (ClassNotFoundException e) {
            System.out.println("jdbc.Driver tidak ditemukan");  //menampilkan error ketika JDBC driver tidak ada
            System.exit(0); //menutup aplikasi
        } catch (SQLException ex){
            System.out.println("koneksi gagal: \n" + ex.toString());    //menampilkan error ketika koneksi (database/host/username) bermasalah
            System.exit(0); //menutup aplikasi
        }
        return conn;
    }
    
    public static int execute(String SQL) { //method execute untuk eksekusi query INSERT/UPDATE/DELETE
        int status = 0;
        Connection koneksi = setKoneksi();
        try {
            Statement st = koneksi.createStatement();
            status = st.executeUpdate(SQL);
        } catch (SQLException ex) {
            System.out.println("ERROR: \n" + ex.toString());
        }
        return status;
    }
    
    public static ResultSet executeQuery(String SQL) {  //method executeQuery untuk eksekusi query SELECT
        ResultSet rs = null;
        Connection koneksi = setKoneksi();
        try {
            Statement st = koneksi.createStatement();
            rs = st.executeQuery(SQL);
        } catch (SQLException ex) {
            System.out.println("ERROR: \n" + ex.toString());
        }
        return rs;
    }
}
