package com.salmac.host.entity;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Script")
public class ScriptEntity implements Serializable {

    @ApiParam(hidden = true)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target_os", nullable = false)
    private String targetOS;

    @Column(name = "content", nullable = false)
    private String content;

}
