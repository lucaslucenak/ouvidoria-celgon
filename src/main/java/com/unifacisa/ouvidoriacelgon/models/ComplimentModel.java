package com.unifacisa.ouvidoriacelgon.models;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER,  cascade= CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private UserModel user;
}
