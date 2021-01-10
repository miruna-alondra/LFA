import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Interpreter {
    public String input;
    public HashMap<Character, Integer> hashMap = new HashMap<>();
    public ArrayList<Integer> sentCodes = new ArrayList<>();
    public ArrayList<String> commands = new ArrayList<>();
    public ArrayList<String> loops = new ArrayList<>();
    public boolean jump = false;
    public int openBracePos = 0;
    public int closedBracePos = 0;

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

    public void getStrings() {
        for (int i = 0; i < input.length(); i += 4) {
            String aux = String.valueOf(sentCodes.get(i)) + String.valueOf(sentCodes.get(i + 1))
                    + String.valueOf(sentCodes.get(i + 2)) + String.valueOf(sentCodes.get(i + 3));
            commands.add(aux);
        }

    }
    public void findBraces(ArrayList<String> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).equals("0110")) {
                openBracePos = i;
            }
            if (commands.get(i).equals("0123")) {
                closedBracePos = i;
            }
        }
        for (int i = openBracePos + 1; i < closedBracePos; i++) {
            loops.add(commands.get(i));
        }
        System.out.println(loops);

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
                        System.out.println(Main.stack.pop());
                    } else {
                        System.err.println("Exception:" + i);
                        System.exit(-2);
                    }
                }
                            break;
            case "0000":    break;
            case "0001":
                if (!jump) {
                    if (Main.scStdin.hasNextBigInteger()) {
                        BigInteger aux = Main.scStdin.nextBigInteger();
                        Main.stack.add(aux);
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
            /*case "0110":    Main.braces.push("[");
                if (!Main.stack.isEmpty()) {
                    jump = (Main.stack.peek() == BigInteger.ZERO);
                    if (!jump) {
                        for (int j = 0; j < loops.size(); j++) {
                            this.interpreter(loops.get(j), j);
                        }
                    }
                } else {
                    System.err.println("Exception:" + i);
                    System.exit(-2);
                }
                            break;
            case "0123":
                if (!Main.stack.isEmpty()) {
                    Main.braces.pop();
                    jump = (Main.stack.peek() != BigInteger.ZERO);
                    this.findBraces(commands);
                } else {
                    System.err.println("Exception:" + i);
                    System.exit(-2);
                }
                            break;*/
            case "0110":
                Main.braces.push(i);
            if (!Main.stack.isEmpty()) {
                if (Main.stack.peek() == BigInteger.ZERO) {
                    jump = true;
                } else {
                    jump = false;
                }
            } else {
                System.err.println("Exception:" + i);
                System.exit(-2);
            }
            break;
            case "0123":
                if (!Main.stack.isEmpty()) {
                    if (!Main.braces.isEmpty()) {
                        int start = Main.braces.pop();
                        int end = i;
                        if (Main.stack.peek() != BigInteger.ZERO) {
                            jump = false;
                        } else {
                            jump = true;
                        }
                        while (Main.stack.peek() != BigInteger.ZERO) {
                            for (int j = start + 1; j < end; j++) {
                                this.interpreter(commands.get(j), j);
                            }
                        }
                    }
                }
                break;
        }
    }


}
