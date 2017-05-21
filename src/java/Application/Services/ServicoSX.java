/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Services;

import Domain.Commom.Util;
import Domain.Commom.enumCondicao;
import Domain.Commom.enumTipoProblema;
import Domain.DTO.RetornoJsonDTO;
import Domain.DTO.TipoProblemaDTO;
import Domain.Entities.Problema;
import Domain.Entities.Simplex;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Leandro
 */
@Path("/servicoSX")
public class ServicoSX {

    @GET
    @Path("/getCondicoes")
    @Produces({MediaType.APPLICATION_JSON})
    public JSONArray getCondicoes() {
        return new JSONArray(enumCondicao.getAllValuesString());
    }

    @GET
    @Path("/getTiposProblema")
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoProblemaDTO> getTiposProblema() {
        return enumTipoProblema.getDtoList();
    }

    @POST
    @Path("/calcularValores")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.APPLICATION_JSON)
    public RetornoJsonDTO calcularValores(String lista, String problema) {
        try {
            Util u = new Util();
            List<List<String>> listal = new ArrayList<List<String>>();
            JSONObject request = new JSONObject(lista);
            JSONArray arr = request.getJSONArray("vetor");
            Gson gson = new Gson();
            TipoProblemaDTO tp = gson.fromJson(request.get("problema").toString(), TipoProblemaDTO.class);
            for (int i = 0; i < arr.length(); i++) {
                listal.add(u.JsonArrayStringToList(arr.getJSONArray(i)));
            }
            Simplex s = new Simplex();

            Problema p = new Problema(listal, tp.getId());
            s.executarSimplex(p);
            List result = s.imprimeResultado();

            RetornoJsonDTO retornojson = new RetornoJsonDTO();
            retornojson.setError(false);
            retornojson.setData(result);

            return retornojson;
        } catch (Exception e) {
            RetornoJsonDTO retornojson = new RetornoJsonDTO();
            retornojson.setError(true);
            retornojson.setErrorMessage(e.getMessage());
            return retornojson;
        }
    }

    //@GET
    //@Path("/getListaUsuariosEventos")
    //@Produces(MediaType.APPLICATION_JSON)
    //public List<UsuarioDTO> getListaUsuariosEventos() {
    //  IUsuarioRepository repository = new UsuarioRepository();
    // List<Usuario> usuarios = repository.getUsuariosVinculadosEventoList();
    //List<UsuarioDTO> listDTO = usuarios.stream().map(i -> new UsuarioDTO(i.getNome())).collect(Collectors.toList());
    //return listDTO;
    // }
}
