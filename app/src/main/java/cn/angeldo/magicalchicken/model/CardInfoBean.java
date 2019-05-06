package cn.angeldo.magicalchicken.model;

public class CardInfoBean {


    /**
     * blueball : 2
     * created : 2019-05-06 16:08:50
     * type : 1
     * mark : 双色球
     * redball : 6, 12, 14, 16, 21, 23
     */

    private String blueball;
    private String created;
    private int type;
    private String mark;
    private String redball;
    private String charstr;

    public String getCharstr() {
        return charstr;
    }

    public void setCharstr(String charstr) {
        this.charstr = charstr;
    }

    public String getBlueball() {
        return blueball;
    }

    public void setBlueball(String blueball) {
        this.blueball = blueball;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRedball() {
        return redball;
    }

    public void setRedball(String redball) {
        this.redball = redball;
    }

    @Override
    public String toString() {
        return "CardInfoBean{" +
                "blueball='" + blueball + '\'' +
                ", created='" + created + '\'' +
                ", type=" + type +
                ", mark='" + mark + '\'' +
                ", redball='" + redball + '\'' +
                ", charstr='" + charstr + '\'' +
                '}';
    }
}
