(ns fwpd.core)
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))


(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))


;; Exercise 1
(defn listify
  "Return a seq of names like (\"Edward Cullen\", \"Other name\")"
  [map_list]
  (map :name map_list))

(listify (glitter-filter 3 (mapify (parse (slurp filename)))))


;; Exercise 2

(defn append
  [clist suspect]
  (
    conj clist suspect
    ))

(append (glitter-filter 3 (mapify (parse (slurp filename)))) "New Suspect")


;; Exercise 3
(defn validate
  [vamp-keys record]
  (
    reduce #(and %1 %2) (map #(not (nil? (% record))) vamp-keys)
    ))

(defn append
  [clist suspect]
    (if (validate vamp-keys suspect) (conj clist suspect) clist )
  )


;;not append
(append (glitter-filter 3 (mapify (parse (slurp filename)))) {:name "New Suspect"})

;; append
(append (glitter-filter 3 (mapify (parse (slurp filename)))) {:name "New Suspect" :glitter-index 10})

;; Exercise 4

(defn to_csv
  [maps]
    (clojure.string/join "\n" (map #(clojure.string/join "," (map % vamp-keys)) maps))
  )