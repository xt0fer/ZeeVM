package rocks.zipcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The ZeeVM - a Zee virtual machine
 * reads in an "vm file of code" and produces an integer result.
 * this is a very simple stack machine.
 *
 */
public class ZeeVM 
{
    public static void main( String[] args ) 
    {
		Scanner scanner;
        try {
            scanner = new Scanner(new File("./testinput.zvm"));
            StringBuilder input = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim().toLowerCase();
                if (line.isEmpty()) continue;
                // if line is comment, drop it.
                if (line.startsWith(";;")) continue;
                if (line.startsWith("//")) continue;
                input.append(line + "\n");
            }
            scanner.close();

            ZeeEngine engine = new ZeeEngine();
            String result = engine.interpret(input.toString());
            System.out.println(result);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }
}
