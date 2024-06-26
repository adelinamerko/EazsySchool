package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@RepositoryRestResource(exported = false)
@RepositoryRestResource(path = "courses")
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    /*
    Spring Data JPA allows us to apply static sorting by adding the OrderBy keyword
    to the method name along with the property name and sort direction (Asc or Desc).
    * */
    List<Courses> findByOrderByNameDesc();

    /*
    The Asc keyword is optional as OrderBy, by default,
    sorts the results in the ascending order.
    * */
    List<Courses> findByOrderByName();

    Courses findByCourseId(Integer courseId);

    @Query(value = "SELECT c.* FROM courses c INNER JOIN person_courses pc ON c.course_id = pc.course_id WHERE pc.person_id = :personId", nativeQuery = true)
    List<Courses> findAllCoursesForPerson(int personId);

    @Query(value = "SELECT c.* FROM courses c INNER JOIN person_courses pc ON c.course_id = pc.course_id WHERE pc.person_id = :personId AND pc.course_id= :courseId", nativeQuery = true)
    Courses findCourseForPerson(int personId, int courseId);

}
