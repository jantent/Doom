package kafka;

public class OrderMessage {

    // 订单ID
    private String id;

    // 商家名称
    private String sName;

    // 创建时间
    private long createTime;

    // 备注
    private String remake;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "id='" + id + '\'' +
                ", sName='" + sName + '\'' +
                ", createTime=" + createTime +
                ", remake='" + remake + '\'' +
                '}';
    }
}
