package DerivationSolver.Rules;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Parser;
import DerivationSolver.Terms.Term;

/**
 * PRODUCT RULE
 *
 * this is used when multiple terms are multiplied together.
 *
 * This class supports a product rule of more than two terms but {@link Parser}
 * will not construct AST that have more than two kid
 */
public class ProductRule extends DerivationRule {


    /**
     * d/dx( f(x) * g(x) ) = f(x) * g'(x) + f'(x) * g(x)
     * @param original the original terms
     * @param derived the derivation of the terms
     * @return
     */
    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        //need to account for a variable number of terms that are being multipled
        if(original.size() != derived.size()){
            throw new RuntimeException("The sizes do not match");
        }
        LinkedList<Term> addTerms = new LinkedList<>();
        for(int i = 0; i < original.size(); i++){
            LinkedList<Term> multTerms = new LinkedList<>();
            for(int j = 0; j < original.size(); j++){
                if(i != j){
                    multTerms.add(original.get(j));
                }
                else{
                    multTerms.add(derived.get(j));
                }
            }
            addTerms.add(new ProductRule(multTerms));
        }
        AdditionRule toReturn = new AdditionRule(addTerms);
        if(this.negative){
            toReturn.flipSign();
        }
        return toReturn;
    }

    public ProductRule addTerm(Term t){
        this.terms.add(t);
        return this;
    }

    public ProductRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return this.terms.stream().map(x -> x.evaluate(dims)).reduce((x,y) -> x*y).get();
    }

}
