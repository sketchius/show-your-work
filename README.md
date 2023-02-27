# show-your-work
A Java program that evaluates mathematical expressions, printing each step.

Click here to try it on Repl.it:

[![Run on Repl.it](https://img.shields.io/badge/Replit-DD1200?style=for-the-badge&logo=Replit&logoColor=white)](https://replit.com/@sketchius/show-your-work?v=1)

## How it works

### Processing the Expression

The program takes a mathematical expression as a string. For instance: `10 * (6 - (12 / 4))`.

Next the string is tokenized, breaking it into individual parts. This is stored as an array of Tokens.

`10`, `*`, `(`, `6`, `-`, `(`, `12`, `/`, `4`, `)`, `)`

The array is given to the parser. The parser processes the tokens into a tree structure.
1) It analyzes the parenthesis and looks at each operator, keeping track of the 'parenthetical level' for each operator. In our example, the multiply operator has a level of 0, the minus operator has a level of 1, and the divide operator has a level of 2. These numbers correspond to how many sets of parentheses are wrapped around each operator

```
10 * (6 - (12 / 4))
   ^    ^     ^     
   0    1     2    
```
   
2) The program enters a loop.
- It checks each operator and generates a score based on the parenthetical level and the type of operator (`*` and `/` score higher than `+` and `-`). This is calculated by parentheticalLevel * 10 + operatorScore, where + and - are worth 1, * and / are worth 2. Our example is scored like this:
```
10 * (6 - (12 / 4))
   ^    ^     ^     
   2   11    22    
```

- The operator with the highest score, along with the operands to the left and right are removed from the Token list and replaced by a new Token of type EXPRESSION, which is a special type of Token that can hold three other Tokens. If there were parenthesis wrapping these three Tokens, those are removed as well, and the new Token is flagged as having parenthesis. In our example, the divide operator has the highest score, so afterwards our tokens look like this:
`10`, `*`, `(`, `6`, `-`, `<EXPRESSION>`, `)`
Where expression is a token that contains the Tokens `12`, `/`, `4`, and is flagged as having parenthesis.
- The loop continues until no operators are found (meaning we are left with a single EXPRESSION Token)
3) This final Token is returned.

### Executing the Expression

The processing generated a tree where the base of the tree represents the final operation to be done. If we label the EXPRESSION Tokens in the tree with letters, it would look something like this:
```
   C
   ^
10 * B
      ^
   B: 6 - A
          ^
      A: 12 / 4
```
In order to execute the expression, it needs to do run a step method:

Starting with the first EXPRESSION Token. Remember, an EXPRESSION Token contains three Token variables: a left operand, an operator, and a right operand.
1) If both operands are EXPRESSION Tokens, it runs getDepth() on both of them, which returns a the number based on how many descendant Tokens are connected to that Token.
2) Otherwise if only the left operand is an EXPRESSION Token, if so, it recursively runs the step method on that Token.
3) It does the same if only the right operand is an EXPRESSION Token.
4) If neither the left or the right operands are EXPRESSION tokens, that means it has reached bedrock arithmetic expression
- It can go ahead and calculate the result based on the operator type (+,-,*,/)
- It clears all of the token variables
- It sets the Token type to NUMBER and stores the result.

Each time this step method is run, a single operation is performed. During the next run of the step method, the results of the first can be used.

### Printing the Steps

Between each step, the program prints the state of the expression so that the user can see all of the steps involved. This is accomplished by recursively calling the Token object's toString() method:
1) If the Token is of type EXPRESSION, the method will build a string made up of three substrings, which are found by calling the toString() on the left operand, operator, and right operand Tokens. The method also will check to see if the Token's parenthesis flag is true. If so it will wrap this in parenthesis. This string is returned.
2) Otherwise, the Token is a NUMBER or OPERATOR, so the method will simply return it as a string, wrapped in parenthesis if the flag is true.

After recursion, a fully formed string is returned from the original toString() call, which can be printed.
