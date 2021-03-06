(ns ninety-nine.list-test
  (:require [clojure.test :refer :all]
            [ninety-nine.list :refer :all]
            [clojure.set :as cs]))

(deftest nn-1
  (testing "last"
    (is (= 8 (nn-last '(1 1 2 3 5 8))))
    ))

(deftest nn-2
  (testing "penultimate"
    (is (= 5 (penultimate '(1 1 2 3 5 8))))
    ))

(deftest nn-3
  (testing "nth"
    (is (= 2 (nn-nth 2 '(1 1 2 3 5 8))))
    ))

(deftest nn-4
  (testing "count"
    (is (= 6 (nn-count '(1 1 2 3 5 8))))
    ))

(deftest nn-5
  (testing "reverse"
    (is (= '(8 5 3 2 1 1) (nn-reverse '(1 1 2 3 5 8))))
    ))

(deftest nn-6
  (testing "palindrome"
    (is (is-palindrome '(1 2 3 2 1)))
    (is (= false (is-palindrome '(1 2 3 1 1))))
    ))

(deftest nn-7
  (testing "flatten"
    (is (= '(1 2 3 4 5) (nn-flatten '((1 2 (3)) (4 5)))))
    ))


(deftest nn-8
  (testing "compress"
    (is (= '(1 2 3 4)
           (nn-compress '(1 2 2 3 3 3 4 4 4 4))))
    ))

(deftest nn-9
  (testing "pack"
    (is (= '((1) (2 2) (3 3 3) (4 4 4 4))
           (nn-pack '(1 2 2 3 3 3 4 4 4 4))))
    ))

(deftest nn-10
  (testing "encode"
    (is (= '((1 1) (2 2) (3 3) (4 4)) (nn-encode '(1 2 2 3 3 3 4 4 4 4))))
    ))

(def abbcccdddd-coll (seq "abbcccdddd"))

(deftest nn-11
  (testing "encode modified"
    (is (= '(\a (2 \b) (3 \c) (4 \d) )  (nn-encode-modified
                                         abbcccdddd-coll)))
    ))

(deftest nn-12
  (testing "decode"
    (is (= abbcccdddd-coll (nn-decode (nn-encode abbcccdddd-coll))))
    ))

(deftest nn-13
  (testing "encode direct"
    (is (= (nn-encode abbcccdddd-coll)  (nn-encode-direct abbcccdddd-coll) ))
    )
  )

(deftest nn-14
  (testing "duplicate elements list")
  (is (= (seq "aabbbbccdd") (nn-duplicate (seq "abbcd"))))
  )

(deftest nn-15
  (testing "duplicate elements list n times"
    (is (= (seq "aaabbbbbbcccddd")
           (nn-duplicate-n 3 (seq "abbcd"))))
    ))

(deftest nn-16
  (testing "drop nth list"
    (is (= (seq "abdeg") (nn-drop 3 (seq "abcdefg"))))
    )
  )

;; got tired of writing deftest testing is =
;; my first clojure macro hurray!!
(defmacro defexpect [name description got expected]
  `(deftest ~name
     (testing ~description
       (is (= ~expected ~got))
       ))
  )

;; just testing the macro
(defexpect nn-16-2 "drop nth list" (seq "abdeg") (nn-drop 3 (seq "abcdefg")))

(defexpect nn-17 "split in 2 list" [[1 2 3][4 5 6 7]]
  (nn-split 3 [1 2 3 4 5 6 7]) )

(defexpect nn-18 "slice" [4 5 6 7] (nn-slice 3 7 [1 2 3 4 5 6 7 8]))

(deftest nn-19
  (testing "rotate"
    (is (= [4 5 6 1 2 3] (nn-rotate 3 [1 2 3 4 5 6])))
    (is (= [5 6 1 2 3 4] (nn-rotate -2 [1 2 3 4 5 6])))
    )
  )


(defexpect nn-20 "remove at" [[1 3 4] 2] (nn-remove-at 1 [1 2 3 4]))

(defexpect nn-21 "insert at" [:a :new :b :c] (nn-insert-at 1 :new [:a :b :c]))

(defexpect nn-22-1 "range" [8 9 10 11] (nn-range-1 8 11))

(defexpect nn-22-2 "range" [8 9 10 11] (nn-range-2 8 11))

(deftest nn-23
  (testing "random select"
    (let [a  [:a :b :c :d :e :f]
          sel (nn-random-select 3 a)]
      (is (= (set  sel) (cs/intersection (set sel) (set a)))
          )
      )
    )
  )

(deftest nn-24
  (testing "Lotto"
    (let [lotto (nn-lotto 2 49)]
      (is (= 2 (nn-count lotto)))
      (is (cs/subset?
           (set lotto)
           (set (range 1 50)))))
    )
  )

;; the sets should be the same after permutation
(deftest nn-25
  (testing "random permutations"
    (is (= (set [1 2 3 4 5]) (set (nn-random-permutation [1 2 3 4 5]))))
    )
  )

(defexpect nn-26 "combinations"  6 (nn-count (nn-combinations 2 [:a :b :c])))

(defexpect nn-26-2 "combinations" 5040 (nn-count (nn-combinations 4 (range 10))))
