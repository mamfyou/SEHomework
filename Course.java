package domain;

import java.util.List;

public class Course {
	private String name;
	private List<Course> prerequisites;

	public Course(String name, List<Course> prerequisites) {
		this.name = name;
		this.prerequisites = prerequisites;
	}

	public String getName() {
		return name;
	}

	public List<Course> getPrerequisites() {
		return prerequisites;
	}
}