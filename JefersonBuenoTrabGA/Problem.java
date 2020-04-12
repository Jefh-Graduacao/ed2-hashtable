package JefersonBuenoTrabGA;

public class Problem {
    public static void main(String[] args) {
        
        var sum = 10;
        var inputArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };


    }

    public static void solveProblem(int sum, int[] input) {
        var hashtable = new HashtableSeparateChaining<Integer>(16);
        for(var i: input) {
            hashtable.insert(Item.create(i));
        }

        System.out.printf("Procurando por pares que tenham como soma %d%n", sum);
        // todo: printar o array

        
    }
}