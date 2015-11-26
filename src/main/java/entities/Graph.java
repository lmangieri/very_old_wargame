package entities;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;

import algorithm.GraphAlgorithm;
import algorithm.StrategiesAlgorithm;
import enums.ContinentNames;

public class Graph {
	private ConcurrentLinkedQueue<Node> nodes;
	private List<Vertice> vertices;
	private List<Continent> continentes;
	private GraphAlgorithm graphAlgorithm;
	private StrategiesAlgorithm strategiesAlgorithm;

	public Graph(ConcurrentLinkedQueue<Node> nodes, List<Vertice> vertices, List<Continent> continentes) {
		this.continentes = continentes;
		this.nodes = nodes;
		this.vertices = vertices;
		graphAlgorithm = new GraphAlgorithm(this);
		setStrategiesAlgorithm(new StrategiesAlgorithm(this));
	}
	
	public ConcurrentLinkedQueue<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ConcurrentLinkedQueue<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Vertice> getVertices() {
		return vertices;
	}
	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}
	public GraphAlgorithm getGraphAlgorithm() {
		return graphAlgorithm;
	}

	public void setGraphAlgorithm(GraphAlgorithm graphAlgorithm) {
		this.graphAlgorithm = graphAlgorithm;
	}
	
	public StrategiesAlgorithm getStrategiesAlgorithm() {
		return strategiesAlgorithm;
	}

	public void setStrategiesAlgorithm(StrategiesAlgorithm strategiesAlgorithm) {
		this.strategiesAlgorithm = strategiesAlgorithm;
	}	
	
	public List<Continent> getContinentes() {
		return continentes;
	}

	public void setContinentes(List<Continent> continentes) {
		this.continentes = continentes;
	}
	
	public Continent getContinentByName(String name) {
		for(Continent c : getContinentes()) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		throw new RuntimeException("Didn't find the continent with the specified name");
	}
	
	public Node getNodeByName(String origin) {
		for(Node n : getNodes()) {
			if(n.getName().equals(origin)) {
				return n;
			}
		}
		throw new RuntimeException("Wrong origin name...");
	}	
	
	
	public static Graph getGraphWithDefaultConfiguration() {

		
		
		List<Continent> continentes = new ArrayList<>();
		Node nArgentina = new Node("Argentina",217,626,ContinentNames.AMERICA_DO_SUL.getName()); 
		Node nBrasil = new Node("Brasil",276,545,ContinentNames.AMERICA_DO_SUL.getName());        
		Node nChile = new Node("Chile",197,547,ContinentNames.AMERICA_DO_SUL.getName());          
		Node nColombia = new Node("Colômbia",181,416,ContinentNames.AMERICA_DO_SUL.getName());
		continentes.add(new Continent(Arrays.asList(nArgentina,nBrasil,nChile,nColombia),ContinentNames.AMERICA_DO_SUL.getName()));

		Node nMexico = new Node("México",63,303,ContinentNames.AMERICA_DO_NORTE.getName());   //
		Node nCuba = new Node("Cuba",163,330,ContinentNames.AMERICA_DO_NORTE.getName());       // 
		Node nCalifornia = new Node("Califórnia",86,202,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nNovaYork = new Node("Nova York",121,263,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nVancouver = new Node("Vancouver",108,132,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nOtawa = new Node("Otawa",197,161,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nLabrador = new Node("Labrador",279,163,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nGroelandia = new Node("Groelândia",402,71,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nAlasca = new Node("Alasca",62,77,ContinentNames.AMERICA_DO_NORTE.getName());
		Node nMackenzie = new Node("Mackenzie",157,92,ContinentNames.AMERICA_DO_NORTE.getName());
		continentes.add(new Continent(Arrays.asList(nMexico,nCuba,nCalifornia,nNovaYork,
				nVancouver,nOtawa,nLabrador,nGroelandia,nAlasca,nMackenzie),ContinentNames.AMERICA_DO_NORTE.getName()));
		
		Node nAfricaDoSul = new Node("África Do Sul",609,606,ContinentNames.AFRICA.getName());
		Node nMadagascar = new Node("Madagascar",733,572,ContinentNames.AFRICA.getName());
		Node nCongo = new Node("Congo",595,515,ContinentNames.AFRICA.getName());
		Node nSudao = new Node("Sudão",630,406,ContinentNames.AFRICA.getName());
		Node nNigeria = new Node("Nigéria",496,386,ContinentNames.AFRICA.getName());
		Node nEgito = new Node("Egito",618,330,ContinentNames.AFRICA.getName());
		continentes.add(new Continent(Arrays.asList(nAfricaDoSul,nMadagascar,nCongo,nSudao,
				nNigeria,nEgito),ContinentNames.AFRICA.getName()));

		Node nIslandia = new Node("Islândia",484,126,ContinentNames.EUROPA.getName());
		Node nInglaterra = new Node("Inglaterra",462,195,ContinentNames.EUROPA.getName());
		Node nSuecia = new Node("Suécia",584,90,ContinentNames.EUROPA.getName());
		Node nMoscou = new Node("Moscou",684,187,ContinentNames.EUROPA.getName());
		Node nPortugal = new Node("Portugal",526,238,ContinentNames.EUROPA.getName());
		Node nPolonia = new Node("Polônia",625,242,ContinentNames.EUROPA.getName());
		Node nAlemanha = new Node("Alemanha",567,190,ContinentNames.EUROPA.getName());
		continentes.add(new Continent(Arrays.asList(nIslandia,nInglaterra,nSuecia,nMoscou,nPortugal,
				nPolonia,nAlemanha),ContinentNames.EUROPA.getName()));
		
		Node nOrienteMedio = new Node("Oriente Médio",732,366,ContinentNames.ASIA.getName());
		Node nIndia = new Node("India",870,365,ContinentNames.ASIA.getName());
		Node nVietna = new Node("Vietnã",979,405,ContinentNames.ASIA.getName());
		Node nChina = new Node("China",975,315,ContinentNames.ASIA.getName());
		Node nJapao = new Node("Japão",1131,302,ContinentNames.ASIA.getName());
		Node nAral = new Node("Aral",789,250,ContinentNames.ASIA.getName());
		Node nOmsk = new Node("Omsk",777,168,ContinentNames.ASIA.getName());
		Node nDudinka = new Node("Dudinka",802,77,ContinentNames.ASIA.getName());
		Node nSiberia = new Node("Sibéria",878,105,ContinentNames.ASIA.getName());
		Node nVladivostok = new Node("Vladivostok",1019,137,ContinentNames.ASIA.getName());
		Node nTchita = new Node("Tchita",931,195,ContinentNames.ASIA.getName());
		Node nMongolia = new Node("Mongólia",929,237,ContinentNames.ASIA.getName());
		continentes.add(new Continent(Arrays.asList(nOrienteMedio,nIndia,nVietna,nChina,nJapao,nAral,nOmsk,nDudinka,
				nSiberia,nVladivostok,nTchita,nMongolia),ContinentNames.ASIA.getName()));
		
		Node nSumatra = new Node("Sumatra",948,503,ContinentNames.OCEANIA.getName());
		Node nBorneo = new Node("Borneo",1021,504,ContinentNames.OCEANIA.getName());
		Node nNovaGuine = new Node("Nova Guiné",1126,557,ContinentNames.OCEANIA.getName());
		Node nAustralia = new Node("Austrália",1014,647,ContinentNames.OCEANIA.getName());
		continentes.add(new Continent(Arrays.asList(nSumatra,nBorneo,nNovaGuine,nAustralia),ContinentNames.OCEANIA.getName()));
		
		/*********************/

		Vertice v1 = new Vertice(nArgentina,nBrasil,1);
		Vertice v2 = new Vertice(nArgentina,nChile,1);
		nArgentina.setVertices(Arrays.asList(v1,v2));

		Vertice v3 = new Vertice(nBrasil,nArgentina,1);
		Vertice v4 = new Vertice(nBrasil,nChile,1);
		Vertice v5 = new Vertice(nBrasil,nColombia,1);
		Vertice v5_1 = new Vertice(nBrasil, nNigeria, 1);
		nBrasil.setVertices(Arrays.asList(v3,v4,v5, v5_1));

		Vertice v6 = new Vertice(nChile,nArgentina,1);
		Vertice v7 = new Vertice(nChile,nColombia,1);
		Vertice v8 = new Vertice(nChile,nBrasil,1);
		nChile.setVertices(Arrays.asList(v6,v7,v8));

		Vertice v9 = new Vertice(nColombia,nMexico,1);
		Vertice v10 = new Vertice(nColombia,nBrasil,1);
		Vertice v11 = new Vertice(nColombia,nChile,1);
		nColombia.setVertices(Arrays.asList(v9,v10,v11));

		// américa do norte

		Vertice v12 = new Vertice(nMexico,nColombia,1);
		Vertice v13 = new Vertice(nMexico,nCuba,1);
		Vertice v14 = new Vertice(nMexico,nNovaYork,1);
		Vertice v15 = new Vertice(nMexico,nCalifornia,1);
		nMexico.setVertices(Arrays.asList(v12,v13,v14,v15));

		Vertice v16 = new Vertice(nCuba,nMexico,1);
		Vertice v17 = new Vertice(nCuba,nNovaYork,1);
		nCuba.setVertices(Arrays.asList(v16,v17));

		Vertice v18 = new Vertice(nCalifornia,nMexico,1);
		Vertice v19 = new Vertice(nCalifornia,nNovaYork,1);
		Vertice v20 = new Vertice(nCalifornia,nVancouver,1);
		Vertice v21 = new Vertice(nCalifornia,nOtawa,1);
		nCalifornia.setVertices(Arrays.asList(v18,v19,v20,v21));

		Vertice v22 = new Vertice(nNovaYork,nMexico,1);
		Vertice v23 = new Vertice(nNovaYork,nCuba,1);
		Vertice v24 = new Vertice(nNovaYork,nOtawa,1);
		Vertice v25 = new Vertice(nNovaYork,nLabrador,1);
		Vertice v25_1 = new Vertice(nNovaYork,nCalifornia,1);
		nNovaYork.setVertices(Arrays.asList(v22,v23,v24,v25,v25_1));

		Vertice v26 = new Vertice(nVancouver,nCalifornia,1);
		Vertice v27 = new Vertice(nVancouver,nOtawa,1);
		Vertice v28 = new Vertice(nVancouver,nMackenzie,1);
		Vertice v29 = new Vertice(nVancouver,nAlasca,1);
		nVancouver.setVertices(Arrays.asList(v26,v27,v28,v29));

		Vertice v30 = new Vertice(nOtawa,nLabrador,1);
		Vertice v31 = new Vertice(nOtawa,nNovaYork,1);
		Vertice v32 = new Vertice(nOtawa,nCalifornia,1);
		Vertice v33 = new Vertice(nOtawa,nVancouver,1);
		Vertice v34 = new Vertice(nOtawa,nMackenzie,1);
		nOtawa.setVertices(Arrays.asList(v30,v31,v32,v33,v34));

		Vertice v35 = new Vertice(nLabrador,nGroelandia,1);
		Vertice v36 = new Vertice(nLabrador,nNovaYork,1);
		Vertice v37 = new Vertice(nLabrador,nOtawa,1);
		nLabrador.setVertices(Arrays.asList(v35,v36,v37));

		Vertice v38 = new Vertice(nGroelandia,nIslandia,1);
		Vertice v39 = new Vertice(nGroelandia,nMackenzie,1);
		Vertice v40 = new Vertice(nGroelandia,nLabrador,1);
		nGroelandia.setVertices(Arrays.asList(v38,v39,v40));

		Vertice v41 = new Vertice(nMackenzie,nGroelandia,1);
		Vertice v42 = new Vertice(nMackenzie,nOtawa,1);
		Vertice v43 = new Vertice(nMackenzie,nVancouver,1);
		Vertice v44 = new Vertice(nMackenzie,nAlasca,1);
		nMackenzie.setVertices(Arrays.asList(v41,v42,v43,v44));

		Vertice v45 = new Vertice(nAlasca,nMackenzie,1);
		Vertice v46 = new Vertice(nAlasca,nVancouver,1);
		Vertice v47 = new Vertice(nAlasca,nVladivostok,1);
		nAlasca.setVertices(Arrays.asList(v45,v46,v47));

		/*** África *****/

		Vertice v48 = new Vertice(nAfricaDoSul,nMadagascar,1);
		Vertice v49 = new Vertice(nAfricaDoSul,nCongo,1);
		Vertice v50 = new Vertice(nAfricaDoSul,nSudao,1);
		nAfricaDoSul.setVertices(Arrays.asList(v48,v49,v50));

		Vertice v51 = new Vertice(nMadagascar,nAfricaDoSul,1);
		Vertice v52 = new Vertice(nMadagascar,nSudao,1);
		nMadagascar.setVertices(Arrays.asList(v51,v52));

		Vertice v53 = new Vertice(nCongo,nAfricaDoSul,1);
		Vertice v54 = new Vertice(nCongo,nSudao,1);
		Vertice v55 = new Vertice(nCongo,nNigeria,1);
		nCongo.setVertices(Arrays.asList(v53,v54,v55));

		Vertice v56 = new Vertice(nSudao,nMadagascar,1);
		Vertice v57 = new Vertice(nSudao,nAfricaDoSul,1);
		Vertice v58 = new Vertice(nSudao,nCongo,1);
		Vertice v59 = new Vertice(nSudao,nNigeria,1);
		Vertice v60 = new Vertice(nSudao,nEgito,1);
		nSudao.setVertices(Arrays.asList(v56,v57,v58,v59,v60));

		Vertice v61 = new Vertice(nEgito,nSudao,1);
		Vertice v62 = new Vertice(nEgito,nNigeria,1);
		Vertice v63 = new Vertice(nEgito,nOrienteMedio,1);
		Vertice v64 = new Vertice(nEgito,nPortugal,1);
		Vertice v65 = new Vertice(nEgito,nPolonia,1);
		nEgito.setVertices(Arrays.asList(v61,v62,v63,v64,v65));

		Vertice v66 = new Vertice(nNigeria,nBrasil,1);
		Vertice v67 = new Vertice(nNigeria,nEgito,1);
		Vertice v68 = new Vertice(nNigeria,nSudao,1);
		Vertice v69 = new Vertice(nNigeria,nCongo,1);
		Vertice v70 = new Vertice(nNigeria,nPortugal,1);
		nNigeria.setVertices(Arrays.asList(v66,v67,v68,v69,v70));

		/*** Europa ***/

		Vertice v71 = new Vertice(nPortugal,nNigeria,1);
		Vertice v72 = new Vertice(nPortugal,nAlemanha,1);
		Vertice v73 = new Vertice(nPortugal,nInglaterra,1);
		Vertice v74 = new Vertice(nPortugal,nEgito,1);
		Vertice v74_1 = new Vertice(nPortugal,nPolonia,1);
 		nPortugal.setVertices(Arrays.asList(v71,v72,v73,v74,v74_1));

		Vertice v75 = new Vertice(nInglaterra,nIslandia,1);
		Vertice v76 = new Vertice(nInglaterra,nSuecia,1);
		Vertice v77 = new Vertice(nInglaterra,nPortugal,1);
		Vertice v78 = new Vertice(nInglaterra,nAlemanha,1);
		nInglaterra.setVertices(Arrays.asList(v75,v76,v77,v78));

		Vertice v79 = new Vertice(nIslandia,nGroelandia,1);
		Vertice v80 = new Vertice(nIslandia,nInglaterra,1);
		nIslandia.setVertices(Arrays.asList(v79,v80));

		Vertice v81 = new Vertice(nSuecia,nMoscou,1);
		Vertice v83 = new Vertice(nSuecia,nInglaterra,1);
		nSuecia.setVertices(Arrays.asList(v81,v83));

		Vertice v84 = new Vertice(nAlemanha,nPortugal,1);
		Vertice v85 = new Vertice(nAlemanha,nPolonia,1);
		Vertice v86 = new Vertice(nAlemanha,nInglaterra,1);
		nAlemanha.setVertices(Arrays.asList(v84,v85,v86));

		Vertice v88 = new Vertice(nMoscou,nSuecia,1);
		Vertice v89 = new Vertice(nMoscou,nPolonia,1);
		Vertice v90 = new Vertice(nMoscou,nOrienteMedio,1);
		Vertice v91 = new Vertice(nMoscou,nAral,1);
		Vertice v92 = new Vertice(nMoscou,nOmsk,1);
		nMoscou.setVertices(Arrays.asList(v88,v89,v90,v91,v92));

		Vertice v93 = new Vertice(nPolonia,nMoscou,1);
		Vertice v94 = new Vertice(nPolonia,nAlemanha,1);
		Vertice v95 = new Vertice(nPolonia,nOrienteMedio,1);
		Vertice v96 = new Vertice(nPolonia,nEgito,1);
		Vertice v9_6 = new Vertice(nPolonia,nPortugal,1);
		nPolonia.setVertices(Arrays.asList(v93,v94,v95,v96,v9_6));

		/****** Asia        ****** */

		Vertice v97 = new Vertice(nOrienteMedio,nEgito,1);
		Vertice v98 = new Vertice(nOrienteMedio,nPolonia,1);
		Vertice v99 = new Vertice(nOrienteMedio,nMoscou,1);
		Vertice v100 = new Vertice(nOrienteMedio,nIndia,1);
		Vertice v101 = new Vertice(nOrienteMedio,nAral,1);
		nOrienteMedio.setVertices(Arrays.asList(v97,v98,v99,v100,v101));

		Vertice v102 = new Vertice(nIndia,nOrienteMedio,1);
		Vertice v103 = new Vertice(nIndia,nAral,1);
		Vertice v104 = new Vertice(nIndia,nChina,1);
		Vertice v105 = new Vertice(nIndia,nVietna,1);
		Vertice v106 = new Vertice(nIndia,nSumatra,1);
		nIndia.setVertices(Arrays.asList(v102,v103,v104,v105,v106));

		Vertice v107 = new Vertice(nVietna,nBorneo,1);
		Vertice v108 = new Vertice(nVietna,nChina,1);
		Vertice v109 = new Vertice(nVietna,nIndia,1);
		nVietna.setVertices(Arrays.asList(v107,v108,v109));

		Vertice v110 = new Vertice(nAral,nOrienteMedio,1);
		Vertice v111 = new Vertice(nAral,nIndia,1);
		Vertice v112 = new Vertice(nAral,nChina,1);
		Vertice v113 = new Vertice(nAral,nOmsk,1);
		Vertice v114 = new Vertice(nAral,nMoscou,1);
		nAral.setVertices(Arrays.asList(v110,v111,v112,v113,v114));

		Vertice v115 = new Vertice(nChina,nJapao,1);
		Vertice v116 = new Vertice(nChina,nVietna,1);
		Vertice v117 = new Vertice(nChina,nIndia,1);
		Vertice v118 = new Vertice(nChina,nAral,1);
		Vertice v119 = new Vertice(nChina,nOmsk,1);
		Vertice v120 = new Vertice(nChina,nMongolia,1);
		Vertice v121 = new Vertice(nChina,nVladivostok,1);
		nChina.setVertices(Arrays.asList(v115,v116,v117,v118,v119,v120,v121));

		Vertice v122 = new Vertice(nJapao,nVladivostok,1);
		Vertice v123 = new Vertice(nJapao,nChina,1);
		nJapao.setVertices(Arrays.asList(v122,v123));

		Vertice v124 = new Vertice(nMongolia,nTchita,1);
		Vertice v125 = new Vertice(nMongolia,nDudinka,1);
		Vertice v126 = new Vertice(nMongolia,nOmsk,1);
		Vertice v127 = new Vertice(nMongolia,nChina,1);
		nMongolia.setVertices(Arrays.asList(v124,v125,v126,v127));


		Vertice v128 = new Vertice(nOmsk,nMoscou,1);
		Vertice v129 = new Vertice(nOmsk,nAral,1);
		Vertice v130 = new Vertice(nOmsk,nChina,1);
		Vertice v131 = new Vertice(nOmsk,nMongolia,1);
		Vertice v132 = new Vertice(nOmsk,nDudinka,1);
		nOmsk.setVertices(Arrays.asList(v128,v129,v130,v131,v132));

		Vertice v133 = new Vertice(nDudinka,nOmsk,1);
		Vertice v134 = new Vertice(nDudinka,nMongolia,1);
		Vertice v135 = new Vertice(nDudinka,nTchita,1);
		Vertice v136 = new Vertice(nDudinka,nSiberia,1);
		nDudinka.setVertices(Arrays.asList(v133,v134,v135,v136));

		Vertice v137 = new Vertice(nTchita,nSiberia,1);
		Vertice v138 = new Vertice(nTchita,nDudinka,1);
		Vertice v139 = new Vertice(nTchita,nMongolia,1);
		Vertice v140 = new Vertice(nTchita,nChina,1);
		Vertice v141 = new Vertice(nTchita,nVladivostok,1);
		nTchita.setVertices(Arrays.asList(v137,v138,v139,v140,v141));

		Vertice v142 = new Vertice(nSiberia,nVladivostok,1);
		Vertice v143 = new Vertice(nSiberia,nTchita,1);
		Vertice v144 = new Vertice(nSiberia,nDudinka,1);
		nSiberia.setVertices(Arrays.asList(v142,v143,v144));

		Vertice v145 = new Vertice(nVladivostok,nAlasca,1);
		Vertice v146 = new Vertice(nVladivostok,nJapao,1);
		Vertice v147 = new Vertice(nVladivostok,nChina,1);
		Vertice v148 = new Vertice(nVladivostok,nTchita,1);
		Vertice v149 = new Vertice(nVladivostok,nSiberia,1);
		nVladivostok.setVertices(Arrays.asList(v145,v146,v147,v148,v149));

		/**** Oceania ****/

		Vertice v150 = new Vertice(nSumatra,nAustralia,1);
		Vertice v151 = new Vertice(nSumatra,nIndia,1);
		nSumatra.setVertices(Arrays.asList(v150,v151));

		Vertice v152 = new Vertice(nBorneo,nVietna,1);
		Vertice v153 = new Vertice(nBorneo,nNovaGuine,1);
		Vertice v154 = new Vertice(nBorneo,nAustralia,1);
		nBorneo.setVertices(Arrays.asList(v152,v153,v154));

		Vertice v155 = new Vertice(nNovaGuine,nBorneo,1);
		Vertice v156 = new Vertice(nNovaGuine,nAustralia,1);
		nNovaGuine.setVertices(Arrays.asList(v155,v156));

		Vertice v157 = new Vertice(nAustralia,nNovaGuine,1);
		Vertice v158 = new Vertice(nAustralia,nBorneo,1);
		Vertice v159 = new Vertice(nAustralia,nSumatra,1);
		nAustralia.setVertices(Arrays.asList(v157,v158,v159));
		
		
		ConcurrentLinkedQueue<Node> nodes = new ConcurrentLinkedQueue<>();
		nodes.addAll(Arrays.asList(nArgentina,nBrasil,nChile,nColombia,nMexico,nCuba,nCalifornia,nNovaYork,nVancouver,nOtawa,nLabrador,nGroelandia,
				nAlasca,nMackenzie,nAfricaDoSul,nMadagascar,nCongo,nSudao,nNigeria,nEgito,nIslandia,nInglaterra,nSuecia,nMoscou,
				nPortugal,nPolonia,nAlemanha,nOrienteMedio,nIndia,nVietna,nChina,nJapao,nAral,nOmsk,nDudinka,nSiberia,nVladivostok,
				nTchita,nMongolia,nSumatra,nBorneo,nNovaGuine,nAustralia));
		
		Graph graph = new Graph(
		nodes,
		Arrays.asList(v1,v2,v3,v4,v5,v5_1,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,
				v27,v28,v29,v30,v31,v32,v33,v34,v35,v36,v37,v38,v39,v40,v41,v42,v43,v44,v45,v46,v47,v48,v49,v50,
				v51,v52,v53,v54,v55,v56,v57,v58,v59,v60,v61,v62,v63,v64,v65,v66,v67,v68,v69,v70,v71,v72,v73,v74,
				v75,v76,v77,v78,v79,v80,v81,v83,v84,v85,v86,v88,v89,v90,v91,v92,v93,v94,v95,v96,v97,v98,
				v99,v100,v101,v102,v103,v104,v105,v106,v107,v108,v109,v110,v111,v112,v113,v114,v115,v116,v117,
				v118,v119,v120,v121,v122,v123,v124,v125,v126,v127,v128,v129,v130,v131,v132,v133,v134,v135,v136,v137,
				v138,v139,v140,v141,v142,v143,v144,v145,v146,v147,v148,v149,v150,v151,v152,v153,v154,v155,v156,v157,v158,v159,v25_1,v74_1,v9_6)
		,continentes);
		
		return graph;
	}


}