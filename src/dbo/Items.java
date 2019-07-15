//Item List
package dbo;

import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class Items extends Vector<Item> {

    final int SUPPLYING = 1;
    final int NOTSUPPLYING = 2;

    public Items() {
        super();
    }

    public int find(String itemCode) {
        for (int i = 0; i < this.size(); i++) {
            if (itemCode.equals(this.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public Item findItem(String itemCode) {
        int i = find(itemCode);
        return i < 0 ? null : this.get(i);
    }

    public void loadFromDB(ItemDBAccess dbObj, Suppliers suppliers, int supply) {
        String itemCode, itemName, supplierCode, unit;
        int price; boolean supplying;
        String sql = "";
        if(supply == SUPPLYING) sql = "Select * from Items where supplying=true";
        else if(supply == NOTSUPPLYING) sql = "Select * from Items where supplying=false";
        else sql = "Select * from Items";
        try {
            ResultSet rs = dbObj.executeQuery(sql);
            while(rs.next()){
                itemCode = rs.getString(1);
                itemName = rs.getString(2);
                supplierCode = rs.getString(3);
                Supplier supplier = suppliers.findSupplier(supplierCode);
                unit = rs.getString(4);
                price = rs.getInt(5);
                supplying = rs.getBoolean(6);
                Item newItem = new Item(itemCode, itemName, supplier, unit, price, supplying);
                this.add(newItem);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
