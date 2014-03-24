(ns digital-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(def clock-paragraph (dom/get-element "digital-clock"))

(defn update-clock []
  (dom/set-text clock-paragraph (js/Date)))

(js/setInterval update-clock 100)
