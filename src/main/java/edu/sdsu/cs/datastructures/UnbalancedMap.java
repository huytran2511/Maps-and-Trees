//Huy Tran 818608122
//Alex Gutierrez 821394815

package edu.sdsu.cs.datastructures;

import java.util.LinkedList;

public class UnbalancedMap<K extends Comparable<K>,V> implements IMap<K,V> {

    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> leftChild;
        private Node<K, V> rightChild;

        public Node(K k, V v) {
            key = k;
            value = v;
            leftChild = rightChild = null;
        }
    }

    private Node<K, V> root;
    private int currSize;

    public UnbalancedMap() {
        clear();
    }

    public UnbalancedMap(IMap<K, V> source) {
        for (K keys : source.keyset()) {
            V value = source.getValue(keys);
            add(keys, value);
        }

    }

    @Override
    public boolean contains(K key) {
        if (root == null)
            return false;
        Node<K, V> current = root;
        while (((Comparable<K>) current.key).compareTo((K) key) != 0) {
            if (((Comparable<K>) key).compareTo((K) current.key) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return false;
        }
        return true;
    }

    private boolean insert(K key, V value, Node<K, V> node, Node<K, V> parent, boolean left) {
        if (node == null) {
            if (left) {
                parent.leftChild = new Node<K, V>(key, value);
                return true;
            } else {
                parent.rightChild = new Node<K, V>(key, value);
                return true;
            }

        } else if (((Comparable<K>) key).compareTo((K) node.key) < 0)
            return insert(key, value, node.leftChild, node, true);
        else if (((Comparable<K>) key).compareTo((K) node.key) == 0)
            return false;
        else
            return insert(key, value, node.rightChild, node, false);
    }

    @Override
    public boolean add(K key, V value) {
        boolean check;
        if (root == null) {
            root = new Node<K, V>(key, value);
            currSize++;
            return true;
        } else {
            check = insert(key, value, root, null, false);
            if (check)
                currSize++;
            return check;
        }
    }

    private Node<K, V> getNode(K key) {
        if (root == null)
            return null;
        Node<K, V> current = root;
        while (((Comparable<K>) current.key).compareTo((K) key) != 0) {
            if (((Comparable<K>) key).compareTo((K) current.key) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return null;
        }
        return current;
    }

    private Node<K, V> getParent(K key) {
        if (root == null)
            return null;
        Node<K, V> current = root;
        Node<K, V> toReturn = null;
        while (((Comparable<K>) current.key).compareTo((K) key) != 0) {
            if (((Comparable<K>) key).compareTo((K) current.leftChild.key) == 0 || ((Comparable<K>) key).compareTo((K) current.rightChild.key) == 0)
                toReturn = current;
            if (((Comparable<K>) key).compareTo((K) current.key) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return null;
        }
        return toReturn;
    }

    private V zeroChild(K key) {
        Node<K, V> current = getParent(key);
        if (((Comparable<K>) key).compareTo((K) current.leftChild.key) == 0)
            current.leftChild = null;
        else if (((Comparable<K>) key).compareTo((K) current.rightChild.key) == 0)
            current.rightChild = null;
        return getNode(key).value;
    }

    private V oneChild(K key) {
        Node<K, V> toDelete = getNode(key);
        if (toDelete.leftChild != null && toDelete.rightChild == null)
            toDelete.leftChild = null;
        else if (toDelete.leftChild == null && toDelete.rightChild != null)
            toDelete.rightChild = null;
        return getNode(key).value;
    }

    private Node<K, V> minKey() {
        Node<K, V> current = root;
        Node<K, V> min = current;
        while (current.leftChild != null) {
            min = current.leftChild;
            current = current.leftChild;
        }
        return min;
    }

    private V twoChild(K key) {
        Node<K, V> toDelete = getNode(key);
        Node<K, V> min = minKey();
        Node<K, V> parent = getParent(min.key);
        toDelete = min;
        if (parent.leftChild != null && parent.rightChild == null)
            parent.leftChild = null;
        else if (parent.leftChild == null && parent.rightChild != null)
            parent.rightChild = null;
        return getNode(key).value;
    }


    @Override
    public V delete(K key) {
        if (root == null)
            return null;
        Node<K, V> node = getNode(key);
        Node<K, V> parent = getParent(key);
        if (root.leftChild == null && root.rightChild == null && ((Comparable<K>) node.key).compareTo((K) root.key) == 0) {
            root = null;
            currSize = 0;
            return node.value;
        }
        if (node.leftChild == null && node.rightChild == null) {
            currSize--;
            return zeroChild(key);
        }
        if ((node.leftChild == null && node.rightChild != null) || (node.rightChild == null && node.leftChild != null)) {
            currSize--;
            return oneChild(key);
        }
        if (node.leftChild != null && node.rightChild != null) {
            currSize--;
            return twoChild(key);
        }

        return null;
    }

    @Override
    public V getValue(K key) {
        if (root == null)
            return null;
        Node<K, V> current = root;
        while (((Comparable<K>) current.key).compareTo((K) key) != 0) {
            if (((Comparable<K>) key).compareTo((K) current.key) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return null;
        }
        return current.value;
    }

    @Override
    public K getKey(V value) {
        if (root == null)
            return null;
        Node<K, V> current = root;
        while (((Comparable<V>) current.value).compareTo((V) value) != 0) {
            if (((Comparable<V>) value).compareTo((V) current.value) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null)
                return null;
        }
        return current.key;
    }

    private Iterable<K> keysFromVal(LinkedList<K> list, Node<K, V> node, V value) {
        if (node == null)
            return list;
        keysFromVal(list, node.leftChild, value);
        if (((Comparable<V>) value).compareTo((V) node.value) == 0)
            list.add(node.key);
        keysFromVal(list, node.rightChild, value);
        return list;
    }

    @Override
    public Iterable<K> getKeys(V value) {
        LinkedList<K> list = new LinkedList<K>();
        return keysFromVal(list, root, value);
    }

    @Override
    public int size() {
        return currSize;
    }

    @Override
    public boolean isEmpty() {
        if (root == null)
            return true;
        else
            return false;
    }

    @Override
    public void clear() {
        root = null;
        currSize = 0;
    }

    private Iterable<K> keys(LinkedList<K> list, Node<K, V> node) {
        if (node == null)
            return list;
        keys(list, node.leftChild);
        list.add(node.key);
        keys(list, node.rightChild);
        return list;
    }

    private Iterable<V> vals(LinkedList<V> list, Node<K, V> node) {
        if (node == null)
            return list;
        vals(list, node.leftChild);
        list.add(node.value);
        vals(list, node.rightChild);
        return list;
    }

    @Override
    public Iterable<K> keyset() {
        LinkedList<K> list = new LinkedList<K>();
        return keys(list, root);
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> list = new LinkedList<V>();
        return vals(list, root);
    }

    public static void main(String[] args) {
    }
}