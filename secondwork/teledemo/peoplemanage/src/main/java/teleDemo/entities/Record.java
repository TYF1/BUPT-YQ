package teleDemo.entities;

import java.sql.Date;

public class Record {
    private int id;
    private int user_id;
    private String pre_status;
    private String cur_status;
    private Date update_time;

    public Record() {
    }

    public Record(int id, int user_id, String pre_status, String cur_status, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.pre_status = pre_status;
        this.cur_status = cur_status;
        this.update_time = update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPre_status() {
        return pre_status;
    }

    public void setPre_status(String pre_status) {
        this.pre_status = pre_status;
    }

    public String getCur_status() {
        return cur_status;
    }

    public void setCur_status(String cur_status) {
        this.cur_status = cur_status;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
