(ns analog-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(defn update-digital-clock []
  (dom/set-text (dom/get-element "digital-clock")
                (js/Date)))

(defn update-analog-clock []
  (let [canvas (dom/get-element "analog-clock")
        context (.getContext canvas "2d")]
    (set! (. context -fillStyle) "#ff0000")
    (.fillRect context 0 0 100 100)))

(defn update-clock []
  (update-digital-clock)
  (update-analog-clock))

(js/setInterval update-clock 100)
