(ns clojure-training-2025.lesson02)

(defn full-function [x y z]
  (+ x y (* z z)))
;; =
(fn [x y z]
  (+ x y (* z z)))
;; =
;; anonymous function with multiple args need to hard code a number after the % to indicate
;; which arg it is refering to %1 %2 = [%1 %2]
#(+ %1 %2 (* %3 %3))

;; anonymous function with only 1 arg does not need a number after the %
;; anon-fn for adding 5
#(+ 5 %)

(def my-foo (fn [x y]
              (+ x y)))

(macroexpand-1 '(defn my-foo [x y]
                  (+ x y)))
;; (def my-foo (clojure.core/fn ([x y] (+ x y))))
;; These are all equivalents
(fn my-foo [x y] (* x y))
(def my-foo #(+ %1 %2))

;;;; Lexical and dynamic scoping

;;; LEXICAL

(let [y 5]
  (map #(+ %1 %2 y)
       [1 2 3]
       [4 5 6]))
;; (10 12 14)


(let [x 3
      y 7]
  (+ x y))
;; 10

;; what if we write
(let [x 4]
  (let [x 7]
    (str "X is now " x))
  (str "X is finally " x))
;; "X is finally 4"

(if true
  "got true")

(when true
  "got true")

(when false
  "this is true")
;; nil
;; also see if-not when-not etc.


;; Closure (not clojure)
;; we say that the returned function CLOSES over the variable x
(def blah
  (let [x 3]
    (fn [y]
      (+ x y))))

(blah 6) ; => 9

;;;; DYNAMIC

(defn change-regular [x]
  (def regular x))

(def regular 43)
(def regular 42)

(defn what-is-regular [n]
  (+ n regular))

(let [regular 333]
  regular)

regular

(def ^:dynamic *bigvar* 42)

(defn zzz []
  *bigvar*)

(zzz)

(binding [*bigvar* 9999]
  (zzz)) ; 9999

*out*
;; #object[java.io.PrintWriter 0x2f141297 "java.io.PrintWriter@2f141297"]
(println "hello")

#_(binding [*out* (clojure.java.io/writer "mylog.txt" :append true)]
    (println "This line goes to log.txt.")
    (println "So does this one."))

#_(def ^:dynamic *db-conn* (database/make-connection ...))
#_(get-from-db *db-conn* :by-name "John") ; => Foong

#_(binding [*db-conn* {"John" "Foong"}]
    (get-from-db *db-conn* :by-name "John"))

;;; Dynamic bindings can act as thread-local "global" variables.
;;; But they are frowned upon in clojure, as they break REFERENTIAL TRANSPARENCY.

;; referential transparency: the property that a pure function, given
;; same arguments, will ALWAYS return the same value!



;;;; Destructuring

(let [x [1 2 3]]
  {:a (first x)
   :b (second x)
   :c (last x)})
;; {:a 1, :b 2, :c 3}

(let [[a b c] [1 2 3]]
  {:a a
   :b b
   :c c})
;; {:a 1, :b 2, :c 3}

(let [[a & more] [1 2 3]]
  {:a    a
   :more more})
;; {:a 1, :more (2 3)}

(def my-data [1 2 3 4])
(let [[a b c] my-data]
  [a b c])
(let [[a b c _] my-data]
  [a b c])
(let [[a b c d e] my-data]
  [a b c d e]) ; [1 2 3 4 nil]

;;; Destructuring maps --- memorize this!!

(def grocery-entry
  {:liquid "Milk"
   :colour "white"
   :price  299})

[(:liquid grocery-entry) (:colour grocery-entry)] ; ["Milk" "white"]

(let [{liq :liquid col :colour} grocery-entry]
  [liq col]) ; ["Milk" "white"]

(let [{liq :liquid col :color u :unknown} grocery-entry] ; Notice the typo!!
  ;; Source of many a hard to track bug.  The perils of dynamic typing.
  [liq col u]) ; ["Milk" nil nil]

;;; Another, very common way of destructuring a map:
(let [colour (get grocery-entry :liquid)
      liquid (get grocery-entry :colour)]
  ;; scope.
  [liquid colour])

(let [[& more] {:status 200 :method "GET" :query-string "foo=7&bar=9"}]
  ;; scope.
  more) ; ([:status 200] [:method "GET"] [:query-string "foo=7&bar=9"])

;; Where can you find out all this crazy stuff?
;; You must (eventually) read the reference, all the way through,
;; at least once.  Destructuring is covered here:
;; https://clojure.org/reference/special_forms#binding-forms

(let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
  [first-name last-name full-name])
;; ["Stephen" "Hawking" ["Stephen" "Hawking"]]

(let [{liq :liquid :as m} grocery-entry]
  (str "Liquid of entry: " m " is - " liq))
;; "Liquid of entry: {:liquid \"Milk\", :colour \"white\", :price 299} is - Milk"

(let [{:keys [liquid colour price] :as m} grocery-entry]
  [liquid colour price m])
;; ["Milk" "white" 299 {:liquid "Milk", :colour "white", :price 299}]

(defn what-liquid? [grocery-entry]
  (:liquid grocery-entry))

(what-liquid? grocery-entry) ; "Milk"

(defn what-liquid-d? [{:keys [liquid] :as grocery-entry}]
  liquid)

(what-liquid-d? grocery-entry)
;; "Milk"



;; clojure tries to encourage LAZINESS.  e.g.
(def all-integers (range)) ; safe to evaluate!
all-integers

;; A lazy sequence is a unrealized sequence (instructions to do things,
;; but we haven't done them yet because nothing needs it)

;; These needs 10, so we will realize enough to fulfill this.
(take 10 all-integers)
;; (0 1 2 3 4 5 6 7 8 9)

(->> all-integers ; The "thread last" macro
     (drop 9999999)
     (take 10))
;; (9999999 10000000 10000001 10000002 10000003 10000004 10000005 10000006 10000007 10000008)

(time (take 10 all-integers))
; "Elapsed time: 0.0193 msecs"

;; ->  threads into the FIRST position
;; ->> threads into the LAST position

(-> grocery-entry
    (assoc :on-special? true)
    ;;    ^ here is where grocery-entry gets spliced in
    (dissoc :liquid)
    ;;     ^ here is where result of above computation gets spliced in
    )

(->> all-integers ; The "thread last" macro
     (drop 77)
     ;;      ^ here is where all-integers gets spliced
     (take 10)
     ;;      ^ here is where result of the drop 10 gets spliced
     (map inc))

;; Functions which create and return lazy sequences:

repeat repeatedly
take
drop
iterate

(type (take 5 (repeat :foo)))

(take 500 (repeat :foo))

(zipmap [:a :b :c]
        [1 2 3])
;; {:a 1, :b 2, :c 3}

(zipmap [:a :b :c]
        (range))
;; {:a 0, :b 1, :c 2}

(type (zipmap [:a :b :c]
              (range)))
;; clojure.lang.PersistentArrayMap (NO LONGER LAZY)

(seq? (range 5 9)) ; Both just SEQS.
(seq? (range))

(type (range 5 9)) ; clojure.lang.LongRange
(type (range)) ; clojure.lang.Iterate

(map inc [1 2 3 4 5]) ; (2 3 4 5 6)

(let [x (map println [1 2 3 4 5])
      y (println "YAAAAAA")])

; YAAAAAA

(map println [1 2 3 4 5])
;; 1
;; 2
;; 3
;; 4
;; 5

(let [x (map println [1 2 3 4 5])
      y (println "YAAAAAA")]
  x)

;; YAAAAAA
;; 1
;; 2
;; 3
;; 4
;; 5

(let [x (doall (map println [1 2 3 4 5]))
      y (println "YAAAAAA")]
  x)

; 1
; 2
; 3
; 4
; 5
; YAAAAAA

;; DONT RUN THIS
#_(doall (map println (range)))


;;;;  * reduce, functional idioms

(update {:a 1} :a inc) ; {:a 2}

#_(update {} :i-dont-exist inc)

(update {} :i-dont-exist (fnil inc 0)) ; {:i-dont-exist 1}

;; fnil defaults the function arg if it is nil
((fnil (fn [x]
         (str "We got given: " x))
       :some-default)
 nil) ; "We got given: :some-default"

(reduce + [1 2 3 4 5]) ; 15
(reduce + 0 [1 2 3 4 5]) ; 15
(reduce + 21 [1 2 3 4 5]); 36

(defn my-plus [total num-to-add]
  (+ total num-to-add))

(reduce my-plus 0 [1 2 3 4 5]) ; 15

;; what if we don't provide a default value?
(reduce my-plus [1 2 3 4 5]) ; 15
;; it's okay, it actually takes the first value as the default
;; and begins on the second value in the collection
;; equivalent to => (reduce my-plus 1 [2 3 4 5])

(def my-number 99)

(defn- add-with-another-number [another-number total number]
  (+ total number another-number))

(reduce (partial add-with-another-number my-number) [1 2 3 4 5]) ; 411
;; this is the same thing:
(reduce #(add-with-another-number my-number %1 %2) [1 2 3 4 5]) ; 411
;; this is the same thing:
(reduce (fn [total number]
          (add-with-another-number my-number total number))
        [1 2 3 4 5]) ; 411

(defn what-name [first-name last-name]
  (str "Hello " first-name " " last-name))

(what-name "jeff" "dog") ; "Hello jeff dog"

(partial what-name "AAAAA") ; #function[clojure.core/partial/fn--5908]

((partial what-name "AAAAA") "BBBBBB") ; "Hello AAAAA BBBBBB"
(def aaaaa-first-name-fn (partial what-name "AAAAA"))
 (aaaaa-first-name-fn "CCCCC") ; "Hello AAAAA CCCCC"

(defn add-these [a b c d]
  (+ a b c d))

((partial add-these 1 2) 3 4)



(reductions my-plus [1 2 3 4 5]) ; (1 3 6 10 15)
;; reductions returns to you a map, of each value at each loop / step
;; (1   => default val
;;  3   => 1 + 2
;;  6   => 3 + 3
;;  10  => 6 + 4
;;  15) => 10 + 5

;; Going back to histogram question
(reduce (fn [hist letter]
          (update hist letter (fnil inc 0)))
        {}
        "hello")
;; {\h 1, \e 1, \l 2, \o 1}

(frequencies "hello")

(conj {:a 1 :b 2} [:c 3]) ; {:a 1, :b 2, :c 3}
(conj {:a 1 :b 2} {:c 3}) ; {:a 1, :b 2, :c 3}
#_(conj {:a 1 :b 2} 1) ; error

(conj #{1 2 3} 3) ; #{1 3 2}