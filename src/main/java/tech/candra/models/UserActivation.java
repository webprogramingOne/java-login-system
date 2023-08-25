package tech.candra.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user_activation")
@Getter
@Setter
public class UserActivation extends  PanacheEntityBase{

        @Id
        @SequenceGenerator(name = "userSequence",
                sequenceName = "user_id_sequence", allocationSize = 1, initialValue = 1)

        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
        private Long id;

        @Column(name = "email")
        @NotBlank(message = "email tidak boleh kosong")
        private String email;

        @Column(name = "token")
        @NotBlank(message = "token tidak boleh kosong")
        private String token;
        @Column(name = "created_at")
        private LocalDateTime createdAt;
        @ManyToOne
        UserAdmin userAdmin;
        public static UserActivation findByToken(String token) {
                return find("token", token).firstResult();
        }

        public void setUserAdmin(Long id) {
                userAdmin.getId();
        }

//        public  confirmToken(){}








}
