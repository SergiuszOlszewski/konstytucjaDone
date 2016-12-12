package konstytucja;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class OptionParser {
    private String path;
    private char printType;
    private List<Integer> contentToPrint = new ArrayList<>();

    String getPath() {
        return this.path;
    }

    char getPrintType() {
        return this.printType;
    }

    List<Integer> getContentToPrint() {
        return this.contentToPrint;
    }

    OptionParser getUserOptions(String[] args) {
        return new OptionParser().parse(args);
    }

    private OptionParser parse(String[] args) throws IllegalArgumentException{
        if (args.length < 3 || args.length > 4)
            throw new IllegalArgumentException("Zla liczba argumantow: " + args.length + " (wymagane 3 lub 4). Nie uzywaj * jako argumentu");

        OptionParser options = new OptionParser();
        int i = 0;
        do {
            if (!(options.findOption(args[i], i)))
                throw new IllegalArgumentException("Podany argument jest bledny: " + args[i]);
            i++;
        } while (i < args.length);

        return options;
    }

    private boolean findOption(String arg, int i) throws NumberFormatException{
        if (i == 0) {
            Pattern pattern = Pattern.compile("[a-zA-Z]");
            if (pattern.matcher(arg).find()) {
                this.path = arg;
                return true;
            } else return false;
        }

        if (i == 1) {
            Pattern pattern = Pattern.compile("-[ar]"); //dostepne przelaczniki -a, -r
            if (pattern.matcher(arg).matches()) {
                this.printType = arg.charAt(i);
                return true;
            } else return false;
        }

        if (i == 2 || (i == 3 && this.printType == 'a')) {
            Integer a = Integer.valueOf(arg);
            Pattern pattern = Pattern.compile("[0-9]");
            if (pattern.matcher(arg).find()) {
                if (a < 0 || a > 243 || (this.printType == 'r' && a > 13))
                    throw new IllegalArgumentException("Nie istnieje artykul/rozdzial o podanym numerze");
                else {
                    this.contentToPrint.add(a);
                    return true;
                }
            } else return false;
        }
        return false;
    }
}
