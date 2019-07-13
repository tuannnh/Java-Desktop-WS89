//Supplier List
package dbo;

import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class Suppliers extends Vector<Supplier> {

    public Suppliers() {
        super();
    }

    public int find(String supCode) {
        for (int i = 0; i < this.size(); i++) {
            if (supCode.equals(this.get(i).getSupCode())) {
                return i;
            }
        }
        return -1;
    }
    
    public Supplier findSupplier(String supCode){
        int i = find(supCode);
        return i < 0? null : this.get(i);
    }
    
    public void loadFromDB(ItemDBAccess dbObj){
        String supCode, supName, address;
        boolean colloborating;
        //Get supplier list from table in DB
        String sql = "select * from Suppliers";
        try {
            ResultSet rs = dbObj.executeQuery(sql);
            while(rs.next()){
                supCode = rs.getString(1);
                supName = rs.getString(2);
                address = rs.getString(3);
                colloborating = rs.getBoolean(4);
                Supplier supplier = new Supplier(supCode, supName, address, colloborating);
                this.add(supplier);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
