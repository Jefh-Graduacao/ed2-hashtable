package JefersonBuenoTrabGA;

import JefersonBuenoTrabGA.Hashtable.HashtableOpenAddressing;
import JefersonBuenoTrabGA.Hashtable.Item;
import JefersonBuenoTrabGA.Hashtable.HashtableOpenAddressing.ColisionSolutionStrategy;

public class Program {
    public static void main(String[] args) {
        testLinearProbing();
        // var table = 
        //     TestHelper.createOpenAddressingHashtable(ColisionSolutionStrategy.LINEAR_PROBING);

        // var entry = table.search(33);
        // table.delete(33);
        // var notExistentEntry = table.search(33);

        // var entry49 = table.search(49);

        // table.print();
    }

    private static void testLinearProbing() {
        var table = new HashtableOpenAddressing<String>(16, 0, ColisionSolutionStrategy.LINEAR_PROBING);
        
        // Criando alguns itens para inserir na tabela
        var item1 = Item.create(1, "Jéferson");
        var item2 = Item.create(99, "Patrícia"); 
        var item3 = Item.create(83, "Márcio"); 
        var item4 = Item.create(78, "Mônica");
        var item5 = Item.create(52, "Maria");
        var item6 = Item.create(51, "James");
        var item7 = Item.create(42, "Francine");

        // Inserindo um item que deve ficar na posição 1
        var index = table.insert(item1);
        if(index != 1) System.out.printf("Item na posição errada");

        // Inserindo um item com chave duplicada (deve ser ignorado)
        index = table.insert(Item.create(1, "Joaquim"));
        if(index >= 0) System.out.println("Item duplicado foi inserido");
        if(table.search(1) != item1) System.out.printf("Item deve ser Jéferson");

        insertAndAssertIndex(table, item2, 3);
        insertAndAssertIndex(table, item3, 4);
        insertAndAssertIndex(table, item4, 14);
        insertAndAssertIndex(table, item5, 5);
        insertAndAssertIndex(table, item6, 6);
        insertAndAssertIndex(table, item7, 10);

        deleteAndAssertDeletion(table, 83); // Remover um item que colidiu na pos. 3

        var searchResult = table.search(51); // Buscar um item inserido após o 83 com colisão na 3
        if(searchResult != item6) {
            System.out.printf("Error: Expected %s but got %s%n", item6, searchResult);
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

    public static void testQuadraticProbing() {
        var table = 
            HashtableCreationHelper.newOpenAddressing(ColisionSolutionStrategy.QUADRATIC_PROBING);

        var entry = table.search(1);
        entry = table.search(2);
        entry = table.search(17);

        table.insert(Item.create(208, "Novo item"));
    }

    private static <T> boolean insertAndAssertIndex(HashtableOpenAddressing<T> table, Item<T> item, int expectedIndex) {
        var index = table.insert(item);

        if(index != expectedIndex) {
            System.out.printf("Error: Index of %s shoud be %d and is %d%n", item, expectedIndex, index);
            return false;
        }

        return true;
    }

    private static <T> boolean deleteAndAssertDeletion(HashtableOpenAddressing<T> table, int key) {
        table.delete(key); 
        if(table.search(key) != null) {
            System.out.printf("Error: Item %d wasn't deleted%n", key);
            return false;
        }

        return true;
    }
}