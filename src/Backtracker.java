import java.util.*;

public class Backtracker {

    private ArrayList<Character> listOfChars = new ArrayList<>();
    private List<Character> listOfVariables = new ArrayList<>();
    private ArrayList<String> listOfParts = new ArrayList<>();
    private ArrayList<String> listOfParts2 = new ArrayList<>();
    private Map<String, String> map = new HashMap<String, String>();

    public Backtracker(String stringExpression) {
        for (char a : stringExpression.toCharArray()) {
            listOfChars.add(listOfChars.size(), a);
        }
    }

    public void startSolving() {
        char currentChar = ' ';
        int position = 0;
        String part = "";

        while (currentChar != '|') {
            currentChar = listOfChars.get(position);
            if (currentChar != ',') {
                part = part + currentChar;
            } else if (currentChar == ',') {
                listOfParts.add(part);
                listOfParts2.add(part);
                part = "";
            }
            if (Character.isAlphabetic(currentChar) & currentChar != 'v') {
                if (!listOfVariables.contains(currentChar)) {
                    listOfVariables.add(currentChar);
                }
            }
            ++position;
        }
        listOfVariables.sort(Comparator.naturalOrder());
        part = part.substring(0, part.length() - 1);
        listOfParts.add(listOfParts.size(), part);
        char next = listOfVariables.get(0);
        solve(next, listOfParts, '0', "", listOfVariables);
        solve(next, listOfParts, '1', "", listOfVariables);
        listOfParts.add(part);
        listOfParts2.add(part);

        ArrayList<String> acquiredValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals("11111")) {
                acquiredValues.add(entry.getKey());
                System.out.println(entry.getKey() + "/" + entry.getValue());
            }
        }
        //Use this method to print tree of solution from list
        //Here used index 3 of the acquired list of solutions
        printTree(acquiredValues.get(3));
    }

    public void solve(char character1, ArrayList<String> equation1, char value1, String key1, List<Character> listofvars) {
        char character = character1;
        int i = 0;
        char value = value1;
        ArrayList<String> equation = new ArrayList<>();
        equation.addAll(equation1);
        String key = key1 + character + value1;
        ArrayList<Character> localListofVars = new ArrayList<Character>(listofvars);

        for (String part : equation) {
            ArrayList<Character> charPart = new ArrayList<>();
            for (char x : part.toCharArray()) {
                charPart.add(charPart.size(), x);
            }
            while (charPart.contains(character)) {
                charPart.set(charPart.indexOf(character), value);
            }
            while (charPart.contains('!')) {
                if (Character.isDigit(charPart.get(charPart.indexOf('!') + 1))) {
                    if (charPart.get(charPart.indexOf('!') + 1) == '1') {
                        charPart.remove(charPart.indexOf('!') + 1);
                        charPart.set(charPart.indexOf('!'), '0');
                    } else {
                        charPart.remove(charPart.indexOf('!') + 1);
                        charPart.set(charPart.indexOf('!'), '1');
                    }
                } else break;
            }
            if (charPart.contains('v')) {
                if (charPart.get(charPart.indexOf('v') - 1) == '1' || charPart.get(charPart.indexOf('v') + 1) == '1') {
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
            equation.set(i, partString);
            i++;

        }
        localListofVars.remove(0);
        String value12 = "";
        for (String r : equation) {
            value12 = value12 + r;
        }
        map.put(key, value12);
        if (!localListofVars.isEmpty()) {
            char next = localListofVars.get(0);
            solve(next, equation, '0', key, localListofVars);
            solve(next, equation, '1', key, localListofVars);
        }

    }

    public void printTree(String stringExpression) {
        ArrayList<Character> listOfElevations = new ArrayList<>();
        for (char c : stringExpression.toCharArray()) {
            listOfElevations.add(listOfElevations.size(), c);
        }
        int x = 0;
        int t = listOfElevations.size() * 3;
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