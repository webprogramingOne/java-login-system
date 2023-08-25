package tech.candra.models;

import java.util.List;
import javax.persistence.*;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "mata_kuliah")
public class MataKuliah extends PanacheEntityBase{

    @Id
    @SequenceGenerator(
            name = "mataKuliahSequence", 
            sequenceName = "mataKuliah_id_sequence",
            allocationSize = 1, initialValue = 222)
   
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mataKuliahSequence")
    public Long id;

    @NotBlank(message = "Matakuliah tidak boleh kosong")
    public String namaMataKuliah;

    @NotNull(message = "SKS harus diisi")
    public Integer sks;

    @JsonGetter
    public Long getId() {
        return this.id;
    }

    @JsonSetter
    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "mahasiswa_kuliah",joinColumns  = @JoinColumn(
              name = "kuliah_id", referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name = "mahasiswa_id",referencedColumnName = "id"))
        public List<Mahasiswa> mahasiswaList;
    
}
