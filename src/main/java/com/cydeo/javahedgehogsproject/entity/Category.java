package com.cydeo.javahedgehogsproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category  extends BaseEntity{

    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}
