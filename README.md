# Clojurecipe
Clojurecipe is a command-line program that uses the Edamam API to suggest recipes based on ingredients. It is written in the Clojure programming language and makes use of functional programming concepts.

## Prerequisites
Before using Clojurecipe, you will need to install the following:

1. Java Development Kit (JDK)
2. Leiningen

## Usage
To use Clojurecipe, follow these steps:
### Clone the repository to your local machine:
```bash
git clone https://github.com/michael-hoon/clojurecipe.git
```

### Follow the instructions in the project's README to configure your Edamam API key.

1. From the project root directory, run the following command to start the program:

```bash
lein run
```

2. Follow the prompts to enter the ingredients you have available to cook.

3. The program will generate a list of recipes that can be cooked using the ingredients available to the user along with other ingredients the user has to buy to complete the dish.

4. The program will also print the URL that is linked to the webpage containing the step-by-step instructions to create the dish.

5. The program will automatically open up the webpage on a local browser on the user's device (it will try chrome, firefox, Microsoft edge, safari).

6. The program loops back to its original state to receive another input query. If the user types "cancel" the loop breaks.

## API Reference
The Clojurecipe project is built on top of the Edamam API. For more information on the API, please refer to the 
[Edamam API Documentation](https://developer.edamam.com/edamam-docs-recipe-api).

## Functional Programming Concepts
Clojurecipe makes use of several programming concepts:

### Immutable Variables
Immutable variables are variables that cannot be modified once they are defined. In Clojure, variables defined with def are immutable by default. This encourages functional programming by minimizing the potential for side effects.

Example from code:
```clojure
(def edamam-url "https://api.edamam.com/api/recipes/v2")
(def app-id "de463581")
(def app-key "364377b5c65a0128d79b275e04d84fcf")
```

In the `code`, `edamam-url`, `app-id`, and `app-key` are all defined as immutable variables using def. This ensures that their values cannot be changed once they are defined.

### Higher-order Functions
Higher-order functions are functions that take other functions as arguments or return functions as values. They are a fundamental aspect of functional programming, as they enable code reuse and abstraction.

Example from code:
```clojure
(map #(get % :recipe) :hits)
```

In the code, map is used to transform the :hits property of the JSON object into a sequence of recipe maps. The anonymous function `#(get % :recipe)` is passed as an argument to map, which returns the value of the :recipe key for each map in the sequence.

### Composition
Function composition is the act of chaining multiple functions together to create a new function. It is a powerful technique for building complex programs from simple, reusable functions.

Example from code:

```clojure
(->> url
     http/get
     :body
     json/read-str
     :hits
     (map #(get % :recipe)))
```

In the code, `->>` is used to thread the url value through a sequence of functions, each of which transforms the data in a specific way. The `http/get` function takes the url and returns an HTTP response, which is then accessed using the `:body` keyword. The `json/read-str` function is used to parse the JSON response, and the `:hits` keyword is used to access the hits property of the parsed object. Finally, map is used to extract the `:recipe` property of each map in the sequence. By chaining these functions together, we can create a simple and readable pipeline for retrieving recipe information.

## Credits
Clojurecipe was created as a school project, to fulfill the requirements of the 01.016 Finding Clojure Module in the Singapore University of Technology and Design.

## License
Distributed under the MIT License. See LICENSE.txt for more information.