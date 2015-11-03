(ns quil-sokoban.core
  (:require [quil.core :as q])
  (:require [quil.middleware :as m])
  (:require [quil-sokoban.dynamic :as dynamic]))

(q/defsketch example
  :title       dynamic/title
  :setup       dynamic/setup
  :update      dynamic/update
  :draw        dynamic/draw
  :size        dynamic/size
  :key-pressed dynamic/key-pressed
  :middleware  [m/fun-mode])
