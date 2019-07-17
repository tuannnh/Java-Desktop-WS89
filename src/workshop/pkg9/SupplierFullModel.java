package workshop.pkg9;
import dbo.*;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class SupplierFullModel extends AbstractTableModel{
    Suppliers suppliers = null;

    public SupplierFullModel(Suppliers suppliers) {
        this.suppliers = suppliers;
    }

    public Suppliers getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Suppliers suppliers) {
        this.suppliers = suppliers;
    }
    

    @Override
    public int getRowCount() {
        return suppliers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
     @Override
    public String getColumnName(int column){
        String columnName = "";
        switch(column){
            case 0: columnName = "Code";break;
            case 1: columnName = "Name";break;
            case 2: columnName = "Address";break;
            case 3: columnName = "Colloborating";break;
        }
        return columnName;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Supplier sup = suppliers.get(rowIndex);
         Object obj = null;
        switch(columnIndex){
            case 0: obj = sup.getSupCode();break;
            case 1: obj = sup.getSupName();break;
            case 2: obj = sup.getAddress();break;
            case 3: obj = sup.isColloborating();break;
        }
        return obj;
    }
}
