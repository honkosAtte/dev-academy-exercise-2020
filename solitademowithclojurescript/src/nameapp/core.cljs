(ns nameapp.core
    (:require
      [reagent.core :as reagent]
      [reagent.dom :as dom]))

; I wanted try ClojureScript and Reagent without diving in too deep. Lean (not clean! ;) code and a very nice development experience!
; Data is now here a bit altered:
(def names (reagent/atom
[{:name "Ville", :amount 24}
 {:name "Anna", :amount 6}
 {:name "Antti", :amount 22}
 {:name "Sanna", :amount 5}
 {:name "Mikko", :amount 19}
 {:name "Minna", :amount 5}
 {:name "Timo", :amount 18}
 {:name "Satu", :amount 5}
 {:name "Tuomas", :amount 16}
 {:name "Tiina", :amount 5}
 {:name "Tero", :amount 15}
 {:name "Kati", :amount 5}
 {:name "Sami", :amount 15}
 {:name "Henna", :amount 4}
 {:name "Mika", :amount 12}
 {:name "Liisa", :amount 4}
 {:name "Janne", :amount 12}
 {:name "Paula", :amount 4}
 {:name "Petri", :amount 11} 
 {:name "Suvi", :amount 4}]
))

(defn name-item [name]
  ^{:key name} [:li (:name name) " " (:amount name)])

  (defn comp-names-amount
  [el1 el2]
  (or  (> (:amount el1) (:amount el2))
       (and (= (:amount el1) (:amount el2))(< (:name el1) (:name el2)))))


(defn name-list-by-amount-desc [names]
 (def NAMES-BY-AMOUNT (sort comp-names-amount names))
   [:ul
    (for [name NAMES-BY-AMOUNT]
      (name-item name))])

(defn name-list-by-name-alphabetical [names]
  (def NAMES-BY-ALPH (sort-by first names))
   [:ul
    (for [name NAMES-BY-ALPH]
      (name-item name))])

(def map-count-of-amount (map #(:amount %)))


(defn home-page []
  [:div
   [:h1 "Names in descending order by amount"]
   [name-list-by-amount-desc @names]
   [:h1 "Names in alphabetical order"]
   [name-list-by-name-alphabetical @names]
   [:h1 "Count of names: " (transduce map-count-of-amount + 0 @names)]
   ]
   )

(defn mount-root []
  (dom/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))