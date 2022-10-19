package com.salmac.host.entity;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tecnique")
public class TechniqueEntity  implements Serializable {
    @ApiParam(hidden = true)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.ORDINAL)
    private AttackCategory category;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "script_id", referencedColumnName = "id")
    private ScriptEntity scriptEntity;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "remedy_script_id", referencedColumnName = "id")
    private ScriptEntity remedyScriptEntity;

    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
