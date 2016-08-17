package adapters;

/**
 * Created by vineeth on 7/1/2016.
 */
public class Uom {



    private  String uonid;
    private  String uomname;


    public Uom() {
    }

    public Uom(String uonid, String uomname) {
        this.uonid = uonid;
        this.uomname = uomname;
    }


    public String getUonid() {
        return uonid;
    }

    public void setUonid(String uonid) {
        this.uonid = uonid;
    }

    public String getUomname() {
        return uomname;
    }

    public void setUomname(String uomname) {
        this.uomname = uomname;
    }
}
