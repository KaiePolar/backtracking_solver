import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Expression {
    private String stringExpression;
    ArrayList<Character> listOfChars = new ArrayList<>();
    ArrayList<Character> listOfElevations = new ArrayList<>();
    HashSet<Character> listOfVariables = new HashSet<>();
    ArrayList<String> listOfParts = new ArrayList<>();
    ArrayList<String> copylistOfParts = new ArrayList<>();
    ArrayList<Character> reverseListOfVars = new ArrayList<>();
    boolean isSolved = false;


    public Expression(String next) {
        this.stringExpression = next;
        for (char z : stringExpression.toCharArray()) {
            listOfChars.add(listOfChars.size(), z);
        }
    }

    public void divideByParts() {
        int position = 0;
        String part = "";
        char currentChar = ' ';
        while (currentChar != '|') {
            currentChar = listOfChars.get(position);
            if (currentChar != ',') {
                part = part + currentChar;
            } else if (currentChar == ',') {
                listOfParts.add(part);
                copylistOfParts.add(part);
                part = "";
            } else if (currentChar == '|') {
                listOfParts.add(part);
                copylistOfParts.add(part);
                part = "";
            }
            if (Character.isAlphabetic(currentChar) & currentChar != 'v') {
                listOfVariables.add(currentChar);
            }
            ++position;


        }
        System.out.print("      ");
        System.out.println(listOfVariables);

        System.out.print("      ");
        System.out.print(listOfParts);
        System.out.println();

        for (char x : listOfVariables) {
            boolean isContained = false;
            for(String q:listOfParts){
                if(q.contains(String.valueOf(x))){
                    isContained = true;
                }
            }

            if (isContained) {
                isSolved = true;
                for (String c : listOfParts) {
                    if (!c.equals("1")) {
                        isSolved = false;
                    }
                }
                if (!isSolved) {
                    System.out.print(x + " = 0 ");
                    solve(x, listOfParts, '0', 0);

                }
                reverseListOfVars.add(x);
            }
        }

        for (char x : listOfVariables) {
            boolean isContained = false;
            for(String q:copylistOfParts){
                if(q.contains(String.valueOf(x))){
                    isContained = true;
                }
            }

            if (isContained) {
                isSolved = true;
                for (String c : copylistOfParts) {
                    if (!c.equals("1")) {
                        isSolved = false;
                    }
                }
                if (!isSolved) {
                    System.out.print(x + " = 1 ");
                    solve(x, copylistOfParts, '1', 0);

                }
                reverseListOfVars.add(x);
            }
        }

        System.out.println("final " + listOfParts);
        System.out.println("final " + listOfElevations);

    }

    public void solve(char character, ArrayList<String> equation, char value, int level) {
        int i = 0;

        listOfElevations.add(character);
        ArrayList equationCopy = new ArrayList();
        equationCopy.addAll(equation);
        level++;
        if (level == 3) {
            isSolved = true;
        }


        if (!isSolved) {
            for (String part : equation) {

                boolean isModified = false;
                boolean isFinished = true;
                ArrayList<Character> charPart = new ArrayList<>();

                for (char x : part.toCharArray()) {
                    charPart.add(charPart.size(), x);
                }

                for (char z : charPart) {
                    if (Character.isAlphabetic(z)) {
                        isFinished = false;
                    }
                }

                while (charPart.contains(character)) {
                    isModified = true;
                    charPart.set(charPart.indexOf(character), value);
                }

                while (charPart.contains('!')) {
                    if (Character.isDigit(charPart.get(charPart.indexOf('!') + 1))) {
                        if (charPart.get(charPart.indexOf('!') + 1) == '1') {
                            isModified = true;
                            charPart.remove(charPart.indexOf('!') + 1);
                            charPart.set(charPart.indexOf('!'), '0');
                        } else {
                            isModified = true;
                            charPart.remove(charPart.indexOf('!') + 1);
                            charPart.set(charPart.indexOf('!'), '1');
                        }
                    } else break;
                }

                if (charPart.contains('v')) {
                    if (charPart.get(charPart.indexOf('v') - 1) == '1' || charPart.get(charPart.indexOf('v') + 1) == '1') {
                        isModified = true;
                        charPart.removeAll(charPart);
                        charPart.add('1');
                    }
                }
                if (charPart.contains('v')) {
                    if (charPart.get(charPart.indexOf('v') - 1) == '0' || charPart.get(charPart.indexOf('v') + 1) == '0') {
                        charPart.remove(charPart.indexOf('v'));
                        charPart.remove(charPart.indexOf('0'));
                    }
                }


                String partString = "";
                for (char x : charPart) {
                    partString = partString + x;
                }

                if (partString.equals("0")) {
                    if (level < 2) {
                        isModified = false;
                        System.out.print(listOfParts);
                        System.out.println();
                        System.out.print(character + " = 1 ");


                        solve(character, equationCopy, '1', 1);
                        listOfElevations.remove(listOfElevations.size() - 1);
                        listOfElevations.add('1');
                    }
                }

                if (isModified) {
                    listOfParts.set(i, partString);
                }
                i++;


            }
        }
        System.out.print(listOfParts);
        System.out.println();
    }

    public void printTree() {
        int x = 0;
        int t = listOfElevations.size() * 10;
        for (int i = 0; i < listOfElevations.size(); i++) {
            if (i < listOfElevations.size() && listOfElevations.get(i) != '1') {
                String str = new String(new char[t]).replace("\0", " ");
                if (x != 0) {
                    t = t + 3;
                    str = str.substring(0, x - 1) + "X" + str.substring(x - 3);
                }
                System.out.print(str);
                System.out.println(listOfElevations.get(i) + "=0");
                str = new String(new char[t - 2]).replace("\0", " ");
                System.out.print(str);
                System.out.print("?//");
                if (i + 1 < listOfElevations.size() && listOfElevations.get(i + 1) == '1')
                    System.out.println("  \\");
                else System.out.println("");
                str = new String(new char[t - 2]).replace("\0", " ");
                System.out.print(str);
                System.out.print("//");
                if (i + 1 < listOfElevations.size() && listOfElevations.get(i + 1) == '1') {
                    x = str.length();
                    System.out.println("    \\");
                } else {
                    System.out.println("");
                    x = 0;
                }
                if (i == listOfElevations.size() - 1) {
                    str = new String(new char[t - 3]).replace("\0", " ");
                    System.out.print(str);
                    System.out.print("V");
                }
                t = t - 3;
            } else t = t + 3;
            if (i == listOfElevations.size() - 1 && listOfElevations.lastIndexOf('1') == listOfElevations.size() - 1) {
                String str = new String(new char[t - 3]).replace("\0", " ");
                System.out.print(str);
                if (!listOfParts.contains("0")) System.out.print("X       V");
                else System.out.print("X       X");
            }
        }
    }
}



