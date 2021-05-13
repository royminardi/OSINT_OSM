

package it.osint;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import hu.supercluster.overpasser.library.output.OutputFormat;
import hu.supercluster.overpasser.library.output.OutputModificator;
import hu.supercluster.overpasser.library.output.OutputOrder;
import hu.supercluster.overpasser.library.output.OutputVerbosity;
import hu.supercluster.overpasser.library.query.OverpassQuery;

public class Query {
	public static void main(String[] args) {
		String[] filename= {"banche", "rifornimenti", "atm",
				};
		int i1=new Query().Interroga(filename[0],"amenity", "bank");
		int i2=new Query().Interroga(filename[1],"amenity", "fuel");
		int i3=new Query().Interroga(filename[2],"amenity", "atm");
		
		System.out.println(filename[0] + " = " + i1);
		System.out.println(filename[1] + " = " + i2);
		System.out.println(filename[2] + " = " + i3);
	}	
		
	/**
	 * @param nomefile indica il nome del file da generare
	 * @param tipologia indica la chiave di ricerca
	 * @param descrizione indica il valore di ricerca
	 * @return il numero di occorrenze
	 */
	public int Interroga (String nomefile, String tipologia,String descrizione) {
		String server = "http://overpass-api.de/api/interpreter?data=";
		String totaleNWR = null;
		String interrogazione = new OverpassQuery()
				.format(OutputFormat.XML)
				.timeout(300) // 300 secondi= 5 minuti (default=180 secondi)
				.filterQuery()
				.nwr()
				.tag(tipologia, descrizione)
				.boundingBox(36.95, 15.08, 37.12, 15.34)
				.end()
				.output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 999) // massimo 999
				.build();
		try (AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient()) {
			Future<Response> f = asyncHttpClient.prepareGet(server + interrogazione).execute();
				Response r = f.get();
				String risultato = r.getResponseBody(StandardCharsets.UTF_8);
			System.out.println(risultato);
			try (BufferedWriter outputFile =
				Files.newBufferedWriter(Paths.get(nomefile + ".osm.xml"), StandardCharsets.UTF_8)) {
				outputFile.write(risultato);
				totaleNWR=risultato;
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("La query Overpass QL è");
		System.out.println(interrogazione);
		System.out.println("il numero di nodi è: " + Occorrenze(totaleNWR,"node id"));
		System.out.println("il numero di vie è: " + Occorrenze(totaleNWR,"way id"));
		System.out.println("il numero di relazioni è: " + Occorrenze(totaleNWR,"rel id"));
		System.out.println("il totale di occorrenze è " + Occorrenze(totaleNWR,"id="));
		return Occorrenze(totaleNWR,"id=");
	}

/**
 * @param str la stringa su cui eseguire la ricerca
 * @param id la sottostringa da cercare
 * @return il numero di occorrenze
 */
	public static int Occorrenze(String str, String id) {
		int conta = 0;
		Pattern p= Pattern.compile(id);
		Matcher m=p.matcher(str);
		while (m.find()) {
			conta++;
		}
		return conta;
	}
}

