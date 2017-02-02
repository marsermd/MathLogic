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
public class EachRuleTest
{
    @Test
    public void testMatches()
    {
        Proof proof = new Proof();
        proof.addLine(new Implication(
            new Variable("a"),
            new Variable("b")
        ));
        proof.addLine(
            new Implication(
                new Each(
                    new Variable("a"),
                    new Variable("a")
                ),
                new Variable("b")
            )
        );
        proof.addLine(
            new Implication(
                new Each(
                    new Variable("a"),
                    new Variable("a")
                ),
                new Variable("c")
            )
        );
        proof.addLine(
            new Implication(
                new Each(
                    new Variable("c"),
                    new Variable("a")
                ),
                new Variable("b")
            )
        );

        EachRule rule = new EachRule();
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
                new Variable("a"),
                new Some(
                    new Variable("b"),
                    new Variable("b")
                )
            ));
            proof.addLine(
                new Implication(
                    new Each(
                        new Variable("b"),
                        new Variable("a")
                    ),
                    new Some(
                        new Variable("b"),
                        new Variable("b")
                    )
                )
            );

            EachRule rule = new EachRule();
            Assert.assertTrue(rule.checkMatches(proof, 1).isRight());
        }
        {
            Proof proof = new Proof();
            proof.addLine(new Implication(
                new Variable("a"),
                new Variable("b")
            ));
            proof.addLine(
                new Implication(
                    new Each(
                        new Variable("b"),
                        new Variable("a")
                    ),
                    new Variable("b")
                )
            );

            EachRule rule = new EachRule();
            Assert.assertFalse(rule.checkMatches(proof, 1).isRight());
        }
    }
}
