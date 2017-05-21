/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Entities;

import Domain.Commom.DomainBase;
import Domain.Commom.enumCondicao;
import Domain.Commom.enumTipoProblema;
import Domain.Repository.Hibernate.Repositories.ProblemaRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class Problema extends DomainBase<ProblemaRepository> {

    public Problema() {
    }
    public Problema(List<List<String>> funcoes, int idTipoProblema) throws Exception{ 
        this.montarProblema(funcoes,idTipoProblema);
    }

    Polinomio funcaoObj;
    List<Polinomio> restricoes;
    private enumTipoProblema tipo;

    
    public void montarProblema(List<List<String>> funcoes, int idTipoProblema) throws Exception {
try{
        Polinomio fo = new Polinomio();
        fo.expressao = new ArrayList<>();

        this.tipo = enumTipoProblema.fromId(idTipoProblema);
        for (String v : funcoes.get(0)) {
            if (funcoes.get(0).indexOf(v) == funcoes.get(0).indexOf(funcoes.get(0).get(funcoes.get(0).size() - 2))) {
                fo.condicao = enumCondicao.get(v);
            } else if (funcoes.get(0).indexOf(v) == funcoes.get(0).indexOf(funcoes.get(0).get(funcoes.get(0).size() - 1))) {

                fo.valor = Float.parseFloat(v);
            } else {
                fo.expressao.add(new Variavel(Float.parseFloat(v)));
            }
            fo.valor = 0;
        }
        funcoes.remove(0);

        this.funcaoObj = fo;
        this.restricoes = new ArrayList<>();

        Polinomio r;

        for (List<String> l : funcoes) {
            r = new Polinomio();
            r.expressao = new ArrayList<>();

            for (String v : l) {

                if (l.indexOf(v) == l.indexOf(l.get(l.size() - 2))) {
                    r.condicao = enumCondicao.get(v);
                } else if (l.indexOf(v) == l.indexOf(l.get(l.size() - 1))) {
                    r.valor = Float.parseFloat(v);
                } else {
                    r.expressao.add(new Variavel(Float.parseFloat(v)));
                }

            }
            this.restricoes.add(r);
        }
       
        
}
catch(NumberFormatException e){ throw new Exception("Erro, sao aceitos somente numeros no campo função!",e);}
catch(Exception e){ throw e;}

    }

    /**
     * @return the tipo
     */
    public enumTipoProblema getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(enumTipoProblema tipo) {
        this.tipo = tipo;
    }

}
