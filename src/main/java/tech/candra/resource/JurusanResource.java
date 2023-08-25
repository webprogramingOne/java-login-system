package tech.candra.resource;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import tech.candra.models.Jurusan;
import tech.candra.models.Mahasiswa;
import tech.candra.templates.Template;

@Path("jurusan")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JurusanResource {

    @GET
    @Operation(summary = "All Jurusan", description = "Tampilkan semua jurusan")
    public Template getAll() {
        List<Jurusan> jurusan = Jurusan.listAll();
        if (jurusan.isEmpty()) {
            return new Template(false, "Data jurusan tidak ditemukan", jurusan);
        }
            return new Template(true, "Data jurusan berhasil ditemukan", jurusan);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Search Jurusan", description = "jurusan by Id")
    public Template getJurusanById(@PathParam("id") Long id) {
         Jurusan jurusan = Jurusan.findById(id);
         if(jurusan == null) {
            return new Template (false, "ID Jurusan tidak ditemukan", jurusan);
         }
            return new Template(true, "ID Jurusan Ditemukan", jurusan);
    }

    @POST
    @Transactional
    @Operation(summary = "Add jurusan", description = "Silahkan masukan data sesuai format")
    public Template addJurusan(@Valid Jurusan jurusan) {
        jurusan.persist();
        for (Mahasiswa mahasiswa : jurusan.mahasiswaList) {
            mahasiswa.persist();
        }
        return new Template(true, "Data jurusan berhasil ditambahkan", jurusan);
    }

    @PUT
    @Transactional
    @Path("{id}")
    @Operation(summary = "Update Jurusan", description = "Silahkan masukan data sesuai format")
    public Template updateJurusan(@PathParam("id") Long id,@Valid Jurusan newJurusan) {
        Jurusan oldJurusan = Jurusan.findById(id);
        if(oldJurusan == null) {
            return new Template(false, "Data jurusan tidak ditemukan", oldJurusan);
        }
        oldJurusan.namaJurusan = newJurusan.namaJurusan;
        oldJurusan.jenjang =  newJurusan.jenjang;
        return new Template(true, "Berhasil mengubah data jurusan", oldJurusan);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete Jurusan", description = "Hapus data by Id")
    public Template deleteJurusanById(@PathParam("id") Long id) {
        Boolean delete  = Jurusan.deleteById(id);
        List<Jurusan> jurusan = Jurusan.findById(id);
        if (delete) {
            return new Template(true, "Data jurusan berhasil dihapus", jurusan);
        }
            return new Template(false, "Data jurusan tidak ditemukan", jurusan);
    }
}