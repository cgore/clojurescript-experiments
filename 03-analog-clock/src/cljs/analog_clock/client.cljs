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

(defn update-analog-clock []
  (let [canvas (dom/get-element "analog-clock")
        context (.getContext canvas "2d")
        width 400
        height 400
        center [(/ width 2)
                (/ height 2)]
        date (js/Date.)]
    (.clearRect context 0 0 width height)
    (draw-hour-hand   context center (.getHours   date))
    (draw-minute-hand context center (.getMinutes date))
    (draw-second-hand context center (.getSeconds date))))

(defn update-clock []
  (update-digital-clock)
  (update-analog-clock))

(js/setInterval update-clock 100)
