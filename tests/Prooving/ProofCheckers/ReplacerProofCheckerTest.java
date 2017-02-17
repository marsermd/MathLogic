package Prooving.ProofCheckers;

import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Boolean.BooleanProofChecker;
import Prooving.ProofCheckers.Boolean.BooleanProofResult;
import SyntaxTree.Parser.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 06.02.2017.
 */
public class ReplacerProofCheckerTest
{
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
