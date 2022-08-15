import java.util.Arrays;

/*
 * Successor with delete.
 *
 * Given a set of nnn integers S={0,1,...,n−1} and a sequence of requests of the following form:
 *   1) Remove x from S
 *   2) Find the successor of x: the smallest y in S such that y >= x.
 * design a data type so that all operations (except construction)  take logarithmic time or better in the worst case.
 */

public class SuccessorWithDelete {
    private final int[] id;
    private final int[] sz;
    private final int[] actualRootId;

    SuccessorWithDelete(int elements) {
        id = new int[elements];
        sz = new int[elements];
        actualRootId = new int[elements];
        for(int i = 0; i < id.length; ++i) {
            id[i] = i;
            actualRootId[i] = i;
        }
        Arrays.fill(sz, 1);
    }

    void union(int firstElement, int secondElement) {
        int firstRoot = root(firstElement);
        int secondRoot = root(secondElement);

        if (firstRoot == secondRoot) return;
        if (sz[firstRoot] > sz[secondRoot]) {
            id[secondRoot] = firstRoot;
            sz[firstRoot] += sz[secondRoot];
            actualRootId[firstRoot] = secondRoot;
        } else {
            id[firstRoot] = secondRoot;
            sz[secondRoot] += sz[firstRoot];
        }
    }

    boolean connected(int firstElement, int secondElement) {
        return root(firstElement) == root(secondElement);
    }

    void remove(int x) {
        union(x, x+1);
    }

    int getSuccessor(int x) {
        return actualRootId[root(x+1)];
    }

    private int root(int element) throws IllegalArgumentException {
        if (element > id.length) {
            throw new IllegalArgumentException("Element is out of range!");
        }
        while (element != id[element]) {
            id[element] = id[id[element]];
            element = id[element];
        }
        return element;
    }
}
