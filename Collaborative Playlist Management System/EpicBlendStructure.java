import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * This EpicBlendStructure is the most crucial class in the project. The most methods program needed is here.
 * With methods of this class, the program take input files and make modification on heaps.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 10.12.2023
 */

public class EpicBlendStructure {


    public void ask() {
        MaxHeap maxHeap = new MaxHeap();
        for (int i = 1; i < hHeap.getCurrentSize() + 1; i++) {
            maxHeap.insertC(hHeap.songArray[i], allSongs);
        }
        for (int i = 1; i < rHeap.getCurrentSize() + 1; i++) {
            maxHeap.insertC(rHeap.songArray[i], allSongs);
        }
        for (int i = 1; i < bHeap.getCurrentSize() + 1; i++) {
            maxHeap.insertC(bHeap.songArray[i], allSongs);
        }
        int currentSize = maxHeap.getCurrentSize() + 1;
        for (int i = 1; i < currentSize; i++) {
            System.out.print(maxHeap.deleteMaxC(allSongs).getID() + " ");
        }
        System.out.println();

    }

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

    public Song[] allSongs;
    public ArrayList<Song> realSongs;
    private Comparator<Song> songComparatorH;
    private Comparator<Song> songComparatorR;
    private Comparator<Song> songComparatorB;
    private Comparator<Song> songComparatorC;
    public MinHeap[] playlistsH;
    public MinHeap[] playlistsR;
    public MinHeap[] playlistsB;

    public MaxHeap[] playlistsNotH;
    public MaxHeap[] playlistsNotB;
    public MaxHeap[] playlistsNotR;

    public MaxHeap playlistHForRemoval;
    public MaxHeap playlistRForRemoval;
    public MaxHeap playlistBForRemoval;


    public int[] playlistNumbersH;   // to compare with limit
    public int[] playlistNumbersR;
    public int[] playlistNumbersB;

    private MaxHeap hFirstHeap;
    private MaxHeap rFirstHeap;
    private MaxHeap bFirstHeap;

    public MinHeap hHeap;
    public MinHeap rHeap;
    public MinHeap bHeap;


    private int chNumber;
    private int crNumber;
    private int cbNumber;

    private int limit;

    public EpicBlendStructure() {
        this.hFirstHeap = new MaxHeap();
        this.rFirstHeap = new MaxHeap();
        this.bFirstHeap = new MaxHeap();
        this.hHeap = new MinHeap();
        this.rHeap = new MinHeap();
        this.bHeap = new MinHeap();
        this.allSongs = new Song[1000001];
        this.realSongs = new ArrayList<>();
        this.songComparatorH = new SongComparatorH();
        this.songComparatorR = new SongComparatorR();
        this.songComparatorB = new SongComparatorB();
        this.songComparatorC = new SongComparatorC();
        this.playlistHForRemoval = new MaxHeap();
        this.playlistRForRemoval = new MaxHeap();
        this.playlistBForRemoval = new MaxHeap();
    }

    // getter setters
    public MaxHeap gethFirstHeap() {
        return hFirstHeap;
    }

    public void sethFirstHeap(MaxHeap hFirstHeap) {
        this.hFirstHeap = hFirstHeap;
    }

    public MaxHeap getrFirstHeap() {
        return rFirstHeap;
    }

    public void setrFirstHeap(MaxHeap rFirstHeap) {
        this.rFirstHeap = rFirstHeap;
    }

    public MaxHeap getbFirstHeap() {
        return bFirstHeap;
    }

    public void setbFirstHeap(MaxHeap bFirstHeap) {
        this.bFirstHeap = bFirstHeap;
    }

    public int getChNumber() {
        return chNumber;
    }

    public void setChNumber(int chNumber) {
        this.chNumber = chNumber;
    }

    public int getCrNumber() {
        return crNumber;
    }

    public void setCrNumber(int crNumber) {
        this.crNumber = crNumber;
    }

    public int getCbNumber() {
        return cbNumber;
    }

    public void setCbNumber(int cbNumber) {
        this.cbNumber = cbNumber;
    }


    // methods
    /*
     One of the main method :taking first file method to collect song database
     */
    public void takingFirstFile(String fileName) throws FileNotFoundException {
        // it takes first file and put the songs into song database
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        int numberOfSong = Integer.parseInt(inputFile.nextLine());
        int counter = 0;
        while (numberOfSong != counter) {
            String[] line = inputFile.nextLine().split(" ");
            Song song = new Song(Integer.parseInt(line[0]), line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]));
            allSongs[Integer.parseInt(line[0])] = song;
            counter++;
        }
    }

    /*
   One of the main method :taking second file to take initial situation and modification and also give compulsory feedbacks
   */
    public void takingPlaylists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        String[] criticNumbers = inputFile.nextLine().split(" ");
        limit = Integer.parseInt(criticNumbers[0]);
        chNumber = Integer.parseInt(criticNumbers[1]);
        crNumber = Integer.parseInt(criticNumbers[2]);
        cbNumber = Integer.parseInt(criticNumbers[3]);
        int playlistNumber = Integer.parseInt(inputFile.nextLine());
        int counter = 0;
        playlistNumbersH = new int[playlistNumber + 1];
        playlistNumbersR = new int[playlistNumber + 1];
        playlistNumbersB = new int[playlistNumber + 1];
        playlistsH = new MinHeap[playlistNumber + 1];
        for (int i = 1; i < playlistNumber + 1; i++) {
            playlistsH[i] = new MinHeap();
        }
        playlistsR = new MinHeap[playlistNumber + 1];
        for (int i = 1; i < playlistNumber + 1; i++) {
            playlistsR[i] = new MinHeap();
        }
        playlistsB = new MinHeap[playlistNumber + 1];
        for (int i = 1; i < playlistNumber + 1; i++) {
            playlistsB[i] = new MinHeap();
        }

        // max heaps for remove
        playlistsNotH = new MaxHeap[playlistNumber + 1];
        for (int i = 1; i < playlistNumber + 1; i++) {
            playlistsNotH[i] = new MaxHeap();
        }
        playlistsNotR = new MaxHeap[playlistNumber + 1];
        for (int i = 1; i < playlistNumber + 1; i++) {
            playlistsNotR[i] = new MaxHeap();
        }
        playlistsNotB = new MaxHeap[playlistNumber + 1];
        for (int i = 1; i < playlistNumber + 1; i++) {
            playlistsNotB[i] = new MaxHeap();
        }
        // this while loop is for taking initial situation
        while (playlistNumber != counter) {
            String[] playlist = inputFile.nextLine().split(" ");
            String[] songs = inputFile.nextLine().split(" ");
            int playlistID = Integer.parseInt(playlist[0]);
            int songNumber = Integer.parseInt(playlist[1]);
            for (int i = 0; i < songNumber; i++) {
                allSongs[Integer.parseInt(songs[i])].setPlaylistID(playlistID);
                realSongs.add(allSongs[Integer.parseInt(songs[i])]);
            }
            counter++;
        }
        createHeapH(realSongs);

        createHeapR(realSongs);
        createHeapB(realSongs);

        createRealHeapH();
        createRealHeapB();
        createRealHeapR();
        realSongs = null;
        hFirstHeap = null;
        rFirstHeap = null;
        bFirstHeap = null;

        // taking modifications
        int modification = Integer.parseInt(inputFile.nextLine());
        while (modification > 0) {
            String[] line = inputFile.nextLine().split(" ");
            if (line[0].equals("ADD")) {
                add(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            } else if (line[0].equals("REM")) {
                remove(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            } else if (line[0].equals("ASK")) {
                ask();

            }
            modification--;
        }
    }

    // create first heaps to just one use
    public void createHeapH(ArrayList<Song> songs) {
        hFirstHeap.setCurrentSize(songs.size());
        hFirstHeap.songArray = new Song[songs.size() + 1];
        for (int i = 0; i < songs.size(); i++) {
            hFirstHeap.songArray[i + 1] = songs.get(i);
        }
        hFirstHeap.buildHeapH();
    }

    public void createHeapR(ArrayList<Song> songs) {
        rFirstHeap.setCurrentSize(songs.size());
        rFirstHeap.songArray = new Song[songs.size() + 1];
        for (int i = 0; i < songs.size(); i++) {
            rFirstHeap.songArray[i + 1] = songs.get(i);
        }
        rFirstHeap.buildHeapR();
    }

    public void createHeapB(ArrayList<Song> songs) {
        bFirstHeap.setCurrentSize(songs.size());
        bFirstHeap.songArray = new Song[songs.size() + 1];

        for (int i = 0; i < songs.size(); i++) {
            bFirstHeap.songArray[i + 1] = songs.get(i);

        }
        bFirstHeap.buildHeapB();
    }


    // create real min heaps and playlist min heaps
    public void createRealHeapH() {
        while (!hFirstHeap.isEmpty()) {
            Song song = hFirstHeap.deleteMaxH();
            if (hHeap.getCurrentSize() == chNumber || playlistNumbersH[song.getPlaylistID()] == limit) {
                // special**
                playlistsNotH[song.getPlaylistID()].insertHForPlaylist(song, allSongs);
            } else {
                hHeap.insertH(song, allSongs);
                playlistNumbersH[song.getPlaylistID()] += 1;
                playlistsH[song.getPlaylistID()].insertHPlaylist(song, allSongs);
            }
        }
        for (int i = 1; i < playlistsNotH.length; i++) {
            if (!playlistsNotH[i].isEmpty() && playlistNumbersH[i] != limit) {
                playlistHForRemoval.insertHForRemoval(playlistsNotH[i].findMax(), allSongs);

            }
        }
    }

    public void createRealHeapR() {
        while (!rFirstHeap.isEmpty()) {
            Song song = rFirstHeap.deleteMaxR();
            if (rHeap.getCurrentSize() == crNumber || playlistNumbersR[song.getPlaylistID()] == limit) {
                // special**
                playlistsNotR[song.getPlaylistID()].insertRForPlaylist(song, allSongs);
            } else {
                rHeap.insertR(song, allSongs);
                playlistNumbersR[song.getPlaylistID()] += 1;
                playlistsR[song.getPlaylistID()].insertRPlaylist(song, allSongs);
            }
        }
        for (int i = 1; i < playlistsNotR.length; i++) {
            if (!playlistsNotR[i].isEmpty() && playlistNumbersR[i] != limit) {
                playlistRForRemoval.insertRForRemoval(playlistsNotR[i].findMax(), allSongs);

            }
        }
    }

    public void createRealHeapB() {
        while (!bFirstHeap.isEmpty()) {
            Song song = bFirstHeap.deleteMaxB();
            if (bHeap.getCurrentSize() == cbNumber || playlistNumbersB[song.getPlaylistID()] == limit) {
                // special**
                playlistsNotB[song.getPlaylistID()].insertBForPlaylist(song, allSongs);
            } else {
                bHeap.insertB(song, allSongs);
                playlistNumbersB[song.getPlaylistID()] += 1;
                playlistsB[song.getPlaylistID()].insertBPlaylist(song, allSongs);
            }
        }
        for (int i = 1; i < playlistsNotB.length; i++) {
            if (!playlistsNotB[i].isEmpty() && playlistNumbersB[i] != limit) {
                playlistBForRemoval.insertBForRemoval(playlistsNotB[i].findMax(), allSongs);

            }
        }
    }


    public void remove(int number, int playlistID) {
        int removedH = 0;
        int removedR = 0;
        int removedB = 0;
        int addedH = 0;
        int addedR = 0;
        int addedB = 0;
        if (allSongs[number].gethHeapIndex() != 0) {
            if (playlistNumbersH[allSongs[number].getPlaylistID()] != limit) {
                // which one added has to be selected from  playlistHForRemoval, and then it should be deleted from also its own playlist
                // it should be added to its own playlist and also hHeap
                Song songAdd = null;
                // insertion it to minHeaps
                if (!playlistHForRemoval.isEmpty()) {
                    songAdd = playlistHForRemoval.deleteMaxHRemoval(allSongs);
                    playlistsNotH[songAdd.getPlaylistID()].deleteMaxHPlaylist(allSongs);
                    playlistNumbersH[songAdd.getPlaylistID()]++;
                    hHeap.insertH(songAdd, allSongs);
                    playlistsH[songAdd.getPlaylistID()].insertHPlaylist(songAdd, allSongs);
                    addedH = songAdd.getID();
                }
                // deleted the removed one from hMinHeap , its playlist heap
                removedH = number;
                hHeap.deleteNodeH(allSongs[number].gethHeapIndex(), allSongs);
                playlistNumbersH[playlistID]--;
                playlistsH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMinHeapIndexH(), allSongs);

                if (songAdd != null && !playlistsNotH[songAdd.getPlaylistID()].isEmpty())
                    playlistHForRemoval.insertHForRemoval(playlistsNotH[songAdd.getPlaylistID()].findMax(), allSongs);
            } else {
                Song songAdd = null;
                Song song2 = null;
                if (!playlistHForRemoval.isEmpty())
                    songAdd = playlistHForRemoval.findMax();
                if (!playlistsNotH[playlistID].isEmpty()) {
                    song2 = playlistsNotH[playlistID].findMax();
                }
                if ((songAdd == null && song2 != null)) {
                    addedH = song2.getID();
                    playlistsNotH[playlistID].deleteMaxHPlaylist(allSongs);
                    hHeap.insertH(allSongs[song2.getID()], allSongs);
                    removedH = number;
                    hHeap.deleteNodeH(allSongs[number].gethHeapIndex(), allSongs);
                    playlistsH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMinHeapIndexH(), allSongs);
                    playlistsH[playlistID].insertHPlaylist(allSongs[song2.getID()], allSongs);


                } else if ((songAdd != null && song2 == null)) {
                    addedH = songAdd.getID();
                    removedH = number;
                    // added one
                    hHeap.insertH(allSongs[songAdd.getID()], allSongs);
                    playlistsH[allSongs[songAdd.getID()].getPlaylistID()].insertHPlaylist(allSongs[songAdd.getID()], allSongs);
                    playlistNumbersH[allSongs[songAdd.getID()].getPlaylistID()]++;
                    playlistHForRemoval.deleteMaxHRemoval(allSongs);
                    playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].deleteMaxHPlaylist(allSongs);
                    if (!playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].isEmpty())
                        playlistHForRemoval.insertHForRemoval(playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].findMax(), allSongs);
                    // removed one
                    hHeap.deleteNodeH(allSongs[number].gethHeapIndex(), allSongs);
                    playlistsH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMinHeapIndexH(), allSongs);
                    playlistNumbersH[playlistID]--;
                    // new
                    // if there is a limitation now for added one
                    if (playlistNumbersH[allSongs[songAdd.getID()].getPlaylistID()] == limit && !playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].isEmpty()) {
                        playlistHForRemoval.deleteNodeHForRemoval(playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].findMax().getMaxHeapIndexH(), allSongs);
                    }
                    // if there is no limit condition now for this
                    if (playlistNumbersH[playlistID] == limit - 1 && !playlistsNotH[playlistID].isEmpty()) {
                        playlistHForRemoval.insertHForRemoval(allSongs[playlistsNotH[playlistID].findMax().getID()], allSongs);
                    }

                } else if (songAdd == null && song2 == null) {
                    removedH = number;
                    hHeap.deleteNodeH(allSongs[number].gethHeapIndex(), allSongs);
                    playlistsH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMinHeapIndexH(), allSongs);
                    playlistNumbersH[playlistID]--;

                } else if (songComparatorH.compare(song2, songAdd) > 0) {
                    addedH = songAdd.getID();
                    removedH = number;
                    // added one
                    hHeap.insertH(allSongs[songAdd.getID()], allSongs);
                    playlistsH[allSongs[songAdd.getID()].getPlaylistID()].insertHPlaylist(allSongs[songAdd.getID()], allSongs);
                    playlistNumbersH[allSongs[songAdd.getID()].getPlaylistID()]++;
                    playlistHForRemoval.deleteMaxHRemoval(allSongs);
                    playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].deleteMaxHPlaylist(allSongs);
                    if (!playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].isEmpty())
                        playlistHForRemoval.insertHForRemoval(playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].findMax(), allSongs);

                    // if there is a limitation now for added one
                    if (playlistNumbersH[allSongs[songAdd.getID()].getPlaylistID()] == limit && !playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].isEmpty()) {
                        playlistHForRemoval.deleteNodeHForRemoval(playlistsNotH[allSongs[songAdd.getID()].getPlaylistID()].findMax().getMaxHeapIndexH(), allSongs);
                    }
                    // removed one
                    hHeap.deleteNodeH(allSongs[number].gethHeapIndex(), allSongs);
                    playlistsH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMinHeapIndexH(), allSongs);
                    playlistNumbersH[playlistID]--;
                    // if there is no limit condition now for this
                    if (playlistNumbersH[playlistID] == limit - 1 && !playlistsNotH[playlistID].isEmpty()) {
                        playlistHForRemoval.insertHForRemoval(allSongs[playlistsNotH[playlistID].findMax().getID()], allSongs);
                    }

                } else if (songComparatorH.compare(songAdd, song2) > 0) {
                    addedH = song2.getID();
                    removedH = number;
                    playlistsNotH[playlistID].deleteMaxHPlaylist(allSongs);
                    hHeap.deleteNodeH(allSongs[number].gethHeapIndex(), allSongs);
                    hHeap.insertH(allSongs[song2.getID()], allSongs);
                    playlistsH[playlistID].insertHPlaylist(allSongs[song2.getID()], allSongs);
                }
            }
        } else {
            if (playlistsNotH[playlistID].findMax().getID() == number && playlistNumbersH[playlistID] != limit) {
                playlistHForRemoval.deleteNodeHForRemoval(allSongs[number].getMaxHeapIndexH(), allSongs);
                playlistsNotH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMaxHeapIndexH(), allSongs);
                if (!playlistsNotH[playlistID].isEmpty())
                    playlistHForRemoval.insertHForRemoval(playlistsNotH[playlistID].findMax(), allSongs);
            } else {
                playlistsNotH[playlistID].deleteNodeHForPlayList(allSongs[number].getPlaylistMaxHeapIndexH(), allSongs);
            }

        }
        if (allSongs[number].getrHeapIndex() != 0) {

            if (playlistNumbersR[allSongs[number].getPlaylistID()] != limit) {
                // which one added has to be selected from  playlistHForRemoval, and then it should be deleted from also its own playlist
                // it should be added to its own playlist and also hHeap
                Song songAdd = null;
                // insertion it to minHeaps
                if (!playlistRForRemoval.isEmpty()) {
                    songAdd = playlistRForRemoval.deleteMaxRRemoval(allSongs);
                    playlistsNotR[songAdd.getPlaylistID()].deleteMaxRPlaylist(allSongs);
                    playlistNumbersR[songAdd.getPlaylistID()]++;
                    rHeap.insertR(songAdd, allSongs);
                    playlistsR[songAdd.getPlaylistID()].insertRPlaylist(songAdd, allSongs);
                    addedR = songAdd.getID();
                }
                // deleted the removed one from hMinHeap , its playlist heap
                removedR = number;
                rHeap.deleteNodeR(allSongs[number].getrHeapIndex(), allSongs);
                playlistNumbersR[playlistID]--;
                playlistsR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMinHeapIndexR(), allSongs);

                if (songAdd != null && !playlistsNotR[songAdd.getPlaylistID()].isEmpty())
                    playlistRForRemoval.insertRForRemoval(playlistsNotR[songAdd.getPlaylistID()].findMax(), allSongs);

            } else {
                Song songAdd = null;
                Song song2 = null;
                if (!playlistRForRemoval.isEmpty())
                    songAdd = playlistRForRemoval.findMax();
                if (!playlistsNotR[playlistID].isEmpty()) {
                    song2 = playlistsNotR[playlistID].findMax();
                }
                if ((songAdd == null && song2 != null)) {
                    addedR = song2.getID();
                    removedR = number;
                    playlistsNotR[playlistID].deleteMaxRPlaylist(allSongs);
                    rHeap.insertR(allSongs[song2.getID()], allSongs);
                    rHeap.deleteNodeR(allSongs[number].getrHeapIndex(), allSongs);
                    playlistsR[playlistID].insertRPlaylist(allSongs[song2.getID()], allSongs);
                    playlistsR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMinHeapIndexR(), allSongs);


                } else if ((songAdd != null && song2 == null)) {
                    addedR = songAdd.getID();
                    removedR = number;
                    // added one
                    rHeap.insertR(allSongs[songAdd.getID()], allSongs);
                    playlistsR[allSongs[songAdd.getID()].getPlaylistID()].insertRPlaylist(allSongs[songAdd.getID()], allSongs);
                    playlistNumbersR[allSongs[songAdd.getID()].getPlaylistID()]++;
                    playlistRForRemoval.deleteMaxRRemoval(allSongs);
                    playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].deleteMaxRPlaylist(allSongs);
                    if (!playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].isEmpty())
                        playlistRForRemoval.insertRForRemoval(playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].findMax(), allSongs);
                    // removed one
                    rHeap.deleteNodeR(allSongs[number].getrHeapIndex(), allSongs);
                    playlistsR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMinHeapIndexR(), allSongs);
                    playlistNumbersR[playlistID]--;

                    // if there is a limitation now for added one
                    if (playlistNumbersR[allSongs[songAdd.getID()].getPlaylistID()] == limit && !playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].isEmpty()) {
                        playlistRForRemoval.deleteNodeRForRemoval(playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].findMax().getMaxHeapIndexR(), allSongs);
                    }

                    // if there is no limit condition now for this
                    if (playlistNumbersR[playlistID] == limit - 1 && !playlistsNotR[playlistID].isEmpty()) {
                        playlistRForRemoval.insertRForRemoval(allSongs[playlistsNotR[playlistID].findMax().getID()], allSongs);
                    }

                } else if (songAdd == null && song2 == null) {
                    removedR = number;
                    rHeap.deleteNodeR(allSongs[number].getrHeapIndex(), allSongs);
                    playlistsR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMinHeapIndexR(), allSongs);
                    playlistNumbersR[playlistID]--;
                } else if (songComparatorR.compare(song2, songAdd) > 0) {
                    addedR = songAdd.getID();
                    removedR = number;
                    // added one
                    rHeap.insertR(allSongs[songAdd.getID()], allSongs);
                    playlistsR[allSongs[songAdd.getID()].getPlaylistID()].insertRPlaylist(allSongs[songAdd.getID()], allSongs);
                    playlistNumbersR[allSongs[songAdd.getID()].getPlaylistID()]++;
                    playlistRForRemoval.deleteMaxRRemoval(allSongs);
                    playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].deleteMaxRPlaylist(allSongs);
                    if (!playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].isEmpty())
                        playlistRForRemoval.insertRForRemoval(playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].findMax(), allSongs);

                    // if there is a limitation now for added one
                    if (playlistNumbersR[allSongs[songAdd.getID()].getPlaylistID()] == limit && !playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].isEmpty()) {
                        playlistRForRemoval.deleteNodeRForRemoval(playlistsNotR[allSongs[songAdd.getID()].getPlaylistID()].findMax().getMaxHeapIndexR(), allSongs);
                    }

                    // removed one
                    rHeap.deleteNodeR(allSongs[number].getrHeapIndex(), allSongs);
                    playlistsR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMinHeapIndexR(), allSongs);
                    playlistNumbersR[playlistID]--;

                    // if there is no limit condition now for this
                    if (playlistNumbersR[playlistID] == limit - 1 && !playlistsNotR[playlistID].isEmpty()) {
                        playlistRForRemoval.insertRForRemoval(allSongs[playlistsNotR[playlistID].findMax().getID()], allSongs);
                    }

                } else if (songComparatorR.compare(songAdd, song2) > 0) {
                    addedR = song2.getID();
                    removedR = number;
                    playlistsNotR[playlistID].deleteMaxRPlaylist(allSongs);
                    rHeap.deleteNodeR(allSongs[number].getrHeapIndex(), allSongs);
                    rHeap.insertR(allSongs[song2.getID()], allSongs);
                    playlistsR[playlistID].insertRPlaylist(allSongs[song2.getID()], allSongs);
                }
            }

        } else {
            if (playlistsNotR[playlistID].findMax().getID() == number && playlistNumbersR[playlistID] != limit) {
                playlistRForRemoval.deleteNodeRForRemoval(allSongs[number].getMaxHeapIndexR(), allSongs);
                playlistsNotR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMaxHeapIndexR(), allSongs);
                if (!playlistsNotR[playlistID].isEmpty())
                    playlistRForRemoval.insertRForRemoval(playlistsNotR[playlistID].findMax(), allSongs);
            } else {
                playlistsNotR[playlistID].deleteNodeRForPlaylist(allSongs[number].getPlaylistMaxHeapIndexR(), allSongs);
            }
        }
        if (allSongs[number].getbHeapIndex() != 0) {
            if (playlistNumbersB[allSongs[number].getPlaylistID()] != limit) {
                // which one added has to be selected from  playlistHForRemoval, and then it should be deleted from also its own playlist
                // it should be added to its own playlist and also hHeap
                Song songAdd = null;
                // insertion it to minHeaps
                if (!playlistBForRemoval.isEmpty()) {
                    songAdd = playlistBForRemoval.deleteMaxBRemoval(allSongs);
                    playlistsNotB[songAdd.getPlaylistID()].deleteMaxBPlaylist(allSongs);
                    playlistNumbersB[songAdd.getPlaylistID()]++;
                    bHeap.insertB(songAdd, allSongs);
                    playlistsB[songAdd.getPlaylistID()].insertBPlaylist(songAdd, allSongs);
                    addedB = songAdd.getID();
                }
                // deleted the removed one from hMinHeap , its playlist heap
                removedB = number;
                bHeap.deleteNodeB(allSongs[number].getbHeapIndex(), allSongs);
                playlistNumbersB[playlistID]--;
                playlistsB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMinHeapIndexB(), allSongs);

                if (songAdd != null && !playlistsNotB[songAdd.getPlaylistID()].isEmpty())
                    playlistBForRemoval.insertBForRemoval(playlistsNotB[songAdd.getPlaylistID()].findMax(), allSongs);
            } else {
                Song songAdd = null;
                Song song2 = null;
                if (!playlistBForRemoval.isEmpty())
                    songAdd = playlistBForRemoval.findMax();
                if (!playlistsNotB[playlistID].isEmpty()) {
                    song2 = playlistsNotB[playlistID].findMax();
                }
                if ((songAdd == null && song2 != null)) {
                    addedB = song2.getID();
                    removedB = number;
                    playlistsNotB[playlistID].deleteMaxBPlaylist(allSongs);
                    bHeap.insertB(allSongs[song2.getID()], allSongs);
                    bHeap.deleteNodeB(allSongs[number].getbHeapIndex(), allSongs);
                    playlistsB[playlistID].insertBPlaylist(allSongs[song2.getID()], allSongs);
                    playlistsB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMinHeapIndexB(), allSongs);


                } else if ((songAdd != null && song2 == null)) {
                    addedB = songAdd.getID();
                    removedB = number;
                    // added one
                    bHeap.insertB(allSongs[songAdd.getID()], allSongs);
                    playlistsB[allSongs[songAdd.getID()].getPlaylistID()].insertBPlaylist(allSongs[songAdd.getID()], allSongs);
                    playlistNumbersB[allSongs[songAdd.getID()].getPlaylistID()]++;
                    playlistBForRemoval.deleteMaxBRemoval(allSongs);
                    playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].deleteMaxBPlaylist(allSongs);
                    if (!playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].isEmpty())
                        playlistBForRemoval.insertBForRemoval(playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].findMax(), allSongs);
                    // removed one
                    bHeap.deleteNodeB(allSongs[number].getbHeapIndex(), allSongs);
                    playlistsB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMinHeapIndexB(), allSongs);
                    playlistNumbersB[playlistID]--;

                    if (playlistNumbersB[allSongs[songAdd.getID()].getPlaylistID()] == limit && !playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].isEmpty()) {
                        playlistBForRemoval.deleteNodeBForRemoval(playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].findMax().getMaxHeapIndexB(), allSongs);
                    }

                    // if there is no limit condition now for this
                    if (playlistNumbersB[playlistID] == limit - 1 && !playlistsNotB[playlistID].isEmpty()) {
                        playlistBForRemoval.insertBForRemoval(allSongs[playlistsNotB[playlistID].findMax().getID()], allSongs);
                    }

                } else if (songAdd == null && song2 == null) {
                    removedB = number;
                    bHeap.deleteNodeB(allSongs[number].getbHeapIndex(), allSongs);
                    playlistsB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMinHeapIndexB(), allSongs);
                    playlistNumbersB[playlistID]--;
                } else if (songComparatorB.compare(song2, songAdd) > 0) {
                    addedB = songAdd.getID();
                    removedB = number;
                    // added one
                    bHeap.insertB(allSongs[songAdd.getID()], allSongs);
                    playlistsB[allSongs[songAdd.getID()].getPlaylistID()].insertBPlaylist(allSongs[songAdd.getID()], allSongs);
                    playlistNumbersB[allSongs[songAdd.getID()].getPlaylistID()]++;
                    playlistBForRemoval.deleteMaxBRemoval(allSongs);
                    playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].deleteMaxBPlaylist(allSongs);
                    if (!playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].isEmpty())
                        playlistBForRemoval.insertBForRemoval(playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].findMax(), allSongs);

                    if (playlistNumbersB[allSongs[songAdd.getID()].getPlaylistID()] == limit && !playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].isEmpty()) {
                        playlistBForRemoval.deleteNodeBForRemoval(playlistsNotB[allSongs[songAdd.getID()].getPlaylistID()].findMax().getMaxHeapIndexB(), allSongs);
                    }
                    // removed one
                    bHeap.deleteNodeB(allSongs[number].getbHeapIndex(), allSongs);
                    playlistsB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMinHeapIndexB(), allSongs);
                    playlistNumbersB[playlistID]--;

                    // if there is no limit condition now for this
                    if (playlistNumbersB[playlistID] == limit - 1 && !playlistsNotB[playlistID].isEmpty()) {
                        playlistBForRemoval.insertBForRemoval(allSongs[playlistsNotB[playlistID].findMax().getID()], allSongs);
                    }


                } else if (songComparatorB.compare(songAdd, song2) > 0) {
                    addedB = song2.getID();
                    removedB = number;
                    playlistsNotB[playlistID].deleteMaxBPlaylist(allSongs);
                    bHeap.insertB(allSongs[song2.getID()], allSongs);
                    bHeap.deleteNodeB(allSongs[number].getbHeapIndex(), allSongs);
                    playlistsB[playlistID].insertBPlaylist(allSongs[song2.getID()], allSongs);
                }
            }
        } else {
            if (playlistsNotB[playlistID].findMax().getID() == number && playlistNumbersB[playlistID] != limit) {
                playlistBForRemoval.deleteNodeBForRemoval(allSongs[number].getMaxHeapIndexB(), allSongs);
                playlistsNotB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMaxHeapIndexB(), allSongs);
                if (!playlistsNotB[playlistID].isEmpty())
                    playlistBForRemoval.insertBForRemoval(playlistsNotB[playlistID].findMax(), allSongs);
            } else {
                playlistsNotB[playlistID].deleteNodeBForPlaylist(allSongs[number].getPlaylistMaxHeapIndexB(), allSongs);
            }
        }
        System.out.println(addedH + " " + addedR + " " + addedB);
        System.out.println(removedH + " " + removedR + " " + removedB);
    }

    public void add(int number, int playListID) {
        allSongs[number].setPlaylistID(playListID);
        int addedH = 0;
        int addedR = 0;
        int addedB = 0;
        int removedH = 0;
        int removedR = 0;
        int removedB = 0;
        // h heap:
        if (hHeap.getCurrentSize() < chNumber && playlistNumbersH[playListID] < limit) {
            // there is no problem about limit and chNumber
            // just add it to hHeap, playlistNumbersH increase by one
            // also insert it to its playlists minHeap
            addedH = number;
            hHeap.insertH(allSongs[number], allSongs);
            playlistNumbersH[playListID]++;
            playlistsH[playListID].insertHPlaylist(allSongs[number], allSongs);
            // if now there is a limit condition for this playlist, delete its max from removal list
            if (playlistNumbersH[playListID] == limit && !playlistsNotH[playListID].isEmpty()) {
                playlistHForRemoval.deleteNodeHForRemoval(playlistsNotH[playListID].findMax().getMaxHeapIndexH(), allSongs);
            }
        } else {
            if (playlistNumbersH[playListID] < limit) {
                Song song = hHeap.findMin();
                if (songComparatorH.compare(song, allSongs[number]) > 0) {
                    removedH = song.getID();
                    addedH = allSongs[number].getID();
                    // delete removed one part
                    playlistNumbersH[song.getPlaylistID()]--;
                    hHeap.deleteMinH(allSongs);
                    playlistsH[song.getPlaylistID()].deleteMinHPlaylist(allSongs);

                    // if now it is not in the limit situation
                    if (playlistNumbersH[song.getPlaylistID()] == limit - 1) {
                        playlistsNotH[song.getPlaylistID()].insertHForPlaylist(song, allSongs);
                        playlistHForRemoval.insertHForRemoval(playlistsNotH[song.getPlaylistID()].findMax(), allSongs);
                    } else {
                        // special**
                        Song song1 = playlistsNotH[allSongs[removedH].getPlaylistID()].findMax();
                        boolean change = false;
                        if (song1 != null && songComparatorH.compare(song1, allSongs[removedH]) > 0) {
                            playlistHForRemoval.deleteNodeHForRemoval(song1.getMaxHeapIndexH(), allSongs);
                            change = true;
                        }
                        playlistsNotH[song.getPlaylistID()].insertHForPlaylist(song, allSongs);
                        if (change || song1 == null) {
                            playlistHForRemoval.insertHForRemoval(allSongs[removedH], allSongs);
                        }
                    }


                    // insert new one
                    playlistNumbersH[playListID]++;
                    hHeap.insertH(allSongs[number], allSongs);
                    playlistsH[playListID].insertHPlaylist(allSongs[number], allSongs);

                    // if there is a limit condition now for us
                    if (playlistNumbersH[playListID] == limit && playlistsNotH[playListID].getCurrentSize() != 0) {
                        playlistHForRemoval.deleteNodeHForRemoval(playlistsNotH[playListID].findMax().getMaxHeapIndexH(), allSongs);
                    }


                } else {
                    // special**
                    // new song added to playlists not H , and if it changes the max of its playlist, it also has to change the removal this
                    Song song1 = playlistsNotH[allSongs[number].getPlaylistID()].findMax();
                    boolean change = false;
                    if (song1 != null && songComparatorH.compare(song1, allSongs[number]) > 0) {
                        playlistHForRemoval.deleteNodeHForRemoval(song1.getMaxHeapIndexH(), allSongs);
                        change = true;
                    }
                    playlistsNotH[allSongs[number].getPlaylistID()].insertHForPlaylist(allSongs[number], allSongs);
                    if (change || song1 == null) {
                        playlistHForRemoval.insertHForRemoval(allSongs[number], allSongs);
                    }

                }
            } else {
                Song songMin = playlistsH[playListID].findMin();
                if (songMin != null && songComparatorH.compare(songMin, allSongs[number]) > 0) {
                    removedH = songMin.getID();
                    addedH = number;
                    // delete removed one
                    hHeap.deleteNodeH(allSongs[removedH].gethHeapIndex(), allSongs);
                    playlistsH[playListID].deleteMinHPlaylist(allSongs);
                    playlistNumbersH[songMin.getPlaylistID()]--;

                    //special**
                    playlistsNotH[songMin.getPlaylistID()].insertHForPlaylist(songMin, allSongs);
                    // insert new one
                    playlistNumbersH[playListID]++;
                    hHeap.insertH(allSongs[number], allSongs);
                    playlistsH[playListID].insertHPlaylist(allSongs[number], allSongs);

                } else {
                    // special**
                    playlistsNotH[allSongs[number].getPlaylistID()].insertHForPlaylist(allSongs[number], allSongs);
                }
            }
        }
        // rHeap
        if (rHeap.getCurrentSize() < crNumber && playlistNumbersR[playListID] < limit) {
            // there is no problem about limit and crNumber
            // just add it to hHeap, playlistNumbersR increase by one
            // also insert it to its playlists minHeap
            addedR = number;
            rHeap.insertR(allSongs[number], allSongs);
            playlistNumbersR[playListID]++;
            playlistsR[playListID].insertRPlaylist(allSongs[number], allSongs);

            // if now there is a limit condition for this playlist, delete its max from removal list
            if (playlistNumbersR[playListID] == limit && !playlistsNotR[playListID].isEmpty()) {
                playlistRForRemoval.deleteNodeRForRemoval(playlistsNotR[playListID].findMax().getMaxHeapIndexR(), allSongs);
            }
        } else {
            if (playlistNumbersR[playListID] < limit) {
                Song song = rHeap.findMin();
                if (songComparatorR.compare(song, allSongs[number]) > 0) {
                    removedR = song.getID();
                    addedR = allSongs[number].getID();

                    // delete removed one
                    playlistNumbersR[song.getPlaylistID()]--;
                    rHeap.deleteMinR(allSongs);
                    playlistsR[song.getPlaylistID()].deleteMinRPlaylist(allSongs);

                    // if now it is not in the limit situation
                    if (playlistNumbersR[song.getPlaylistID()] == limit - 1) {
                        playlistsNotR[song.getPlaylistID()].insertRForPlaylist(song, allSongs);
                        playlistRForRemoval.insertRForRemoval(playlistsNotR[song.getPlaylistID()].findMax(), allSongs);
                    } else {
                        // special**
                        Song song1 = playlistsNotR[allSongs[removedR].getPlaylistID()].findMax();
                        boolean change = false;
                        if (song1 != null && songComparatorR.compare(song1, allSongs[removedR]) > 0) {
                            playlistRForRemoval.deleteNodeRForRemoval(song1.getMaxHeapIndexR(), allSongs);
                            change = true;
                        }
                        playlistsNotR[song.getPlaylistID()].insertRForPlaylist(song, allSongs);
                        if (change || song1 == null) {
                            playlistRForRemoval.insertRForRemoval(allSongs[removedR], allSongs);
                        }
                    }
                    // insert new one
                    playlistNumbersR[playListID]++;
                    rHeap.insertR(allSongs[number], allSongs);
                    playlistsR[playListID].insertRPlaylist(allSongs[number], allSongs);
                    // if there is a limit condition now for us
                    if (playlistNumbersR[playListID] == limit && playlistsNotR[playListID].getCurrentSize() != 0) {
                        playlistRForRemoval.deleteNodeRForRemoval(playlistsNotR[playListID].findMax().getMaxHeapIndexR(), allSongs);
                    }

                } else {
                    //special**
                    // new song added to playlists not H , and if it changes the max of its playlist, it also has to change the removal this
                    Song song1 = playlistsNotR[allSongs[number].getPlaylistID()].findMax();
                    boolean change = false;
                    if (song1 != null && songComparatorR.compare(song1, allSongs[number]) > 0) {
                        playlistRForRemoval.deleteNodeRForRemoval(song1.getMaxHeapIndexR(), allSongs);
                        change = true;
                    }
                    playlistsNotR[allSongs[number].getPlaylistID()].insertRForPlaylist(allSongs[number], allSongs);
                    if (change || song1 == null) {
                        playlistRForRemoval.insertRForRemoval(allSongs[number], allSongs);
                    }

                }
            } else {
                Song songMin = playlistsR[playListID].findMin();
                if (songMin != null && songComparatorR.compare(songMin, allSongs[number]) > 0) {
                    removedR = songMin.getID();
                    addedR = number;

                    // delete removed one
                    rHeap.deleteNodeR(allSongs[removedR].getrHeapIndex(), allSongs);
                    playlistNumbersR[songMin.getPlaylistID()]--;
                    playlistsR[playListID].deleteMinRPlaylist(allSongs);

                    //special**
                    playlistsNotR[songMin.getPlaylistID()].insertRForPlaylist(songMin, allSongs);

                    // insert new one
                    playlistNumbersR[playListID]++;
                    rHeap.insertR(allSongs[number], allSongs);
                    playlistsR[playListID].insertRPlaylist(allSongs[number], allSongs);

                } else {
                    // special**
                    playlistsNotR[allSongs[number].getPlaylistID()].insertRForPlaylist(allSongs[number], allSongs);
                }
            }
        }

        //bHeap
        if (bHeap.getCurrentSize() < cbNumber && playlistNumbersB[playListID] < limit) {
            // there is no problem about limit and cbNumber
            // just add it to hHeap, playlistNumbersR increase by one
            // also insert it to its playlists minHeap
            addedB = number;
            bHeap.insertB(allSongs[number], allSongs);
            playlistNumbersB[playListID]++;
            playlistsB[playListID].insertBPlaylist(allSongs[number], allSongs);

            // if now there is a limit condition for this playlist, delete its max from removal list
            if (playlistNumbersB[playListID] == limit && !playlistsNotB[playListID].isEmpty()) {
                playlistBForRemoval.deleteNodeBForRemoval(playlistsNotB[playListID].findMax().getMaxHeapIndexB(), allSongs);
            }
        } else {
            if (playlistNumbersB[playListID] < limit) {
                Song song = bHeap.findMin();
                if (songComparatorB.compare(song, allSongs[number]) > 0) {
                    removedB = song.getID();
                    addedB = allSongs[number].getID();

                    // delete removed one
                    playlistNumbersB[song.getPlaylistID()]--;
                    bHeap.deleteMinB(allSongs);
                    playlistsB[song.getPlaylistID()].deleteMinBPlaylist(allSongs);


                    // if now it is not in the limit situation
                    if (playlistNumbersB[song.getPlaylistID()] == limit - 1) {
                        playlistsNotB[song.getPlaylistID()].insertBForPlaylist(song, allSongs);
                        playlistBForRemoval.insertBForRemoval(playlistsNotB[song.getPlaylistID()].findMax(), allSongs);
                    } else {
                        // special**
                        Song song1 = playlistsNotB[allSongs[removedB].getPlaylistID()].findMax();
                        boolean change = false;
                        if (song1 != null && songComparatorB.compare(song1, allSongs[removedB]) > 0) {
                            playlistBForRemoval.deleteNodeBForRemoval(song1.getMaxHeapIndexB(), allSongs);
                            change = true;
                        }
                        playlistsNotB[song.getPlaylistID()].insertBForPlaylist(allSongs[song.getID()], allSongs);
                        if (change || song1 == null) {
                            playlistBForRemoval.insertBForRemoval(allSongs[removedB], allSongs);
                        }
                    }

                    // insert new one
                    playlistNumbersB[playListID]++;
                    playlistsB[playListID].insertBPlaylist(allSongs[number], allSongs);
                    bHeap.insertB(allSongs[number], allSongs);

                    // if there is a limit condition now for us
                    if (playlistNumbersB[playListID] == limit && playlistsNotB[playListID].getCurrentSize() != 0) {
                        playlistBForRemoval.deleteNodeBForRemoval(playlistsNotB[playListID].findMax().getMaxHeapIndexB(), allSongs);
                    }

                } else {
                    //special**
                    // new song added to playlists not H , and if it changes the max of its playlist, it also has to change the removal this
                    Song song1 = playlistsNotB[allSongs[number].getPlaylistID()].findMax();
                    boolean change = false;
                    if (song1 != null && songComparatorB.compare(song1, allSongs[number]) > 0) {
                        playlistBForRemoval.deleteNodeBForRemoval(song1.getMaxHeapIndexB(), allSongs);
                        change = true;
                    }
                    playlistsNotB[allSongs[number].getPlaylistID()].insertBForPlaylist(allSongs[number], allSongs);
                    if (change || song1 == null) {
                        playlistBForRemoval.insertBForRemoval(allSongs[number], allSongs);
                    }
                }
            } else {
                Song songMin = playlistsB[playListID].findMin();
                if (songMin != null && songComparatorB.compare(songMin, allSongs[number]) > 0) {
                    removedB = songMin.getID();
                    addedB = number;


                    // delete removed one
                    playlistNumbersB[songMin.getPlaylistID()]--;
                    playlistsB[playListID].deleteMinBPlaylist(allSongs);
                    bHeap.deleteNodeB(allSongs[removedB].getbHeapIndex(), allSongs);

                    //special**
                    playlistsNotB[songMin.getPlaylistID()].insertBForPlaylist(songMin, allSongs);

                    // insert new one
                    playlistNumbersB[playListID]++;
                    bHeap.insertB(allSongs[number], allSongs);
                    playlistsB[playListID].insertBPlaylist(allSongs[number], allSongs);

                } else {
                    // special**
                    playlistsNotB[allSongs[number].getPlaylistID()].insertBForPlaylist(allSongs[number], allSongs);
                }
            }
        }
        System.out.println(addedH + " " + addedR + " " + addedB);
        System.out.println(removedH + " " + removedR + " " + removedB);
    }
}