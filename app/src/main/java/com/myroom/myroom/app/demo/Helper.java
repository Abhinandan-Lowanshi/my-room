package com.myroom.myroom.app.demo;

import java.util.ArrayList;

public class Helper {
    ArrayList<RoomData>  data = new ArrayList<>();
    ArrayList<RoomDataForSearch>  roomDataforsearch = new ArrayList<>();


    public Helper() {
    }

    public Helper(int status) {
      RoomData roomData =   new RoomData(22.0772,76.4794,"Mundi","Lala Nagar","Chandan","955055451541","1");
        data.add(roomData);
       roomData =   new RoomData(22.0772,76.4794,"Khalwa","Narayan Nagar","Mansingh","955055451541","2");
        data.add(roomData);
         roomData =   new RoomData(21.6122,76.4013,"Chhanera","Ravi Nagar","Nayantara","9550552651541","3");
        data.add(roomData);
         roomData =   new RoomData(22.4514,77.4660,"Seoni Mawla","Shivji Nagar","JSK","5456345255","4");
        data.add(roomData);
         roomData =   new RoomData(22.4286,77.4258,"Bharlaye","Ram Nagar","Abhinandan","64235440311","5");
        data.add(roomData);
    }

    public ArrayList<RoomData> getData()
     {
          return data;
     }

     public ArrayList<RoomDataForSearch> getRoomDataForSearch()
     {
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1200","3","Abhi","Banapura"));
          roomDataforsearch.add(new RoomDataForSearch("1BHK","3000","3","Hemant","Aanand Nagar"));
          roomDataforsearch.add(new RoomDataForSearch("2BHK","4500","4","Aaksh","Betun"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","2000","4","Vinayak","Seoni Malwa"));
          roomDataforsearch.add(new RoomDataForSearch("3BHK","7500","3","Ajay","Mandideep"));
          roomDataforsearch.add(new RoomDataForSearch("3BHK","12000","5","Ritesh","Budhni"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1200","3","Deeoendra","Bholaram"));
          roomDataforsearch.add(new RoomDataForSearch("3BHK","8200","2","Aman","Kothda"));
          roomDataforsearch.add(new RoomDataForSearch("1BHK","1200","1","Aadarsh Gour","Banapura colony"));
          roomDataforsearch.add(new RoomDataForSearch("2BHK","3200","4","Adarsh","Chhapra"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","900","0","Madhur","Shohagpur"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1300","3","Aadesh Shukla","Hoshangabad"));
          roomDataforsearch.add(new RoomDataForSearch("1BHK","1000","4","Rohit singh","Reewa"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","3200","5","Dheerendra","Vikram tower"));
          roomDataforsearch.add(new RoomDataForSearch("2BHK","8200","5","Chandan","Bhabar kua indore"));
          roomDataforsearch.add(new RoomDataForSearch("1BHK","1600","4","Anil","Old Ashoka Garden Bhopal"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1000","2","Rahul Patel","New Market Bhapal"));
          roomDataforsearch.add(new RoomDataForSearch("2BHK","5600","3","Preetam","Lal ghati bhopal"));
          roomDataforsearch.add(new RoomDataForSearch("3BHK","6500","3","Praveen","Seoni Malwa"));
          roomDataforsearch.add(new RoomDataForSearch("1BHK","3500","4","Raj","Veena Nagar Indore"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1500","2","Vivek","Ashoka Garden Bhaopal"));
          roomDataforsearch.add(new RoomDataForSearch("1BHK","9200","5","Bhura seth","Ujjain"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1000","3","Ketan Lowanshi","Surendra Bihar"));
          roomDataforsearch.add(new RoomDataForSearch("Single Room","1800","4","vishal","Dhamniya"));
          return roomDataforsearch;
     }
}
