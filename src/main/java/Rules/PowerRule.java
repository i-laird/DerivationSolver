package Rules;

import java.util.LinkedList;
import java.util.List;

import Terms.Term;


public class PowerRule extends DerivationRule {
    public PowerRule(LinkedList<Term> l) {
        super(l);
    }

    //TODO fix the ordering
    @Override
    public Term getDerivative() {
        int pow = this.terms.get(0).getNum();
        Term base = this.terms.get(1);

        //if it is not zero we just do a simple multiplication
        if (pow != 0){
            return rf.makeProductRule(this.negative ? new Term(pow * -1) : new Term(pow), rf.makePowerRule(base, new Term(pow - 1)));
        }

        return new Term(1);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return (int)Math.pow((double)this.terms.get(0).evaluate(dims), (double)this.terms.get(1).evaluate(dims));
    }

}
