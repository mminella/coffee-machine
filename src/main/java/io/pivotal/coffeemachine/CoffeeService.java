package io.pivotal.coffeemachine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import io.pivotal.coffeemachine.exception.InvalidDrinkException;
import io.pivotal.coffeemachine.exception.OutOfStockException;
import io.pivotal.coffeemachine.support.Recipe;

public class CoffeeService {

	private Inventory inventory;
	private Map<String, Recipe> recipes;
	private Map<String, Double> menu;

	protected CoffeeService(Inventory inventory) {
		this.inventory = inventory;
		this.menu = new TreeMap<String, Double>();
		this.menu.put("coffee", 2.75);
		this.menu.put("cappuccino", 2.90);
		this.menu.put("caffe mocha", 3.90);

		Map<String, Integer> ingredients = new HashMap<String, Integer>(3);
		ingredients.put("coffee", 2);
		ingredients.put("sugar", 1);
		ingredients.put("cream", 1);

		Recipe recipe = new Recipe();
		recipe.setName("cappuccino");
		recipe.setIngredients(ingredients);
		this.recipes = new HashMap<String, Recipe>(1);
		this.recipes.put("cappuccino", recipe);
	}

	/**
	 * Returns the menu for this coffee machine.
	 *
	 * @return a map of drink name to drink cost
	 */
	public Map<String, Double> getMenu() {
		return Collections.unmodifiableMap(this.menu);
	}

	/**
	 * Make a drink using the given name. Ingredients for the drink are deducted from the inventory.
	 *
	 * @param name the name of the drink
	 */
	public Drink makeDrink(String name) {
		Recipe recipe;

		if(this.recipes.containsKey(name)) {
			recipe = this.recipes.get(name);
		}
		else {
			throw new InvalidDrinkException(String.format("%s is not a valid drink", name));
		}

		// I validate that we have the ingredients in advance since we won't be able to easily roll back
		if(validateIngredients(recipe)) {
			Drink drink = new Drink();

			for (Map.Entry<String, Integer> stringRecipeEntry : recipe.getIngredients().entrySet()) {
				this.inventory.deduct(stringRecipeEntry.getKey(), stringRecipeEntry.getValue());
			}

			drink.setName(name);
			drink.setIngredients(Collections.unmodifiableMap(recipe.getIngredients()));

			return drink;
		}
		else {
			throw new OutOfStockException("Not enough of the requested ingredients");
		}
	}

	private boolean validateIngredients(Recipe recipe) {
		Map<String, Integer> ingredients = this.inventory.getIngredients();

		for (Map.Entry<String, Integer> ingredient : recipe.getIngredients().entrySet()) {

			if(ingredients.containsKey(recipe.getName())) {
				if(ingredients.get(ingredient.getKey()) < ingredient.getValue()) {
					return false;
				}
			}
			else {
				return false;
			}
		}

		return true;
	}
}
