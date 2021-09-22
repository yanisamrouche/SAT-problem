import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        
        try{

            /** Lecture du fichier (parser les données) */
            InputStream flux=new FileInputStream("2-SAT/formule-2-sat.txt");
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);

            String s;
            String[] mots = null;
            List<String > list = new ArrayList<>();
            /** récupération du contenu du fichier avec élimination des espaces entre les mots
             * puis les stocker dans une list de string */
            while ((s=buff.readLine())!=null){
                mots=s.split(" ");
                for(String wrd : mots){
                    list.add(wrd);
                }
            }



            /** récupération de l'indice du string "cnf" */
            int IndexCnf=0;
            for (int i=0; i < list.size(); i++){
                if (list.get(i).equals("cnf")){
                    IndexCnf = i;
                }
            }



            /** une list qui contient l'ensemble des clauses  */
            List listOfClauses = new ArrayList<>();

            /** récupération de tout les clauses
             * dans une list */
            for(int i=IndexCnf+3 ; i < list.size(); i++){
                listOfClauses.add(list.get(i));
            }



            String v1="";
            String v2="";
            String v3="";
            String v4="";
            Graph g = new Graph();
            Graph gt = new Graph();
            for(int i=0 ; i<listOfClauses.size() ; i++){
                if(listOfClauses.get(i).equals("0")){
                    v1 =(String)listOfClauses.get(i-2);
                    v2 =(String)listOfClauses.get(i-1);
                    v3 = OppositeValue(v1);
                    v4 = OppositeValue(v2);
                    g.addVertex(v1);
                    g.addVertex(v2);
                    g.addVertex(v3);
                    g.addVertex(v4);
                    /** Graphe transposé */
                    gt.addVertex(v1);
                    gt.addVertex(v2);
                    gt.addVertex(v3);
                    gt.addVertex(v4);
                }
            }
            for(int i=0 ; i<listOfClauses.size() ; i++){
                if(listOfClauses.get(i).equals("0")){
                    v1 =(String)listOfClauses.get(i-2);
                    v2 =(String)listOfClauses.get(i-1);
                    //application-de-la-formule
                    v3 = OppositeValue(v1);
                    v4 = OppositeValue(v2);
                    g.addEdge(v3,v2);
                    g.addEdge(v4,v1);
                    /** Graphe transposé */
                    gt.addEdgeInv(v3,v2);
                    gt.addEdgeInv(v4,v1);
                }
            }
            System.out.println("Graphe d'implication : ");
            g.display();
            System.out.println("Graphe transposé : ");
            gt.display();
            System.out.println("Les composantes fortement connexes du graphe : ");
            Graph.stronglyConnectedComponents(g,gt,"-2");

            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public static String OppositeValue(String s1){
        if(s1.length()>1 && s1.charAt(0)=='-') {
            s1 =s1.substring(1);
        }
        else {
            s1 ="-"+s1;
        }
        return s1;
    }
}
