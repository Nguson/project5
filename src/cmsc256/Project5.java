/**
 *  Cmsc 256
 *  Minh Nguyen
 *  Project 5
 *  Project 5 takes an equation and returns the root, evaluates the tree, and returns the original equation as well
 *
 */
package cmsc256;
import bridges.base.BinTreeElement;
import bridges.connect.Bridges;
import java.util.Stack;

public class Project5 {

    public static BinTreeElement<String> buildParseTree(String expression) {
        BinTreeElement<String> Root = new BinTreeElement<>("root", "e");
        String[] operationSigns = expression.split(" ");
        Stack<BinTreeElement<String>> variables = new Stack<>();
        /**
         * Switch case looks through the String for specific tokens such as (, ), +, -, *, /, %
         * and sets a new node depending on the operation
         */
        for (String Tokens : operationSigns) {
            if (Tokens == null){
                throw new IllegalArgumentException();
            }
            switch (Tokens) {
                case "(":
                    Root.setLeft(new BinTreeElement<>("Left", "e"));
                    variables.push(Root);
                    Root = Root.getLeft();
                    break;
                case ")":
                    if (!variables.isEmpty()) {
                        Root = variables.pop();
                    }
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                    Root.setLabel(Tokens);
                    Root.setRight(new BinTreeElement<>("right", "e"));
                    variables.push(Root);
                    Root = Root.getRight();
                    break;
                default:
                    try{
                        double temp = Double.parseDouble(Tokens);
                    }
                    catch (Exception e) {
                        throw new IllegalArgumentException();
                }
                    Root.setLabel(Tokens);
                    if (!variables.isEmpty()) {
                        Root = variables.pop();
                    }
                    break;

            }
        }
        return Root;
    }



    public static double evaluate(bridges.base.BinTreeElement<String> tree){

        if(tree == null) {
            return Double.NaN;
        }
        double start;
        double end;
        if(tree.getLeft() == null && tree.getRight() == null){
            return Double.parseDouble(tree.getLabel());
        }
        /**
         * Method will return the result of the operation
         * and its operands
         */
        start = evaluate(tree.getLeft());
        end = evaluate(tree.getRight());
        if(tree.getLabel().equals("+")){
            return start + end;
        }
        else if(tree.getLabel().equals("-")){
            return start - end;
        }
        else if(tree.getLabel().equals("*")){
            return start * end;
        }
        else if(tree.getLabel().equals("%")){
            return start % end;
        }
        if(tree.getLabel().equals("/")){
            if (end == 0){
                throw new ArithmeticException();
            }
            else {
                return start / end;
            }
        }
        return Double.NaN;
    }

    public static String getEquation(bridges.base.BinTreeElement<String> tree){
        /**
         * method returns original equation
         */
        String equation = "";
        if(tree.getLeft() == null && tree.getRight() == null){
            return tree.getLabel();
        }
        equation += "( ";
        equation += getEquation(tree.getLeft());
        equation += " " + tree.getLabel() + " ";
        equation += getEquation(tree.getRight());
        equation += " )";

        return equation;
    }


    public static void main(String[] args){
        String ex1 = "( ( 7 ^ 3 ) * ( 5 - 2 ) )";
        BinTreeElement<String> parseTree1 = buildParseTree(ex1);
        double answer1 = evaluate(parseTree1);
        System.out.println(answer1);
        System.out.println(getEquation(parseTree1));

        String ex2 = "( ( 10 + 5 ) * 3 )";
        BinTreeElement<String>  parseTree2 = buildParseTree(ex2);
        double answer2 = evaluate(parseTree2);
        System.out.println(answer2);
        System.out.println(getEquation(parseTree2));

        String ex3 = "( ( ( ( ( 2 * 12 ) / 6 ) + 3 ) - 17 ) + ( 2 * 0 ) )";
        BinTreeElement<String>  parseTree3 = buildParseTree(ex3);
        double answer3 = evaluate(parseTree3);
        System.out.println(answer3);
        System.out.println(getEquation(parseTree3));

        String ex4 = "( 3 + ( 4 * 5 ) )";
        BinTreeElement<String>  parseTree4 = buildParseTree(ex4);
        double answer4 = evaluate(parseTree4);
        System.out.println(answer4);
        System.out.println(getEquation(parseTree4));

        /* Initialize a Bridges connection */
        Bridges bridges = new Bridges(5,  "nguyenms2", "386889807102");

        /* Set an assignment title */
        bridges.setTitle("Arithmetic Parse Tree Project - Debra Duke");
        bridges.setDescription("CMSC 256, Spring 2022");

        try {
            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree1);
            /* Visualize the Array */
            bridges.visualize();

            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree2);
            /* Visualize the Array */
            bridges.visualize();

            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree3);
            /* Visualize the Array */
            bridges.visualize();

            /* Tell BRIDGES which data structure to visualize */
            bridges.setDataStructure(parseTree4);
            /* Visualize the Array */
            bridges.visualize();
        }
        catch(Exception ex){
            System.out.println("Error connecting to Bridges server.");
        }
    }
}
