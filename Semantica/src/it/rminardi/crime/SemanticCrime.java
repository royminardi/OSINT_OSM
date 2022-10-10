package it.rminardi.crime;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.MouseInputListener;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import hu.supercluster.overpasser.library.output.OutputFormat;
import hu.supercluster.overpasser.library.output.OutputModificator;
import hu.supercluster.overpasser.library.output.OutputOrder;
import hu.supercluster.overpasser.library.output.OutputVerbosity;
import hu.supercluster.overpasser.library.query.OverpassQuery;
import sample7_swingwaypoints.SwingWaypoint;
import sample7_swingwaypoints.SwingWaypointOverlayPainter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class SemanticCrime {

	static int numTotale;
	static double[] latitude;
	static double[] longitude;
	
	/* ONTOLOGIA */
	static String defaultNameSpace = "http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#";
	Model modello = null;
	Model schema = null;
	InfModel inferredModello = null;
	
	JFrame frmSicurezzaDelTerritorio;
	
	String[] item= {
			"Archaeological_site",
			"Bank",
			"Beach_resort",
			"Bus_stops",
			"Cathedral",
			"Church",
			"Cinema",
			"Drinking_water_system",
			"Electric_generator",
			"Electrical_substation",
			"Fire_station",
			"Fuel_sale",
			"Gas_cylinder_dealer",
			"Government_office",
			"Grandstand",
			"Helipad",
			"Highways_and_junctions",
			"Historical_monument",
			"Hospital",
			"Jewellery",
			"Kindergarten",
			"Lighthouse",
			"Marketplace",
			"Museum",
			"National_police",
			"Petrol_station",
			"Pharmacy",
			"Pier",
			"Pipeline",
			"Place_of_worship",
			"Playground",
			"Post_office",
			"Prison",
			"Public_building",
			"Pyrotechnic_material_for_sale",
			"Railway_station",
			"School",
			"Shopping_center",
			"Silo",
			"Storage_tank",
			"Tobacco_store",
			"Town_hall",
			"Transmission_line", 
			"Warehouse",
			"Wooded_area", 	
	};
	
	String[] system= { 
			"Archaeological_site", "historic", "archaeological_site",
			"Bank","amenity", "bank", 
			"Beach_resort", "leisure", "beach_resort", 
			"Bus_stops", "public_transport", "platform", 
			"Cathedral", "building", "cathedral", 
			"Church", "building", "church", 
			"Cinema", "amenity", "cinema",
			"Drinking_water_system", "man_made", "water_works", 
			"Electric_generator", "power", "generator", 
			"Electrical_substation", "power", "substation", 
			"Fire_station", "amenity", "fire_station",
			"Fuel_sale", "shop", "fuel",
			"Gas_cylinder_dealer", "shop", "gas",
			"Government_office", "office", "government", 
			"Grandstand", "building", "grandstand", 
			"Helipad", "aeroway", "helipad", 
			"Highways_and_junctions", "highway", "motorway",
			"Historical_monument", "historic", "monument",
			"Hospital", "building", "hospital", 
			"Jewellery", "shop", "jewelry", 
			"Kindergarten", "amenity", "kindergarten",
			"Lighthouse", "man_made", "lighthouse", 
			"Marketplace", "amenity", "marketplace",
			"Museum", "tourism", "museum",
			"National_police", "amenity", "police", 
			"Petrol_station", "amenity", "fuel", 
			"Pharmacy", "amenity", "pharmacy",
			"Pier", "man_made", "pier",
			"Pipeline", "man_made", "pipeline", 
			"Place_of_worship", "amenity", "place_of_worship",
			"Playground", "leisure", "playground", 
			"Post_office", "amenity", "post_office", 
			"Prison", "amenity", "prison",
			"Public_building", "building", "public", 
			"Pyrotechnic_material_for_sale", "shop", "pyrotechnics",
			"Railway_station", "public_transport", "station", 
			"School", "building", "school", 
			"Shopping_center", "shop", "mall",
			"Silo", "man_made", "silo", 
			"Storage_tank", "man_made", "storage_tank", 
			"Tobacco_store", "shop", "tobacco",
			"Town_hall", "amenity", "townhall", 
			"Transmission_line", "power", "line", 
			"Warehouse", "building", "warehouse",
			"Wooded_area", "natural", "wood", 
	};
	
	String[] soggetto= {
			"",
			"System",
			"Anthropic hazard", 
			"Critical event of system",
			"System aspect",
			"Vulnerability",
			"Stakeholder"
	};
	
	int indice;
	/* Da usare per le query SPARQL */
	static String valSystem="";
	static String valHazard="";
	static String valEvent="";
	static String valAspect="";
	static String valVulnerability="";
	static String valStakeholder="";
	
	String comboSoggetto1="";
	String tempQuery1="", tempQuery2="", tempQuery3="", tempQuery4="", tempQuery5="", tempQuery6="";
	String itemQuery1="", itemQuery2="",itemQuery3="", itemQuery4="", itemQuery5="", itemQuery6="";
	String comboQuery2="";
	
	static Set<String> resultListSystem=new TreeSet<>();
	static Set<String> resultListHazard=new TreeSet<>();
	static Set<String> resultListEvent=new TreeSet<>();
	static Set<String> resultListAspect=new TreeSet<>();
	static Set<String> resultListVulnerability=new TreeSet<>();
	static Set<String> resultListStakeholder=new TreeSet<>();
	
	String infoMap="";
	
	private JTextField txttotale;
	private JTextField textFieldSoggetto2;
	private JTextField textFieldSoggetto3;
	private JTextField textFieldSoggetto4;
	private JTextField textFieldSoggetto5;
	private JTextField textFieldSoggetto6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SemanticCrime window = new SemanticCrime();
					window.frmSicurezzaDelTerritorio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SemanticCrime() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/* ONTOLOGIA */
		OntModel model1=ModelFactory.createOntologyModel();
		model1.read("file:///d:/TERMINUS-crime.owl","RDF/XML");
		
		frmSicurezzaDelTerritorio = new JFrame();
		frmSicurezzaDelTerritorio.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sives\\Eclipse progetti Roy\\police1.png"));
		frmSicurezzaDelTerritorio.setTitle("Crime Prevention Platform");
		frmSicurezzaDelTerritorio.setBounds(100, 100, 1024, 600);
		frmSicurezzaDelTerritorio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSicurezzaDelTerritorio.getContentPane().setLayout(null);
		
		JTabbedPane tabGenerale = new JTabbedPane(JTabbedPane.TOP);
		tabGenerale.setBounds(10, 11, 990, 530);
		frmSicurezzaDelTerritorio.getContentPane().add(tabGenerale);
		
		
		/* 1° pannello */
		
		JPanel tabricerca = new JPanel();
		tabGenerale.addTab("Search POI", null, tabricerca, null);
		tabricerca.setLayout(null);
		
		txttotale = new JTextField();
		txttotale.setEditable(false);
		txttotale.setBounds(486, 42, 86, 20);
		tabricerca.add(txttotale);
		txttotale.setColumns(10);
		
		JPanel tabOSM = new JPanel();
		tabGenerale.addTab("Results on OpenStreetMap", null, tabOSM, null);
		tabOSM.setLayout(null);
		
		JTextArea txtareaosm = new JTextArea();
		/* si aggiunge un JScrollPane */
		JScrollPane scrollpaneosm = new JScrollPane(txtareaosm);
		
		scrollpaneosm.setAutoscrolls(true);
		
		scrollpaneosm.setBounds(10, 11, 965, 480);
		tabOSM.add(scrollpaneosm);
		scrollpaneosm.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel tabOWL = new JPanel();
		tabGenerale.addTab("SPARQL query results", null, tabOWL, null);
		tabOWL.setLayout(null);
		
		JTextArea txtareaowl = new JTextArea();
		/* Setta il font */
		txtareaowl.setFont(new Font("Monospaced",Font.PLAIN,12));
		
		JScrollPane scrollpaneowl = new JScrollPane(txtareaowl);
		scrollpaneowl.setBounds(10, 11, 965, 480);
		tabOWL.add(scrollpaneowl);
		scrollpaneowl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lbltotale = new JLabel("n\u00B0 occurrences");
		lbltotale.setBounds(398, 45, 89, 14);
		tabricerca.add(lbltotale);
		
		JTextArea txtsparql = new JTextArea();
		txtsparql.setFont(new Font("Monospaced",Font.PLAIN,12));
		JScrollPane scrollpanesparql = new JScrollPane(txtsparql);
		scrollpanesparql.setBounds(86, 350, 756, 141);
		tabricerca.add(scrollpanesparql);
		scrollpanesparql.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JButton btnsparql = new JButton("Run query");
		btnsparql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String queryString=txtsparql.getText();
				Query query=QueryFactory.create(queryString);
			    QueryExecution qe=QueryExecutionFactory.create(query, model1);
			    ResultSet results= qe.execSelect();
			    /* modificato */
//			    ResultSetFormatter.out(System.out, results, query);
			    ByteArrayOutputStream risultato;
				ResultSetFormatter.out(risultato=new ByteArrayOutputStream(), results, query);
			    /* restituisce il formato XML come stringa */
//			    String risultato=ResultSetFormatter.asXMLString(results);
			    txtareaowl.setText(risultato.toString());
			}
		});
		btnsparql.setActionCommand("Run");
		btnsparql.setBounds(852, 466, 120, 23);
		tabricerca.add(btnsparql);
		
		JLabel lblsparql = new JLabel("Free SPARQL query");
		lblsparql.setBounds(86, 325, 147, 14);
		tabricerca.add(lblsparql);
	
		
		JComboBox comboBoxQuery6 = new JComboBox();
		comboBoxQuery6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* recupera il valore e ne rimuove gli eventuali spazi */
				Object tmpQ6=comboBoxQuery6.getSelectedItem();
				if (tmpQ6 != null) {
					tempQuery6=tmpQ6.toString().replaceAll("\\s+","");
					/* memorizza il valore solo se modificato */
					if (tempQuery6 != itemQuery6) {
						itemQuery6=tempQuery6;
					}
				}
				switch (indice) {
				case 0:
					
					break;
				case 1:
					valStakeholder=itemQuery6;
					break;
				case 2:
					valStakeholder=itemQuery6;
					break;
				case 3:
					valStakeholder=itemQuery6;
					break;
				case 4:
					valHazard=itemQuery6;
					break;
				case 5:
					valHazard=itemQuery6;
					break;
				case 6:
					valHazard=itemQuery6;
					break;
				}
			}
		});
		comboBoxQuery6.setBounds(220, 235, 649, 22);
		tabricerca.add(comboBoxQuery6);
	
	
		JComboBox comboBoxQuery5 = new JComboBox();
		comboBoxQuery5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxQuery6.removeAllItems();
				/* recupera il valore e ne rimuove gli eventuali spazi */
				Object tmpQ5=comboBoxQuery5.getSelectedItem();
				if (tmpQ5 != null) {
					tempQuery5=tmpQ5.toString().replaceAll("\\s+","");
					/* memorizza il valore solo se modificato */
					if (tempQuery5 != itemQuery5) {
						itemQuery5=tempQuery5;
					}
				}
				
				switch (indice) {
				case 0:
					
					break;
				case 1:
					valHazard=itemQuery5;
					String comboqStakeholder12345=Query("qStakeholder12345",itemQuery1,itemQuery2,"");
					Query queryBox1= QueryFactory.create(comboqStakeholder12345);
					QueryExecution qexecBox1=QueryExecutionFactory.create(queryBox1, model1);
					try {
						List<QuerySolution> resultList1 = null;
						ResultSet rsBox1 = qexecBox1.execSelect();
						resultList1=ResultSetFormatter.toList(rsBox1);
						if (resultList1 != null) {
							String stringa1="";
							resultListStakeholder.clear();
							for (QuerySolution valore : resultList1) {
								stringa1=valore.toString();
								/* elimina i prefissi IRI */
								stringa1=stringa1.substring(stringa1.indexOf("#")+1, stringa1.length()-3);
								resultListStakeholder.add(stringa1);
								/* prova per visualizzare su console */
							}
							for (String i : resultListStakeholder) {
								comboBoxQuery6.addItem(i);
							}
						}
					}
					finally {
						qexecBox1.close();
					}
					break;
				case 2:
					valVulnerability=itemQuery5;
					String comboqEvent2=Query("qStakeholder12345",itemQuery4,itemQuery3,"");
					Query queryBox2= QueryFactory.create(comboqEvent2);
					QueryExecution qexecBox2=QueryExecutionFactory.create(queryBox2, model1);
					try {
						List<QuerySolution> resultList2 = null;
						ResultSet rsBox2 = qexecBox2.execSelect();
						resultList2=ResultSetFormatter.toList(rsBox2);
						if (resultList2 != null) {
							String stringa2="";
							resultListStakeholder.clear();
							for (QuerySolution valore : resultList2) {
								stringa2=valore.toString();
								/* elimina i prefissi IRI */
								stringa2=stringa2.substring(stringa2.indexOf("#")+1, stringa2.length()-3);
								resultListStakeholder.add(stringa2);
							}
							for (String i : resultListStakeholder) {
								comboBoxQuery6.addItem(i);
							}
						}	
					}
					finally {
						qexecBox2.close();
					}
					break;
				case 3:
					valVulnerability=itemQuery5;
					/* Query che valorizza comboBoxQuery2 */
					String comboqHazard3=Query("qStakeholder12345",itemQuery4,itemQuery3,"");
					Query queryBox3= QueryFactory.create(comboqHazard3);
					QueryExecution qexecBox3=QueryExecutionFactory.create(queryBox3, model1);
					try {
						List<QuerySolution> resultList3 = null;
						ResultSet rsBox3 = qexecBox3.execSelect();
						resultList3=ResultSetFormatter.toList(rsBox3);
						if (resultList3 != null) {
							String stringa3="";
							resultListStakeholder.clear();
							for (QuerySolution valore : resultList3) {
								stringa3=valore.toString();
								/* elimina i prefissi IRI */
								stringa3=stringa3.substring(stringa3.indexOf("#")+1, stringa3.length()-3);
								resultListStakeholder.add(stringa3);
							}
							for (String i : resultListStakeholder) {
								comboBoxQuery6.addItem(i);
							}
						}	
					}
					finally {
						qexecBox3.close();
					}
					break;
				case 4:
					valEvent=itemQuery5;
					/* Query che valorizza comboBoxQuery2 */
					String comboqSystem4=Query("qHazard1456",itemQuery2,itemQuery5,"");
					Query queryBox4= QueryFactory.create(comboqSystem4);
					QueryExecution qexecBox4=QueryExecutionFactory.create(queryBox4, model1);
					try {
						List<QuerySolution> resultList4 = null;
						ResultSet rsBox4 = qexecBox4.execSelect();
						resultList4=ResultSetFormatter.toList(rsBox4);
						if (resultList4 != null) {
							String stringa4="";
							resultListHazard.clear();
							for (QuerySolution valore : resultList4) {
								stringa4=valore.toString();
								/* elimina i prefissi IRI */
								stringa4=stringa4.substring(stringa4.indexOf("#")+1, stringa4.length()-3);
								resultListHazard.add(stringa4);
							}
							for (String i : resultListHazard) {
								comboBoxQuery6.addItem(i);
							}
						}	
					}
					finally {
						qexecBox4.close();
					}
					break;
				case 5:
					valEvent=itemQuery5;
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect5=Query("qHazard1456",itemQuery3,itemQuery5,"");
					Query queryBox5= QueryFactory.create(comboqAspect5);
					QueryExecution qexecBox5=QueryExecutionFactory.create(queryBox5, model1);
					try {
						List<QuerySolution> resultList5 = null;
						ResultSet rsBox5 = qexecBox5.execSelect();
						resultList5=ResultSetFormatter.toList(rsBox5);
						if (resultList5 != null) {
							String stringa5="";
							resultListHazard.clear();
							for (QuerySolution valore : resultList5) {
								stringa5=valore.toString();
								/* elimina i prefissi IRI */
								stringa5=stringa5.substring(stringa5.indexOf("#")+1, stringa5.length()-3);
								resultListHazard.add(stringa5);
							}
							for (String i : resultListHazard) {
								comboBoxQuery6.addItem(i);
							}
						}
					}
					
					finally {
						qexecBox5.close();
					}
					break;
				case 6:
					valEvent=itemQuery5;
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect6=Query("qHazard1456",itemQuery3,itemQuery5,"");
					Query queryBox6= QueryFactory.create(comboqAspect6);
					QueryExecution qexecBox6=QueryExecutionFactory.create(queryBox6, model1);
					try {
						List<QuerySolution> resultList6 = null;
						ResultSet rsBox6 = qexecBox6.execSelect();
						resultList6=ResultSetFormatter.toList(rsBox6);
						if (resultList6 != null) {
							String stringa6="";
							resultListHazard.clear();
							for (QuerySolution valore : resultList6) {
								stringa6=valore.toString();
								/* elimina i prefissi IRI */
								stringa6=stringa6.substring(stringa6.indexOf("#")+1, stringa6.length()-3);
								resultListHazard.add(stringa6);
							}
							for (String i : resultListHazard) {
								comboBoxQuery6.addItem(i);
							}
						}
					}
					finally {
						qexecBox6.close();
					}
					break;
				}
			}
		});

		comboBoxQuery5.setBounds(220, 204, 649, 22);
		tabricerca.add(comboBoxQuery5);
		
	JComboBox comboBoxQuery4 = new JComboBox();
	comboBoxQuery4.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			comboBoxQuery5.removeAllItems();
			comboBoxQuery6.removeAllItems();
			/* recupera il valore e ne rimuove gli eventuali spazi */
			Object tmpQ4=comboBoxQuery4.getSelectedItem();
			if (tmpQ4 != null) {
				tempQuery4=tmpQ4.toString().replaceAll("\\s+","");
				/* memorizza il valore solo se modificato */
				if (tempQuery4 != itemQuery4) {
					itemQuery4=tempQuery4;
				}
			}
			
			switch (indice) {
			case 0:
				
				break;
			case 1:
				valEvent=itemQuery4;
				String comboqHazard1456=Query("qHazard1456",itemQuery1,itemQuery4,"");
				Query queryBox1= QueryFactory.create(comboqHazard1456);
				QueryExecution qexecBox1=QueryExecutionFactory.create(queryBox1, model1);
				try {
					List<QuerySolution> resultList1 = null;
					ResultSet rsBox1 = qexecBox1.execSelect();
					resultList1=ResultSetFormatter.toList(rsBox1);
					if (resultList1 != null) {
						String stringa1="";
						resultListHazard.clear();
						for (QuerySolution valore : resultList1) {
							stringa1=valore.toString();
							/* elimina i prefissi IRI */
							stringa1=stringa1.substring(stringa1.indexOf("#")+1, stringa1.length()-3);
							resultListHazard.add(stringa1);
						}
						for (String i : resultListHazard) {
							comboBoxQuery5.addItem(i);
						}
					}
				}
				finally {
					qexecBox1.close();
				}
				break;
			case 2:
				valSystem=itemQuery4;
				String comboqEvent2=Query("qVulnerability12346",itemQuery4,itemQuery3,"");
				Query queryBox2= QueryFactory.create(comboqEvent2);
				QueryExecution qexecBox2=QueryExecutionFactory.create(queryBox2, model1);
				try {
					List<QuerySolution> resultList2 = null;
					ResultSet rsBox2 = qexecBox2.execSelect();
					resultList2=ResultSetFormatter.toList(rsBox2);
					if (resultList2 != null) {
						String stringa2="";
						resultListVulnerability.clear();
						for (QuerySolution valore : resultList2) {
							stringa2=valore.toString();
							/* elimina i prefissi IRI */
							stringa2=stringa2.substring(stringa2.indexOf("#")+1, stringa2.length()-3);
							resultListVulnerability.add(stringa2);
						}
						for (String i : resultListVulnerability) {
							comboBoxQuery5.addItem(i);
						}
					}
				}
				finally {
					qexecBox2.close();
				}
				break;
			case 3:
				valSystem=itemQuery4;
				/* Query che valorizza comboBoxQuery2 */
				String comboqHazard3=Query("qVulnerability12346",itemQuery4,itemQuery3,"");
				Query queryBox3= QueryFactory.create(comboqHazard3);
				QueryExecution qexecBox3=QueryExecutionFactory.create(queryBox3, model1);
				try {
					List<QuerySolution> resultList3 = null;
					ResultSet rsBox3 = qexecBox3.execSelect();
					resultList3=ResultSetFormatter.toList(rsBox3);
					if (resultList3 != null) {
						String stringa3="";
						resultListVulnerability.clear();
						for (QuerySolution valore : resultList3) {
							stringa3=valore.toString();
							/* elimina i prefissi IRI */
							stringa3=stringa3.substring(stringa3.indexOf("#")+1, stringa3.length()-3);
							resultListVulnerability.add(stringa3);
						}
						for (String i : resultListVulnerability) {
							comboBoxQuery5.addItem(i);
						}
					}
				}
				finally {
					qexecBox3.close();
				}
				break;
			case 4:
				valStakeholder=itemQuery4;
				/* Query che valorizza comboBoxQuery2 */
				String comboqSystem4=Query("qEvent1456",itemQuery2,itemQuery1,"");
				Query queryBox4= QueryFactory.create(comboqSystem4);
				QueryExecution qexecBox4=QueryExecutionFactory.create(queryBox4, model1);
				try {
					List<QuerySolution> resultList4 = null;
					ResultSet rsBox4 = qexecBox4.execSelect();
					resultList4=ResultSetFormatter.toList(rsBox4);
					if (resultList4 != null) {
						String stringa4="";
						resultListEvent.clear();
						for (QuerySolution valore : resultList4) {
							stringa4=valore.toString();
							/* elimina i prefissi IRI */
							stringa4=stringa4.substring(stringa4.indexOf("#")+1, stringa4.length()-3);
							resultListEvent.add(stringa4);
						}
						for (String i : resultListEvent) {
							comboBoxQuery5.addItem(i);
						}
					}
				}
				finally {
					qexecBox4.close();
				}
				break;
			case 5:
				valStakeholder=itemQuery4;
				/* Query che valorizza comboBoxQuery2 */
				String comboqAspect5=Query("qEvent1456",itemQuery3,itemQuery2,"");
				Query queryBox5= QueryFactory.create(comboqAspect5);
				QueryExecution qexecBox5=QueryExecutionFactory.create(queryBox5, model1);
				try {
					List<QuerySolution> resultList5 = null;
					ResultSet rsBox5 = qexecBox5.execSelect();
					resultList5=ResultSetFormatter.toList(rsBox5);
					if (resultList5 != null) {
						String stringa5="";
						resultListEvent.clear();
						for (QuerySolution valore : resultList5) {
							stringa5=valore.toString();
							/* elimina i prefissi IRI */
							stringa5=stringa5.substring(stringa5.indexOf("#")+1, stringa5.length()-3);
							resultListEvent.add(stringa5);
						}
						for (String i : resultListEvent) {
							comboBoxQuery5.addItem(i);
						}
					}
				}
				finally {
					qexecBox5.close();
				}
				break;
			case 6:
				valVulnerability=itemQuery4;
				/* Query che valorizza comboBoxQuery2 */
				String comboqAspect6=Query("qEvent1456",itemQuery3,itemQuery2,"");
				Query queryBox6= QueryFactory.create(comboqAspect6);
				QueryExecution qexecBox6=QueryExecutionFactory.create(queryBox6, model1);
				try {
					List<QuerySolution> resultList6 = null;
					ResultSet rsBox6 = qexecBox6.execSelect();
					resultList6=ResultSetFormatter.toList(rsBox6);
					if (resultList6 != null) {
						String stringa6="";
						resultListEvent.clear();
						for (QuerySolution valore : resultList6) {
							stringa6=valore.toString();
							/* elimina i prefissi IRI */
							stringa6=stringa6.substring(stringa6.indexOf("#")+1, stringa6.length()-3);
							resultListEvent.add(stringa6);
						}
						for (String i : resultListEvent) {
							comboBoxQuery5.addItem(i);
						}
					}
				}
				finally {
					qexecBox6.close();
				}
				break;
			}
		}
	});

	comboBoxQuery4.setBounds(220, 174, 649, 22);
	tabricerca.add(comboBoxQuery4);
	
	JComboBox comboBoxQuery3 = new JComboBox();
	comboBoxQuery3.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			comboBoxQuery4.removeAllItems();
			comboBoxQuery5.removeAllItems();
			comboBoxQuery6.removeAllItems();
			/* recupera il valore e ne rimuove gli eventuali spazi */
			Object tmpQ3=comboBoxQuery3.getSelectedItem();
			if (tmpQ3 != null) {
				tempQuery3=tmpQ3.toString().replaceAll("\\s+","");
				/* memorizza il valore solo se modificato */
				if (tempQuery3 != itemQuery3) {
					itemQuery3=tempQuery3;
				}
			}
			
			switch (indice) {
			case 0:
				
				break;
			case 1:
				valVulnerability=itemQuery3;
				String comboqEvent1456=Query("qEvent1456",itemQuery1,itemQuery2,"");
				Query queryBox1= QueryFactory.create(comboqEvent1456);
				QueryExecution qexecBox1=QueryExecutionFactory.create(queryBox1, model1);
				try {
					List<QuerySolution> resultList1 = null;
					ResultSet rsBox1 = qexecBox1.execSelect();
					resultList1=ResultSetFormatter.toList(rsBox1);
					if (resultList1 != null) {
						String stringa1="";
						resultListEvent.clear();
						for (QuerySolution valore : resultList1) {
							stringa1=valore.toString();
							/* elimina i prefissi IRI */
							stringa1=stringa1.substring(stringa1.indexOf("#")+1, stringa1.length()-3);
							resultListEvent.add(stringa1);
						}
						for (String i : resultListEvent) {
							comboBoxQuery4.addItem(i);
						}
					}
				}
				finally {
					qexecBox1.close();
				}
				break;
			case 2:
				valAspect=itemQuery3;
				String comboqEvent2=Query("qSystem23",itemQuery1,itemQuery2,itemQuery3);
				Query queryBox2= QueryFactory.create(comboqEvent2);
				QueryExecution qexecBox2=QueryExecutionFactory.create(queryBox2, model1);
				try {
					List<QuerySolution> resultList2 = null;
					ResultSet rsBox2 = qexecBox2.execSelect();
					resultList2=ResultSetFormatter.toList(rsBox2);
					if (resultList2 != null) {
						String stringa2="";
						resultListSystem.clear();
						for (QuerySolution valore : resultList2) {
							stringa2=valore.toString();
							/* elimina i prefissi IRI */
							stringa2=stringa2.substring(stringa2.indexOf("#")+1, stringa2.length()-3);
							resultListSystem.add(stringa2);
						}
						for (String i : resultListSystem) {
							comboBoxQuery4.addItem(i);
						}
					}
				}
				finally {
					qexecBox2.close();
				}
				break;
			case 3:
				valAspect=itemQuery3;
				/* Query che valorizza comboBoxQuery2 */
				String comboqHazard3=Query("qSystem23",itemQuery2,itemQuery1,itemQuery3);
				Query queryBox3= QueryFactory.create(comboqHazard3);
				QueryExecution qexecBox3=QueryExecutionFactory.create(queryBox3, model1);
				try {
					List<QuerySolution> resultList3 = null;
					ResultSet rsBox3 = qexecBox3.execSelect();
					resultList3=ResultSetFormatter.toList(rsBox3);
					if (resultList3 != null) {
						String stringa3="";
						resultListSystem.clear();
						for (QuerySolution valore : resultList3) {
							stringa3=valore.toString();
							/* elimina i prefissi IRI */
							stringa3=stringa3.substring(stringa3.indexOf("#")+1, stringa3.length()-3);
							resultListSystem.add(stringa3);
						}
						for (String i : resultListSystem) {
							comboBoxQuery4.addItem(i);
						}
					}
				}
				finally {
					qexecBox3.close();
				}
				break;
			case 4:
				valVulnerability=itemQuery3;
				/* Query che valorizza comboBoxQuery2 */
				String comboqSystem4=Query("qStakeholder12345",itemQuery2,itemQuery1,"");
				Query queryBox4= QueryFactory.create(comboqSystem4);
				QueryExecution qexecBox4=QueryExecutionFactory.create(queryBox4, model1);
				try {
					List<QuerySolution> resultList4 = null;
					ResultSet rsBox4 = qexecBox4.execSelect();
					resultList4=ResultSetFormatter.toList(rsBox4);
					if (resultList4 != null) {
						String stringa4="";
						resultListStakeholder.clear();
						for (QuerySolution valore : resultList4) {
							stringa4=valore.toString();
							/* elimina i prefissi IRI */
							stringa4=stringa4.substring(stringa4.indexOf("#")+1, stringa4.length()-3);
							resultListStakeholder.add(stringa4);
						}
						for (String i : resultListStakeholder) {
							comboBoxQuery4.addItem(i);
						}
					}
				}
				finally {
					qexecBox4.close();
				}
				break;
			case 5:
				valSystem=itemQuery3;
				/* Query che valorizza comboBoxQuery2 */
				String comboqAspect5=Query("qStakeholder12345",itemQuery3,itemQuery2,"");
				Query queryBox5= QueryFactory.create(comboqAspect5);
				QueryExecution qexecBox5=QueryExecutionFactory.create(queryBox5, model1);
				try {
					List<QuerySolution> resultList5 = null;
					ResultSet rsBox5 = qexecBox5.execSelect();
					resultList5=ResultSetFormatter.toList(rsBox5);
					if (resultList5 != null) {
						String stringa5="";
						resultListStakeholder.clear();
						for (QuerySolution valore : resultList5) {
							stringa5=valore.toString();
							/* elimina i prefissi IRI */
							stringa5=stringa5.substring(stringa5.indexOf("#")+1, stringa5.length()-3);
							resultListStakeholder.add(stringa5);
						}
						for (String i : resultListStakeholder) {
							comboBoxQuery4.addItem(i);
						}
					}
				}
				finally {
					qexecBox5.close();
				}
				break;
			case 6:
				valSystem=itemQuery3;
				/* Query che valorizza comboBoxQuery2 */
				String comboqAspect6=Query("qVulnerability12346",itemQuery3,itemQuery2,"");
				Query queryBox6= QueryFactory.create(comboqAspect6);
				QueryExecution qexecBox6=QueryExecutionFactory.create(queryBox6, model1);
				try {
					List<QuerySolution> resultList6 = null;
					ResultSet rsBox6 = qexecBox6.execSelect();
					resultList6=ResultSetFormatter.toList(rsBox6);
					if (resultList6 != null) {
						String stringa6="";
						resultListVulnerability.clear();
						for (QuerySolution valore : resultList6) {
							stringa6=valore.toString();
							/* elimina i prefissi IRI */
							stringa6=stringa6.substring(stringa6.indexOf("#")+1, stringa6.length()-3);
							resultListVulnerability.add(stringa6);
						}
						for (String i : resultListVulnerability) {
							comboBoxQuery4.addItem(i);
						}
					}
				}
				finally {
					qexecBox6.close();
				}
				break;
			}
		}
	});
	
	comboBoxQuery3.setBounds(220, 142, 649, 22);
	tabricerca.add(comboBoxQuery3);
		
		JComboBox comboBoxQuery2 = new JComboBox();
		comboBoxQuery2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxQuery3.removeAllItems();
				comboBoxQuery4.removeAllItems();
				comboBoxQuery5.removeAllItems();
				comboBoxQuery6.removeAllItems();
				/* recupera il valore e ne rimuove gli eventuali spazi */
				Object tmpQ2 = comboBoxQuery2.getSelectedItem();
				if (tmpQ2 != null) {
					tempQuery2=tmpQ2.toString().replaceAll("\\s+","");
				/* memorizza il valore solo se modificato */
					if (tempQuery2 != itemQuery2) {
						itemQuery2=tempQuery2;
					}
				}
				
				switch (indice) {
				case 0:
					break;
				case 1:
					valAspect=itemQuery2;
					String comboqVulnerability12346=Query("qVulnerability12346",itemQuery1,itemQuery2,"");
					Query queryBox1= QueryFactory.create(comboqVulnerability12346);
					QueryExecution qexecBox1=QueryExecutionFactory.create(queryBox1, model1);
					try {
						List<QuerySolution> resultList1 = null;
						ResultSet rsBox1 = qexecBox1.execSelect();
						resultList1=ResultSetFormatter.toList(rsBox1);
						if (resultList1 != null) {
							String stringa1="";
							resultListVulnerability.clear();
							for (QuerySolution valore : resultList1) {
								stringa1=valore.toString();
								/* elimina i prefissi IRI */
								stringa1=stringa1.substring(stringa1.indexOf("#")+1, stringa1.length()-3);
								resultListVulnerability.add(stringa1);
								/* prova per visualizzare su console */
							}
							for (String i : resultListVulnerability) {
								comboBoxQuery3.addItem(i);
							}
						}
					}
					finally {
						qexecBox1.close();
					}
					break;
				case 2:
					valEvent=itemQuery2;
					String comboqEvent2=Query("qAspect23",itemQuery2,"","");
					Query queryBox2= QueryFactory.create(comboqEvent2);
					QueryExecution qexecBox2=QueryExecutionFactory.create(queryBox2, model1);
					try {
						List<QuerySolution> resultList2 = null;
						ResultSet rsBox2 = qexecBox2.execSelect();
						resultList2=ResultSetFormatter.toList(rsBox2);
						if (resultList2 != null) {
							String stringa2="";
							resultListAspect.clear();
							for (QuerySolution valore : resultList2) {
								stringa2=valore.toString();
								/* elimina i prefissi IRI */
								stringa2=stringa2.substring(stringa2.indexOf("#")+1, stringa2.length()-3);
								resultListAspect.add(stringa2);
							}
							for (String i : resultListAspect) {
								comboBoxQuery3.addItem(i);
							}
						}
					}
					finally {
						qexecBox2.close();
					}
					break;
				case 3:
					valHazard=itemQuery2;
					/* Query che valorizza comboBoxQuery2 */
					String comboqHazard3=Query("qAspect23",itemQuery1,"","");
					Query queryBox3= QueryFactory.create(comboqHazard3);
					QueryExecution qexecBox3=QueryExecutionFactory.create(queryBox3, model1);
					try {
						List<QuerySolution> resultList3 = null;
						ResultSet rsBox3 = qexecBox3.execSelect();
						resultList3=ResultSetFormatter.toList(rsBox3);
						if (resultList3 != null) {
							String stringa3="";
							resultListAspect.clear();
							for (QuerySolution valore : resultList3) {
								stringa3=valore.toString();
								/* elimina i prefissi IRI */
								stringa3=stringa3.substring(stringa3.indexOf("#")+1, stringa3.length()-3);
								resultListAspect.add(stringa3);
							}
							for (String i : resultListAspect) {
								comboBoxQuery3.addItem(i);
							}
						}
					}
					finally {
						qexecBox3.close();
					}
					break;
				case 4:
					valSystem=itemQuery2;
					/* Query che valorizza comboBoxQuery2 */
					String comboqSystem4=Query("qVulnerability12346",itemQuery2,itemQuery1,"");
					Query queryBox4= QueryFactory.create(comboqSystem4);
					QueryExecution qexecBox4=QueryExecutionFactory.create(queryBox4, model1);
					try {
						List<QuerySolution> resultList4 = null;
						ResultSet rsBox4 = qexecBox4.execSelect();
						resultList4=ResultSetFormatter.toList(rsBox4);
						if (resultList4 != null) {
							String stringa4="";
							resultListVulnerability.clear();
							for (QuerySolution valore : resultList4) {
								stringa4=valore.toString();
								/* elimina i prefissi IRI */
								stringa4=stringa4.substring(stringa4.indexOf("#")+1, stringa4.length()-3);
								resultListVulnerability.add(stringa4);
							}
							for (String i : resultListVulnerability) {
								comboBoxQuery3.addItem(i);
							}
						}
					}
					finally {
						qexecBox4.close();
					}
					break;
				case 5:
					valAspect=itemQuery2;
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect5=Query("qSystem5",itemQuery2,itemQuery1,"");
					Query queryBox5= QueryFactory.create(comboqAspect5);
					QueryExecution qexecBox5=QueryExecutionFactory.create(queryBox5, model1);
					try {
						List<QuerySolution> resultList5 = null;
						ResultSet rsBox5 = qexecBox5.execSelect();
						resultList5=ResultSetFormatter.toList(rsBox5);
						if (resultList5 != null) {
							String stringa5="";
							resultListSystem.clear();
							for (QuerySolution valore : resultList5) {
								stringa5=valore.toString();
								/* elimina i prefissi IRI */
								stringa5=stringa5.substring(stringa5.indexOf("#")+1, stringa5.length()-3);
								resultListSystem.add(stringa5);
							}
							for (String i : resultListSystem) {
								comboBoxQuery3.addItem(i);
							}
						}
					}
					finally {
						qexecBox5.close();
					}
					break;
				case 6:
					valAspect=itemQuery2;
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect6=Query("qSystem6",itemQuery2,itemQuery1,"");
					Query queryBox6= QueryFactory.create(comboqAspect6);
					QueryExecution qexecBox6=QueryExecutionFactory.create(queryBox6, model1);
					try {
						List<QuerySolution> resultList6 = null;
						ResultSet rsBox6 = qexecBox6.execSelect();
						resultList6=ResultSetFormatter.toList(rsBox6);
						if (resultList6 != null) {
							String stringa6="";
							resultListSystem.clear();
							for (QuerySolution valore : resultList6) {
								stringa6=valore.toString();
								/* elimina i prefissi IRI */
								stringa6=stringa6.substring(stringa6.indexOf("#")+1, stringa6.length()-3);
								resultListSystem.add(stringa6);
							} 
							for (String i : resultListSystem) {
								comboBoxQuery3.addItem(i);
							}
						}
					}
					finally {
						qexecBox6.close();
					}
					break;
					
				}
			}
		});
		comboBoxQuery2.setBounds(220, 112, 649, 22);
		tabricerca.add(comboBoxQuery2);
		
		JComboBox comboBoxQuery1 = new JComboBox();
		comboBoxQuery1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxQuery2.removeAllItems();
				comboBoxQuery3.removeAllItems();
				comboBoxQuery4.removeAllItems();
				comboBoxQuery5.removeAllItems();
				comboBoxQuery6.removeAllItems();
				/* recupera il valore e ne rimuove gli eventuali spazi */
				Object tmpQ1=comboBoxQuery1.getSelectedItem();
				if (tmpQ1 != null) {
					tempQuery1=tmpQ1.toString().replaceAll("\\s+","");
					/* memorizza il valore solo se modificato */
					if (tempQuery1 != itemQuery1) {
						itemQuery1=tempQuery1;
					}
				}
				
				switch (indice) {
				case 0:
					break;
				case 1:
					/* System_aspect */
					valSystem=itemQuery1;
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect1=Query("qAspect1",itemQuery1,"","");
					Query queryBox1= QueryFactory.create(comboqAspect1);
					QueryExecution qexecBox1=QueryExecutionFactory.create(queryBox1, model1);
					try {
						List<QuerySolution> resultList1 = null;
						ResultSet rsBox1 = qexecBox1.execSelect();
						resultList1=ResultSetFormatter.toList(rsBox1);
						if (resultList1 != null) {
							String stringa1="";
							resultListAspect.clear();
							for (QuerySolution valore : resultList1) {
								stringa1=valore.toString();
								/* elimina i prefissi IRI */
								stringa1=stringa1.substring(stringa1.indexOf("#")+1, stringa1.length()-3);
								resultListAspect.add(stringa1);
							}
							for (String i : resultListAspect) {
								comboBoxQuery2.addItem(i);
							}
						}
					}
					finally {
						qexecBox1.close();
					}
					break;
				case 2:
					valHazard=itemQuery1;
					/* Critical event of system */
					/* Query che valorizza comboBoxQuery2 */
					String comboqEvent2=Query("qEvent2",itemQuery1,"","");
					Query queryBox2= QueryFactory.create(comboqEvent2);
					QueryExecution qexecBox2=QueryExecutionFactory.create(queryBox2, model1);
					try {
						List<QuerySolution> resultList2 = null;
						ResultSet rsBox2 = qexecBox2.execSelect();
						resultList2=ResultSetFormatter.toList(rsBox2);
						if (resultList2 != null) {
							String stringa2="";
							resultListEvent.clear();
							for (QuerySolution valore : resultList2) {
								stringa2=valore.toString();
								/* elimina i prefissi IRI */
								stringa2=stringa2.substring(stringa2.indexOf("#")+1, stringa2.length()-3);
								resultListEvent.add(stringa2);
							}
							for (String i : resultListEvent) {
								comboBoxQuery2.addItem(i);
							}
						}
					}
					finally {
						qexecBox2.close();
					}
					break;
				case 3:
					valEvent=itemQuery1;
					/* Anthropic hazard */
					/* Query che valorizza comboBoxQuery2 */
					String comboqHazard3=Query("qHazard3",itemQuery1,"","");
					Query queryBox3= QueryFactory.create(comboqHazard3);
					QueryExecution qexecBox3=QueryExecutionFactory.create(queryBox3, model1);
					try {
						List<QuerySolution> resultList3 = null;
						ResultSet rsBox3 = qexecBox3.execSelect();
						resultList3=ResultSetFormatter.toList(rsBox3);
						if (resultList3 != null) {
							String stringa3="";
							resultListHazard.clear();
							for (QuerySolution valore : resultList3) {
								stringa3=valore.toString();
								/* elimina i prefissi IRI */
								stringa3=stringa3.substring(stringa3.indexOf("#")+1, stringa3.length()-3);
								resultListHazard.add(stringa3);
							}
							for (String i : resultListHazard) {
								comboBoxQuery2.addItem(i);
							}
						}
					}
					finally {
						qexecBox3.close();
					}
					break;
				case 4:
					valAspect=itemQuery1;
					/* System_aspect */
					/* Query che valorizza comboBoxQuery2 */
					String comboqSystem4=Query("qSystem4",itemQuery1,"","");
					Query queryBox4= QueryFactory.create(comboqSystem4);
					QueryExecution qexecBox4=QueryExecutionFactory.create(queryBox4, model1);
					try {
						List<QuerySolution> resultList4 = null;
						ResultSet rsBox4 = qexecBox4.execSelect();
						resultList4=ResultSetFormatter.toList(rsBox4);
						if (resultList4 != null) {
							String stringa4="";
							resultListSystem.clear();
							for (QuerySolution valore : resultList4) {
								stringa4=valore.toString();
								/* elimina i prefissi IRI */
								stringa4=stringa4.substring(stringa4.indexOf("#")+1, stringa4.length()-3);
								resultListSystem.add(stringa4);
							}
							for (String i : resultListSystem) {
								comboBoxQuery2.addItem(i);
							}
						}
					}
					finally {
						qexecBox4.close();
					}
					break;
				case 5:
					valVulnerability=itemQuery1;
					/* Vulnerability */
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect5=Query("qAspect5",itemQuery1,"","");
					Query queryBox5= QueryFactory.create(comboqAspect5);
					QueryExecution qexecBox5=QueryExecutionFactory.create(queryBox5, model1);
					try {
						List<QuerySolution> resultList5 = null;
						ResultSet rsBox5 = qexecBox5.execSelect();
						resultList5=ResultSetFormatter.toList(rsBox5);
						if (resultList5 != null) {
							String stringa5="";
							resultListAspect.clear();
							for (QuerySolution valore : resultList5) {
								stringa5=valore.toString();
								/* elimina i prefissi IRI */
								stringa5=stringa5.substring(stringa5.indexOf("#")+1, stringa5.length()-3);
								resultListAspect.add(stringa5);
							}
							for (String i : resultListAspect) {
								comboBoxQuery2.addItem(i);
							}
						}
					}
					finally {
						qexecBox5.close();
					}
					break;
				case 6:
					valStakeholder=itemQuery1;
					/* Stakeholder */
					/* Query che valorizza comboBoxQuery2 */
					String comboqAspect6=Query("qAspect6",itemQuery1,"","");
					Query queryBox6= QueryFactory.create(comboqAspect6);
					QueryExecution qexecBox6=QueryExecutionFactory.create(queryBox6, model1);
					try {
						List<QuerySolution> resultList6 = null;
						ResultSet rsBox6 = qexecBox6.execSelect();
						resultList6=ResultSetFormatter.toList(rsBox6);
						if (resultList6 != null) {
							String stringa6="";
							resultListAspect.clear();
							for (QuerySolution valore : resultList6) {
								stringa6=valore.toString();
								/* elimina i prefissi IRI */
								stringa6=stringa6.substring(stringa6.indexOf("#")+1, stringa6.length()-3);
								resultListAspect.add(stringa6);
							}
							for (String i : resultListAspect) {
								comboBoxQuery2.addItem(i);
							}
						}
					}
					finally {
						qexecBox6.close();
					}
					break;
				}
			}
		});
		;
		
		comboBoxQuery1.setBounds(220, 79, 649, 22);
		tabricerca.add(comboBoxQuery1);
		
		JComboBox comboBoxSoggetto1 = new JComboBox(soggetto);
		comboBoxSoggetto1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxQuery1.removeAllItems();
				comboBoxQuery2.removeAllItems();
				comboBoxQuery3.removeAllItems();
				comboBoxQuery4.removeAllItems();
				comboBoxQuery5.removeAllItems();
				comboBoxQuery6.removeAllItems();
				comboBoxQuery1.setEnabled(true);
				comboBoxQuery2.setEnabled(true);
				comboBoxQuery3.setEnabled(true);
				comboBoxQuery4.setEnabled(true);
				comboBoxQuery5.setEnabled(true);
				comboBoxQuery6.setEnabled(true);
				/* indice dell'elenco selezionato partendo da 0 */
				indice=comboBoxSoggetto1.getSelectedIndex();
				Object cbS1=comboBoxSoggetto1.getSelectedItem();
				if (cbS1 != null) {
					comboSoggetto1=cbS1.toString();
				}
				switch (indice) {
				case 0:
					/* elenco a discesa vuoto */
					textFieldSoggetto2.setText("");
					textFieldSoggetto3.setText("");
					textFieldSoggetto4.setText("");
					textFieldSoggetto5.setText("");
					textFieldSoggetto6.setText("");
				break;
				case 1:
					/* valore System */
					textFieldSoggetto2.setText("System_aspect");
					textFieldSoggetto3.setText("Vulnerability");
					textFieldSoggetto4.setText("Critical_event_of_system");
					textFieldSoggetto5.setText("Anthropic_hazard");
					textFieldSoggetto6.setText("Stakeholder");
					/* inserire i POI come stringa */
					for (int i=0; i<item.length; i++) {
						comboBoxQuery1.addItem(item[i]);
					}
				break;
				case 2:
					/* valore Anthropic hazard */
					textFieldSoggetto2.setText("Critical_event_of_system");
					textFieldSoggetto3.setText("System_aspect");
					textFieldSoggetto4.setText("System");
					textFieldSoggetto5.setText("Vulnerability");
					textFieldSoggetto6.setText("Stakeholder");
					/* aggiungere query */
					String queryAllHazard=Query("qAllHazard", "", "", "");
					Query querySog2= QueryFactory.create(queryAllHazard);
					QueryExecution qexecSog2=QueryExecutionFactory.create(querySog2, model1);
						List<QuerySolution> resultSog2 = null;
						ResultSet rsSog2 = qexecSog2.execSelect();
						resultSog2=ResultSetFormatter.toList(rsSog2);
						if (resultSog2 != null) {
							resultListHazard.clear();
							for (QuerySolution valore : resultSog2) {
								String stringa2=valore.toString();
								/* elimina i prefissi IRI */
								stringa2=stringa2.substring(stringa2.indexOf("#")+1, stringa2.length()-3);
								resultListHazard.add(stringa2);
							}
							for (String i : resultListHazard) {
								comboBoxQuery1.addItem(i);
							}
						}
				break;
				case 3:
					/* valore Critical Event of System */
					textFieldSoggetto2.setText("Anthropic_hazard");
					textFieldSoggetto3.setText("System_aspect");
					textFieldSoggetto4.setText("System");
					textFieldSoggetto5.setText("Vulnerability");
					textFieldSoggetto6.setText("Stakeholder");
					String queryAllEvent=Query("qAllEvent", "", "", "");
					Query querySog3= QueryFactory.create(queryAllEvent);
					QueryExecution qexecSog3=QueryExecutionFactory.create(querySog3, model1);
					List<QuerySolution> resultSog3 = null;
					ResultSet rsSog3 = qexecSog3.execSelect();
					resultSog3=ResultSetFormatter.toList(rsSog3);
					if (resultSog3 != null) {
						resultListEvent.clear();
						for (QuerySolution valore : resultSog3) {
							String stringa3=valore.toString();
							/* elimina i prefissi IRI */
							stringa3=stringa3.substring(stringa3.indexOf("#")+1, stringa3.length()-3);
							resultListEvent.add(stringa3);
						}
						for (String i : resultListEvent) {
							comboBoxQuery1.addItem(i);
						}
					}
				break;
				case 4:
					/* valore System aspect */ 
					textFieldSoggetto2.setText("System");
					textFieldSoggetto3.setText("Vulnerability");
					textFieldSoggetto4.setText("Stakeholder");
					textFieldSoggetto5.setText("Critical_event_of_system");
					textFieldSoggetto6.setText("Anthropic_hazard");
					String queryAllAspect=Query("qAllAspect", "", "", "");
					Query querySog4= QueryFactory.create(queryAllAspect);
					QueryExecution qexecSog4=QueryExecutionFactory.create(querySog4, model1);
					List<QuerySolution> resultSog4 = null;
					ResultSet rsSog4 = qexecSog4.execSelect();
					resultSog4=ResultSetFormatter.toList(rsSog4);
					if (resultSog4 != null) {	
						resultListAspect.clear();
						for (QuerySolution valore : resultSog4) {
							String stringa4=valore.toString();
							/* elimina i prefissi IRI */
							stringa4=stringa4.substring(stringa4.indexOf("#")+1, stringa4.length()-3);
							resultListAspect.add(stringa4);
						}
						for (String i : resultListAspect) {
							comboBoxQuery1.addItem(i);
						}
					}
				break;
				case 5:
					/* valore Vulnerability */
					textFieldSoggetto2.setText("System_aspect");
					textFieldSoggetto3.setText("System");
					textFieldSoggetto4.setText("Stakeholder");
					textFieldSoggetto5.setText("Critical_event_of_system");
					textFieldSoggetto6.setText("Anthropic_hazard");
					String queryAllVulnerability=Query("qAllVulnerability", "", "", "");
					Query querySog5= QueryFactory.create(queryAllVulnerability);
					QueryExecution qexecSog5=QueryExecutionFactory.create(querySog5, model1);
					List<QuerySolution> resultSog5 = null;
					ResultSet rsSog5 = qexecSog5.execSelect();
					resultSog5=ResultSetFormatter.toList(rsSog5);
					if (resultSog5 != null) {	
						resultListVulnerability.clear();
						for (QuerySolution valore : resultSog5) {
							String stringa5=valore.toString();
							/* elimina i prefissi IRI */
							stringa5=stringa5.substring(stringa5.indexOf("#")+1, stringa5.length()-3);
							resultListVulnerability.add(stringa5);
						}
						for (String i : resultListVulnerability) {
							comboBoxQuery1.addItem(i);
						}
					}
				break;
				case 6:
					/* valore Stakeholder */
					textFieldSoggetto2.setText("System_aspect");
					textFieldSoggetto3.setText("System");
					textFieldSoggetto4.setText("Vulnerability");
					textFieldSoggetto5.setText("Critical_event_of_system");
					textFieldSoggetto6.setText("Anthropic_hazard");
					String queryAllStakeholder=Query("qAllStakeholder", "", "", "");
					Query querySog6= QueryFactory.create(queryAllStakeholder);
					QueryExecution qexecSog6=QueryExecutionFactory.create(querySog6, model1);
					List<QuerySolution> resultSog6 = null;
					ResultSet rsSog6 = qexecSog6.execSelect();
					resultSog6=ResultSetFormatter.toList(rsSog6);
					if (resultSog6 != null) {		
						resultListStakeholder.clear();
						for (QuerySolution valore : resultSog6) {
							String stringa6=valore.toString();
							/* elimina i prefissi IRI */
							stringa6=stringa6.substring(stringa6.indexOf("#")+1, stringa6.length()-3);
							resultListStakeholder.add(stringa6);
						}
						for (String i : resultListStakeholder) {
							comboBoxQuery1.addItem(i);
						}
					}
				break;
				}
			}
		});
		
		
		comboBoxSoggetto1.setBounds(32, 79, 178, 22);
		tabricerca.add(comboBoxSoggetto1);
		
		textFieldSoggetto2 = new JTextField();
		textFieldSoggetto2.setEditable(false);
		textFieldSoggetto2.setBounds(32, 112, 178, 20);
		tabricerca.add(textFieldSoggetto2);
		textFieldSoggetto2.setColumns(10);
		
		textFieldSoggetto3 = new JTextField();
		textFieldSoggetto3.setEditable(false);
		textFieldSoggetto3.setBounds(32, 143, 178, 20);
		tabricerca.add(textFieldSoggetto3);
		textFieldSoggetto3.setColumns(10);
		
		textFieldSoggetto4 = new JTextField();
		textFieldSoggetto4.setEditable(false);
		textFieldSoggetto4.setBounds(32, 174, 178, 20);
		tabricerca.add(textFieldSoggetto4);
		textFieldSoggetto4.setColumns(10);
		
		textFieldSoggetto5 = new JTextField();
		textFieldSoggetto5.setEditable(false);
		textFieldSoggetto5.setBounds(32, 205, 178, 20);
		tabricerca.add(textFieldSoggetto5);
		textFieldSoggetto5.setColumns(10);
		
		textFieldSoggetto6 = new JTextField();
		textFieldSoggetto6.setEditable(false);
		textFieldSoggetto6.setBounds(32, 236, 178, 20);
		tabricerca.add(textFieldSoggetto6);
		textFieldSoggetto6.setColumns(10);
		
		JButton btnEsegui = new JButton("Run");
		btnEsegui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxQuery1.setEnabled(false);
				comboBoxQuery2.setEnabled(false);
				comboBoxQuery3.setEnabled(false);
				comboBoxQuery4.setEnabled(false);
				comboBoxQuery5.setEnabled(false);
				comboBoxQuery6.setEnabled(false);
				infoMap=CreaInfo(indice);
				/* ricava l'indice dell'elemento della combobox POI selezionato */
				int indice=FindInArray(item, valSystem);
				/* la seconda scheda viene riempita con il risultato del metodo Question */
				txtareaosm.setText(SemanticCrime.Question("",system[indice*3],system[indice*3+1],system[indice*3+2]));
				String totale="" + numTotale;
				txttotale.setText(totale);
				JPanel tabMappa = new JPanel();
				/* Aggiunge la scheda della mappa con il nome del POI */
				tabGenerale.addTab(system[indice*3].toString(), null, tabMappa, null);
				tabMappa.setBounds(10, 11, 965, 480);
				tabMappa.setLayout(null);
				
				/* Chiude scheda della mappa meno recente */
				JButton btnchiudi= new JButton("Close map");
				tabMappa.add(btnchiudi);
				btnchiudi.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tabGenerale.remove(tabMappa);
						tabricerca.remove(btnchiudi);
						/* roy azzeramento occorrenze */
						txttotale.setText("");
					}
				});
				
				// Create a TileFactoryInfo for OSM
		        TileFactoryInfo info = new OSMTileFactoryInfo();
		        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		        tileFactory.setThreadPoolSize(8);
				// Setup local file cache
		        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
				// Setup JXMapViewer
		        JXMapViewer mapViewer = new JXMapViewer();
		        mapViewer.setBounds(10, 5, 965, 486);
		        mapViewer.setTileFactory(tileFactory);
		        GeoPosition centroSiracusa = new GeoPosition(37.0746000,15.2819000);
		        GeoPosition punto[]= new GeoPosition[numTotale];
		        for (int i=0; i<numTotale; i++) {
		        	punto[i]=new GeoPosition(latitude[i],longitude[i]);
		        }
		        
		     // Set the focus
		        mapViewer.setZoom(8);
		        mapViewer.setAddressLocation(centroSiracusa);
		     // Add interactions
		        MouseInputListener mia = new PanMouseInputListener(mapViewer);
		        mapViewer.addMouseListener(mia);
		        mapViewer.addMouseMotionListener(mia);
		        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
		        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
		        
		        
		        SwingWaypoint[] segnaposto= new SwingWaypoint[numTotale];
		    	for (int i=0; i<numTotale; i++) {
		    		/* Testo da visualizzare nel segnaposto  e finestra di dialogo info */
//		    		segnaposto[i]=new SwingWaypoint(item[indice],punto[i]);
		    		segnaposto[i]=new SwingWaypoint(infoMap,punto[i]);
		    	}
		        
		        Set<SwingWaypoint> waypoints = new HashSet<SwingWaypoint>(Arrays.asList(segnaposto));
		        		
		        // Set the overlay painter
		        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
		        swingWaypointPainter.setWaypoints(waypoints);
		        mapViewer.setOverlayPainter(swingWaypointPainter);
		        // Add the JButtons to the map viewer
		        for (SwingWaypoint w : waypoints) {
		            mapViewer.add(w.getButton());
		        }
		        
				tabMappa.add(mapViewer);
				/* Aggiunge il pulsante per chiudere la mappa */
				btnchiudi.setBounds(263, 40, 120, 23);
				tabricerca.add(btnchiudi);
			}
		});
		btnEsegui.setBounds(879, 235, 89, 22);
		tabricerca.add(btnEsegui);
		
		JLabel lblNewLabel = new JLabel("Searching the Terminus Crime ontology");
		lblNewLabel.setBounds(32, 45, 277, 14);
		tabricerca.add(lblNewLabel);
	}
		
	/**
	 * 
	 * @param type Specifica il nome di uno dei 19 tipi di query SPARQL
	 * @param var1 Eventuale valore dell'ontologia Terminus-Crime in input
	 * @param var2 Eventuale valore dell'ontologia Terminus-Crime in input
	 * @param var3 Eventuale valore dell'ontologia Terminus-Crime in input
	 * @return Risultato della query SPARQL
	 */
	public static String Query (String type, String var1, String var2, String var3) {
		String risultato="";
		switch (type) {
		case "qAllHazard":
			/* elenca gli hazard sul primo elenco a discesa */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"Prefix owl: <http://www.w3.org/2002/07/owl#>" +
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"Select Distinct ?hazard " +
				"Where { " +
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s1 . " +
					"?s1 owl:onProperty tr:havingHazard . " +
					"?s1 owl:someValuesFrom ?hazard . " +
					"?hazard rdfs:subClassOf tr:Anthropic_hazard . " +
				"}" +
				"Order by ?hazard";
			break;
		case "qAllEvent":
			/* elenca gli eventi critici sul primo elenco a discesa  */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?event " +	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s1 . " +
					"?s1 owl:onProperty tr:havingCriticalEvent . " +
					"?s1 owl:someValuesFrom ?event . " +
					"?event rdfs:subClassOf tr:Critical_event_of_system . " +
				"} " +	
				"Order by ?event";
			break;
		case "qAllAspect":
			/* Elenca i system aspect sul primo elenco a discesa */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"Prefix owl: <http://www.w3.org/2002/07/owl#>" +
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"Select Distinct ?aspect " +
				"Where { " +
					"?event rdfs:subClassOf tr:Critical_event_of_system . " +
					"?event rdfs:subClassOf ?s1 . " +
					"?s1 owl:onProperty tr:concerning . " +
					"?s1 owl:someValuesFrom ?aspect . " +
					"?aspect rdfs:subClassOf tr:System_aspect . " +
				"}" +
				"Order by ?aspect";
			break;
		case "qAllVulnerability":
			/* elenca le vulnerabilità sul primo elenco a discesa  */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?vulnerability " +	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s1 . " +
					"?s1 owl:onProperty tr:isVulnerableTo . " +
					"?s1 owl:someValuesFrom ?vulnerability . " +
					"?vulnerability rdfs:subClassOf tr:Vulnerability . " +
				"} " +
				"Order by ?vulnerability";			
			break;
		case "qAllStakeholder":
			/* elenca gli stakeholder sul primo elenco a discesa  */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?stakeholder " +
				"Where { " +
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s1 . " +
					"?s1 owl:onProperty tr:havingPerformer . " +
					"?s1 owl:someValuesFrom ?stakeholder . " +
					"?stakeholder rdfs:subClassOf tr:Stakeholder . " +
				"} " +	
				"Order by ?stakeholder";
			break;
		case "qAspect1":
			/* var1=?system */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"Prefix owl: <http://www.w3.org/2002/07/owl#>" +
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"Select Distinct ?aspect " +
				"Where { " +
					"tr:" + var1 + " rdfs:subClassOf tr:System . " +
					"tr:" + var1 + " rdfs:subClassOf ?s19 . " +
					"?s19 owl:onProperty tr:havingSystemAspect . " +
					"?s19 owl:someValuesFrom ?aspect . " +
					"?aspect rdfs:subClassOf tr:System_aspect . " +
				"}" +
				"Order by ?aspect";
			break;
		case "qAspect6":
			/* var1=?stakeholder */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"Prefix owl: <http://www.w3.org/2002/07/owl#>" +
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"Select Distinct ?aspect " +
				"Where { " +
					"?system rdfs:subClassOf tr:System . " +
					"?system rdfs:subClassOf ?s25 . " +
					"?s25 owl:onProperty tr:havingSystemAspect . " +
					"?s25 owl:someValuesFrom ?aspect . " +
					"?aspect rdfs:subClassOf tr:System_aspect . " +
					"tr:" + var1 + " rdfs:subClassOf tr:Stakeholder . " +
					"tr:" + var1 + " rdfs:subClassOf ?s26 . " +
					"?s26 owl:onProperty tr:havingInterestOn . " +
					"?s26 owl:someValuesFrom ?aspect . " +
				"}" +
				"Order by ?aspect";
			break;
		case "qAspect23":
			/* var1=?event */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?aspect " +	
				"Where { " +	
					"?system rdfs:subClassOf tr:System . " +
					"?system rdfs:subClassOf ?s20 . " +
					"?s20 owl:onProperty tr:havingSystemAspect . " +
					"?s20 owl:someValuesFrom ?aspect . " +
					"?aspect rdfs:subClassOf tr:System_aspect . " +
					"tr:" + var1 + " rdfs:subClassOf tr:Critical_event_of_system . " +
					"tr:" + var1 + " rdfs:subClassOf ?s21 . " +
					"?s21 owl:onProperty tr:concerning . " +
					"?s21 owl:someValuesFrom ?aspect . " +
				"}" +	
				"Order by ?aspect";
			break;
		case "qAspect5":
			/* var1=?vulnerability */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?aspect " +	
				"Where { " +	
					"?system rdfs:subClassOf tr:System . " +
					"?system rdfs:subClassOf ?s22 . " +
					"?s22 owl:onProperty tr:havingSystemAspect . " +
					"?s22 owl:someValuesFrom ?aspect . " +
					"?aspect rdfs:subClassOf tr:System_aspect . " +
					"?aspect rdfs:subClassOf ?s23 . " +
					"?s23 owl:onProperty tr:havingVulnerability . " +
					"?s23 owl:someValuesFrom tr:" + var1 + " . " +
					"tr:" + var1 + " rdfs:subClassOf tr:Vulnerability . " +
					"?system rdfs:subClassOf ?s24 . " +
					"?s24 owl:onProperty tr:isVulnerableTo . " +
					"?s24 owl:someValuesFrom tr:" + var1 + " . " +
				"} " +	
				"Order by ?aspect";	
			break;
		case "qEvent2":
			/* var1=?hazard */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?event " +	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s16 . " +
					"?s16 owl:onProperty tr:havingCriticalEvent . " +
					"?s16 owl:someValuesFrom ?event . " +
					"?event rdfs:subClassOf tr:Critical_event_of_system . " +
					"tr:" + var1 + " rdfs:subClassOf tr:Anthropic_hazard . " +
					"tr:" + var1 + " rdfs:subClassOf ?s17 . " +
					"?s17 owl:onProperty tr:havingImpact . " +
					"?s17 owl:someValuesFrom ?event . " +
					"?poi rdfs:subClassOf ?s18 . " +
					"?s18 owl:onProperty tr:havingHazard . " +
					"?s18 owl:someValuesFrom tr:" + var1 + " . " +
				"} " +	
				"Order by ?event ";	
			break;
		case "qEvent1456":
			/* var1=?system var2=?aspect */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?event " +
				"Where { " +	
					"tr:" + var1 + " rdfs:subClassOf tr:System . " +
					"tr:" + var1 + " rdfs:subClassOf ?s14 . " +
					"?s14 owl:onProperty tr:havingCriticalEvent . " +
					"?s14 owl:someValuesFrom ?event . " +
					"?event rdfs:subClassOf tr:Critical_event_of_system . " +
					"?event rdfs:subClassOf ?s15 . " +
					"?s15 owl:onProperty tr:concerning . " +
					"?s15 owl:someValuesFrom tr:" + var2 + " . " +
					"tr:" + var2 + " rdfs:subClassOf tr:System_aspect . " +
				"} " +	
				"Order by ?event";
			break;
		case "qHazard3":
			/* var1=?event */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?hazard " +	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s11 . " +
					"?s11 owl:onProperty tr:havingHazard . " +
					"?s11 owl:someValuesFrom ?hazard . " +
					"?hazard rdfs:subClassOf tr:Anthropic_hazard . " +
					"?hazard rdfs:subClassOf ?s12 . " +
					"?s12 owl:onProperty tr:havingImpact . " +
					"?s12 owl:someValuesFrom tr:" + var1 + " . " +
					"?poi rdfs:subClassOf ?s13 . " +
					"?s13 owl:onProperty tr:havingCriticalEvent . " +
					"?s13 owl:someValuesFrom tr:" + var1 + " . " +
					"tr:" + var1 + " rdfs:subClassOf tr:Critical_event_of_system . " +
				"} " +	
				"Order by ?hazard";	
			break;
		case "qHazard1456":
			/* var1=?system var2=?event */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?hazard " +	
				"Where { " +	
					"tr:" + var1 + " rdfs:subClassOf tr:System . " +
					"tr:" + var1 + " rdfs:subClassOf ?s9 . " +
					"?s9 owl:onProperty tr:havingHazard . " +
					"?s9 owl:someValuesFrom ?hazard . " +
					"?hazard rdfs:subClassOf tr:Anthropic_hazard . " +
					"?hazard rdfs:subClassOf ?s10 . " +
					"?s10 owl:onProperty tr:havingImpact . " +
					"?s10 owl:someValuesFrom tr:" + var2 + " . " +
					"tr:" + var2 + " rdfs:subClassOf tr:Critical_event_of_system . " +
				"} " +
				"Order by ?hazard";
			break;
		case "qStakeholder12345":
			/* var1=?system var2=?aspect */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?stakeholder " +	
				"Where { " +	
					"tr:" + var1 + " rdfs:subClassOf tr:System . " +
					"tr:" + var1 + " rdfs:subClassOf ?s29 . " +
					"?s29 owl:onProperty tr:havingPerformer . " +
					"?s29 owl:someValuesFrom ?stakeholder . " +
					"?stakeholder rdfs:subClassOf tr:Stakeholder . " +
					"?stakeholder rdfs:subClassOf ?s30 . " +
					"?s30 owl:onProperty tr:havingInterestOn . " +
					"?s30 owl:someValuesFrom tr:" + var2 + " . " +
					"tr:" + var2 + " rdfs:subClassOf tr:System_aspect . " +
				"} " +	
				"Order by ?stakeholder";
			break;
		case "qSystem4":
			/* var1=?aspect */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?poi " +	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s4 . " +
					"?s4 owl:onProperty tr:havingSystemAspect . " +
					"?s4 owl:someValuesFrom tr:" + var1 + " . " +
					"tr:" + var1 + " rdfs:subClassOf tr:System_aspect . " +
				"} " +
				"Order by ?poi";
			break;
		case "qSystem5":
			/* var1=?aspect var2=?vulnerability */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?poi " + 	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s5 . " +
					"?s5 owl:onProperty tr:havingSystemAspect . " +
					"?s5 owl:someValuesFrom tr:" + var1 + " . " +
					"tr:" + var1 + " rdfs:subClassOf tr:System_aspect . " +
					"?poi rdfs:subClassOf ?s6 . " +
					"?s6 owl:onProperty tr:isVulnerableTo . " +
					"?s6 owl:someValuesFrom tr:" + var2 + " . " +
					"tr:" + var2 + " rdfs:subClassOf tr:Vulnerability . " +
				"} " +	
				"Order by ?poi";
			break;
		case "qSystem6":
			/* var1=?aspect var2=?stakeholder */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?poi " +	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s7 . " +
					"?s7 owl:onProperty tr:havingSystemAspect . " +
					"?s7 owl:someValuesFrom tr:" + var1 + " . " +
					"tr:" + var1 + " rdfs:subClassOf tr:System_aspect . " +
					"?poi rdfs:subClassOf ?s8 . " +
					"?s8 owl:onProperty tr:havingPerformer . " +
					"?s8 owl:someValuesFrom tr:" + var2 + " . " +
					"tr:" + var2 + " rdfs:subClassOf tr:Stakeholder . " +
				"} " +	
				"Order by ?poi";	
			break;
		case "qSystem23":
			/* var1=?hazard var2=?event var3=?aspect */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " + 	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?poi " + 	
				"Where { " +	
					"?poi rdfs:subClassOf tr:System . " +
					"?poi rdfs:subClassOf ?s1 . " +
					"?s1 owl:onProperty tr:havingHazard . " +
					"?s1 owl:someValuesFrom tr:" + var1 + " . " +
					"tr:" + var1 + " rdfs:subClassOf tr:Anthropic_hazard . " +
					"?poi rdfs:subClassOf ?s2 . " +
					"?s2 owl:onProperty tr:havingCriticalEvent . " +
					"?s2 owl:someValuesFrom tr:" + var2 + " . " +
					"tr:" + var2 + " rdfs:subClassOf tr:Critical_event_of_system . " +
					"?poi rdfs:subClassOf ?s3 . " +
					"?s3 owl:onProperty tr:havingSystemAspect . " +
					"?s3 owl:someValuesFrom tr:" + var3 + " . " +
					"tr:" + var3 + " rdfs:subClassOf tr:System_aspect . " +
				"} " +
				"Order by ?poi";
			break;
		case "qVulnerability12346":
			/* var1=?system var2=?aspect */
			risultato=
				"Prefix tr: <http://www.enea-terin-sen-apic.it/TERMINUS-crime-v01#> " +	
				"Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 	
				"Prefix owl: <http://www.w3.org/2002/07/owl#> " +	
				"Prefix ns: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
				"Select Distinct ?vulnerability " +	
				"Where { " +	
					"tr:" + var1 + " rdfs:subClassOf tr:System . " +
					"tr:" + var1 + " rdfs:subClassOf ?s27 . " +
					"?s27 owl:onProperty tr:isVulnerableTo . " +
					"?s27 owl:someValuesFrom ?vulnerability . " +
					"?vulnerability rdfs:subClassOf tr:Vulnerability . " +
					"tr:" + var2 + " rdfs:subClassOf tr:System_aspect . " +
					"tr:" + var2 + " rdfs:subClassOf ?s28 . " +
					"?s28 owl:onProperty tr:havingVulnerability . " +
					"?s28 owl:someValuesFrom ?vulnerability . " +
				"} " +	
				"Order by ?vulnerability";
			break;
		}
		return risultato;
	}
	
	/**
	 * @param type indica se cercare nodi, vie, relazioni o in tutto (con "")
	 * @param nomefile indica il nome del file da generare
	 * @param tipologia indica la chiave di ricerca
	 * @param descrizione indica il valore di ricerca
	 * @return il risultato della query Overpass QL
	 */
	public static String Question (String type, String nomefile, String tipologia,String descrizione) {
		
		String server = "http://overpass-api.de/api/interpreter?data=";
		String totaleNWR = null;
		String interrogazione=""; 
		String risultato="";
		switch (type) {
			case "nodi":
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
			case "vie":
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
			case "relazioni":
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
				risultato = r.getResponseBody(StandardCharsets.UTF_8);
			try (BufferedWriter outputFile =
				Files.newBufferedWriter(Paths.get(nomefile + ".osm.xml"), StandardCharsets.UTF_8)) {
				outputFile.write(risultato);
				totaleNWR=risultato;
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
		numTotale= Occurrences(totaleNWR,"id=");
		latitude=Value(totaleNWR, "lat=", 5, 10, numTotale);
		longitude=Value(totaleNWR, "lon=", 5, 10, numTotale);
		return risultato;
	}

/**
 * @param str la stringa su cui eseguire la ricerca
 * @param id la sottostringa da cercare
 * @return il numero di occorrenze
 */
	public static int Occurrences(String str, String id) {
		int conta = 0;
		Pattern p= Pattern.compile(id);
		Matcher m=p.matcher(str);
		while (m.find()) {
			conta++;
		}
		return conta;
	}
	
	/**
	 * 
	 * @param stringa indica la stringa da cui estrarre i valori
	 * @param etichetta indica l'etichetta che contiene i valori
	 * @param posIniziale indica l'offset del valore rispetto alla posizione dell'etichetta
	 * @param dimensione indica la lunghezza del valore
	 * @param numTotale indica il numero di occorrenze da prendere
	 * @return un array con tutti i valori
	 */
	public static double[] Value(String stringa, String etichetta, int posIniziale, int dimensione, int numTotale) { 
		String testo="";
		double[] numero=new double[numTotale];
		int posizione=0, inizio=0, fine=0;
		for (int i=0; i<numTotale; i++) {
			posizione+=1;
			posizione=stringa.indexOf(etichetta,posizione);
			inizio=posizione+posIniziale;
			fine=inizio+dimensione;
			testo=stringa.substring(inizio, fine);
			numero[i]=Double.parseDouble(testo);
		}
		return numero;
	}
	
	/**
	 * 
	 * @param array l'array di strighe oggetto della ricerca
	 * @param valoreCercato la stringa da cercare all'interno dell'array
	 * @return l'indice dell'array che contiene la stringa altrimenti -1
	 */
	public static int FindInArray(String[] array, String valoreCercato) {
		for (int i=0; i<array.length; i++)
			if (array[i].indexOf(valoreCercato)>=0) return i;  
		return -1;
	}
	
	/**
	 * 
	 * @param index indice dell'elenco a discesa (comboBoxSoggetto1)
	 * @return una stringa contenente tutti i risultati delle query SPARQL
	 */
	public static String CreaInfo(int index) {
		int j=1;
		/* System */
		String info="System:\n";
		if (index != 1) {
			for (String i : resultListSystem) {
				info= info + i + ", ";
				j++;
			}
		} else {
			info= info + valSystem;
		}
		/* System aspect */
		info= info + "\n\nSystem aspect:\n";
		if (index != 4) {
			for (String i : resultListAspect) {
				info= info + i + ", ";
				j++;
			}
		} else {
			info= info + valAspect;
		}
		/* Vulnerability */
		info= info + "\n\nVulnerability:\n";
		if (index != 5) {
			for (String i : resultListVulnerability) {
				info= info + i + ", ";
				j++;
			}
		} else {
			info= info + valVulnerability;
		}
		/* Critical event of system */
		info= info + "\n\nCritical event of system:\n";
		if (index != 3) {
			for (String i : resultListEvent) {
				info= info + i + ", ";
				j++;
			}
		} else {
			info= info + valEvent;
		}
		/* Anthropic hazard */
		info= info + "\n\nAnthropic hazard:\n";
		if (index != 2) {
			for (String i : resultListHazard) {
				info= info + i + ", ";
				j++;
			} 
		} else {
			info= info + valHazard;
		}
		/* Stakeholder */
		info= info + "\n\nStakeholder:\n";
		if (index != 6) {
			for (String i : resultListStakeholder) {
				info= info + i + ", ";
				j++;
			}
		} else {
			info= info + valStakeholder;
		}
		resultListSystem.clear();
		resultListHazard.clear();
		resultListEvent.clear();
		resultListAspect.clear();
		resultListVulnerability.clear();
		resultListStakeholder.clear();
		return info;
	} 
}
