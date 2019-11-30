package calculadora;

class Pilha{  
    int max = 10, topo = 0;  
         
    String[] elem = new String[max];  
      
    boolean cheia(){  
        return( topo == max );  
    }  
      
    boolean vazia(){  
        return ( topo == 0 );  
    }  
      
    //empilha um valor como uma string  
    void empilha(String x){  
        elem[topo] = x;  
        topo = topo + 1;  
    }  
      
    //desempilha a quantidade de elmentos solicitados  
    String desempilha(int x){
   	 String retorno = elem[topo]; 
        topo = topo - x;
        return retorno;
    }  
      
    void mostrapilha(){  
      if(this.vazia()){
   	   System.out.println("Pilha vazia..");  
      }else{  
        for(int i = topo-1; i >= 0; i--){  
            System.out.println(elem[i]);  
        }  
      }  
    }  
}  
/** 
* 
* @author jubei 
*/  
public class Main {  
  
    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
          
        Pilha novaPilha = new Pilha();  
        String expressao = new String();  
        expressao = "(2+2)/2";  
          
        for(int i = expressao.length() - 1; i >= 0; i--){  
            if ((expressao.charAt(i) != ')') && (expressao.charAt(i) != '(')){  
                novaPilha.empilha(String.valueOf(expressao.charAt(i)));  
            }  
        }
        novaPilha.mostrapilha();
          
    }  
} 