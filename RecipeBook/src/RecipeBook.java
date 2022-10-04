import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.parser.*;
import org.json.*;


public class RecipeBook {
    public static void main(String[] args) throws Exception {

        /*
        Testing JSON
        JSONObject jo = new JSONObject("{\"abc\": \"def\" }");
        System.out.println(jo.toString());
         */

        FileReader file = new FileReader("Recipes.json");
        JSONTokener token = new JSONTokener(file);


        // building recipe book form recipes in recipes.json
        //read_json("./Recipes.json");

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
                System.out.println("'b' or 'browse' to browse all recipes\n" +
                                   "'s' or 'search' to search for a recipe\n" +
                                   "'a' or 'add' to add a new recipe\n" +
                                   "'r' or 'random' to get a recipe\n" +
                                   "'f' or 'favorite' to get your favorite recipe list\n" +
                                   "'e' or 'exit' to exit the recipe book");
            }

            if (input.equals("e") || input.equals("")){
                System.exit(0);
            }
        }
    }

    public static void read_json(String filename) throws FileNotFoundException, IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(filename));
        JSONArray book = (JSONArray) obj;

        int length = book.length();
        for (int i = 0; i<length; i++){

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

    }
}