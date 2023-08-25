package tech.candra.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user_profile")
@Setter
@Getter
public class UserProfile extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "profileSequence",
            sequenceName = "profile_id_sequence",allocationSize = 1, initialValue = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSequence")
    private Long id;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "jenis_kelamin")
    private String jenisKelamin;

    @Column(name = "pendidikan")
    private String pendidikan;

    @Column(name = "nomor_telepon")
    private String nomorTelepon;

    @Column(name = "agama")
    private String agama;

    @Column(name = "tanggal_lahir")
    private Date tanggalLahir;


    @OneToOne(targetEntity = UserAdmin.class)
    @JoinColumn(name = "user_admin_id")
    UserAdmin userAdminId;

    @Column(name = "created_at")
    public LocalDateTime createAt;


    @Column(name = "update_at")
    public LocalDateTime updateAt;



    public static UserProfile findByUserAdminId(long id) {
        return find("user_admin_id", id).firstResult();
    }



}
