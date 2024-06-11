/**
 * This OwnHashTable class is mainly created for utilizing hash table features and its methods with creating them on my own.
 * There are methods to put, remove,rehash ext. in hashtable.
 * The data fields of the hashtable capacity, table, size and load factor to keep the track of fullness of the table.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 24.11.2023
 */

public class OwnHashTable<K, V> {

    // data fields of the ownHashTable class: capacity, table, size, load factor
    private static int capacity = 2791;
    private Entry<K, V>[] table;
    private int size;
    private final static double LOAD_FACTOR = 0.6;

    public Entry<K, V>[] getTable() {
        return table;
    }


    public OwnHashTable() {
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
    }


    //   Entry class is used for storing key and value pairs in tables.
    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    // Deleted entry is created to put it as space holder.
    public static class DeletedEntry<K, V> extends Entry<K, V> {
        public DeletedEntry() {
            super(null, null);
        }
    }

    private int findPosition(K key) {
        // This method finds appropriate position in hash table
        int hash = key.hashCode() % table.length;
        if (hash < 0) {
            hash += table.length;
        }
        int i = 1;

        while (table[hash] != null && (table[hash] instanceof OwnHashTable.DeletedEntry<K, V> || !table[hash].getKey().equals(key))) {
            hash = (hash + i * i) % table.length;
            i++;
            if (hash >= table.length) {
                hash -= table.length;
            }
            if (hash < 0) {
                hash += table.length;
            }
        }
        return hash;
    }

    public void remove(K key) {
        int location = findPosition(key);

        table[location] = new DeletedEntry<>();
        size--;
    }

    public V get(K key) {
        int location = findPosition(key);
        if (table[location] != null) return table[location].getValue();

        return null;
    }

    public void put(K key, V value) {

        if (size >= LOAD_FACTOR * table.length) {
            rehash(table.length);
        }
        int location = findPosition(key);
        table[location] = new Entry<>(key, value);
        size++;
    }

    private static int nextPrime(int currentPrime) {
        if (currentPrime % 2 == 0) {
            currentPrime++;
        }
        while (isPrime(currentPrime)) {
            currentPrime += 2;
        }
        return currentPrime;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }

        if (n == 1 || n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i < n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private void rehash(int arraySize) {
        Entry<K, V>[] newTable = new Entry[nextPrime(2 * arraySize)];
        Entry<K, V>[] oldTable = table;
        table = newTable;
        for (Entry<K, V> entry : oldTable) {
            if (entry != null && !(entry instanceof OwnHashTable.DeletedEntry<K, V>)) {
                put(entry.key, entry.value);
            }
        }
    }
    public boolean containsKey(K key) {
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                if (entry.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }
}