package com.eazybytes.eazyschool.model;

import com.eazybytes.eazyschool.utils.RequestType;
import com.eazybytes.eazyschool.utils.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_courses")
@IdClass(PersonCourseId.class)
public class PersonCourse {

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    @Min(1)
    @Max(5)
    private int rating = 0;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private Status status;

    public PersonCourse(PersonCourseId personCourseId, Status status) {
        this.person = personCourseId.getPerson();
        this.course = personCourseId.getCourse();
        this.status = status;
    }

    public PersonCourse(PersonCourseId personCourseId) {
        this.person = personCourseId.getPerson();
        this.course = personCourseId.getCourse();
    }
}
