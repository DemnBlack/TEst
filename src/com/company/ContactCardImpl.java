package com.company;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class ContactCardImpl implements ContactCard {

    private String FN;
    private String ORG;
    private boolean GENDER;
    private Calendar BDAY;
    public Map<String, String> TEL = new HashMap<String, String>();

    public ContactCard getInstance(Scanner scanner) {
        while (scanner.hasNext() == true) {
            String s = scanner.nextLine();
            String sub = s.substring(0, s.indexOf(':'));
            if (sub.equals("FN")) {
                FN = s.substring(s.indexOf(':') + 1);
            }

            if (sub.equals("ORG")) {
                ORG = s.substring(s.indexOf(':') + 1);
            }

            if (sub.equals("GENDER")) {
                GENDER = (s.substring(s.indexOf(':') + 1).equals("F") ? true : false);
            }

            if (sub.equals("BDAY")) {
                String ss = (s.substring(s.indexOf(':') + 1));
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = format.parse(ss);
                    BDAY = Calendar.getInstance();
                    BDAY.setTime(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (sub.indexOf("TEL;") != -1) {
                TEL.put(sub.substring(s.indexOf('=') + 1, s.indexOf(':')), s.substring(s.indexOf(':') + 1));
            }
        }
        return this;
    }


    public ContactCard getInstance(String data) {
        return getInstance(new Scanner(data));
    }


    public String getFullName() {
        return  FN;
    }


    public String getOrganization() {
        return  ORG;
    }


    public boolean isWoman() {
        return GENDER;
    }

    public Calendar getBirthday() {
            if (BDAY == null) throw new NoSuchElementException();
        return BDAY;
    }


    public Period getAge() {
            if (BDAY == null) throw new NoSuchElementException();

        LocalDate now = LocalDate.from(LocalDateTime.now());
        LocalDate bDay = LocalDateTime.ofInstant(BDAY.toInstant(), BDAY.getTimeZone().toZoneId()).toLocalDate();
        Period period = Period.between(bDay, now);


        return period;
    }


    public int getAgeYears(){
            if (BDAY == null) throw new NoSuchElementException();
        return getAge().getYears();
    }


    public String getPhone(String type) {
        if(!TEL.containsKey(type)){
            throw new NoSuchElementException();
        }else if(TEL.get(type).toCharArray().length > 10){
            throw new NoSuchElementException();
        }
        String sc = TEL.get(type);

//            if (TEL.size() == 0) throw new NoSuchElementException();
//
//        for (Map.Entry<String, String> entry : TEL.entrySet()) {
//            String sc = entry.getValue();
//            System.out.println(sc);
//            if(sc.toCharArray().length > 10){ throw new NoSuchElementException();}
//
            return "(" + sc.substring(0, 3) + ") " + sc.substring(3, 6) + "-" + sc.substring(6);

    }
}

