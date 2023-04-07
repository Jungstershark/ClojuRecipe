(ns assignment.core
  (:gen-class)
  (:require [clojure.data.json :as json])
  (:require [clj-http.client :as http])
  (:require [clojure.java.shell :as sh])
  (:require [clojure.java.io :as io]))

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
  (let [browser "firefox"] 
    (sh/sh browser url)))

(defn open-all-url [recipe]
  (open-url (:url recipe)))

; Function to check if keys.txt exists
(defn check-keys [path]
  (.exists (io/file path)))

(defn read-keys [path]
  (with-open [rdr (clojure.java.io/reader path)]
    (reduce conj [] (line-seq rdr))))

(defn write-keys [path]
  (println "\nEnter your Edamam app-id: ") 
  (with-open [wrtr  (io/writer path)] 
    (.write wrtr (str (read-line) "\n"))) 
  (println "Enter your Edamam app-key: ") 
  (with-open [wrtr  (io/writer path :append true)]  
    (.write wrtr (read-line))))

(defn start-repl [app-id app-key]
  (while true
    (try
      (println "\nEnter your available ingredients, separated by commas (or enter 'Quit' to exit): ")
      (let [input (read-line)]
        (if (or (= input "Quit") (= input "quit"))
          (do
            (println "Hope you enjoyed cooking, Goodbye!")
            (System/exit 0))
          (let [ingredients (clojure.string/split input #",")]
            (doseq [recipe (get-top-3 (get-recipe ingredients app-id app-key))]
              (display-recipe recipe)
              (open-all-url recipe)))))
      (catch Exception e (do
                           (println (str "\nInvalid app-id or app-key.\n" e))
                           (write-keys "./keys.txt"))))))


 (defn -main []
   (println "Welcome to ClojuRecipe! We'll provide you with delicious recipes that you can cook at home, based on the ingredients which you have!")
   (if (and (check-keys "./keys.txt") (= (count (read-keys "./keys.txt")) 2))
     (apply start-repl (read-keys "./keys.txt"))
     (do 
       (write-keys "./keys.txt")
       (apply start-repl (read-keys "./keys.txt")))))