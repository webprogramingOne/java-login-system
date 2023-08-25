package tech.candra.resource;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import tech.candra.templates.Template;
import tech.candra.models.Jurusan;
import tech.candra.models.Mahasiswa;

@Path("mahasiswa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MahasiswaResource {

    @Inject
    EntityManager em;

    @GET
    @Operation(summary = "All Mahasiswa", description = "Tampilkan semua data mahasiswa")
    public Template getAllMahasiswa() {
        List<Mahasiswa> mahasiswa = Mahasiswa.listAll();
        if (mahasiswa.isEmpty()) {
            return new Template(false, "Data mahasiwa tidak ditemukan", null);
        }
            return new Template(true, "Data mahasiwa berhasil ditemukan", mahasiswa);
    }
    
    @GET
    @Path("{idMahasiswa}")
    @Operation(summary = "Search Mahasiswa", description = "Silahkan masukan id tertentu")
    public Template getMahasiswaById(@PathParam("idMahasiswa") Long id){
        Mahasiswa mahasiswa = Mahasiswa.findById(id);
        if (mahasiswa == null) {
            return new Template(false, "Data mahasiwa tidak ditemukan", null);
        }
            return new Template(true, "Data mahasiwa berhasil ditemukan", mahasiswa);
    }

    @POST
    @Path("{idJurusan}")
    @Transactional
    @Operation(summary = "Add Mahasiswa Jurusan", description = "Silahkan masukan data sesuai format")
    public List <Mahasiswa> addJurusan(@PathParam("idJurusan") long idJurusan, @Valid Mahasiswa mahasiswa) {
        Jurusan jurusan = Jurusan.findById(idJurusan);
        jurusan.mahasiswaList.add(mahasiswa);
        mahasiswa.persist();
        return Mahasiswa.find("jurusan_id = ?1", idJurusan).list();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update Mahasiswa", description = "Silahkan masukan data sesuai format")
    public Template updateMahasiswa(@PathParam("id") Long id,@Valid Mahasiswa newMahasiswa) {
        Mahasiswa oldMahasiswa = Mahasiswa.findById(id);
        if(oldMahasiswa == null){
            return new Template(false, "Data mahasiwa tidak ditemukan", null);
        }

        oldMahasiswa.nama = newMahasiswa.nama;
        oldMahasiswa.jenisKelamin = newMahasiswa.jenisKelamin;
        oldMahasiswa.noTlp = newMahasiswa.noTlp;
        oldMahasiswa.alamat = newMahasiswa.alamat;

        return new Template(true, "Berhasil mengubah data mahasiswa", oldMahasiswa);     
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete Mahasiswa", description = "Silahkan masukan data sesuai format")
    public Template deleteMahasiswa(@PathParam("id") Long id) {
        em.createNativeQuery("DELETE FROM mahasiswa_kuliah WHERE mahasiswa_id = :id").setParameter("id", id).executeUpdate();
        Boolean delete = Mahasiswa.deleteById(id);
        List<Mahasiswa> mahasiswa = Mahasiswa.findById(id);
        if (delete) {
            return new Template(true, "Data mahasiwa berhasil dihapus", null);
        }
            return new Template(false, "Data mahasiwa tidak ditemukan", mahasiswa);
    }  
}