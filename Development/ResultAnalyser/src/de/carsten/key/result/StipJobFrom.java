package de.carsten.key.result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.carsten.key.Result;
import de.carsten.key.network.Job;

public class StipJobFrom {

	private static final File JOB_IN = new File("zwischenergebnisse_in.txt");
	private static final File JOB_OUT = new File("zwischenergebnisse_out.txt");
	
	public static void main(String[] args) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(JOB_IN));
				BufferedWriter bw = new BufferedWriter(new FileWriter(JOB_OUT))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
//				Job j = gson.fromJson(line, Job.class);
//				if(j.getMethod().equals("trimToSize"))
				Result r = gson.fromJson(line, Result.class);
				if(r.getName().startsWith("java.util.ArrayList[java.util.ArrayList::trimToSize"))
					continue;
				bw.write(line);
				bw.newLine();
			}
		}
	}
}
