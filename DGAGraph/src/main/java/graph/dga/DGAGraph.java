package graph.dga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 有向无环图的拓扑排序
 */
public class DGAGraph {
    /**
     * 用于保存图中节点
     */
    private List<DGANode> graphNodes;
    private List<DGANode> topologySortedNodes;

    private class GraphNodeLink {
        DGANode node;
        GraphNodeLink next;
    }

    /**
     * 对有向图进行拓扑排序
     *
     * @return 返回一种排序后的结果
     * @throws CircleDetectedException
     */
    public List<DGANode> topologySort() throws CircleDetectedException, GraphNotBuildException {
        //排序
        if (graphNodes == null) {
            throw new GraphNotBuildException();
        }
        topologySortedNodes = new ArrayList<>(graphNodes.size());
        GraphNodeLink head = createGraphNodeLink(graphNodes);
        for (GraphNodeLink pN = head.next, pPre = head; pN != null; pPre = pN, pN = pN.next) {
            DGANode current = pN.node;
            //找到入度为0的节点
            if (current.getInDegree() == 0) {
                //当前节点加入已排序集合
                topologySortedNodes.add(current);
                //所有后继节点入度-1
                current.getInheritNodes().forEach(DGANode::decreaseInDegree);
                //断开当前节点，重新开始遍历
                pPre.next = pN.next;
                pN = head;
                pPre = head;
            }
        }
        if (topologySortedNodes.size() != graphNodes.size())
            throw new CircleDetectedException();
        return topologySortedNodes;
    }

    /**
     * 构建出一条遍历链表
     * @param nodes
     * @return
     */
    private GraphNodeLink createGraphNodeLink(List<DGANode> nodes) {
        GraphNodeLink head = null;
        GraphNodeLink pN = null;
        for (DGANode n : graphNodes) {
            GraphNodeLink nodeLink = new GraphNodeLink();
            nodeLink.node = n;
            if (head == null) {
                head = new GraphNodeLink();
                head.next = nodeLink;
            } else {
                pN.next = nodeLink;
            }
            pN = nodeLink;
        }
        return head;
    }

    public static TopologyGraphBuilder builder() {
        return new TopologyGraphBuilder();
    }

    public static class TopologyGraphBuilder {
        /**
         * 根据后继节点的引用信息
         * 构建出有向图
         *
         * @param nodes
         * @return
         */
        public DGAGraph build(List<DGANode> nodes) {
            //构建完成的节点，构建过程计算入度&出度
            Map nodesMap = nodes.stream().collect(Collectors.toMap(DGANode::getId, d -> d));
            //构建前驱节点
            nodes.forEach(n -> {
                if (n.getInheritNodesStr() != null && !"".equals(n.getInheritNodesStr())) {
                    List inheritIds = Arrays.stream(n.getInheritNodesStr().split(",")).distinct().collect(Collectors.toList());
                    inheritIds.forEach(id -> n.addInherits((DGANode) nodesMap.get(id)));
                }
            });
            //构建后继节点
            nodes.forEach(n -> n.getInheritNodes().forEach(n2 -> n2.addPioneers(n)));
            DGAGraph dgaGraph = new DGAGraph();
            dgaGraph.graphNodes = new ArrayList<>(nodes);
            //计算出度和入度
            dgaGraph.graphNodes.forEach(DGANode::computeDegree);
            return dgaGraph;
        }
    }

    /**
     * 检测出换环异常
     */
    public class CircleDetectedException extends Throwable {
        @Override
        public String getMessage() {
            return "检测出环";
        }
    }

    /**
     * 未构建图异常
     */
    public class GraphNotBuildException extends Throwable {
        @Override
        public String getMessage() {
            return "未构建图";
        }
    }
}
