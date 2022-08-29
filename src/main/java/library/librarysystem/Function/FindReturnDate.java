package library.librarysystem.Function;

public class FindReturnDate {
//    public static void main(String[] args) {
//
//        FindReturnDate find = new FindReturnDate();
//
//        String date = find.findReturnDate(200);
//
//    }
    
    public String findReturnDate (int bookIssueDateCount){
        
        String returnDate = null;
        String currentDate = String.valueOf(java.time.LocalDate.now());

        int[] monthDates  = {31,28,31,30,31,30,31,31,30,31,30,31};

        int nowYear = Integer.parseInt(currentDate.substring(0,4));
        int nowMonth = Integer.parseInt(currentDate.substring(5,7));
        int nowDate = Integer.parseInt(currentDate.substring(8,10));

        int runYear = nowYear;
        int runMonth = nowMonth;
        int runDate = nowDate;


        for (int dateCount=0; dateCount<bookIssueDateCount; dateCount++){

//            change february month date count depending on year
            if (runYear%4==0){
                monthDates[1]=29;
            }else {
                monthDates[1]=28;
            }

            runDate++;
          if (runDate > monthDates[runMonth-1]){

              runMonth++;
              runDate=1;

              if (runMonth > 12){

                  runYear++;
                  runMonth=1;

              }

          }

        }
        System.out.println(currentDate);
        returnDate = runYear +"-"+runMonth +"-"+ runDate;
        System.out.println (returnDate);
        return returnDate;
    }
}
