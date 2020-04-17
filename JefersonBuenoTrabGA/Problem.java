package JefersonBuenoTrabGA;

import java.util.stream.Stream;

public class Problem {
    public static void main(String[] args) {
        if(args == null || args.length == 0) {
            args = new String[] { "10", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        }

        Integer[] intArgs = Stream.of(args)
            .map(Integer::valueOf)
            .toArray(Integer[]::new);

        var sum = intArgs[0];
        var inputArray = Stream.of(intArgs).skip(1).toArray(Integer[]::new);
        
        solveProblem(sum, inputArray);
    }

    public static void solveProblem(int sum, Integer[] input) {
        var hashtable = new HashtableSeparateChaining<Integer>(4);
        for(var i: input) {
            hashtable.insert(Item.create(i));
        }

        System.out.printf("Procurando por pares que tenham como soma %d%n", sum);
        // todo: printar o array

        for (var item : input) {
            var complement = sum - item;
            if(complement < 0) continue;
            
            var searchResult = hashtable.search(complement);

            if(searchResult != null)
                System.out.printf("(%d, %d)%n", item, searchResult.getValue());
        }

        System.out.printf("%n*** Mostrando a Hashtable criada para resolver o problema ***%n");
        hashtable.print();
    }
}