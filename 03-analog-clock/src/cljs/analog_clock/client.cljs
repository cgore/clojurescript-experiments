(ns analog-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(defn update-digital-clock []
  (dom/set-text (dom/get-element "digital-clock")
                (js/Date)))

(defn update-analog-clock []) ; TODO

(defn update-clock []
  (update-digital-clock)
  (update-analog-clock))

(js/setInterval update-clock 100)
