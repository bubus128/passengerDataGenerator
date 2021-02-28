package dataGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;

public class dataGenerator {
    public static void main(String[] args) {
        Generator generator=new Generator();
        generator.generate();
    }
}
