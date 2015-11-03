(ns quil-sokoban.levels
  (:require [quil.core :as q]))

(def level-1
  {:map [
         [\# \# \# \# \#]
         [\# \  \  \  \#]
         [\# \  \x \o \#]
         [\# \# \# \# \#]]
   :player-start {:x 1 :y 2}
   :size         {:width 4 :height 3}})


(def level-2
  {:map [
         [\# \# \# \# \# \#]
         [\# \  \  \  \  \#]
         [\# \  \  \  \  \#]
         [\# \  \  \  \  \#]
         [\# \# \# \# \# \#]]
   :player-start {:x 2 :y 2}
   :size         {:width 5 :height 4}})


(defn level-width
  [level]
  (get-in level [:size :width]))

(defn level-height
  [level]
  (get-in level [:size :height]))

(defn at-pos
  [level x y]
  (get-in level [:map y x]))

(defn level-x-max
  [env]
  (inc (level-width (:current-level env))))

(defn level-y-max
  [env]
  (inc (level-height (:current-level env))))

(defn level-1-at-pos
  [x y]
  (at-pos level-1 x y))

(defn letter-to-tile-type
  [letter]
  (condp = letter
    \#     :wall
    \@     :player
    \o     :goal
    \x     :box
    \space :floor
    :else  :floor))

(defn tile-at
  [level x y]
  (letter-to-tile-type (at-pos level x y)))

(defn level-1-tile-at
  [x y]
  (letter-to-tile-type (level-1-at-pos x y)))


(defn blocked-at?
  [level x y]
  (condp = (tile-at level x y)
    :floor  false
    :goal   false
    :box    false
    :wall   true
    :player false))


(defn player-y
  [env]
  (get-in env [:player :y]))

(defn player-x
  [env]
  (get-in env [:player :x]))


(defn move-left
  [env]
  (let [level  (:current-level env)
        x      (player-x env)
        y      (player-y env)
        next-x (dec x)
        next-y y]
    (cond
     (<= next-y 0) x
     (blocked-at? level next-x next-y) x
     :else next-x)))

(defn move-right
  [env]
  (let [level  (:current-level env)
        x      (player-x env)
        y      (player-y env)
        next-x (inc x)
        next-y y]
    (cond
     (>= next-y (level-width level-1)) x
     (blocked-at? level next-x next-y) x
     :else next-x)))

(defn move-up
  [env]
  (let [level  (:current-level env)
        x      (player-x env)
        y      (player-y env)
        next-x x
        next-y (dec y)]
    (cond
     (<= next-y 0) y
     (blocked-at? level next-x next-y) y
     :else next-y)))

(defn move-down
  [env]
  (let [level  (:current-level env)
        x      (player-x env)
        y      (player-y env)
        next-x x
        next-y (inc y)]
    (cond
     (>= next-y (level-height level)) y
     (blocked-at? level next-x next-y) y
     :else next-y)))
