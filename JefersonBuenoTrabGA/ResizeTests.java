package JefersonBuenoTrabGA;

import java.util.Arrays;
import java.util.stream.Stream;

import JefersonBuenoTrabGA.HashtableOpenAddressing.ColisionSolutionStrategy;

public class ResizeTests {
    public static void main(String[] args) {
        final boolean debug = false;
        if(debug) {
            args = new String [] { "auto", "openaddressing", "linear_probing", "1", "2" };
        }

        if(args == null || args.length < 2) {
            printError("Este programa precisa de, no mínimo, dois argumentos da linha de comando para funcionar");
            return;
        }

        var mode = args[0];
        var type = args[1];

        if(!type.equals("openaddressing") && !type.equals("separatechaining")) {
            printError("Os tipos de hashtable disponíveis são 'openaddressing' e 'separatechaining'");
            return;
        }

        var openAddressing = "openaddressing".equals(type);
        var availableCss
            = Arrays.asList(new String[] { "linear_probing", "quadratic_probing", "double_hashing" });
            
        if(openAddressing && (args.length < 3 || !availableCss.contains(args[2]))) {
            var msg = String.format("Para criar uma hashtable do tipo Open Addressing " + 
                "é necessário definir uma estratégia de tratamento de colisões%n" + 
                "As opções disponíveis são %s%n", String.join(", ", availableCss));
            printError(msg);
            return;
        }

        var css = ColisionSolutionStrategy.valueOf(args[2].toUpperCase());

        Hashtable<String> hashtable = openAddressing
            ? new HashtableOpenAddressing<>(4, 3, css)
            : new HashtableSeparateChaining<>(4);

        switch(mode) {
            case "interactive":
                interactiveMode(hashtable);
                break;
            case "auto":
                var skip = openAddressing ? 3 : 2;
                autoMode(hashtable, Stream.of(args).skip(skip).toArray(String[]::new));
                break;
            default:
                System.out.printf("Valor %s inválido, os argumentos aceitos são %s e %s", mode, "interactive", "auto");
                return;
        }
    }

    private static void interactiveMode(Hashtable<String> hashtable) {
        String input;

        System.out.printf("Você está agora manipulando uma hashtable%n" + 
            "Digite 'help' para ver os comandos disponíveis%n%n");

        do {
            System.out.printf("Digite um comando: ");
            input = System.console().readLine();

            System.out.println();

            var inputArray = input.split(" ");

            if(inputArray == null || inputArray.length == 0) {
                System.out.printf("%nEntre com algum comando%n");
                continue;
            }

            var command = inputArray[0];
            
            if(command.equals("exit")) {
                break;
            }

            if(command.equals("help")) {
                showHelp();
                continue;
            }

            if(command.equals("show")) {
                hashtable.print();
                System.out.println();
                continue;
            }

            if(inputArray.length < 2) {
                System.out.printf("Sintaxe inválida, digite 'help' para obter ajuda%n%n");
                continue;
            }

            processCommand(command, inputArray, hashtable);

            System.out.printf("%n");

        }while(true);

        System.out.println();
    }

    private static void autoMode(Hashtable<String> hashtable, String[] input) {
        Integer[] intArgs = Stream.of(input)
            .map(Integer::valueOf)
            .toArray(Integer[]::new);

        hashtable.print();

        for (var arg : intArgs) {
            System.out.printf("%nInserindo %d%n", arg);
            hashtable.insert(Item.create(arg, arg.toString()));
            hashtable.print();
        }
    }

    private static void processCommand(String command, String[] inputArray, Hashtable<String> hashtable) {
        Integer keyArg;
        try {
            keyArg = Integer.parseInt(inputArray[1]);
        } catch (NumberFormatException e) {
            System.out.printf("Não foi possível converter %s para Integer%n", inputArray[1]);
            return;
        }

        switch(command) {
            case "add":
                if(inputArray.length < 3) {
                    System.out.printf("O comando 'add' espera 2 argumentos%n");
                    break;
                }

                var newItem = Item.create(keyArg, inputArray[2]);
                var insertedPosition = hashtable.insert(newItem);
                if(insertedPosition == -1) {
                    var existent = hashtable.search(keyArg);

                    System.out.printf("Não possível inserir a entrada com chave %d%n" + 
                    "Já existe uma entrada com esta chave %s%n", keyArg, existent);
                } else {
                    System.out.printf("Registro inserido %s%n", newItem);
                }
                break;
            case "rm":
                var deletedItem = hashtable.delete(keyArg);
                if(deletedItem == null) {
                    System.out.printf("Não foi encontrado nenhum registro com a chave %d%n", keyArg);
                }else {
                    System.out.printf("Item deletado: %s%n", deletedItem.toString());
                }

                //hashtable.print();
                break;
            case "find":
                var entryFound = hashtable.search(keyArg);
                if(entryFound == null) {
                    System.out.printf("Não foi encontrado nenhum registro com a chave %d%n", keyArg);
                } else {
                    System.out.printf("%s%n", entryFound.toString());
                }
                break;
            default:
                System.out.printf("Comando inválido. Use 'help' para ver a lista de comandos disponíveis%n");
                break;
        }
    }

    private static void showHelp() {
        System.out.printf("Os comandos abaixo são utilizados para manipular a Hashtable%n" +
            "Note que as chaves devem ser números inteiros%n%n" + 
            "add <key> <value>\tAdiciona uma nova entrada com a chave e valor especificados%n" + 
            "rm <key>\t\tRemove a entrada com a chave especificada%n" + 
            "find <key>\t\tBusca por uma entrada usando a chave especificada%n" + 
            "show\t\t\tMostra informações internas da Hashtable%n%n");
    }

    private static void printError(String message) {
        System.out.printf(AnsiEscapeCodes.BACKGROUND_RED + AnsiEscapeCodes.WHITE +
                "%s" + AnsiEscapeCodes.RESET + AnsiEscapeCodes.RED + " %s" + AnsiEscapeCodes.RESET + "%n",
                "ERRO:", message);
    }
}