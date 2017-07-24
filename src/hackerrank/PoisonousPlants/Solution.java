package hackerrank.PoisonousPlants;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Plant[] plants = new Plant[n];
        for(int i=0; i < n; i++) {
            plants[i] = new Plant(in.nextInt());
        }
        for(int i=0; i < n; i++) {
            plants[i].left = i == 0 ? null : plants[i-1];
            plants[i].right = i == plants.length-1 ? null : plants[i+1];
        }
        List<Plant> checkList = new ArrayList<>(n);
        List<Plant> deadList = new ArrayList<>();
        checkList.addAll(Arrays.asList(plants));
        
        int countDead = 1;
        int days = 0;
        while(countDead != 0) {
            countDead = 0;
            checkList.stream().filter( plant -> plant.left != null && plant.p > plant.left.p)
                    .forEach(plant -> deadList.add(plant));

            checkList.clear();
            for(Plant plant: deadList) {
                if(plant.left != null) {
                    plant.left.right = plant.right;
                }
                if(plant.right != null) {
                    plant.right.left = plant.left;
                    checkList.add(plant.right);
                }
            }
            days++;
        }
        System.out.println(days);
    }
    
    static class Plant {
        int p; // pesticide;
        Plant left;
        Plant right;
        Plant(int pesticide) {
            this.p = pesticide;
        }
    }
}