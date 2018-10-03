/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pivotal.coffeemachine.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.pivotal.coffeemachine.Inventory;

/**
 * @author Michael Minella
 */
public class InventorySupport implements Inventory {

	private Map<String, Integer> ingredents;

	public InventorySupport() {
		this.ingredents = new HashMap<String, Integer>(3);
		this.ingredents.put("coffee", 10);
		this.ingredents.put("sugar", 10);
		this.ingredents.put("cream", 10);
	}

	public Map<String, Integer> getIngredients() {
		return Collections.unmodifiableMap(ingredents);
	}

	public void deduct(String name, Integer amount) {
		if(this.ingredents.containsKey(name)) {
			Integer quantity = this.ingredents.get(name);

			if(quantity >= amount) {
				this.ingredents.put(name, quantity - amount);
			}
			else {
				System.err.println(String.format("Invalid amount.  Not enough available"));
			}
		}
		else {
			System.err.println(String.format("Invalid ingrediant name: %s", name));
		}
	}
}