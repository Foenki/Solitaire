package Core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chemin
{
    List< Coup > coups;

    public Chemin()
    {
        this.coups = new ArrayList<Coup>();
    }

    public Chemin(Chemin chemin)
    {
        this.coups = new ArrayList<>();
        add(chemin);
    }

    public String toString()
    {
        String s = new String();
        for(Coup c : coups)
        {
            s += c.toString() + '\n';
        }
        return s;
    }

    public void writeToFile(String fileName)
    {
        FileWriter rapport = null;
        try
        {
            rapport = new FileWriter(fileName, false);
            rapport.write(toString());
            rapport.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public int size()
    {
        return coups.size();
    }

    public List< Coup > getCoups()
    {
        return coups;
    }

    public Coup getCoup(int i)
    {
        return coups.get(i);
    }

    public void add(Coup coup)
    {
        coups.add(coup);
    }

    public void remove(int i)
    {
        coups.remove(i);
    }

    public void add(Chemin chemin)
    {
        coups.addAll(chemin.getCoups());
    }
}
