import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.parser.*;
import org.json.*;


public class RecipeBook {

    public static ArrayList<Recipe> recipe_book = new ArrayList<Recipe>();

    public static void main(String[] args) throws Exception {

        /*
        Testing JSON
        JSONObject jo = new JSONObject("{\"abc\": \"def\" }");
        System.out.println(jo.toString());
         */

        //FileReader file = new FileReader("Recipes.json");
        //JSONTokener token = new JSONTokener(file);


        // building recipe book from recipes in recipes.json
        readJson("Recipes.json");

        // current recipe being read
        int recipeIndex = 1000;

        // current step in ingredients
        int currentStep = 0;

        // user input in terminal (could try transferring this in a window using swing or javafx
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome: Type 'm' or 'menu' to go to main menu");

        while (true){
            String input = in.nextLine();

            // menu
            if (input.equals("m") || input.equals("menu")){
                System.out.println("'1' to browse all recipes\n" +
                                   "'2' to search for a recipe\n" +
                                   "'3' to add a new recipe\n" +
                                   "'4' to get a recipe\n" +
                                   "'e' or 'exit' to exit the recipe book");
            }

            if (input.equals("e") || input.equals("")){
                System.exit(0);
            }

            // browsing recipes
            if(input.equals("1")) {
                // listing the recipes to choose from
                System.out.println("Choose a recipe from the list below by entering the corresponding number");
                for(int i = 0; i <recipe_book.size(); i++) {
                    System.out.println((i+1) + ". " + recipe_book.get(i).getName());
                }
                while(true) {
                    try {
                        recipeIndex = Integer.parseInt(in.nextLine()) - 1;
                        currentStep = 0;

                        //printing recipe
                        recipe_book.get(recipeIndex).printAll();

                        System.out.println("Enjoy! Type 'v' to view instructions or 'h' to return to the help menu.");
                        break;
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Oops. You should enter a number.");
                    }
                    catch (IndexOutOfBoundsException ex){
                        System.out.println("Oops. Please enter a recipe number that is listed.");
                    }
                }
            }
            // adding new recipe
            if (in.equals("3")) {
                System.out.println("What is the recipe name?");
                String name = in.nextLine();
                System.out.println("What is the recipe description?");
                String description = in.nextLine();

                // ingredients
                System.out.println("What are the ingredients? Hit enter after each ingredient. Type 'done' when finished.");
                String ingredients = "";
                String next = "";
                while(!next.equals("done")) {
                    next = in.nextLine();
                    if (next.equals("done")) {
                        break;
                    }
                    ingredients += next + "\n";
                }
                String[] ingredientsArray = ingredients.split("\n");

                // instructions
                System.out.println("What are the instructions? Hit enter after each instruction. Type 'done' when finished.");
                String instructions = "";
                next = "";
                int i = 1;
                while(!next.equals("done")) {
                    next = in.nextLine();
                    if (next.equals("done"))
                        break;

                    // listing instruction with index
                    instructions += String.format("%d.%s\n",i,next); // format the instruction with index
                    i++;
                }
                String[] instructionsArray = instructions.split("\n");
                System.out.println("Success! Type 'h' or 'help' to return to the help menu.");

                addRecipe(name, description, ingredientsArray, instructionsArray, "Recipes.json");

                // clearing recipe_book for new recipes
                recipe_book = new ArrayList<Recipe>();

                readJson("Recipes.json"); //read again because of the new updates
            }
        }
    }

    public static void readJson(String filename) throws FileNotFoundException, IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(filename));
        JSONArray book = (JSONArray) obj;

        //parsing through every
        int length = book.length();
        for (int i = 0; i<length; i++){
            parseRecipe((JSONObject)book.get(i));
        }
    }

    public static void parseRecipe(JSONObject recipe){

        // getting "ingredient" elements from JSON file and putting them into an array
        JSONArray jsonIngredients = (JSONArray) recipe.get("ingredients");
        String [] ingredients = new String[jsonIngredients.length()];
        for (int i = 0; i<ingredients.length; i++)
            ingredients[i] = (String) jsonIngredients.get(i);

        // getting "instructions" elements from JSON file and putting them into an array
        JSONArray jsonInstructions = (JSONArray) recipe.get("instructions");
        String instructions[] = new String[jsonInstructions.length()];
        for (int i = 0; i<instructions.length; i++)
            instructions[i] = (String) jsonInstructions.get(i);

        // getting recipe name and description
        Object recipeName = recipe.get("name");
        Object recipeDescription = recipe.get("description");

        Recipe newRecipe = new Recipe((String) recipeName, (String) recipeDescription, ingredients, instructions);

        recipe_book.add(newRecipe);
    }

    public static Recipe getRandomRecipe(int index){
        int randNum = ThreadLocalRandom.current().nextInt(0,recipe_book.size());
        return recipe_book.get(randNum);
    }

    public static void addRecipe(String name, String description, String[] ingredients, String[] instructions, String filename) throws FileNotFoundException, IOException, ParseException {
        FileWriter outFile = null;

        Object obj = new JSONParser().parse(new FileReader(filename));
        JSONArray book = (JSONArray) obj;

        //new entry
        JSONObject entry = new JSONObject();
        entry.put("name", name);
        entry.put("description",description);

        // Taking care of the arrays
        JSONArray jsonIngredients = new JSONArray();

        for (int i = 0; i < ingredients.length; i++)
        {
            jsonIngredients.put(ingredients[i]);
        }

        JSONArray jsonInstructions = new JSONArray();

        for (int i = 0; i < instructions.length; i++)
        {
            jsonInstructions.put(instructions[i]);
        }

        // Putting the last key-value pairs together
        entry.put("ingredients", jsonIngredients);
        entry.put("instructions", jsonInstructions);

        book.put(entry);

        System.out.println(book.toString());

        try
        {

            // Constructs a FileWriter given a file name, using the platform's default charset
            outFile = new FileWriter(filename);
            outFile.write(book.toString());

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            outFile.flush();
            outFile.close();
        }
    }
}