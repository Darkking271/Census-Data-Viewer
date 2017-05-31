package Cache;

import java.io.*;
import java.net.URL;

/**
 * Created by Alex on 3/31/2017.
 */
public class Loader{
    //&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2

    public static void createFiles()throws IOException {
        File newDir = new File(System.getProperty("user.dir") + File.separator + "runfiles");
        if (!newDir.exists()){
            newDir.mkdir();
        }

        createFile("month", 2016, "http://api.census.gov/data/2016/pep/natmonthly?get=POP&for=us:*&MONTHLY=71:82&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");
        createFile("month", 2015, "http://api.census.gov/data/2015/pep/natmonthly?get=POP&for=us:*&MONTHLY=59:70&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");
        createFile("month", 2014, "http://api.census.gov/data/2015/pep/natmonthly?get=POP&for=us:*&MONTHLY=47:58&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");

        createFile("state", 2016, "http://api.census.gov/data/2016/pep/charagegroups?get=POP,GEONAME&for=state:*&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");
        createFile("state", 2015, "http://api.census.gov/data/2015/pep/charagegroups?get=POP,GEONAME&for=state:*&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");
        createFile("state", 2014, "http://api.census.gov/data/2014/pep/natstprc18?get=POPEST18PLUS2014&for=state:*&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");


        createFile("total", 2016, "http://api.census.gov/data/2016/pep/charagegroups?get=POP,GEONAME&for=us:*&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");
        createFile("total", 2015, "http://api.census.gov/data/2015/pep/charagegroups?get=POP,GEONAME&for=us:*&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");
        createFile("total", 2014, "http://api.census.gov/data/2014/pep/natstprc18?get=POPEST18PLUS2014&for=us:*&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");

        createTree(System.getProperty("user.dir") + File.separator + "runfiles" + File.separator + "root.bt",
                System.getProperty("user.dir") + File.separator + "runfiles" + File.separator + "value.raf",
                "http://api.census.gov/data/2010/surname?get=NAME,COUNT&RANK=1:1000&key=70938b51725b84860251b1b3ac7dc6ecb2974cb2");

    }

    public static void createFile(String type, int year, String url) throws IOException {

        String s = File.separator;
        String folder = "runfiles";
        File newFile = new File(System.getProperty("user.dir") + s + folder + s + type + year + ".pop");
        if (!newFile.exists()) {
            URL site = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(site.openStream()));
            newFile.createNewFile();

            FileWriter fileW = new FileWriter(newFile);
            BufferedWriter buffW = new BufferedWriter(fileW);

            String inputLine = in.readLine();
            while((inputLine = in.readLine()) != null){
                buffW.write(inputLine);
                buffW.newLine();
            }
            in.close();
            buffW.close();
        }
        else{
            return;
        }
    }

    public static void createTree(String rootLoc, String rafLoc, String url)throws IOException{
        BTree<String> tree = new BTree<>(32, rootLoc, rafLoc);
        File newRoot = new File(rootLoc);
        File newRaf = new File(rafLoc);
        File cluster = new File(System.getProperty("user.dir") + File.separator + "runfiles" + File.separator + "clusters.clu");
        if (!newRoot.exists() || !newRaf.exists() || !cluster.exists()){
            URL site = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(site.openStream()));

            String inputLine = in.readLine();
            while((inputLine = in.readLine()) != null){
                String[] data = inputLine.split("\"");
                tree.put(data[1], Integer.parseInt(data[3]));
            }
            tree.save();
            KMeans clusters = new KMeans(300, tree.size());
            for (String s : tree.keySet()){
                clusters.add(s, tree.get(s));
            }
            clusters.run();
            if (!cluster.exists()){
                FileOutputStream fileOut =
                        new FileOutputStream(System.getProperty("user.dir") + File.separator + "runfiles" + File.separator + "clusters.clu");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(clusters);
                out.close();
                fileOut.close();
            }
        }
    }
}
