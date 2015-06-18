(ns nginx-log-parser.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:gen-class))

(defn parse-line
  [ln]
  {:status (nth (str/split (nth (str/split ln #"\"") 2) #" ") 1)})

(defn get-status
  [ln]
  (nth (str/split (nth (str/split ln #"\"") 2) #" ") 1))

(defn inc-status
  [map key]
  {key (inc (get map key 0))})

(defn analyze-status
  [sq]
  (loop [result {} sq2 sq]
    (if (empty? sq2)
    result
    (recur (merge result (inc-status result (get-status (first sq2)))) (rest sq2)))))

(defn line-count [rdr]
  (count (line-seq rdr)))

(defn -main
  [& args]
  (with-open [rdr (io/reader "access.log")]
   (println (analyze-status (line-seq rdr)))))
