package hashmap;

public class MyHashMap<K,V> {
    //Entry数组表
    private Entry[] table;
    //默认数组长度
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    private int size;
    //构造函数
    public MyHashMap() {
        table = new Entry[DEFAULT_INITIAL_CAPACITY];
        size = DEFAULT_INITIAL_CAPACITY;
    }
    //获取数组长度
    public int getSize() {
        return size;
    }

    // 求index
    static int indexFor(int h, int length) {
        return h % (length - 1);
    }

    //获取value
    public V get(Object key){
        if (key == null){
            return null;
        }
        // 获取key的hash值
        int hash = key.hashCode();
        // 求key在数组中的下标
        int index = indexFor(hash,table.length);
        for (Entry<K,V> e = table[index];e!=null;e=e.next){
            Object k = e.key;
            if (e.key.hashCode()==hash && (k==key || k.equals(k))){
                return e.value;
            }
        }
        return null;
    }

    // 添加元素
    public V put(K key,V value){
        if (key == null){
            return null;
        }
        int hash = key.hashCode();
        int index = indexFor(hash,table.length);

        // 如果添加的key已经存在，那么只需要修改value值即可
        for (Entry<K,V>e = table[index];e!=null;e= e.next){
            Object k = e.key;
            if (e.key.hashCode() == hash && (k == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;// 原来的value值
            }
        }
        // 如果key值不存在，那么需要添加
        Entry<K,V> e = table[index];
        table[index] = new Entry<K, V>(key, value, e);// 新建一个Entry，并将其指向原先的e
        return null;
    }

}
