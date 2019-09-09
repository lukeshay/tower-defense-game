package com.javabrains.springboot.tutorial.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(String topicId) {
        return courseRepository.findByTopicId(topicId);
    }

    public Course getCourse(String id) {
        return courseRepository.findById(id).get();
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(String id, Course course) {
        Optional<Course> t = courseRepository.findById(id);

        if (t.isPresent()) {
            courseRepository.delete(t.get());
            courseRepository.save(course);
        }
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }
}
