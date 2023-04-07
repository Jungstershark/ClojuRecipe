# ClojuRecipe
ClojuRecipe is a command-line program that uses the Edamam API to suggest recipes based on ingredients. It is written in the Clojure programming language and makes use of functional programming concepts.

## Prerequisites
Before using ClojuRecipe, you will need to install the following:

1. [Java Development Kit (JDK)](https://www.oracle.com/sg/java/technologies/downloads/)
2. [Leiningen](https://leiningen.org/) (Not necessary, but good to have)

ClojuRecipe uses Leiningen as a dependency management tool for configurating our project files. To install Leiningen, follow the instructions on the documentation page, for the appropriate operating system. **This app currently only works with the default firefox browser. Make sure to set the firefox application to your PATH environment variables.**

## API Reference
The Clojurecipe project is built on top of the Edamam API. For more information on the API, please refer to the [Edamam API Documentation](https://developer.edamam.com/edamam-docs-recipe-api).

## Installation
To use ClojuRecipe, follow these steps:
### 1. Clone the repository to your local machine:
```bash
git clone https://github.com/michael-hoon/clojurecipe.git
```

### 2. Get a free API key and app-id from Edamam 
[Edamam account setup and API key retrieval.](https://developer.edamam.com/edamam-docs-recipe-api) Store your API key and app-id in a safe place, as you will be prompted to enter these details before using ClojuRecipe.


### 3. Open your terminal and navigate to the project root file directory. Follow these steps accordingly:

- Run the following command on terminal to start the program:

     ```java
     java -jar target/uberjar/assignment-Final-standalone.jar 
     ```
- Alternatively, if you have leiningen already installed in your system, you can simply run:

     ```bash 
     lein run
     ```

-  If it is the first time you are using ClojuRecipe, follow the prompts to enter your `app-id` and `app-key`. Otherwise, proceed to enter the available main ingredients which you have, comma separated.

- The program will generate a list of the top three recipes that can be cooked using the ingredients available to you, as well as additional ingredients which are required. 

- The program will also redirect you to the relevant URLs that are linked to the webpage containing step-by-step instructions on how to cook the dish. Proceed to follow the instructions and cook your meals!

- You will be able to search for as many different recipes as possible for different ingredients, by returning to the terminal again  


## Functional Programming Concepts
Clojurecipe makes use of several programming concepts:

### Immutable Variables
Immutable variables are variables that cannot be modified once they are defined. In Clojure, variables defined with def are immutable by default. This encourages functional programming by minimizing the potential for side effects.

### Pure Functions
`get-recipe`, `get-top-3`, `display-recipe`, `extract-url`, `open-url`, are all pure functions, which means they have no side effects and always return the same output for the same input. This allows the functions to be modular and easily tested.

### Higher-order Functions
Higher-order functions are functions that take other functions as arguments or return functions as values. They are a fundamental aspect of functional programming, as they enable code reuse and abstraction. `map`, `take`, `doseq`, and `start-repl` are all higher-order functions, which means they take one or more functions as arguments and/or return a function as a result.

Example from source code:
```clojure
(map #(get % :recipe) (-> url
                         http/get
                         :body
                         (json/read-str :key-fn keyword)
                         :hits))
```

In the code, map is used to transform the :hits property of the JSON object into a sequence of recipe maps. The anonymous function `#(get % :recipe)` is passed as an argument to map, which returns the value of the :recipe key for each map in the sequence. The pipeline function `->` will be explained below.

### Loops
The `start-repl` function is a higher order function which demonstrates the input/output while loop functionality, by taking a 'handler' function. Specifically, this is the conditional that the user enters either the ingredients that they have, or the string `"Quit"`. This is called every time the user inputs a command into the terminal. By making use of higher function orders such as `start-repl`, the code becomes more modular and can be applied to different scenarios requiring a conditional loop. 

### Function Composition
The act of chaining multiple functions together to create a new function.

Example from source code:

```clojure
(-> url
     http/get
     :body
     (json/read-str :key-fn keyword)
     :hits)
```

In the code, `->` is used to thread the url value through a sequence of function calls. The `http/get` function makes a GET request to the URL specified by `url`. The `:body` keyword is then used to extract the body of the response. The `json/read-str` function is used to parse the JSON response, returning a Clojure data structure. The `:hits` keyword is used to access the "hits" value from the JSON response.

## Source Code Navigation
The source code for our project can be found under the `src/assignment` folder, which contains the `core.clj` file with all of the function definitions. Our project file with dependencies used can be found in `project.clj`.

## Code Explanation

The code is written in a functional programming style, which means that it emphasizes the use of functions and immutable data structures. The code is also designed to be modular and reusable, with each function serving a specific purpose.

Here is a brief explanation of each function:
`get-recipe`

This function takes in a list of ingredients, an application ID, and an application key, and makes an API call to Edamam to retrieve a list of recipes that match the ingredients. The function returns the top three recipes.

`get-top-3`

This function takes in a list of recipes and returns the top three recipes.

`display-recipe`

This function takes in a recipe and displays the recipe name, the list of ingredients, and a link to the full recipe instructions.

`open-url`

This function takes in a URL and opens it in a web browser.

`extract-url`

This function takes in a recipe and opens the URL for the full recipe instructions in a web browser.

`check-keys`

This function checks whether the keys.txt file exists in the project directory.

`read-keys`

This function reads the Edamam application ID and application key from the keys.txt file.

`write-keys`

This function prompts the user to enter their Edamam application ID and application key, and writes the credentials to the keys.txt file.

`start-repl`

This function starts a REPL (Read-Eval-Print Loop) that prompts the user to enter the ingredients that they have available. The function then displays the top three recipes that match the ingredients, and prompts the user to choose a recipe. If the user chooses a recipe, the function opens the URL for the full recipe instructions in a web browser. If the user enters "Quit", the function exits.

## Future Improvements
1. Ability to tell users what ingredients are missing from their stash.
   
2. Ability to bring up all browsers regardless of what users are using.

3. Faster runtime. 

# Credits
Clojurecipe was created as a school project, to fulfill the requirements of the 01.016 Finding Clojure Module in the Singapore University of Technology and Design. Happy Cooking!

# License
Distributed under the Eclipse Public License - v 2.0. See LICENSE.txt for more information.