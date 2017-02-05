import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.BooleanProofChecker;
import Prooving.ProofCheckers.ProofChecker;
import Prooving.ProofCheckers.Results.BooleanProofResult;
import SyntaxTree.Parser.Parser;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        File file = new File("testResources/" + "correct11.in");

        System.out.println("parsing");
        Proof proof = ProofParser.parseProof(file, Parser.createDefault());

        System.out.println("checking");
        BooleanProofChecker proofChecker = new BooleanProofChecker(proof, ProofChecker.getFormalArithmeticsCheckers());
        BooleanProofResult result = proofChecker.Check();

        System.err.println(result.lineID);
        System.err.println(result.lastExpressionResult.reason);

        System.out.println(result.lastExpressionResult.isRight());
    }
}
