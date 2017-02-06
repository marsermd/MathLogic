package Prooving.ProofCheckers.Rewriter;

import Prooving.Proof;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 05.02.2017.
 */
public class RewriterProofResult
{
    public boolean failed;
    public String failureReason;

    public Expression alphaAssumption;
    public Proof generatedProof;
}
