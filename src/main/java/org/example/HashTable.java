package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashTable<K, V> {
     class HashEntry<K, V> {
        private K key;
        private V value;
        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public void setKey(K key) { this.key = key; }
        public K getKey() { return this.key; }

        public void setValue(V value) { this.value = value; }
        public V getValue() { return this.value; }
    }

        private final static int DEFAULT_CAPACITY = 16;

        private int count;
        private int capacity;
        private HashEntry<K, V>[] table;

        public HashTable() {
            this(DEFAULT_CAPACITY);
        }

        public HashTable(int capacity) {
            super();
            this.capacity = capacity;
            table = new HashEntry[capacity];
        }

        public boolean isEmpty() {
            return (count == 0);
        }

        public int size() {
            return count;
        }

        public void clear() {
            table = new HashEntry[this.capacity];
            count = 0;
        }

        /**
         * Возвращает значение null, если количество опробований превышает емкость.
         * @param key
         * @return
         */
        public V get(K key) {
            V value = null;
            int probeCount = 0;
            int hash = this.hashCode(key);
            while (table[hash] != null && !table[hash].getKey().equals(key) && probeCount <= this.capacity) {
                hash = (hash + 1) % this.capacity;
                probeCount++;
            }
            if (table[hash] != null && probeCount <= this.capacity) {
                value = table[hash].getValue();
            }
            return value;
        }

        /**
         * Проверьте количество выполненных проб и завершает работу, если количество проб достигнет недопустимого значения.
         * <p>
         * Создает исключение, если таблица заполнена.
         *
         * @param key
         * @param value
         * @return
         * @throws Exception
         */
        public V put(K key, V value) throws Exception {
            int probeCount = 0;
            int hash = this.hashCode(key);
            while (table[hash] != null && !table[hash].getKey().equals(key) && probeCount <= this.capacity) {
                hash = (hash + 1) % this.capacity;
                probeCount++;
            }
            if (probeCount <= this.capacity) {
                if (table[hash] != null) {
                    table[hash].setValue(value);
                } else {
                    table[hash] = new HashEntry(key, value);
                    count++;
                }
                return table[hash].getValue();
            } else {
                throw new Exception("Table Full");
            }
        }


    /**
     * Если ключ присутствует, то table[hash] = null и возвращается значение, в противном случае возвращется null.
     * @param key
     * @return
     */
    public V remove(K key) {
        V value = null;
        int probeCount = 0;
        int hash = this.hashCode(key);
        while (table[hash] != null && !table[hash].getKey().equals(key) && probeCount <= this.capacity) {
            hash = (hash + 1) % this.capacity;
            probeCount++;
        }
        if (table[hash] != null && probeCount <= this.capacity) {
            value = table[hash].getValue();
            table[hash] = null;
            count--;
        }
        return value;
    }

    public boolean contains(Object value) {
        return this.containsValue(value);
    }

    public List<V> getValues() {
        List<V> res = new ArrayList<>();
        for (HashEntry<K, V> entry : table) {
            if(Objects.isNull(entry))
                break;
            res.add(entry.getValue());
        }
        return res;
    }

    public boolean containsKey(Object key) {
        for (HashEntry<K, V> entry : table) {
            if (entry != null && entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (HashEntry<K, V> entry : table) {
            if (entry != null && entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        data.append("{");
        for (HashEntry<K, V> entry : table) {
            if (entry != null) {
                data.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        if (data.toString().endsWith(", ")) {
            data.delete(data.length() - 2, data.length());
        }
        data.append("}");
        return data.toString();
    }

    private int hashCode(K key) {
        double hash = Math.pow(key.hashCode(), 2);
        return (int) (hash % this.capacity);
    }

    }
