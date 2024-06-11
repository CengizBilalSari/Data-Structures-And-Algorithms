import java.nio.BufferUnderflowException;
import java.util.Comparator;

/**
 * This MinHeap class is the appropriate min heap data structure for this project.
 * It has its own way of comparing and keeping heap property with its methods.
 * There are different types of methods for different minHeaps(like h,r,b etc.) but their logic is actually same.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 10.12.2023
 */
public class MinHeap {

    // song comparators for keeping heap property
    private Comparator<Song> songComparatorH;
    private Comparator<Song> songComparatorR;
    private Comparator<Song> songComparatorB;

    class SongComparatorH implements Comparator<Song> {
        @Override
        public int compare(Song song1, Song song2) {
            int countComparison = Integer.compare(song2.getHeartacheScore(), song1.getHeartacheScore());
            // gives positive integer if song2 bigger than song1
            if (countComparison == 0) {
                return song1.getName().compareTo(song2.getName());
                // gives positive integer if song2 is bigger than song1 as name
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
                // gives positive integer if song2 is bigger than song1 as name
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
                // gives positive integer if song2 is bigger than song1 as name
            }
            return countComparison;
        }
    }

    // data fields
    public Song[] songArray;

    private int currentSize;

    //  necessary getters and setters
    public int getCurrentSize() {
        return currentSize;
    }

    public MinHeap() {
        currentSize = 0;
        songArray = new Song[1000];
        songComparatorH = new MinHeap.SongComparatorH();
        songComparatorR = new MinHeap.SongComparatorR();
        songComparatorB = new MinHeap.SongComparatorB();
    }


    // methods
    public void enlargeArray(int newSize) {
        Song[] newArray = new Song[newSize];
        System.arraycopy(songArray, 0, newArray, 0, songArray.length);
        songArray = newArray;
    }

    public Song findMin() {
        return songArray[1];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }


    public void buildHeapH(Song[] songs) {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDownH(i, songs);
        }
    }

    public void buildHeapR(Song[] songs) {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDownR(i, songs);
        }
    }

    public void buildHeapB(Song[] songs) {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDownB(i, songs);
        }
    }

    /*
     method for main minHeaps
     */
    //  insert methods for hHeap,rHeap, bHeap which are main minHeaps
    public void insertH(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }

        // percolate up (O(logn), on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorH.compare(songArray[hole / 2], x) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].sethHeapIndex(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].sethHeapIndex(hole);
        songArray[0] = null;

    }

    public void insertR(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorR.compare(songArray[hole / 2], x) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setrHeapIndex(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].setrHeapIndex(hole);
        songArray[0] = null;

    }

    public void insertB(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorB.compare(songArray[hole / 2], x) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setbHeapIndex(hole);
        }
        songArray[hole] = x;
        songs[x.getID()].setbHeapIndex(hole);
        songArray[0] = null;
    }

    //  deleteMin methods for hHeap,rHeap, bHeap which are main minHeaps
    public Song deleteMinH(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song minItem = findMin();
        songs[minItem.getID()].sethHeapIndex(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].sethHeapIndex(0);
            songArray[currentSize + 1] = null;
            return minItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownH(1, songs);
        return minItem;
    }

    public Song deleteMinR(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song minItem = findMin();
        songs[minItem.getID()].setrHeapIndex(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setrHeapIndex(0);
            songArray[currentSize + 1] = null;
            return minItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownR(1, songs);
        return minItem;
    }

    public Song deleteMinB(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song minItem = findMin();
        songs[minItem.getID()].setbHeapIndex(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setbHeapIndex(0);
            songArray[currentSize + 1] = null;
            return minItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownB(1, songs);
        return minItem;
    }

    //   percolateDown methods for hHeap,rHeap, bHeap which are main minHeaps
    private void percolateDownH(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorH.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].sethHeapIndex(hole);
            } else {
                break;
            }
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].sethHeapIndex(hole);


    }

    private void percolateDownR(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorR.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setrHeapIndex(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setrHeapIndex(hole);
    }

    private void percolateDownB(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorB.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setbHeapIndex(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setbHeapIndex(hole);
    }


    //  methods for hHeap,rHeap, bHeap which are main minHeaps to delete node in anywhere of the heap

    private void percolateUpH(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorH.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].sethHeapIndex(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].sethHeapIndex(hole);
        songArray[0] = null;
    }

    public Song deleteNodeH(int index, Song[] songs) {
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
        percolateDownHForDeletion(index, songs);

        // Perform up-heap operation
        // Perform up-heap operation
        if (index > 1 && songComparatorH.compare(songArray[(index)], songArray[index / 2]) > 0) {
            percolateUpH(index, songs);
        }
        songs[deletedItem.getID()].sethHeapIndex(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownHForDeletion(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorH.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].sethHeapIndex(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].sethHeapIndex(hole);
    }

    // for r heap
    private void percolateUpR(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorR.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setrHeapIndex(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setrHeapIndex(hole);
        songArray[0] = null;
    }

    public Song deleteNodeR(int index, Song[] songs) {
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
        percolateDownRForDeletion(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorR.compare(songArray[index], songArray[index / 2]) > 0) {
            percolateUpR(index, songs);
        }
        songs[deletedItem.getID()].setrHeapIndex(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownRForDeletion(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorR.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setrHeapIndex(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setrHeapIndex(hole);
    }

    //for b heap
    private void percolateUpB(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorB.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setbHeapIndex(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setbHeapIndex(hole);
        songArray[0] = null;
    }

    public Song deleteNodeB(int index, Song[] songs) {
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
        percolateDownBForDeletion(index, songs);

        // Perform up-heap operation
        if (index > 1 && songComparatorB.compare(songArray[index], songArray[index / 2]) > 0) {
            percolateUpB(index, songs);
        }

        songs[deletedItem.getID()].setbHeapIndex(0); // Mark the song as removed from the heap

        return deletedItem;
    }

    private void percolateDownBForDeletion(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorB.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setbHeapIndex(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setbHeapIndex(hole);
    }

    //..................................................................................................................


    // for playList insertion and deletion
    public void insertHPlaylist(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }

        // percolate up (O(logn), on average constant)
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorH.compare(songArray[hole / 2], x) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMinHeapIndexH(hole);
        }
        songArray[hole] = x;
        songArray[0] = null;
        songs[x.getID()].setPlaylistMinHeapIndexH(hole);

    }

    public void insertRPlaylist(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorR.compare(songArray[hole / 2], x) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMinHeapIndexR(hole);
        }
        songArray[hole] = x;
        songArray[0] = null;
        songs[x.getID()].setPlaylistMinHeapIndexR(hole);

    }

    public void insertBPlaylist(Song x, Song[] songs) {
        if (currentSize == songArray.length - 1) {
            enlargeArray(songArray.length * 2 + 1);
        }
        int hole = ++currentSize;
        for (songArray[0] = x; songComparatorB.compare(songArray[hole / 2], x) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMinHeapIndexB(hole);
        }
        songArray[hole] = x;
        songArray[0] = null;
        songs[x.getID()].setPlaylistMinHeapIndexB(hole);

    }

    private void percolateDownHPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorH.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMinHeapIndexH(hole);
            } else {
                break;
            }
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMinHeapIndexH(hole);
    }

    private void percolateDownRPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorR.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMinHeapIndexR(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMinHeapIndexR(hole);
    }

    private void percolateDownBPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorB.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMinHeapIndexB(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMinHeapIndexB(hole);
    }

    public Song deleteMinHPlaylist(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song minItem = findMin();
        songs[minItem.getID()].setPlaylistMinHeapIndexH(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setPlaylistMinHeapIndexH(0);
            songArray[currentSize + 1] = null;
            return minItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownHPlaylist(1, songs);
        return minItem;
    }

    public Song deleteMinRPlaylist(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song minItem = findMin();
        songs[minItem.getID()].setPlaylistMinHeapIndexR(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setPlaylistMinHeapIndexR(0);
            songArray[currentSize + 1] = null;
            return minItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownRPlaylist(1, songs);
        return minItem;
    }

    public Song deleteMinBPlaylist(Song[] songs) {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        Song minItem = findMin();
        songs[minItem.getID()].setPlaylistMinHeapIndexB(0);
        songArray[1] = songArray[currentSize--];
        if (currentSize == 0) {
            songs[songArray[1].getID()].setPlaylistMinHeapIndexB(0);
            songArray[currentSize + 1] = null;
            return minItem;
        }
        songArray[currentSize + 1] = null;
        percolateDownBPlaylist(1, songs);
        return minItem;
    }


    // for playlist min heap deletion in anywhere of the tree
    private void percolateUpHForPlayList(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorH.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMinHeapIndexH(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMinHeapIndexH(hole);
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
        if (index > 1 && songComparatorH.compare(songArray[(index)], songArray[index / 2]) > 0) {
            percolateUpHForPlayList(index, songs);
        }
        songs[deletedItem.getID()].setPlaylistMinHeapIndexH(0);

        return deletedItem;
    }

    private void percolateDownHForDeletionPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorH.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorH.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMinHeapIndexH(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMinHeapIndexH(hole);
    }

    // for r heap
    private void percolateUpRForPlaylist(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorR.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMinHeapIndexR(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMinHeapIndexR(hole);
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
        if (index > 1 && songComparatorR.compare(songArray[index], songArray[index / 2]) > 0) {
            percolateUpRForPlaylist(index, songs);
        }
        songs[deletedItem.getID()].setPlaylistMinHeapIndexR(0);
        return deletedItem;
    }

    private void percolateDownRForDeletionPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorR.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorR.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMinHeapIndexR(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[tmp.getID()].setPlaylistMinHeapIndexR(hole);
    }

    //for b heap
    private void percolateUpBForPlaylist(int index, Song[] songs) {
        Song tmp = songArray[index];
        int hole = index;

        for (songArray[0] = tmp; hole > 1 && songComparatorB.compare(songArray[hole / 2], tmp) < 0; hole /= 2) {
            songArray[hole] = songArray[hole / 2];
            songs[songArray[hole].getID()].setPlaylistMinHeapIndexB(hole);
        }

        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMinHeapIndexB(hole);
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
        if (index > 1 && songComparatorB.compare(songArray[index], songArray[index / 2]) > 0) {
            percolateUpBForPlaylist(index, songs);
        }

        songs[deletedItem.getID()].setPlaylistMinHeapIndexB(0);

        return deletedItem;
    }

    private void percolateDownBForDeletionPlaylist(int hole, Song[] songs) {
        int child;
        Song tmp = songArray[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && songComparatorB.compare(songArray[child], songArray[child + 1]) < 0)
                child++;
            if (songComparatorB.compare(tmp, songArray[child]) < 0) {
                songArray[hole] = songArray[child];
                songs[songArray[hole].getID()].setPlaylistMinHeapIndexB(hole);
            } else
                break;
        }
        songArray[hole] = tmp;
        songs[songArray[hole].getID()].setPlaylistMinHeapIndexB(hole);
    }
}