package DerivationSolver.TrigFunctions.Hyperbolic;

import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class TanhRule extends TrigRule {

    public TanhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makeAdditionRule(new Term(1), rf.makePowerRule(rf.makeTanhRule(this.terms.get(0)), new Term(2)).flipSign());
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
