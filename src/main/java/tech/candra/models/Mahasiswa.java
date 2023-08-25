package tech.candra.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@Entity
@Table(name = "mahasiswa")
public class Mahasiswa extends PanacheEntityBase {

    @Id
    @SequenceGenerator(
            name = "mahasiswaSequence",
            sequenceName = "mahasiswa_id_sequence",
            allocationSize = 1, initialValue = 2021)
            
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mahasiswaSequence")
    public Long id;

    @NotBlank(message = "Nama Mahasiswa tidak boleh kosong")
    @Column(name = "nama_mahasiswa")
    public String nama;

    @NotBlank(message = "Jenis kelamin tidak boleh kosong")
    @Column(name = "jenis_kelamin")
    public String jenisKelamin;

    @NotBlank(message = "No TLP tidak boleh kosong")
    @Column(name = "no_tlp")
    public String noTlp;

    @NotBlank(message = "Alamat tidak boleh kosong")
    public String alamat;

    @JsonGetter
    public Long getId(){
        return id;
    }

    @JsonIgnore
    public void setId(Long id){
        this.id = id;
    }

    public static boolean isEmpty() {
        return false;
    }
}
