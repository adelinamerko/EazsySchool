package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.PersonCourse;
import com.eazybytes.eazyschool.model.PersonCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonCourseRepository extends JpaRepository<PersonCourse, PersonCourseId> {
    List<PersonCourse> findByPersonPersonId(int personId);

    @Query(value = "SELECT AVG(pc.rating) AS average_rating FROM person_courses pc WHERE pc.course_id = :courseId AND pc.rating > 0;", nativeQuery = true)
    Integer findCourseRate(int courseId);

    @Query(value = "SELECT pc FROM PersonCourse pc WHERE (pc.status = 'PENDING_REGISTRATION' OR pc.status = 'PENDING_UNREGISTRATION') AND (pc.requestType IS NOT NULL AND pc.requestType != 'NONE')")
    List<PersonCourse> findAllRequests();

    PersonCourse findByPersonPersonIdAndCourseCourseId(int personId, int courseId);
}
