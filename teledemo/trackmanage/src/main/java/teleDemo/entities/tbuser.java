package teleDemo.entities;

import java.io.Serializable;
/**
 * @Project Name:trackmanage
 * @File Name: TbInfo
 * @Description: 用于接收tbuser数据库数据
 * @ HISTORY：
 *    Created   2022.8.22  Tom
 *    Modified  2022.8.22  Tom
 */
public class TbUser implements Serializable {
    private int id;
    private String phoneNumber;
    private String status;

    public TbUser(int id, String phoneNumber, String status){
        this.id=id;
        this.phoneNumber=phoneNumber;
        this.status=status;
    }
    public TbUser(){}

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
}
