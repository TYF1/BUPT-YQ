package teleDemo.entities;

import java.io.Serializable;

public class StatusCount implements Serializable {
    private int status;
    private int count;
    public StatusCount(int status, int count){
        this.status = status;
        this.count = count;
    }
    public StatusCount(){}

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
