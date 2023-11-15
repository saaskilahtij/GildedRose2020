package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}

	@Test
	@SuppressWarnings("static-access")
	public void dayChangeDecreasesValuesByOne() {
		
		GildedRose inn = new GildedRose();

		//Item initialization
		List<Item> items = new ArrayList<Item>();
        inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
        inn.setItem(new Item("Aged Brie", 2, 0));
        inn.setItem(new Item("sElixir of the Mongoose", 5, 7));
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        inn.setItem(new Item("Conjured Mana Cake", 3, 6));

		int [] qualities = new int [items.size()];
		int [] prices = new int [items.size()];		
		
		for (int i = 0; i < items.size(); i++) {
			qualities[i] = items.get(i).getQuality();
			prices[i] = items.get(i).getSellIn();
		}
		
		inn.oneDay();
		
		for (int i = 0; i < items.size(); i++) {
			assertEquals("Quality of " + items.get(i).name + " was not lowered", 
				false, items.get(i).getQuality() != (qualities[i] - 1));
			
			assertEquals("Cost of " + items.get(i).name + " was not lowered", 
				false, items.get(i).getSellIn() != (prices[i] - 1));
		}
	}
	
	@Test
	@SuppressWarnings("static-access")
	public void afterSellDatePassedDecreaseByTwo() {
		
		GildedRose inn = new GildedRose();

		//Item initialization
		List<Item> items = new ArrayList<Item>();
        inn.setItem(new Item("+5 Dexterity Vest", 0, 20));
        inn.setItem(new Item("Aged Brie", 0, 0));
        inn.setItem(new Item("sElixir of the Mongoose", 0, 7));
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
        inn.setItem(new Item("Conjured Mana Cake", 0, 6));

		int [] qualities = new int [items.size()];
		
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) != null) {
				qualities[i] = items.get(i).getQuality();
			}
		}
		
		inn.oneDay();
		
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) != null) {
				assertEquals("Quality of " + items.get(i).name + 
						" was not lowered twice after sell date has passed", 
						false, items.get(i).getQuality() != (qualities[i] - 2));
			}
		}
	}
	
	@Test 
	public void qualityIsNotNegative() { 
		GildedRose inn = new GildedRose();

		//Item initialization
		List<Item> items = new ArrayList<Item>();
        inn.setItem(new Item("+5 Dexterity Vest", 10, 0));
        inn.setItem(new Item("Aged Brie", 2, 0));
        inn.setItem(new Item("sElixir of the Mongoose", 5, 0));
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 0));
        inn.setItem(new Item("Conjured Mana Cake", 3, 0));
        
        
        for (int i = 0; i < items.size(); i++) {
        	if (items.get(i) != null) {
        		assertEquals("Quality of" + items.get(i) + "can't be negative",
        				false, items.get(i).getQuality() < 0);
        	}
        }        
	}
	
	@Test
	@SuppressWarnings("static-access")
	public void brieQualityIncrementsByDay() {
		
		GildedRose inn = new GildedRose();
		
        Item brie = new Item("Aged Brie", 0, 0);
        int qualityToCompare = 0;
        
        inn.oneDay();
        qualityToCompare++;
        
        assertEquals("Quality of Aged Brie doesn't improve on time",
        		false, brie.getQuality() <= qualityToCompare);
	}

	
	@Test
	@SuppressWarnings("static-access")
	public void itemSulfurasQualityCantDecrease() {
		
		GildedRose inn = new GildedRose();
		
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 25, 1));
		List<Item> items = inn.getItems();
		int valueToCompare = (items.get(0).getQuality() - 1);
		
		inn.oneDay();
		
		assertEquals("Quality value of Sulfuras can't decrease",
				false, items.get(0).getQuality() != valueToCompare);
	}
	
	
}
