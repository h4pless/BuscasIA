package ArvoreZLtrab;

import buscas.estruturas.*;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class ArvoreZLtrab {
    
    
    
    public static void printaResposta(String resposta, List<Noh> lista){
        
        for(int i = 0; i < lista.size(); i++)
            System.out.println(resposta + lista.get(i));    
        
    }
    
    public static void printaLista(List<Noh> lista){
    
        System.out.println("________________________________________\n");
        for(int i = 0; i < lista.size();i++)
            System.out.println("indice ["+i+"] \t valor \t" + lista.get(i).getValor() +"\t"+ "Custo \t" + lista.get(i).getCusto());
            System.out.println("________________________________________\n");
    }
    
    public static int getNivel(Noh noh){
        int i = 0;
        boolean x = true;
        
        while(x){
            if(noh.getPai() != null){
                i++;
                noh = noh.getPai();
            }else{
                x = false;
            }
        }
        return i;
    }
    
    public static boolean maisEsquerda(Noh noh1, Noh noh2){
        
        boolean x = true;
        
        Noh atual;
        
        while(x){
            if(noh1.getPai() == noh2.getPai()){
                atual = noh1.getPai();
                if(atual.getFilhos().get(0) == noh1)
                    return true;
                else 
                    return false;
            }else{
                noh1 = noh1.getPai();
                noh2 = noh2.getPai();
            }
        }
        return false;
    }
    
    public static boolean bubbleSort(List<Noh> lista){ //falso caso lista.size() == 0 ( lista vazia ) retorna false, caso isso nunca aconteça, retorna true
    
        Noh aux;
        
        if(lista.size() == 0){
                System.out.println("here");
                return false;
        }else{
            for(int j = 0 ; j < lista.size(); j++){
                for(int i = 1; i < lista.size(); i++){
            	
                    if(lista.get(i-1).getCusto() > lista.get(i).getCusto()){
                        aux = lista.get(i-1);
                        lista.set(i-1, lista.get(i));
                        lista.set(i, aux);            
                    }
                    if(lista.get(i-1).getCusto() == lista.get(i).getCusto()){
                        if(getNivel(lista.get(i-1)) > getNivel(lista.get(i))){
                            aux = lista.get(i-1);
                            lista.set(i-1, lista.get(i));
                            lista.set(i, aux);
                        }
                        if(getNivel(lista.get(i-1)) == getNivel(lista.get(i))){
                            if(!maisEsquerda(lista.get(i-1), lista.get(i))){
                                aux = lista.get(i-1);
                                lista.set(i-1, lista.get(i));
                                lista.set(i, aux);                       
                            }                        
                        }                       
                    }                  
                }
            
            }
            return true;        
        }
    }         

    public static void buscaProfundidade(Arvore arvore, int valor, List<Noh> acessos, List<Noh> visitas ){
        
        //lista -> pilha
    
        //List<Noh> lista = new ArrayList();    
        Noh atual = arvore.getRaiz();    
        boolean x = true;
        
    
        while(x){                                                           // caminha a arvore pela esquerda até achar folha
            if(atual.getFilhos() == null || atual.getFilhos().size() == 0){ // se Noh atual for folha
                visitas.add(atual);                                         // adiciona na lista de visitas, folha -> visita
                acessos.add(atual);                                         // se for folha tem que "acessar" dentro do if
                if(atual.getValor() == valor){                              // já que é folha, visita
                    System.out.println("achoux\t" + atual.getValor());       // printa
                    x = false;                                              // sai do loopin
                    //return pilha;
                }else{                                                      // se a visita não achar o resultado
                    atual = atual.getPai();  /*                               // atual volta apontar pro pai
                    if(atual == arvore.getRaiz() && atual.getValor() == arvore.getRaiz().getValor()){       //se tirar comentario aqui, vira arvore esquerda-raiz-direita
                        System.out.print("achou\t"+atual.getValor()+"\n\n");          
                        visitas.add(atual);
                        acessos.add(atual);
                        x = false;
                    }*/
                    atual.getFilhos().remove(0);                            // remove o filho 0(mais a esquerda), antigo indice 1 começa a fazer o "papel" e mais a esquerda
                }
            }else{                                                          // caso não seja folha
                acessos.add(atual);                                         // mesmo não sendo folha, passar por esse Noh ainda é um acesso
                atual = atual.getFilhos().get(0);                           // atual passa a ser o filho "mais a esquerda" (antigo indice 1)
            }   
            
        }   

    }
    
    public static int buscaUniforme(Arvore arvore, int valor, List<Noh> acessos, List<Noh> visitas){
    
        //como saber que não existe o elemento na busca//arvore???
        //solucionado.

        List<Noh> lista = new ArrayList();
        int i;  
        boolean condicao = true;
       
        
        lista.add(arvore.getRaiz()); // coloco na lista auxiliar o Noh Raiz
        printaLista(lista);
        



        while(condicao){
            
            visitas.add(lista.get(0));          //gerou visita
            acessos.add(lista.get(0));          //gerou acesso
            if(lista.get(0).getValor() == valor){   //verifico se o primeiro Noh na lista( raiz, custo 0) é o valor
                System.out.println("\t achou\n\n");  
                condicao = false;
            }else{                                  //se o primeiro nó da lista não for o valor
                if(lista.get(0).getFilhos() != null){       //se o primeiro nó da lista for folha ( não tiver .get().getFilhos() == null
                    for(i = 0; i < lista.get(0).getFilhos().size(); i++){       //percorro o tamanho de .getFilhos()
                        lista.add(lista.get(0).getFilhos().get(i));             // pega os filhos de 0 e poe na lista 
                        lista.get(lista.size()-1).setCusto(lista.get(0).getCusto() + lista.get(lista.size()-1).getCusto());    // seta o custo de cada filho com o custo pai + filho                                       
                    }
                }
                
                lista.remove(0);            //remove o pai já que ja foi verificado
                
                printaLista(lista);      
                condicao = bubbleSort(lista);          //ordena a lista
                System.out.println("yy" + condicao);
                if(condicao == false){
                System.out.println("Elemento não encontrado!x");
                return 0;
                }
            }
            
        }
        
        return 1;
    }          
    
        
    
    
   
//compareTo  ( <  valorpositivo )   ( > valornegativo )  ( =  0 )
    public static void main(String[] args) {
        
        List<Noh> acessos = new ArrayList();
        List<Noh> visitas = new ArrayList();
        List<Noh> ListaFilhosDeRaiz = new ArrayList();
        List<Noh> ListaFilhosDeB = new ArrayList();
        List<Noh> ListaFilhosDeC = new ArrayList();
        
        Noh Raiz = new Noh(-1, null , 0);         //nivel 1
        Raiz.setPai(null);
        
        Noh B = new Noh(-2, null, 4);             //nivel 2
        B.setPai(Raiz);
        Noh C = new Noh(-3, null, 6);
        C.setPai(Raiz);
        
        Noh D = new Noh(-4, null, 8);             //nivel 3
        D.setPai(B);  
        Noh E = new Noh(-5, null, 9);
        E.setPai(B);
        
        Noh F = new Noh(-6, null, 4);             //nivel 3
        F.setPai(C);       
        Noh G = new Noh(-7, null, 5);
        G.setPai(C);    
        
        ListaFilhosDeRaiz.add(B);
        ListaFilhosDeRaiz.add(C);
        
        ListaFilhosDeB.add(D);
        ListaFilhosDeB.add(E);
        
        ListaFilhosDeC.add(F);
        ListaFilhosDeC.add(G);
        
        Raiz.setFilhos(ListaFilhosDeRaiz);
        B.setFilhos(ListaFilhosDeB);
        C.setFilhos(ListaFilhosDeC);
        D.setFilhos(null);
        E.setFilhos(null);
        F.setFilhos(null);
        G.setFilhos(null);
 
        Arvore NovaArvore = new Arvore();
  
        NovaArvore.setRaiz(Raiz);
        
        
        
        
        buscaUniforme(NovaArvore, -4, acessos, visitas);
        System.out.println();
        printaResposta("acessos ", acessos);
        System.out.println();
        System.out.println();
        printaResposta("visitas ", visitas);
        
              
    }
    
}
