package tech.candra.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "dosen")
public class Dosen extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "dosenSequence",
            sequenceName = "dosen_id_sequence",
            allocationSize = 1,
            initialValue = 0001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dosenSequence")
    public Long id;

    @NotBlank(message = "NIP tidak boleh kosong")
    public String nip;

    @NotBlank(message = "Nama Dosen tidak boleh kosong")
    @Column(name = "nama_dosen")
    public String nama;

    @NotBlank(message = "Jenis Kelamin tidak boleh kosong")
    @Column(name = "jenis_kelamin")
    public String jenisKelamin;

    @NotBlank(message = "Nomer Telepon tidakboleh kosong")
    @Column(name = "nomer_telepon")
    public String noTlp;

    @NotBlank(message = "Alamat tidak boleh kosong")
    public String alamat;


    @JsonGetter
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dosen_id", referencedColumnName = "id")
    public List<MataKuliah> mataKuliahList;



}