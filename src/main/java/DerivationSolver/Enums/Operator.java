package DerivationSolver.Enums;

import DerivationSolver.Rules.AdditionRule;
import DerivationSolver.Rules.ProductRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.Terms.Variable;

public enum Operator implements AbstractMath{
    ADD(1, Associativity.LEFT), SUBTRACT(1, Associativity.LEFT), MULTIPLY(2, Associativity.LEFT), DIVIDE(2, Associativity.LEFT), EXPONENT(3, Associativity.RIGHT), LOG(3, Associativity.RIGHT), NAT_LOG(3, Associativity.RIGHT);
    public static enum Associativity{
        RIGHT, LEFT
    }
    public int precedence;
    public Associativity associativity;

    Operator(int p, Associativity a){
        precedence = p;
        associativity = a;
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
        Term toReturn = null;
        boolean specialCase = false;
        switch(this) {
            case NAT_LOG:
                toReturn = rf.makeNaturalLogRule(two);
                specialCase = true;
                break;
            case LOG:
                toReturn = rf.makeLogRule(one, two);
                specialCase = true;
                break;
            case SUBTRACT:
                one.flipSign();
            case ADD:
                // see if an addition is being made to an existing addtion
                if(one.getClass() == AdditionRule.class){
                    toReturn = ((AdditionRule)one).addTerm(two);
                }
                else if (two.getClass() == AdditionRule.class){
                    toReturn = ((AdditionRule)two).addTerm(one);
                }

                // if not create a new addition
                else {
                    toReturn = rf.makeAdditionRule(one, two);
                }
                break;
            case MULTIPLY:
                // see if an mult is being made to an existing mult
                if(one.getClass() == ProductRule.class){
                    toReturn = ((ProductRule)one).addTerm(two);
                }
                else if (two.getClass() == ProductRule.class){
                    toReturn = ((ProductRule)two).addTerm(one);
                }

                // if not create a new mult
                else {
                    toReturn = rf.makeProductRule(one, two);
                }
                break;
            case DIVIDE:
                toReturn = rf.makeFracRule(one, two);
                break;
            case EXPONENT:
                toReturn = rf.makePowerRule(one, two);
                specialCase = true;
                break;
            default:
                throw new RuntimeException("Invalid DerivationSolver.Enums.Operator");
        }
            // see if the rule needs to be wrapped in a chain rule
            // if it is just one variable inside there is no need for the chain rule because of the implied one
            if(specialCase && one.getClass() != Variable.class){
                toReturn = rf.makeChainRule(toReturn, two);
            }
            return toReturn;
    }
}
