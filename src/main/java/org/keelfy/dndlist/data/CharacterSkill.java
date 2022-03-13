package org.keelfy.dndlist.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.keelfy.dndlist.data.embeddable.LocalizedName;
import org.keelfy.dndlist.data.embeddable.ModificationInfo;
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
@Table(name = "character_skill_tab")
public class CharacterSkill {

    @Id
    @SequenceGenerator(name = "characterSkillIdSeq", sequenceName = "character_skill_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "characterSkillIdSeq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType = AccessType.FRIENDS;

    @ElementCollection
    @CollectionTable(name = "character_skill_translation_tab", joinColumns = @JoinColumn(name = "character_skill_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "character_skill_translation_fk")))
    @MapKeyJoinColumn(name = "locale_id")
    private Map<Locale, LocalizedName> translations;

    @Embedded
    private ModificationInfo modificationInfo;

}
