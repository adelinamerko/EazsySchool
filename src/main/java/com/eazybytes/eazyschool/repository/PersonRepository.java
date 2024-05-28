package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.PersonCourse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person readByEmail(String email);
    List<Person> findByRolesRoleName(Sort sort, String roleName);

    @Query(value = "SELECT p.* FROM person p INNER JOIN person_courses pc ON p.person_id = pc.person_id INNER JOIN roles r ON p.role_id = r.role_id WHERE pc.course_id = :courseId AND r.role_name = :roleName", nativeQuery = true)
    List<Person> findByCourseIdAndRoleName(int courseId, String roleName);

    @Query(value="SELECT * FROM Person p Where p.roles.roleName = 'LECTURER'", nativeQuery = true)
    List<PersonCourse> findLecturers();


    @Query(value="SELECT * FROM Person p Where p.roles.roleName = 'LECTURER'", nativeQuery = true)
    List<Person> findAllLecturers(String lecturerRole);
}
