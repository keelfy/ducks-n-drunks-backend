package org.keelfy.dndlist.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * @author Egor Kuzmin
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "locale_tab")
public class Locale {

    @Id
    @SequenceGenerator(name = "localeIdSeq", sequenceName = "locale_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "localeIdSeq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @Column(name = "code", length = 3)
    private String code;

}
