(ns analog-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(defn update-digital-clock []
  (dom/set-text (dom/get-element "digital-clock")
                (js/Date)))

(def pi (aget js/Math "PI"))
(def tau (* 2 pi))

(defn radians-from-degrees [degrees]
  (* tau (/ (mod degrees 360) 360)))

(defn sin [theta]
  (.sin js/Math theta))
(defn cos [theta]
  (.cos js/Math theta))
(defn tan [theta]
  (.tan js/Math theta))

(defn cartesian-from-polar [[x0 y0] r theta]
  [(+ x0 (* r (cos theta)))
   (+ y0 (* r (sin theta)))])

(defn draw-line [context [x0 y0] [x1 y1] color line-width]
  (set! (. context -strokeStyle) color)
  (set! (. context -lineCap) "round")
  (set! (. context -lineWidth) line-width)
  (.beginPath context)
  (.moveTo context x0 y0)
  (.lineTo context x1 y1)
  (.stroke context))

(defn degrees-from-hour [hour]
  (let [degrees-per-hour (/ 360 12)
        hour-offset 3]
    (* (- (mod hour 12) hour-offset) degrees-per-hour)))

(defn radians-from-hour [hour]
  (radians-from-degrees (degrees-from-hour hour)))

(defn degrees-from-minute [minute]
  (let [degrees-per-minute (/ 360 60)
        minute-offset 15]
    (* (- minute minute-offset)
       degrees-per-minute)))

(defn radians-from-minute [minute]
  (radians-from-degrees (degrees-from-minute minute)))

(defn radians-from-second [second]
  (radians-from-minute second))

(defn draw-hour-hand [context center hour]
  (draw-line context
             center
             (cartesian-from-polar center 100 (radians-from-hour hour))
             "red"
             20))

(defn draw-minute-hand [context center minute]
  (draw-line context
             center
             (cartesian-from-polar center 150 (radians-from-minute minute))
             "green"
             10))

(defn draw-second-hand [context center second]
  (draw-line context
             center
             (cartesian-from-polar center 175 (radians-from-second second))
             "blue"
             5))

(defn draw-bezel [context center]
  (set! (. context -strokeStyle) "black")
  (set! (. context -fillStyle) "yellow")
  (set! (. context -lineWidth) 5)
  (.beginPath context)
  (.arc context (first center) (second center) 190 0 tau false)
  (.stroke context))

(defn draw-hour-dot [context center hour]
  (let [[x y] (cartesian-from-polar center 190 (radians-from-hour hour))]
    (set! (. context -strokeStyle) "black")
    (set! (. context -fillStyle)
          ;;; WARNING: (contains? [3 6 9] hour) doesn't work the obvious way.
          (cond (contains? #{0 12}  hour) "black"
                (contains? #{3 6 9} hour) "gray"
                :else                     "white"))
    (set! (. context -lineWidth) 4)
    (.beginPath context)
    (.arc context x y 10 0 tau false)
    (.fill context)
    (.stroke context)))

(defn update-analog-clock []
  (let [canvas (dom/get-element "analog-clock")
        context (.getContext canvas "2d")
        width 420
        height 420
        center [(/ width 2)
                (/ height 2)]
        date (js/Date.)]
    (.clearRect context 0 0 width height)
    (draw-bezel context center)

    ;;; WARNING: I tried doing this with map; apparently me and ClojureScript
    ;;; have some disagreements about how map should work I guess, but this
    ;;; actually works at least.
    (doseq [hour (range 12)]
      (draw-hour-dot context center hour))

    (draw-hour-hand   context center (.getHours   date))
    (draw-minute-hand context center (.getMinutes date))
    (draw-second-hand context center (.getSeconds date))))

(defn update-clock []
  (update-digital-clock)
  (update-analog-clock))

(js/setInterval update-clock 100)
