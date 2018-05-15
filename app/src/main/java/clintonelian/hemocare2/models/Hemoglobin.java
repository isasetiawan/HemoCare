package clintonelian.hemocare2.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Hemoglobin extends RealmObject{

//    @PrimaryKey
    private String idHb; //start from 1000


    private String name;
    private String date;
    private float hb;
    private String idAccount; //start from 100000


    public String getIdHb() {
        return idHb;
    }

    public void setIdHb(String idHb) {
        this.idHb = idHb;
    }

    public float getHb() {
        return hb;
    }

    public void setHb(float hb) {
        this.hb = hb;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}