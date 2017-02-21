import Prooving.Parsing.Enchanced.EnchancedProofParser;
import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Boolean.BooleanProofChecker;
import Prooving.ProofCheckers.Boolean.BooleanProofResult;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        System.out.println("running");
        if (args.length == 0 || "-compile".equals(args[0]))
        {
            File file;
            if (args.length == 0)
            {
                file = new File("adding/" + "o)t+t=2Xt");
            }
            else
            {
                file = new File(args[1]);
            }

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
                System.err.println("Wrong!" + proof.getAssumptions().size() + proof.getAssumptions());
                return;
            }

            System.out.println("|-" + proof.getProofLines().get(proof.getProofLines().size() - 1));
            HashSet<Expression> usedExpressions = new HashSet<Expression>();
            for (Expression line : proof.getProofLines())
            {
                usedExpressions.add(line);
                System.out.println(line.toParsableString());
            }
            System.out.println(proof.getProofLines().size() + " " + usedExpressions.size());
        }
        else if ("-check".equals(args[0]))
        {
            File file = new File(args[1]);

            System.out.println("parsing");
            Proof proof = ProofParser.parseProof(file, Parser.createDefault());

            System.out.println("checking");
            BooleanProofChecker checker = BooleanProofChecker.getDefaultChecker(proof);
            BooleanProofResult result = checker.Check();
            if (result.lastExpressionResult.isWrong())
            {
                for (int i = 0; i < proof.getProofLines().size(); i++)
                {
                    //System.err.println(i + ")" + proof.getProofLines().get(i).toParsableString());
                }

                System.out.println("wrong at line " + result.lineID);
                System.out.println("reason:" + result.lastExpressionResult.reason);
                return;
            }
            else
            {
                System.out.println("Correct!");
            }
            System.out.println(proof.getProofLines().size());
        }

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
