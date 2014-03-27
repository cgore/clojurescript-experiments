(ns analog-clock.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [ring.util.response :as response])
  (:gen-class))

(defn render-app []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body
   (str "<!DOCTYPE html>"
        "<html>"
        "<head>"
        "<link rel=\"stylesheet\" href=\"css/page.css\" />"
        "</head>"
        "<body>"
        "<div>"
        "<canvas id=\"analog-clock\" width=\"420\" height=\"420\">canvas not supported</canvas>"
        "<p id=\"digital-clock\">waiting on Javascript ...</p>"
        "</div>"
        "<script src=\"js/cljs.js\"></script>"
        "</body>"
        "</html>")})

(defn handler [request]
  (if (= "/" (:uri request))
      (response/redirect "/help.html")
      (render-app)))

(def app 
  (-> handler
    (resources/wrap-resource "public")))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000}))

