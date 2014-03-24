(ns digital-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(def clock-paragraph (dom/get-element "digital-clock"))

(defn handle-click []
  (dom/set-text clock-paragraph (js/Date)))

(event/listen clock-paragraph :click handle-click)
