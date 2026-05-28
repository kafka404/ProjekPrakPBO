/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Medify;
import java.util.List;
/**
 *
 * @author USER
 */
public interface InterfaceDAO {
    public void insert(Object obj);
    public void update(Object obj);
    public void delete(int id);
    public Object getById(int id);
    public List<Object> getAll();
}
