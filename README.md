# JAIL

Jail is a simple script language used for rapid development of small customized components of an application.  It opens up the internals of applications to the user's control at many levels.  JailScript is interpreted, not compiled.

I wrote this when our product was undergoing rapid breaking changes, but we needed its configuration datafill to remain portable.  Jail served as a kind of ETL tool that could move as fast as developers could change their expected configuration schema.

It resembles LISP, but there are also elements that are frankly declarative in nature.  It's a "git-er-done" language; it ain't pure.  If you're looking for pure, then head over to Clojure.

The Jail script is composed of data definitions, functions, and flow control statements. 

Jail is Case Sensitive. All Jail values are case sensitive. 

## Hello, World

To print "Hello, World", one creates a list beginning with the method name echo, which sends all of its arguments to stdout.  Please note that strings do not have to be in double quotes, but they will be interpreted as variables if they have been defined earlier.

```
(echo "Hello, World\n")        # one string 
(echo Hello ", " World "\n")   # four strings
```

## Data

Data can take the form of atomic variables, lists, and hashes.   Atoms are strings, such as "Hello, World\n", or numbers, such as 3.14.  Lists are extensible arrays, and are delimited by parentheses or curly braces: for example, (a b 3) is a list containing two strings and one number.  It can also be written with curly braces as {a b e}: both styles are fine.  Lists can contain atoms, lists, and hashes, and are be indexed by integer position.  A Hash is a list of key-value pairs, indexed by key.  Keys are always Strings, while values may be atoms, lists, or hashes.

Atoms and lists are declared by a list beginning with the method name define.

Examples:
```
(define foo "mystring")     # foo is a scalar 
(define foo (1 2 a))        # foo is a list with 3 members 
(define foo (1 (a b) 2))    # foo is a list with 3 members 
(echo foo[0])               # print the first element of foo
(echo Hello ", World\n" )   # prints "Hello, World\n" 
(define Hello Goodbye)      # define a variable named Hello 
(echo Hello ", World\n" )   # prints "Goodbye, World\n"! 
(undef Hello)               # removes the variable 
(echo Hello ", World\n" )   # prints "Hello, World\n"
```

Hashes, however, must be declared using the method name Hash:
```
(define foo (Hash ((key1 value1) (key2 value2) (key3 value3))))
```
Once created, hashes may be indexed by key name:
```
(echo foo[key2])
```
For the sake of symmetry, atoms and lists can be created using the methods Atom and List:
```
(define foo (Atom "mystring")) 
(define foo (List (a b c)))
```

## Flow Control

Flow control consists of a foreach() statement and an if() statement: 
 
```
(foreach element list (body)) 
Example: 
(foreach element (a b c)  
(  
   (echo element)  
))
(if (test-expression) (true-expression) (false-expression)) 
Example: 
(if (equals a b) 
    (echo "a equals b") 
    (echo "a != b") 
)
```

