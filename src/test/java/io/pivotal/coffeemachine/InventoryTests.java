package io.pivotal.coffeemachine;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class InventoryTests {

	private Inventory inventory;

	@Before
	public void setup() {
		this.inventory = new Inventory();
	}

	@Test
	public void listShouldReturnTheIngredientsInInventory() {
		Map<String, Integer> ingredients = this.inventory.list();
		assertThat(ingredients).contains(entry("coffee", 10));
		assertThat(ingredients).contains(entry("sugar", 10));
		assertThat(ingredients).contains(entry("cream", 10));
	}

	@Test
	public void getShouldReduceQuantity() {
		this.inventory.get("coffee", 2);
		Map<String, Integer> ingredients = this.inventory.list();
		assertThat(ingredients).contains(entry("coffee", 8));
	}

}
