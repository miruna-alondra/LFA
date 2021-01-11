import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Interpreter {
    public String input;
    public HashMap<Character, Integer> hashMap = new HashMap<>();
    public ArrayList<Integer> sentCodes = new ArrayList<>();
    public ArrayList<String> commands = new ArrayList<>();
    public static boolean jump = false;

    public Interpreter(String input) {
        this.input = input;
    }

    public void split() {
        int counter = 0;
        int code = 0;
        int index;
        for (index = 0; index < input.length(); index++) {
            if (counter == 4) {
                counter = 0;
                code = 0;
                hashMap.clear();
            }
            if (hashMap.containsKey(input.charAt(index))) {
                sentCodes.add(hashMap.get(input.charAt(index)));
            } else {
                hashMap.put(input.charAt(index), code);
                sentCodes.add(code);

                code++;
            }
            counter++;
        }
    }

    public static String convertInteger(String theValue, int initialBase, int finalBase) {

        BigInteger bigInteger = new BigInteger(theValue, initialBase);
        String value = bigInteger.toString(finalBase);
        value = value.toUpperCase();


        return value;
    }

    public void getStrings() {
        for (int i = 0; i < input.length(); i += 4) {
            String aux = String.valueOf(sentCodes.get(i)) + String.valueOf(sentCodes.get(i + 1))
                    + String.valueOf(sentCodes.get(i + 2)) + String.valueOf(sentCodes.get(i + 3));
            commands.add(aux);
        }

    }


    public void interpreter(String str, int i) {
        switch (str) {
            case "0012":
                if (!jump) {
                    Main.stack.add(BigInteger.ONE);
                }
                            break;
            case "0111":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        String smth = Main.stack.pop().toString();
                        System.out.println(this.convertInteger(smth, 10, Main.base));
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0000":    break;
            case "0001":
                if (!jump) {
                    if (Main.scStdin.hasNext()) {
                        String aux = Main.scStdin.next();
                        BigInteger number = new BigInteger(convertInteger(aux, Main.base, 10));
                        Main.stack.add(number);
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0122":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        Main.stack.pop();
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0011":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        BigInteger first = Main.stack.pop();
                        if (!Main.stack.isEmpty()) {
                            BigInteger second = Main.stack.pop();
                            Main.stack.push(first);
                            Main.stack.push(second);
                        } else {
                            System.err.println("Exception:" + i);
                            System.exit(-2);
                        }
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0101":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        BigInteger dup = Main.stack.peek();
                        Main.stack.push(dup);
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0010":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        BigInteger onTop = new BigInteger(String.valueOf(Main.stack.peek()));
                        Main.stack.pop();
                        Main.stack.add(0, onTop);
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }

                            break;

            case "0102":
                if (!jump) {
                    BigInteger one = BigInteger.ZERO;
                    BigInteger two;
                    BigInteger sum = BigInteger.ZERO;
                    if (!Main.stack.isEmpty()) {
                        one = Main.stack.pop();
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                    if (!Main.stack.isEmpty()) {
                        two = Main.stack.pop();
                        sum = one.add(two);
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                    Main.stack.push(sum);
                }
                            break;
            case "0121":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        BigInteger elem = Main.stack.pop();
                        Main.stack.push(elem.negate());
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0112":
                if (!jump) {
                    if (!Main.stack.isEmpty()) {
                        BigInteger term1 = Main.stack.pop();
                        if (!Main.stack.isEmpty()) {
                            BigInteger term2 = Main.stack.pop();
                            BigInteger prod = term1.multiply(term2);
                            Main.stack.push(prod);
                        } else {
                            System.err.println("Exception:" + i);
                            System.exit(-2);
                        }
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0100":
                if (!jump) {
                    Stack<BigInteger> auxStack = new Stack<>();
                    if (!Main.stack.isEmpty()) {
                        BigInteger ex = Main.stack.get(0);
                        Main.stack.remove(0);
                        Main.stack.push(ex);
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0120":
                if (!jump) {
                String x = new String();
                String y = new String();
                String z = new String();
                String t = new String();
                if (!Main.stack.isEmpty()) {
                    x = Main.stack.pop().toString();
                    if (!Main.stack.isEmpty()) {
                        y = Main.stack.pop().toString();
                        if (!Main.stack.isEmpty()) {
                            z = Main.stack.pop().toString();
                            if (!Main.stack.isEmpty()) {
                                t = Main.stack.pop().toString();
                            } else {
                                System.err.println("Exception:" + (i - 1));
                                System.exit(-2);
                            }
                        } else {
                            System.err.println("Exception:" + (i - 1));
                            System.exit(-2);
                        }
                    } else {
                        System.err.println("Exception:" + (i - 1));
                        System.exit(-2);
                    }
                } else {
                    System.err.println("Exception:" + (i - 1));
                    System.exit(-2);
                }


                int a1, a2, a3, a4;
                a1 = 0;
                if (y.equals(x)) {
                    a2 = a1;
                } else {
                    a2 = a1 + 1;
                }
                if (z.equals(x)) {
                    a3 = a1;
                } else if (z.equals(y)) {
                    a3 = a2;
                } else {
                    a3 = a2 + 1;
                }
                if (t.equals(x)) {
                    a4 = a1;
                } else if (t.equals(y)) {
                    a4 = a2;
                } else if (t.equals(z)) {
                    a4 = a3;
                } else {
                    int aux4 = Integer.max(a1, a2);
                    a4 = Integer.max(a3, aux4) + 1;
                }
                String aux2 = String.valueOf(a1) + String.valueOf(a2)
                        + String.valueOf(a3) + String.valueOf(a4);
                commands.add(i + 1, aux2);
            }
                            break;
            case "0110":
                Main.braces.push(i);
            if (!Main.stack.isEmpty()) {
                jump = (Main.stack.peek().equals(BigInteger.ZERO));
            } else {
                System.err.println("Exception:" + i);
                System.exit(-2);
            }
            break;
            case "0123":
                jump = (Main.stack.peek().equals(BigInteger.ZERO));
                if (!Main.stack.isEmpty()) {
                    if (!Main.braces.isEmpty()) {
                        int start = Main.braces.pop();
                        int end = i;
                        while (!Main.stack.peek().equals(BigInteger.ZERO)) {
                            for (int j = start + 1; j < end; j++) {
                                this.interpreter(commands.get(j), j);
                            }
                        }
                    } else {
                        System.out.println("Error:" + i);
                        System.exit(-1);
                    }
                }
                jump = false;
                break;
        }
    }


}
