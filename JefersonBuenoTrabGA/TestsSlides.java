package JefersonBuenoTrabGA;

import JefersonBuenoTrabGA.HashtableOpenAddressing.ColisionSolutionStrategy;

/**
 * Classe para validações iniciais, tomando como base os exemplos das aulas
 */
public class TestsSlides {
    public static void main(String[] args) {
        testLinearProbing();
        System.out.println();
        testQuadraticProbing();
        System.out.println();
        testDoubleHashing();
        System.out.println();
    }

    private static void testLinearProbing() {
        var table = create(ColisionSolutionStrategy.LINEAR_PROBING);
        table.print();
    }

    private static void testQuadraticProbing(){
        var table = create(ColisionSolutionStrategy.QUADRATIC_PROBING);
        table.print();
    }

    private static void testDoubleHashing() {
        var table = create(ColisionSolutionStrategy.DOUBLE_HASHING);
        table.print();
    }

    private static HashtableOpenAddressing<Integer> create(ColisionSolutionStrategy css) {
        var table = new HashtableOpenAddressing<Integer>(8, 7, css);

        table.insert(Item.create(7));
        table.insert(Item.create(17));
        table.insert(Item.create(36));
        table.insert(Item.create(100));
        table.insert(Item.create(106));
        table.insert(Item.create(205));

        return table;
    }
}