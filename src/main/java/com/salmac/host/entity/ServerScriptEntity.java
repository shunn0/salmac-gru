package com.salmac.host.entity;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "server_script")
public class ServerScriptEntity {

    @ApiParam(hidden = true)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "server_id", nullable = false, updatable = false)
    private Long serverId;

    @Column(name = "technique_id", nullable = false , updatable = false)
    private Long techniqueId;

    @Column(name = "script_id", nullable = false, updatable = false)
    private Long scriptId;

    @Column(name = "last_execution_time", nullable = true)
    private LocalDateTime lastExecutionTime;

    @Column(name = "output_text")
    private String outputText;

    @Column(name = "is_remedy")
    private Boolean isRemedy;

    @Column(name = "is_positive")
    private Boolean isPositive;
}
