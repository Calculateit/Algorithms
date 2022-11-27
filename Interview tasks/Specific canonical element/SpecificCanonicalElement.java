import java.util.Arrays;

/*
 * Union-find with specific canonical element.
 *
 * Add a method find() to the union-find data type so that find(i) returns the largest element in the connected
 * component containing i. The operations, union(), connected(), and find() should all take logarithmic time or better.
 *
 * For example, if one of the connected components is {1,2,6,9}, then the find() method should return 9 for each of
 * the four elements in the connected components.
 */

public class SpecificCanonicalElement {
    private final int[] id;
    private final int[] sz;
    private final int[] maxElementInComponent;

    SpecificCanonicalElement(int elements) {
        id = new int[elements];
        maxElementInComponent = new int[elements];
        sz = new int[elements];

        for(int i = 0; i < id.length; ++i) {
            id[i] = i;
            maxElementInComponent[i] = i;
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
            updateMaxElement(firstRoot, secondRoot);
        } else {
            id[firstRoot] = secondRoot;
            sz[secondRoot] += sz[firstRoot];
            updateMaxElement(secondRoot, firstRoot);
        }
    }

    boolean connected(int firstElement, int secondElement) {
        return root(firstElement) == root(secondElement);
    }

    int find(int element) {
        return maxElementInComponent[root(element)];
    }

    private void updateMaxElement(int primaryElement, int secondaryElement) {
        if (maxElementInComponent[primaryElement] < maxElementInComponent[secondaryElement]) {
            maxElementInComponent[primaryElement] = maxElementInComponent[secondaryElement];
        }
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
