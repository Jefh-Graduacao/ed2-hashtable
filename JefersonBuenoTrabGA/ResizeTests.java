package JefersonBuenoTrabGA;

import java.util.stream.Stream;

public class ResizeTests {
    public static void main(String[] args) {
        var hashTable = new HashtableSeparateChaining<Integer>(4);

        if(args == null || args.length == 0) {
            args = new String[] { "1", "2", "3", "4", "5", "6" };
        }

        Integer[] intArgs = Stream.of(args)
            .map(Integer::valueOf)
            .toArray(Integer[]::new);

        hashTable.print();

        for (var arg : intArgs) {
            System.out.printf("%nInserindo %d%n", arg);
            hashTable.insert(Item.create(arg));
            hashTable.print();
        }
    }
}