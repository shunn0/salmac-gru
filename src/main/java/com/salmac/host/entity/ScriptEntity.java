package com.salmac.host.entity;

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

    @Column(name = "path", nullable = true)
    private String path;

    @Column(name = "target_os", nullable = false)
    private String targetOS;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "purpose", nullable = true)
    private String purpose;

    @Column(name = "script_type", nullable = true)
    private String scriptType;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "last_update_time", nullable = true)
    private LocalDateTime lastUpdateTime;

}
