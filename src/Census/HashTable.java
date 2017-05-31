/*
 * This class implements my own version of the hashTable interface
 */
package Census;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 *
 * @author Alex
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> implements HashTableADT<K, V>
{
    private Entry table[];
    private int count;
    private int limit;
    int hashMod;
    
    static class Entry
    {
        Object key;
        Object value;
        int hash;
        Entry next;
        Entry(Object k, Object v, int h, Entry e)
        {
            key = k;
            value = v;
            hash = h;
            next = e;
        }
    }
            
    public HashTable()
    {
        table = new Entry[32];
        count = 0;
        limit = 32;
        hashMod = limit - 1;
    }
    
    public HashTable(HashTable T)
    {
        count = T.size();
        limit = T.limit();
        hashMod = limit - 1;
        table = Arrays.copyOf(T.copy(), T.tableSize());
    }
    
    @Override
    public void put(K key, V value) 
    {
        Entry e = new Entry(key, value, key.hashCode(), null);
        
        if (count < limit / 2)
        {
            if (!contains(key))
            {
                int index = hashGen(e.hash);
                if (table[index] == null)
                {
                    table[index] = e;   
                    count++;
                }
                else
                {
                    Entry node = table[index];
                    while(node.next != null)
                    {
                        node = node.next;
                    }
                    node.next = e;
                    count++;
                }
            }
            else
                update(key, value);
        }
        else
        {
            if (!contains(key))
            {
                int index = hashGen(e.hash);
                shiftTable();
                if (table[index] == null)
                {
                    table[index] = e;
                    count++;
                }
                else
                {
                    Entry node = table[index];
                    while(node.next != null)
                    {
                        node = node.next;
                    }
                    node.next = e;
                    count++;
                }
                
            }
            else
                update(key, value);  
        }
    }
    
    @Override
    public boolean contains(K key)
    {
        int index = hashGen(key.hashCode());
        
        for (Entry e = table[index]; e != null; e = e.next)
        {
            if (hashGen(e.hash) == index && key == e.key)
                return true;
        }
        return false;
    }
    
    private void update(K key, V value) 
    {
        Entry e = new Entry(key, value, key.hashCode(), null);
        int index = hashGen(e.hash);
        
        for (Entry f = table[index]; f != null; f = f.next)
        {
            if (hashGen(f.hash) == index && key == f.key)
            {
                f.value = value;
                break;
            }
        }
    }

    private void shiftTable() 
    {
        limit *= 2;
        Entry[] emptyTable = new Entry[limit];
        for (int i = 0; i < limit / 2; i++)
        {
            if(table[i] != null)
            {
                emptyTable[hashGen(table[i].hash)] = table[i];
            }
        }
        table = Arrays.copyOf(emptyTable, limit);
    }

    public V get(K key) 
    {
        int index = hashGen(key.hashCode());
        
        for (Entry e = table[index]; e != null; e = e.next)
        {
            if (hashGen(e.hash) == index && e.key == key)
                return (V)e.value;
        }
        return null;
    }

    public Set<K> keySet()
    {
        Set<K> newSet = new LinkedHashSet<>();
        for (int i = 0; i < limit; i++)
        {
            for (Entry e = table[i]; e != null; e = e.next)
            {
                newSet.add((K)e.key);
            }
        }
        return newSet;
    }
    
    public Set<K> alphaSet()
    {
        Set<K> newSet = new TreeSet<>();
        for (int i = 0; i < limit; i++)
        {
            for (Entry e = table[i]; e != null; e = e.next)
            {
                newSet.add((K)e.key);
            }
        }
        return newSet;
    }

    public int size() 
    {
        return count;
    }
    
    public boolean isEmpty()
    {
        if (count == 0)
            return true;
        return false;
    }
    
    public void empty()
    {
        Entry[] newTab = new Entry[16];
        limit = 16;
        count = 0;
        table = Arrays.copyOf(newTab, limit);
    }

    private int hashGen(int hashCode) 
    {
        int newHash = hashCode % hashMod;
        newHash = Math.abs(newHash);
        return newHash;
    }
    
    private int limit()
    {
        return limit;
    }
    
    private Entry[] copy()
    {
        return table;
    }
    
    private int tableSize()
    {
        return table.length;
    }
}
