(ns analog-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(defn update-digital-clock []
  (dom/set-text (dom/get-element "digital-clock")
                (js/Date)))

(defn draw-line [context start-point end-point color line-width]
  (set! (. context -strokeStyle) color)
  (set! (. context -lineCap) "round")
  (set! (. context -lineWidth) line-width)
  (.beginPath context)
  (.moveTo context (first start-point) (second start-point))
  (.lineTo context (first end-point) (second end-point))
  (.stroke context))

(defn update-analog-clock []
  (let [canvas (dom/get-element "analog-clock")
        context (.getContext canvas "2d")]
    (draw-line context [200 200] [300 300] "red" 20)))

(defn update-clock []
  (update-digital-clock)
  (update-analog-clock))

(js/setInterval update-clock 100)
