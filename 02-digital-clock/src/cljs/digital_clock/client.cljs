(ns digital-clock
  (:require [clojure.browser.dom :as dom]
            [clojure.browser.event :as event]))

(defn handle-click []
  (js/alert (js/Date)))

(def clock-paragraph (dom/get-element "digital-clock"))

(event/listen clock-paragraph :click handle-click)
