package kafka;

import java.util.HashMap;
import java.util.Map;

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


    public static void main(String args[]){
        OrderMessage orderMessageA = new OrderMessage();
        orderMessageA.setsName("aaaa");
        OrderMessage orderMessageB = new OrderMessage();
        orderMessageB.setsName("BBBB");
        Map<OrderMessage,String> hashMap = new HashMap<>();
        hashMap.put(orderMessageA,orderMessageA.getsName());
        hashMap.put(orderMessageB,orderMessageB.getsName());

        System.out.println(orderMessageA.hashCode());
        System.out.println(orderMessageB.hashCode());


        System.out.println(hashMap.get(orderMessageB));
    }
}
