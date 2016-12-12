package konstytucja;

import java.io.IOException;
import java.io.FileNotFoundException;

public class ConstitutionSystem {
    public static void main(String[] args) {
        try {
            new ConstitutionPrinter().print(args);
        } catch (NumberFormatException err){
            System.out.println("Numer artykulu/rozdzialu musi byc liczba.");
        } catch (IllegalArgumentException err) {
            System.out.println(err.getMessage());
        } catch (FileNotFoundException err){
            System.out.println("Plik " + args[0] + " nie istnieje.");
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}