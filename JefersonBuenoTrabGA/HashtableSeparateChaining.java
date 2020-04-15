package JefersonBuenoTrabGA;

import java.util.LinkedList;

public class HashtableSeparateChaining<V> implements Hashtable<V> {
    static final float MAX_LOAD_FACTOR = 0.9f;

    private LinkedList<Item<V>>[] table;

    /**
     * Capacidade da Hashtable (tamanho da tabela/array interna)
     */
    private int capacity;

    /**
     * Quantidade de elementos na Hashtable (posições ocupadas)
     */
    private int count = 0;

    public HashtableSeparateChaining() {
        this(16);
    }

    public HashtableSeparateChaining(int initialCapacity) {
        capacity = initialCapacity;
        createTable();
    }

    /**
     * Cria uma nova tabela interna de acordo com a capacidade definida em this.capacity 
     * e define this.table para referenciar a tabela criada
     */
    @SuppressWarnings("unchecked")
    private void createTable() {
        table = (LinkedList<Item<V>>[])new LinkedList[capacity];
        for(int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
    }

    @Override
    public Item<V> delete(int key) {
        var hash = hash(key);
        var linkedList = table[hash];

        for (var item : linkedList) {
            if (item.getKey() == key) {
                linkedList.remove(item);
                count -= 1;
                return item;
            }
        }

        return null;
    }

    @Override
    public int insert(Item<V> item) {
        var positionToInsert = hash(item.getKey());

        var entry = table[positionToInsert];

        // Tratamento de chaves duplicadas. A estratégia usada foi ignorar o novo valor (manter o original)
        for (var listEntry : entry) {
            if (listEntry.getKey() == item.getKey()) {
                return -1;
            }
        }

        entry.addLast(item);
        count += 1;

        // Fazer o resize da Hashtable caso o fator de carga seja maior que a nossa base
        if(getLoadFactor() >= MAX_LOAD_FACTOR) {
            resize();
        }

        return positionToInsert;
    }

    @Override
    public Item<V> search(int key) {
        var hash = hash(key);

        for (var item : table[hash]) {
            if (item.getKey() == key)
                return item;
        }

        return null;
    }

    @Override
    public void print() {
        final var line = "-".repeat(80);
        final var doubleLine = "=".repeat(80);

        System.out.printf("%s%n" + 
            "Hashtable's capacity is %d and it has %d elements%n" +
            "The load factor is %.3f the hashtable will resize when it reaches %.3f%n" +
            "%s%n" + "Items:%n", 
            doubleLine, capacity, count, getLoadFactor(), MAX_LOAD_FACTOR, line);

        for (int tableIndex = 0; tableIndex < table.length; tableIndex++) {
            var list = table[tableIndex];
            if (!list.isEmpty()) {
                System.out.printf("(%d) ", tableIndex);
                for (var item: list) {
                    System.out.print(item.toString() + " ");
                }
                System.out.println();
            }
        }

        System.out.println(doubleLine);
    }

    private int hash(int key) {
        return key % capacity;
    }

    private float getLoadFactor() {
        return (float) count / table.length;
    }

    private void resize() {
        var oldTable = table;

        /* Aqui a hashtable é "resetada", ou seja, 
         * a única fonte de informações sobre o estado anterior é a variável oldTable. */
        capacity = table.length * 2;
        count = 0;

        createTable();

        for (LinkedList<Item<V>> tableEntry : oldTable) {
            for(var listEntry : tableEntry) {
                this.insert(listEntry);
            }
        }
    }
}