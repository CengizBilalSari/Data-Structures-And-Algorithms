/**
 * This Member class is mainly created for keeping employees as member in the hash table.
 * All members have features like name, promotion, duty...
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 24.11.2023
 */
public class Member {

    // data fields
    private String name;
    private int promotion;
    private String duty;

    //getters
    public String getDuty() {
        return duty;
    }
    public String getName() {
        return name;
    }
    public int getPromotion() {
        return promotion;
    }

    //setters
    public void setPromotion(int promotion) {
        this.promotion += promotion;
    }
    public void setDuty(String duty) {
        this.duty = duty;
    }


    // constructor of member class
    public Member(String name, String duty) {
        this.name = name;
        this.duty = duty;
        this.promotion = 0;
    }
}
