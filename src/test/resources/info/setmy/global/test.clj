(ns global.test
    (:require [clojure.string :as str])
    (:gen-class))

(println "Clojure file" "test.clj")

(defn -main
    "Entry point"
    [args]
    (if (seq args)
        (doseq [name args]
            (if (not (nil? name))
                (println "Say hello to" name)
                (println "Can't say hello to anyone")))
        (println "No arguments provided")))
