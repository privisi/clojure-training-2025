(ns clojure-training-2025.lesson01)

; This is a comment

; same-line as code comment
(+ 1 2) ; this is a comment

;; inline-comment

;; adds 1 and 2
;; 2nd line hehe
(+ 1 2)

;;; section header

;;;; file header

(comment
  This is a comment block
  that can span multiple lines.
  It is ignored by the compiler.aaewf qf32v q2f4 q32 ff 2)

#_(anything in this block is ignored by the compiler)

#_(+ 1 (+ 2 3))

;; (operator operand1 operand2 ...)
;; (function 1 2 3)
;; (function) - no args is fine too

(+ 1 2)

(+ 1
   (+ 2
      (+ 3
         (+ 4 5))))

(+ 1 2 3 4 5)

(+)

;; operators are almost, but not always, FUNCTIONS.

;;;; Fundamental Variable Types

"hello"

7

(type (int 7))

;; int = 32 bits (range -2,147,483,648 to 2,147,483,647)

(int 2.2)

(type 7) ; => long = 64 bits (range -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807)

(type 7.0)

(float 7.0)
(double 2.3)
(type "hello")

(type 7/3)

(* 7/3 3)

(* (/ 7.0 3.0)
   3.0)

(+ 2 3.5)
(type 32323223223223232332)
(type 7N)

java.lang.Long/MAX_VALUE

java.lang.Integer/MAX_VALUE

(type 9223372036854775807)

#_(+ 9223372036854775807 1)

(+ 9223372036854775807N 1)

(type 3232M)


;; The REPL (Read Eval Print Loop)
(comment
  (loop
   (print
    (eval
     (read)))))


:foo-bar ; I am a keyword
:adsdsaads

:big-menu-342-432-324-2-34!!

[1 2 3] ; I am a vector
(vector 1 2 3)
[1 :banner "split"]

;; Sets
#{1 2 3}

(= #{1 2 3} #{3 2 1})

(conj #{1 2 3} 4)

(conj #{1 2 3} 3)


(into #{} (range 99))

;; conjs at the end
(conj [1 2 3] 99) ; => [1 2 3 99]

'(1 2 3)

(conj '(1 2 3) 99) ; => (99 1 2 3)
;; MAKE THIS AN ASSIGNMENT


{:key "value"}
{:key1 "value1" :key2 "value2"}
{:key1 "value1" :key2 "value2" :key3 "value3"}
{:key1 1 :key2 2 :key3 3}
{:key1 1 :key2 2 :key3 3 :key4 4}
{:key1 1 :key2 2 :key3 3 :key4 4 :key5 5}
{:key1 1 :key2 2 :key3 3 :key4 4 :key5 5}

{1 :value}
{"string" "string"}
#_{key value}

(get {:key1 1 :key2 2} :key1) ; => 1
(get {:key1 1 :key2 2} :key3) ; => nil
(get {:key1 1 :key2 2} :key3 "default") ; => "default"

;; student-id record
{:student-1234 {:name "John Doe" :age 20 :grade "A"}
 :student-5678 {:name "Jane Smith" :age 22 :grade "B"}
 :student-9101 {:name "Alice Johnson" :age 21 :grade "A"}} ; => student records

(def student-records
  {:student-1234 {:name "John Doe" :age 20 :grade "A"}
   :student-5678 {:name "Jane Smith" :age 22 :grade "B"}
   :student-9101 {:name "Alice Johnson" :age 21 :grade "A"}}) ; => student records

(get student-records :student-1234) ; => {:name "John Doe" :age 20 :grade "A"}

(conj {:foo 99} {:bar 99}) ; => {:foo 99, :bar 99}

(conj {:foo 99} [:vector 99]) ; => {:foo 99, :vector 99}

{:liquid "Milk"
 :colour "White"
 :price  299}

(def my-funcs {:plus  +
               :minus -})

((get my-funcs :plus) 1 2)
;; => (+ 1 2)

{+ "this is plus"}

(get {+ "this is plus"} +) ; => "this is plus"

{{:foo :bar} :baz}

(get {{:foo :bar} :baz} {:foo :bar}) ; => :baz

{"hello world" 1}

(get {"hello world" 1} "hello world") ; => 1



;;; using DEF for VARS

(def foo 7)

foo ; => 7

;; foo is a symbol
(type foo)
;; => (type 7)
'foo

(type 'foo) ; => clojure.lang.Symbol

;; ASSOC

;; Clojure defines states, you can modify the value into a different state,
;; But you can never change existing states.

(def num 2)

(inc num)

num

(def num 2323)

num

(def grocery-entry {:liquid "Milk"
                    :colour "White"
                    :price  299})

(assoc grocery-entry :on-special? true)
;; {:liquid "Milk", :colour "White", :price 299, :on-special? true}

grocery-entry
;; => {:liquid "Milk", :colour "White", :price 299}

(dissoc grocery-entry :price)
;; => {:liquid "Milk", :colour "White"}

(update grocery-entry :price inc)
;; (inc 299) => 300
;; => {:liquid "Milk", :colour "White", :price 300} - price has inc'd

(update grocery-entry :price + 50)
;; what is the value of :price? => 299
;; (+ 299 59) => 349 (+ (value of :price) 50)
;; => {:liquid "Milk", :colour "White", :price 349} - price has inc'd

;; when you use a function with more than 1 arg in update
;; the first arg is the value of the key you are updating

(dissoc
 (update (assoc grocery-entry :on-special? true)
         :price * 0.5)
 :liquid)
;; => {:colour "White", :on-special? true, :price 149.5}

;; Step-by-step breakdown of the transformation:

;; 1. Start with the original map `grocery-entry`.
(def grocery-entry {:liquid "Milk"
                    :colour "White"
                    :price  299})

;; 2. Add a new key-value pair `:on-special? true` using `assoc`.
(assoc grocery-entry :on-special? true)
;; => {:liquid "Milk", :colour "White", :price 299, :on-special? true}

;; 3. Update the `:price` key by multiplying its value by 0.5 using `update`.
(update (assoc grocery-entry :on-special? true)
        :price * 0.5)
;; => {:liquid "Milk", :colour "White", :price 149.5, :on-special? true}

;; 4. Remove the `:liquid` key using `dissoc`.
(dissoc
 (update (assoc grocery-entry :on-special? true)
         :price * 0.5)
 :liquid)
;; => {:colour "White", :on-special? true, :price 149.5}

;; thread-first
(-> grocery-entry
    (assoc :on-special? true)
    (update :price * 0.5)
    (dissoc :liquid))
;; => {:colour "White", :on-special? true, :price 149.5}
;; this also exists ->> (thread-last)

#_(->> grocery-entry
       (assoc :on-special? true))

;; => (assoc :on-special? true grocery-entry)

;; situations where you don't want to use -> or ->>

(dissoc (assoc grocery-entry :rotten? true)
        :liquid)

(-> grocery-entry
    (assoc :rotten? true)
    (dissoc :liquid))

(* 5 (inc 2))

(-> 5
    (inc)
    (* 2))


(defn put-on-special [entry discount]
  (-> entry
      (assoc :on-special? true)
      (update :price * discount)
      (dissoc :liquid)))

(put-on-special grocery-entry 0.1)
;; => {:colour "White", :on-special? true, :price 29.9}

(def grocery-entry-2 {:liquid "Lemonade"
                      :colour "Yellow"
                      :price  500})

(put-on-special grocery-entry-2 0.25)
;; => {:colour "Yellow", :on-special? true, :price 125.0}



;;;; FUNCTIONAL PROGRAMMING

;; this is a function

(fn [x]
  (* x x))

((fn [x]
   (* x x)) 5)
;; => 25

;; you can define them and give them names

(def my-square (fn [x] (* x x)))

(my-square 5)

;; This was so common that defn was created as a short cut.

(defn square [x]
  (* x x))

;; anonymous functions

#(* % %)
;; same as (fn [x] (* x x))
(#(* % %) 5)

;; These are called first class functions.
;; Functional programming is about usng functions as first class
;; objects in your program; that is both as arguments AND return
;; values to functions

;; higher order functions

(map square [1 2 3 4 5])
;; => (1 4 9 16 25)

;; map is called a higher order function, because one or more
;; of it's arguments are themselves functions.

;; Clojure is full of higher order functions
;; can you name some?

(filter odd? [1 2 3 4 5])
;; => (1 3 5)
(remove odd? [1 2 3 4 5])
;; => (2 4)

(defn under-3? [x]
  (< x 3))

(filter under-3? [1 2 3 4 5])
;; => (1 2)

(filter #(< % 3) [1 2 3 4 5])
;; => (1 2)
(map #(+ % 55) [1 2 3 4 5])
;; => (56 57 58 59 60)

(macroexpand '#(< % 3))
;; => (fn* [p1__11705#] (< p1__11705# 3))

(defn my-divide [x y]
  (/ x y))

(my-divide 10 2)
;; => 5
#(/ %1 %2)
(#(/ %1 %2) 10 2)
;; => 5
(macroexpand '#(/ %1 %2))
;; => (fn* [p1__11723# p2__11724#] (/ p1__11723# p2__11724#))

(defn doubler [x]
  (* x 2))

(defn tripler [x]
  (* x 3))

;; Comp returns a function which is the composite of multiple functions
;; It will apply the functions from the right first (doubler -> tripler)

(def sixer (comp tripler doubler))

sixer

(sixer 2)

;; complement returns a function that is the opposite of the function provided
(def my-even (complement odd?))

(my-even 2) ; => true

(filter my-even [1 2 3 4 5])
;; => (2 4)

(clojure.repl/doc filter)

;; special form: LET

(let [x 2
      y 3]
  (+ x y))

;; => 5
(let [x 5
      y 99]
  (+ x y))
;; => 104

(def gg 50)

(let [x 5]
  (+ gg x))
;; => 55

(let [double-me #(* % 2)]
  {:was-5 (double-me 5)
   :was-10 (double-me 10)})
;; => {:was-5 10, :was-10 20}

(let [special-calculator (fn [add-1 add-2 divide-by multiply-by] 
                           (/ (* (+ add-1 add-2)
                                 multiply-by)
                              divide-by))]
  {:was-5 (special-calculator 1 2 3 4)
   :was-10 (special-calculator 10 20 30 40)})
;; => {:was-5 4, :was-10 40}

(defn add-3 [number]
  (let [to-add 3]
    (+ number to-add)))

(add-3 5)
;; => 8


;; This returns a function
(defn make-n-adder [adder-amount]
  (fn [number]
    (+ number adder-amount)))

(def long-form-make-n-adder
  (fn [adder-amount]
    (fn [number]
      (+ number adder-amount))))

(make-n-adder 59)

((make-n-adder 59) 5)
;; => 64

(def add-100 (make-n-adder 100))

(add-100 5)
;; => 105

(defn capitalise [my-string]
  (clojure.string/upper-case my-string))

(def long-form-capitalise
  (fn [my-string]
    (clojure.string/upper-case my-string)))

(capitalise "hello world")
;; => "HELLO WORLD"

;;; utility for sequences
(first [1 2 3])
(last [1 2 3])
(rest [1 2 3])

(first {:a 1 :b 2 :c 3})
;; => [:a 1]

(first "HELLO")
;; => \H

;; char / character \H

(first [])
;; => nil
(last [])
;; => nil

(seq [1 2 3])
;; => (1 2 3)
(seq {:a 1 :b 2 :c 3})
;; => ([:a 1] [:b 2] [:c 3])
(seq #{1 2 3})
;; => (1 2 3)
(seq "HELLO")
;; => (\H \E \L \L \O)
(seq [])
;; => nil

;; true and false / truthy and falsey

true
false

1 ; truthy
0 ; truthy
nil ; falsey
"" ; truthy
[] ; truthy
{} ; truthy

(if true
  "yes"
  "no")
;; => "yes"

(if false
  "yes"
  "no")
;; => "no"

(if :hello!
  "yes"
  "no")
;; => "yes"

(if 0
  "yes"
  "no")
;; => "yes"

(if []
  "yes"
  "no")
;; => "yes"

(seq [])
;; => nil

;; check if there are values inside
(if (seq [])
  "I have values"
  "I am empty")
;; => "I am empty"

(defn is-empty? [my-seq]
  (if (seq my-seq)
    false
    true))

(is-empty? [])
(is-empty? [1 2 3])
(is-empty? {:a 1 :b 2})
(is-empty? #{})
(is-empty? "")

(macroexpand '(defn halfer [number]
                (/ number 2)))

;; => (def halfer (clojure.core/fn ([number] (/ number 2))))

(macroexpand '(-> grocery-entry
                  (assoc :on-special? true)
                  (update :price * 0.5)
                  (dissoc :liquid)))

;; => (dissoc (update (assoc grocery-entry :on-special? true) :price * 0.5) :liquid)

;; Basic structure of the LOOP macro

(loop [my-numbers [1 2 3]]
  (if (is-empty? my-numbers)
    "I am empty"
    (let [first-number (first my-numbers)
          rest-numbers (rest my-numbers)]
      (println "Dropping - " first-number)
      (println "Looping with - " rest-numbers)
      (recur rest-numbers))))

; Dropping -  1
; Looping with -  (2 3)
; Dropping -  2
; Looping with -  (3)
; Dropping -  3
; Looping with -  ()
"I am empty"


;; Bad equilavent of the above code
(loop [x [1 2 3]]
  (if (seq x)
    (do
      (println "Dropping - " (first x))
      (println "Looping with - " (rest x))
      (recur (rest x)))
    "I am empty"))

(loop [nums-to-add [1 2 3]
       total       0]
  (if-not (is-empty? nums-to-add)
    (let [remaining-nums-to-add (rest nums-to-add)
          current-number-to-add (first nums-to-add)]
      (recur remaining-nums-to-add
             (+ total current-number-to-add)))
    total))
;; => 6

;; numbers to add = (2 3)
;; total = 1

;; remaining-nums-to-add = (3)
;; total = 3

;; remaining-nums-to-add = ()
;; total = 6

;; remaining-nums-to-add = ()
;; this is empty so at the step => if-not (is-empty? nums-to-add)
;; it will return total instead of continueing to recur

;; bad example of the above
;; (recur (rest nums-to-add) (+ total (first nums-to-add)))

(def bag-of-marbles ["red" "blue" "green" "red" "blue"
                     "red" "blue" "green" "red" "blue"
                     "red" "blue" "green" "red" "blue"
                     "red" "blue" "green" "red" "blue"
                     "red" "blue" "green" "red" "blue"
                     "red" "blue" "green" "red" "blue"
                     "red" "blue" "green" "red" "blue"])

(defn types-of-marbles [marbles]
  (set marbles))

(types-of-marbles bag-of-marbles)
;; => #{"red" "blue" "green"}
