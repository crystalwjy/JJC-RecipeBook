import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

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



        // building recipe book form recipes in recipes.json
        //read_json("./Recipes.json");

        // current recipe being read
        int recipeIndex = 1000;

        // current step in ingredients
        int currentStep = 0;

        // user input in terminal (could try transferring this in a window using swing or javafx
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome: Type 'm' or 'menu' to go to main menu");

        /*while (true){
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
                System.out.println("Enter the recipe you'd like to search for: ");
                String searchLine = in.nextLine();


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

            // random recipe

        }*/
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
            System.out.println(recipeName);
            Object recipeDescription = recipe.get("description");
            //String name = (String) recipe.get("name");
            //System.out.println(name);
        }

    }





}

