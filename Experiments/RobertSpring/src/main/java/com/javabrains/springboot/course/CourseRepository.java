package com.javabrains.springboot.course;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, String> {
    List<Course> findByTopicId(String topicId);
}
