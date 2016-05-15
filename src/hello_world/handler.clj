(ns hello-world.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response]]
            [cheshire.core :refer :all]))


(def users [{:id "1" :firstname "foo" :lastname "bar"}
            {:id "2" :firstname "john" :lastname "doe"}])

(defn log-json
  "Logs given clojure map in json"
  [data]
  (println (generate-string data)))

(defn health
  "Returns ring response health check"
  []
  (log-json {:message "health check OK"})
  (response {:status "OK"}))

(defn get-users
  "Returns all users"
  []
  (response users))

(defn get-user
  "Return user with given id"
  [id]
  (let [user (first (filter (fn [user]
                              (when (= (:id user) id)
                                user))
                            users))]
    (log-json user)
    (if user
      (response user)
      (route/not-found (response {:message "Not found"})))))

(defn duration
  "Returns duration in seconds. Input params should be System/nanoTime"
  [start]
  (float (/ (- (System/nanoTime) start) 1000000)))

(defn wrap-response-time
  "Logs duration of response time"
  [handler]
  (fn [request]
    (let [start (System/nanoTime)
          response (handler request)]
      (log-json {:message "response time"
                 :url (:uri request)
                 :response-time-sec (duration start)})
      response)))

(defroutes handler
  (GET "/health" [] (health))
  (GET "/users" {params :params} (get-users))
  (GET "/users/:id" {{id :id} :params} (get-user id))
  (route/not-found (response {:message "Not Found"})))

(def app
  (-> handler
      wrap-response-time
      wrap-params
      wrap-json-body
      wrap-json-response))
