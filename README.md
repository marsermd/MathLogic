# MathLogic
Parser &amp; proof checker and transformer for Formal theories of arithmetic

### Usage:
`java -jar MathLogic.jar --%option% input output`  
options:
- --compile  
  Compiles input file as specified in Fromat section
- --deduct  
  Makes one step of deduction on input file, prints result to output
- --sqr  
  Given that input file contains a number a, it prints ptoof for
  (a + 1) \* (a + 1) = a \* a + 2 \* a + 1 

### Format:
Proof may link to another proof by using  

**#use** _localFilePath_ ; _targetFormula_ ; (_variable_ to _expression_;)\*
_localFilePath_ is the path to proof you want to use to prove _targetFormula_. If there is some ambiguity, you can fix it by assigning expressions to variables of the proof (only if proof is really going to be set there).

You define how the proof will be used by other proofs in it's header.

There is one header you **MUST** write.  
**#match** _formula_  
This is the formula to which _targetFormula_ is compared in #use.

To apply deduction, just write  
**#deduct** _N_  
where _N_ is count of deduction applications.

You might not want to do a fair replacement of the line with proof, since resulting proof length will grow exponentially.
If there is no Generaliation(Each) rule involved in your proof, you can specify that it should be generalied and later used with axiom @phi->phi[x:=o]. To do so, place  
**#each** in the header
