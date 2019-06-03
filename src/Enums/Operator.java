package Enums;

import Rules.RuleFactory;
import Terms.Term;

public enum Operator implements AbstractMath{
    ADD(1), SUBTRACT(1), MULTIPLY(2), DIVIDE(2), EXPONENT(3), LOG(3), NAT_LOG(3);
    public int precedence;

    Operator(int p){
        precedence = p;
    }

    public static Operator getOp(String s){
        switch(s){
            case "ln":
                return NAT_LOG;
            case "log":
                return LOG;
            case "+":
                return ADD;
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "^":
                return EXPONENT;
        }
        return null;
    }

    public Term getTermFromOp(Term one, Term two){
        switch(this){
            case NAT_LOG:
                return rf.makeNaturalLogRule(one);
            case LOG:
                return rf.makeLogRule(one, two);
            case ADD:
                return rf.makeAdditionRule(one, two);
            case SUBTRACT:
                return rf.makeAdditionRule(one, two.flipSign());
            case MULTIPLY:
                return rf.makeProductRule(one, two);
            case DIVIDE:
                return rf.makeFracRule(one, two);
            case EXPONENT:
                return rf.makePowerRule(one, two);
        }
        throw new RuntimeException("Invalid Enums.Operator");
    }
}