

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
	public static void main(String[] args) throws InterruptedException {
		String[] filename= {"banche", "atm", "rifornimenti",
				"elisuperficie", "police","ufficiPostali", 
				"municipio", "mercato", "saleDaGioco per magiorenni", 
				"saleDaGioco libere", "stabilimentiBalneari", "areeGiocoBambini",
				"bunckerMilitari", "areePericolose", "ufficiGoverno",
				"stazioniFerroviarie", "fermateAutobus", "supermercati",
				"calzature", "gioielleria", "bevandeAlcoliche",
				"pelletteria", "orologeria", "cellulari",
				"galleriaArte", "centriScommesse", "prestaSoldi",
				"tabaccai"};
		
		
		
//		int i0=new Query().Interroga("",filename[0],"amenity", "bank");
//		Thread.sleep(30000);
//		int i1=new Query().Interroga("",filename[1],"amenity", "atm");
//		Thread.sleep(30000);
//		int i2=new Query().Interroga("",filename[2],"amenity", "fuel");
//		Thread.sleep(30000);
//		int i3=new Query().Interroga("",filename[3],"aeroway", "helipad");
//		Thread.sleep(30000);
//		int i4=new Query().Interroga("",filename[4],"amenity", "police");
//		Thread.sleep(30000);
//		int i5=new Query().Interroga("",filename[5],"amenity", "post_office");
//		Thread.sleep(30000);
//		int i6=new Query().Interroga("",filename[6],"amenity", "townhall");
//		Thread.sleep(30000);
//		int i7=new Query().Interroga("",filename[7],"amenity", "marketplace");
//		Thread.sleep(30000);
//		int i8=new Query().Interroga("",filename[8],"leisure", "adult_gaming_centre");
//		Thread.sleep(30000);
//		int i9=new Query().Interroga("",filename[9],"leisure", "amusement_arcade");
//		Thread.sleep(30000);
//		int i10=new Query().Interroga("",filename[10],"leisure", "beach_resort");
//		Thread.sleep(30000);
//		int i11=new Query().Interroga("",filename[11],"leisure", "playground");
//		Thread.sleep(30000);
//		int i12=new Query().Interroga("",filename[12],"military", "bunker");
//		Thread.sleep(30000);
//		int i13=new Query().Interroga("",filename[13],"military", "danger_area");
//		Thread.sleep(30000);
//		int i14=new Query().Interroga("",filename[14],"office", "government");
//		Thread.sleep(30000);
		int i15=new Query().Interroga("",filename[15],"public_transport", "station");
		Thread.sleep(30000);
		int i16=new Query().Interroga("",filename[16],"public_transport", "platform");
		Thread.sleep(30000);
		int i17=new Query().Interroga("",filename[17],"shop", "supermarket");
		Thread.sleep(30000);
		int i18=new Query().Interroga("",filename[18],"shop", "shoes");
		Thread.sleep(30000);
		int i19=new Query().Interroga("",filename[19],"shop", "jewelry");
		Thread.sleep(30000);
		int i20=new Query().Interroga("",filename[20],"shop", "alcohol");
		Thread.sleep(30000);
		int i21=new Query().Interroga("",filename[21],"shop", "leather");
		Thread.sleep(30000);
		int i22=new Query().Interroga("",filename[22],"shop", "watches");
		Thread.sleep(30000);
		int i23=new Query().Interroga("",filename[23],"shop", "mobile_phone");
		Thread.sleep(30000);
		int i24=new Query().Interroga("",filename[24],"shop", "art");
		Thread.sleep(30000);
		int i25=new Query().Interroga("",filename[25],"shop", "bookmaker");
		Thread.sleep(30000);
		int i26=new Query().Interroga("",filename[26],"shop", "money_lender");
		Thread.sleep(30000);
		int i27=new Query().Interroga("",filename[27],"shop", "tobacco");

		
//		System.out.println(filename[0] + " = " + i0);
//		System.out.println(filename[1] + " = " + i1);
//		System.out.println(filename[2] + " = " + i2);
//		System.out.println(filename[3] + " = " + i3);
//		System.out.println(filename[4] + " = " + i4);
//		System.out.println(filename[5] + " = " + i5);
//		System.out.println(filename[6] + " = " + i6);
//		System.out.println(filename[7] + " = " + i7);
//		System.out.println(filename[8] + " = " + i8);
//		System.out.println(filename[9] + " = " + i9);
//		System.out.println(filename[10] + " = " + i10);
//		System.out.println(filename[11] + " = " + i11);
//		System.out.println(filename[12] + " = " + i12);
//		System.out.println(filename[13] + " = " + i13);
//		System.out.println(filename[14] + " = " + i14);
		System.out.println(filename[15] + " = " + i15);
		System.out.println(filename[16] + " = " + i16);
		System.out.println(filename[17] + " = " + i17);
		System.out.println(filename[18] + " = " + i18);
		System.out.println(filename[19] + " = " + i19);
		System.out.println(filename[20] + " = " + i20);
		System.out.println(filename[21] + " = " + i21);
		System.out.println(filename[22] + " = " + i22);
		System.out.println(filename[23] + " = " + i23);
		System.out.println(filename[24] + " = " + i24);
		System.out.println(filename[25] + " = " + i25);
		System.out.println(filename[26] + " = " + i26);
		System.out.println(filename[27] + " = " + i27);
		
	}	
		
	/**
	 * @param nomefile indica il nome del file da generare
	 * @param tipologia indica la chiave di ricerca
	 * @param descrizione indica il valore di ricerca
	 * @return il numero di occorrenze
	 */
	public int Interroga (String type, String nomefile, String tipologia,String descrizione) {
		String server = "http://overpass-api.de/api/interpreter?data=";
		String totaleNWR = null;
		String interrogazione=""; 
		switch (type) {
			case "node":
				interrogazione = new OverpassQuery()
					.format(OutputFormat.XML)
					.timeout(300) // 300 secondi= 5 minuti (default=180 secondi)
					.filterQuery()
					.area("Siracusa")
					.adminLevel(8)
					.wikipedia("it:Siracusa")
					.searchArea()
					.node()
					.tag(tipologia, descrizione)
					.end()
					.output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 999) // massimo 999
					.build();
				break;
			case "way":
				interrogazione = new OverpassQuery()
					.format(OutputFormat.XML)
					.timeout(300) // 300 secondi= 5 minuti (default=180 secondi)
					.filterQuery()
					.area("Siracusa")
					.adminLevel(8)
					.wikipedia("it:Siracusa")
					.searchArea()
					.way()
					.tag(tipologia, descrizione)
					.end()
					.output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 999) // massimo 999
					.build();
				break;
			case "rel":
				interrogazione = new OverpassQuery()
					.format(OutputFormat.XML)
					.timeout(300) // 300 secondi= 5 minuti (default=180 secondi)
					.filterQuery()
					.area("Siracusa")
					.adminLevel(8)
					.wikipedia("it:Siracusa")
					.searchArea()
					.rel()
					.tag(tipologia, descrizione)
					.end()
					.output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 999) // massimo 999
					.build();
				break;
			default:
				interrogazione = new OverpassQuery()
					.format(OutputFormat.XML)
					.timeout(300) // 300 secondi= 5 minuti (default=180 secondi)
					.filterQuery()
					.area("Siracusa")
					.adminLevel(8)
					.wikipedia("it:Siracusa")
					.searchArea()
					.nwr()
					.tag(tipologia, descrizione)
					.end()
					.output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 999) // massimo 999
					.build();
		}
		
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
		System.out.println("il numero di relazioni è: " + Occorrenze(totaleNWR,"relation id"));
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

