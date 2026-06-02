package Model.Medify;

import Model.Connector;
import java.sql.*;
import java.util.*;

public class DAOObat implements InterfaceDAO {
    
    @Override
    public void insert(Object object){
        ModelObat obat = (ModelObat) object;
        try{
            String query = "INSERT INTO obat (nama_obat, harga, stok, jenis) VALUES (?,?,?,?)";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, obat.getNamaObat());
            statement.setInt(2, obat.getHarga());
            statement.setInt(3, obat.getStok());
            statement.setString(4, obat.getJenis());
            statement.executeUpdate();
            
            
            System.out.println("Obat '" + obat.getNamaObat() + "' berhasil ditambahkan!");
            
        }catch(Exception exception){
            System.out.println("Gagal: " + exception.getMessage());
        }
    }

    @Override
    public void update(Object object){
        ModelObat obat = (ModelObat) object;
        try{
            String query = "UPDATE obat SET nama_obat=?, harga=?, stok=?, jenis=? WHERE id_obat=?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, obat.getNamaObat());
            statement.setInt(2, obat.getHarga());
            statement.setInt(3, obat.getStok());
            statement.setString(4, obat.getJenis());
            statement.setInt(5, obat.getIdObat());
            
            int result = statement.executeUpdate();
            
            
            if (result > 0) {
                System.out.println("Obat ID " + obat.getIdObat() + " (" + obat.getNamaObat() + ") berhasil diupdate!");
            } else {
                System.out.println("Obat ID " + obat.getIdObat() + " tidak ditemukan!");
            }
            
        }catch(Exception exception){
            System.out.println("Gagal: " + exception.getMessage());
        }
    }

    @Override
    public void delete(int id){
        try{
            // Cek dulu ada ga datanya
            ModelObat obat = (ModelObat) getById(id);
            String namaObat = (obat != null) ? obat.getNamaObat() : "Unknown";
            
            String query = "DELETE FROM obat WHERE id_obat=?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);           
            int result = statement.executeUpdate();
            
            
            if (result > 0) {
                System.out.println("Obat ID " + id + " (" + namaObat + ") berhasil dihapus!");
            } else {
                System.out.println("Obat ID " + id + " tidak ditemukan!");
            }
            
        }catch(Exception exception){
            System.out.println("Gagal: " + exception.getMessage());
        }
    }

    @Override
    public Object getById(int id){
        ModelObat obat = null;
        try{
            String query = "SELECT * FROM obat WHERE id_obat=?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                String jenis = result.getString("jenis");
                if(jenis.equalsIgnoreCase("resep")){
                    obat = new ObatResep(
                        result.getInt("id_obat"), result.getString("nama_obat"), 
                        result.getInt("stok"), result.getInt("harga"));
                }else{
                    obat = new ObatBiasa(
                        result.getInt("id_obat"), result.getString("nama_obat"), 
                        result.getInt("stok"), result.getInt("harga"));
                }
            }
        }catch(Exception exception){
            System.out.println("Gagal: " + exception.getMessage());
        }
        return obat;
    }

    @Override
    public List<Object> getAll(){
        List<Object> listObat = new ArrayList<>();
        try{
            Statement statement = Connector.Connect().createStatement();
            String query = "SELECT * FROM obat";
            ResultSet result = statement.executeQuery(query);
            
            while(result.next()){
                String jenis = result.getString("jenis");
                ModelObat obat;
                if(jenis.equalsIgnoreCase("resep")){
                    obat = new ObatResep(
                        result.getInt("id_obat"), result.getString("nama_obat"), 
                        result.getInt("stok"), result.getInt("harga"));
                }else{
                    obat = new ObatBiasa(
                        result.getInt("id_obat"), result.getString("nama_obat"), 
                        result.getInt("stok"), result.getInt("harga"));
                }
                listObat.add(obat);
            }
            
            
            System.out.println("Menampilkan " + listObat.size() + " data obat dimuat dari database");
            
        }catch(Exception exception){
            System.out.println("Gagal: " + exception.getMessage());
        }
        return listObat;
    }
    
    public void updateStok(int idObat, int stokBaru){
        try{
            String query = "UPDATE obat SET stok=? WHERE id_obat=?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, stokBaru);
            statement.setInt(2, idObat);
            int result = statement.executeUpdate();
            
            
            if (result > 0) {
                System.out.println("ID " + idObat + " stok menjadi " + stokBaru);
            } else {
                System.out.println("ID " + idObat + " tidak ditemukan!");
            }
            
        }catch(Exception exception){
            System.out.println("Gagal: " + exception.getMessage());
        }
    }
}
