//Table model
package workshop.pkg9;
import dbo.*;
import javax.swing.table.AbstractTableModel;

public class ItemFullModel extends AbstractTableModel
{
    
    Items items = null; 

    public ItemFullModel(Items items) {
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
    
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column){
        String columnName = "";
        switch(column){
            case 0: columnName = "Code";break;
            case 1: columnName = "Name";break;
            case 2: columnName = "Supplier";break;
            case 3: columnName = "Unit";break;
            case 4: columnName = "Price";break;
            case 5: columnName = "Supply";break;
        }
        return columnName;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = items.get(rowIndex);
        Object obj = null;
        switch(columnIndex){
            case 0: obj = item.getItemCode();break;
            case 1: obj = item.getItemName();break;
            case 2: obj = item.getSupplier().getSupCode();break;
            case 3: obj = item.getUnit();break;
            case 4: obj = item.getPrice();break;
            case 5: obj = item.isSupplying();break;
        }
        return obj;
    }
    
}
