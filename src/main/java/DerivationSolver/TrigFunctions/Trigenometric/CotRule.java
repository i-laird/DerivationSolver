package DerivationSolver.TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Rules.DerivationRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;


public class CotRule extends TrigRule {

    public CotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx cot(x) = -csc(x)^2
        return DerivationRule.rf.makePowerRule(DerivationRule.rf.makeCscRule(this.terms.get(0)), new Term(2).flipSign());
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.tan(this.terms.get(0).evaluate(dims));
    }
}
