/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;
import java.sql.Timestamp;
/**
 *
 * @author USER
 */
public class ModelTransaksi {
    private int idTransaksi;
    private String namaCustomer;
    private String namaObat;
    private String jenisObat;
    private int jumlahBeli;
    private int hargaSatuan;
    private int totalBayar;
    private Timestamp tanggal;
    
    // Constructor Default
    public ModelTransaksi() {
        
    }
    
    // Constructor dengan parameter
    public ModelTransaksi(int idTransaksi, String namaCustomer, String namaObat, 
                          String jenisObat, int jumlahBeli, int hargaSatuan, 
                          int totalBayar, Timestamp tanggal) {
        this.idTransaksi = idTransaksi;
        this.namaCustomer = namaCustomer;
        this.namaObat = namaObat;
        this.jenisObat = jenisObat;
        this.jumlahBeli = jumlahBeli;
        this.hargaSatuan = hargaSatuan;
        this.totalBayar = totalBayar;
        this.tanggal = tanggal;
    }
    
    // Getter dan Setter
    public int getIdTransaksi() {
        return idTransaksi;
    }
    
    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }
    
    public String getNamaCustomer() {
        return namaCustomer;
    }
    
    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }
    
    public String getNamaObat() {
        return namaObat;
    }
    
    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }
    
    public String getJenisObat() {
        return jenisObat;
    }
    
    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
    }
    
    public int getJumlahBeli() {
        return jumlahBeli;
    }
    
    public void setJumlahBeli(int jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
    }
    
    public int getHargaSatuan() {
        return hargaSatuan;
    }
    
    public void setHargaSatuan(int hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }
    
    public int getTotalBayar() {
        return totalBayar;
    }
    
    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }
    
    public Timestamp getTanggal() {
        return tanggal;
    }
    
    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }
}
