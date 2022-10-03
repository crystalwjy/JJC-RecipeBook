public class Recipe{
    private int id;
    private String name;
    private String[] ingredients;
    private String[] instructions;

    public Recipe(){

    }

    public Recipe(int id, String name, String[] ingredients, String[] instructions){
        this.id=id;
        this.name=name;
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
         System.out.println("Recipe Ingredients: ");
         for(String i: this.ingredients){
            System.out.println(this.i);
         }
         System.out.println("Recipe Instructions: ");
         for(String i: this.instructions){
             System.out.println(this.i);
         }

    }



    
}