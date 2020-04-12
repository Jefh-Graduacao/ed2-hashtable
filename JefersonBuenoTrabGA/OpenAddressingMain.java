package JefersonBuenoTrabGA;

import java.util.function.Supplier;
import java.util.stream.Stream;

import JefersonBuenoTrabGA.HashtableOpenAddressing.ColisionSolutionStrategy;

public class OpenAddressingMain {
    public static void main(String[] args) {
        var runAllTests = args.length == 0;
        Supplier<Stream<String>> streamSupplier = () -> Stream.of(args);

        if(runAllTests || streamSupplier.get().anyMatch(v -> v.equals("linearprobing")))
            testLinearProbing();
        
        if(runAllTests || streamSupplier.get().anyMatch(v -> v.equals("quadraticprobing")))
            testQuadraticProbing();
        
        if(runAllTests || streamSupplier.get().anyMatch(v -> v.equals("doublehashing")))
            testDoubleHashing();
    }

    private static void testLinearProbing() {
        var table = new HashtableOpenAddressing<String>(16, 0, ColisionSolutionStrategy.LINEAR_PROBING);
        
        System.out.printf("%n***** Testing Linear Probing *****%n");

        // Criando alguns itens para inserir na tabela
        var item_key01 = Item.create(1, "Jéferson");
        var item_key99 = Item.create(99, "Patrícia"); 
        var item_key83 = Item.create(83, "Márcio"); 
        var item_key78 = Item.create(78, "Mônica");
        var item_key52 = Item.create(52, "Maria");
        var item_key51 = Item.create(51, "James");
        var item_key42 = Item.create(42, "Francine");

        // Inserindo um item que deve ficar na posição 1
        insertAndAssertIndex(table, item_key01, 1);

        // Inserindo um item com chave duplicada (deve ser ignorado)
        var index = table.insert(Item.create(1, "Joaquim"));
        if(index >= 0) printError("Item duplicado foi inserido");
        if(table.search(1) != item_key01) printError("Error: Item deve ser " + item_key01.getValue());

        insertAndAssertIndex(table, item_key99, 3);
        insertAndAssertIndex(table, item_key83, 4);
        insertAndAssertIndex(table, item_key78, 14);
        insertAndAssertIndex(table, item_key52, 5);
        insertAndAssertIndex(table, item_key51, 6);
        insertAndAssertIndex(table, item_key42, 10);

        deleteAndAssertDeletion(table, 83); // Remover um item que colidiu na pos. 3

        var searchResult = table.search(51); // Buscar um item inserido após o 83 com colisão na 3
        if(searchResult != item_key51) {
            printError(String.format("Expected %s but got %s%n", item_key51, searchResult));
        }

        // Inserindo novo item que vai colidir na posição 3
        // Este deve tomar o lugar do 83 e ser inserido na posição 4
        insertAndAssertIndex(table, Item.create(35, "José"), 4);

        // Inserir um novo item 83
        insertAndAssertIndex(table, Item.create(83, "Maria"), 7);

        // Remover o item 78
        deleteAndAssertDeletion(table, 78);
        insertAndAssertIndex(table, Item.create(78, "Novo 78"), 14);

        System.out.println("Testes finalizados");

        table.print();
    }

    private static void testQuadraticProbing() {
        var table = new HashtableOpenAddressing<String>(16, 0, ColisionSolutionStrategy.QUADRATIC_PROBING);
        
        System.out.printf("%n***** Testing Quadratic Probing *****%n");

        // Criando alguns itens para inserir na tabela
        var item_key01 = Item.create(1, "Jéferson");
        var item_key99 = Item.create(99, "Patrícia"); 
        var item_key83 = Item.create(83, "Márcio"); 
        var item_key78 = Item.create(78, "Mônica");
        var item_key52 = Item.create(52, "Maria");
        var item_key51 = Item.create(51, "James");
        var item_key42 = Item.create(42, "Francine");

        // Inserindo um item que deve ficar na posição 1
        insertAndAssertIndex(table, item_key01, 1);

        // Inserindo um item com chave duplicada (deve ser ignorado)
        var index = table.insert(Item.create(1, "Joaquim"));
        if(index >= 0) printError("Item duplicado foi inserido");
        if(table.search(1) != item_key01) printError("Item deve ser " + item_key01.getValue());

        insertAndAssertIndex(table, item_key99, 3);
        insertAndAssertIndex(table, item_key83, 4);
        insertAndAssertIndex(table, item_key78, 14);
        insertAndAssertIndex(table, item_key52, 5);
        insertAndAssertIndex(table, item_key51, 7);
        insertAndAssertIndex(table, item_key42, 10);

        deleteAndAssertDeletion(table, 52);

        // Inserindo novo item que vai colidir na posição 3, depois na 14
        insertAndAssertIndex(table, Item.create(67, "José"), 12);

        // Inserir um novo item 52
        insertAndAssertIndex(table, Item.create(52, "Maria"), 5);

        // Remover o item 78
        deleteAndAssertDeletion(table, 78);
        insertAndAssertIndex(table, Item.create(78, "Novo 78"), 14);

        System.out.println("Testes finalizados");

        table.print();
    }

    private static void testDoubleHashing() {
        var table = new HashtableOpenAddressing<String>(16, 13, ColisionSolutionStrategy.DOUBLE_HASHING);
        
        System.out.printf("%n***** Testing Double Hashing *****%n");

        // Criando alguns itens para inserir na tabela
        var item_key01 = Item.create(1, "Jéferson");
        var item_key99 = Item.create(99, "Patrícia"); 
        var item_key83 = Item.create(83, "Márcio"); 
        var item_key78 = Item.create(78, "Mônica");
        var item_key52 = Item.create(52, "Maria");
        var item_key51 = Item.create(51, "James");
        var item_key42 = Item.create(42, "Francine");

        // Inserindo um item que deve ficar na posição 1
        insertAndAssertIndex(table, item_key01, 1);

        // Inserindo um item com chave duplicada (deve ser ignorado)
        var index = table.insert(Item.create(1, "Joaquim"));
        if(index >= 0) printError("Item duplicado foi inserido");
        if(table.search(1) != item_key01) printError("Item deve ser " + item_key01.getValue());

        insertAndAssertIndex(table, item_key99, 3);
        insertAndAssertIndex(table, item_key83, 11);
        insertAndAssertIndex(table, item_key78, 14);
        insertAndAssertIndex(table, item_key52, 4);
        insertAndAssertIndex(table, item_key51, 5);
        insertAndAssertIndex(table, item_key42, 10);

        deleteAndAssertDeletion(table, 52);

        insertAndAssertIndex(table, Item.create(67, "José"), 9);

        // Inserir um novo item 52
        insertAndAssertIndex(table, Item.create(52, "Maria"), 4);

        // Remover o item 78
        deleteAndAssertDeletion(table, 78);
        insertAndAssertIndex(table, Item.create(78, "Novo 78"), 14);

        System.out.println("Testes finalizados");

        table.print();
    }

    private static <T> boolean insertAndAssertIndex(HashtableOpenAddressing<T> table, Item<T> item, int expectedIndex) {
        var index = table.insert(item);

        if(index != expectedIndex) {
            var msg = String.format("Index of %s shoud be %d and is %d%n", item, expectedIndex, index);
            printError(msg);
            return false;
        }

        return true;
    }

    private static <T> boolean deleteAndAssertDeletion(HashtableOpenAddressing<T> table, int key) {
        table.delete(key); 
        if(table.search(key) != null) {
            var msg = String.format("Item %d wasn't deleted%n", key);
            printError(msg);
            return false;
        }

        return true;
    }

    private static void printError(String message) {
        System.out.printf("%sError: %s%s%n", AnsiEscapeCodes.RED, message, AnsiEscapeCodes.RESET);
    }
}