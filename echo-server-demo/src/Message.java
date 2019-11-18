import java.io.Serializable;

public class Message implements Serializable {
    private String str;
    private Integer num;
    public Message(String str, Integer num) {
        this.str = str;
        this.num = num;
    }
    public String getStr() {
        return this.str;
    }
    public Integer getNum() {
        return this.num;
    }

    @Override
    public String toString() {
        return "This Message contains a String " + this.str + " and an Integer " + this.num;
    }
}