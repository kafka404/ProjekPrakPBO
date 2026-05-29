/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;
import javax.swing.table.AbstractTableModel;
import java.util.List;
/**
 *
 * @author USER
 */
public class ModelTable extends AbstractTableModel{
    List<Object> DaftarObat;
    String kolom[] = {"ID", "Nama Obat", "Harga", "Stok", "Jenis"};
    
    // Constructor
    public ModelTable(List<Object> DaftarObat) {
        this.DaftarObat = DaftarObat;
    }
    
    @Override
    public int getRowCount() {
        return DaftarObat.size();
    }
    
    @Override
    public int getColumnCount() {
        return kolom.length;
    }
    
    // Untuk Merubah Data ArrayList Ke Dalam Bentuk Objek
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelObat obat = (ModelObat) DaftarObat.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                return obat.getIdObat();
            case 1:
                return obat.getNamaObat();
            case 2:
                return "Rp " + String.format("%,d", obat.getHarga());
            case 3:
                return obat.getStok();
            case 4:
                return obat.getJenis().equals("resep") ? "Obat Resep" : "Obat Biasa";
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        // Akan Mengembalikan Nama Kolom
        return kolom[columnIndex];
    }
}
