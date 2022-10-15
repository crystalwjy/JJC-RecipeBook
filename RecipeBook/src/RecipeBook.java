import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import me.xdrop.fuzzywuzzy.*;
import me.xdrop.fuzzywuzzy.model.*;

import javax.swing.*;

public class RecipeBook {
    public static ArrayList<Recipe> recipeBook=new ArrayList<Recipe>();
    //private JButton startButton;
    //private JPanel panelMain;

    public static void main(String[] args) throws Exception {
        read_json("Recipes.json");
        /*
        Testing JSON
        // parsing file "JSONExample.json"
        Object array = new JSONParser().parse(new FileReader("Recipes.json"));
        // typecasting obj to JSONObject
        JSONArray recipes = (JSONArray) array;
        // getting firstName and lastName
        int length = recipes.size();
        for (int i = 0; i < length; i++){
            JSONObject recipe = (JSONObject) recipes.get(i);
            String name = (String) recipe.get("name");
            System.out.println(name);
        }
         */
        // current recipe being read
        int recipeIndex = 1000;

        // current step in ingredients
        int currentStep = 0;

        // user input in terminal (could try transferring this in a window using swing or javafx
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to your Recipe Book! Type 'm' or 'menu' to go to main menu");

        while (true){
            String input = in.nextLine();



            // menu
            if (input.equals("m") || input.equals("menu")){
                System.out.println("'b' or 'browse' to browse all recipes\n" +
                        "'s' or 'search' to search for a recipe\n" +
                        "'a' or 'add' to add a new recipe\n" +
                        "'e' or 'exit' to exit the recipe book");
            }

            // exit
            if (input.equals("e") || input.equals("exit")){
                System.exit(0);
            }

            // browse recipe
            if(input.equals("b") || input.equals("browse")){

                // listing the recipes to choose from
                System.out.println("Choose a recipe from the list below by entering the corresponding number");
                for(int i = 0; i < recipeBook.size(); i++) {
                    System.out.println((i+1) + ". " + recipeBook.get(i).getName());
                }
                while(true) {
                    try {
                        recipeIndex = Integer.parseInt(in.nextLine()) - 1;
                        currentStep = 0;

                        //printing recipe
                        recipeBook.get(recipeIndex).printAll();

                        System.out.println("\nType 'v' to view instructions or 'm' to return to menu.");
                        break;
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Error: Please enter a valid number.");
                    }
                    catch (IndexOutOfBoundsException ex){
                        System.out.println("Error: Please enter a recipe number that is listed.");
                    }
                }
            }

            // printing instructions
            if(input.equalsIgnoreCase("v")) {
                if (recipeIndex == 1000) {
                    System.out.println("Oops. There are no instructions for you because you haven't chosen a recipe yet.");
                    continue;
                }

                System.out.println("Step by step view. Hit enter to view the next instruction.");
                //print the next step
                // while (in.nextLine().isEmpty() && currentStep < recipe_book.get(recipeIndex).getInstructions().length){
                while ((recipeBook.get(recipeIndex).getInstructions().length > currentStep) && in.nextLine().isEmpty() ){
                    System.out.println(recipeBook.get(recipeIndex).getInstructions()[currentStep]);
                    currentStep++;
                }
                System.out.println("End of recipe instructions. Type 'm' or 'menu'");

            }
            // search recipe
            if(input.equals("s") || input.equals("search")){
                //System.out.println(recipeBook);
                System.out.println("Search for a recipe:");
                String searchLine = in.nextLine();
                ArrayList<String> recipeNames = new ArrayList<String>();
                for (Recipe r : recipeBook) {
                    recipeNames.add(r.getName());
                }
                List<ExtractedResult> resultList = FuzzySearch.extractTop(searchLine, recipeNames, 2);

                for (int i = 0; i < resultList.size(); i++) {
                    String index = Integer.toString(i + 1);
                    System.out.println(index + ". " + resultList.get(i).getString());
                }
                int searchIndex = 0;
                System.out.println("Select a number:");
                String number = in.nextLine();
                while(true){
                    try {
                        searchIndex = Integer.parseInt(number) - 1;
                        //print entire recipe
                        for (Recipe r: recipeBook) {
                            if (r.getName().equals(resultList.get(searchIndex).getString())) {
                                r.printAll();
                            }
                        }
                        System.out.println("Bon Appetit! Type 'v' to view instructions or 'm' to return to menu.");
                        break;
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Error. Please enter a number.");
                    }
                    catch (IndexOutOfBoundsException ex){
                        System.out.println("Error. Please enter a listed number.");
                    }
                }
            }

            // add recipe
            if(input.equals("a") || input.equals("add")){
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
                System.out.println("Type 'm' or 'menu' to return to menu.");

                addRecipe(name, description, ingredientsArray, instructionsArray, "Recipes.json");

                // clearing recipe_book for new recipes
                recipeBook = new ArrayList<Recipe>();

                read_json("Recipes.json"); //read again because of the new updates

            }

        }
    }

    public static void read_json(String filename) throws FileNotFoundException, IOException, ParseException {

        Object array = new JSONParser().parse(new FileReader(filename));
        // typecasting obj to JSONArray
        JSONArray recipes = (JSONArray) array;
        int length = recipes.size();
        for (int i = 0; i < length; i++){
            JSONObject recipe = (JSONObject) recipes.get(i);
            // getting "ingredient" elements from JSON file and putting them into an array
            JSONArray jsonIngredients = (JSONArray) recipe.get("ingredients");
            String [] ingredients = new String[jsonIngredients.size()];
            for (int j = 0; j<ingredients.length; j++)
                ingredients[j] = (String) jsonIngredients.get(j);
            // getting "instructions" elements from JSON file and putting them into an array
            JSONArray jsonInstructions = (JSONArray) recipe.get("instructions");
            String instructions[] = new String[jsonInstructions.size()];
            for (int j = 0; j<instructions.length; j++)
                instructions[j] = (String) jsonInstructions.get(j);

            // getting recipe name and description
            Object recipeName = recipe.get("name");
            //System.out.println(recipeName);
            Object recipeDescription = recipe.get("description");
            Recipe new_recipe = new Recipe(
                    (String) recipe.get("name"),
                    (String) recipe.get("description"),
                    ingredients, instructions);
            recipeBook.add(new_recipe);
            //System.out.println(recipeBook);
            //String name = (String) recipe.get("name");
            //System.out.println(name);
        }

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
            jsonIngredients.add(ingredients[i]);
        }

        JSONArray jsonInstructions = new JSONArray();

        for (int i = 0; i < instructions.length; i++)
        {
            jsonInstructions.add(instructions[i]);
        }

        // Putting the last key-value pairs together
        entry.put("ingredients", jsonIngredients);
        entry.put("instructions", jsonInstructions);

        book.add(entry);

        //System.out.println(book.toString());

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

