package SyntaxTree.Structure;

import SyntaxTree.Structure.BinaryOperators.*;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

/**
 * Created by marsermd on 31.01.2017.
 */
public class ReplaceTest
{
    private Expression createClassicalTree(Predicate predicate, Variable variable)
    {
        return new Implication(
            predicate,
            new Conjunction(
                predicate,
                new Disjunction(
                    new Negation(
                        variable
                    ),
                    predicate
                )
            )
        );
    }

    private Expression createSimpleTree(Predicate predicate, Variable variable, Variable forQuantifiers, Variable underQuantifiers)
    {
        return new Implication(
            predicate,
            new Conjunction(
                predicate,
                new Disjunction(
                    new Negation(
                        new Equals(
                            variable,
                            variable
                        )
                    ),
                    new Disjunction(
                        new Each(
                            forQuantifiers,
                            new Equals(
                                new Multiply(
                                    underQuantifiers,
                                    new Increment(underQuantifiers)
                                ),
                                new Plus(
                                    underQuantifiers,
                                    underQuantifiers
                                )
                            )
                        ),
                        new Some(
                            forQuantifiers,
                            new Equals(
                                underQuantifiers,
                                underQuantifiers
                            )
                        )
                    )
                )
            )
        );
    }

    @Test
    public void classicalTest()
    {
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Predicate simple = new Predicate("A1");
        AnyFormula any = new AnyFormula();

        Expression first = createClassicalTree(simple, a);
        Expression second = createClassicalTree(simple, b);
        Assert.assertFalse(first.fairEquals(second));
        Assert.assertTrue(first.replace(a, any).fairEquals(second));
    }

    @Test
    public void simpleTest()
    {
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Predicate simple = new Predicate("A1");

        Expression aba = createSimpleTree(simple, a, b, a);
        Expression aab = createSimpleTree(simple, a, a, b);
        Expression aaa = createSimpleTree(simple, a, a, a);
        Expression bbb = createSimpleTree(simple, b, b, b);
        Expression baa = createSimpleTree(simple, b, a, a);

        Assert.assertTrue(aba.replace(a, new AnyFormula()).fairEquals(bbb));
        Assert.assertTrue(aab.replace(b, new AnyFormula()).fairEquals(aaa));
        Assert.assertTrue(aaa.replace(a, new AnyFormula()).fairEquals(baa));
    }
}
