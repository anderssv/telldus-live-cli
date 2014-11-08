package no.f12;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MapNavigationWrapper {

	private Map<String, Object> map;

	public MapNavigationWrapper(Map<String, Object> map) {
		this.map = map;
	}

	public Object get(String path) {		
		Object result = this.check(path);
		if (result == null) {
			throw new IllegalArgumentException("Could not find value for '" + path + "' in map " + this.map.toString());
		}
		return result;
	}
	
	public Object check(String path) {
		Queue<ItemSelector> selectors = parsePath(path);
		
		return recurse(map, selectors);
	}
	
	
	private Queue<ItemSelector> parsePath(String path) {
		Queue<ItemSelector> selectors = new LinkedList<>();
		Arrays.asList(path.split("\\.")).forEach(element -> selectors.add(parseSelector(element)));;
		
		return selectors;
	}

	private Object recurse(Object currentLevel, Queue<ItemSelector> selectors) {
		ItemSelector currentSelector = selectors.poll();
		
		if (currentSelector != null) {
			Object nextLevel = currentSelector.select(currentLevel);
			
			if (nextLevel instanceof Map || nextLevel instanceof List) {
				return recurse(nextLevel, selectors);
			}
			
			return nextLevel;
		}
		
		return currentLevel;
	}
	
	private class ItemSelector {
		
		private String item;
		private Integer index;
		private String selectorAttribute;
		private String selectorValue;
		
		private ItemSelector(String item, Integer selectorIndex) {
			this.item = item;
			this.index = selectorIndex;
			
			if (this.item == null) {
				throw new IllegalStateException();
			} 
		}		
		
		public ItemSelector(String element, String attribute, String value) {
			this.item = element;
			this.selectorAttribute = attribute;
			this.selectorValue = value;
		}

		public Object select(Object currentLevel) {
			Object workingLevel = currentLevel;
			if (workingLevel instanceof Map) {
				workingLevel = ((Map) workingLevel).get(this.item);
			}
			
			if (workingLevel instanceof List && this.selectorAttribute != null) {
				List<Map> searchList = (List) workingLevel;
				for (Map listElement : searchList) {
					if (listElement.get(this.selectorAttribute).equals(this.selectorValue)) {
						workingLevel = listElement;
						break;
					}
				}
			} else if (workingLevel instanceof List && this.index != null) {
				workingLevel = ((List) workingLevel).get(this.index);
			}
			
			return workingLevel;
		}

		public String toString() {
			return this.item + ":" + this.index;
		}
	}

	private ItemSelector parseSelector(String elementString) {
		if (elementString.contains("[")) {
			String[] elementParts = elementString.split("\\[");
			String element = elementParts[0];
			String selectorRaw = elementParts[1].replace("]", "");
			if (selectorRaw.contains("=")) {
				String[] selectorElements = selectorRaw.split("=");
				return new ItemSelector(element, selectorElements[0], selectorElements[1]);
			} else {
				return new ItemSelector(element, new Integer(selectorRaw));
			}
			
			
		} else {
			return new ItemSelector(elementString, null);
		}
	}

	@Override
	public String toString() {
		return map.toString();
	}
	
	
	
	

}
