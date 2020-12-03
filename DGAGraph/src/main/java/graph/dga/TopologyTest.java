package graph.dga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopologyTest {
    public static void main(String[] args) {
        DGANode n1 = new DGANode().setId("1").setName("Spike").setInheritNodesStr("6");
        DGANode n2 = new DGANode().setId("2").setName("Faye").setInheritNodesStr("1");
        DGANode n3 = new DGANode().setId("3").setName("Jet").setInheritNodesStr("2,4,5");
        DGANode n4 = new DGANode().setId("4").setName("Ed").setInheritNodesStr("5");
        DGANode n5 = new DGANode().setId("5").setName("Ein").setInheritNodesStr("6");
        DGANode n6 = new DGANode().setId("6").setName("Julia").setInheritNodesStr("");
        List<DGANode> nodes = new ArrayList<>(Arrays.asList(
                //节点1
               n1,n2,n3,n4,n5,n6
        ));
        try {
            List<DGANode> sortedDGANodes = DGAGraph.builder().build(nodes).topologySort();
        } catch (DGAGraph.CircleDetectedException|DGAGraph.GraphNotBuildException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
