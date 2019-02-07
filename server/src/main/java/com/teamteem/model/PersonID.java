package com.teamteem.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class PersonID implements Serializable {

    int id;

    String name;

    @Id
    String username;

}
