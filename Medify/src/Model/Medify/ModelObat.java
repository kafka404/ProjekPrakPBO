/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;

/**
 *
 * @author USER
 */
public abstract class ModelObat {
    // Kelas Abstract
    
    private int idObat;
    private String namaObat;
    private int stok;
    
    // Constructor
    public ModelObat(int idObat , String namaObat , int stok){
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.stok = stok;
    }
    
    // Abstract Method Untuk Kelas Anak
    public abstract int getHarga();
    public abstract double   getDiskon();
    public abstract String getJenis();
    
     // Method Untuk Harga Obat Setelah Diskon
    public int getHargaSetelahDiskon(){
        return (int)(getHarga() * (1 - getDiskon()/100));
    }
    
    // Getter dan Setter
    public int getIdObat(){
        return idObat;
    }
    
    public void setIdObat(int idObat){
        this.idObat = idObat;
    }
    
    public String getNamaObat(){
        return namaObat;
    }
    
    public void setNamaObat(String namaObat){
        this.namaObat = namaObat;
    } 
    
    public int getStok(){
        return stok;
    }
    
    public void setStok(int stok){
        this.stok = stok;
    }
}
