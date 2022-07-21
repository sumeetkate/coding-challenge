package com.example.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Java Model to hold user object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    int id;
    String name;
    String email;
    String gender;
    String status;
}
