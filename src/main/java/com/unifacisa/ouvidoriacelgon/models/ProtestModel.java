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
@Table(name = "tb_protest")
@PrimaryKeyJoinColumn(name = "id")
public class ProtestModel extends ExpressionModel {

    @Column
    private String protestDescription;

    @ManyToOne(fetch = FetchType.EAGER,  cascade= CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private UserModel user;
}
