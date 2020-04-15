package JefersonBuenoTrabGA;

import java.util.function.Supplier;
import java.util.stream.Stream;

import JefersonBuenoTrabGA.HashtableOpenAddressing.ColisionSolutionStrategy;

public class ResizeTests {
    public static void main(String[] args) {
        final boolean debug = false;
        if(debug) {
            args = new String [] { "auto", "openaddressing", "1", "2" };
        }

        if(args == null || args.length < 1) {
            System.out.printf("%s%s%s%n", AnsiEscapeCodes.RED, 
                "Este programa precisa de, no mínimo, dois argumentos da linha de comando para funcionar", AnsiEscapeCodes.RESET);
            
            return;
        }

        //Supplier<Stream<String>> argsSupplier = () -> Stream.of(args);

        var mode = args[0];
        var type = args[1];

        Hashtable<Integer> hashtable = "openaddressing".equals(type)
            ? new HashtableOpenAddressing<>(4, 3, ColisionSolutionStrategy.DOUBLE_HASHING)
            : new HashtableSeparateChaining<>(4);

        switch(mode) {
            case "interactive":
                return;
            case "auto":
                autoMode(hashtable, Stream.of(args).skip(2).toArray(String[]::new));
                break;
            default:
                System.out.printf("Valor %s inválido, os argumentos aceitos são %s e %s", mode, "interactive", "auto");
                return;
        }
    }

    private static void autoMode(Hashtable<Integer> hashtable, String[] input) {
        Integer[] intArgs = Stream.of(input)
            .map(Integer::valueOf)
            .toArray(Integer[]::new);

        hashtable.print();

        for (var arg : intArgs) {
            System.out.printf("%nInserindo %d%n", arg);
            hashtable.insert(Item.create(arg));
            hashtable.print();
        }
    }
}