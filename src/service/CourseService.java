package service;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Course;

@Transactional 
@Component(value = "courseService")
public class CourseService extends BaseService<Course> implements ICourseService{
	
}
