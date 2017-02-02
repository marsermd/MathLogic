package ProofChecker.ExpressionCheckers.InferenceRules;

import ProofChecker.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by marsermd on 01.02.2017.
 */
public class SomeRuleTest
{
    @Test
    public void testMatches()
    {
        Proof proof = new Proof();
        proof.addLine(new Implication(
            new Variable("b"),
            new Variable("a")
        ));
        proof.addLine(
            new Implication(
                new Variable("b"),
                new Some(
                    new Variable("a"),
                    new Variable("a")
                )
            )
        );
        proof.addLine(
            new Implication(
                new Variable("c"),
                new Some(
                    new Variable("a"),
                    new Variable("a")
                )
            )
        );
        proof.addLine(
            new Implication(
                new Variable("b"),
                new Some(
                    new Variable("c"),
                    new Variable("a")
                )
            )
        );

        SomeRule rule = new SomeRule();
        Assert.assertTrue(rule.checkMatches(proof, 1).isRight());
        Assert.assertFalse(rule.checkMatches(proof, 2).isRight());
        Assert.assertTrue(rule.checkMatches(proof, 3).isRight());
    }

    @Test
    public void testMatchesBound()
    {
        {
            Proof proof = new Proof();
            proof.addLine(new Implication(
                new Each(
                    new Variable("b"),
                    new Variable("b")
                ),
                new Variable("a")
            ));
            proof.addLine(
                new Implication(
                    new Each(
                        new Variable("b"),
                        new Variable("b")
                    ),
                    new Some(
                        new Variable("b"),
                        new Variable("a")
                    )
                )
            );

            SomeRule rule = new SomeRule();
            Assert.assertTrue(rule.checkMatches(proof, 1).isRight());
        }
        {
            Proof proof = new Proof();
            proof.addLine(new Implication(
                new Variable("b"),
                new Variable("a")
            ));
            proof.addLine(
                new Implication(
                    new Variable("b"),
                    new Some(
                        new Variable("b"),
                        new Variable("a")
                    )
                )
            );

            SomeRule rule = new SomeRule();
            Assert.assertFalse(rule.checkMatches(proof, 1).isRight());
        }
    }
}
