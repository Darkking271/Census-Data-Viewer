/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hash;

/**
 *
 * @author Alex <your.name at your.org>
 */
public class HashTable
{
    Entry[] tab;
    int count;
    
    public HashTable()
    {
        tab = new Entry[16];
        count = 0;
    }
    
    static class Entry
    {
        String key;
        Object value;
        Entry next;
        int hash;
        Entry(String k, Object v, Entry p, int h)
        {
            key = k;
            value = v;
            next = p;
            hash = h;
        }
    }
    
    public boolean ContainsKey(String key)
    {
        int h = key.hashCode();
        Entry[] T = tab;
        int i = h & (T.length);
        for (Entry e = T[i]; e != null; e = e.next)
        {
            if (e.hash == h && key.equals(e.key))
                return true;
        }
        return false;
    }//END ContainsKey
    
    public void put(String key, Object value)
    {
        int h = key.hashCode();
        Entry[] T = tab;
        int i = h & (T.length);
        for (Entry e = T[i]; e != null; e = e.next)
        {
            if (e.hash == h && key.equals(e.key))
            {
                e.value = value;
                return;
            }
        }
        
        Entry p = new Entry(key, value, T[i], h);
        T[i] = p;
        int c = count++;
        int n = T.length;
        if (c < (n - (n >>> 2)))
            return;
        
        int newN = n << 1;
        Entry[] newTab = new Entry[newN];
        for (int x = 0; x < n; x++)
        {
            Entry e;
            while ((e = T[x]) != null)
            {
                T[i] = e.next;
                int newH = e.hash & (newN - 1);
                e.next = newTab[i];
                newTab[i] = e;
            }
        }
    }//END put
    
    public void remove(String key)
    {
        int h = key.hashCode();
        Entry[] t = tab;
        int i = h & (t.length);
        Entry pred = null;
        Entry p  = t[i];
        while (p != null)
        {
            /*if (p.hash == null)
            {
                if (pred == null)
                    t[i] = p.next;
                else
                    pred.next = p.next;
            }
            --count;
            pred = p;
            p = p.next;*/
        }
    }
}
