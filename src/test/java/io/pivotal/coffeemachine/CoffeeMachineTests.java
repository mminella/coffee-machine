/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.pivotal.coffeemachine;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CoffeeMachineTests {

	private CoffeeMachine machine;

	private Inventory inventory;

	@Before
	public void setup() {
		this.inventory = mock(Inventory.class);
		this.machine = new CoffeeMachine(this.inventory);
	}

	@Test
	public void getMenu() {
		Map<String, Double> menu = this.machine.getMenu();
		assertThat(menu).contains(entry("coffee", 2.75));
		assertThat(menu).contains(entry("cappuccino", 2.90));
		assertThat(menu).contains(entry("caffe mocha", 3.90));
	}

	@Test
	public void makeDrink() {
		this.machine.makeDrink("cappuccino");
		verify(this.inventory).get("coffee", 2);
		verify(this.inventory).get("sugar", 1);
		verify(this.inventory).get("cream", 1);
	}

}