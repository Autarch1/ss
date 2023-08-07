package StudentRegistration.dto;

import javax.servlet.annotation.WebFilter;


public class CourseResponseDTO {

	private String courseName;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}

