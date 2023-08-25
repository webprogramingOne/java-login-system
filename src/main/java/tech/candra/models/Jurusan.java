package tech.candra.models;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "jurusan")
public class Jurusan extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "jurusanSequence", 
            sequenceName = "jurusan_id_sequence",
            allocationSize = 1, initialValue = 111)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jurusanSequence")
    public Long id;

    @Column( name = "nama_jurusan")
    @NotBlank(message = "Jurusan tidak boleh kosong")
    public String namaJurusan;

    @NotBlank(message = "Jenjang tidak boleh kosong")
    public String jenjang;

    @JsonGetter
    public Long getId(){
        return id;
    }

    @JsonIgnore
    public void setId(Long id){
        this.id = id;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "jurusan_id", referencedColumnName = "id")
    public List<Mahasiswa> mahasiswaList;
}

