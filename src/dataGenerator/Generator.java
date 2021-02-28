package dataGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;
public class Generator {
    private int peopleToRandom=1000;
    private int personsOn1Infected=1000;
    private int maxPassage=20;
    private int passagesCount=10;
    private int maxCars=10;
    private int normalInCar=10;
    private int disableInCar=5;
    private int disSeats=50;
    private int nromalSeats=100;
    private PeopleDataBase base=new PeopleDataBase();
    public void generate(){
        int a=0,days=0;
        Scanner scan=new Scanner(System.in);
        readPeopleData();
        readStationData();
        generateTrains();
        generateCars();
        System.out.println("podaj całkowitą liczbę dni działania programu");
        days=scan.nextInt();
        base.saveFirst(days);
        do{
            System.out.println("podaj ilość dni do wygenerowania danych");
            a=scan.nextInt();
            generatePassages(a);
            base.saveSecound();
            System.out.println("gotowe");
        }while(a>0);
    }
    private void generatePeople(String[] names,String[] surenames,int nSize,int sSize){
        Random rand=new Random();
        for(int i=0;i<peopleToRandom;i++) {
            String name = names[abs(rand.nextInt() % nSize)];
            String surename = surenames[abs(rand.nextInt() % sSize)];
            base.createPerson(name, surename);
        }
    }

    private void generatePassages(int days){
        Random rand=new Random();
        for(int day=0;day<days;day++) {
            System.out.println(day);
        base.findAllTrains().forEach(
            train -> {
                Passage passage = base.newPassage(train);
                train.getRoad().forEach(station -> {
                    Stop stop = base.newStop(station);
                    Stop prewStop = passage.getLastStop();
                    if (prewStop == null) {
                        stop.setTime(1);
                    } else {
                        stop.setTime(prewStop.getNumber()+1);
                    }
                    stop.setDelay(abs(rand.nextInt() % 20), abs(rand.nextInt() % 20));
                    passage.addStop(stop);
                });
                int disabled=abs(rand.nextInt()%disSeats);
                int normal=abs(rand.nextInt()%nromalSeats);
                for(int i=0;i<normal;i++){
                    int seat=i%normalInCar+1;
                    int car=i/normalInCar+1;
                    int first=abs(rand.nextInt()%19);
                    int last=abs(rand.nextInt()%(19-first))+1;
                    Stop firstStop=passage.getFirstStop();
                    while(first>0){
                        firstStop=firstStop.getNextStop();
                        first--;
                    }
                    Stop lastStop=firstStop;
                    while(last>0){
                        lastStop=lastStop.getNextStop();
                        last--;
                    }
                    base.newTicket(train,passage,seat,car,false,firstStop,lastStop,personsOn1Infected);
                }
                for(int i=0;i<disabled;i++){
                    int seat=i%disableInCar+1;
                    int car=i/disableInCar+1;
                    int first=abs(rand.nextInt()%19);
                    int last=abs(rand.nextInt()%(19-first))+1;
                    Stop firstStop=passage.getFirstStop();
                    while(first>0){
                        firstStop=firstStop.getNextStop();
                        first--;
                    }
                    Stop lastStop=firstStop;
                    while(last>0){
                        lastStop=lastStop.getNextStop();
                        last--;
                    }
                    base.newTicket(train,passage,seat+20,car,true,firstStop,lastStop,personsOn1Infected);
                }
                base.nextDay();
            }
        );
        }
    }
    private void generateDates(){

    }
    private void generateCars(){
        base.findAllTrains().forEach(
            train ->{
                for(int i=0;i<maxCars;i++){
                    base.newCar(train,i+1);
                }
            }
        );
    }
    private void generateTrains(){
        Random rand=new Random();
        int count=base.findAllStations().size();
        Station[] stations= base.findAllStations().toArray(new Station[count]);
        for(int j=0;j<passagesCount;j++) {
            Train train = base.newTrain();
            for (int i = 0; i < maxPassage; i++) {
                int k=abs(rand.nextInt()%count);
                while(train.stationExistsInRoad(stations[k].getName())){
                    k=abs(rand.nextInt()%count);
                }
                train.addStation(stations[k]);
            }
        }
    }
    private void readStationData(){
        int stationsSize=0;
        File stationNames = new File("data/stations.txt");
        try {
            Scanner scan =new Scanner(stationNames);
            while(scan.hasNextLine()){
                base.createStation(scan.nextLine());
                stationsSize++;
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("plik: "+ "data/stations.txt"+" nie znaleziony");
            return;
        }
    }
    private void readPeopleData(){
        int countBaseNames=20000;
        int namesToRandom=1000;
        String[] names=new String[countBaseNames];
        String[] surenames=new String[countBaseNames];
        String[] people=new String[namesToRandom];
        int sSize=0;
        int nSize=0;
        File namesFile = new File("data/names.txt");
        File surenamesFile=new File("data/surenames.txt");
        try {
            Scanner scan =new Scanner(namesFile);
            while(scan.hasNextLine()){
                names[nSize]=scan.nextLine();
                nSize++;
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("plik: "+ namesFile+" nie znaleziony");
            return;
        }
        try {
            Scanner scan=new Scanner(surenamesFile);
            while(scan.hasNextLine()){
                surenames[sSize]=scan.nextLine();
                sSize++;
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("plik: "+ surenamesFile+" nie znaleziony");
        }
        generatePeople(names,surenames,nSize,sSize);
    }
}
