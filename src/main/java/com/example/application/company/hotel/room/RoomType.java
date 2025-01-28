package com.example.application.company.hotel.room;

import com.example.application.common.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity(name = "RoomType")
@Table(name = "room_type", schema = "public")
public class RoomType extends AbstractEntity {
    private static final long serialVersionUID = -5723739010704127836L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Exclude
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, name = "type")
    private RoomTypeEnum type;

    @OneToMany(mappedBy = "room_type", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomDescription> roomDescriptionsList;
}
