package com.unifacisa.ouvidoriacelgon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_compliment")
@PrimaryKeyJoinColumn(name = "id")
public class ComplimentModel extends ExpressionModel {

    @Column
    private String complimentDescription;
}
