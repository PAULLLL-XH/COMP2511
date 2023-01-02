package q11.confluence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfluenceSpace extends ConfluenceNode implements Iterable<ConfluencePage> {
    private List<ConfluenceNode> nodes;

    public ConfluenceSpace(){
        nodes = new ArrayList<>();
    }

    public void addSubnode(ConfluenceNode node) {
        nodes.add(node);
    }

    public int getNumPages() {
        int ret = 0;
        for (ConfluenceNode node : nodes){
            ret += node.getNumPages();
        }
        return ret;
    }

    @Override
    public Iterator<ConfluencePage> iterator() {
        Iterator<ConfluencePage> it = new Iterator<ConfluencePage>() {
            private int currentIndex = 0;
            private int currentPos = 0;
            private Iterator<ConfluencePage> it_sub;

            @Override
            public boolean hasNext() {
                return currentIndex < getNumPages();
            }

            @Override
            public ConfluencePage next() {
                if (nodes.get(currentPos) instanceof ConfluencePage){
                    it_sub = null;
                    currentIndex++;
                    return (ConfluencePage) nodes.get(currentPos++);
                }
                ConfluenceSpace subspace = (ConfluenceSpace) nodes.get(currentPos);
                if (it_sub == null){
                    it_sub = subspace.iterator();
                }
                if (it_sub.hasNext()){
                    currentIndex++;
                    return it_sub.next();
                }
                else{
                    currentPos++;
                    return next();
                }
            }
        };
        return it;
    }
}