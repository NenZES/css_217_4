import java.util.HashMap;
import java.util.List;
import java.util.Map;

// NutritionPlan class
class NutritionPlan {
    private int dailyCaloricIntake;
    private Map<String, Integer> macroNutrients; // Map to store carbs, protein, fat ratios
    private List<String> mealPlans;
    private String fitnessGoal;
    private List<String> dietaryRestrictions;

    public NutritionPlan(int dailyCaloricIntake, Map<String, Integer> macroNutrients, List<String> mealPlans, String fitnessGoal, List<String> dietaryRestrictions) {
        this.dailyCaloricIntake = dailyCaloricIntake;
        this.macroNutrients = macroNutrients;
        this.mealPlans = mealPlans;
        this.fitnessGoal = fitnessGoal;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Daily Caloric Intake: ").append(dailyCaloricIntake).append("\n");
        output.append("Macro-nutrients: Carbs: ").append(macroNutrients.get("carbs")).append(", Protein: ").append(macroNutrients.get("protein")).append(", Fat: ").append(macroNutrients.get("fat")).append("\n");
        output.append("Meal Plans:\n");
        for (String meal : mealPlans) {
            output.append("- ").append(meal).append("\n");
        }
        output.append("Fitness Goal: ").append(fitnessGoal).append("\n");
        output.append("Dietary Restrictions: ").append(String.join(", ", dietaryRestrictions)).append("\n");
        return output.toString();
    }
}

// NutritionPlanBuilder abstract class
abstract class NutritionPlanBuilder {
    protected int dailyCaloricIntake;
    protected Map<String, Integer> macroNutrients;
    protected List<String> mealPlans;
    protected String fitnessGoal;
    protected List<String> dietaryRestrictions;

    public NutritionPlanBuilder setCaloricIntake(int caloricIntake) {
        this.dailyCaloricIntake = caloricIntake;
        return this;
    }

    public NutritionPlanBuilder setMacronutrientRatios(int carbs, int protein, int fat) {
        this.macroNutrients = new HashMap<>();
        macroNutrients.put("carbs", carbs);
        macroNutrients.put("protein", protein);
        macroNutrients.put("fat", fat);
        return this;
    }

    public NutritionPlanBuilder setMealPlans(List<String> mealPlans) {
        this.mealPlans = mealPlans;
        return this;
    }

    public NutritionPlanBuilder setFitnessGoal(String goal) {
        this.fitnessGoal = goal;
        return this;
    }

    public NutritionPlanBuilder setDietaryRestrictions(List<String> restrictions) {
        this.dietaryRestrictions = restrictions;
        return this;
    }

    public abstract NutritionPlan build();
}

// NutritionPlanDirector class
class NutritionPlanDirector {
    private NutritionPlanBuilder builder;

    public void setBuilder(NutritionPlanBuilder builder) {
        this.builder = builder;
    }

    public NutritionPlan createNutritionPlan() {
        if (builder == null) {
            throw new IllegalStateException("NutritionPlanBuilder not set!");
        }
        return builder.build();
    }
}

// Concrete builder classes
class WeightLossNutritionPlanBuilder extends NutritionPlanBuilder {
    @Override
    public NutritionPlan build() {
        // Construct and return a weight loss nutrition plan
        return new NutritionPlan(dailyCaloricIntake, macroNutrients, mealPlans, fitnessGoal, dietaryRestrictions);
    }
}


public class Main {
    public static void main(String[] args) {
        NutritionPlanDirector director = new NutritionPlanDirector();
        director.setBuilder(new WeightLossNutritionPlanBuilder()
                .setCaloricIntake(2000)
                .setMacronutrientRatios(50, 30, 20)
                .setMealPlans(List.of("Meal 1", "Meal 2"))
                .setFitnessGoal("Weight Loss")
                .setDietaryRestrictions(List.of("Gluten-free", "Vegan")));
        NutritionPlan plan = director.createNutritionPlan();
        System.out.println(plan);
    }
}
