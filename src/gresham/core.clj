(ns gresham.core
  (:gen-class))


(def notneg? (complement neg?))


;; Useful when working on multi-methods
(comment
  (ns-unalias *ns* 'debit-credit)
  )

(defmulti debit-credit :debit-credit)

(defmethod debit-credit :credit [trans] (bigdec (:amount trans)))
(defmethod debit-credit :debit [trans] (- (bigdec (:amount trans))))

; This also works, but need to multiply by 0.00005M to get interest in calculate-interest
;
;(defmethod debit-credit :credit [trans] (BigInteger. ^String (clojure.string/replace (:amount trans) "." "")))
;(defmethod debit-credit :debit [trans] (- (BigInteger. ^String (clojure.string/replace (:amount trans) "." ""))))

(defn calculate-interest
  "Given a full list of transactions for the bank account, and a month in the
  format '201812', calculates the interest payable for that month."
  [transactions yr-month]
  (->>
    (sort-by :date transactions)
    (filter #(notneg? (compare yr-month (subs (:date %) 0 6))))
    ;(map #(case (:debit-credit %)
    ;        :credit (bigdec (:amount %))
    ;        (- (bigdec (:amount %)))
    ;        )
    ;     )
    (map debit-credit)   ; multi-method replacement for preceding expression
    (reduce +)
    (* 0.005M)))


;; Are transducers useful here?
