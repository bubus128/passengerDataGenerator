package dataGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.StrictMath.abs;

public class PeopleDataBase {
    private Set<Person> people=new HashSet<>();
    private Set<Ticket> tickets=new HashSet<>();
    private Set<Passage> passages =new HashSet<>();
    private Set<Car> cars=new HashSet<>();
    private Set<Station> stations=new HashSet<>();
    private Set<Stop> stops=new HashSet<>();
    private Set<Train> trains=new HashSet<>();
    private Set<Infected> infectedPeople=new HashSet<>();
    private Date currentDay=new Date(120,3,4);
    private Long currentTicket= Long.valueOf(0);
    private Long currentStop=Long.valueOf(0);
    private Long currentPassage=Long.valueOf(0);

    public void createPerson(String name,String surename){
        Random rand=new Random();
        Person person=new Person(name,surename,newPesel(),abs(rand.nextInt()%100000));
        people.add(person);
    }

    public void nextDay(){
        currentDay.setTime(currentDay.getTime()+(1000 * 60 * 60 * 24));
    }
    private void nextMin(Date date){
        date.setTime(date.getTime()+(1000 * 60));
    }
    private void nextDay(Date date){
        date.setTime(date.getTime()+(1000 * 60 * 60 * 24));
    }

    public Car newCar(Train train,int i){
        Random rand=new Random();
        Long id=abs(rand.nextLong()%10000000000L);
        while(!findCar(id).isEmpty()){
            id=abs(rand.nextLong()%10000000000L);
        }
        Car car=new Car(id,train,i);
        cars.add(car);
        return car;
    }
    public Train newTrain(){
        Random rand=new Random();
        Long id=abs(rand.nextLong()%10000000000L);
        while(!findTrain(id).isEmpty()){
            id=abs(rand.nextLong()%10000000000L);
        }
        Train train=new Train(id);
        trains.add(train);
        return train;
    }

    public Stop newStop(Station station){
        Stop stop=new Stop(currentStop,station,currentDay);
        currentStop++;
        stops.add(stop);
        return stop;
    }
    public void newInfected(Person person){
        Infected infected=new Infected(person,currentDay,3);
        infectedPeople.add(infected);
    }

    public Ticket newTicket(Train train,Passage passage, int seat,int car,boolean disabled,Stop firstStop,Stop lastStop,int personsOn1Infected){
        Random rand=new Random();
        int person=abs(rand.nextInt()%people.size())+1;
        Person passager=null;
        Iterator<Person> iterator=people.iterator();
        while (person>0){
            passager= iterator.next();
            person--;
        }
        Ticket ticket=new Ticket(currentTicket,passager,passage,firstStop,lastStop,seat,car,disabled);
        currentTicket++;
        int inf=rand.nextInt()%personsOn1Infected;
        if (inf==personsOn1Infected/2){
            newInfected(passager);
        }
        tickets.add(ticket);
        return ticket;
    }

    public Passage newPassage(Train train){
        Passage passage=new Passage(currentPassage,train);
        currentPassage++;
        passages.add(passage);
        return passage;
    }


    public boolean stationExistsInRoad(Long id,String name){
        return findTrain(id).get().stationExistsInRoad(name);
    }

    public void createStation(String name){
        Station station=new Station(name);
        stations.add(station);
    }

    public Optional<Person> findPerson(Long pesel){
        return people.stream().filter(person -> person.getPesel()==pesel).findFirst();
    }
    public Optional<Car> findCar(Long id){
        return cars.stream().filter(car -> car.getID()==id).findFirst();
    }
    public Optional<Passage> findPassage(Long id){
        return passages.stream().filter(passage -> passage.getID()==id).findFirst();
    }
    public Optional<Ticket> findTickets(Long id){
        return tickets.stream().filter(ticket -> ticket.getID()==id).findFirst();
    }
    public Optional<Stop> findStop(Long id){
        return stops.stream().filter(stop -> stop.getID()==id).findFirst();
    }

    public Optional<Train> findTrain(long id){
        return trains.stream().filter(train -> train.getID()==id).findFirst();
    }

    public synchronized List<Person> findAllPeople(){
        return new ArrayList<>(people);
    }
    public synchronized List<Infected> findAllInfected() {return new ArrayList<>(infectedPeople);}
    public synchronized List<Ticket> findAllTickets(){
        return new ArrayList<>(tickets);
    }
    public synchronized List<Passage> findAllPassages(){
        return new ArrayList<>(passages);
    }
    public synchronized List<Car> findAllCars(){ return new ArrayList<>(cars);}
    public synchronized List<Station> findAllStations(){ return new ArrayList<>(stations); }
    public synchronized List<Stop> findAllStops(){
        return new ArrayList<>(stops);
    }
    public synchronized List<Train> findAllTrains(){
        return new ArrayList<>(trains);
    }



    public Long newPesel(){
        Random rand=new Random();
        Long pesel=abs(rand.nextLong()% 99999999999L);
        while(!findPerson(pesel).isEmpty()) {
            pesel=abs(rand.nextLong()% 99999999999L);
        }
        return pesel;
    }


    private void saveTickets(){
        String outputFile="ticketsData.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllTickets().forEach(ticket -> {
                try {
                    writer.write(String.valueOf(ticket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveDates(int days){
        Date temp= new Date(currentDay.getTime());
        String outputFile="dates.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            try {
                for(int i=0;i<days;i++){
                    writer.write(String.valueOf(i+"|"+dayString(temp)));
                    nextDay(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveHours();
    }
    private void saveHours(){
        Date temp= new Date(currentDay.getTime());
        String outputFile="time.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            try {
                for(int i=0;i<60*24;i++){
                    writer.write(String.valueOf(i+"|"+hourString(temp)));
                    nextMin(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void savePeople(){
        String outputFile="randomNames.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllPeople().forEach(person -> {
                try {
                    writer.write(String.valueOf(person));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveTrains(){
        String outputFile="trainData.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllTrains().forEach(train -> {
                try {
                    writer.write(String.valueOf(train));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void savePassages(){
        String outputFile="passagesData.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllPassages().forEach(passage -> {
                try {
                    writer.write(String.valueOf(passage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveStations(){
        String outputFile="stationsData.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllStations().forEach(station -> {
                try {
                    writer.write(String.valueOf(station));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveStops(){
        String outputFile="stopsData.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllStops().forEach(stop -> {
                try {
                    writer.write(String.valueOf(stop));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveCars(){
        String outputFile="carsData.bulk";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllCars().forEach(car -> {
                try {
                    writer.write(String.valueOf(car));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveInfected(){
        String outputFile="infectedData.csv";
        File out=new File(outputFile);
        if(!out.exists()){
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer=new FileWriter(outputFile);
            findAllInfected().forEach(inf -> {
                try {
                    writer.write(String.valueOf(inf));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String doubleFormat(int a){
        if (a<10)
            return("0"+a);
        else
            return String.valueOf(a);
    }
    private String hourString(Date date){
        int hours=date.getHours();
        int minutes=date.getMinutes();
        return(hours+"|"+minutes+"\r");
    }
    private String dayString(Date date){
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        int day=date.getDate();
        String weekend;
        if(date.getDay()>4){
            weekend="tak|nie";
        }
        else{
            weekend="nie|nie";
        }
        return(year+"|"+month+"|"+day+"|"+weekend+"\r");
    }

    public void saveFirst(int days){
        saveDates(days);
        savePeople();
        saveTrains();
        saveStations();
        saveCars();
    }

    public void saveSecound(){
        saveStops();
        savePassages();
        saveTickets();
        saveInfected();
    }
}
