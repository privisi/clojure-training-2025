(ns clojure-training-2025.assignments.John.lesson-01-answers)
;; Explain the differences between list and vector, show examples and usages

; Lists are a sequential form of a collection, the input values are accessed from the first to the last in order

; Lists add new args to the beginning of their sequences
; e.g. (conj '(1 2 3 4)5) would output (5 1 2 3 4) 
; With this behavior, it's better to use a list if you need to keep adding data to the start 

; Lists are also processed as function calls, so when you call it, you're asking clojure to process it as a function 

; For vectors, the elements inside are treated as pure data, they won't be processed as a full function
; You could see vector data as having an index number assigned to each argument to call 
; When you add args via conj to a vector, it adds it to the end 
; e.g. (conj [1 2 3 4 5] 6) = [1 2 3 4 5 6]

; Vectors are processed as data, so making something like [+ 1 1] = [+ 1 1], it doesn't evaluate the values
; inside as code, but rather as raw data

; Vectors are better for pulling quick data as we can simply let the data be pulled from the list, the data
; doesn't have to accessed from the start if you wanted to find data at the very end
; so, better for random access?

;; Problem https://4clojure.oxal.org/#/problem/20
; Write a function which returns the second to last element from a sequence.

;(= (__ (list 1 2 3 4 5)) 4)

;(= (__ ["a" "b" "c"]) "b")

;(= (__ [[1 2] [3 4]]) [1 2])

(fn [collection]
  (nth collection
       (- (count collection) 2)))

; I'm turning the data into a collection, and we want the code to find the nth value within it
; We'll use count because it would be able to take the different number of variables in each problem
; We'll minus 2 from the count so the nth value would be the count minus 2 
; which would always be the second last value of the collection

;; Problem https://4clojure.oxal.org/#/problem/24
; Write a function which returns the sum of a sequence of numbers.

;(= (__ [1 2 3]) 6)
;(= (__ (list 0 -2 5 5)) 8)
;(= (__ #{4 2 1}) 7)
;(= (__ '(0 0 -1)) -1)
;(= (__ '(1 10 3)) 14)

(fn [collection]
  (reduce + collection))

; Once again, we're giving all these different lists, vectors, sets, a simple arg name, "collection"
; Reduce takes a function and applies to all elements in the collection to give a final value
; In the first example, it's taking [1 2 3] as the collection then applying the "+" function to each value returned
; so it's doing 1+2 = 3 then 3 + 3 = 6

;; Problem https://4clojure.oxal.org/#/problem/38 (No using max)
; Write a function which takes a variable number of parameters and returns the maximum value.

(= (__ 1 8 3 4) 8)
(= (__ 30 20) 30)
(= (__ 45 67 11) 67)

(fn [& numbers]
  (reduce (fn [a b]
          (if (> a b) a b))
          numbers)
  )

; This time we'll name the collection numbers (or whatever)
; We need & because we have multiple variables in each line
; We'll make a function with args a and b, so if a is greater than b, it'll return a, else b
; Reduce is going to apply this function to each value 
; so in the first line, it's essentially doing variables 1 and 8, if 1 is greater than 8, return 1, but 
; since it's false, it returns 8, then since we have reduce, it's going to go to the next number in sequence
; which is 3
; so since 8 > 3, it returns 8, so on and so forth


;; Create a function that returns a histogram of the amount of letters in a string. e.g. 
;; “Hello” returns some data structure that indicates there is 1 ‘H’, 1 ‘e’, 2 ‘l’, 1 ‘o’

( (fn [thewordimcounting]
    (frequencies thewordimcounting)) "Hello")

; the function counts the frequencies of the number of letters, I think it's pretty simple
