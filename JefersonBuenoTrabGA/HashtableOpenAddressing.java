package JefersonBuenoTrabGA;

public class HashtableOpenAddressing<V> implements Hashtable<V> {
    static final float MAX_LOAD_FACTOR = 0.5f;

    private final int q;
    private final ColisionSolutionStrategy colisionSolutionStrategy;
    
    private int capacity;
    private int count;
    private ItemEntry<V>[] table;
    
    public HashtableOpenAddressing(int capacity, int q, ColisionSolutionStrategy colisionStrategy) {
        this.capacity = capacity;
        
        // Não trabalhei em validações de 'q' pois achei que isso foge do escopo aqui
        this.q = q;
        this.colisionSolutionStrategy = colisionStrategy;
        
        createTable();
    }

    /**
     * Cria uma nova tabela interna de acordo com a capacidade definida em this.capacity 
     * e define this.table para referenciar a tabela criada
     */
    @SuppressWarnings("unchecked")
    private void createTable() {
        table = (ItemEntry<V>[])new ItemEntry[capacity];
    }

    @Override
    public Item<V> delete(int key) {
        var entryToDelete = findEntry(key);
        
        if(entryToDelete == null || entryToDelete.isDeleted())
            return null;

        entryToDelete.delete();
        count -= 1;
        return entryToDelete.getItem();
    }

    @Override
    public int insert(Item<V> item) {
        int j = 0; 

        var existingEntry = findEntry(item.getKey());
        // Se já existir um item com a chave especificada, o novo será ignorado
        if(existingEntry != null && !existingEntry.isDeleted()) return -1;

        int hash;
        do {
            hash = hash(item.getKey(), j++);
        }
        while(table[hash] != null && !table[hash].isDeleted());
        // Se não houver item na posição 'hash' ou se o item tiver sido deletado, devemos usá-la

        table[hash] = new ItemEntry<V>(item);
        count += 1;

        if(getLoadFactor() >= MAX_LOAD_FACTOR) {
            resize();
        }

        return hash;
    }

    @Override
    public Item<V> search(int key) {
        var entry = findEntry(key);

        // Se o item tiver sido deletado, retornamos 'null' porque, bem, ele foi deletado ¯\_(ツ)_/¯
        return entry == null || entry.isDeleted() 
            ? null 
            : entry.getItem();
    }

    private ItemEntry<V> findEntry(int key) {
        int j = 0;
        ItemEntry<V> entry;
        do {
            var hash = hash(key, j++);
            entry = table[hash];
        }
        while(entry != null && entry.getItem().getKey() != key);
        // Aqui devemos continuar procurando, mesmo que seja encontrado algum item deletado

        return entry;
    }

    @Override
    public void print() {
        final var doubleLine = "=".repeat(80);

        System.out.printf("%s%n" + 
            "%s%n" + 
            "Hashtable's capacity is %d and it has %d elements%n" +
            "The colision strategy being used is %s%n" + 
            "The load factor is %.3f the hashtable will resize when it reaches %.3f%n" +
            "%s%n" + 
            "Items:%n", 
            " ".repeat(16) + "Hash table (Open Addresing) info", doubleLine, capacity, count, 
            colisionSolutionStrategy, getLoadFactor(), MAX_LOAD_FACTOR, "-".repeat(80));

        for(int i = 0; i < table.length; i++) {
            var entry = table[i];
            if(entry == null || entry.isDeleted()) continue;

            System.out.printf("[%02d] %s%n", i, entry.getItem().toString());
        }
        
        System.out.println(doubleLine);
    }

    private int hash(int key, int j) {
        switch(this.colisionSolutionStrategy) {
            case LINEAR_PROBING:
                return hashLinearProbing(key, j);
            case QUADRATIC_PROBING:
                return hashQuadraticProbing(key, j);
            case DOUBLE_HASHING:
                return hashDoubleHashing(key, j);
			default:
				return -1;
        }
    }

    private int hashLinearProbing(int key, int j) {
        var h1 = basicHash(key);
        return (h1 + j) % this.capacity;
    }

    private int hashQuadraticProbing(int key, int j) {
        var h1 = basicHash(key);
        return (h1 + (int)Math.pow(j, 2)) % this.capacity;
    }

    private int hashDoubleHashing(int key, int j) {
        var h1 = basicHash(key);
        var secHash = this.q - (key % this.q);
        return (h1 + j * secHash) % this.capacity;
    }

    private int basicHash(int key) {
        return key % this.capacity;
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

        for (var tableItem : oldTable) {
            /* Aqui é seguro ignorar os itens deletados porque, 
             * de qualquer forma, todos os hashes serão recalculados */
            if(tableItem == null || tableItem.isDeleted())
                continue;

            this.insert(tableItem.getItem());
        }
    }

    public enum ColisionSolutionStrategy {
        LINEAR_PROBING, QUADRATIC_PROBING, DOUBLE_HASHING
    }

    /**
     * Classe auxiliar para representar cada item da hashtable
     * Serve para controlar quais itens foram deletados
     * @param <T>
     */
    private class ItemEntry<T> {
        private boolean deleted;
        private Item<T> item;

        private ItemEntry(Item<T> item) {
            this.item = item;
            this.deleted = false;
        }

        public boolean isDeleted() {
            return this.deleted;
        }

        public Item<T> getItem() {
            return this.item;
        }

        public void delete() {
            this.deleted = true;
        }
    }
}