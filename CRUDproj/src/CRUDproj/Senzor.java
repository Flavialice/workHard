package CRUDproj;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Senzor {
    private String id;
    private String tip;
    private float valoare;
    private String ultimul_vazut;
    private String status;

    public Senzor() {
    }

    public Senzor(String id, String tip, float valoare, String ultimul_vazut, String status) {
        this.id = id;
        this.tip = tip;
        this.valoare = valoare;
        this.ultimul_vazut = ultimul_vazut;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public String getUltimul_vazut() {
        return ultimul_vazut;
    }

    public void setUltimul_vazut(String ultimul_vazut) {
        this.ultimul_vazut = ultimul_vazut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
