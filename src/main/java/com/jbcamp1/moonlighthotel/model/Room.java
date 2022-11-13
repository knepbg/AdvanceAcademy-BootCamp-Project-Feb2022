package com.jbcamp1.moonlighthotel.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private Set<Image> images;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String facilities;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Integer people;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer count;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<RoomReservation> roomReservations;
}
