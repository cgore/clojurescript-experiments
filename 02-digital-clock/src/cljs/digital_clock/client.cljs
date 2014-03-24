(ns digital-clock
  (:require [clojure.browser.dom :as dom]))

(defn handle-click []
  (js/alert (js/Date)))

(def clock-paragraph (dom/get-element "digital-clock"))
(.addEventListener clock-paragraph "click" handle-click)
