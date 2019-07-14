//Item List
package dbo;
import java.util.*;
import java.sql.*;

public class Items extends Vector<Item>{
    final int SUPPLYING = 1;
    final int NOTSUPPLYING = 2;

    public Items() {
        super();
    }
    
    public int find (String itemCode){
        for (int i = 0; i < this.size(); i++) {
            if(itemCode.equals(this.get(i).getItemCode()))
                return i;
        }
        return -1;
    }
    
    public Item findItem(String itemCode){
        int i = find(itemCode);
        return i<0? null : this.get(i);
    }
    
    public void loadFromDB(ItemDBAccess dbObj, Suppliers suppliers, int supply){
        
    }
    
    
}
