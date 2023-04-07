(ns assignment.core
  (:gen-class)
  (:require [clojure.data.json :as json])
  (:require [clj-http.client :as http])
  (:require [clojure.java.shell :as sh]))

; Defining variables
(def edamam-url "https://api.edamam.com/api/recipes/v2")

; Function to api call to Edamam
(defn get-recipe [ingredients app-id app-key]
  (let [url (str "https://api.edamam.com/search?q="
                 (clojure.string/join "," ingredients)
                 "&app_id="
                 app-id
                 "&app_key="
                 app-key)]
    
         (map #(get % :recipe) (-> url
                                    http/get
                                    :body
                                    (json/read-str :key-fn keyword)
                                    :hits))))

(defn get-top-3 [recipe]
  (take 3 recipe))

(defn display-recipe [recipe]
  (println "Recipe: " (:label recipe))
  (println "Ingredients:")
  (doseq [ingredient (:ingredientLines recipe)]
    (println "  " ingredient))
  (println "Instructions:" (:url recipe)) 
  (println))

(defn open-url [url]
  (let [browser "chrome"] 
    (sh/sh browser url)))

(defn extract-url [recipe]
  (open-url (:url recipe)))

(defn start-repl [app-id app-key]
  (while true
    (println "Enter your available ingredients, separated by commas (or enter 'Quit' to exit): ")
    (let [input (read-line)]
      (if (= input "Quit")
        (do
          (println "Hope you enjoyed cooking, Goodbye!")
          (System/exit 0))
        (let [ingredients (clojure.string/split input #",")]
          (doseq [recipe (get-top-3 (get-recipe ingredients app-id app-key))]
            (display-recipe recipe) 
            (extract-url recipe)))))))

(defn -main []
  (println "Welcome to ClojuRecipe! We'll provide you with delicious recipes that you can cook at home, based on the ingredients which you have!")
  (println "Enter your Edamam app-id: ")
  (let [app-id (read-line)]
    (println "Enter your Edamam API-key: ")
    (let [app-key (read-line)]
      (start-repl app-id app-key))))

