//Accessing ItemDB
package dbo;
public class ItemDBAccess extends DBAccess{
    final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final String url = "jdbc:sqlserver://localhost:1433;" + "databasename=ItemDB; user=sa; password=123";

    public ItemDBAccess() {
        super();
        connectDB(driver,url);
    }
    
    
}
