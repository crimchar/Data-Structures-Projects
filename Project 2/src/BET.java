import java.util.EmptyStackException;
import java.util.Stack;

public class BET {


    public class BinaryNode {

        public String elem;
        public BinaryNode parent;
        public BinaryNode left;
        public BinaryNode right;

        BinaryNode(String val,BinaryNode a, BinaryNode b, BinaryNode c) {

            elem = val;

            parent = a;

            left = b;

            right = c;
        }

    }

    public BinaryNode root = new BinaryNode(null,null,null,null);

    public BET() {

        root = new BinaryNode("", null, null, null);
    }

    public BET(String expr, char mode) {

        if (mode == 'p') {

            boolean pass = buildFromPostfix(expr);

            if (!pass)
                throw new IllegalStateException("Invalid notation: " + expr);

        }

        else if(mode == 'i') {

            boolean pass = buildFromInfix(expr);

            if (!pass)
                throw new IllegalStateException("Invalid notation: " + expr);

        }

        else
            throw new IllegalStateException("Invalid notation: " + expr);

    }

    public boolean buildFromPostfix(String postfix) {

        Stack<BinaryNode> postStack = new Stack<BinaryNode>();

        String[] postArr = postfix.split(" ");  //creates array of strings to  get rid of spaces

        makeEmpty(root);

        for (String p : postArr) {

            switch(p) {

                case ("*"):
                case ("/"):
                case ("+"):
                case ("-"):

                    if (postStack.size() < 2)
                        return false;

                    BinaryNode rightChild = postStack.pop();

                    BinaryNode leftChild = postStack.pop();

                    BinaryNode operator = new BinaryNode(p, null, leftChild, rightChild);

                    root = operator;

                    postStack.push(operator);

                    break;

                default:

                    BinaryNode operand = new BinaryNode(p,null,  null, null);

                    postStack.push(operand);

            }
        }

        return postStack.size() == 1;

    }

    public boolean buildFromInfix(String infix) throws EmptyStackException {

        Stack<BinaryNode> operandsStack = new Stack<BinaryNode>();

        Stack<BinaryNode> operatorStack = new Stack<BinaryNode>();

        String[] inArr = infix.split(" ");

        makeEmpty(root);

        for (String p : inArr) {

            switch(p) {

                case ("*"):
                case ("/"):

                    if (operatorStack.empty()) {

                        BinaryNode proto = new BinaryNode(p, null, null, null);

                        operatorStack.push(proto);

                        break;
                    }

                    while (operatorStack.peek().elem.equals("*") || operatorStack.peek().elem.equals("/")) { //keep adding to operands stack until no longer can

                        try {

                            infixAllPushPop(operandsStack, operatorStack);
                        }
                        catch(Exception e) {

                            return false;
                        }

                        if (operatorStack.empty())  //without this there are empty stack exceptions
                            break;
                        }

                    BinaryNode proto = new BinaryNode(p, null, null, null);

                    operatorStack.push(proto);

                    break;

                case ("+"):
                case ("-"):

                    if (operatorStack.empty() || operatorStack.peek().elem.equals("(")) {    //stop adding to operands if there is a (

                        BinaryNode operator = new BinaryNode(p, null, null, null);

                        operatorStack.push(operator);

                        break;

                    }

                    while (!operatorStack.empty() || !operatorStack.peek().elem.equals("(")) {  //keeps adding to operands until no longer can

                        try {

                            infixAllPushPop(operandsStack, operatorStack);
                        }
                        catch(Exception e) {

                            return false;
                        }

                        if (operatorStack.empty())  //without this there are empty stack exceptions
                            break;
                    }

                    BinaryNode operator = new BinaryNode(p, null, null, null);

                    operatorStack.push(operator);

                    break;

                case ("("):

                    BinaryNode operatorPar = new BinaryNode(p, null, null, null);

                    operatorStack.push(operatorPar);

                    break;

                case (")"):

                    if (operatorStack.empty())
                        return false;

                    while(!operatorStack.peek().elem.equals(("("))) {

                        try {

                            infixAllPushPop(operandsStack, operatorStack);
                        }
                        catch(Exception e) {

                            return false;
                        }
                    }

                    operatorStack.pop();

                    break;

                default:

                    BinaryNode operand = new BinaryNode(p, null, null, null);

                    operandsStack.push(operand);
            }
        }

        while (!operatorStack.empty()) {

            if(operandsStack.empty())
                break;

            try {

                infixAllPushPop(operandsStack, operatorStack);
            }
            catch(Exception e) {

                return false;
            }
        }

        root = operandsStack.pop();

        operandsStack.push(root);

        return operatorStack.empty() && operandsStack.size() == 1;
    }

    private void infixAllPushPop(Stack<BinaryNode> operandsStack, Stack<BinaryNode> operatorStack) { //pops 2 operands and makes them children of operator and push operator to operands stack

        if (operandsStack.empty())
            return;

        BinaryNode operatorParent = operatorStack.pop();

        BinaryNode rightChild = operandsStack.pop();

        BinaryNode leftChild = operandsStack.pop(); //my IDE is telling me this is redundant cuz i can shorten 4 lines into 2 but this is more readable

        rightChild.parent = operatorParent;

        leftChild.parent = operatorParent;

        operatorParent.left = leftChild;

        operatorParent.right = rightChild;

        operandsStack.push(operatorParent);
    }

    public void printInfixExpression() {

        printInfixExpression(root);
        System.out.println("");
    }

    public void printPostfixExpression () {

        printPostfixExpression(root);
        System.out.println("");
    }

    public int size() {

        return size(root);
    }

    public boolean isEmpty() {

        return (root.left == null && root.right == null && root.elem == null);
    }

    public int leafNodes() {

        return leafNodes(root);
    }

    private void printInfixExpression(BinaryNode n) {

        if (n.left != null) {

            System.out.print("( ");
            printInfixExpression(n.left);
        }

        System.out.print(n.elem + " ");

        if (n.right != null) {

            printInfixExpression(n.right);
            System.out.print(") ");
        }

    }

    private void makeEmpty(BinaryNode t) {  //i honestly don't know what this is used for, you can just do root = new (null null null...)

        if (t.left == null && t.right == null) {

            t = null;
            return;
        }

        if (t.left != null)
            makeEmpty(t.left);

        if (t.right != null)
            makeEmpty(t.right);

    }

    private void printPostfixExpression(BinaryNode n) {

        if  (n.left != null)
            printPostfixExpression(n.left);

        if (n.right!= null)
            printPostfixExpression(n.right);

        System.out.print(n.elem + " ");

    }

    private int size (BinaryNode t) {

        if (t == null)
            return 0;

        return (1 + size(t.left) + size(t.right));
    }

    private int leafNodes(BinaryNode t) {

        if (t == null)
            return 0;

        if (t.left == null && t.right == null)
            return 1;

        return(leafNodes(t.left) + leafNodes(t.right));
    }
}
