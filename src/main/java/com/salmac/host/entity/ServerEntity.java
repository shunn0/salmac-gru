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
@Table(name = "server")
public class ServerEntity implements Serializable {

    @ApiParam(hidden = true)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "os", nullable = false)
    private String os;

    @Column(name = "latest_uptime", nullable = true)
    private LocalDateTime latestUptime;

    @Column(name = "latest_downtime", nullable = true)
    private LocalDateTime latestDowntime;

    @Column(name = "status", nullable = false)
    private String status;
}