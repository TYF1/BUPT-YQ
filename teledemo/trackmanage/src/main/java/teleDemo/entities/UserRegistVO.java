package teleDemo.entities;
/**
 * @Project Name:trackmanage
 * @File Name: UserRegistVO
 * @Description: 用于接收tbuser数据库数据
 * @ HISTORY：
 *    Created   2022.8.23  ZNY
 *    Modified  2022.8.25  WYJ
 */
public class UserRegistVO<T> {
    public String userName;
    public String password;
    public int result;
    public String msg;

    public UserRegistVO(String userName, String password, int result, String msg){
        this.userName=userName;
        this.password=password;
        this.result=result;
        this.msg=msg;
    }
    public UserRegistVO(){}

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public String getPassword(){return password;}

    public void setPassword(String password) {this.password = password;}
//
//    public String getPhone_number(){return phone_number;}
//
//    public void setPhone_number(String phone_number) {this.phone_number = phone_number;}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
