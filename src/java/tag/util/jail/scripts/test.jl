#
#  This test suite is organized simple to complex.
#


(= v1 foobar)
(= v2 (a b c d e f g))
(= v3 (split a bcabcabcabc))
(= v4 (join \n (a b c d)))

(pr "Test One\n")
(pr v1 ' ' v2 ' ' v3 ' 'v4)

(pr "Test Two\n")
(foreach v3 (pr $_))

(pr "Test Three\n")
(foreach v3 (pr))

(pr "Test Four\n")
(= foo (x)
   (pr x)
)
(foo 4)

(pr "Test Five\n")
(= bar (x y)
(do
   (pr (add x y))
   (pr x)   
))
(bar 4 5)


(= i 0)

(pr "Entering if block.\n")

(if (!= i 10) 
    (pr "i ne 10\n") 
    (pr "i eq 10\n") 
)

(pr "Entering foreach loop.\n")

(foreach ('1..10')
   (pr $_ \n)
)

(pr "Entering while loop.\n")

(while (lt i 10)
(do
   (pr i \n)
   (= i (add i 1))
))

(pr "Done.\n")

(exit)

