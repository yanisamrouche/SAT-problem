import java.util.*;
import java.util.List;

public class Graph {

    /** structure de données de type Map avec les sommets comme clés , la liste des voisins comme valeur (liste d'adjcence) */
    public Map<Vertex, List<Vertex>> incidency;
    /** Constructeur du graphe , avec l'inisialisation de la liste d'adjacence */
    public Graph() {
        this.incidency = new HashMap<>(0);
    }



    /** Méthode d'ajout de sommets au graphe */
    void addVertex(String label)
    {
        //incidency.putIfAbsent(new Vertex(label), new ArrayList<>());
        incidency.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    /** Méthode d'ajout d'arcs orientés entre deux sommets*/
    void addEdge(String label1, String label2)
    {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        incidency.get(v1).add(v2);

    }
    /** Méthode d'ajout d'arcs orientés entre deux sommets (pour le graphe transposé)*/
    void addEdgeInv(String label1, String label2)
    {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        incidency.get(v2).add(v1);
    }


    /** Méthode pour récupérer les voisins d'un sommet donnée en paramétre */
    List<Vertex> getAdjVertices(String label)
    {
        return incidency.get(new Vertex(label));
    }


    List<String> visited = new ArrayList<>();
    /** Méthode de parcours en profondeur (pour tout sommets du graphe)*/
    public List<String> depthFirstTraversal(Graph graph, String root)
    {


        Stack<String> stack = new Stack<String>();
        stack.push(root);
        while (!stack.isEmpty())
        {

            String vertex = stack.pop();


            if (!visited.contains(vertex))
            {
                visited.add(vertex);
                List<Vertex> neighbours = graph.getAdjVertices(vertex);
                if(!neighbours.isEmpty()) {
                    for (Vertex v : graph.getAdjVertices(vertex)) {
                        stack.push(v.name);
                    }
                }
            }
        }
        for (Vertex v : incidency.keySet()){
            if(!visited.contains(v.name)){
                depthFirstTraversal(graph,v.name);
            }
        }
        return visited;
    }




    List<String> visitedS = new ArrayList<>();
    /** Méthode de parcours en profondeur (pour les sommets accessible seulement)*/
    public  List<String> depthFirstSearch(Graph graph, String root)
      {
        Stack<String> stack = new Stack<String>();
        stack.push(root);
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
                if (!visitedS.contains(vertex) ) {
                    visitedS.add(vertex);
                    for (Vertex v : graph.getAdjVertices(vertex)) {
                        stack.push(v.name);
                    }
                }
        }
        return visitedS;
    }



       Set<String> lastVisited = new LinkedHashSet<>();
       /** Méthode de parcours en profondeur avec mémorisation des dates de fin de chaque sommets*/
       Set<String> DFSExplore(Graph g,String startVertex){
           Set<String> visitedSummits = new LinkedHashSet();
           Stack<String> s = new Stack<>();
           s.push(startVertex);
           while (!s.isEmpty()){
               String n = s.pop();
               s.push(n);
               visitedSummits.add(n);
               Boolean isDone=true;//pour savoir si on a visité tout les voisins
               for (Vertex v : g.getAdjVertices(n)) {
                   if (!visitedSummits.contains(v.name)) {
                       s.push(v.name);
                       visitedSummits.add(v.name);
                       isDone = false;
                       break;
                   } else {
                       continue;
                   }
               }
               //tout les voisins sont visités
               if(isDone==true){
                   String OutN=s.pop();
                   lastVisited.add(OutN);
               }
       }
       for(Vertex ve : g.incidency.keySet()){
           if(!lastVisited.contains(ve.name)){
               DFSExplore(g,ve.name);
           }
       }
       return lastVisited;
    }

    /** Méthode d'application de la premiere étape de l'algorithme de Kusarajo */
    static List<String> firstStepOfKosarajuAlgorithm(Graph graph, String start){
        Set<String> list = new LinkedHashSet<>();
        List<String> list1 = new ArrayList<>();
        List<String> reversed = new ArrayList<>();
        list = graph.DFSExplore(graph, start);
        for(String s : list){
            list1.add(s);
        }
        for(int i=list1.size()-1; i>=0;i--){
            reversed.add(list1.get(i));
        }
        return reversed;
     }

    /** Méthode d'application de la deuxieme étape de l'algorithme de Kusarajo (la derniere étape) */
    static void stronglyConnectedComponents(Graph graph, Graph grapht, String start){
       List<String> list = new ArrayList<>();
       list = firstStepOfKosarajuAlgorithm(graph,start);//list a suivre pour appliquer le DFS

       int cfcCounter=0;
       int literal;
       Boolean satisfaisable=true;
       List<String> list1 = new ArrayList<>();
       for (int i=0; i < list.size(); i++){
           if(list1.isEmpty()){
               list1 = grapht.depthFirstSearch(grapht, list.get(0));
               System.out.println(list1);
               if (list1.size() ==1){
                   satisfaisable=true;
               }
               else {

                   for (int j = 0; j < list1.size(); j++) {
                       literal = Integer.parseInt(list1.get(j));
                       for (int k = 1; k < list1.size(); k++) {
                           if (literal == (-1) * Integer.parseInt(list1.get(k))) {
                               satisfaisable=false;
                           }
                       }
                   }
               }
               cfcCounter++;
           }
           else if (!list1.contains(list.get(i))) {
               List<String> l = new ArrayList<>();
               l = grapht.depthFirstSearch(grapht, list.get(i));
               List<String> result = new ArrayList<>();
               for(int j=i;j <l.size() ;j++ ){
                   result.add(l.get(j));
               }
               System.out.println(result);

               if (result.size() ==1){
                   satisfaisable=true;
               }
               else {
                   for (int j = 0; j < result.size(); j++) {
                       literal = Integer.parseInt(result.get(j));
                           for (int k = 1; k < result.size(); k++) {
                               if (literal == (-1) * Integer.parseInt(result.get(k))) {
                                   satisfaisable = false;
                               } else {
                                   satisfaisable = true;
                               }
                           }
                       }

                   }
                   cfcCounter++;
           }
       }
        if (satisfaisable == true) {
            System.out.println("satisfaisable , aucune composante ne contient un littéral et son opposé");
        } else {
            System.out.println("insatisfaisable , car il existe une composante qui contient un littéral et son opposé");
        }
       System.out.println("le nombre de composantes fortement connexes est : "+cfcCounter);
     }

    /** Méthode d'affichage de notre liste d'adjacence (graphe d'implication) */
    public void display(){
        Set keys = incidency.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()){
            Object key = it.next();
            Object value = incidency.get(key);
            System.out.println(key.toString()+" -> "+value.toString());
        }
    }
}
