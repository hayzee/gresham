(ns gresham.core-test
  (:require [clojure.test :refer :all]
            [gresham.core :refer :all]))


(def test-transactions
  [{:date "20180101", :amount "123.45", :debit-credit :credit}
   {:date "20180201", :amount "123.45", :debit-credit :credit}
   {:date "20180301", :amount "123.45", :debit-credit :debit}
   {:date "20180401", :amount "123.45", :debit-credit :debit}
   {:date "20180501", :amount "123.45", :debit-credit :credit}
   {:date "20180601", :amount "123.45", :debit-credit :debit}
   {:date "20180701", :amount "123.45", :debit-credit :credit}
   {:date "20180801", :amount "123.45", :debit-credit :debit}
   {:date "20180901", :amount "123.45", :debit-credit :credit}
   {:date "20181001", :amount "123.45", :debit-credit :credit}
   {:date "20181101", :amount "123.45", :debit-credit :debit}
   {:date "20181101", :amount "123.45", :debit-credit :debit}
   {:date "20181101", :amount "123.45", :debit-credit :debit}
   {:date "20181201", :amount "123.45", :debit-credit :credit}
   {:date "20190101", :amount "123.45", :debit-credit :credit}])


(deftest debit-credit-test
  (is (= (debit-credit {:date "20180408", :amount "283.07", :debit-credit :credit}) 283.07M))
  (is (= (debit-credit {:date "20180408", :amount "283.07", :debit-credit :debit}) -283.07M))
  )


(deftest calculate-interest-test
  (is (= (calculate-interest test-transactions "201801") 0.61725M))
  (is (= (calculate-interest test-transactions "201802") 1.23450M))
  (is (= (calculate-interest test-transactions "201803") 0.61725M))
  (is (= (calculate-interest test-transactions "201804") 0.00000M))
  (is (= (calculate-interest test-transactions "201805") 0.61725M))
  (is (= (calculate-interest test-transactions "201806") 0.00000M))
  (is (= (calculate-interest test-transactions "201807") 0.61725M))
  (is (= (calculate-interest test-transactions "201808") 0.00000M))
  (is (= (calculate-interest test-transactions "201809") 0.61725M))
  (is (= (calculate-interest test-transactions "201810") 1.23450M))
  (is (= (calculate-interest test-transactions "201811") -0.61725M))
  (is (= (calculate-interest test-transactions "201812") 0.00000M))
  (is (= (calculate-interest test-transactions "201901") 0.61725M)))
