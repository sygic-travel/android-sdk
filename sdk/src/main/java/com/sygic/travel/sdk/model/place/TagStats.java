package com.sygic.travel.sdk.model.place;

/**
 * <p>Tag data.</p>
 */
public class TagStats {
	private String name, key;
	private int count, priority;

	/**
	 * Contructor.
	 * @param name Tag's name.
	 * @param key
	 * @param count
	 * @param priority
	 */
	public TagStats(String name, String key, int count, int priority) {
		this.name = name;
		this.key = key;
		this.count = count;
		this.priority = priority;
	}

	public TagStats(String key){
		this("", key, 0, 0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object tagStats){
		if(tagStats != null && tagStats instanceof TagStats){
			return key.equals(((TagStats) tagStats).getKey());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode(){
		return key.hashCode();
	}
}
