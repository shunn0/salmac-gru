package com.salmac.host.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "script")
public class ScriptEntity implements Serializable {

    @ApiParam(hidden = true)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ApiParam(hidden = true)
    @JsonIgnore
    @Column(name = "path", nullable = true)
    private String path;

    @Column(name = "target_os", nullable = false)
    private String targetOS;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "purpose", nullable = true)
    private String purpose;

    @Enumerated(EnumType.ORDINAL)
    private ScriptType scriptType;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "last_update_time", nullable = true)
    private LocalDateTime lastUpdateTime;

    //@OneToOne(mappedBy = "scriptEntity")
    //private TechniqueEntity techniqueEntity;

}
