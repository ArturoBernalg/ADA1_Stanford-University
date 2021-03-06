import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class KargerMinCut
{
    public HashMap<Integer, ArrayList<Integer>> graphy = new HashMap<Integer, ArrayList<Integer>>();
    public HashMap<Integer, ArrayList<Integer>> graphy2 = new HashMap<Integer, ArrayList<Integer>>();
    int result=0;
    int finalResult;

    KargerMinCut()
    {
        File input=new File("src/main/resources/kargerMinCut.txt");
        Scanner scannerLine, scannerRow;
        boolean isFirst = true;
        ArrayList<Integer> line;
        int key=0;

        try
        {
            scannerLine=new Scanner(input);
            while(scannerLine.hasNextLine())
            {
                scannerRow = new Scanner(scannerLine.nextLine());
                isFirst = true;
                line = new ArrayList<Integer>();
                while(scannerRow.hasNextInt())
                {
                    if(isFirst)
                    {
                        isFirst = false;
                        key = scannerRow.nextInt();
                    }
                    else
                    {
                        line.add(scannerRow.nextInt());
                    }
                }
                graphy2.put(key, line);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    void contraction()
    {

        //choose a line randomly
        List<Integer> endAList,endBList, bContainList;
        int endA, endB;
        Object[] graphyArray;

        graphyArray=graphy.keySet().toArray();
        endA=(Integer)graphyArray[(int)(Math.random()*graphyArray.length-1)];
        endAList=graphy.get(endA);
        endB = endAList.get((int)(Math.random()*(endAList.size()-1)));

        endBList = graphy.get(endB);
        for(int i=0; i<endBList.size(); i++)
        {
            bContainList = graphy.get(endBList.get(i));

            bContainList.remove(new Integer(endB));
            bContainList.add(endA);

            //add to endalist
            if(endA!=endBList.get(i))
                endAList.add(endBList.get(i));

            //check for self loop
            while(bContainList.contains(endBList.get(i)))
                bContainList.remove(new Integer(endBList.get(i)));

        }


        graphy.remove(endB);


        if(graphy.size()<3)
        {
            Iterator<Entry<Integer, ArrayList<Integer>>> iter = graphy.entrySet().iterator();
            Map.Entry entry = (Map.Entry) iter.next();
            result = ((ArrayList<Integer>) entry.getValue()).size();
        }
        else
        {
            contraction();
        }
    }

    void Dcontraction()
    {
        boolean firstCompare=true;

        for(int i=0; i<(100); i++)
        {
            graphy.clear();
            Iterator<Entry<Integer, ArrayList<Integer>>> iter = graphy2.entrySet().iterator();
            while (iter.hasNext())
            {
                Map.Entry entry = (Map.Entry) iter.next();
                graphy.put((Integer)entry.getKey(),new ArrayList<Integer>((ArrayList<Integer>) entry.getValue()));
            }
            contraction();
            if(firstCompare)
            {
                finalResult = result;
                firstCompare = false;
            }
            if(result<finalResult)
                finalResult = result;
        }
    }

    public static void main(String[] args)
    {
        KargerMinCut kmc = new KargerMinCut();
        kmc.Dcontraction();
        System.out.println(kmc.finalResult);
    }
}