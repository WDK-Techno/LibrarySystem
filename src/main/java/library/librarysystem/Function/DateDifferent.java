package library.librarysystem.Function;

public class DateDifferent {


    public int findDifferent(String previousDate){

//        1999-08-27
        String currentDate = String.valueOf(java.time.LocalDate.now());

        int[] monthDates  = {31,28,31,30,31,30,31,31,30,31,30,31};

        int preYear = Integer.parseInt(previousDate.substring(0,4));
        int preMonth = Integer.parseInt(previousDate.substring(5,7));
        int preDate = Integer.parseInt(previousDate.substring(8,10));

        int nowYear = Integer.parseInt(currentDate.substring(0,4));
        int nowMonth = Integer.parseInt(currentDate.substring(5,7));
        int nowDate = Integer.parseInt(currentDate.substring(8,10));

//        System.out.println("Previoues Date :-> " + preYear  + " : " + preMonth + " : " + preDate);
//        System.out.println("Current Date   :-> " + nowYear  + " : " + nowMonth + " : " + nowDate);

        int runYear;
        int runMonth;
        int runDate;

        int yearCount=0;
        int monthCount=0;
        int daysCount=0;


        for (runYear=preYear;runYear<=nowYear;runYear++){

            if (runYear%4==0){
                monthDates[1]=29;
            }else {
                monthDates[1]=28;
            }


            yearCount++; //for get year count end of the program

//            System.out.println("Year : " + runYear);

            for (runMonth=preMonth;runMonth <=12;runMonth++){

//                System.out.println(runMonth);

                preMonth=1; //after run one time reset month

                monthCount++; //for get month count end of the program


                for (runDate=preDate;runDate<= monthDates[runMonth-1];runDate++){

//                    System.out.println("D : " + runDate);


                    preDate=1; //after run one time reset date
                    daysCount++;


                    if (runYear==nowYear && runMonth==nowMonth && runDate==nowDate){
                        //after reach to current month and year break the loop
                        break;
                    }

                }

                if (runYear==nowYear && runMonth==nowMonth){
                    //after reach to current month and year break the loop
                    break;
                }
            }

        }

//        System.out.println("Years :--> " + (yearCount-1));
//        System.out.println("Months :--> " + (monthCount-1));
//        System.out.println("Days :--> " + (daysCount-1));

        return (daysCount-1);
    }

//    public static void main(String[] args) {
//
//        DateDifferent date = new DateDifferent();
//
//        int day = date.findDifferent("1999-08-27");
//        System.out.println("D : " + day);
//    }

}
