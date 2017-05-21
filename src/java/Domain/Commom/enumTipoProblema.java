/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Commom;

import Domain.DTO.TipoProblemaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leandro
 */
public enum enumTipoProblema {

    Maximizacao(1, "Maximização"),
    Minimizacao(2, "Minimização");

    private int cod;
    private String descricao;

    enumTipoProblema(int c, String desc) {
        setCod(c);
        setDescricao(desc);
    }

    public void setCod(int c) {
        this.cod = c;
    }

    public int getCod() {
        return this.cod;
    }

    public static List<TipoProblemaDTO> getDtoList() {
        List<TipoProblemaDTO> lista = new ArrayList<TipoProblemaDTO>();
        for (enumTipoProblema instance : enumTipoProblema.values()) {

            lista.add(new TipoProblemaDTO(instance.getCod(), instance.getDescricao()));
        }
        return lista;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

      public static enumTipoProblema fromId(int id) {
                for (enumTipoProblema type : enumTipoProblema.values()) {
                    if (type.getCod()== id) {
                        return type;
                    }
                }
                return null;
            }
    
    
}
