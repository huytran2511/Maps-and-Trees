//Huy Tran 818608122
//Alex Gutierrez 821394815

package edu.sdsu.cs.datastructures;

import java.util.TreeMap;
import java.util.LinkedList;

public class BalancedMap<K extends Comparable<K>,V> implements IMap<K,V>{

    private TreeMap<K,V> map = new TreeMap<K,V>();

    public BalancedMap(){}

    public BalancedMap(IMap<K,V> source){
        for(K key:source.keyset())
            map.put(key, source.getValue(key));
    }

    @Override
    public boolean contains(K key){
        return map.containsKey(key);
    }

    @Override
    public boolean add(K key, V value){
        if(map.containsKey(key))
            return false;
        else{
            map.put(key, value);
            return true;
        }
    }

    @Override
    public V delete(K key){
        return map.remove(key);
    }

    @Override
    public V getValue(K key){
        return map.get(key);
    }

    @Override
    public K getKey(V value){
        for(K key:map.keySet()){
            if(((Comparable<V>)map.get(key)).compareTo((V)value) == 0)
                return key;
        }
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value){
        LinkedList<K> list = new LinkedList<K>();
        for(K key:map.keySet()){
            if(((Comparable<V>)map.get(key)).compareTo((V)value) == 0)
                list.add(key);
        }
        return list;
    }

    @Override
    public int size(){
        return map.size();
    }

    @Override
    public boolean isEmpty(){
        return map.isEmpty();
    }

    @Override
    public void clear(){
        map.clear();
    }

    @Override
    public Iterable<K> keyset(){
        LinkedList<K> list = new LinkedList<K>(map.keySet());
        return list;
    }

    @Override
    public Iterable<V> values(){
        LinkedList<V> list = new LinkedList<V>(map.values());
        return list;
    }
}