package tech.candra.resource;

import java.util.List;
import javax.ws.rs.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import io.quarkus.panache.common.Sort;
import tech.candra.models.Dosen;
import tech.candra.models.Mahasiswa;
import tech.candra.models.MataKuliah;
import tech.candra.templates.Template;

@Path("mataKuliah")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MataKuliahResource {
    @Inject
    EntityManager em;

    @GET
    @Operation(summary = "All Matakuliah", description = "Tampilan semua Matakuliah")
    public Template getMataKuliah() {
        List<MataKuliah> mataKuliah = MataKuliah.listAll();
        if(mataKuliah.isEmpty()) {
          return new Template(false, "Data Matakuliah tidak ditemukan",mataKuliah);
        }
          return new Template(true, "Data Matakuliah berhasil ditemukan",mataKuliah);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Search Matakuliah", description = "")
    public Template getMataKuliahyId(Long id) {
        MataKuliah mataKuliah = MataKuliah.findById(id);
        if(mataKuliah == null) {
            return new Template (false, "ID Matakuliah Tidak Ditemukan", mataKuliah);
        }
            return new Template (true, "ID Matakuliah Ditemukan", mataKuliah);
    }

    @POST
    @Transactional
    @Operation(summary = "Add Matakuliah", description = "Silahkan masukan data sesuai format")
    public Template addMatakuliah(@Valid MataKuliah mataKuliah) {
        mataKuliah.persist();
        return new Template(true, "Data berhasil ditambahkan", mataKuliah);
    }

    @POST
    @Path("{idDosen}")
    @Transactional
    public List<MataKuliah> mataKuliah(@PathParam("idDosen") Long id, MataKuliah mataKuliah) {
        Dosen dosen = Dosen.findById(id);
        dosen.mataKuliahList.add(mataKuliah);
        mataKuliah.persist();
        return MataKuliah.find("dosen_id = ?1", id).list();


    }


    @POST
    @Transactional
    @Path("{idMataKuliah}/mahasiswaList/{idMahasiswa}")
    public List <MataKuliah> addMataKuliah (@PathParam("idMataKuliah") Long idMataKuliah, @PathParam("idMahasiswa") long idMahasiswa) {
        MataKuliah mataKuliah = MataKuliah.findById(idMataKuliah);
        Mahasiswa mahasiswa = Mahasiswa.findById(idMahasiswa);
        mataKuliah.mahasiswaList.add(mahasiswa);
        mataKuliah.persist();
        return MataKuliah.listAll(Sort.ascending("id"));
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update Matakuliah", description = "Silahkan masukan data sesuai format")
    public Template updateMataKuliah(@PathParam("id") Long id, @Valid MataKuliah newMataKuliah) {
        MataKuliah oldMataKuliah = MataKuliah.findById(id);
        if (oldMataKuliah == null) {
            return new Template(false, "Data Matakuliah tidak ditemukan", null);
        }
        oldMataKuliah.namaMataKuliah = newMataKuliah.namaMataKuliah;
        oldMataKuliah.sks = newMataKuliah.sks;
        return new Template(true, "Data Berhasil diubah", oldMataKuliah);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete Matakuliah", description = "Silahkan masukan data sesuai format")
    public Template deleteMataKuliah(@PathParam("id") Long id) {
        em.createNativeQuery("DELETE FROM mahasiswa_kuliah WHERE kuliah_id = :id").setParameter("id", id).executeUpdate();
        Boolean delete  = MataKuliah.deleteById(id);
        List<MataKuliah> mataKuliah  = MataKuliah.findById(id);
        if (delete) {
            return new Template(true, "Data matakuliah berhasil dihapus", null);
        }
            return new Template(false, "Data matakuliah Tidak ditemukan", mataKuliah);
        }
}
