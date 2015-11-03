(ns quil-sokoban.dynamic
  (:require [quil-sokoban.levels :as l])
  (:require [quil.core :as q]))

(def title
  "Sokoban")

(def size
  [800 600])

(def tile-size 32)

(defn player-tile-x
  [env]
  (* tile-size (l/player-x env)))

(defn player-tile-y
  [env]
  (* tile-size (l/player-y env)))

(defn update
  [env]
  env)

(defn game-images
  []
  {:floor   (q/load-image "yoshi-32-floor.png")
   :wall    (q/load-image "yoshi-32-wall.png")
   :box     (q/load-image "yoshi-32-box.png")
   :goal    (q/load-image "yoshi-32-dock.png")
   :player  (q/load-image "yoshi-32-worker.png")})

(defn setup
  []
  (let [level l/level-2]
    {:player        (:player-start level)
     :images        (game-images)
     :current-level level}))


(defn handle-up-key-press
  [env]
  (assoc-in env [:player :y] (l/move-up env)))

(defn handle-down-key-press
  [env]
  (assoc-in env [:player :y] (l/move-down env)))

(defn handle-left-key-press
  [env]
  (assoc-in env [:player :x] (l/move-left env)))

(defn handle-right-key-press
  [env]
  (assoc-in env [:player :x] (l/move-right env)))


(defn handle-key-press
  [event env]
  (condp = (:key-code event)
    37 (handle-left-key-press env)
    39 (handle-right-key-press env)
    38 (handle-up-key-press env)
    40 (handle-down-key-press env)
    env))

(defn key-pressed
  [env event]
  (handle-key-press event env))

(defn draw-level
  [env]
  (doseq [x (range 0 (l/level-x-max env))
          y (range 0 (l/level-y-max env))
          :let [tile-type  (l/tile-at (:current-level env) x y)
                tile-x     (* tile-size x)
                tile-y     (* tile-size y)
                tile-image (get (:images env) tile-type)]]
    (when (some? tile-image)
      (q/image tile-image tile-x tile-y))))

(defn draw-player
  [env]
  (let [player-image (:player (:images env))]
    (q/image player-image
             (player-tile-x env) (player-tile-y env))))

(defn draw
  [env]
  (draw-level env)
  (draw-player env)
  env)
