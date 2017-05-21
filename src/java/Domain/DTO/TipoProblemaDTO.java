/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.DTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro
 */@XmlRootElement(name = "TipoProblemaDTO")
public class TipoProblemaDTO {
    
    public TipoProblemaDTO(){}
    public TipoProblemaDTO(int id,String desc){
   
    this.id = id;
    this.descricao = desc;
    }
    @XmlElement(name="descricao")
    private String descricao;
    @XmlElement(name="id")
    private int id;

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

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
