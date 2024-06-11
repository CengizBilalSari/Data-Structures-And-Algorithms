import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
 * This CompanyStructure class is mainly created for building company structure correctly.
 *  The main hashtable is String-Branch branches hashtable. It keeps the  all branches inside of it.
 *  There are methods to do necessary alterations.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 24.11.2023
 */
public class CompanyStructure {

    OwnHashTable<String, Branch> branches;

    public CompanyStructure() {
        this.branches = new OwnHashTable<>();
    }

    public void takingSecondFile(String fileName) throws FileNotFoundException {
        //this is the main method of tbe project, it utilizes from helper methods. It reads the second file and give the output.

        // Months and commands lists are to check lines and understand what is the command or if there is new month.
        ArrayList<String> months = new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        ArrayList<String> commands = new ArrayList<>(Arrays.asList("ADD", "LEAVE", "PERFORMANCE_UPDATE", "PRINT_MANAGER", "PRINT_MONTHLY_BONUSES", "PRINT_OVERALL_BONUSES"));

        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);

        // main while loop to read file
        while (inputFile.hasNext()) {
            String[] line = inputFile.nextLine().split(",\\s*|:\\s*");


            if (months.contains(line[0])) {
                // make every monthly bonuses zero here
                for (OwnHashTable.Entry<String, Branch> entry : branches.getTable()) {
                    if (entry != null && !(entry instanceof OwnHashTable.DeletedEntry<String, Branch>)) {
                        entry.getValue().setMonthlyBonusto0();
                    }
                }
                continue;
            }
            if (commands.indexOf(line[0]) == 0) {
                add(line);
            } else if (commands.indexOf(line[0]) == 1) {
                leaving(line);
            } else if (commands.indexOf(line[0]) == 2) {
                performanceUpdate(line);
            } else if (commands.indexOf(line[0]) == 3) {
                printManager(line);
            } else if (commands.indexOf(line[0]) == 4) {
                System.out.println("Total bonuses for the " + line[2] + " branch this month are: " + branches.get(line[1] + line[2]).getMonthlyBonus());
                ;
            } else if (commands.indexOf(line[0]) == 5) {
                System.out.println("Total bonuses for the " + line[2] + " branch are: " + branches.get(line[1] + line[2]).getTotalBonus());
            }
        }
    }
    public void takingFirstFile(String fileName) throws FileNotFoundException {
        // it takes first file and put the employees into hash table
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNext()) {
            String[] line = inputFile.nextLine().split(",\\s*");
            if (!branches.containsKey((line[0] + line[1]))) {
                branches.put(line[0] + line[1], new Branch());
            }
            branches.get(line[0] + line[1]).memberHashtable.put(line[2], new Member(line[2], line[3]));
            branches.get(line[0] + line[1]).whatJob(line[3]);
            if (line[3].equals("MANAGER")) {
                branches.get(line[0] + line[1]).setManager(line[2]);
            }
        }
    }


    // add method is for basically adding new employees into branches.
    public void add(String[] strings) {
        String access = strings[1] + strings[2];
        Member member = new Member(strings[3], strings[4]);

        // if the employee is already in the branch do not add it again.
        if (branches.get(access).memberHashtable.get(strings[3]) != null) {
            System.out.println("Existing employee cannot be added again.");
            return;
        }
        branches.get(access).memberHashtable.put(strings[3], member);
        branches.get(access).whatJob(strings[4]);

        if ((strings[4].equals("COURIER") && branches.get(access).dismissCourier != null)) {
            System.out.println(branches.get(access).dismissCourier.getName() + " is dismissed from branch: " + strings[2] + ".");
            branches.get(access).memberHashtable.remove(branches.get(access).dismissCourier.getName());
            branches.get(access).dismissCourier = null;
            branches.get(access).numberOfCourier -= 1;
        }
        if (strings[4].equals("COOK")) {
            if ((!branches.get(access).cooksToManager.isEmpty()) && (branches.get(access).managerDismissal)) {
                String manager = branches.get(access).getManager();
                System.out.println(branches.get(access).getManager() + " is dismissed from branch: " + strings[2] + ".");
                branches.get(access).memberHashtable.remove(manager);
                Member member2 = branches.get(access).cooksToManager.poll();
                branches.get(access).memberHashtable.get(member2.getName()).setDuty("MANAGER");
                branches.get(access).memberHashtable.get(member2.getName()).setPromotion(-10);
                branches.get(access).setManager(member2.getName());
                System.out.println(branches.get(access).getManager() + " is promoted from Cook to Manager.");
                branches.get(access).numberOfCook -= 1;
                branches.get(access).managerDismissal = false;
            }
            if (branches.get(access).dismissCook != null) {
                System.out.println(branches.get(access).dismissCook.getName() + " is dismissed from branch: " + strings[2] + ".");
                branches.get(access).memberHashtable.remove(branches.get(access).dismissCook.getName());
                branches.get(access).dismissCook = null;
                branches.get(access).numberOfCook -= 1;
            }
        } else if (strings[4].equals("CASHIER")) {
            if (!(branches.get(access).increaseCashier == null)) {
                branches.get(access).memberHashtable.get(branches.get(access).increaseCashier.getName()).setDuty("COOK");
                branches.get(access).memberHashtable.get(branches.get(access).increaseCashier.getName()).setPromotion(-3);
                System.out.println(branches.get(access).memberHashtable.get(branches.get(access).increaseCashier.getName()).getName() + " is promoted from Cashier to Cook.");
                branches.get(access).numberOfCashier -= 1;
                branches.get(access).numberOfCook += 1;
                if (branches.get(access).increaseCashier.getPromotion() > 10) {
                    String reference2 = branches.get(access).increaseCashier.getName();
                    if (branches.get(access).numberOfCook > 1 && (branches.get(access).managerDismissal)) {
                        //System.out.println(branches.get(reference1).numberOfCook);
                        //System.out.println(branches.get(reference1).getManager());
                        String manager = branches.get(access).getManager();
                        branches.get(access).memberHashtable.remove(manager);
                        branches.get(access).memberHashtable.get(reference2).setDuty("MANAGER");
                        branches.get(access).memberHashtable.get(reference2).setPromotion(-10);
                        branches.get(access).setManager(reference2);
                        branches.get(access).numberOfCook -= 1;
                        branches.get(access).managerDismissal = false;
                    } else {
                        branches.get(access).cooksToManager.offer(branches.get(access).memberHashtable.get(reference2));
                    }
                }
                branches.get(access).increaseCashier = null;
                if ((branches.get(access).numberOfCook > 1 && branches.get(access).managerDismissal) && (branches.get(access).cooksToManager.size() > 0)) {
                    System.out.println(branches.get(access).getManager() + " is dismissed from branch: " + strings[2] + ".");
                    String reference2 = branches.get(access).getManager();
                    branches.get(access).memberHashtable.remove(reference2);
                    Member member1 = branches.get(access).cooksToManager.poll();
                    //System.out.println(member.getName());
                    branches.get(access).setManager(member1.getName());
                    branches.get(access).memberHashtable.get(member1.getName()).setPromotion(-10);
                    branches.get(access).memberHashtable.get(member1.getName()).setDuty("MANAGER");
                    branches.get(access).setManager(member1.getName());
                    System.out.println(branches.get(access).getManager() + " is promoted from Cook to Manager.");
                    branches.get(access).numberOfCook -= 1;
                    branches.get(access).managerDismissal = false;
                }
                branches.get(access).increaseCashier = null;
            }
            if (branches.get(access).dismissCashier != null) {
                System.out.println(branches.get(access).dismissCashier.getName() + " is dismissed from branch: " + strings[2] + ".");
                branches.get(access).memberHashtable.remove(branches.get(access).dismissCashier.getName());
                branches.get(access).dismissCashier = null;
                branches.get(access).numberOfCashier -= 1;
            }
        }
    }

    public void printManager(String[] strings) {
        // to print the manager of the branch
        String manager = branches.get(strings[1] + strings[2]).getManager();
        System.out.println("Manager of the " + strings[2] + " branch is " + manager + ".");
    }


    public void performanceUpdate(String[] strings) {
        String firstAccess = strings[1] + strings[2];
        // if there is no such employee do not try to update anything and return
        if (branches.get(firstAccess).memberHashtable.get(strings[3]) == null) {
            System.out.println("There is no such employee.");
            return;
        }
        String job = branches.get(firstAccess).memberHashtable.get(strings[3]).getDuty();
        int promotion = Integer.parseInt(strings[4]) / 200;
        int bonus;
        if (promotion >= 0) {
            bonus = Integer.parseInt(strings[4]) - promotion * 200;
            if (bonus < 0)
                bonus = 0;
            branches.get(firstAccess).setMonthlyBonus1(bonus);
            branches.get(firstAccess).setTotalBonus(bonus);
            branches.get(firstAccess).memberHashtable.get(strings[3]).setPromotion(promotion);
            // change the job part
            String write = changingJob(firstAccess, strings[3], job);
            if (!write.equals("")) {
                System.out.println(write + " is dismissed from branch: " + strings[2] + ".");
                System.out.println(branches.get(firstAccess).memberHashtable.get(strings[3]).getName() + " is promoted from Cook to Manager.");
            }
        } else {
            branches.get(firstAccess).memberHashtable.get(strings[3]).setPromotion(promotion);
            boolean dismissal = dismissals(firstAccess, strings[3], job);
            if (dismissal) {
                System.out.println(strings[3] + " is dismissed from branch: " + strings[2] + ".");
                if (job.equals("MANAGER")) {
                    System.out.println(branches.get(firstAccess).getManager() + " is promoted from Cook to Manager.");
                }
            }
        }
    }

    public String changingJob(String reference1, String reference2, String job) {
        // changing job is used when there is a positive performance update
        if (job.equals("CASHIER")) {
            if (branches.get(reference1).dismissCashier != null) {
                if (branches.get(reference1).dismissCashier.equals(branches.get(reference1).memberHashtable.get(reference2)) && branches.get(reference1).memberHashtable.get(reference2).getPromotion() > -5) {
                    branches.get(reference1).dismissCashier = null;
                }
            }
            if (branches.get(reference1).memberHashtable.get(reference2).getPromotion() >= 3) {
                if (branches.get(reference1).numberOfCashier > 1) {
                    branches.get(reference1).memberHashtable.get(reference2).setDuty("COOK");
                    branches.get(reference1).memberHashtable.get(reference2).setPromotion(-3);
                    branches.get(reference1).numberOfCook += 1;
                    branches.get(reference1).numberOfCashier -= 1;
                    System.out.println(branches.get(reference1).memberHashtable.get(reference2).getName() + " is promoted from Cashier to Cook.");
                } else {
                    branches.get(reference1).increaseCashier = branches.get(reference1).memberHashtable.get(reference2);
                }

                if ((branches.get(reference1).cooksToManager.size() > 0 && branches.get(reference1).managerDismissal) && (branches.get(reference1).numberOfCook > 1)) {
                    String manager = branches.get(reference1).getManager();
                    branches.get(reference1).memberHashtable.remove(manager);
                    Member member1 = branches.get(reference1).cooksToManager.poll();
                    branches.get(reference1).memberHashtable.get(member1.getName()).setDuty("MANAGER");
                    branches.get(reference1).memberHashtable.get(member1.getName()).setPromotion(-10);
                    branches.get(reference1).setManager(member1.getName());
                    branches.get(reference1).numberOfCook -= 1;
                    branches.get(reference1).managerDismissal = false;
                    return manager;
                }
            }
        } else if (job.equals("COOK")) {
            if (branches.get(reference1).dismissCook != null) {
                if (branches.get(reference1).dismissCook.equals(branches.get(reference1).memberHashtable.get(reference2)) && branches.get(reference1).memberHashtable.get(reference2).getPromotion() > -5) {
                    branches.get(reference1).dismissCook = null;
                }
            }
            if (branches.get(reference1).memberHashtable.get(reference2).getPromotion() >= 10) {
                if (branches.get(reference1).numberOfCook > 1 && (branches.get(reference1).managerDismissal)) {
                    String manager = branches.get(reference1).getManager();
                    branches.get(reference1).memberHashtable.remove(manager);
                    branches.get(reference1).memberHashtable.get(reference2).setDuty("MANAGER");
                    branches.get(reference1).memberHashtable.get(reference2).setPromotion(-10);
                    branches.get(reference1).setManager(reference2);
                    branches.get(reference1).numberOfCook -= 1;
                    branches.get(reference1).managerDismissal = false;
                    return manager;
                } else if (!branches.get(reference1).cooksToManager.contains(branches.get(reference1).memberHashtable.get(reference2))) {
                    branches.get(reference1).cooksToManager.offer(branches.get(reference1).memberHashtable.get(reference2));
                }

            }
        } else if (job.equals("COURIER")) {
            if (branches.get(reference1).dismissCourier != null) {
                if (branches.get(reference1).dismissCourier.equals(branches.get(reference1).memberHashtable.get(reference2)) && branches.get(reference1).memberHashtable.get(reference2).getPromotion() > -5) {
                    branches.get(reference1).dismissCourier = null;
                }
            }
        } else if (job.equals("MANAGER")) {
            if (branches.get(reference1).managerDismissal && branches.get(reference1).memberHashtable.get(reference2).getPromotion() > -5) {
                //System.out.println(branches.get(reference1).getManager());
                branches.get(reference1).managerDismissal = false;
            }
        }
        return "";
    }

    public boolean dismissals(String reference1, String reference2, String job) {
        if (job.equals("CASHIER")) {
            if ((branches.get(reference1).numberOfCashier > 1 && branches.get(reference1).memberHashtable.get(reference2).getPromotion() <= -5)) {
                branches.get(reference1).memberHashtable.remove(reference2);
                branches.get(reference1).numberOfCashier -= 1;
                return true;
            }
            if (branches.get(reference1).increaseCashier != null) {
                if (branches.get(reference1).increaseCashier.getName().equals(branches.get(reference1).memberHashtable.get(reference2).getName()) && branches.get(reference1).memberHashtable.get(reference2).getPromotion() < 3) {
                    branches.get(reference1).increaseCashier = null;
                }
            }
            if (branches.get(reference1).numberOfCashier <= 1 && branches.get(reference1).memberHashtable.get(reference2).getPromotion() <= -5) {
                branches.get(reference1).dismissCashier = branches.get(reference1).memberHashtable.get(reference2);
            }
        } else if (job.equals("COURIER")) {
            if (branches.get(reference1).memberHashtable.get(reference2).getPromotion() <= -5) {
                if (branches.get(reference1).numberOfCourier > 1) {
                    branches.get(reference1).memberHashtable.remove(reference2);
                    branches.get(reference1).numberOfCourier -= 1;
                    return true;
                } else {
                    branches.get(reference1).dismissCourier = branches.get(reference1).memberHashtable.get(reference2);
                }
            }
        } else if (job.equals("COOK")) {
            if (branches.get(reference1).cooksToManager.contains(branches.get(reference1).memberHashtable.get(reference2)) && branches.get(reference1).memberHashtable.get(reference2).getPromotion() < 10) {
                branches.get(reference1).cooksToManager.remove(branches.get(reference1).memberHashtable.get(reference2));
            }
            if (branches.get(reference1).memberHashtable.get(reference2).getPromotion() <= -5) {
                if (branches.get(reference1).numberOfCook > 1) {
                    branches.get(reference1).memberHashtable.remove(reference2);
                    branches.get(reference1).numberOfCook -= 1;
                    return true;
                } else {
                    branches.get(reference1).dismissCook = branches.get(reference1).memberHashtable.get(reference2);
                }
            }
        } else if (job.equals("MANAGER")) {
            if (branches.get(reference1).memberHashtable.get(reference2).getPromotion() <= -5) {
                if (branches.get(reference1).numberOfCook > 1 && !branches.get(reference1).cooksToManager.isEmpty()) {
                    branches.get(reference1).memberHashtable.remove(reference2);
                    Member member = branches.get(reference1).cooksToManager.poll();
                    branches.get(reference1).setManager(member.getName());
                    branches.get(reference1).memberHashtable.get(member.getName()).setDuty("MANAGER");
                    branches.get(reference1).memberHashtable.get(member.getName()).setPromotion(-10);
                    branches.get(reference1).numberOfCook -= 1;
                    return true;
                } else {
                    branches.get(reference1).managerDismissal = true;
                }
            }
        }
        return false;
    }
    public void leaving(String[] strings) {
        String firstAccess = strings[1] + strings[2];
        if (branches.get(firstAccess).memberHashtable.get(strings[3]) == null) {
            System.out.println("There is no such employee.");
            return;
        }
        String job = branches.get(firstAccess).memberHashtable.get(strings[3]).getDuty();
        if (job.equals("CASHIER")) {
            if ((branches.get(firstAccess).numberOfCashier > 1)) {
                branches.get(firstAccess).memberHashtable.remove(strings[3]);
                branches.get(firstAccess).numberOfCashier -= 1;
                System.out.println(strings[3] + " is leaving from branch: " + strings[2] + ".");
            } else {
                if ((branches.get(firstAccess).dismissCashier != null)) {
                    if (!branches.get(firstAccess).dismissCashier.equals(branches.get(firstAccess).memberHashtable.get(strings[3]))) {
                        branches.get(firstAccess).setMonthlyBonus1(200);
                        branches.get(firstAccess).setTotalBonus(200);
                    }
                } else {
                    branches.get(firstAccess).setMonthlyBonus1(200);
                    branches.get(firstAccess).setTotalBonus(200);
                }
            }
        } else if (job.equals("COURIER")) {
            if ((branches.get(firstAccess).numberOfCourier > 1)) {
                branches.get(firstAccess).memberHashtable.remove(strings[3]);
                branches.get(firstAccess).numberOfCourier -= 1;
                System.out.println(strings[3] + " is leaving from branch: " + strings[2] + ".");
            } else {
                if ((branches.get(firstAccess).dismissCourier != null)) {
                    if (!branches.get(firstAccess).dismissCourier.equals(branches.get(firstAccess).memberHashtable.get(strings[3]))) {
                        branches.get(firstAccess).setMonthlyBonus1(200);
                        branches.get(firstAccess).setTotalBonus(200);
                    }
                } else {
                    branches.get(firstAccess).setMonthlyBonus1(200);
                    branches.get(firstAccess).setTotalBonus(200);
                }
            }
        } else if (job.equals("COOK")) {
            if ((branches.get(firstAccess).numberOfCook > 1)) {
                if(branches.get(firstAccess).cooksToManager.contains(branches.get(firstAccess).memberHashtable.get(strings[3]))) {
                    branches.get(firstAccess).cooksToManager.remove(branches.get(firstAccess).memberHashtable.get(strings[3]));
                }
                branches.get(firstAccess).memberHashtable.remove(strings[3]);
                branches.get(firstAccess).numberOfCook -= 1;
                System.out.println(strings[3] + " is leaving from branch: " + strings[2] + ".");
            } else {
                if ((branches.get(firstAccess).dismissCook != null)) {
                    if (!branches.get(firstAccess).dismissCook.equals(branches.get(firstAccess).memberHashtable.get(strings[3]))) {
                        branches.get(firstAccess).setMonthlyBonus1(200);
                        branches.get(firstAccess).setTotalBonus(200);
                    }
                } else {
                    branches.get(firstAccess).setMonthlyBonus1(200);
                    branches.get(firstAccess).setTotalBonus(200);
                }
            }
        } else if (job.equals("MANAGER")) {
            if (branches.get(firstAccess).numberOfCook > 1 && !branches.get(firstAccess).cooksToManager.isEmpty()) {
                branches.get(firstAccess).memberHashtable.remove(strings[3]);
                Member member = branches.get(firstAccess).cooksToManager.poll();
                branches.get(firstAccess).memberHashtable.get(member.getName()).setDuty("MANAGER");
                branches.get(firstAccess).setManager(member.getName());
                branches.get(firstAccess).memberHashtable.get(member.getName()).setPromotion(-10);
                branches.get(firstAccess).numberOfCook -= 1;
                System.out.println(strings[3] + " is leaving from branch: " + strings[2] + ".");
                System.out.println(branches.get(firstAccess).getManager() + " is promoted from Cook to Manager.");
            } else {
                if (!branches.get(firstAccess).managerDismissal) {
                    branches.get(firstAccess).setMonthlyBonus1(200);
                    branches.get(firstAccess).setTotalBonus(200);
                }
            }
        }
    }
}