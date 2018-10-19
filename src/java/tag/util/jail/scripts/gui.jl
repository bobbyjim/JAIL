

//(= GF (label) ((gui.field label 10)))

(= a (gui.field f0 10)) // GF ("User Group")))

(= b (gui.field f1 10))
(= c (gui.field f2 10))
(= d (gui.field f3 10))

(= d (gui.button "Press me" (pr "hi there\n") ))
(= e (gui.button "No, press me!" (gui.field a Label4 HiThere) ))
(= f (gui.panel d e))

(gui.frame MyTitle (a b c d e))

(cli)
