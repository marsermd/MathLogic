package ProofChecker.ExpressionCheckers.InferenceRules;

import ProofChecker.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Predicate;
import org.junit.Assert;
import org.junit.Test;

public class ModusPonensTest
{

    @Test
    public void testMatches()
    {
        Proof proof = new Proof();
        proof.addLine(new Predicate("A"));
        proof.addLine(new Implication(
            new Predicate("A"),
            new Predicate("B")
        ));
        proof.addLine(
            new Predicate("B")
        );
        proof.addLine(
            new Predicate("C")
        );

        ModusPonens modusPonens = new ModusPonens();
        Assert.assertTrue(modusPonens.checkMatches(proof, 2).isRight());
        Assert.assertFalse(modusPonens.checkMatches(proof, 3).isRight());
    }

    @Test
    public void testMatchesComplex()
    {
        Proof proof = new Proof();
        proof.addLine(new Implication(
            new Predicate("A"),
            new Predicate("B")
        ));

        proof.addLine(
            new Predicate("A")
        );
        proof.addLine(new Implication(
            new Predicate("A"),
            new Implication(
                new Predicate("B"),
                new Predicate("C")
            )
        ));
        proof.addLine(
            new Predicate("B")
        );
        proof.addLine(
            new Predicate("C")
        );
        proof.addLine(
            new Implication(
                new Predicate("B"),
                new Predicate("C")
            )
        );

        ModusPonens modusPonens = new ModusPonens();
        Assert.assertFalse(modusPonens.checkMatches(proof, 3).isRight());
        Assert.assertFalse(modusPonens.checkMatches(proof, 4).isRight());
        Assert.assertTrue(modusPonens.checkMatches(proof, 5).isRight());
    }
}