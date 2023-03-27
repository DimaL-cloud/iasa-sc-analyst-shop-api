package iasa.sc.site.Backend.entity;

import iasa.sc.site.Backend.entity.enums.PrintType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.util.UUID;

@Entity
@Table(name = "prints")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Print {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "uuid", nullable = false, unique = true, columnDefinition = "uuid")
    @Generated(value = GenerationTime.INSERT)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = false)
    private PrintType printType;

    @Column(name = "text", nullable = true, unique = false)
    private String text;
}
