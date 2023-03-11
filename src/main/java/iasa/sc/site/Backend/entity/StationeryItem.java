package iasa.sc.site.Backend.entity;

import iasa.sc.site.Backend.entity.enums.StationeryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "stationery")
public class StationeryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private StationeryType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

//    @OneToMany(mappedBy = "stationeryItem", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Picture> pictures;
}