import java.nio.BufferUnderflowException;
import java.util.Comparator;

/**
 * This MaxHeap class is the appropriate max heap data structure for this project.
 * It has its own way of comparing and keeping heap property with its methods.
 * There are different types of methods for different maxHeaps(like h,r,b etc.) but their logic is actually same.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 10.12.2023
 */
public class MaxHeap {

    // song comparators for keeping heap property
    private Comparator<Song> songComparatorH;
    private Comparator<Song> songComparatorR;
    private Comparator<Song> songComparatorB;

    private Comparator<Song> songComparatorC;

    class SongComparatorC implements Comparator<Song> {
        @Override
        public int compare(Song song1, Song song2) {
            int countComparison = Integer.compare(song2.getPlayCount(), song1.getPlayCount());

            if (countComparison == 0) {
                return song1.getName().compareTo(song2.getName());
            }
            return countComparison;
        }
    }

    class SongComparatorH implements Comparator<Song> {
        @Override
        public int compare(Song song1, Song song2) {
            int countComparison = Integer.compare(song2.getHeartacheScore(), song1.getHeartacheScore());
            // gives positive integer if song2 bigger than song1
            if (countComparison == 0) {
                return song1.getName().compareTo(song2.getName());
                // gives positive integer if song1 is bigger than song2 as name
            }
            return countComparison;
        }
    }

    class SongComparatorR implements Comparator<Song> {
        @Override
        public int compare(Song song1, Song song2) {
            int countComparison = Integer.compare(song2.getRoadTripScore(), song1.getRoadTripScore());
            // gives positive integer if song2 bigger than song1
            if (countComparison == 0) {
                return song1.getName().compareTo(song2.getName());
                // gives positive integer if song1 is bigger than song2 as name
            }
            return countComparison;
        }
    }

    class SongComparatorB implements Comparator<Song> {
        @Override
        public int compare(Song song1, Song song2) {
            int countComparison = Integer.compare(song2.getBlissfulScore(), song1.getBlissfulScore());
            // gives positive integer if song2 bigger than song1
            if (countComparison == 0) {
                return song1.getName().compareTo(song2.getName());
                // gives positive integer if song1 is bigger than song2 as name
            }
            return countComparison;
        }
    }

    // data fields
    public Song[] songArray;

    private int currentSize;

    // getters and setters
    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public MaxHeap() {
        currentSize = 0;
        songArray = new Song[1000];
        songComparatorH = new SongComparatorH();
        songComparatorR = new SongComparatorR();
        songComparatorB = new SongComparatorB();
        songComparatorC = new SongComparatorC();
    }


    // methods
    public void enlargeArray(int newSize) {
        Song[] newArray = new Song[newSize];
        System.arraycopy(songArray, 0, newArray, 0, songArray.length);
        songArray = newArray;
    }

    public Song findMax() {
        return songArray[1];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    // these insertions and deletions are for first heaps which is used by just one
    public void insertH(Song x) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }

        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorH.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
        }
        songArray[hole] = x;
        songArray[0] = null;
    }

    public void insertR(Song x) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorR.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
        }
        songArray[hole] = x;
        songArray[0] = null;
    }

    public void insertB(Song x) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorB.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
        }
        songArray[hole] = x;
        songArray[0] = null;
    }

    public void insertC(Song x, Song[] songs) {
        if (x.getAddedMaxHeap()) {
            return;
        }
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorC.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
        }
        songArray[hole] = x;
        songArray[0] = null;
        songs[x.getID()].setAddedMaxHeap(true);

    }

    public Song deleteMaxC(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setAddedMaxHeap(false);
        songArray[1] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        percolateDownC(1);
        return maxItem;
    }

    private void percolateDownC(int hole) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorC.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorC.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
            } else break;
        }
        songArray[hole] = tmp;
    }


    public void buildHeapH() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDownH(i);
        }
    }

    public void buildHeapR() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDownR(i);
        }
    }

    public void buildHeapB() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDownB(i);
        }
    }

    public Song deleteMaxH() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songArray[1] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        percolateDownH(1);
        return maxItem;
    }

    public Song deleteMaxR() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songArray[1] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        percolateDownR(1);
        return maxItem;
    }

    public Song deleteMaxB() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songArray[1] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        percolateDownB(1);
        return maxItem;
    }

    private void percolateDownH(int hole) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorH.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
            } else break;
        }
        songArray[hole] = tmp;
    }

    private void percolateDownR(int hole) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorR.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
            } else break;
        }
        songArray[hole] = tmp;
    }

    private void percolateDownB(int hole) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorB.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
            } else break;
        }
        songArray[hole] = tmp;
    }
    //...........................................................................

    // for all ones removal playlists  , insertions and deletions
    public void insertHForRemoval(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }

        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorH.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setMaxHeapIndexH(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].setMaxHeapIndexH(hole);
        songArray[0] = null;
    }

    public void insertRForRemoval(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorR.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setMaxHeapIndexR(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].setMaxHeapIndexR(hole);
        songArray[0] = null;
    }

    public void insertBForRemoval(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorB.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
        }
        songArray[hole] = x;
        songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
        songArray[0] = null;
    }

    //.............................................................
    public Song deleteMaxHRemoval(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setMaxHeapIndexH(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setMaxHeapIndexH(0);
            songArray[currentSize + 1] = null;
            return maxItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownHRemoval(1, songs);
        return maxItem;
    }

    public Song deleteMaxRRemoval(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setMaxHeapIndexR(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setMaxHeapIndexR(0);
            songArray[currentSize + 1] = null;
            return maxItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownRRemoval(1, songs);
        return maxItem;
    }

    public Song deleteMaxBRemoval(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setMaxHeapIndexB(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setMaxHeapIndexB(0);
            songArray[currentSize + 1] = null;
            return maxItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownBRemoval(1, songs);
        return maxItem;
    }

    private void percolateDownHRemoval(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorH.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setMaxHeapIndexH(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setMaxHeapIndexH(hole);
    }

    private void percolateDownRRemoval(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorR.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setMaxHeapIndexR(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setMaxHeapIndexR(hole);
    }

    private void percolateDownBRemoval(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorB.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setMaxHeapIndexB(hole);
    }

    ///.........................................
    // for max heap deletion in anywhere of the tree
    private void percolateUpHForRemoval(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorH.compare(songArray[hole / 2], tmp) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setMaxHeapIndexH(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setMaxHeapIndexH(hole);
        songArray[0] = null;
    }

    public Song deleteNodeHForRemoval(int index, Song[] songs) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Song deletedItem = songArray[index];
        songArray[index] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        if (songArray[index] == null) {
            return deletedItem;
        }

        // Perform down-heap operation
        percolateDownHForRemoval(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorH.compare(songArray[(index)], songArray[index / 2]) < 0) {
            percolateUpHForRemoval(index, songs);
        }
        songs[deletedItem.getID()].setMaxHeapIndexH(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownHForRemoval(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorH.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setMaxHeapIndexH(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setMaxHeapIndexH(hole);
    }

    // for r heap
    private void percolateUpRForRemoval(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorR.compare(songArray[hole / 2], tmp) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setMaxHeapIndexR(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setMaxHeapIndexR(hole);
        songArray[0] = null;
    }

    public Song deleteNodeRForRemoval(int index, Song[] songs) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Song deletedItem = songArray[index];
        songArray[index] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        if (songArray[index] == null) {
            return deletedItem;
        }

        // Perform down-heap operation
        percolateDownRForRemoval(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorR.compare(songArray[index], songArray[index / 2]) < 0) {
            percolateUpRForRemoval(index, songs);
        }
        songs[deletedItem.getID()].setMaxHeapIndexR(0); // Mark the song as removed from the heap
        return deletedItem;
    }

    private void percolateDownRForRemoval(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorR.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setMaxHeapIndexR(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setMaxHeapIndexR(hole);
    }

    //for b heap
    private void percolateUpBForRemoval(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorB.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
        songArray[0] = null;
    }

    public Song deleteNodeBForRemoval(int index, Song[] songs) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Song deletedItem = songArray[index];
        songArray[index] = songArray[currentSize--];
        songArray[currentSize + 1] = null;

        if (songArray[index] == null) {
            return deletedItem;
        }
        // Perform down-heap operation
        percolateDownBForRemoval(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorB.compare(songArray[index], songArray[index / 2]) < 0) {
            percolateUpBForRemoval(index, songs);
        }

        songs[deletedItem.getID()].setMaxHeapIndexB(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownBForRemoval(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorB.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setMaxHeapIndexB(hole);
    }


    // method for playlists - insertions and deletions
    public void insertHForPlaylist(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }

        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorH.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMaxHeapIndexH(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].setPlaylistMaxHeapIndexH(hole);
        songArray[0] = null;
    }

    public void insertRForPlaylist(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorR.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMaxHeapIndexR(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].setPlaylistMaxHeapIndexR(hole);
        songArray[0] = null;
    }

    public void insertBForPlaylist(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        // percolate up ( O(logn) , on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorB.compare(songArray[hole / 2], x) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
        }
        songArray[hole] = x;
        songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
        songArray[0] = null;
    }

    //.............................................................
    public Song deleteMaxHPlaylist(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setPlaylistMaxHeapIndexH(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setPlaylistMaxHeapIndexH(0);
            songArray[currentSize + 1] = null;
            return maxItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownHPlaylist(1, songs);
        return maxItem;
    }

    public Song deleteMaxRPlaylist(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setPlaylistMaxHeapIndexR(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setPlaylistMaxHeapIndexR(0);
            songArray[currentSize + 1] = null;
            return maxItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownRPlaylist(1, songs);
        return maxItem;
    }

    public Song deleteMaxBPlaylist(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song maxItem = findMax();
        songs[maxItem.getID()].setPlaylistMaxHeapIndexB(0);
        songArray[1] = songArray[currentSize--];

        if (currentSize == 0) {
            songs[songArray[1].getID()].setPlaylistMaxHeapIndexB(0);
            songArray[currentSize + 1] = null;
            return maxItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownBPlaylist(1, songs);
        return maxItem;
    }

    private void percolateDownHPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorH.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMaxHeapIndexH(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMaxHeapIndexH(hole);
    }

    private void percolateDownRPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorR.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMaxHeapIndexR(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMaxHeapIndexR(hole);
    }

    private void percolateDownBPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorB.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMaxHeapIndexB(hole);
    }

    ///.........................................
    // for playlist max heap deletion in anywhere of the tree
    private void percolateUpHForPlayList(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorH.compare(songArray[hole / 2], tmp) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMaxHeapIndexH(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMaxHeapIndexH(hole);
        songArray[0] = null;
    }

    public Song deleteNodeHForPlayList(int index, Song[] songs) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Song deletedItem = songArray[index];
        songArray[index] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        if (songArray[index] == null) {
            return deletedItem;
        }
        // Perform down-heap operation
        percolateDownHForDeletionPlaylist(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorH.compare(songArray[(index)], songArray[index / 2]) < 0) {
            percolateUpHForPlayList(index, songs);
        }
        songs[deletedItem.getID()].setPlaylistMaxHeapIndexH(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownHForDeletionPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorH.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMaxHeapIndexH(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMaxHeapIndexH(hole);
    }

    // for r heap
    private void percolateUpRForPlaylist(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorR.compare(songArray[hole / 2], tmp) > 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMaxHeapIndexR(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMaxHeapIndexR(hole);
        songArray[0] = null;
    }

    public Song deleteNodeRForPlaylist(int index, Song[] songs) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Song deletedItem = songArray[index];
        songArray[index] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        if (songArray[index] == null) {
            return deletedItem;
        }

        // Perform down-heap operation
        percolateDownRForDeletionPlaylist(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorR.compare(songArray[index], songArray[index / 2]) < 0) {
            percolateUpRForPlaylist(index, songs);
        }
        songs[deletedItem.getID()].setPlaylistMaxHeapIndexR(0); // Mark the song as removed from the heap
        return deletedItem;
    }

    private void percolateDownRForDeletionPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorR.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMaxHeapIndexR(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMaxHeapIndexR(hole);
    }

    //for b heap
    private void percolateUpBForPlaylist(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorB.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
        songArray[0] = null;
    }

    public Song deleteNodeBForPlaylist(int index, Song[] songs) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Song deletedItem = songArray[index];
        songArray[index] = songArray[currentSize--];
        songArray[currentSize + 1] = null;
        if (songArray[index] == null) {
            return deletedItem;
        }

        // Perform down-heap operation
        percolateDownBForDeletionPlaylist(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorB.compare(songArray[index], songArray[index / 2]) < 0) {
            percolateUpBForPlaylist(index, songs);
        }

        songs[deletedItem.getID()].setPlaylistMaxHeapIndexB(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownBForDeletionPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) > 0) child++;
            if (songComparatorB.compare(tmp, songArray[child]) > 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
            } else break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMaxHeapIndexB(hole);
    }
}