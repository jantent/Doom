package hashmap;

public class Entry<K,V> {
    final K key;
    V value;
    // 下一个节点
    Entry<K,V> next;

    public Entry(K key,V v,Entry<K,V> next) {
        this.key = key;
        this.value = v;
        this.next = next;
    }

    public final K getKey() {
        return key;
    }

    public final V getValue() {
        return value;
    }

    public final V setValue(V newValue){
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    public final boolean equals(Object o){
        if (!(o instanceof Entry)){
            return false;
        }
        Entry entry = (Entry)o;
        Object k1 = getKey();
        Object k2 = entry.getKey();
        if (k1 == k2 || (k1!=null && k1.equals(k2))){
            Object v1 = getValue();
            Object v2 = entry.getKey();
            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    public final int hashCode(){
        return (key == null ? 0:key.hashCode())^(value == null?0:value.hashCode());
    }
    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
