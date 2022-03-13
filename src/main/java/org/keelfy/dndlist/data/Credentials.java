package org.keelfy.dndlist.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.keelfy.dndlist.data.embeddable.ModificationInfo;
import org.keelfy.dndlist.security.model.CredentialsLockType;
import org.keelfy.dndlist.security.model.UserCredentials;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.ZonedDateTime;

/**
 * @author Egor Kuzmin
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "credentials_tab")
public class Credentials implements UserCredentials {

    @Id
    @SequenceGenerator(name = "credentialsIdSeq", sequenceName = "credentials_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "credentialsIdSeq", strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "authorities", length = 511)
    private String roles;

    @Column(name = "activated", nullable = false)
    private Boolean activated = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "lock_type", nullable = false)
    private CredentialsLockType lockType = CredentialsLockType.NONE;

    @Column(name = "locked_at")
    private ZonedDateTime lockedAt;

    @Column(name = "latest_authentication_attempt_at")
    private ZonedDateTime latestAuthenticationAttemptAt;

    @Column(name = "latest_successful_authentication_at")
    private ZonedDateTime latestSuccessfulAuthenticationAt;

    @Column(name = "authentication_attempt_count", nullable = false)
    private Integer authenticationAttemptCount = 0;

    @Embedded
    private ModificationInfo modificationInfo;

}
