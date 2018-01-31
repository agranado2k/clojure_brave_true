;1) Use the list function, quoting, and read-string to create a list that, when evaluated, prints your first name and your favorite sci-fi movie.
(eval (read-string "(list \"Arthur\" \"Matrix\")"))

;2) Create an infix function that takes a list like (1 + 3 * 4 - 5) and transforms it into the lists that Clojure needs in order to correctly evaluate the expression using operator precedence rules.
(defn infix [exp]
  (eval (list (second exp) (first exp) (last exp)))
)

(defn infix [[num1 op num2] sub_op]
  (if(empty? sub_op)
    (list num1 op num2)
    (infix (list op num1 num2) sub_op)
  )
)

(infix '(1 + 3)) ; 4
(infix '(1 + 3 + 2)) ; 6
(infix '(1 + 3 * 4 - 5)) ;8
