package hashmap;

public class MyHashMapDemo {
    public static void main(String args[]){
        MyHashMap<String,String> map = new MyHashMap<>();
        map.put("aaa","aaa");
        map.put("bbb","bbb");
        System.out.println(map.get("aaa"));
        System.out.println(map.get("bbb"));
    }
}
