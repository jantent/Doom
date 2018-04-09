package hashmap;

import java.util.HashMap;

public class HashMapTest {
    public static void main(String args[]){
        HashMap<String,Object> map = new HashMap<>();
        String key = "map";
        for (int i=0;i<500000000;i++){
            map.put(key+i,i);
        }
        System.out.println(map.get("map1000"));
    }
}
