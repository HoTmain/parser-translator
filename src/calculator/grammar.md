    r1: exp  -> term exp2
    r2; exp2 -> + term exp2
    r3:      | - term exp2
    r4:      | ε

    r5: term  -> factor term2
    r6: term2 -> **factor term2
    r7:        | ε

    r8: factor -> num
    r9:         | (exp)

    r10: num  -> digit num2
    r11: num2 -> digit num2
    r12:       | ε

    r13: digit -> 0..9

####

    first(r1)= first(r5)= {0..9,(}
    first(r2)= {+}
    first(r3)= {-}
    first(r4)= {ε}
    first(r5)= {0..9,(}
    first(r6)= {**}
    first(r7)= {ε}
    first(r8)= {0..9}
    first(r9)= {(}
    first(r10)= first(r11)= {0..9}
    first(r12)= {ε}
    first(r13)= {0..9}

####

    follow(exp)= {$,)}
    follow(exp2)= {$,)}
    follow(term)= {+,-,$,)}
    follow(term2)= {+,-,$,)}
    follow(factor)= {**,+,-,$,)}
    follow(num)= {**,+,-,$,)}
    follow(num2)= {**,+,-,$,)}

###

    first(r1)+= {0..9,(}
    first(r2)+= {+}
    first(r3)+= {-}
    first(r4)+= {$,)}
    first(r5)+= {0..9,(}
    first(r6)+= {**}
    first(r7)+= {+,-,$,)}
    first(r8)+= {0..9}
    first(r9)+= {(}
    first(r10)+= {0..9}
    first(r11)+= {0..9}
    first(r12)+= {**,+,-,$,)}
    first(r13)+= {0..9}

|        | `0..9`                     | `(`                        | `+`                       | `-`                       | `**`                           | `)`              | `$`              |
|--------|----------------------------|----------------------------|---------------------------|---------------------------|--------------------------------|------------------|------------------|
| exp    | r1: `exp -> term exp2`     | r1: `exp -> term exp2`     |                           |                           |                                |                  |                  |
| exp2   |                            |                            | r2: `exp2 -> + term exp2` | r3: `exp2 -> - term exp2` |                                | r4: `exp2 -> ε`  | r4: `exp2 -> ε`  |
| term   | r5: `term -> factor term2` | r5: `term -> factor term2` |                           |                           |                                |                  |                  |
| term2  |                            |                            | r7: `term2 -> ε`          | r7: `term2 -> ε`          | r6: `term2 -> ** factor term2` | r7: `term2 -> ε` | r7: `term2 -> ε` |
| factor | r8: `factor -> num`        | r9: `factor -> (exp)`      |                           |                           |                                |                  |                  |
| num    | r10: `num -> digit num2`   |                            |                           |                           |                                |                  |                  |
| num2   | r11: `num2 -> digit num2`  |                            | r12: `num2 -> ε`          | r12: `num2 -> ε`          | r12: `num2 -> ε`               | r12: `num2 -> ε` | r12: `num2 -> ε` |
| digit  | r13: `digit -> 0-9`        |                            |                           |                           |                                |                  |                  |
