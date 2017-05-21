/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Entities;

import Domain.Commom.enumCondicao;
import Domain.Commom.enumTipoProblema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Leandro
 */
public class Simplex {
    private Problema problema;
    private double mCima[][], mBaixo[][];   
    Map vb, vnb;
    TreeMap<String, Double> resultado;
    
    
    
    public Simplex(){} 
    
    
    
    public void executarSimplex(Problema p)
    {
        boolean proxima = false;
        int conclui;

        fazerTransformacoes(p);
        iniciaMatriz();
        // fase 1
        proxima = primeiraFase();
        if (proxima)
        {
            // fase 2
            conclui = 0;
            conclui = segundaFase();
            if (conclui == 1)
            {
                // multiplas soluções
                System.out.println("Multiplas Soluções. Solução possível:");
                imprimeResultado();

            }
            else if (conclui == 2)
            {   
                // solucao ótima encontrada
                System.out.println("Solução Ótima.");
                imprimeResultado();
            }
            else
            {
                // não existe ponto ótimo 
                // solução ilimitada
                System.out.println("Solução Ilimitada. Solução possível:");
                imprimeResultado();
            }
        }
        else
        {
            // solução não existe
            System.out.println("Solução Impossível");
        }
        

    }

    public void fazerTransformacoes(Problema p)
    {
        p.funcaoObj.expressao.add(0, new Variavel(0));
        if (p.getTipo() == enumTipoProblema.Minimizacao)
            for (Variavel v : p.funcaoObj.expressao)
                if (v.valor != 0)
                    v.valor *= -1;
      
        for (Polinomio e : p.restricoes)
        {
            e.expressao.add(0, new Variavel(e.valor));
            if (e.condicao == enumCondicao.MaiorIgual)
                for (Variavel v : e.expressao)
                    if (v.valor != 0)
                        v.valor *= -1;
        }

        setProblema(p);
    }  
    
    public void iniciaMatriz()
    {
        int nLinhas = problema.restricoes.size() + 1;
        int nColunas = problema.funcaoObj.expressao.size();
        mCima = new double[nLinhas][nColunas];
        mBaixo = new double[nLinhas][nColunas];
        vb = new HashMap(); 
        vnb = new HashMap(); 
        
        try{
        for (int i = 0; i < mCima.length; i++)
        {
            if (i==0)
                vb.put(i, "Z");
            else
                vb.put(i, "X" + (i + mCima[0].length - 1));
                
            for (int j = 0; j < mCima[0].length; j++)
                if (i == 0)
                {
                    mCima[i][j] = problema.funcaoObj.expressao.get(j).valor;
                    if (j>0)
                        vnb.put(j, "X" + j);
                }
                else
                    mCima[i][j] = problema.restricoes.get(i-1).expressao.get(j).valor;
        }
        }
        catch(Exception e){
            System.out.printf(e.getMessage());}
    }
    
    public boolean primeiraFase()
    {
        int lin = 0, col = 0;
        boolean etapa1 = false, etapa2 = false, conclui = false;
        
        for (int i = 1; i < mCima.length; i++)
            if (mCima[i][0] < 0)
            {
                etapa1 = true;
                for (int j = 1; j < mCima[0].length; j++)
                    if (mCima[i][j] < 0)
                    {
                        etapa2 = true;
                        col = j;
                        double menor = 999999;
                        for (int k = 1; k < mCima.length; k++)
                            if ((mCima[k][0] < 0 && mCima[k][j] < 0) || (mCima[k][0] >= 0 && mCima[k][j] > 0))
                                if (menor > mCima[k][0] / mCima[k][j])
                                {
                                    lin = k;
                                    menor = mCima[k][0] / mCima[k][j];
                                }
                        break;
                    }
                break;
            }
        
        if (!etapa1)
            return true;
        else if (!etapa2)
            return false;
        else
        {
            algoritmoDaTroca(lin, col);
            conclui = primeiraFase();
        }
        
        return conclui;
    }
    
    public int segundaFase()
    {
        int lin = 0, col = 0, conclui = 0;
        boolean etapa1 = false, etapa2 = false, possivelMultiplas = false;
        
        for (int j = 1; j < mCima[0].length; j++)
            if (mCima[0][j] > 0)
            {
                etapa1 = true;
                col = j;
                for (int i = 1; i < mCima.length; i++)
                    if (mCima[i][j] >= 0)
                    { 
                        etapa2 = true;
                        double menor = 999999;
                        for (int k = i; k < mCima.length; k++)
                            if (mCima[k][0] >= 0 && mCima[k][j] > 0)
                                if (menor > mCima[k][0] / mCima[k][j])
                                {
                                    lin = k;
                                    menor = mCima[k][0] / mCima[k][j];
                                }
                        break;
                    }
                break;
            }
            else if (mCima[0][j] == 0)
               possivelMultiplas = true;
        
        if (!etapa1 && possivelMultiplas)
            return 1;
        else if (!etapa1) 
            return 2;
        else if (!etapa2)
            return 3;
        else
        {
            algoritmoDaTroca(lin, col);
            conclui = segundaFase();
        }
        
        return conclui;
    }
    
    public void algoritmoDaTroca(int l, int c)
    {
        double iep = 0;
        
        iep = 1/mCima[l][c];
        mBaixo[l][c] = iep;
        
        for (int j = 0; j < mBaixo[0].length; j++)
            if (j != c)
                mBaixo[l][j] = mCima[l][j] * iep;
        
        for (int i = 0; i < mBaixo.length; i++)
            if (i != l)
                mBaixo[i][c] = mCima[i][c] * (-iep);
        
        for (int j = 0; j < mBaixo[0].length; j++)
            if (j != c)
                for (int i = 0; i < mBaixo.length; i++)
                    if (i != l)
                        mBaixo[i][j] = mCima[l][j] * mBaixo[i][c];
        
        double mAux[][] = new double[mCima.length][mCima[0].length];        
        
        Object varAux;
        varAux = vb.get(l);
        vb.replace(l, vnb.get(c));
        vnb.replace(c, varAux);
             
        for (int i = 0; i < mAux.length; i++)
            for (int j = 0; j < mAux[0].length; j++)
            {
                if (i == l || j == c)
                    mAux[i][j] = mBaixo[i][j];
                else
                    mAux[i][j] = mCima[i][j] + mBaixo[i][j];
                
                mCima[i][j] = 0;
                mBaixo[i][j] = 0;
            }   
        
        mCima = mAux;
    }
    
    public List<String> imprimeResultado()
    {
       List<String> resultadoList = new ArrayList<String>();
        resultado = new TreeMap<>();
        
        for (int i = 0; i < vb.size(); i++)
            if (problema.getTipo() == enumTipoProblema.Maximizacao && i == 0)
                resultado.put(String.valueOf(vb.get(i)), -mCima[i][0]);
            else    
                resultado.put(String.valueOf(vb.get(i)), mCima[i][0]);
        
        for (int j = 0; j < vnb.size(); j++){
            resultado.put(String.valueOf(vnb.get(j+1)), 0.0); 
        }
        
        
         Double valueF = resultado.lastEntry().getValue();
         String keyF = resultado.lastEntry().getKey();
            System.out.printf("%s = %.2f\n", keyF, valueF);
            resultadoList.add(String.format("%s = %.2f\n", keyF, valueF));
            
        for (String key : resultado.keySet())
        {
            if(key!=resultado.lastKey()){
            Double value = resultado.get(key);
            resultadoList.add(String.format("%s = %.2f\n", key, value));
            
            }
        }
        return resultadoList;
        }
    
    
    public void setProblema(Problema p) {
        this.problema = p;
    }
    
    public Problema getTipo() {
        return problema;
    }
}
