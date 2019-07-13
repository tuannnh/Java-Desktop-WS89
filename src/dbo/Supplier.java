// a Supplier Object
package dbo;


public class Supplier {
    private String supCode="", supName="", address="";
    private boolean colloborating = true;

    public Supplier() {
    }
     public Supplier(String code, String name, String add, boolean colloborating) {
         this.supCode = code;
         this.supName = name;
         this.address = add;
         this.colloborating = colloborating;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isColloborating() {
        return colloborating;
    }

    public void setColloborating(boolean colloborating) {
        this.colloborating = colloborating;
    }

    @Override
    public String toString() {
            return supCode + "-" + supName;
    }
     
}
