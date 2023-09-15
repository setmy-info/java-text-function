(ns info.setmy.global.helper
    (:require [clojure.string :as str])
    (:gen-class))

(import
 'info.setmy.textfunctions.Example)

(println "helper.clj")

(defn print-first-name [example]
    (println (. example getFirstName)))

(defn foo [param]
    (let [example (info.setmy.textfunctions.Example.)]
        (println "Foo" param)
        ;;(.. example (setFirstName "John"))
        (. example setFirstName "John")
        (print-first-name example)))
