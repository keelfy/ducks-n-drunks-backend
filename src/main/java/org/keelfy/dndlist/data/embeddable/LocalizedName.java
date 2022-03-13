package org.keelfy.dndlist.data.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Egor Kuzmin
 */
@Data
@Embeddable
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LocalizedName {

    @Column(name = "name")
    private String name;

}