/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;
import Model.Connector;
import java.sql.*;
import java.util.*;
/**
 *
 * @author USER
 */
public class DAOTransaksi implements InterfaceDAO{
    Connector connector = new Connector();
    
    @Override
    public void insert(Object obj) {
        ModelTransaksi transaksi = (ModelTransaksi) obj;
        try {
            String query = "INSERT INTO transaksi (nama_customer, nama_obat, jenis_obat, jumlah_beli, harga_satuan, total_bayar) VALUES (?,?,?,?,?,?)";
            
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, transaksi.getNamaCustomer());
            statement.setString(2, transaksi.getNamaObat());
            statement.setString(3, transaksi.getJenisObat());
            statement.setInt(4, transaksi.getJumlahBeli());
            statement.setInt(5, transaksi.getHargaSatuan());
            statement.setInt(6, transaksi.getTotalBayar());
            
            statement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Insert Transaksi Gagal: " + e.getMessage());
        }
    }
    
    @Override
    public void update(Object obj) {
        // Transaksi tidak perlu update (hanya insert dan delete)
        System.out.println("Method update tidak digunakan untuk Transaksi");
    }
    
    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM transaksi WHERE id_transaksi=?";
            
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);
            
            statement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Delete Transaksi Gagal: " + e.getMessage());
        }
    }
    
    @Override
    public Object getById(int id) {
        ModelTransaksi transaksi = null;
        try {
            String query = "SELECT * FROM transaksi WHERE id_transaksi=?";
            
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                transaksi = new ModelTransaksi();
                transaksi.setIdTransaksi(resultSet.getInt("id_transaksi"));
                transaksi.setNamaCustomer(resultSet.getString("nama_customer"));
                transaksi.setNamaObat(resultSet.getString("nama_obat"));
                transaksi.setJenisObat(resultSet.getString("jenis_obat"));
                transaksi.setJumlahBeli(resultSet.getInt("jumlah_beli"));
                transaksi.setHargaSatuan(resultSet.getInt("harga_satuan"));
                transaksi.setTotalBayar(resultSet.getInt("total_bayar"));
                transaksi.setTanggal(resultSet.getTimestamp("tanggal"));
            }
            
        } catch (Exception e) {
            System.out.println("Get Transaksi By Id Gagal: " + e.getMessage());
        }
        return transaksi;
    }
    
    @Override
    public List<Object> getAll() {
        List<Object> listTransaksi = new ArrayList<>();
        
        try {
            Statement statement = Connector.Connect().createStatement();
            String query = "SELECT * FROM transaksi ORDER BY tanggal DESC";
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                ModelTransaksi transaksi = new ModelTransaksi();
                transaksi.setIdTransaksi(resultSet.getInt("id_transaksi"));
                transaksi.setNamaCustomer(resultSet.getString("nama_customer"));
                transaksi.setNamaObat(resultSet.getString("nama_obat"));
                transaksi.setJenisObat(resultSet.getString("jenis_obat"));
                transaksi.setJumlahBeli(resultSet.getInt("jumlah_beli"));
                transaksi.setHargaSatuan(resultSet.getInt("harga_satuan"));
                transaksi.setTotalBayar(resultSet.getInt("total_bayar"));
                transaksi.setTanggal(resultSet.getTimestamp("tanggal"));
                listTransaksi.add(transaksi);
            }
            
        } catch (Exception e) {
            System.out.println("Get All Transaksi Gagal: " + e.getMessage());
        }
        return listTransaksi;
    }
    
    // Method tambahan untuk mencari transaksi berdasarkan nama customer
    public List<Object> getByCustomer(String namaCustomer) {
        List<Object> listTransaksi = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM transaksi WHERE nama_customer LIKE ? ORDER BY tanggal DESC";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, "%" + namaCustomer + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                ModelTransaksi transaksi = new ModelTransaksi();
                transaksi.setIdTransaksi(resultSet.getInt("id_transaksi"));
                transaksi.setNamaCustomer(resultSet.getString("nama_customer"));
                transaksi.setNamaObat(resultSet.getString("nama_obat"));
                transaksi.setJenisObat(resultSet.getString("jenis_obat"));
                transaksi.setJumlahBeli(resultSet.getInt("jumlah_beli"));
                transaksi.setHargaSatuan(resultSet.getInt("harga_satuan"));
                transaksi.setTotalBayar(resultSet.getInt("total_bayar"));
                transaksi.setTanggal(resultSet.getTimestamp("tanggal"));
                listTransaksi.add(transaksi);
            }
            
        } catch (Exception e) {
            System.out.println("Get Transaksi By Customer Gagal: " + e.getMessage());
        }
        return listTransaksi;
    }
    
    // Method untuk menghapus semua transaksi (reset)
    public void deleteAll() {
        try {
            String query = "DELETE FROM transaksi";
            Statement statement = Connector.Connect().createStatement();
            statement.executeUpdate(query);
            
        } catch (Exception e) {
            System.out.println("Delete All Transaksi Gagal: " + e.getMessage());
        }
    }
}
