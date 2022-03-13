package org.keelfy.dndlist.data.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Egor Kuzmin
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProtectionAmount {

    @Column(name = "protection")
    private Integer protection;

    @Max(100)
    @Min(0)
    @Column(name = "chance")
    private Integer chance = 100;

}
