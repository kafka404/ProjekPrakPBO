/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.Medify.DAOObat;
import Model.Medify.ModelObat;
import Model.Medify.ObatBiasa;
import Model.Medify.ObatResep;
import java.util.List;

/**
 *
 * @author USER
 */
public class ControllerObat {
    DAOObat dao;
    List<Object> list;
    
    public ControllerObat() {
        dao = new DAOObat();
    }
    
    // Tambah Obat
    public void insert(String namaObat , int harga , int stok , String jenis){
        ModelObat obat;
        if(jenis.equalsIgnoreCase("Resep")){
            obat = new ObatResep(0 , namaObat , stok , harga);
        }else{
            obat = new ObatBiasa(0 , namaObat , stok , harga);
        }
        dao.insert(obat);
    }
    
    // Update Obat
    public void update(int id, String namaObat , int harga , int stok , String jenis){
        ModelObat obat;
        if(jenis.equalsIgnoreCase("Resep")){
            obat = new ObatResep(id , namaObat , stok , harga);
        }else{
            obat = new ObatBiasa(id , namaObat , stok , harga);
        }
        dao.update(obat);
    }
    
    // Hapus Obat
    public void delete(int id){
        dao.delete(id);
    }
    
    // Tampilkan Semua Obat
    public List<Object> getAll() {
        return dao.getAll();
    }

    public ModelObat cariObat(String namaObat) {
    List<Object> list = dao.getAll();
    for (Object obj : list) {
        ModelObat o = (ModelObat) obj;
        if (o.getNamaObat().equalsIgnoreCase(namaObat)) {
            return o;
        }
    }
    return null;
    }
    
    // Cari Obat Berdasarkan ID
    public ModelObat cariObat(int id) {
        return (ModelObat) dao.getById(id);
    }
    
    // Update Stok
    public void KurangiStok(int idObat , int jumlahBeli) throws Exception{
        ModelObat obat = getById (idObat);
        if(obat == null){
            throw new Exception("Obat Tidak Ditemukkan!");
        }
        if(obat.getStok() < jumlahBeli){
            throw new Exception("Stok Tidak Cukup , Sisa Obat : " + obat.getStok());
        }
        dao.updateStok(idObat, obat.getStok() - jumlahBeli);
    }
}
    
