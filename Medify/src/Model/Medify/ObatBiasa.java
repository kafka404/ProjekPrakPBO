/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;

/**
 *
 * @author USER
 */
public class ObatBiasa extends ModelObat {
    // Kelas Anak 
    private int harga;
    
    // Constructor 
    public ObatBiasa(int idObat , String namaObat , int stok , int harga){
        super(idObat , namaObat , stok); // Panggil Constructor Di Kelas Induk
        this.harga = harga;
    }
    
    @Override // Mewarisi Sifat Dan Perilaku Kelas Induk
    public int getHarga(){
        return harga;
    }
    
    @Override
    public double getDiskon(){
        return 0 ; // Obat Biasa Ga Dapet Diskon
    }
    
    @Override
    public String getJenis(){
        return "Biasa";
    }
    
    public void setHarga(int harga){
        this.harga = harga;
    }
}
