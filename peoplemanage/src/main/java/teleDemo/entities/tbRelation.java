package teleDemo.entities;

import java.io.Serializable;

public class tbRelation implements Serializable {
    private int user_id;
    private int relation;
    private String status;

    public tbRelation(int user_id, int relation, String status){
        this.user_id=user_id;
        this.relation=relation;
        this.status=status;
    }
    public tbRelation(){}

    public int getUserId() {
            return user_id;
        }

        public void setUserId(int user_id) {
            this.user_id = user_id;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
    }
}
