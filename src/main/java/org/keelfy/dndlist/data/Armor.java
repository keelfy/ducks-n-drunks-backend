package org.keelfy.dndlist.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.keelfy.dndlist.data.embeddable.ModificationInfo;
import org.keelfy.dndlist.data.embeddable.LocalizedName;
import org.keelfy.dndlist.data.embeddable.ProtectionAmount;
import org.keelfy.dndlist.model.AccessType;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author Egor Kuzmin
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "armor_tab")
public class Armor {

    @Id
    @SequenceGenerator(name = "armorIdSeq", sequenceName = "armor_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "armorIdSeq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

    @ElementCollection
    @CollectionTable(name = "armor_translation_tab", joinColumns = @JoinColumn(name = "armor_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "locale_id")
    private Map<Locale, LocalizedName> translations;

    @ElementCollection
    @CollectionTable(name = "armor_protection_tab", joinColumns = @JoinColumn(name = "armor_protection_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "damage_type_id")
    private Map<DamageType, ProtectionAmount> protection;

    @Embedded
    private ModificationInfo modificationInfo;

}
