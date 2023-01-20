import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class ReadConfig {
    public List<Monster> allMonster;
    public List<Move> allMoves;
    public List<ElementType> listElmnt;
    public List<Move> listMonsterMoves;
    public StatusType moveStatus;
    public List<String[]> effectivity;

    public ReadConfig(){
        this.readMonster();
    }

    public List<Monster> getAllMonster(){
        return this.allMonster;
    }

    public List<String[]> getEffectivity() {
        return this.effectivity;
    }

    public Move getMoveOFID(int MoveID){
        List<Move> tempListMoves = this.allMoves;
        Move searchMove = null;
        for (Move themove : tempListMoves){
            if (themove.getMoveID() == MoveID){
                searchMove = themove;
            }
        }
        return searchMove;
    }

    public boolean isContainsComa(String stringName){
        boolean contains = false;

        if (stringName.contains(",")){
            contains = true;
        }

        return contains;
    }

    public List<ElementType> readElmnt(String[] elmnt) {
        

        for (int i = 0; i <elmnt.length; i++){
            if(elmnt[i].equals("FIRE")) {
                listElmnt.add(ElementType.FIRE);
            } else if(elmnt[i].equals("GRASS")) {
                listElmnt.add(ElementType.GRASS);
            } else if(elmnt[i].equals("WATER")) {
                listElmnt.add(ElementType.WATER);
            } else if(elmnt[i].equals("NORMAL")) {
                listElmnt.add(ElementType.NORMAL);
            }
        }
        return listElmnt;

        // for(String elmntType : elmnt) {
        //     if(elmntType == "FIRE") {
        //         listElmnt.add(ElementType.FIRE);
        //     } else if(elmntType == "GRASS") {
        //         listElmnt.add(ElementType.GRASS);
        //     } else if(elmntType == "WATER") {
        //         listElmnt.add(ElementType.WATER);
        //     } else if(elmntType == "NORMAL") {
        //         listElmnt.add(ElementType.NORMAL);
        //     }
        // }
        // return listElmnt;
    }

    public void readMonster() {
        try{
            CSVReader readerMove = new CSVReader(new File(Main.class.getResource("movepool.csv").toURI()), ";");
            readerMove.setSkipHeader(true);
            List<String[]> lines = readerMove.read();

            allMoves = new ArrayList<>();
            
            for (String[] line : lines) {
                if(line[1].equals("STATUS")) {
                    // System.out.println("tes");
                    
                    if(line[8].equals("BURN")) {
                        moveStatus = StatusType.BURN;
                    } else if(line[8].equals("POISON")) {
                        moveStatus = StatusType.POISON;
                    } else if(line[8].equals("SLEEP")) {
                        moveStatus = StatusType.SLEEP;
                    } else if(line[8].equals("PARALYZE")) {
                        moveStatus = StatusType.PARALYZE;
                    } else if(line[8].equals("-")) {
                        moveStatus = StatusType.NONE;
                    }
                    String[] eff = line[9].split(",");
                    Move moveBaru = new Move(Integer.parseInt(line[0]), line[2], ElementType.valueOf(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]), TargetMove.valueOf(line[7]), moveStatus, Double.parseDouble(eff[0]), Double.parseDouble(eff[1]), Double.parseDouble(eff[2]), Double.parseDouble(eff[3]), Double.parseDouble(eff[4]), Double.parseDouble(eff[5]));
                    allMoves.add(moveBaru);
                } else if(line[1].equals("NORMAL")) {
                    // System.out.println("normal masuk");
                    Move moveBaru = new Move(Integer.parseInt(line[0]), TypeMove.NORMAL, line[2], ElementType.valueOf(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[8]));
                    allMoves.add(moveBaru);
                } else if(line[1].equals("SPECIAL")) {
                    // System.out.println("spesial masuk");
                    Move moveBaru = new Move(Integer.parseInt(line[0]), TypeMove.SPECIAL, line[2], ElementType.valueOf(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[8]));
                    allMoves.add(moveBaru);
                } 
            }

            CSVReader readerMonster = new CSVReader(new File(Main.class.getResource("monsterpool.csv").toURI()), ";");
            readerMonster.setSkipHeader(true);
            List<String[]> linesMonster = readerMonster.read();

            allMonster = new ArrayList<>();
            
            
            for (String[] line : linesMonster) {
                listElmnt = new ArrayList<>();
                // String[] elmnt = {line[2]};
                // if (isContainsComa(line[2])){
                //     elmnt = line[2].split(",");
                // }
                String[] elmnt = line[2].split(",");
                List<ElementType> listElmntType;
                listElmntType = readElmnt(elmnt);
                String[] baseStat = line[3].split(",");
                
                String[] monsterMoveID = line[4].split(",");
                
                listMonsterMoves = new ArrayList<Move>();
                Move defaultMove = new Move();
                Move skipMove = new Move("skip");
                listMonsterMoves.add(defaultMove);
                listMonsterMoves.add(skipMove);
                for(String moveID : monsterMoveID) {
                    // for(Move move : allMoves) {
                        //     if(move.getMoveID()==Integer.valueOf(moveID)) {
                    //         // System.out.println("tessss");
                    //         listMonsterMoves.add(move);
                    //         //System.out.println(move.getName());
                    //     }
                    // }
                    int theID = Integer.valueOf(moveID);
                    if (getMoveOFID(theID)!= null){
                        listMonsterMoves.add(getMoveOFID(theID));
                    }
                }
                Monster monsterBaru = new Monster(Integer.parseInt(line[0]), line[1], listElmntType, listMonsterMoves, Double.parseDouble(baseStat[0]), Double.parseDouble(baseStat[1]), Double.parseDouble(baseStat[2]), Double.parseDouble(baseStat[3]), Double.parseDouble(baseStat[4]), Double.parseDouble(baseStat[5]));
                this.allMonster.add(monsterBaru);
                // monsterBaru.printElementTypes();
                // System.out.println(" ");
                // System.out.println(" ");
                // System.out.println("ss");
                // System.out.println(allMonster.get(0).getMonsterID()); 
               // monsterBaru = null;
                // System.out.println("baba");
                // System.out.println(monsterBaru.getMonsterID());
            }
            //allMonster.get(0).getmoves();
            // System.out.println(allMoves.get(0).moveName());

            CSVReader readerEffectivity = new CSVReader(new File(Main.class.getResource("movepool.csv").toURI()), ";");
            readerEffectivity.setSkipHeader(true);
            effectivity = readerEffectivity.read();
        } catch (IOException e){

            System.out.println("cobalagi");
        } catch (URISyntaxException e) {
            System.out.println("cobalagi2");
        }
    }

    
}
