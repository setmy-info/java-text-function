(ns global.init
    (:require [clojure.string :as str])
    (:gen-class)
    (:use info.setmy.global.helper))

(println "init.clj")

(defn -init-entry
    [args]
    (println "Entered clojure world")
    (foo "bar"))
