/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;

/**
 *
 * @author USER
 */
public class ObatResep extends ModelObat {
    private int harga;
    
    public ObatResep(int idObat , String namaObat , int stok , int harga){
        super(idObat , namaObat , stok);
        this.harga = harga;
    }
    
    @Override 
    public int getHarga(){
        return harga;
    }
    
    @Override
    public double getDiskon(){
        return 10; // Obat Resep Dapet Diskon 10%
    }
    
    @Override 
    public String getJenis(){
       return "Resep";
    }
    
    public void setHarga(int harga){
        this.harga = harga;
    }
}
