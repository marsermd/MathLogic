package Prooving.ProofCheckers;

import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Rewriter.RewriterProofChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Parser.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 06.02.2017.
 */
@RunWith(Parameterized.class)
public class ReplacerProofCheckerTest
{
    @Parameterized.Parameters
    public static Iterable<? extends Object[]> data()
    {
        List<Object[]> result = new ArrayList<Object[]>();
        for (int i = 1; i <= 13; i++)
        {
            // to big files
            if (i != 11 && i != 14)
                result.add(new Object[]{"correct" + i + ".in"});
        }
        for (int i = 1; i <= 11; i++)
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
        System.err.println(fileName);
        File file = new File("testResources/" + fileName);
        Proof proof = ProofParser.parseProof(file, Parser.createDefault());

        RewriterProofChecker proofChecker = RewriterProofChecker.getDeductChecker(proof);
        RewriterProofResult result = proofChecker.Check();

        System.err.println(result.failed);
        System.err.println(result.failureReason);

        Assert.assertEquals(fileName.contains("incorrect"), result.failed);
    }
}
