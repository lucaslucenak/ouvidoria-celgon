package com.unifacisa.ouvidoriacelgon.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class ExpressionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
