/**
 * This Song class is mainly created keeping features of songs in a structure which is called Song.
 * The types of scores, indexes etc. is kept in songs.
 * @author Cengiz Bilal Sarı
 * @since Date: 10.12.2023
 */

public class Song {

    //Data fields

    // booleans for ask
    private boolean addedMaxHeap;
    private int ID;
    private int playlistID;
    private String name;
    private int playCount;
    private int heartacheScore;
    private int roadTripScore;
    private int blissfulScore;
    private int hHeapIndex;
    private int rHeapIndex;
    private int bHeapIndex;
    private int playlistMinHeapIndexH;
    private int playlistMinHeapIndexR;
    private int playlistMinHeapIndexB;
    private int playlistMaxHeapIndexH;
    private int playlistMaxHeapIndexR;
    private int playlistMaxHeapIndexB;
    private int maxHeapIndexH;
    private int maxHeapIndexR;
    private int maxHeapIndexB;


    // constructor
    public Song(int ID, String name, int playCount, int heartacheScore, int roadTripScore, int blissfulScore) {
        this.addedMaxHeap= false;
        this.ID = ID;
        this.name = name;
        this.playCount = playCount;
        this.heartacheScore = heartacheScore;
        this.roadTripScore = roadTripScore;
        this.blissfulScore = blissfulScore;
        this.playlistID = 0;
        this.hHeapIndex = 0;
        this.rHeapIndex = 0;
        this.bHeapIndex = 0;
        this.playlistMinHeapIndexH = 0;
        this.playlistMinHeapIndexR = 0;
        this.playlistMinHeapIndexB = 0;

        this.playlistMaxHeapIndexH = 0;
        this.playlistMaxHeapIndexR = 0;
        this.playlistMaxHeapIndexB = 0;

        this.maxHeapIndexH = 0;
        this.maxHeapIndexR = 0;
        this.maxHeapIndexB = 0;
    }

    // getters and setters

    public int getPlayCount() {
        return playCount;
    }

    public boolean getAddedMaxHeap() {
        return addedMaxHeap;
    }

    public void setAddedMaxHeap(boolean addedMaxHeap) {
        this.addedMaxHeap = addedMaxHeap;
    }

    public int getID() {
        return ID;
    }
    public int getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }
    public String getName() {
        return name;
    }
    public int getHeartacheScore() {
        return heartacheScore;
    }
    public int getRoadTripScore() {
        return roadTripScore;
    }
    public int getBlissfulScore() {
        return blissfulScore;
    }
    public int gethHeapIndex() {
        return hHeapIndex;
    }
    public void sethHeapIndex(int hHeapIndex) {
        this.hHeapIndex = hHeapIndex;
    }
    public int getrHeapIndex() {
        return rHeapIndex;
    }
    public void setrHeapIndex(int rHeapIndex) {
        this.rHeapIndex = rHeapIndex;
    }
    public int getbHeapIndex() {
        return bHeapIndex;
    }
    public void setbHeapIndex(int bHeapIndex) {
        this.bHeapIndex = bHeapIndex;
    }
    public int getPlaylistMinHeapIndexH() {
        return playlistMinHeapIndexH;
    }
    public void setPlaylistMinHeapIndexH(int playlistMinHeapIndexH) {
        this.playlistMinHeapIndexH = playlistMinHeapIndexH;
    }
    public int getPlaylistMinHeapIndexR() {
        return playlistMinHeapIndexR;
    }
    public void setPlaylistMinHeapIndexR(int playlistMinHeapIndexR) {
        this.playlistMinHeapIndexR = playlistMinHeapIndexR;
    }
    public int getPlaylistMinHeapIndexB() {
        return playlistMinHeapIndexB;
    }
    public void setPlaylistMinHeapIndexB(int playlistMinHeapIndexB) {
        this.playlistMinHeapIndexB = playlistMinHeapIndexB;
    }
    public int getPlaylistMaxHeapIndexH() {
        return playlistMaxHeapIndexH;
    }
    public void setPlaylistMaxHeapIndexH(int playlistMaxHeapIndexH) {
        this.playlistMaxHeapIndexH = playlistMaxHeapIndexH;
    }
    public int getPlaylistMaxHeapIndexR() {
        return playlistMaxHeapIndexR;
    }
    public void setPlaylistMaxHeapIndexR(int playlistMaxHeapIndexR) {
        this.playlistMaxHeapIndexR = playlistMaxHeapIndexR;
    }
    public int getPlaylistMaxHeapIndexB() {
        return playlistMaxHeapIndexB;
    }
    public void setPlaylistMaxHeapIndexB(int playlistMaxHeapIndexB) {
        this.playlistMaxHeapIndexB = playlistMaxHeapIndexB;
    }
    public int getMaxHeapIndexH() {
        return maxHeapIndexH;
    }
    public void setMaxHeapIndexH(int maxHeapIndexH) {
        this.maxHeapIndexH = maxHeapIndexH;
    }
    public int getMaxHeapIndexR() {
        return maxHeapIndexR;
    }
    public void setMaxHeapIndexR(int maxHeapIndexR) {
        this.maxHeapIndexR = maxHeapIndexR;
    }
    public int getMaxHeapIndexB() {
        return maxHeapIndexB;
    }
    public void setMaxHeapIndexB(int maxHeapIndexB) {
        this.maxHeapIndexB = maxHeapIndexB;
    }
}
