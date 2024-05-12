package com.eazybytes.eazyschool.model;

import com.eazybytes.eazyschool.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CourseMaterial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int materialId;

    private String name;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;
}
