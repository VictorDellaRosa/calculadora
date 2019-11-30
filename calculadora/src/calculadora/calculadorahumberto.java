package calculadora;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class calculadorahumberto
{

    static final HashMap<String, Integer> prec;

    static
    {
        prec = new HashMap<>();
        prec.put("^", 3);
        prec.put("%", 2);
        prec.put("*", 2);
        prec.put("/", 2);
        prec.put("+", 1);
        prec.put("-", 1);
    }

    public static void main(String[] args)
    {

        Queue<String> infixQueue = new LinkedList<>(); 
        Scanner tecladin = new Scanner(System.in);
        Double numero = 0.0;
        Character algarismo, algarismoNext = ' ';
        String colocar;
        String componentes = "";
        do
        {
            System.out.println("Por favor, ponha a expressão que deseja transformar em notação polonesa reversa: ");
            colocar = tecladin.nextLine();
            colocar = colocar.replaceAll(" ", ""); 
            if (colocar.equals("quit"))
            {
                System.exit(0);
            }

            for (int i = 0; i < colocar.length(); i++)
            {
                algarismo = colocar.charAt(i);
                if (i + 1 < colocar.length())
                {
                    algarismoNext = colocar.charAt(i + 1);
                }

                if (algarismo.equals('(') || algarismo.equals(')'))
                {
                    if (algarismo.equals('(') && algarismoNext.equals('-'))
                    {
                        System.out.println("Não é possível fazer com número negativo.");
                        main(args);
                    } else
                    {
                        infixQueue.add(algarismo.toString());
                    }
                } else if (!Character.isDigit(algarismo))
                {
                    if (infixQueue.isEmpty() && algarismo.equals('-'))
                    {
                        System.out.println("Não é possível fazer com número negativo.");
                        main(args);
                    } else if (algarismoNext.equals('-'))
                    {
                        System.out.println("Não é possível fazer com número negativo.");
                        main(args);
                    } else
                    {
                        infixQueue.add(algarismo.toString());
                    }
                } else if (Character.isDigit(algarismo))
                {
                    if (i + 1 < colocar.length() && colocar.charAt(i + 1) == '.') //to handle decimal
                    {
                        int j = i + 1;
                        componentes = algarismo.toString() + colocar.charAt(j); //to handle multidigit
                        while (j + 1 <= colocar.length() - 1 && Character.isDigit(colocar.charAt(j + 1)))
                        {
                        	componentes = componentes + colocar.charAt(j + 1);
                            j++;
                        }
                        i = j;
                        infixQueue.add(componentes);
                        componentes = "";
                    } else if (i + 1 <= colocar.length() - 1 && Character.isDigit(colocar.charAt(i + 1)))
                    {
                        int j = i;
                        //multiDigit=c.toString()+input.charAt(i);
                        while (j <= colocar.length() - 1 && Character.isDigit(colocar.charAt(j)))
                        {
                        	componentes = componentes + colocar.charAt(j);
                            j++;
                        }
                        i = j - 1;
                        infixQueue.add(componentes);
                        componentes = "";
                    } else
                    {
                        infixQueue.add(algarismo.toString());
                    }

                }
            }

            TransformarEquação(infixQueue);
        } while (!colocar.equals("quit"));
    }

    
    public static void TransformarEquação(Queue<String> infixQueue)
    {
        Stack operatorStack = new Stack();
        Queue<String> postQueue = new LinkedList<>();
        String t;
        while (!infixQueue.isEmpty())
        {
            t = infixQueue.poll();
            try
            {
                double num = Double.parseDouble(t);
                postQueue.add(t);
            } catch (NumberFormatException nfe)
            {
                if (operatorStack.isEmpty())
                {
                    operatorStack.add(t);
                } else if (t.equals("("))
                {
                    operatorStack.add(t);
                } else if (t.equals(")"))
                {
                    while (!operatorStack.peek().toString().equals("("))
                    {
                        postQueue.add(operatorStack.peek().toString());
                        operatorStack.pop();
                    }
                    operatorStack.pop();
                } else
                {
                    while (!operatorStack.empty() && !operatorStack.peek().toString().equals("(") && prec.get(t) <= prec.get(operatorStack.peek().toString()))
                    {
                        postQueue.add(operatorStack.peek().toString());
                        operatorStack.pop();
                    }
                    operatorStack.push(t);
                }
            }
        }
        while (!operatorStack.empty())
        {
            postQueue.add(operatorStack.peek().toString());
            operatorStack.pop();
        }
        System.out.println();
        System.out.println("Sua expressão posfixa é: ");
        //numbers and operators all seperated by 1 space.
        for (String val : postQueue)
        {
            System.out.print(val + " ");
        }
        ResultadodaPosfixa(postQueue);
    }

    //method to calculate the reuslt of postfix expression.
    
    public static void ResultadodaPosfixa(Queue<String> postQueue)
    {
        Stack<String> eval = new Stack<>(); //Standard Stack class provided by Java Framework.
        String t;
        Double headNumber, nextNumber, result = 0.0;
        while (!postQueue.isEmpty())
        {
            t = postQueue.poll();
            try
            {
                double num = Double.parseDouble(t);
                eval.add(t);
            } catch (NumberFormatException nfe)
            {
                headNumber = Double.parseDouble(eval.peek());
                eval.pop();
                nextNumber = Double.parseDouble(eval.peek());
                eval.pop();

                switch (t)
                {
                    case "+":
                        result = nextNumber + headNumber;
                        break;
                    case "-":
                        result = nextNumber - headNumber;
                        break;
                    case "*":
                        result = nextNumber * headNumber;
                        break;
                    case "/":
                        //in java, there is no exception generated when divided by zero and thus checking
                        //for 
                        if (headNumber == 0)
                        {
                            System.out.println("\nERROR: Cannot Divide by zero!\n");
                            return;
                        } else
                        {
                            result = nextNumber / headNumber;
                            break;
                        }
                    case "%":
                        result = nextNumber % headNumber;
                        break;
                    case "^":
                        result = Math.pow(nextNumber, headNumber);
                        break;

                }

                eval.push(result.toString());

            }

        }
        //Outputing the result in this function to verify that the o/p is a result of postfix expression.
        //Outputing the result to 3 decimal places
        System.out.println("\nO resultado da sua expressão é: ");
        DecimalFormat formadecimal = new DecimalFormat("0.000");
        for (String val : eval)
        {
            System.out.println(formadecimal.format(Double.parseDouble(val)) + "\n");
        }
    }

}