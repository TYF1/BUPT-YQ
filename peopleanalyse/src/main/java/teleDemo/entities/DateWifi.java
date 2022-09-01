package teleDemo.entities;

import java.io.Serializable;
import java.sql.Date;

public class DateWifi implements Serializable {
    private Date date;
    private String wifi;
    private int status;
    public DateWifi(Date date, String wifi, int status){
        this.date = date;
        this.wifi = wifi;
        this.status = status;
    }
    public DateWifi(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
