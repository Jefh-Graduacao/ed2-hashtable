package JefersonBuenoTrabGA;

import JefersonBuenoTrabGA.Hashtable.HashtableOpenAddressing;
import JefersonBuenoTrabGA.Hashtable.Item;

public class HashtableCreationHelper {
    /**
     * Cria uma hashtable padrão para os testes
     */
    public static HashtableOpenAddressing<String> newOpenAddressing(HashtableOpenAddressing.ColisionSolutionStrategy colisionSolutionStrategy) {
        var table = new HashtableOpenAddressing<String>(16, 1, colisionSolutionStrategy);

        table.insert(Item.create(1, "Jéferson (1)"));
        table.insert(Item.create(17, "Jéferson 2"));
        table.insert(Item.create(33, "Jéferson 3"));
        table.insert(Item.create(49, "Marquin"));
        table.insert(Item.create(65, "Joca"));
        table.insert(Item.create(81, "Tinoco"));
        
        return table;
    }

    public static HashtableOpenAddressing<Integer> newOpenAddressingSlides(HashtableOpenAddressing.ColisionSolutionStrategy colisionSolutionStrategy) {
        var table = new HashtableOpenAddressing<Integer>(11, 7, colisionSolutionStrategy);

        table.insert(Item.create(7));
        table.insert(Item.create(17));
        table.insert(Item.create(36));
        table.insert(Item.create(100));
        table.insert(Item.create(106));
        table.insert(Item.create(205));

        return table;
    }
}