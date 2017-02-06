import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Boolean.BooleanProofChecker;
import Prooving.ProofCheckers.ProofChecker;
import Prooving.ProofCheckers.Boolean.BooleanProofResult;
import Prooving.ProofCheckers.Rewriter.RewriterProofChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.BinaryOperators.BinaryOperator;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        File file = new File("testResources/" + "correct14.in");

        System.out.println("parsing");
        Proof proof = ProofParser.parseProof(file, Parser.createDefault());

        System.out.println("checking");
        RewriterProofChecker proofChecker = RewriterProofChecker.getDefaultChecker(proof);
        RewriterProofResult result = proofChecker.Check();

        System.err.println(!result.failed);
        System.err.println(result.generatedProof.getProofLines().size());

        System.out.println(result.failed == false);

        BooleanProofChecker generatedChecker = BooleanProofChecker.getDefaultChecker(result.generatedProof);
        BooleanProofResult generatedResult = generatedChecker.Check();

        System.out.println(generatedResult.lastExpressionResult.isRight());


    }
}
