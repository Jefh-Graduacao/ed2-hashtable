package JefersonBuenoTrabGA.Hashtable;

public class Item<V> {
    private int key;
    private V value;

    public Item(int key, V value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("{ Key: %02d, Value: %s }", this.key, this.value.toString());
    }

    /**
     * Método para construção de um Item<V>
     * Este método é útil para se fazer melhor uso da inferência de tipos genéricos. Desta forma
     * é possível criar uma instância de Item<V> sem especificar o tipo genérico.
     * @param <V> Tipo do valor
     * @param key Chave do item (usado para inserá-lo na Hashtable)
     * @param value Valor do item
     * @return Instância de Item<V>
     */
    public static <V> Item<V> create(int key, V value) {
        return new Item<V>(key, value);
    }

    public static Item<Integer> create(int key) {
        return new Item<Integer>(key, key);
    }
}