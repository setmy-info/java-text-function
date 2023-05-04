Documenting Clojure code is usually done in the ClojureDoc format, which is similar to JavaDoc and allows developers to
write documentation for Clojure functions that can be then generated into HTML documentation using tools such as Codox
or Marginalia.

To write ClojureDoc documentation, there are some rules to follow to ensure the quality and clarity of the
documentation. Some of the most important rules are:

1. Document public functions - Document functions that you want to present to other developers for use. Private
   functions usually don't require documentation as they are intended for internal use within the project.
1. Write a clear and concise description - The description should be short but clear enough for other developers to
   understand what the function does. Examples of how to use the function should be added at the end of the description.
1. Document arguments - Add a separate line for each argument description. The description should explain the meaning
   and type of the argument.
1. Write a return value description - Document what type of value the function returns and when it may return null.
1. Add examples - Examples are important so that other developers can understand how to use the function. Examples
   should be as clear and simple as possible.
1. Use ClojureDoc annotations - ClojureDoc uses certain annotations that should be followed when writing documentation.
   For example, the function name and description should be separated by two semicolons and arguments should be marked
   with the :[arg-name] annotation. For example:

```clojure
;;;;
;;; Add two numbers together.
;;;;
;;
;; (add-numbers x y)
;;
;; Adds two numbers together.
;;
;; Example:
;;   (add-numbers 1 2) ;; => 3
;;
;; Arguments:
;;   x - The first number.
;;   y - The second number.
;;
;; Returns:
;;   The sum of the two numbers.
;;
(defn add-numbers
    "Adds two numbers together."
    [x y]
    (+ x y))
```

These are some of the ClojureDoc documentation rules to follow to ensure clear and understandable documentation for
other developers.
