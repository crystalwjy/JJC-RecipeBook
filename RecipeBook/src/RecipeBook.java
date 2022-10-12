import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import me.xdrop.fuzzywuzzy.*;
import me.xdrop.fuzzywuzzy.model.*;

public class RecipeBook {
    public static ArrayList<Recipe> recipeBook=new ArrayList<Recipe>();
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

                System.out.println("Please enter the recipe number from the list");
                for(int i=0;i<recipeBook.size();i++){
                    System.out.println((i+1)+". "+recipeBook.get(i).getName());
                }
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
                        System.out.println("Bon Appetit!");
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
                System.out.println("Please enter recipe name: ");
                String rname=in.nextLine();

                System.out.println("Please enter recipe description: ");
                String rdescription=in.nextLine();

                System.out.println("Please enter recipe ingredients. Press enter after each ingredient and type 'done' when you are finished.");
                String ringredients="";
                String next="";
                while(next!="done"){
                    next=in.nextLine();
                    if(next.equals("done")){
                        break;
                    }
                    ringredients+=next+"\n";
                }
                String[] ingredientsArr=ringredients.split("\n");

                System.out.println("Please enter recipe instructions. Press enter after each instruction and type 'done' when you are finished.");
                String rinstructions="";
                next="";
                int index=1;
                while(!next.equals("done")){
                    next=in.nextLine();
                    if(next.equals("done")){
                        break;
                    }
                    rinstructions+=String.format("%d.%s\n",index,next);
                }
                String[] instructionsArr=rinstructions.split("\n");

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
            Boolean favorite = false;
            if(recipe.get("favorite") != null){
                favorite = true;
            }
            Recipe new_recipe = new Recipe(
                    (String) recipe.get("name"),
                    (String) recipe.get("description"),
                    ingredients, instructions, favorite);
            recipeBook.add(new_recipe);
            //System.out.println(recipeBook);
            //String name = (String) recipe.get("name");
            //System.out.println(name);
        }

    }





}

