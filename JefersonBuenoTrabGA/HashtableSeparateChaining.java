package JefersonBuenoTrabGA;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashtableSeparateChaining<V> implements Hashtable<V> {

    // https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays
    // Usei um arraylist porque em Java não é possível criar arrays de tipos parametrizados e
    // é seguro considerar que os acessos em um ArrayList são O(1).
    private final ArrayList<LinkedList<Item<V>>> table;
    private final int capacity;

    public HashtableSeparateChaining() {
        this(16);
    }

    public HashtableSeparateChaining(int initialCapacity) {
        this.capacity = initialCapacity;
        table = new ArrayList<>(this.capacity);

        for (int i = 0; i < this.capacity; i++) {
            table.add(new LinkedList<>());
        }
    }

    @Override
    public Item<V> delete(int key) {
        var hash = hash(key);
        var linkedList = table.get(hash);

        for(var item : linkedList) {
            if(item.getKey() == key) {
                linkedList.remove(item);
                return item;
            }
        }

        return null;
    }

    @Override
    public int insert(Item<V> item) {
        var positionToInsert = hash(item.getKey());

        var entry = table.get(positionToInsert);
        // Tratamento de chaves duplicadas. A estratégia usada foi ignorar o novo valor (manter o original)
        for(var listEntry: entry) {
            if(listEntry.getKey() == item.getKey()) {
                return -1;
            }
        }

        entry.addLast(item);
        return positionToInsert;
    }

    /**
     * Procura por um item na hashtable usando a chave especificada
     * Retorna nulo caso não encontre a chave
     */
    @Override
    public Item<V> search(int key) {
        var hash = hash(key);
        
        for(var item : table.get(hash)) {
            if(item.getKey() == key) 
                return item;
        }

        return null;
    }

    @Override
    public void print() {
        for(int tableIndex = 0; tableIndex < table.size(); tableIndex++) {
            var list = table.get(tableIndex);
            if(!list.isEmpty()) {
                System.out.printf("(%d) ", tableIndex);
                for (var item : list) {
                    System.out.printf("{ Key: %d, Value: %s } ", item.getKey(), item.getValue().toString());
                }
                System.out.println();
            }
        }
    }

    private int hash(int key) {
        return key % this.capacity;
    }
}