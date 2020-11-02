package model;

public class ViewType {
    private String typeName;
    private String proCode;

    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String toString(){
        return this.typeName;
    }

}
