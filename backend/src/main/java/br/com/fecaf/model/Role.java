package br.com.fecaf.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //Alterar no banco para id_role
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;  //Alterar no banco para role_name

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

}
