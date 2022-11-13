package com.jbcamp1.moonlighthotel.model;

import com.jbcamp1.moonlighthotel.enumeration.BedType;
import com.jbcamp1.moonlighthotel.enumeration.StatusType;
import com.jbcamp1.moonlighthotel.enumeration.ViewType;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "room_reservations")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    @Column(nullable = false)
    private Integer days;

    @Column(nullable = false)
    private Integer adults;

    @Column(nullable = false)
    private Integer kids;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BedType typeBed;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViewType typeView;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Instant created;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status;
}
