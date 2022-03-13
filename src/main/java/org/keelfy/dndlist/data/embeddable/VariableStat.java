package org.keelfy.dndlist.data.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

/**
 * @author Egor Kuzmin
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VariableStat {

    @Min(0)
    @Column(name = "total")
    private Integer total;

    @Min(0)
    @Column(name = "current")
    private Integer current;

}
