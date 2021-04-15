package games.battleship.battleship3;

import games.battleship.battleship3.exceptions.*;

import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class DefaultBattleshipPersistence implements IBattleshipPersistence {

    @Override
    public void load(IBattleship alef, IEnemy duo) throws IOException,
     OversizeException,
     OverlapTilesException,
     AdjacentTilesException,
     InvalidCountExeception {
        // File.separator different in OpSystems
        
        File file = new File(System.getProperty("user.dir")+
                File.separator + 
                "src" + 
                File.separator + 
                "medialab" + 
                File.separator + 
                "player_default.txt"); 
        
        Scanner sc = new Scanner(file);
        
        List<String> lines = new ArrayList<>(10);

        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        sc.close();
        
        // out -> lines, shipInf, attributes
        //System.out.println(lines);
        //
        List<Ship> ships = new ArrayList<>();
        int type, startx, starty;
        boolean orientationIsVertical;
        for (int i = 0; i <= 4; i++) {
            List<String> shipInf = Arrays.asList(lines.get(i).split(","));
            //System.out.println(shipInf);

            type= Integer.parseInt(shipInf.get(0));
            startx= Integer.parseInt(shipInf.get(1));
            starty= Integer.parseInt(shipInf.get(2));
            if((Integer.parseInt(shipInf.get(3))-1)==1) {
            	orientationIsVertical= true;
            }
            else {
            	orientationIsVertical= false;
            }
            //System.out.println(type+"("+startx+","+starty+")");

            switch (type) {
                case 5:
                    ships.add(new Ship("Destroyer",startx, starty, orientationIsVertical));
                    break;
                case 4: 
                    ships.add(new Ship("Submarine",startx, starty, orientationIsVertical));
                    break;
                case 3:
                    ships.add(new Ship("Cruiser",startx , starty, orientationIsVertical));
                    break;
                case 2: 
                    ships.add(new Ship("Battleship",startx , starty, orientationIsVertical));
                    break;
                case 1:
                    ships.add(new Ship("Carrier",startx ,starty, orientationIsVertical));
                    break;      
            };
        }

        // Check fot OversizeException
        // Check outOfBounds
        int x, y;
        String stringTypeOfShip;
        boolean orientationIsVert;

        int lengthShip;
        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    lengthShip = 2;
                    break;
                case "Submarine":   
                    lengthShip =3;
                    break;
                case "Cruiser":
                    lengthShip =3;
                    break;
                case "Battleship":  
                    lengthShip =4;
                    break;
                case "Carrier":
                    lengthShip =5;
                    break;      
            };
            x = ship.getX();
            y = ship.getY();
            lengthShip = ship.getLength();
            orientationIsVert = ship.getOrientationIsVertical(); 
            if (orientationIsVert == true){
                if(y>9 || y<0 || x>9 || x<0 || x+lengthShip-1 >9 || x+lengthShip-1 <0) {
                    throw new OversizeException (
                        "A ship was placed out of the board info:"+ship.getTypeOfShip()+" at "+ship.getX()+ship.getY()+" vertical " );
                }
            }
            else {
                if(x>9 || x<0 || y>9 || y<0 || y+lengthShip-1 >9 || y+lengthShip-1 <0) {
                    throw new OversizeException (
                        "A ship was placed out of the boarrd"+ship.getTypeOfShip()+" at "+ship.getX()+ship.getY()+" horizontal ");
                }
            }
        }


        //Check for OverlapTilesException


        int[][] arr = new int[10][10];
        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    lengthShip = 2;
                    break;
                case "Submarine":   
                    lengthShip =3;
                    break;
                case "Cruiser":
                    lengthShip =3;
                    break;
                case "Battleship":  
                    lengthShip =4;
                    break;
                case "Carrier":
                    lengthShip =5;
                    break;      
            };
            x = ship.getX();
            y = ship.getY();
            lengthShip = ship.getLength();
            orientationIsVert = ship.getOrientationIsVertical(); 
            if (orientationIsVert == false){
                int row = x;
                for (int col = y; col <= y + lengthShip - 1; col++) {
                    arr[row][col] ++;
                }
            }
            else {
                int col = y;
                for (int row = x; row <= x +lengthShip -1 ; row++) {
                    arr[row][col] ++;
                }
            }
        }

        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9 ; col++) {
                if (arr[row][col]>=2){
                    throw new OverlapTilesException(
                        "Ships can not be one upon the other Information:"+" at "+ row+ col);
                }
            }
        }


        // Check for AdjacentTilesException


        int[][] twoD_arr = new int[12][12];

        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    lengthShip = 2;
                    break;
                case "Submarine":   
                    lengthShip =3;
                    break;
                case "Cruiser":
                    lengthShip =3;
                    break;
                case "Battleship":  
                    lengthShip =4;
                    break;
                case "Carrier":
                    lengthShip =5;
                    break;      
            };
            x = ship.getX();
            y = ship.getY();
            lengthShip = ship.getLength();
            //System.out.println(lengthShip+"lengajfTH");
            orientationIsVert = ship.getOrientationIsVertical(); 
            if (orientationIsVert == false){
                for (int col = y-1; col <= y + lengthShip; col++) {
                    for (int row = x-1; row <= x + 1 ; row++) {
                        // make (-1,-1) -> (0,0) the starting point
                        twoD_arr[row+1][col+1] ++;
                    }
                }
                int row = ship.getX();
                y = ship.getY();
                for (int col = y; col <= y + lengthShip - 1; col++) {
                    twoD_arr[row+1][col+1] ++;
                }
                // Sub the diagonal
                x = ship.getX();
                y = ship.getY();
                twoD_arr[x-1+1][y-1+1] --;
                twoD_arr[x-1+1][y+lengthShip+1] --;
                twoD_arr[x+1+1][y-1+1] --;
                twoD_arr[x+1+1][y+lengthShip+1] --;
            }
            else {
                for (int col = y-1; col <= y + 1; col++) {
                    for (int row = x-1; row <= x +lengthShip ; row++) {
                        // make (-1,-1) -> (0,0) the starting point
                        twoD_arr[row+1][col+1] ++;
                    }
                }
                int col = ship.getY();
                x = ship.getX();
                for (int row = x; row <= x +lengthShip -1 ; row++) {
                    twoD_arr[row+1][col+1] ++;
                }
                // Sub the diagonal
                x = ship.getX();
                y = ship.getY();
                twoD_arr[x-1+1][y-1+1] --;
                twoD_arr[x+lengthShip+1][y-1+1] --;
                twoD_arr[x-1+1][y+1+1] --;
                twoD_arr[x+lengthShip+1][y+1+1] --;
            }
        }

        // Check adjacent
        for (int row = 0; row <= 11; row++) {
            for (int col = 0; col <= 11 ; col++) {
                if (twoD_arr[row][col]>=3){
                    throw new AdjacentTilesException(
                        "Ships can not be adjaccent vertical or horizontal as in squere:"+row+col);
                }
            }
        }


        // Check for InvalidCountExeception

        int doubleShipsCheck[];
        doubleShipsCheck= new int[6];
        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    doubleShipsCheck[1]++;
                    break;
                case "Submarine":   
                    doubleShipsCheck[2]++;
                    break;
                case "Cruiser":
                    doubleShipsCheck[3]++;
                    break;
                case "Battleship":  
                    doubleShipsCheck[4]++;
                    break;
                case "Carrier":
                    doubleShipsCheck[5]++;
                    break;      
            };
        }

        // Check DoubleShips
        for (int i = 1; i<=5; i++) {
            if (doubleShipsCheck[i]>1)
                throw new InvalidCountExeception(
                    "More than one Ship of a specific type");
        }

        alef.init(ships);
        

        // Load player_SCENARIO-ID.txt
        

        File file2 = new File(System.getProperty("user.dir")+
                File.separator + 
                "src" + 
                File.separator + 
                "medialab" + 
                File.separator + 
                "enemy_default.txt"); 
        
        Scanner sc2 = new Scanner(file2);
        
        // mine : may different (10)
        lines = new ArrayList<>(10);

        while (sc2.hasNextLine()) {
            lines.add(sc2.nextLine());
        }
        sc2.close();
        
        // out -> lines, shipInf, attributes
        //System.out.println(lines);
        //
        ships = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            List<String> shipInf = Arrays.asList(lines.get(i).split(","));
            //System.out.println(shipInf);

            type= Integer.parseInt(shipInf.get(0));
            startx= Integer.parseInt(shipInf.get(1));
            starty= Integer.parseInt(shipInf.get(2));
            if((Integer.parseInt(shipInf.get(3))-1)==1) {
            	orientationIsVertical= true;
            }
            else {
            	orientationIsVertical= false;
            }
            //System.out.println(attirbutes);

            switch (type) {
                case 5:
                    ships.add(new Ship("Destroyer",startx, starty, orientationIsVertical));
                    break;
                case 4: 
                    ships.add(new Ship("Submarine",startx ,starty, orientationIsVertical));
                    break;
                case 3:
                    ships.add(new Ship("Cruiser",startx ,starty, orientationIsVertical));
                    break;
                case 2: 
                    ships.add(new Ship("Battleship",startx, starty, orientationIsVertical));
                    break;
                case 1:
                    ships.add(new Ship("Carrier",startx, starty, orientationIsVertical));
                    break;      
            };
        }

        // Check fot OversizeException
        // Check outOfBounds
        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    lengthShip = 2;
                    break;
                case "Submarine":   
                    lengthShip =3;
                    break;
                case "Cruiser":
                    lengthShip =3;
                    break;
                case "Battleship":  
                    lengthShip =4;
                    break;
                case "Carrier":
                    lengthShip =5;
                    break;      
            };
            x = ship.getX();
            y = ship.getY();
            lengthShip = ship.getLength();
            orientationIsVert = ship.getOrientationIsVertical(); 
            if (orientationIsVert == true){
                if(y>9 || y<0 || x>9 || x<0 || x+lengthShip-1 >9 || x+lengthShip-1 <0) {
                    throw new OversizeException (
                        "A ship was placed out of the boardd "+ship.getTypeOfShip()+ship.getX()+ship.getY()+ship.getLength() );
                }
            }
            else {
                if(x>9 || x<0 || y>9 || y<0 || y+lengthShip-1 >9 || y+lengthShip-1 <0) {
                    throw new OversizeException (
                        "A ship was placed out of the boarrd"+ship.getTypeOfShip()+ship.getX()+ship.getY()+ship.getLength());
                }
            }
        }


        //Check for OverlapTilesException

        arr = new int[10][10];

        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    lengthShip = 2;
                    break;
                case "Submarine":   
                    lengthShip =3;
                    break;
                case "Cruiser":
                    lengthShip =3;
                    break;
                case "Battleship":  
                    lengthShip =4;
                    break;
                case "Carrier":
                    lengthShip =5;
                    break;      
            };
            x = ship.getX();
            y = ship.getY();
            lengthShip = ship.getLength();
            orientationIsVert = ship.getOrientationIsVertical(); 
            if (orientationIsVert == false){
                int row = x;
                for (int col = y; col <= y + lengthShip - 1; col++) {
                    arr[row][col] ++;
                }
            }
            else {
                int col = y;
                for (int row = x; row <= x +lengthShip -1 ; row++) {
                    arr[row][col] ++;
                }
            }
        }

        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9 ; col++) {
                if (arr[row][col]>=2){
                    throw new OverlapTilesException(
                        "Ships can not be one uupon the other");
                }
            }
        }


        // Check for AdjacentTilesException


        twoD_arr = new int[12][12];

        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    lengthShip = 2;
                    break;
                case "Submarine":   
                    lengthShip =3;
                    break;
                case "Cruiser":
                    lengthShip =3;
                    break;
                case "Battleship":  
                    lengthShip =4;
                    break;
                case "Carrier":
                    lengthShip =5;
                    break;      
            };
            x = ship.getX();
            y = ship.getY();
            lengthShip = ship.getLength();
            //System.out.println(lengthShip+"lengajfTH");
            orientationIsVert = ship.getOrientationIsVertical(); 
            if (orientationIsVert == false){
                for (int col = y-1; col <= y + lengthShip; col++) {
                    for (int row = x-1; row <= x + 1 ; row++) {
                        // make (-1,-1) -> (0,0) the starting point
                        twoD_arr[row+1][col+1] ++;
                    }
                }
                int row = ship.getX();
                y = ship.getY();
                for (int col = y; col <= y + lengthShip - 1; col++) {
                    twoD_arr[row+1][col+1] ++;
                }
                // Sub the diagonal
                x = ship.getX();
                y = ship.getY();
                twoD_arr[x-1+1][y-1+1] --;
                twoD_arr[x-1+1][y+lengthShip+1] --;
                twoD_arr[x+1+1][y-1+1] --;
                twoD_arr[x+1+1][y+lengthShip+1] --;
            }
            else {
                for (int col = y-1; col <= y + 1; col++) {
                    for (int row = x-1; row <= x +lengthShip ; row++) {
                        // make (-1,-1) -> (0,0) the starting point
                        twoD_arr[row+1][col+1] ++;
                    }
                }
                int col = ship.getY();
                x = ship.getX();
                for (int row = x; row <= x +lengthShip -1 ; row++) {
                    twoD_arr[row+1][col+1] ++;
                }
                // Sub the diagonal
                x = ship.getX();
                y = ship.getY();
                twoD_arr[x-1+1][y-1+1] --;
                twoD_arr[x+lengthShip+1][y-1+1] --;
                twoD_arr[x-1+1][y+1+1] --;
                twoD_arr[x+lengthShip+1][y+1+1] --;
            }
        }

        // Check adjacent
        for (int row = 0; row <= 11; row++) {
            for (int col = 0; col <= 11 ; col++) {
                if (twoD_arr[row][col]>=3){
                    throw new AdjacentTilesException(
                        "Ships can not be adjaccent vertical or horizontal"+row+col);
                }
            }
        }



        // Check for InvalidCountExeception

        doubleShipsCheck= new int[6];
        for (Ship ship : ships) {
            stringTypeOfShip = ship.getTypeOfShip();
            switch (stringTypeOfShip) {
                case "Destroyer":
                    doubleShipsCheck[1]++;
                    break;
                case "Submarine":   
                    doubleShipsCheck[2]++;
                    break;
                case "Cruiser":
                    doubleShipsCheck[3]++;
                    break;
                case "Battleship":  
                    doubleShipsCheck[4]++;
                    break;
                case "Carrier":
                    doubleShipsCheck[5]++;
                    break;      
            };
        }

        // Check DoubleShips
        for (int i = 1; i<=5; i++) {
            if (doubleShipsCheck[i]>1)
                throw new InvalidCountExeception(
                    "More than one Ship of a specific type");
        }
            //
        duo.init(ships);
    }

    @Override
    public void save(IBattleshipGame game, OutputStream outputStream) throws IOException {
        ;
    }

}
