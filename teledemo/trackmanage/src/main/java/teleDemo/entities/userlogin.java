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
public class UserLogin implements Serializable {

    private int userID;
    private String userName;
    private String password;
    private int role;

    public UserLogin(int userID, String userName, String password, int role) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}
