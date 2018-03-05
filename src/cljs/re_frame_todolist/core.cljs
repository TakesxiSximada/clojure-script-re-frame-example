(ns re-frame-todolist.core
  (:require [reagent.core :as reagent]
            [re-frame-todolist.config :as config]
            [re-frame-todolist.events :as events]
            [re-frame-todolist.views :as views]
            [re-frame.core :as re-frame]
            [stylefy.core :as stylefy]
            ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))


(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (stylefy/init)
  (reagent/render [views/layout]
                  (.getElementById js/document "app")))


(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
