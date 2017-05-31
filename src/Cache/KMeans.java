package Cache;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

/**
 * Created by Alex on 4/4/2017.
 */
public class KMeans<K> implements Serializable{

    static class Entry implements Serializable{
        Object key;
        int value;
        Entry(Object k, int v){
            key = k;
            value = v;
        }
        public String toString(){
            return key + ": " + value;
        }
    }

    private int k;
    private int total;
    private int count;
    private ArrayList<Entry> dataItems;
    private ArrayList<Integer> cz;
    private ArrayList<Integer> oldCz;
    private ArrayList<Integer> row;
    private ArrayList<ArrayList<Entry>> groups;

    public KMeans(int k, int total){
        this.k = k;
        this.total = total;
        this.count = 0;
        dataItems = new ArrayList<>();
        cz = new ArrayList<>();
        oldCz = new ArrayList<>();
        row = new ArrayList<>();
        groups = new ArrayList<>();
        for (int i = 0; i < k; i++){
            groups.add(new ArrayList<>());
        }
    }

    public void add(K key, int value){
        if (count < total){
            Entry e = new Entry(key, value);
            dataItems.add(e);
            if (count < k)
                cz.add(dataItems.get(count).value);
            count++;
        }
    }

    public void run(){
        int iter = 1;
        do {
            for (Entry aItem : dataItems) {
                for (int c : cz) {
                    row.add(abs(c - aItem.value));
                }
                groups.get(row.indexOf(Collections.min(row))).add(aItem);
                row.removeAll(row);
            }
            for (int i = 0; i < k; i++) {
                if (iter == 1) {
                    oldCz.add(cz.get(i));
                } else {
                    oldCz.set(i, cz.get(i));
                }
                if (!groups.get(i).isEmpty()) {
                    cz.set(i, average(groups.get(i)));
                }
            }
            if (!cz.equals(oldCz)) {
                for (int i = 0; i < groups.size(); i++) {
                    groups.get(i).removeAll(groups.get(i));
                }
            }
            iter++;
        } while (!cz.equals(oldCz));
        /*for (int i = 0; i < cz.size(); i++) {
            System.out.println("New C" + (i + 1) + " " + cz.get(i));
        }
        System.out.println("Number of Itrations: " + iter);*/
    }

    private int average(ArrayList<Entry> list){
        int sum = 0;
        for (Entry e : list)
            sum += e.value;
        return sum / list.size();
    }

    public void displayLists() {
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + (i + 1));
            System.out.println(groups.get(i).toString());
        }
    }

    public ArrayList<String> getCluster(String name, int pop){
        int listNum = 0;
        for (int i = 0; i < groups.size(); i++){
            if (!groups.get(i).isEmpty()) {
                if (pop >= groups.get(i).get(0).value && pop <= groups.get(i).get(groups.get(i).size() - 1).value) {
                    listNum = i;
                    break;
                }
            }
        }
        ArrayList<String> names = new ArrayList<>();
        for (Entry e : groups.get(listNum)){
            if (!e.key.equals(name))
               names.add(e.toString());
        }
        return names;
    }
}
