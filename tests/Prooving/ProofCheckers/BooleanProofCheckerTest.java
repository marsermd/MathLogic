package Prooving.ProofCheckers;

import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Boolean.BooleanProofChecker;
import Prooving.ProofCheckers.Boolean.BooleanProofResult;
import SyntaxTree.Parser.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RunWith(Parameterized.class)
public class BooleanProofCheckerTest
{

    @Test
    public void testSomeQuantifierProofCheckResult() throws Exception
    {
        Proof proof = new Proof();
        String[] lines = new String[]{
            "B->A->B",
            "A->B->C",
            "(A->B->C)->B->A->B->C",
            "B->A->B->C",
            "(A->B)->(A->B->C)->A->C",
            "((A->B)->(A->B->C)->A->C)->B->(A->B)->(A->B->C)->A->C",
            "B->(A->B)->(A->B->C)->A->C",
            "(B->A->B)->(B->(A->B)->(A->B->C)->A->C)->B->(A->B->C)->A->C",
            "(B->(A->B)->(A->B->C)->A->C)->B->(A->B->C)->A->C",
            "B->(A->B->C)->A->C",
            "(B->A->B->C)->(B->(A->B->C)->A->C)->B->A->C",
            "(B->(A->B->C)->A->C)->B->A->C",
            "B->A->C",
            "?xB->A->C",
            "A->?xB->A",
            "?xB->A->C",
            "(?xB->A->C)->A->?xB->A->C",
            "A->?xB->A->C",
            "(?xB->A)->(?xB->A->C)->?xB->C",
            "((?xB->A)->(?xB->A->C)->?xB->C)->A->(?xB->A)->(?xB->A->C)->?xB->C",
            "A->(?xB->A)->(?xB->A->C)->?xB->C",
            "(A->?xB->A)->(A->(?xB->A)->(?xB->A->C)->?xB->C)->A->(?xB->A->C)->?xB->C",
            "(A->(?xB->A)->(?xB->A->C)->?xB->C)->A->(?xB->A->C)->?xB->C",
            "A->(?xB->A->C)->?xB->C",
            "(A->?xB->A->C)->(A->(?xB->A->C)->?xB->C)->A->?xB->C",
            "(A->(?xB->A->C)->?xB->C)->A->?xB->C",
            "A->?xB->C"
        };
        for (String line : lines)
        {
            proof.addLine(Parser.parseDefault(line));
        }

        BooleanProofChecker proofChecker = new BooleanProofChecker(proof, ProofChecker.getFormalArithmeticsCheckers());
        BooleanProofResult result = proofChecker.Check();

        Assert.assertTrue(result.lastExpressionResult.isWrong());

        proof.assumeThat(Parser.parseDefault("A->B->C"));

        result = proofChecker.Check();
        Assert.assertTrue(result.lastExpressionResult.isRight());
    }

    @Test
    public void testEachQuantifierProofCheckResult() throws Exception
    {
        Proof proof = new Proof();
        String[] lines = new String[]{
            "A->B->C",
            "A&B->A",
            "A&B->B",
            "A->B->C",
            "(A->B->C)->A&B->A->B->C",
            "A&B->A->B->C",
            "(A&B->A)->(A&B->A->B->C)->A&B->B->C",
            "(A&B->A->B->C)->A&B->B->C",
            "A&B->B->C",
            "(A&B->B)->(A&B->B->C)->(A&B->C)",
            "(A&B->B->C)->(A&B->C)",
            "A&B->C",
            "A&B->@xC",
            "A->B->A&B",
            "A&B->@xC",
            "(A&B->@xC)->A->A&B->@xC",
            "A->A&B->@xC",
            "(A&B->@xC)->B->A&B->@xC",
            "((A&B->@xC)->B->A&B->@xC)->A->(A&B->@xC)->B->A&B->@xC",
            "A->(A&B->@xC)->B->A&B->@xC",
            "(A->A&B->@xC)->(A->((A&B->@xC)->B->A&B->@xC))->A->B->A&B->@xC",
            "(A->((A&B->@xC)->B->A&B->@xC))->A->B->A&B->@xC",
            "A->B->A&B->@xC",
            "(B->A&B)->(B->A&B->@xC)->B->@xC",
            "((B->A&B)->(B->A&B->@xC)->B->@xC)->A->(B->A&B)->(B->A&B->@xC)->B->@xC",
            "A->(B->A&B)->(B->A&B->@xC)->B->@xC",
            "(A->B->A&B)->(A->(B->A&B)->(B->A&B->@xC)->B->@xC)->A->(B->A&B->@xC)->B->@xC",
            "(A->(B->A&B)->(B->A&B->@xC)->B->@xC)->A->(B->A&B->@xC)->B->@xC",
            "A->(B->A&B->@xC)->B->@xC",
            "(A->B->A&B->@xC)->(A->(B->A&B->@xC)->B->@xC)->A->B->@xC",
            "(A->(B->A&B->@xC)->B->@xC)->A->B->@xC",
            "A->B->@xC"
        };
        for (String line : lines)
        {
            proof.addLine(Parser.parseDefault(line));
        }

        BooleanProofChecker proofChecker = new BooleanProofChecker(proof, ProofChecker.getFormalArithmeticsCheckers());
        BooleanProofResult result = proofChecker.Check();

        Assert.assertTrue(result.lastExpressionResult.isWrong());

        proof.assumeThat(Parser.parseDefault("A->B->C"));

        result = proofChecker.Check();
        Assert.assertTrue(result.lastExpressionResult.isRight());
    }

    @Parameterized.Parameters
    public static Iterable<? extends Object[]> data()
    {
        List<Object[]> result = new ArrayList<Object[]>();
        for (int i = 1; i <= 14; i++)
        {
            // to big files
            if (i != 11 && i != 14)
                result.add(new Object[]{"correct" + i + ".in"});
        }
        for (int i = 1; i <= 10; i++)
        {
            // these files are only valid for proof rewriter
            if (i != 1 && i != 4 && i != 9)
                result.add(new Object[]{"incorrect" + i + ".in"});
        }
        return result;
    }

    @Parameterized.Parameter
    public String fileName;

    @Test
    public void runOnFiles() throws FileNotFoundException
    {
        System.out.println(fileName);
        File file = new File("testResources/" + fileName);
        Proof proof = ProofParser.parseProof(file, Parser.createDefault());

        BooleanProofChecker proofChecker = new BooleanProofChecker(proof, ProofChecker.getFormalArithmeticsCheckers());
        BooleanProofResult result = proofChecker.Check();

        System.err.println(result.lineID);
        System.err.println(result.lastExpressionResult.reason);

        Assert.assertEquals(!fileName.contains("incorrect"), result.lastExpressionResult.isRight());
    }
}