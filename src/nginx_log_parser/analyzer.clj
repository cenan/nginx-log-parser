(ns nginx-log-parser.analyzer
  (:require [clojure.string :as str])
  (:gen-class))

(defn get-status
  [ln]
  (nth (str/split (nth (str/split ln #"\"") 2) #" ") 1))

(defn get-url
  [ln]
  (nth (str/split ln #"\"") 1 ""))

(defn get-ua
  [ln]
  (nth (str/split ln #"\"") 5 ""))

(defn inc-key
  [map key]
  {key (inc (get map key 0))})

(defn analyze-field
  [sq field]
  (loop [result {} sq2 sq]
    (if (empty? sq2)
      result
      (recur (merge result
                    (inc-key result
                             (field (first sq2))))
             (rest sq2)))))

