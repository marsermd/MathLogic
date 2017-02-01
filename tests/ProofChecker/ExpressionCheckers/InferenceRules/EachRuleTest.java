package ProofChecker.ExpressionCheckers.InferenceRules;

import ProofChecker.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Predicate;
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
        Assert.assertTrue(rule.Matches(proof, 1));
        Assert.assertFalse(rule.Matches(proof, 2));
        Assert.assertTrue(rule.Matches(proof, 3));
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
            Assert.assertTrue(rule.Matches(proof, 1));
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
            Assert.assertFalse(rule.Matches(proof, 1));
        }
    }
}
