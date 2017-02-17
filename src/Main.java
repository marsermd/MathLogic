import Prooving.Parsing.Enchanced.EnchancedProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Boolean.BooleanProofChecker;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Utils.StringHash;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        File file = new File("adding/" + "i)t=r→s+t=s+r");

        System.out.println("parsing");
        Proof proof = EnchancedProofParser.parseProof(file, Parser.createDefault());

        System.out.println("checking");
        BooleanProofChecker checker = BooleanProofChecker.getDefaultChecker(proof);
        System.out.println(checker.Check().lastExpressionResult.isRight());
        System.out.println(proof.getProofLines().size());
        if (checker.Check().lastExpressionResult.isWrong())
        {
            for (int i = 0; i < proof.getProofLines().size(); i++)
            {
                System.err.println(i + ")" + proof.getProofLines().get(i).toParsableString());
            }
            return;
        }

        for (Expression line: proof.getProofLines())
        {
            System.out.println(line.toParsableString());
        }
        System.out.println(proof.getProofLines().size());

//        RewriterProofChecker proofChecker = RewriterProofChecker.getDefaultChecker(proof);
//        RewriterProofResult result = proofChecker.Check();
//
//        System.err.println(!result.failed);
//        System.err.println(result.generatedProof.getProofLines().size());
//
//        BooleanProofChecker generatedChecker = BooleanProofChecker.getDefaultChecker(proof);
//        BooleanProofResult generatedResult = generatedChecker.Check();
//
//        System.out.println(generatedResult.lastExpressionResult.isRight());
    }
}
