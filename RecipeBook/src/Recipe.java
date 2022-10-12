public class Recipe{
    private int id;
    private String name;
    private String description;
    private Boolean favorite;
    private String[] ingredients;
    private String[] instructions;

    public Recipe(){

    }



    public Recipe(String name, String description, String[] ingredients, String[] instructions, boolean favorite) {
        this.name=name;
        this.favorite=favorite;
        this.description=description;
        this.ingredients=ingredients;
        this.instructions=instructions;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean isFavorite() {
        return this.favorite;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String[] getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public void printAll(){
         System.out.println("Recipe Name: ");
         System.out.println(this.name);
         System.out.println("Recipe Description: ");
         System.out.println(this.description);
         System.out.println("Recipe Ingredients: ");
         for(String i: this.ingredients)
            System.out.println(i);
         System.out.println("Recipe Instructions: ");
         for(String i: this.instructions)
             System.out.println(i);

    }



    
}