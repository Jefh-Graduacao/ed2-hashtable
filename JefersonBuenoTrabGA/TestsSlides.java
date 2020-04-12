package JefersonBuenoTrabGA;

import JefersonBuenoTrabGA.Hashtable.HashtableOpenAddressing;
import JefersonBuenoTrabGA.Hashtable.HashtableOpenAddressing.ColisionSolutionStrategy;

/**
 * Classe para validações iniciais, tomando como base os exemplos das aulas
 */
public class TestsSlides {
    public static void main(String[] args) {
        testLinearProbing();
        testQuadraticProbing();
        testDoubleHashing();
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
        return HashtableCreationHelper.newOpenAddressingSlides(css); 
    }
}