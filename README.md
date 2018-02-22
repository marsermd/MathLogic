# MathLogic
Parser &amp; proof checker and transformer for [Logical Calculus](https://en.wikipedia.org/wiki/Peano_axioms).

### Usage:
`java -jar MathLogic.jar --%option% input output`  
options:
- --compile  
  Compiles input file (see Fromat section).
- --deduct  
  Makes one step of [deduction](https://en.wikipedia.org/wiki/Deduction_theorem) on an input. Prints result to output.
  If there are no assumptions left, the proof will be checked.
- --sqr  
  Given that the input file contains a number a, prints proof for
  (a + 1) \* (a + 1) = a \* a + 2 \* a + 1 

### Format:
A proof can include another proof:  

**#use** _localFilePath_ ; _targetFormula_ ; (_variable_ to _expression_;)\*
_localFilePath_ is the path to proof you want to use to prove _targetFormula_. If there is some ambiguity, you can fix it by assigning expressions to variables of the proof (only if proof is really going to be set there).

Proof usage is defined in it's header.

There is one header you **MUST** write.  
**#match** _formula_  
This is the formula to which _targetFormula_ is matched in #use.

To apply N deduction steps, write  
**#deduct** _N_

You might not want to do a naive replacement of the line with proof, since resulting proof length will grow exponentially.
If there is no Generaliation(Each) rule involved in your proof, you can specify that it should be generalzied and later used with axiom @phi->phi[x:=o]. To do so, place  
**#each** in the header
