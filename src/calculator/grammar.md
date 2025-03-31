    exp -> num
         | exp op exp
         | (exp)

    op -> +
        | -
        | **

    num -> digit
         | digit num

    digit -> 0..9


    ------------------->

    exp  -> num exp2
          | (exp) exp2
    exp2 -> op exp
          | ε

    op -> +
        | -
        | **

    num  -> digit num2
    num2 -> digit num2
          | ε

    digit -> 0..9