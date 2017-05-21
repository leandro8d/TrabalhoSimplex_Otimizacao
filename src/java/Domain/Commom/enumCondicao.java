/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Commom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Leandro
 */
public enum enumCondicao {
        
        Igual(1, "="),
        MenorIgual(2, "<="),
        MaiorIgual(3, ">=");
        
        private int cod;
        private String simbolo;
        private static final Map<String,enumCondicao> ENUM_MAP;
        
        enumCondicao(int c, String s){
            setCod(c);
            setSimbolo(s);
        }
        
        public static List<String> getAllValuesString()
        {
            List<String> list = new ArrayList<String>();
          for(enumCondicao i:enumCondicao.values()){
              list.add(i.getSimbolo());
          }
          return list;
        }
        
        public void setCod(int c){
            this.cod = c;
        }
        
        public void setSimbolo(String s){
            this.simbolo = s;
        }

        public double getCod(){
            return this.cod;
        }
        
        public String getSimbolo(){
            return this.simbolo;
        }
        
        
         static {
    Map<String,enumCondicao> map = new ConcurrentHashMap<String,enumCondicao>();
    for (enumCondicao instance : enumCondicao.values()) {
      map.put(instance.getSimbolo(),instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static enumCondicao get (String name) {
    return ENUM_MAP.get(name);
  }
  

  
        
    }

