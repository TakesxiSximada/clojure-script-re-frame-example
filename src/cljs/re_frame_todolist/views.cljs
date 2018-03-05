(ns re-frame-todolist.views
  (:require [reagent.core :as reagent]
             [clojure.string :as cstr]
             [re-frame-todolist.events :as events]
             [re-frame-todolist.subs :as subs]
             [re-frame.core :as re-frame]
             [stylefy.core :as stylefy :refer [use-style]]
             ))


(defn todo-item
  [{:keys [id completed] :as todo}]
  [:li
   [:input {:type "checkbox"
            :class "toggle"
            :checked (and completed "checked")
            :on-change #(re-frame/dispatch [::events/toggle id])}]

   [:span (use-style {:text-decoration (when completed "line-through")})
    (:title todo)]

   [:span {:class "delete"
           :on-click #(re-frame/dispatch [::events/delete id])}
    "[x]"]])


(defn todo-input
  []
  (let [val (reagent/atom "")]
    (fn []
      [:input (merge (use-style {:margin "30px"
                                 :width "60%"})
                     {:type        "text"
                      :value       @val
                      :class       "new-todo"
                      :placeholder "What needs to be done?"
                      :on-change   #(reset! val (-> % .-target .-value))
                      :on-key-down #(when (= (.-which %) 13)
                                      (let [title (-> @val cstr/trim)]
                                        (when (seq title)
                                          (re-frame/dispatch [::events/add-todo title]))
                                        (reset! val "")))})])))


(defn todo-list
  []
  (let [todos @(re-frame/subscribe [::subs/todos])]
    [:div (use-style {:margin "auto"
                      :text-align "center"
                      :width "60%"})
     [todo-input]
     [:ul (use-style {:list-style "none"
                      :text-align "left"})
      (for [todo todos]
        ^{:key (:id todo)}
        [todo-item todo])]]))


(defn header []
  [:header ""])


(defn footer []
  [:footer (use-style {:text-align "center"
                       :position "absolute"
                       :bottom "0"
                       :width "100%"
                       :color "rgba(175,47,47,.15)"})
   (map (fn [txt]
          [:p {:key txt :style {:margin "0"}} txt])
        '("Created by TakesxiSximada"
          "Part of Todo"))])


(defn main-content []
  [:section (use-style {:margin "auto"
                        :text-align "center"})
   [:h1 "Todos"]
   [todo-list]])


(defn layout []
  [:div
   [header]
   [main-content]
   [footer]])
