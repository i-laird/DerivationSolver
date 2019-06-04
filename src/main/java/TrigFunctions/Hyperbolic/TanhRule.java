package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;

public class TanhRule extends DerivationRule {

    public TanhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeAdditionRule(new Term(1), rf.makePowerRule(rf.makeTanhRule(this.terms.get(0)), new Term(2)).flipSign());
    }
}