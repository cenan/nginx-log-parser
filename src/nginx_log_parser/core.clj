(ns nginx-log-parser.core
  (:use     [nginx-log-parser.analyzer])
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:gen-class))

(defn check-file
  "check if a file exists and is not a directory"
  [filename]
  (let [f (new java.io.File filename)]
    (and
      (.exists f)
      (not (.isDirectory f)))))

(defn -main
  [& args]
    (if (not (check-file (first args)))
      (println "Bad argument")
      (with-open [rdr (io/reader (first args))]
        (doall
          (->>
            get-url
            (analyze-field (line-seq rdr))
            (sort-by last)
            (reverse)
            (take 20)
            (map println))))))
