package graph.dga;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DGA图节点
 */
@Data
@Accessors(chain = true)
public class DGANode {
    private String id;
    private String name;
    //后继节点字符串
    private String inheritNodesStr;
    /**
     * 节点入度
     */
    private Integer inDegree;
    /**
     * 节点出度
     */
    private Integer outDegree;
    /**
     * 后继节点
     * desc:当前节点指向的节点
     */
    private List<DGANode> inheritNodes;

    /**
     * 前驱节点
     * desc:指向当前节点的节点
     */
    private List<DGANode> pioneerNodes;
    /**
     * 描述
     */
    private String describe;

    public DGANode() {
        this.inheritNodes = new ArrayList<>();
        this.pioneerNodes = new ArrayList<>();
    }

    /**
     * 出度+1
     *
     * @return
     */
    public Integer increaseOutDegree() {
        outDegree++;
        return outDegree;
    }

    /**
     * 出度-1
     *
     * @return
     */
    public Integer decreaseOutDegree() {
        outDegree--;
        return outDegree;
    }

    /**
     * 入度+1
     *
     * @return
     */
    public Integer increaseInDegree() {
        inDegree--;
        return inDegree;
    }

    /**
     * 入度-1
     *
     * @return
     */
    public Integer decreaseInDegree() {
        inDegree--;
        return inDegree;
    }
    public DGANode computeDegree(){
        Objects.requireNonNull(inheritNodes);
        Objects.requireNonNull(pioneerNodes);
        this.inDegree = pioneerNodes.size();
        this.outDegree = inheritNodes.size();
        return this;
    }

    public DGANode addPioneers(DGANode... pioneers) {
        Objects.requireNonNull(pioneerNodes);
        pioneerNodes.addAll(Arrays.stream(pioneers).collect(Collectors.toList()));
        return this;
    }

    public DGANode addInherits(DGANode... inherits) {
        Objects.requireNonNull(inheritNodes);
        inheritNodes.addAll(Arrays.stream(inherits).collect(Collectors.toList()));
        return this;
    }

    @Override
    public String toString() {
        return "DGANode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inheritNodesStr='" + inheritNodesStr + '\'' +
                ", inDegree=" + inDegree +
                ", outDegree=" + outDegree +
                ", describe='" + describe + '\'' +
                '}';
    }
}
