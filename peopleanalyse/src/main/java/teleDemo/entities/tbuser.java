package teleDemo.entities;

import java.io.Serializable;
import java.sql.Date;

public class tbuser implements Serializable {
    private int id;
    private String phoneNumber;
    private String status;
    private Date date;
    private int source;

    public tbuser(int id,String phoneNumber,String status){
        this.id=id;
        this.phoneNumber=phoneNumber;
        this.status=status;
    }
    public tbuser(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public int getSource() {
        return source;
    }
    public void setSource(int source) {
        this.source = source;
    }
}
