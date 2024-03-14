package com.final_project_csc308_winter_2024;
import java.util.Stack;

public class Solver {
    private Repository repository; // Reference to the Repository

    public Solver(Repository repository) {
        this.repository = repository;
    }

    public void hanoi(Stack<Integer> from, Stack<Integer> to, Stack<Integer> buf, int nmv) {
        if (nmv > 1) {
            hanoi(from, buf, to, nmv - 1);
            moveDisk(from, to);
            repository.move(from.peek(), to.peek()); // Inform Repository about the move
            hanoi(buf, to, from, nmv - 1);
        } else {
            moveDisk(from, to);
            repository.move(from.peek(), to.peek()); // Inform Repository about the move
        }
    }

    public static void moveDisk(Stack<Integer> source, Stack<Integer> target) {
        int disk = source.pop();
        target.push(disk);
        System.out.println("Move disk " + disk + " from " + source + " to " + target);
    }

    public static void printState(Stack<Integer> t1, Stack<Integer> t2, Stack<Integer> t3) {
        System.out.println("t1: " + t1);
        System.out.println("t2: " + t2);
        System.out.println("t3: " + t3);
        System.out.println();
    }
}
