/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.Connector;
import Model.Medify.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author USER
 */
public class ControllerTransaksi {
    private ControllerObat controllerObat;
    
    public ControllerTransaksi() {
        controllerObat = new ControllerObat();
    }

    // Proses Penjualan - Mengembalikan data struk (tanpa print langsung)
    public StrukData prosesPenjualan(String namaCustomer, int idObat, int jumlah) throws Exception {
        // Validasi input
        if (namaCustomer.trim().isEmpty()) {
            throw new Exception("Nama customer tidak boleh kosong!");
        }
        if (jumlah <= 0) {
            throw new Exception("Jumlah beli harus lebih dari 0!");
        }
        
        // Ambil data obat
        ModelObat obat = controllerObat.getById(idObat);
        if (obat == null) {
            throw new Exception("Obat tidak ditemukan!");
        }
        
        // Cek stok
        if (obat.getStok() < jumlah) {
            throw new Exception("Stok tidak cukup! Sisa stok: " + obat.getStok());
        }
        
        // Hitung total (dengan diskon dari polymorphism)
        int hargaSetelahDiskon = obat.getHargaSetelahDiskon();
        int totalBayar = hargaSetelahDiskon * jumlah;
        
        // Kurangi stok
        controllerObat.kurangiStok(idObat, jumlah);
        
        // Simpan transaksi ke database
        String query = "INSERT INTO transaksi (nama_customer, nama_obat, jenis_obat, jumlah_beli, harga_satuan, total_bayar) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, namaCustomer);
            statement.setString(2, obat.getNamaObat());
            statement.setString(3, obat.getJenis());
            statement.setInt(4, jumlah);
            statement.setInt(5, obat.getHarga());
            statement.setInt(6, totalBayar);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Gagal menyimpan transaksi: " + e.getMessage());
        }
        
        // Kembalikan data struk (biar View yang nampilin)
        return new StrukData(namaCustomer, obat, jumlah, totalBayar);
    }
    
    // Inner class untuk membungkus data struk
    public static class StrukData {
        public String namaCustomer;
        public String namaObat;
        public String jenisObat;
        public int hargaSatuan;
        public double diskon;
        public int hargaSetelahDiskon;
        public int jumlahBeli;
        public int totalBayar;
        
        public StrukData(String namaCustomer, ModelObat obat, int jumlah, int totalBayar) {
            this.namaCustomer = namaCustomer;
            this.namaObat = obat.getNamaObat();
            this.jenisObat = obat.getJenis().equals("resep") ? "Obat Resep (Diskon 10%)" : "Obat Biasa";
            this.hargaSatuan = obat.getHarga();
            this.diskon = obat.getDiskon();
            this.hargaSetelahDiskon = obat.getHargaSetelahDiskon();
            this.jumlahBeli = jumlah;
            this.totalBayar = totalBayar;
        }
        
        // Method untuk membuat teks struk
        public String toStrukString() {
            StringBuilder sb = new StringBuilder();
            sb.append("╔════════════════════════════════════════╗\n");
            sb.append("║           APOTEK SEHAT                 ║\n");
            sb.append("╠════════════════════════════════════════╣\n");
            sb.append(String.format("║ Customer  : %-25s ║\n", namaCustomer));
            sb.append(String.format("║ Obat      : %-25s ║\n", namaObat));
            sb.append(String.format("║ Jenis     : %-25s ║\n", jenisObat));
            sb.append("╠════════════════════════════════════════╣\n");
            sb.append(String.format("║ Harga     : Rp %,10d      ║\n", hargaSatuan));
            if (diskon > 0) {
                sb.append(String.format("║ Diskon    : %d%%                      ║\n", (int)diskon));
                sb.append(String.format("║ Harga Akhir: Rp %,10d      ║\n", hargaSetelahDiskon));
            }
            sb.append(String.format("║ Jumlah    : %d x                    ║\n", jumlahBeli));
            sb.append("╠════════════════════════════════════════╣\n");
            sb.append(String.format("║ TOTAL     : Rp %,10d      ║\n", totalBayar));
            sb.append("╠════════════════════════════════════════╣\n");
            sb.append("║         TERIMA KASIH!                  ║\n");
            sb.append("╚════════════════════════════════════════╝\n");
            return sb.toString();
        }
    }
    
    // Get All Transaksi (untuk riwayat admin)
    public List<ModelTransaksi> getAllTransaksi() {
        List<ModelTransaksi> listTransaksi = new ArrayList<>();
        try {
            Statement statement = Connector.Connect().createStatement();
            String query = "SELECT * FROM transaksi ORDER BY tanggal DESC";
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                ModelTransaksi transaksi = new ModelTransaksi();
                transaksi.setIdTransaksi(rs.getInt("id_transaksi"));
                transaksi.setNamaCustomer(rs.getString("nama_customer"));
                transaksi.setNamaObat(rs.getString("nama_obat"));
                transaksi.setJenisObat(rs.getString("jenis_obat"));
                transaksi.setJumlahBeli(rs.getInt("jumlah_beli"));
                transaksi.setHargaSatuan(rs.getInt("harga_satuan"));
                transaksi.setTotalBayar(rs.getInt("total_bayar"));
                transaksi.setTanggal(rs.getTimestamp("tanggal"));
                listTransaksi.add(transaksi);
            }
        } catch (Exception e) {
            System.out.println("Get All Transaksi Gagal: " + e.getMessage());
        }
        return listTransaksi;
    }
}
