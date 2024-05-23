package com.eazybytes.eazyschool.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class PersonCourseId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    public PersonCourseId() {
    }

    public PersonCourseId(Person person, Courses course) {
        this.person = person;
        this.course = course;
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, course);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PersonCourseId that = (PersonCourseId) obj;
        return Objects.equals(person, that.person) &&
               Objects.equals(course, that.course);
    }
}
