package org.keelfy.dndlist.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.keelfy.dndlist.data.embeddable.ModificationInfo;
import org.keelfy.dndlist.data.embeddable.VariableStat;
import org.keelfy.dndlist.model.AccessType;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Egor Kuzmin
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "character_tab")
public class Character {

    @Id
    @SequenceGenerator(name = "characterListIdSeq", sequenceName = "character_list_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "characterListIdSeq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType = AccessType.GAME;

    @Column(name = "name")
    private String name;

    @Column(name = "experience")
    private Long experience;

    @Column(name = "level")
    private Long level;

    @Column(name = "speed")
    private Long speed;

    @ElementCollection
    @CollectionTable(name = "character_stat_value_tab", joinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "character_stat_value_fk")))
    @MapKeyJoinColumn(name = "character_stat_id")
    private Map<CharacterStat, VariableStat> stats;

    @ElementCollection
    @CollectionTable(name = "character_skill_value_tab", joinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "character_skill_value_fk")))
    @MapKeyJoinColumn(name = "character_skill_id")
    private Map<CharacterSkill, VariableStat> skills;

    @Embedded
    private VariableStat hitPoints;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "character_class_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "character_class_fk"))
    private CharacterClass characterClass;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "character_race_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "character_race_fk"))
    private CharacterRace characterRace;

    @Embedded
    private ModificationInfo modificationInfo;

}
