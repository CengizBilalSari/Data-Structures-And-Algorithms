import java.util.*;
/**
 * This Branch class is mainly created keeping members in a branch and every branch there are lots of data fields to keep track of structure correctly.
 * @author Cengiz Bilal Sarı
 * @since Date: 24.11.2023
 */
public class Branch {
    public Queue<Member> cooksToManager;

    public Member increaseCashier;
    public Member dismissCashier;
    public Member dismissCourier;
    public Member dismissCook;
    public OwnHashTable<String, Member> memberHashtable;
    public int numberOfManager;
    public int numberOfCook;
    public int numberOfCourier;
    public int numberOfCashier;
    private String manager;
    private int totalBonus;
    private int monthlyBonus;
    public boolean managerDismissal;


    //getters
    public int getTotalBonus() {
        return totalBonus;
    }

    public int getMonthlyBonus() {
        return monthlyBonus;
    }

    public String getManager() {
        return manager;
    }

    //setters
    public void setTotalBonus(int totalBonus) {
        this.totalBonus += totalBonus;
    }

    public void setMonthlyBonusto0() {
        this.monthlyBonus = 0;
    }

    public void setMonthlyBonus1(int monthlyBonus) {
        this.monthlyBonus += monthlyBonus;

    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Branch() {
        this.manager = null;
        this.memberHashtable = new OwnHashTable<>();
        this.numberOfCook = 0;
        this.numberOfCashier = 0;
        this.numberOfCourier = 0;
        this.numberOfManager = 0;
        this.monthlyBonus = 0;
        this.totalBonus = 0;
        this.cooksToManager = new LinkedList<>();
        this.managerDismissal = false;
        this.increaseCashier = null;
    }

    // when the user wants to add new employee, this method increase the number of this job.
    public void whatJob(String string) {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("MANAGER", "COOK", "COURIER", "CASHIER"));
        int index = arrayList.indexOf(string);
        if (index == 0) {
            numberOfManager += 1;
        } else if (index == 1) {
            numberOfCook += 1;
        } else if (index == 2) {
            numberOfCourier += 1;
        } else {
            numberOfCashier += 1;
        }
    }
}