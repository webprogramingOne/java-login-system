package tech.candra.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import tech.candra.util.RegexUtil;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_admin")
@Setter
@Getter
public class UserAdmin extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "userSequence",
            sequenceName = "user_id_sequence", allocationSize = 1, initialValue = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    private Long id;

    @Column(name = "nama_lengkap")
//    @NotBlank(message = "nama tidak boleh kosong")
    private String namaLengkap;

    @Column(name = "username")
//    @NotBlank(message = "username tidak boleh kosong")
    private String username;

    @Column(name = "password")
//    @NotBlank(message = "password tidak boleh kosong")
    private String password;

    @Column(name = "email")
//    @NotBlank(message = "alamat email tidak boleh kosong")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "isActive")
    private Boolean isActive = false;

    @Column(name = "update_at")
    private LocalDateTime updateAt;
    private String token;




    public static UserAdmin findbyUsername(String username) {
        return find("username", username).firstResult();
    }
    public static UserAdmin findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
