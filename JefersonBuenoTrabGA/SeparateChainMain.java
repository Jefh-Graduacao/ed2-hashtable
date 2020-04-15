package JefersonBuenoTrabGA;

public class SeparateChainMain {
    static final String separator = "\n" + "-".repeat(60) + "\n";

    public static void main(String[] args) {
        var table = createSeparateChainingTable();
        print("Criação da hashtable", table);

        var newItem = Item.create(933, "Nietzsche");
        table.insert(newItem);
        print("Inserção do item: " + newItem.toString(), table);

        table.delete(105); // Remove item inexistente
        print("Inserção do item com chave 105", table);

        table.insert(Item.create(52, "Item p/ pos. 4"));
        table.insert(Item.create(84, "Item p/ pos. 4"));
        print("2 novos itens na pos. 4", table);

        table.delete(100); // Remove 'Descartes'
        print("Remoção de item na pos. 4 (no meio)", table);

        table.insert(Item.create(205, "Chave duplicada"));
        print("Inserção de item com chave duplicada (205)", table);
 
        var searchResult = table.search(106); // Encontra Hegel
        var searchResultValue = searchResult.getValue();
        System.out.printf("Esperado Hegel - Encontrado %s - Correto: %b", searchResultValue, searchResultValue.equals("Hegel"));
    }

    private static Hashtable<String> createSeparateChainingTable() {
        var hashtable = new HashtableSeparateChaining<String>();

        hashtable.insert(Item.create(7, "Aristóteles"));
        hashtable.insert(Item.create(17, "Kant"));
        hashtable.insert(Item.create(36, "Platão"));
        hashtable.insert(Item.create(100, "Descartes"));
        hashtable.insert(Item.create(106, "Hegel"));
        hashtable.insert(Item.create(205, "São Tomás de Aquino"));

        return hashtable;
    }

    private static void print(String operationPerformed, Hashtable hashtable) {
        var stars = "*".repeat(7);
        System.out.printf("%s %s %s\n", stars, operationPerformed, stars);
        hashtable.print();
        System.out.println(separator);
    }
}