package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.model.PersonCourse;
import com.eazybytes.eazyschool.repository.PersonCourseRepository;
import com.eazybytes.eazyschool.utils.RequestType;
import com.eazybytes.eazyschool.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonCourseService {
    private final PersonCourseRepository personCourseRepository;

    @Autowired
    public PersonCourseService(PersonCourseRepository personCourseRepository) {
        this.personCourseRepository = personCourseRepository;
    }

    public void updateStatusAndRequestType(int courseId, int personId, RequestType requestType, Status status) {
        PersonCourse personCourse = personCourseRepository.findByPersonPersonIdAndCourseCourseId(personId, courseId);
        personCourse.setRequestType(requestType);
        personCourse.setStatus(status);
        personCourseRepository.save(personCourse);
    }

}