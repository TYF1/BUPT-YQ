package teleDemo.entities;

import java.io.Serializable;
import java.sql.Date;

public class Date1 implements Serializable {
    private Date date;
    public Date1(Date date){
        this.date = date;
    }
    public Date1(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
