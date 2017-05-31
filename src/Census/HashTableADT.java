/*
 * This interface provides the necessary methods used in a proper hashtable
 */
package Census;
import java.util.Set;

/**
 *
 * @author Alex
 * @param <K>
 * @param <V>
 */
public interface HashTableADT<K, V> {
    public void put(K key, V value);
    public boolean contains(K key);
    public V get(K key);
    public Set<K> keySet();
    public Set<K> alphaSet();
    public int size();
    public boolean isEmpty();
    public void empty();
}
